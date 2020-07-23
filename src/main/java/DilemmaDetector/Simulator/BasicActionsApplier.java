package DilemmaDetector.Simulator;

import project.*;

public class BasicActionsApplier {
    final static protected double GRAVITY = 9.81;
    static public void CarBreaking(RigidBody car, Class weatherType){
        double frictionCoefficient = getTireRoadFriction(weatherType);
        Vector2 breaking = car.getSpeed().getNormalized().mul(-1);
        breaking.mul(GRAVITY * frictionCoefficient);
        car.setAcceleration(breaking);
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
