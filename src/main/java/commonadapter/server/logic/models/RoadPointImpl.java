package commonadapter.server.logic.models;

import adapter.RoadPoint;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.models.BaseItemImpl;
import project.MyFactory;

public abstract class RoadPointImpl extends BaseItemImpl implements RoadPoint {

    protected project.Road_point roadPoint;

    public RoadPointImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
    @Override
    public void setLatitude(String lat, Current current) {

        this.roadPoint.addLatitude(lat);
    }

    @Override
    public void setLongitude(String lon, Current current) {

        this.roadPoint.addLongitude(lon);
    }
}
