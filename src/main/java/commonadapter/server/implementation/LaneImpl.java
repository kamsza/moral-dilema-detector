package commonadapter.server.implementation;

import adapter.Lane;
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
        return 0;
    }

    @Override
    public void setWidth(int width, Current current) {

    }
}
