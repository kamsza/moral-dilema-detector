package commonadapter.server.logic.models;

import adapter.Scenario;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.models.BaseItemImpl;
import project.MyFactory;

public class ScenarioImpl extends BaseItemImpl implements Scenario {

    private project.Scenario scenario;

    public ScenarioImpl(String id, project.Scenario ontoScenario, MyFactory owlFactory) {
        super(id, owlFactory);
        this.scenario = ontoScenario;
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
}