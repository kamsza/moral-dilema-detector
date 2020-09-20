package commonadapter.test.server.implementation;

import adapter.Scenario;
import com.zeroc.Ice.Current;

public class ScenarioImpl extends BaseItemImpl implements Scenario {

    @Override
    public void addVehicle(String vehicleId, Current current) {
        System.out.println("addveh. remotely invoked; id=" + vehicleId);
    }

    @Override
    public void addCyclist(String cyclistId, Current current) {

    }

    @Override
    public void addPedestrian(String pedestrianId, Current current) {

    }

    @Override
    public void addLane(String laneId, Current current) {

    }

    @Override
    public void addRoad(String roadId, Current current) {

    }

    @Override
    public void addRoadPoint(String roadPointId, Current current) {

    }

    @Override
    public void addJunction(String junctionId, Current current) {

    }

    @Override
    public void persist(Current current) {

    }
}
