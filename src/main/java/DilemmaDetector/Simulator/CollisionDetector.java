package DilemmaDetector.Simulator;

import generator.Model;
import project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollisionDetector {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 3.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.1;

    private Model model;
    private Map<RigidBody, Vehicle> vehicles = null;
    private Map<RigidBody, Animal> animals = null;
    private Map<RigidBody, Pedestrian> pedestrians = null;
    private RigidBody mainVehicle;

    public CollisionDetector(Model model,
                             RigidBody mainVehicle,
                             Map<RigidBody, Vehicle> vehicles,
                             Map<RigidBody, Animal> animals,
                             Map<RigidBody, Pedestrian> pedestrians) {
        this.model = model;
        this.mainVehicle = mainVehicle;
        this.vehicles = vehicles;
        this.animals = animals;
        this.pedestrians = pedestrians;
    }

    public boolean detectCollisionInMoment() {
        if (!vehicles.isEmpty()) {
            for (Map.Entry<RigidBody, Vehicle> entry : vehicles.entrySet()) {
                if (detectCollisionWithVehicleInMoment(entry.getKey(), entry.getValue()))
                    return true;
            }
        }
        if (!animals.isEmpty()) {
            for (Map.Entry<RigidBody, Animal> entry : animals.entrySet()) {
                if (detectCollisionWithAnimalInMoment(entry.getKey(), entry.getValue()))
                    return true;
            }
        }

        if (!pedestrians.isEmpty()) {
            for (Map.Entry<RigidBody, Pedestrian> entry : pedestrians.entrySet()) {
                if (detectCollisionWithPedestrianInMoment(entry.getKey(), entry.getValue()))
                    return true;
            }
        }
        return false;
    }

    public boolean detectCollisionWithVehicleInMoment(RigidBody rigidBody, Vehicle vehicle) {
        boolean isCollision = false;
        double vehicleWidth = RigidBodyMapper.getProperty(vehicle, "width");
        Double vehicleLength = RigidBodyMapper.getProperty(vehicle, "length");
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle, rigidBody);
        if(distanceBetweenRigidBodies.x < (vehicleWidth + mainVehicle.getWidth()) /2
                && distanceBetweenRigidBodies.y < (vehicleLength + mainVehicle.getLength()) /2) {
            isCollision = true;
        }
        return isCollision;
    }

    public boolean detectCollisionWithAnimalInMoment(RigidBody rigidBody, Animal animal) {
        boolean isCollision = false;
        double animalWidth = RigidBodyMapper.getProperty(animal, "width");
        Double animalLength = RigidBodyMapper.getProperty(animal, "length");
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle, rigidBody);
        if(distanceBetweenRigidBodies.x < (animalWidth + mainVehicle.getWidth()) /2
                && distanceBetweenRigidBodies.y < (animalLength + mainVehicle.getLength()) /2) {
            isCollision = true;
        }
        return isCollision;
    }

    public boolean detectCollisionWithPedestrianInMoment(RigidBody rigidBody, Pedestrian pedestrian) {
        boolean isCollision = false;
        double pedestrianWidth = RigidBodyMapper.getProperty(pedestrian, "width");
        Double pedestrianLength = RigidBodyMapper.getProperty(pedestrian, "length");
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle, rigidBody);
        if(distanceBetweenRigidBodies.x < (pedestrianWidth + mainVehicle.getWidth()) /2
                && distanceBetweenRigidBodies.y < (pedestrianLength + mainVehicle.getLength()) /2) {
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




