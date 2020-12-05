package commonadapter.server.logic.models;

import adapter.ItemType;
import adapter.Lane;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.Lane_boundary;
import project.OWLFactory;
import project.Road;

import static commonadapter.OntologyUtils.getPrefixedId;

public class LaneImpl extends BaseItemImpl implements Lane {

    private project.Lane lane;

    public LaneImpl(String id, project.Lane ontoLane, OntologyService ontologyService) {
        super(id, ontologyService);
        this.lane = ontoLane;
    }

    @Override
    public void setWidth(int width, Current current) {

        this.lane.addLane_width(width);
    }

    @Override
    public void setLeftSideBoundary(String boundaryId, Current current) {

        project.Lane_boundary boundary = (Lane_boundary) ontologyService.loadItem(boundaryId, ItemType.LANEBOUNDARY).getWrappedIndividual();
        this.lane.addHas_left_side_boundary(boundary);
    }

    @Override
    public void setRightSideBoundary(String boundaryId, Current current) {

        project.Lane_boundary boundary = (Lane_boundary) ontologyService.loadItem(boundaryId, ItemType.LANEBOUNDARY).getWrappedIndividual();
        this.lane.addHas_right_side_boundary(boundary);
    }

    @Override
    public void setRoad(String roadId, Current current) {

        project.Road road = (Road) ontologyService.loadItem(roadId, ItemType.ROAD).getWrappedIndividual();
        this.lane.addIs_on_road(road);
    }

    @Override
    public void setLaneNumber(int laneNumber, Current current) {

        this.lane.addLane_number(laneNumber);
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return lane;
    }
}
