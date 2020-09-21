package commonadapter.test.server.implementation;

import adapter.RoadPoint;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class RoadPointImpl extends BaseItemImpl implements RoadPoint {


    public RoadPointImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
