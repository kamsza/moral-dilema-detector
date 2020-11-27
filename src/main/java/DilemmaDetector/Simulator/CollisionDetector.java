package DilemmaDetector.Simulator;

import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import generator.Model;
import generator.MyFactorySingleton;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Living_entity;
import project.MyFactory;
import project.Surrounding;
import project.Vehicle;

import java.io.FileNotFoundException;
import java.util.*;

public class CollisionDetector {

    private Model scenarioModel;
    private List<Actor> actors;
    private List<Actor> surroundingActors;
    private Actor mainVehicle;
    private MyFactory factory = MyFactorySingleton.getFactory();

    public CollisionDetector(Model model, Actor mainVehicle,
                             List<Actor> actors, List<Actor> surroundingActors) throws FileNotFoundException, OWLOntologyCreationException {
        this.scenarioModel = model;
        this.mainVehicle = mainVehicle;
        this.actors = actors;
        this.surroundingActors = surroundingActors;
    }


    private boolean isPedestrian(Actor victimActor, Living_entity victim){
        /*
         Check if entity described in victimActor is same as Living_entity victim. If true, then it must be pedestrian.
         It's a hack because of problems with ontology classes factory.getPedestrian(victim) will not work properly
       */
        return victimActor.getEntityName().equals(victim.getOwlIndividual().getIRI().toString());
    }

    private List<Living_entity> getLivingEntitiesFromActor(Actor actor) {
        Vehicle vehicle = factory.getVehicle(actor.getEntity());
        Living_entity living_entity = factory.getLiving_entity(actor.getEntity());
        List<Living_entity> result = new ArrayList<>();

        if (vehicle != null) {
//            System.out.println("Get victims from vehicle");
            result.addAll(vehicle.getVehicle_has_passenger());
            result.addAll(vehicle.getVehicle_has_driver());
        } else if (living_entity != null) {
            result.add(living_entity);
        }
        return result;
    }


    public boolean detectCollisionInMoment(Set<Actor> collidedActors) {
        for (Actor surroundingActor: surroundingActors) {
            if (detectCollisionWithRigidBodyInMoment(surroundingActor.getRigidBody(), surroundingActor.getEntityName())) {
                collidedActors.add(mainVehicle);
            }
        }

        boolean isPedestrian = false;

        for (Actor entry : actors) {
            if (detectCollisionWithRigidBodyInMoment(entry.getRigidBody(), entry.getEntityName())) {
                List<Living_entity> individualVictims = getLivingEntitiesFromActor(entry);
                for (Living_entity living_entity : individualVictims){
                    if (isPedestrian(entry, living_entity)){
                        isPedestrian = true;
                    };
                }
                collidedActors.add(entry);
                collidedActors.add(mainVehicle);
            }
        }

        return isPedestrian;
    }

    public boolean detectOutOfRoad(Actor mainVehicle){
        boolean outOfRoad = false;
        int lastLaneLeft =  scenarioModel.getLanes().get(Model.Side.LEFT).entrySet().size() + 1;
        int lastLaneRight = scenarioModel.getLanes().get(Model.Side.RIGHT).entrySet().size() + 1;

        double leftBorderY = lastLaneLeft * RigidBodyMapper.LANE_WIDTH;
        double rightBorderY = lastLaneRight * RigidBodyMapper.LANE_WIDTH * (-1);

        double vehicleY = mainVehicle.getRigidBody().getPosition().y;

        if (vehicleY > leftBorderY || vehicleY < rightBorderY){
            System.out.println("Main vehicle out of road ");
            outOfRoad = true;
        }

        return outOfRoad;
    }



    public boolean detectCollisionWithRigidBodyInMoment(RigidBody rigidBody, String entityName) {
        boolean isCollision = false;
        double rigidBodyWidth = rigidBody.getWidth();
        double rigidBodyLength = rigidBody.getLength();
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle.getRigidBody(), rigidBody);
        if(distanceBetweenRigidBodies.y < (rigidBodyWidth + mainVehicle.getRigidBody().getWidth()) /2
                && distanceBetweenRigidBodies.x < (rigidBodyLength + mainVehicle.getRigidBody().getLength()) /2) {
//            System.out.println("Collision with " + entityName + "  " + rigidBody.getPosition());
            isCollision = true;
        }
        return isCollision;
    }

    /*
    Returns distance between centres of rigidBodies as Vector2
     */
    private Vector2 getDistanceBetweenRigidBodies(RigidBody mainVehicle, RigidBody rigidBody) {
        double xDistance = Math.abs(rigidBody.getPosition().x - mainVehicle.getPosition().x);
        double yDistance = Math.abs(rigidBody.getPosition().y - mainVehicle.getPosition().y);
        return new Vector2(xDistance, yDistance);
    }


}




