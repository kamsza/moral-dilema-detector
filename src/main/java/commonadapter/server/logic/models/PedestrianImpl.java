package commonadapter.server.logic.models;

import adapter.Pedestrian;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    private project.Pedestrian pedestrian;

    public PedestrianImpl(String id, project.Pedestrian ontoPedestrian, OntologyService ontologyService) {
        super(id, ontologyService);
        super.entity = this.pedestrian = ontoPedestrian;
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return pedestrian;
    }

}
