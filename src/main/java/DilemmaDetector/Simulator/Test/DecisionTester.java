package DilemmaDetector.Simulator.Test;

import DilemmaDetector.Simulator.BasicActionsApplier;
import DilemmaDetector.Simulator.ChangeLaneActionApplier;
import DilemmaDetector.Simulator.RigidBody;
import DilemmaDetector.Simulator.Vector2;
import generator.BaseScenarioGenerator;
import project.Sunny;

public class DecisionTester {
    private RigidBody car = new RigidBody();
    private ChangeLaneActionApplier actionsApplier= new ChangeLaneActionApplier();



    private void everyTickAction(double deltaTime){
        car.update(deltaTime);
        System.out.println(
                "Pos: " + car.getPosition() +
                " | PrevPos: " + car.getPreviousPosition() +
                " | Speed: " + car.getSpeed() +
                " = " + car.getSpeed().getMagnitude() +
                " | Acc: " + car.getAcceleration());
    }

    private void testChangeLane(){
        double currentTime = 0;
        car.setSpeed(new Vector2(50, 0));
        car.setPosition(Vector2.zero());
        car.setAcceleration(Vector2.zero());

        double TIME_PART = 0.01;

        System.out.println("Changing lane from 0 to -10");

        while (currentTime < 10) {
            currentTime += TIME_PART;
            actionsApplier.CarChangeLanes(car, Sunny.class, 0, -10, 25);
//            BasicActionsApplier.CarTurning(car, Sunny.class, true);
//            BasicActionsApplier.CarBreaking(car, Sunny.class);
            everyTickAction(TIME_PART);
        }
    }

    static public void main(String[] args){
        DecisionTester tester = new DecisionTester();
        tester.testChangeLane();
    }
}
