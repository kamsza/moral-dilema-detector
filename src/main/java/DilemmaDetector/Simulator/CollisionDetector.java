package DilemmaDetector.Simulator;

import generator.Model;
import project.Surrounding;

import java.util.*;

public class CollisionDetector {

    private Model scenarioModel;
    private List<Actor> actors;
    private List<Actor> surroundingActors;
    private Actor mainVehicle;

    public CollisionDetector(Model model, Actor mainVehicle,
                             List<Actor> actors, List<Actor> surroundingActors) {
        this.scenarioModel = model;
        this.mainVehicle = mainVehicle;
        this.actors = actors;
        this.surroundingActors = surroundingActors;
    }

    public Set<Actor> detectCollisionInMoment() {
        Set<Actor> collidedActors = new LinkedHashSet<>();
        for (Actor surroundingActor: surroundingActors) {
            if (detectCollisionWithRigidBodyInMoment(surroundingActor.getRigidBody(), surroundingActor.getEntityName())) {
                collidedActors.add(mainVehicle);
                collidedActors.add(surroundingActor); //ja dodałem
            }
        }

        for (Actor entry : actors) {
            if (entry.collidable) {
                if (detectCollisionWithRigidBodyInMoment(entry.getRigidBody(), entry.getEntityName())) {
                    collidedActors.add(entry);
                    collidedActors.add(mainVehicle);
                }
            }
        }

        return collidedActors;
    }

    public boolean detectCollisionWithRigidBodyInMoment(RigidBody rigidBody, String entityName) {
        boolean isCollision = false;
        double rigidBodyWidth = rigidBody.getWidth();
        double rigidBodyLength = rigidBody.getLength();
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle.getRigidBody(), rigidBody);
        if(distanceBetweenRigidBodies.y < (rigidBodyWidth + mainVehicle.getRigidBody().getWidth()) /2
                && distanceBetweenRigidBodies.x < (rigidBodyLength + mainVehicle.getRigidBody().getLength()) /2) {
            System.out.println("Collision with " + entityName + "  " + rigidBody.getPosition());
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




