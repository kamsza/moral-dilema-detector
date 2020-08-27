package generator;

import project.Lane;

import java.util.*;

public class RandomPositioner {
    private static class EntityWithSize {
        float distance, size;

        EntityWithSize(float distance, float size) {
            this.distance = distance;
            this.size = size;
        }

        float getStartDistance() {
            return distance - (size / 2);
        }

        float getEndDistance() {
            return distance + (size / 2);
        }

        @Override
        public String toString() {
            return "(d = " + distance + ", s = " + size + ")";
        }
    }

    private int maxDist;
    private int lanesCount;
    private int vehicleCount;
    private Random rand;
    Map<Integer, List<EntityWithSize>> entities = new HashMap<>();
    Map<Integer, Float> maxSpaceOnLane = new HashMap<>();

    public RandomPositioner(int lanesCount) {
        this.rand = new Random();
        this.lanesCount = lanesCount;
        this.vehicleCount = rand.nextInt(2 * lanesCount);
        this.maxDist = 2000;


        // initialize
        for (int i = 0; i < lanesCount; i++) {
            List<EntityWithSize> entitiesList = new ArrayList<>();
            // add bounds to avoid placing entities not in range
            entitiesList.add(new EntityWithSize(-maxDist, 0));
            // reserve space around main vehicle
            entitiesList.add(new EntityWithSize(0, 600F));
            entitiesList.add(new EntityWithSize(maxDist, 0));
            entities.put(i, entitiesList);
            maxSpaceOnLane.put(i, maxDist - 300F);
        }
    }

    public int getVehiclesCount() {
        return vehicleCount;
    }

    public int getRandomLaneNumber(float entitySize) {
        int laneNo = rand.nextInt(lanesCount);
        // if lane is full, check whether others have free space
        if (maxSpaceOnLane.get(laneNo) < entitySize) {
            System.out.println("NO SPACE on lane");
            laneNo = getNextFreeLaneNo(entitySize);
        }
        return laneNo;
    }

    private int getNextFreeLaneNo(float entitySize) {
        for (int i = 0; i < lanesCount; i++) {
            if (maxSpaceOnLane.get(i) > entitySize)
                return i;
        }
        // no place for new entity
        System.out.println("NO MORE PLACES ON ROAD");
        return -1;
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

    public float getRandomDistance(int laneNo, float entitySize) {
        // add margin to entity
//        entitySize = entitySize * 1.1F;
        int distance = rand.nextInt(maxDist);

        if (rand.nextBoolean()) {
            distance *= (-1);
        }

        List<EntityWithSize> entitiesOnLane = entities.get(laneNo);

        int i = 0;
        // no need for checking 'out of bound', cause distance is in valid range
        while (distance > entitiesOnLane.get(i).distance) {
            i++;
        }

        if (distance - (entitySize / 2) > entitiesOnLane.get(i - 1).getEndDistance()
                && distance + (entitySize / 2) < entitiesOnLane.get(i).getStartDistance()) {
            entitiesOnLane.add(i, new EntityWithSize(distance, entitySize));

        } else {
            distance = (int) getNextDistance(entitiesOnLane, entitySize);
        }
        updateMaxSpaceOnLane(laneNo);
        return distance;

    }

    private float getNextDistance(List<EntityWithSize> entitiesOnLane, float entitySize) {
        for (int i = 1; i < entitiesOnLane.size(); i++) {
            float start = entitiesOnLane.get(i - 1).getEndDistance() + entitySize / 2;
            float end = entitiesOnLane.get(i).getStartDistance() - entitySize / 2;
            float spaceBetween = end - start;
            if (spaceBetween > entitySize) {
                float distance = rand.nextInt((int) spaceBetween) + start;
                entitiesOnLane.add(i, new EntityWithSize(distance, entitySize));
                return distance;
            }
        }

        // no place on lane
        return 0;
    }

    private void updateMaxSpaceOnLane(int laneNo) {
        float maxGap = maxSpaceOnLane.get(laneNo);
        List<EntityWithSize> entitiesOnLane = entities.get(laneNo);

        for (int i = 1; i < entitiesOnLane.size(); i++) {
            float spaceBetween = entitiesOnLane.get(i).getStartDistance() - entitiesOnLane.get(i - 1).getEndDistance();
            if (spaceBetween > maxGap)
                maxGap = spaceBetween;
        }
    }

}