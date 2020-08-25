package DilemmaDetector.Simulator;

import java.util.LinkedList;
import java.util.List;

public class CollisionDetector {

    private List<Actor> actors;
    private Actor mainVehicle;

    public CollisionDetector(Actor mainVehicle,
                             List<Actor> actors) {
        this.mainVehicle = mainVehicle;
        this.actors = actors;
    }

    public List<Actor> detectCollisionInMoment() {
        List<Actor> collidedActors = new LinkedList<>();
        for (Actor entry : actors) {
            if (detectCollisionWithRigidbodyInMoment(entry.getRigidBody()))
                collidedActors.add(entry);
        }

        return collidedActors;
    }


    public boolean detectCollisionWithRigidbodyInMoment(RigidBody rigidBody) {
        boolean isCollision = false;
        double vehicleWidth = rigidBody.getWidth();
        double vehicleLength = rigidBody.getLength();
        Vector2 distanceBetweenRigidBodies = getDistanceBetweenRigidBodies(mainVehicle.getRigidBody(), rigidBody);
        if(distanceBetweenRigidBodies.x < (vehicleWidth + mainVehicle.getRigidBody().getWidth()) /2
                && distanceBetweenRigidBodies.y < (vehicleLength + mainVehicle.getRigidBody().getLength()) /2) {
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




