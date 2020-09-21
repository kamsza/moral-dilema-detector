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

    }

    @Override
    public void addCyclist(String cyclistId, Current current) {

    }

    @Override
    public void addPedestrian(String pedestrianId, Current current) {

    }

    @Override
    public void addLane(String laneId, Current current) {

        project.Lane lane = owlFactory.getLane(laneId);
        this.scenario.addHas_lane(lane);
    }

    @Override
    public void addRoad(String roadId, Current current) {

    }

    @Override
    public void addRoadPoint(String roadPointId, Current current) {

    }

    @Override
    public void addJunction(String junctionId, Current current) {

    }
}
