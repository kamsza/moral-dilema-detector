package DilemmaDetector.Simulator;

import generator.Model;
import project.*;

import java.util.*;

public class RigidBodyMapper {

    //TODO get lane width from ontology? is it in centimeters
    public static final double LANE_WIDTH = PhysicsUtils.CmToMeters(300);


    public static List<Actor> createActors(Model model) {
        Map<Lane, ArrayList<Vehicle>> vehicleMap = model.getVehicles();
        Map<Lane, ArrayList<Living_entity>> livingEntityMap = model.getLivingEntities();

        List<Actor> result = new LinkedList<>();

        for (Map.Entry<Model.Side, TreeMap<Integer, Lane>> parentPair : model.getLanes().entrySet()) {
            Model.Side side = parentPair.getKey();
            for (Map.Entry childPair : (parentPair.getValue()).entrySet()) {
                Integer laneNumber = (Integer) childPair.getKey();
                Lane lane = (Lane) childPair.getValue();
                for (Vehicle vehicle : vehicleMap.get(lane)) {
                    if(vehicle != model.getVehicle()) {
                        RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(vehicle, side, laneNumber);
                        result.add(new Actor(vehicle, rigidBody));
                    }
                }
                for (Living_entity entity : livingEntityMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(entity, side, laneNumber);
                    result.add(new Actor(entity, rigidBody));
                }
            }
        }
        return result;
    }

    public static RigidBody rigidBodyForMainVehicle(Vehicle mainVehicle) {
        RigidBody rigidBody = new RigidBody();
        rigidBody.setPosition(new Vector2(0, 0));

        double accelX, accelY, speedX, speedY, width, length;

        accelX = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "accelX"));
        accelY = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "accelY"));
        speedX = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "speedX"));
        speedY = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "speedY"));
        width = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "width"));
        length = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "length"));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));
        rigidBody.setLength(length);
        rigidBody.setWidth(width);
        rigidBody.setInitialValues(rigidBody.getPosition(), rigidBody.getSpeed(), rigidBody.getAcceleration());

        return rigidBody;
    }

    public static RigidBody rigidBodyForEntity(Entity entity, Model.Side side, int laneNumber) {
        RigidBody rigidBody = new RigidBody();

        double positionX;
        double positionY;
        double accelX, accelY, speedX, speedY, width, length;

        Object[] pos = entity.getDistance().toArray();
        positionX = (float) pos[0];// It is in cm, so we change it to meters
        positionX = PhysicsUtils.CmToMeters(positionX);

        if (side == Model.Side.LEFT) {
            positionY = laneNumber * LANE_WIDTH * (-1);
        } else if (side == Model.Side.RIGHT) {
            positionY = laneNumber * LANE_WIDTH;
        } else {
            positionY = 0;
        }

        accelX = PhysicsUtils.CmToMeters(getProperty(entity, "accelX"));
        accelY = PhysicsUtils.CmToMeters(getProperty(entity, "accelY"));
        speedX = PhysicsUtils.CmToMeters(getProperty(entity, "speedX"));
        speedY = PhysicsUtils.CmToMeters(getProperty(entity, "speedY"));
        width = PhysicsUtils.CmToMeters(getProperty(entity, "width"));
        length = PhysicsUtils.CmToMeters(getProperty(entity, "length"));
        rigidBody.setPosition(new Vector2(positionX, positionY));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));
        rigidBody.setLength(length);
        rigidBody.setWidth(width);

        rigidBody.setInitialValues(rigidBody.getPosition(), rigidBody.getSpeed(), rigidBody.getAcceleration());

        return rigidBody;
    }

    public static double getProperty(Entity entity, String propertyName) {

        Double returnValue = 0.0;
        switch (propertyName) {
            case "accelX":
                if (entity.hasAccelerationX()) {
                    Iterator<? extends Float> iterator = entity.getAccelerationX().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "accelY":
                if (entity.hasAccelerationY()) {
                    Iterator<? extends Float> iterator = entity.getAccelerationY().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "speedX":
                if (entity.hasSpeedX()) {
                    Iterator<? extends Float> iterator = entity.getSpeedX().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "speedY":
                if (entity.hasSpeedY()) {
                    Iterator<? extends Float> iterator = entity.getSpeedY().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "width":
                if (entity.hasWidth()) {
                    Iterator<? extends Float> iterator = entity.getWidth().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "length":
                if (entity.hasLength()) {
                    Iterator<? extends Float> iterator = entity.getLength().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            default:
                return 0.0;
        }
    }

}
