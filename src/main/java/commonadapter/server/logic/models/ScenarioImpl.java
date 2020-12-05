package commonadapter.server.logic.models;

import adapter.ItemType;
import adapter.Scenario;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.Cyclist;
import project.OWLFactory;
import project.Pedestrian;
import project.Vehicle;

public class ScenarioImpl extends BaseItemImpl implements Scenario {

    private project.Scenario scenario;

    public ScenarioImpl(String id, project.Scenario ontoScenario, OntologyService ontologyService) {
        super(id, ontologyService);
        this.scenario = ontoScenario;
    }

    @Override
    public void addVehicle(String vehicleId, Current current) {

        project.Vehicle vehicle = (Vehicle) ontologyService.loadItem(vehicleId, ItemType.VEHICLE).getWrappedIndividual();
        this.scenario.addHas_vehicle(vehicle);
    }

    @Override
    public void addCyclist(String cyclistId, Current current) {

        project.Cyclist cyclist = (Cyclist) ontologyService.loadItem(cyclistId, ItemType.CYCLIST).getWrappedIndividual();
        this.scenario.addHas_cyclist(cyclist);
    }

    @Override
    public void addPedestrian(String pedestrianId, Current current) {

        project.Pedestrian pedestrian = (Pedestrian) ontologyService.loadItem(pedestrianId, ItemType.PEDESTRIAN).getWrappedIndividual();
        this.scenario.addHas_pedestrian(pedestrian);
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return scenario;
    }
}
