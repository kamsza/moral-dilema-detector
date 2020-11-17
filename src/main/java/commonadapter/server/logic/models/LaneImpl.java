package commonadapter.server.logic.models;

import adapter.Lane;
import adapter.LaneBoundary;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class LaneImpl extends BaseItemImpl implements Lane {

    private project.Lane lane;

    public LaneImpl(String id, project.Lane ontoLane, MyFactory owlFactory) {
        super(id, owlFactory);
        this.lane = ontoLane;
    }

    @Override
    public void setWidth(int width, Current current) {

        this.lane.addLane_width(width);
    }

    @Override
    public void setLeftSideBoundary(String boundaryId, Current current) {

        project.Lane_boundary boundary = owlFactory.getLane_boundary(boundaryId);
        this.lane.addHas_left_side_boundary(boundary);
    }

    @Override
    public void setRightSideBoundary(String boundaryId, Current current) {

        project.Lane_boundary boundary = owlFactory.getLane_boundary(boundaryId);
        this.lane.addHas_right_side_boundary(boundary);
    }

    @Override
    public void setRoad(String roadId, Current current) {

        project.Road road = owlFactory.getRoad(roadId);
        this.lane.addIs_on_road(road);
    }

    @Override
    public String getId(com.zeroc.Ice.Current current) {
        return null;
    }
}
