package DilemmaDetector.Simulator;

import generator.Model;
import project.*;

import java.util.*;

public class RigidBodyMapper {

    public enum ActorType{SURROUNDING, LIVING, VEHICLE, OBSTACLE}

    //TODO get lane width from ontology? is it in centimeters
    public static final double LANE_WIDTH = PhysicsUtils.CmToMeters(300);


    public static List<Actor> createSurroundingActors(Model model) {

        Set leftLanes = model.getLanes().get(Model.Side.LEFT).entrySet();
        Set rightLanes = model.getLanes().get(Model.Side.RIGHT).entrySet();

        int lastLeftLane = leftLanes.size();
        int lastRightLane = rightLanes.size();


        Map<Model.Side, ArrayList<Surrounding>> surrounding = model.getSurrounding();
        List<Actor> result = new LinkedList<>();
        for (Map.Entry<Model.Side, ArrayList<Surrounding>> pair : surrounding.entrySet()) {
            Model.Side side = pair.getKey();
            int laneNumber;
            if (side == Model.Side.LEFT) {
                laneNumber = lastLeftLane;
            } else {
                laneNumber = lastRightLane;
            }

            for (Surrounding s : pair.getValue()) {
                RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(s, side, laneNumber, ActorType.SURROUNDING);
                Actor surroundingActor = new Actor(s, rigidBody);
                result.add(surroundingActor);
            }
        }
        return result;
    }

    public static List<Actor> createActors(Model model) {
        Map<Lane, ArrayList<Vehicle>> vehicleMap = model.getVehicles();
        Map<Lane, ArrayList<Living_entity>> livingEntityMap = model.getEntities();
        Map<Lane, ArrayList<Non_living_entity>> obstaclesMap = model.getObjects();

        List<Actor> result = new LinkedList<>();

        for (Map.Entry<Model.Side, TreeMap<Integer, Lane>> parentPair : model.getLanes().entrySet()) {
            Model.Side side = parentPair.getKey();
            for (Map.Entry childPair : (parentPair.getValue()).entrySet()) {
                Integer laneNumber = (Integer) childPair.getKey();
                Lane lane = (Lane) childPair.getValue();
                for (Vehicle vehicle : vehicleMap.get(lane)) {
                    if (vehicle != model.getVehicle()) {
                        RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(vehicle, side, laneNumber, ActorType.VEHICLE);
                        Actor actor = new Actor(vehicle, rigidBody);
                        actor.setValueInDollars(RigidBodyMapper.getValueInDollars(vehicle));
                        result.add(actor);
                    }
                }
                for (Living_entity entity : livingEntityMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(entity, side, laneNumber, ActorType.LIVING);
                    result.add(new Actor(entity, rigidBody));
                }

                for (Non_living_entity obstacle : obstaclesMap.get(lane)) {
                    RigidBody rigidBody = RigidBodyMapper.rigidBodyForEntity(obstacle, side, laneNumber, ActorType.OBSTACLE);
                    result.add(new Actor(obstacle, rigidBody));
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
        speedX = PhysicsUtils.KmphToMeters(getProperty(mainVehicle, "speedX"));
        speedY = PhysicsUtils.KmphToMeters(getProperty(mainVehicle, "speedY"));
        width = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "width"));
        length = PhysicsUtils.CmToMeters(getProperty(mainVehicle, "length"));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));
        rigidBody.setLength(length);
        rigidBody.setWidth(width);
        rigidBody.setInitialValues(rigidBody.getPosition(), rigidBody.getSpeed(), rigidBody.getAcceleration());
        return rigidBody;
    }

    private static RigidBody rigidBodyForEntity(Entity entity, Model.Side side, int laneNumber, ActorType actorType) {
        RigidBody rigidBody = new RigidBody();

        double positionX;
        double positionY = 0;
        double width, length, distanceToRoad;
        width = PhysicsUtils.CmToMeters(getProperty(entity, "width"));
        length = PhysicsUtils.CmToMeters(getProperty(entity, "length"));
        positionX = PhysicsUtils.CmToMeters(getProperty(entity, "distance"));

        if (actorType == ActorType.SURROUNDING){
            distanceToRoad = PhysicsUtils.CmToMeters(getProperty(entity, "distanceToRoad"));
            if (side == Model.Side.LEFT) {
                positionY = (laneNumber + 1) * LANE_WIDTH + distanceToRoad + width / 2;
            } else if (side == Model.Side.RIGHT) {
                positionY = (laneNumber + 1) * LANE_WIDTH * (-1) - distanceToRoad - width / 2;
            } else {
                positionY = 0;
            }
        }
        else if (actorType == ActorType.OBSTACLE) {
            if (side == Model.Side.LEFT) {
                positionY = laneNumber * LANE_WIDTH;
            } else if (side == Model.Side.RIGHT) {
                positionY = laneNumber * LANE_WIDTH * (-1);
            } else {
                positionY = 0;
            }
        }

        else if (actorType == ActorType.LIVING || actorType == ActorType.VEHICLE){
            double accelX, accelY, speedX, speedY;

            if (side == Model.Side.LEFT) {
                positionY = laneNumber * LANE_WIDTH;
            } else if (side == Model.Side.RIGHT) {
                positionY = laneNumber * LANE_WIDTH * (-1);
            } else {
                positionY = 0;
            }

            accelX = PhysicsUtils.CmToMeters(getProperty(entity, "accelX"));
            accelY = PhysicsUtils.CmToMeters(getProperty(entity, "accelY"));
            speedX = PhysicsUtils.KmphToMeters(getProperty(entity, "speedX"));
            speedY = PhysicsUtils.KmphToMeters(getProperty(entity, "speedY"));

            rigidBody.setSpeed(new Vector2(speedX, speedY));
            rigidBody.setAcceleration(new Vector2(accelX, accelY));
        }

        rigidBody.setPosition(new Vector2(positionX, positionY));
        rigidBody.setLength(length);
        rigidBody.setWidth(width);
        rigidBody.setInitialValues(rigidBody.getPosition(), rigidBody.getSpeed(), rigidBody.getAcceleration());

        return rigidBody;
    }

    public static double getValueInDollars(Entity entity){
        return getProperty(entity, "valueInDollars");
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
            case "distance":
                if (entity.hasDistance()) {
                    Iterator<? extends Float> iterator = entity.getDistance().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "distanceToRoad":
                if (entity.hasDistanceToRoad()) {
                    Iterator<? extends Float> iterator = entity.getDistanceToRoad().iterator();
                    while (iterator.hasNext()) {
                        returnValue = (double) iterator.next();
                    }
                    return returnValue;
                }
            case "valueInDollars":
                if (entity.hasValueInDollars()) {
                    Iterator<? extends Float> iterator = entity.getValueInDollars().iterator();
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
