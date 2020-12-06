package DilemmaDetector.Simulator;

import project.Weather;

public class ChangeLaneActionApplier extends BasicActionsApplier {

    private float offsetToMiddle = 0;

    public void CarChangeLanes(RigidBody car, Weather weather, int startLane, int endLane, float laneWidth){
        CarChangeCoordY(car, weather, startLane * laneWidth, endLane*laneWidth);
    }

    public void CarChangeCoordY(RigidBody car, Weather weather, float startLaneCoordY, float endLaneCoordY){
//        Lanes goes:
//        ----------------------------------------------------------------
//        -1
//        - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//         0       |auto|>
//        ----------------------------------------------------------------

        car.setAcceleration(Vector2.zero());
        float middle = (endLaneCoordY - startLaneCoordY) / 2 + startLaneCoordY;
        boolean changingLanesToTheRight = endLaneCoordY < startLaneCoordY;

        if(car.getPosition().y < endLaneCoordY == changingLanesToTheRight){ //straight it up after getting to the desired lane
            if(car.getSpeed().y < 0 == changingLanesToTheRight)
                CarTurning(car, weather, !changingLanesToTheRight);
            else
                car.setSpeed(new Vector2(car.getSpeed().getMagnitude(),0));
            return;
        }

        boolean angleToRoadLessThan45 = Math.abs(car.getSpeed().x) > Math.abs(car.getSpeed().y);
        boolean beforeMiddle = car.getPosition().y < middle != changingLanesToTheRight;
        //System.out.println("Before middle = " + beforeMiddle);
        if(angleToRoadLessThan45 && beforeMiddle){
            CarTurning(car, weather, changingLanesToTheRight);
          //  System.out.println("Turn 1");
        }else if(!angleToRoadLessThan45 && beforeMiddle){
            //System.out.println("Straight");
            if(offsetToMiddle == 0){
                offsetToMiddle = (float) Math.abs(middle - car.getPreviousPosition().y);
                //CarTurning(car, weatherType, !changingLanesToTheRight);
            }
            //straight
        }else if(offsetToMiddle == 0){
            CarTurning(car, weather, !changingLanesToTheRight);
            //System.out.println("Turn 2");
        }else if(!angleToRoadLessThan45 && offsetToMiddle < Math.abs(car.getPosition().y +
                2 * (car.getPosition().y - car.getPreviousPosition().y) - middle)){ // && !beforeMiddle){
            //System.out.println("EndStraight offset: " + offsetToMiddle + " pos : " + car.getPosition().y + " current offset : " + (car.getPosition().y - middle));
            offsetToMiddle = 0;
            CarTurning(car, weather, !changingLanesToTheRight);
        }
    }

}
