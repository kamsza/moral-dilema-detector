package commonadapter.server.logic.models;

import adapter.ItemType;
import adapter.Road;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.OWLFactory;
import project.Road_attributes;
import project.Road_point;

public class RoadImpl extends BaseItemImpl implements Road {

    private project.Road road;

    public RoadImpl(String id, project.Road ontoRoad, OntologyService ontologyService) {
        super(id, ontologyService);
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
        
        project.Road_point roadPoint = (Road_point) ontologyService.loadItem(roadPointId).getWrappedIndividual();
        this.road.addStarts(roadPoint);
    }

    @Override
    public void setEnds(String roadPointId, Current current) {

        project.Road_point roadPoint = (Road_point) ontologyService.loadItem(roadPointId).getWrappedIndividual();
        this.road.addEnds(roadPoint);
    }

    @Override
    public void setRoadAttributes(String roadAttributesId, Current current) {

        project.Road_attributes attributes = (Road_attributes) ontologyService.loadItem(roadAttributesId, ItemType.ROADATTRIBUTES).getWrappedIndividual();
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

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return road;
    }
}
