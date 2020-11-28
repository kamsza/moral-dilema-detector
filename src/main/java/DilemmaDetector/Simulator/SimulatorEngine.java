package DilemmaDetector.Simulator;

import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import generator.Model;
import project.*;

import java.util.*;

public class SimulatorEngine {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 5.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.01;
    //after first collision with pedestrian we continue simulation for extra time to detect next collisions
    private static final double EXTRA_TIME = 1.0;


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
        this.mainVehicle.setValueInDollars(RigidBodyMapper.getValueInDollars(model.getVehicle()));

        this.surroundingActors = RigidBodyMapper.createSurroundingActors(model);
        this.actors = RigidBodyMapper.createActors(model);
        collisionDetector = new CollisionDetector(model, mainVehicle, this.actors, this.surroundingActors);
    }

    public Map<Decision, Set<Actor>> simulateAll() {
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

        Set<Actor> collided = new LinkedHashSet<>();
        mainVehicle.getRigidBody().setToInitialValues();
        for (Actor actor : actors){
            actor.getRigidBody().setToInitialValues();
        }

        boolean collisionNotWithPedestrian = false;
        int collisionWithPedestrians = 0;

        double SIMULATION_TIME = MOVING_TIME;
        while (currentTime < SIMULATION_TIME && !collisionNotWithPedestrian) {
            currentTime += TIME_PART;
            System.out.print("Current time: " + currentTime + " | Simulation time: " + SIMULATION_TIME  + " | ");
            System.out.println(
                    "Pos: " + mainVehicle.getRigidBody().getPosition() +
                            " | PrevPos: " + mainVehicle.getRigidBody().getPreviousPosition() +
                            " | Speed: " + mainVehicle.getRigidBody().getSpeed() +
                            " = " + mainVehicle.getRigidBody().getSpeed().getMagnitude() +
                            " | Acc: " + mainVehicle.getRigidBody().getAcceleration());


            if (action instanceof Turn_left) {
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather(), false);
            } else if (action instanceof Turn_right) {
                BasicActionsApplier.CarTurning(mainVehicle.getRigidBody(), model.getWeather(), true);
            } else if (action instanceof Stop) {
                BasicActionsApplier.CarBreaking(mainVehicle.getRigidBody(), model.getWeather());
            } else if (action instanceof Change_lane){
                int laneNumber = ((Change_lane)action).getLane_change_by().iterator().next();
                changeLaneActionApplier.CarChangeLanes(mainVehicle.getRigidBody(), model.getWeather(), 0, laneNumber, laneWidth);
            }

            mainVehicle.getRigidBody().update(TIME_PART);
            for (Actor actor : actors) {
                actor.getRigidBody().update(TIME_PART);
            }

            Set<Actor> collidedInMoment = collisionDetector.detectCollisionInMoment();
            for(Actor actor : collidedInMoment ){
                if(Utils.isPedestrian(actor) ){
                    collisionWithPedestrians +=1;
                    if(collisionWithPedestrians == 1) {
                        SIMULATION_TIME = updateSimulationTime(currentTime);
                    }
                }
                else if(!Utils.isPedestrian(actor)){
                    if (!actor.equals(mainVehicle)) {
                        collisionNotWithPedestrian = true;
                    }
                }
            }
            if (!collidedInMoment.isEmpty())
                collided.addAll(collidedInMoment);
        }

        if (!collided.isEmpty()) {
            System.out.println("Collision in action: " + action.toString() + "  " + collided.size());
            collided.stream().map(a-> a.getEntityName()).forEach(System.out::println);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

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
        return new LinkedHashSet<>();
    }

    private double updateSimulationTime(double currentTime) {
        double SIMULATION_TIME;
        SIMULATION_TIME = currentTime + EXTRA_TIME;
        return SIMULATION_TIME;
    }
}
