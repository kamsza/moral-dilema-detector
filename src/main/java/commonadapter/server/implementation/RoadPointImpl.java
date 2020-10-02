package commonadapter.server.implementation;

import adapter.RoadPoint;
import project.MyFactory;

public  class RoadPointImpl extends BaseItemImpl implements RoadPoint {

    protected project.Road_point roadPoint;

    public RoadPointImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
