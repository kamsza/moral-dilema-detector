package DilemmaDetector.Simulator;

import generator.Model;
import project.Animal;
import project.Decision;
import project.Pedestrian;
import project.Vehicle;
import project.impl.DefaultDecision;

import java.util.HashMap;
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
    private RigidBody mainVehicle;
    private CollisionDetector collisionDetector;

    public SimulatorEngine(Model model) {
        this.model = model;
        this.mainVehicle = RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle());
        RigidBodyMapper.createRigidBodies(model, vehicles, animals, pedestrians);
        collisionDetector = new CollisionDetector(model, mainVehicle, vehicles, animals, pedestrians);
    }

    // TODO wstrzykiwanie decyzji
    public void simulate(Decision decision)
    {
        collisionDetector.setMOVING_TIME(MOVING_TIME);
        collisionDetector.setTIME_PART(TIME_PART);
        collisionDetector.detectCollisionInTime(decision);
    }

}
