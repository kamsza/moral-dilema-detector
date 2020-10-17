package DilemmaDetector.Simulator;

import generator.Model;

import java.util.LinkedList;
import java.util.List;

public class CollisionDetector {

    private Model scenarioModel;
    private List<Actor> actors;
    private Actor mainVehicle;

    public CollisionDetector(Model model, Actor mainVehicle,
                             List<Actor> actors) {
        this.scenarioModel = model;
        this.mainVehicle = mainVehicle;
        this.actors = actors;
    }

    public List<Actor> detectCollisionInMoment() {
        List<Actor> collidedActors = new LinkedList<>();
        if (detectOutOfRoad(mainVehicle)) {
            collidedActors.add(mainVehicle);
        }
        for (Actor entry : actors) {
            if (detectCollisionWithRigidBodyInMoment(entry.getRigidBody()))
                collidedActors.add(entry);
        }

        return collidedActors;
    }

    public boolean detectOutOfRoad(Actor mainVehicle){
        boolean outOfRoad = false;
        int lastLaneLeft =  scenarioModel.getLanes().get(Model.Side.LEFT).entrySet().size() + 1;
        int lastLaneRight = scenarioModel.getLanes().get(Model.Side.RIGHT).entrySet().size() + 1;

        double leftBorderY = lastLaneLeft * RigidBodyMapper.LANE_WIDTH * (-1);
        double rightBorderY = lastLaneRight * RigidBodyMapper.LANE_WIDTH;

        double vehicleY = mainVehicle.getRigidBody().getPosition().y;

        if (vehicleY < leftBorderY ||  vehicleY > rightBorderY){
            System.out.println("Main vehicle out of road ");
            outOfRoad = true;
        }

        return outOfRoad;
    }


    public boolean detectCollisionWithRigidBodyInMoment(RigidBody rigidBody) {
        boolean isCollision = false;
        double vehicleWidth = rigidBody.getWidth();
        double vehicleLength = rigidBody.getLength();
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle.getRigidBody(), rigidBody);
        if(distanceBetweenRigidBodies.y < (vehicleWidth + mainVehicle.getRigidBody().getWidth()) /2
                && distanceBetweenRigidBodies.x < (vehicleLength + mainVehicle.getRigidBody().getLength()) /2) {
        //small change x and y




            System.out.println("DETECTED COLL" );
            System.out.println("MAIN VEHICLE POS: " + mainVehicle.getRigidBody().getPosition());
            System.out.println("MAIN VEHICLE SHAPE: "  + mainVehicle.getRigidBody().getWidth() +
                    " " + mainVehicle.getRigidBody().getLength());
            System.out.println("OTHER VEHICLE POS: " + rigidBody.getPosition());
            System.out.println("OTHER VEHICLE SHAPE: "  + vehicleWidth +
                    " " + vehicleLength);
            System.out.println("DISTANCE  " + distanceBetweenRigidBodies + " " + distanceBetweenRigidBodies.x +  " " + distanceBetweenRigidBodies.y);
            System.out.println("COUNTED : " + (vehicleWidth + mainVehicle.getRigidBody().getWidth()) /2 + "  " +
            (vehicleLength + mainVehicle.getRigidBody().getLength()) /2);



            System.out.println(mainVehicle.getRigidBody().getPosition());

            isCollision = true;
        }
        return isCollision;
    }

    /*
    Returns distance between centres of rigidBodies as Vector2
     */
    private Vector2 getDistanceBetweenRigidBodies(RigidBody mainVehicle, RigidBody rigidBody) {
        double xDistance = Math.abs(rigidBody.getPosition().x - mainVehicle.getPosition().x);
        double yDistance = Math.abs(rigidBody.getPosition().y - mainVehicle.getPosition().y);
        return new Vector2(xDistance, yDistance);
    }


}




