package commonadapter.test.server.implementation;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseFactoryImpl implements BaseFactory {

    private Map<String, BaseItemImpl> items;

    public BaseFactoryImpl() {
        items = new HashMap<>();
    }

    @Override
    public String create(ItemType type, Current current) {

        BaseItemImpl item = null;

        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl();
                break;
            case VEHICLE:
                item = new VehicleImpl();
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl();
                break;
            case CYCLIST:
                item = new CyclistImpl();
                break;
            case LANE:
                item = new LaneImpl();
                break;
            case ROAD:
                item = new RoadImpl();
                break;
            case ROADPOINT:
                item = new RoadPointImpl();
                break;
            case JUNCTION:
                item = new JunctionImpl();
                break;
        }

        String uuid = UUID.randomUUID().toString();
        String id = type.toString() + "/" + uuid;

        items.put(id, item);

        current.adapter.add(item, new Identity(uuid, type.toString()));

        return id;
    }
}
