package DilemmaDetector.Simulator;

import generator.Model;
import project.*;


import java.util.Map;

public class CollisionDetector {

    private Model model;
    private Map<RigidBody, Vehicle> vehicles;
    private Map<RigidBody, Animal> animals;
    private Map<RigidBody, Pedestrian> pedestrians;
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

//    public void detectCollisionInTime(Decision decision) {
//        double currentTime = 0;
//        while (currentTime < movingTime) {
//            currentTime += timePart;
//            if (detectCollisionInMoment()) {
//                System.out.println("Collision in decision " + decision.toString());
//                return;
//            }
//        }
//        System.out.println("No collision in decision  " + decision.toString());
//    }


    public boolean detectCollisionInMoment() {
        if (!vehicles.isEmpty()) {
            for (Map.Entry<RigidBody, Vehicle> entry : vehicles.entrySet()) {
                if (detectCollisionWithVehicleInMoment(entry.getKey(), entry.getValue(), Vector2.zero()))
                    return true;
            }
        }
        if (!animals.isEmpty()) {
            for (Map.Entry<RigidBody, Animal> entry : animals.entrySet()) {
                if (detectCollisionWithAnimalInMoment(entry.getKey(), entry.getValue(), Vector2.zero()))
                    return true;
            }
        }

        if (!pedestrians.isEmpty()) {
            for (Map.Entry<RigidBody, Pedestrian> entry : pedestrians.entrySet()) {
                if (detectCollisionWithPedestrianInMoment(entry.getKey(), entry.getValue(), Vector2.zero()))
                    return true;
            }
        }
        return false;
    }

    public boolean detectCollisionWithVehicleInMoment(RigidBody rigidBody, Vehicle vehicle, Vector2 previousPosition) {
        //TODO: is previous position needed?
        //TODO: if it is, the whole call chain needs to be refactored
        boolean isCollision = false;
        Double vehicleWidth = RigidBodyMapper.getProperty(vehicle, "width");
        if (rigidBody.getPosition().y < 0 && previousPosition.y > 0 &&
                rigidBody.getPosition().x + previousPosition.x < (vehicleWidth + mainVehicle.getWidth()) / 2) {
            isCollision = true;
        }
        return isCollision;
    }

    public boolean detectCollisionWithAnimalInMoment(RigidBody rigidBody, Animal animal, Vector2 previousPosition) {
        boolean isCollision = false;
        Double vehicleWidth = RigidBodyMapper.getProperty(animal, "width");
        if (rigidBody.getPosition().y < 0 && previousPosition.y > 0 &&
                rigidBody.getPosition().x + previousPosition.x < (vehicleWidth + mainVehicle.getWidth()) / 2) {
            isCollision = true;
        }
        return isCollision;
    }

    public boolean detectCollisionWithPedestrianInMoment(RigidBody rigidBody, Pedestrian pedestrian, Vector2 previousPosition) {
        boolean isCollision = false;
        Double vehicleWidth = RigidBodyMapper.getProperty(pedestrian, "width");
        if (rigidBody.getPosition().y < 0 && previousPosition.y > 0 &&
                rigidBody.getPosition().x + previousPosition.x < (vehicleWidth + mainVehicle.getWidth()) / 2) {
            isCollision = true;
        }
        return isCollision;
    }

}




