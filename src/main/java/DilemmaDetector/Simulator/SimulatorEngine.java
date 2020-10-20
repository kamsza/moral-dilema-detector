package DilemmaDetector.Simulator;

import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import generator.Model;
import project.*;
import project.impl.DefaultDecision;

import java.util.*;

public class SimulatorEngine {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 3.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.01;

    private Model model;

    private List<Actor> actors;
    private List<Actor> surroundingActors;

    private Actor mainVehicle;
    private CollisionDetector collisionDetector;

    private CollisionConsequencePredictor consequencePredictor;

    public SimulatorEngine(Model model, CollisionConsequencePredictor consequencePredictor) {
        this.model = model;
        this.consequencePredictor = consequencePredictor;
        this.mainVehicle = new Actor(model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));
        this.surroundingActors = RigidBodyMapper.createSurroundingActors(model);
        this.actors = RigidBodyMapper.createActors(model);
        collisionDetector = new CollisionDetector(model, mainVehicle, this.actors, this.surroundingActors);
    }

    public Map<Decision, Set<Actor>> simulateAll(int lastLaneLeft, int lastLaneRight) {
        Map<Decision, Set<Actor>> collided = new HashMap<>();
        for (Map.Entry<Decision, Action> entry : this.model.getActionByDecision().entrySet()) {
            System.out.println(entry.getValue().getOwlIndividual().toString() + " \n \n");
            collided.put(entry.getKey(), simulate(entry.getValue(), entry.getKey()));
        }
        return collided;
    }

    public Set<Actor> simulate(Action action, Decision decision) {
        ChangeLaneActionApplier changeLaneActionApplier = new ChangeLaneActionApplier();
        double currentTime = 0;
        int laneWidth = 3;

        mainVehicle.getRigidBody().setToInitialValues();
        for (Actor actor : actors){
            actor.getRigidBody().setToInitialValues();
        }

        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            System.out.println(
                    "Pos: " + mainVehicle.getRigidBody().getPosition() +
                            " | PrevPos: " + mainVehicle.getRigidBody().getPreviousPosition() +
                            " | Speed: " + mainVehicle.getRigidBody().getSpeed() +
                            " = " + mainVehicle.getRigidBody().getSpeed().getMagnitude() +
                            " | Acc: " + mainVehicle.getRigidBody().getAcceleration());


            if (action instanceof Turn_left) {
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather().getClass(), false);
            } else if (action instanceof Turn_right) {
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather().getClass(), true);
            } else if (action instanceof Follow) {
            }
            else{
                // changing lanes
                // parsing String for now, have to change ontology to make use of instanceof
                String[] string = action.getOwlIndividual().toString().split("_");
                int sign;
                if(string[3].equals("right")){
                    sign = -1;
                }

                else{
                    sign = 1;
                }
                int laneNumber = sign*Integer.parseInt(string[5].substring(0, string[5].length()-1));
                System.out.println(laneNumber);
                changeLaneActionApplier.CarChangeLanes(mainVehicle.getRigidBody(), model.getWeather().getClass(), 0, laneNumber, laneWidth);
            }

            mainVehicle.getRigidBody().update(TIME_PART);
            for (Actor actor : actors) {
                actor.getRigidBody().update(TIME_PART);
            }

            Set<Actor> collided = collisionDetector.detectCollisionInMoment();

            if (!collided.isEmpty()) {
                System.out.println("Collision in action: " + action.toString() + "  " + collided.size());
                if(collided.size() == 1){
                    System.out.println("Create consequences");
                    for (Actor actor : collided) {
                        consequencePredictor.createCollisionConsequences(decision, actor);
                    }
                }
                for(Actor victim : collided){
                    for(Actor other : collided){
                        if(!victim.equals(other)){
                           consequencePredictor.createCollisionConsequences(decision, victim, other);
                        }
                    }
                }
                return collided;
            }
        }
        return new LinkedHashSet<>();
    }
}
