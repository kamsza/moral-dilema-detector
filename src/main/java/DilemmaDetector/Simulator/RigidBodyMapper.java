package DilemmaDetector.Simulator;

import generator.Model;
import project.Animal;
import project.Lane;
import project.Pedestrian;
import project.Vehicle;

import java.util.Collection;

public class RigidBodyMapper {

    //TODO get lane width from ontology? is it in centimeters
    public static final int LANE_WIDTH = 300;

    public static RigidBody rigidBodyForMainVehicle(Vehicle mainVehicle){
        RigidBody rigidBody = new RigidBody();
        rigidBody.setPosition(new Vector2(0,0));

        double accelX, accelY, speedX, speedY;

        if (!mainVehicle.hasAccelX())
            accelX = 0;
        else accelX = mainVehicle.getAccelX();

        if (!mainVehicle.hasAccelY())
            accelY = 0;
        else accelY = mainVehicle.getAccelY();

        if (!mainVehicle.hasSpeedX())
            speedX = 0;
        else speedX = mainVehicle.getSpeedX();

        if (!mainVehicle.hasSpeedY())
            speedY = 0;
        else speedY = mainVehicle.getSpeedY();

        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));

        return rigidBody;
    }



    public static RigidBody rigidBodyForVehicle(Vehicle vehicle, Model.Side side, Lane lane, Integer integer){
        RigidBody rigidBody = new RigidBody();

        double positionX;
        double positionY;
        double accelX, accelY, speedX, speedY;

        Object[] pos = vehicle.getDistance().toArray();
        positionY = (double) pos[0];

        //Vehicles on left side have negative position X
        if (side == Model.Side.LEFT){
            positionX = integer *LANE_WIDTH *(-1);
        }
        else if (side == Model.Side.RIGHT){
            positionX = integer * LANE_WIDTH;
        }
        else{
            positionX = 0;
        }

        if (!vehicle.hasAccelX())
            accelX = 0;
        else accelX = vehicle.getAccelX();

        if (!vehicle.hasAccelY())
            accelY = 0;
        else accelY = vehicle.getAccelY();

        if (!vehicle.hasSpeedX())
            speedX = 0;
        else speedX = vehicle.getSpeedX();

        if (!vehicle.hasSpeedY())
            speedY = 0;
        else speedY = vehicle.getSpeedY();

        rigidBody.setPosition(new Vector2(positionX, positionY));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));

        return rigidBody;
    }


    public static RigidBody rigidBodyForPedestrian(Pedestrian pedestrian, Model.Side side, Lane lane, Integer integer){
        RigidBody rigidBody = new RigidBody();

        double positionX;
        double positionY;
        double accelX, accelY, speedX, speedY;

        Object[] pos = pedestrian.getDistance().toArray();
        positionY = (double) pos[0];

        //Entities on left side have negative position X
        if (side == Model.Side.LEFT){
            positionX = integer *LANE_WIDTH *(-1);
        }
        else if (side == Model.Side.RIGHT){
            positionX = integer * LANE_WIDTH;
        }
        else{
            positionX = 0;
        }

        if (!pedestrian.hasAccelX())
            accelX = 0;
        else accelX = pedestrian.getAccelX();

        if (!pedestrian.hasAccelY())
            accelY = 0;
        else accelY = pedestrian.getAccelY();

        if (!pedestrian.hasSpeedX())
            speedX = 0;
        else speedX = pedestrian.getSpeedX();

        if (!pedestrian.hasSpeedY())
            speedY = 0;
        else speedY = pedestrian.getSpeedY();

        rigidBody.setPosition(new Vector2(positionX, positionY));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));

        return rigidBody;
    }

    public static RigidBody rigidBodyForAnimal(Animal animal, Model.Side side, Lane lane, Integer integer){
        RigidBody rigidBody = new RigidBody();

        double positionX;
        double positionY;
        double accelX, accelY, speedX, speedY;

        Object[] pos = animal.getDistance().toArray();
        positionY = (double) pos[0];

        if (side == Model.Side.LEFT){
            positionX = integer *LANE_WIDTH *(-1);
        }
        else if (side == Model.Side.RIGHT){
            positionX = integer *LANE_WIDTH;
        }
        else{
            positionX = 0;
        }

        if (!animal.hasAccelX())
            accelX = 0;
        else accelX = animal.getAccelX();

        if (!animal.hasAccelY())
            accelY = 0;
        else accelY = animal.getAccelY();

        if (!animal.hasSpeedX())
            speedX = 0;
        else speedX = animal.getSpeedX();

        if (!animal.hasSpeedY())
            speedY = 0;
        else speedY = animal.getSpeedY();

        rigidBody.setPosition(new Vector2(positionX, positionY));
        rigidBody.setSpeed(new Vector2(speedX, speedY));
        rigidBody.setAcceleration(new Vector2(accelX, accelY));

        return rigidBody;
    }

}
