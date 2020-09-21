package commonadapter.server.implementation;

import adapter.Entity;
import com.zeroc.Ice.Current;
import project.MyFactory;

public abstract class EntityImpl extends BaseItemImpl implements Entity {


    public EntityImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }

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
