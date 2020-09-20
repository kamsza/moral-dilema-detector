package commonadapter.test.server.implementation;

import adapter.Entity;
import com.zeroc.Ice.Current;

public abstract class EntityImpl extends BaseItemImpl implements Entity {
    @Override
    public void setLane(String laneId, Current current) {

    }

    @Override
    public String getLaneId(Current current) {
        return null;
    }

    @Override
    public float getDistance(Current current) {
        return 0;
    }

    @Override
    public void setDistance(float distance, Current current) {

    }
}
