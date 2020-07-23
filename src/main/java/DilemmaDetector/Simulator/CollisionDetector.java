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

    public CollisionDetector(Model model){
        this.model = model;
        this.mainVehicle = RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle());
        vehicles = new HashMap<>();
        animals = new HashMap<>();
        pedestrians = new HashMap<>();
        createRigidBodies(model);
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

    public void detectCollisionInTime(Decision decision){
        double currentTime = 0;
        while (currentTime < MOVING_TIME){
            currentTime += TIME_PART;
            if (detectCollisionInMoment()){
                System.out.println("Collision in decision " + decision.toString());
                return;
            }
        }
        System.out.println("No collision in decision  " + decision.toString());
    }


    public boolean detectCollisionInMoment(){
        //s = v0*t + at^2/2
        Vector2 mainVehiclePositionChange = mainVehicle.getSpeed().mul(TIME_PART).add((mainVehicle.getAcceleration().mul(TIME_PART * TIME_PART).mul(0.5)));
        Vector2 mainVehicleNewSpeed = mainVehicle.getSpeed().add(mainVehicle.getAcceleration().mul(TIME_PART)); //v = v0 + at

        if (!vehicles.isEmpty()) {
            for (Map.Entry<RigidBody, Vehicle> entry : vehicles.entrySet()) {
                if (detectCollisionWithVehicleInMoment(entry.getKey(), entry.getValue(), mainVehiclePositionChange))
                   return true;
            }
        }
        if(! animals.isEmpty()) {
            for (Map.Entry<RigidBody, Animal> entry : animals.entrySet()) {
                if (detectCollisionWithAnimalInMoment(entry.getKey(), entry.getValue(), mainVehiclePositionChange))
                    return true;
            }
        }

        if(! pedestrians.isEmpty()) {
            for (Map.Entry<RigidBody, Pedestrian> entry : pedestrians.entrySet()) {
                if (detectCollisionWithPedestrianInMoment(entry.getKey(), entry.getValue(), mainVehiclePositionChange))
                    return true;
            }
        }
    }

    public boolean detectCollisionWithVehicleInMoment(RigidBody rigidBody, Vehicle vehicle, Vector2 mainVehiclePositionChange){
        Vector2 positionChange = rigidBody.getSpeed().mul(TIME_PART).add(rigidBody.getAcceleration().mul(TIME_PART*TIME_PART).mul(0.5));
        Vector2 newSpeed = rigidBody.getSpeed().add(rigidBody.getAcceleration().mul(TIME_PART));
        Vector2 newPosition = positionChange.sub(mainVehiclePositionChange);
        boolean isCollision = false;
        if (rigidBody.getPosition().y > 0 && newPosition.y < 0 &&
                rigidBody.getPosition().x + newPosition.x < (vehicle.getWidth() + mainVehicle.getWidth())/2){
            isCollision = true;
        }
        else {
            rigidBody.setSpeed(newSpeed);
            rigidBody.setPosition(newPosition);
        }
        return isCollision;
    }

    public boolean detectCollisionWithAnimalInMoment(RigidBody rigidBody, Animal animal, Vector2 mainVehiclePositionChange){
        Vector2 positionChange = rigidBody.getSpeed().mul(TIME_PART).add(rigidBody.getAcceleration().mul(TIME_PART*TIME_PART).mul(0.5));
        Vector2 newSpeed = rigidBody.getSpeed().add(rigidBody.getAcceleration().mul(TIME_PART));
        Vector2 newPosition = positionChange.sub(mainVehiclePositionChange);
        boolean isCollision = false;
        if (rigidBody.getPosition().y > 0 && newPosition.y < 0 &&
                rigidBody.getPosition().x + newPosition.x < (animal.getWidth() + mainVehicle.getWidth())/2){
            isCollision = true;
        }
        else {
            rigidBody.setSpeed(newSpeed);
            rigidBody.setPosition(newPosition);
        }
        return isCollision;
    }

    public boolean detectCollisionWithPedestrianInMoment(RigidBody rigidBody, Pedestrian pedestrian, Vector2 mainVehiclePositionChange){
        Vector2 positionChange = rigidBody.getSpeed().mul(TIME_PART).add(rigidBody.getAcceleration().mul(TIME_PART*TIME_PART).mul(0.5));
        Vector2 newSpeed = rigidBody.getSpeed().add(rigidBody.getAcceleration().mul(TIME_PART));
        Vector2 newPosition = positionChange.sub(mainVehiclePositionChange);
        boolean isCollision = false;
        if (rigidBody.getPosition().y > 0 && newPosition.y < 0 &&
                rigidBody.getPosition().x + newPosition.x < (pedestrian.getWidth() + mainVehicle.getWidth())/2){
            isCollision = true;
        }
        else {
            rigidBody.setSpeed(newSpeed);
            rigidBody.setPosition(newPosition);
        }
        return isCollision;
    }

}




