package DilemmaDetector.Simulator;

import generator.Model;
import project.*;
import project.impl.DefaultDecision;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulatorEngine {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 3.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.1;

    private Model model;
    private Map<RigidBody, Vehicle> vehicles = new HashMap<>();
    private Map<RigidBody, Animal> animals = new HashMap<>();
    private Map<RigidBody, Pedestrian> pedestrians = new HashMap<>();

    private List<RigidBody> actors = new LinkedList<>();

    private RigidBody mainVehicle;
    private CollisionDetector collisionDetector;

    public SimulatorEngine(Model model) {
        this.model = model;
        this.mainVehicle = RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle());
        RigidBodyMapper.createRigidBodies(model, vehicles, animals, pedestrians);
        actors.addAll(vehicles.keySet());
        actors.addAll(animals.keySet());
        actors.addAll(pedestrians.keySet());
        collisionDetector = new CollisionDetector(model, mainVehicle, vehicles, animals, pedestrians);
    }


    public void simulate(Decision decision)
    {
        double currentTime = 0;
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            // TODO wstrzykiwanie decyzji
            //  BasicActionsApplier.CarTurning(mainVehicle, Sunny.class, false);
            mainVehicle.update(TIME_PART);
            for (RigidBody actor : actors) {
                actor.update(TIME_PART);
                //TODO: IDEA: we can detect every collision here based only on rigidbodies
                //      and then set get from maps what was hit
            }

            if (collisionDetector.detectCollisionInMoment()) {
                System.out.println("Collision in decision " + decision.toString());
                return;
            }
        }
    }

}
