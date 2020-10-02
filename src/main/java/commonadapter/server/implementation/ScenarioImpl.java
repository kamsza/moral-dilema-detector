package commonadapter.server.implementation;

import adapter.Scenario;
import com.zeroc.Ice.Current;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;

public class ScenarioImpl extends BaseItemImpl implements Scenario {

    private project.Scenario scenario;

    public ScenarioImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        this.scenario = owlFactory.createScenario(id);
    }

    @Override
    public void addVehicle(String vehicleId, Current current) {

        project.Vehicle vehicle = owlFactory.getVehicle(vehicleId);
        this.scenario.addHas_vehicle(vehicle);
    }

    @Override
    public void addCyclist(String cyclistId, Current current) {

        project.Cyclist cyclist = owlFactory.getCyclist(cyclistId);
        this.scenario.addHas_cyclist(cyclist);
    }

    @Override
    public void addPedestrian(String pedestrianId, Current current) {

        project.Pedestrian pedestrian = owlFactory.getPedestrian(pedestrianId);
        this.scenario.addHas_pedestrian(pedestrian);
    }

    @Override
    public void addLane(String laneId, Current current) {

        project.Lane lane = owlFactory.getLane(laneId);
        this.scenario.addHas_lane(lane);
    }

    @Override
    public void addRoad(String roadId, Current current) {

        project.Road road = owlFactory.getRoad(roadId);
        this.scenario.addHas_road(road);
    }

    @Override
    public void addRoadPoint(String roadPointId, Current current) {

        project.Road_point roadPoint = owlFactory.getRoad_point(roadPointId);
        this.scenario.addHas_road_point(roadPoint);
    }

    @Override
    public void addJunction(String junctionId, Current current) {

        project.Junction junction = owlFactory.getJunction(junctionId);
        this.scenario.addHas_road_point(junction);
    }
}
