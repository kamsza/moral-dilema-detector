package DilemmaDetector.Simulator;

import project.*;

public class BasicActionsApplier {
    final static private double GRAVITY = 9.81;

    static public void CarBreaking(RigidBody car, Class weatherType){
        double frictionCoefficient = getTireRoadFriction(weatherType);
        Vector2 breaking = car.getSpeed().getNormalized().mul(-1);
        breaking.mul(GRAVITY * frictionCoefficient);
        car.setAcceleration(breaking);
    }

    // This function has to be run every frame of simulation in order to update centripetal acceleration direction
    // It might cause vehicle to accelerate ahead if updated too seldom
    // radius = v^2/(friction*g)
    // a = - v^2/radius * r_norm
    // where r_norm is unit vector with the direction of the radius and return from the axis of rotation
    static public void CarTurning(RigidBody car, Class weatherType, boolean right){
        double frictionCoefficient = getTireRoadFriction(weatherType);
        double speed = car.getSpeed().getMagnitude();

        double radius = speed*speed/(frictionCoefficient*GRAVITY);

        Vector2 r = new Vector2(0,0);
        if(right){
            r.x = car.getSpeed().y;
            r.y = -car.getSpeed().x;
        }
        else{
            r.x = -car.getSpeed().y;
            r.y = car.getSpeed().x;
        }
        r = r.getNormalized();
        Vector2 turning = r.mul(speed*speed/radius);

        car.setAcceleration(turning);
    }

    //Data from http://www.modlab.lv/klimats/produkti/road/Road_notebook.html
    static private double getTireRoadFriction(Class weather){
        double result;
        if(weather == Sunny.class){
            result = 0.9;
        }else if (weather == Fog.class){
            result = 0.5;
        }else if (weather == Shower.class){
            result = 0.3;
        }else if (weather == Heavy_rain.class){
            result = 0.3;
        }else if (weather == Snow.class){
            result = 0.2;
        }else if (weather == Glaze.class){
            result = 0.13;
        }else{
            result = 0.5;
        }
        return result;
    }
}
