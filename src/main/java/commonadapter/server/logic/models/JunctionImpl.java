package commonadapter.server.logic.models;

import adapter.Junction;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.OWLFactory;

public class JunctionImpl extends RoadPointImpl implements Junction {

    private project.Junction junction;

    public JunctionImpl(String id, project.Junction junction, OntologyService ontologyService) {
        super(id, ontologyService);
        super.roadPoint = this.junction = junction;
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return junction;
    }
}
