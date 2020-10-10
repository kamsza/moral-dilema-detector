package commonadapter.server.implementation;

import adapter.Lane;
import adapter.LaneBoundary;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class LaneImpl extends BaseItemImpl implements Lane {

    private project.Lane lane;

    public LaneImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        this.lane = owlFactory.createLane(id);
    }

    @Override
    public int getWidth(Current current) {

        return 0; // TODO
    }

    @Override
    public void setWidth(int width, Current current) {

        this.lane.addLane_width(width);
    }

    @Override
    public void setOpenToCurbSide(boolean isOpen, com.zeroc.Ice.Current current) {

    }

    @Override
    public boolean getOpenToCurbSide(com.zeroc.Ice.Current current) {
        return false;
    }

    @Override
    public void setOpenToMiddleSide(boolean isOpen, com.zeroc.Ice.Current current) {

    }

    @Override
    public boolean getOpenToMiddleSide(com.zeroc.Ice.Current current) {
        return false;
    }

    @Override
    public void setLaneBoundary(LaneBoundary laneBoundary, com.zeroc.Ice.Current current) {

    }

    @Override
    public LaneBoundary getLaneBoundary(com.zeroc.Ice.Current current) {
        return null;
    }

    @Override
    public String getId(com.zeroc.Ice.Current current) {
        return null;
    }
}
