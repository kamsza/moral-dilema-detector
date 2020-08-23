package generator;

import project.Entity;
import project.Lane;
import project.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPositioner {
    private int maxDist;
    private Random rand;

    public RandomPositioner() {
        this.maxDist = 2000;
        this.rand = new Random();
    }

    public RandomPositioner(int maxDist) {
        this.maxDist = maxDist;
        this.rand = new Random();
    }

    public int getRandomLaneNumber(int lanesNumber) {
        return rand.nextInt(lanesNumber);
    }

    public Lane getLane(Model model, int laneNo) {
        Lane lane;
        int mainVehicleLaneId = model.getLanes().get(Model.Side.LEFT).size();

        if (laneNo == mainVehicleLaneId)
            lane = model.getLanes().get(Model.Side.CENTER).get(0);
        else if (laneNo < mainVehicleLaneId)
            lane = model.getLanes().get(Model.Side.LEFT).get(mainVehicleLaneId - laneNo);
        else
            lane = model.getLanes().get(Model.Side.RIGHT).get(laneNo - mainVehicleLaneId);

        return lane;
    }

    public float getRandomDistance() {
        return (float) rand.nextInt(maxDist);
    }

    public float getRandomDistance(Model model, Lane lane, float interval) {
        int distance = rand.nextInt(maxDist);
        int startDistance = distance;

        if (rand.nextBoolean()) {
            distance *= (-1);
        }

        ArrayList<Vehicle> vehiclesOnLane = model.getVehicles().get(lane);
        boolean reachedEnd = false;

        while (hasCollision(vehiclesOnLane, distance, interval)) {
            distance += rand.nextInt(50);
            if (reachedEnd && distance > startDistance) {
                // this lane is full, no place for new vehicle
                return 0;
            }

            if (distance > maxDist) {
                reachedEnd = true;
                distance = distance - 2 * maxDist;
            }
        }
        return (float) distance;
    }

    private <E extends Entity> boolean hasCollision(List<E> objectsOnLane, int distance, float objectSize) {
        for (Entity e : objectsOnLane) {
            float distanceInBetween = e.getDistance().iterator().next() - distance;
            float minDistance = (e.getLength().iterator().next() + objectSize) / 2;
            if (Math.abs(distanceInBetween) < minDistance) {
                return true;
            }
        }
        return false;
    }

}