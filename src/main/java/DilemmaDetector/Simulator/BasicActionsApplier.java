package DilemmaDetector.Simulator;

import project.*;

public class BasicActionsApplier {
    final static private double GRAVITY = 9.81;
    private static Vector2 last_braking = Vector2.zero();

    static public void CarBraking(RigidBody car, Weather weather){
        double frictionCoefficient = getTireRoadFriction(weather);
        Vector2 braking = car.getSpeed().getNormalized().mul(-1);
        braking.mul(GRAVITY * frictionCoefficient);
        if(last_braking.equals(new Vector2(braking).mul(-1))) {
            car.setSpeed(Vector2.zero());
            car.setAcceleration(Vector2.zero());
        }
        else{
            last_braking = braking;
            car.setAcceleration(braking);
        }
    }

    // This function has to be run every frame of simulation in order to update centripetal acceleration direction
    // It might cause vehicle to accelerate ahead if updated too seldom
    // radius = v^2/(friction*g)
    // a = - v^2/radius * r_norm
    // where r_norm is unit vector with the direction of the radius and return from the axis of rotation
    static public void CarTurning(RigidBody car, Weather weather, boolean right){
        double frictionCoefficient = getTireRoadFriction(weather);
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
    static private double getTireRoadFriction(Weather weather){
        double result;
        if(weather instanceof Sunny){
            result = 0.9;
        }else if (weather instanceof Fog){
            result = 0.5;
        }else if (weather instanceof Shower){
            result = 0.3;
        }else if (weather instanceof Heavy_rain){
            result = 0.3;
        }else if (weather instanceof Snow){
            result = 0.2;
        }else if (weather instanceof Glaze){
            result = 0.13;
        }else{
            result = 0.5;
        }
        return result;
    }

    public static double getGRAVITY() {
        return GRAVITY;
    }
}
