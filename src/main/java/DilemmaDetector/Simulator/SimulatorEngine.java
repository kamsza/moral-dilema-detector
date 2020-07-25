package DilemmaDetector.Simulator;

import generator.Model;
import project.Animal;
import project.Lane;
import project.Pedestrian;
import project.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimulatorEngine {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 3.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.1;

    private Model model;
    private Map<RigidBody, Vehicle> vehicles = new HashMap<>();;
    private Map<RigidBody, Animal> animals = new HashMap<>();;
    private Map<RigidBody, Pedestrian> pedestrians = new HashMap<>();;
    private RigidBody mainVehicle;
    private CollisionDetector collisionDetector;

    public SimulatorEngine(Model model) {
        this.model = model;
        this.mainVehicle = RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle());
        createRigidBodies(model);
        collisionDetector = new CollisionDetector(model, mainVehicle, vehicles, animals, pedestrians);
    }

    public void createRigidBodies(Model model) {
        Map<Lane, ArrayList<Vehicle>> vehicleMap = model.getVehicles();
        Map<Lane, ArrayList<Animal>> animalMap = model.getAnimals();
        Map<Lane, ArrayList<Pedestrian>> pedestrianMap = model.getPedestrians();

        Iterator<Map.Entry<Model.Side, Map<Integer, Lane>>> iterator = model.getLanes().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Model.Side, Map<Integer, Lane>> parentPair = iterator.next();
            Model.Side side = parentPair.getKey();
            Iterator<Map.Entry<Integer, Lane>> child = (parentPair.getValue()).entrySet().iterator();
            while (child.hasNext()) {
                Map.Entry childPair = child.next();
                Integer number = (Integer) childPair.getKey();
                Lane lane = (Lane) childPair.getValue();
                for (Vehicle vehicle : vehicleMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForVehicle(vehicle, side, lane, number);
                    vehicles.put(rigidBody, vehicle);
                }
                for (Animal animal : animalMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForAnimal(animal, side, lane, number);
                    animals.put(rigidBody, animal);
                }
                for (Pedestrian pedestrian : pedestrianMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForPedestrian(pedestrian, side, lane, number);
                    pedestrians.put(rigidBody, pedestrian);
                }
            }
        }
    }
}
