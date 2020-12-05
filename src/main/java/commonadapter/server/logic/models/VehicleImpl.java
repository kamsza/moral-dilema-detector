package commonadapter.server.logic.models;

import adapter.Vehicle;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.OWLFactory;

public class VehicleImpl extends EntityImpl implements Vehicle {

    private project.Vehicle vehicle;

    public VehicleImpl(String id, project.Vehicle vehicle, OntologyService ontologyService) {
        super(id, ontologyService);
        super.entity = this.vehicle = vehicle;
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return vehicle;
    }
}
