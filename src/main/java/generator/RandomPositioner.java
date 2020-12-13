package generator;

import project.Lane;

import java.util.*;

public class RandomPositioner {
    private static class EntityWithSize {
        float distance, length;

        EntityWithSize(float distance, float length) {
            this.distance = distance;
            this.length = length;
        }

        float getStartDistance() {
            return distance - (length / 2);
        }

        float getEndDistance() {
            return distance + (length / 2);
        }

        @Override
        public String toString() {
            return "(d = " + distance + ", l = " + length + ")";
        }
    }

    private int maxDist;
    private int lanesCount;

    private Random rand;

    private Map<Integer, List<EntityWithSize>> entities = new HashMap<>();
    private Map<Integer, Float> maxSpaceOnLane = new HashMap<>();


    public RandomPositioner(int lanesCount) {
        this.rand = new Random();
        this.lanesCount = lanesCount;
        this.maxDist = 3200;
        SizeManager sizeManager = new SizeManager();

        // initialize
        for (int i = 0; i < lanesCount; i++) {
            List<EntityWithSize> entitiesList = new ArrayList<>();
            // add bounds to avoid placing entities not in range
            entitiesList.add(new EntityWithSize(-maxDist, 0));
            entitiesList.add(new EntityWithSize(maxDist, 0));
            entities.put(i, entitiesList);
            maxSpaceOnLane.put(i, maxDist - sizeManager.getLength("car")/2);
        }
    }

    public void addMainVehicle(int laneNo, float length) {
        entities.get(laneNo).add(1, new EntityWithSize(0F, length));
    }

    public int getRandomLaneNumber(float entitySize) {
        int laneNo = rand.nextInt(lanesCount);
        // if lane is full, check whether others have free space
        if (maxSpaceOnLane.get(laneNo) < entitySize) {
            laneNo = getNextFreeLaneNo(entitySize);
        }
        return laneNo;
    }

    private int getNextFreeLaneNo(float entitySize) {
        for (int i = 0; i < lanesCount; i++) {
            if (maxSpaceOnLane.get(i) > entitySize) {
                return i;
            }
        }
        // no place for new entity
        System.out.println("NO MORE PLACES ON ROAD");
        return -1;
    }

    public Lane getLane(Model model, int laneNo) {
        Lane lane;
        int mainVehicleLaneId = model.getMainRoad().getRoadType().getMain_vehicle_lane_id().iterator().next();

        if (laneNo == mainVehicleLaneId)
            lane = model.getMainRoad().getLanes().get(Model.Side.CENTER).get(0);
        else if (laneNo < mainVehicleLaneId)
            lane = model.getMainRoad().getLanes().get(Model.Side.LEFT).get(mainVehicleLaneId - laneNo);
        else
            lane = model.getMainRoad().getLanes().get(Model.Side.RIGHT).get(laneNo - mainVehicleLaneId);

        return lane;
    }

    public float getRandomDistance() {
        return (float) rand.nextInt(maxDist);
    }

    public float getRandomDistance(int laneNo, float entitySize) {
        return this.getRandomDistance(laneNo, entitySize, true);
    }

    public float getRandomDistance(int laneNo, float entitySize, boolean canBeNegative) {
        int distance = rand.nextInt(maxDist);

        if (canBeNegative && rand.nextBoolean()) {
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
            distance = (int) getNextDistance(entitiesOnLane, entitySize, canBeNegative);
        }
        updateMaxSpaceOnLane(laneNo);
        return distance;
    }

    private float getNextDistance(List<EntityWithSize> entitiesOnLane, float entitySize, boolean canBeNegative) {
        if(entitiesOnLane.size() == 1) {
            return entitiesOnLane.get(0).getEndDistance() + entitySize / 2 + rand.nextInt(3000);
        }

        for (int i = 1; i < entitiesOnLane.size(); i++) {
            float start = entitiesOnLane.get(i - 1).getEndDistance();
            float end = entitiesOnLane.get(i).getStartDistance();
            float spaceBetween = end - start;
            if (spaceBetween > entitySize) {
                float bound = spaceBetween - entitySize; // to avoid overlapping
                float distance = rand.nextInt((int) bound) + start + entitySize / 2;
                if(!canBeNegative && distance < 0)
                    continue;
                entitiesOnLane.add(i, new EntityWithSize(distance, entitySize));
                return distance;
            }
        }

        // no place on lane
        System.out.println("NO PLACE ON LANE");
        return 0;
    }

    private void updateMaxSpaceOnLane(int laneNo) {
        float maxGap = 0;
        List<EntityWithSize> entitiesOnLane = entities.get(laneNo);

        for (int i = 1; i < entitiesOnLane.size(); i++) {
            float spaceBetween = entitiesOnLane.get(i).getStartDistance() - entitiesOnLane.get(i - 1).getEndDistance();
            if (spaceBetween > maxGap)
                maxGap = spaceBetween;
        }
        maxSpaceOnLane.put(laneNo, maxGap);
    }

}