package commonadapter.server.logic.models;

import adapter.Road;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.models.BaseItemImpl;
import project.MyFactory;

public class RoadImpl extends BaseItemImpl implements Road {

    private project.Road road;

    public RoadImpl(String id, project.Road ontoRoad, MyFactory owlFactory) {
        super(id, owlFactory);
        this.road = ontoRoad;
    }

    @Override
    public void setStartAngle(float angle, Current current) {

        this.road.addStart_angle(angle);
    }

    @Override
    public void setEndAngle(float angle, Current current) {

        this.road.addEnd_angle(angle);
    }

    @Override
    public void setStarts(String roadPointId, Current current) {

        project.Road_point roadPoint = owlFactory.getRoad_point(roadPointId);
        this.road.addStarts(roadPoint);
    }

    @Override
    public void setEnds(String roadPointId, Current current) {

        project.Road_point roadPoint = owlFactory.getRoad_point(roadPointId);
        this.road.addEnds(roadPoint);
    }

    @Override
    public void setRoadAttributes(String roadAttributesId, Current current) {

        project.Road_attributes attributes = owlFactory.getRoad_attributes(roadAttributesId);
        this.road.addHas_road_attributes(attributes);
    }

    @Override
    public void setAverageSpeed(int averageSpeed, Current current) {

        this.road.addAverage_speed(averageSpeed);
    }

    @Override
    public void setSpeedLimit(int speedLimit, Current current) {
        this.road.addSpeed_limit(speedLimit);
    }


}
