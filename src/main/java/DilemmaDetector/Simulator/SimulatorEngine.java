package DilemmaDetector.Simulator;

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

    private Actor mainVehicle;
    private CollisionDetector collisionDetector;

    public SimulatorEngine(Model model) {
        this.model = model;
        this.mainVehicle = new Actor(model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));
        this.actors = RigidBodyMapper.createActors(model);
        collisionDetector = new CollisionDetector(mainVehicle, this.actors);
    }

    public Map<Decision, List<Actor>> simulateAll(){
        Map<Decision, List<Actor>> collided = new HashMap<>();
        for(Map.Entry<Decision, Action> entry : this.model.getActionByDecision().entrySet()) {
            collided.put(entry.getKey(), simulate(entry.getValue()));
        }
        return collided;
    }

    public List<Actor> simulate(Action action) {
        double currentTime = 0;
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            System.out.println(
                    "Pos: " + mainVehicle.getRigidBody().getPosition() +
                            " | PrevPos: " + mainVehicle.getRigidBody().getPreviousPosition() +
                            " | Speed: " + mainVehicle.getRigidBody().getSpeed() +
                            " = " + mainVehicle.getRigidBody().getSpeed().getMagnitude() +
                            " | Acc: " + mainVehicle.getRigidBody().getAcceleration());

            if(action instanceof Turn_left){
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather().getClass(), false);
            }else if(action instanceof Turn_right){
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather().getClass(), true);
            }else if(action instanceof Follow){}

            mainVehicle.getRigidBody().update(TIME_PART);
            for (Actor actor : actors) {
                actor.getRigidBody().update(TIME_PART);
            }

            List<Actor> collided = collisionDetector.detectCollisionInMoment();

            if (!collided.isEmpty()) {
                System.out.println("Collision in action: " + action.toString());
                return collided;
            }
        }
        return new ArrayList<>();
    }
}
