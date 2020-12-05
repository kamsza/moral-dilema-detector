package commonadapter.server.logic.models;

import adapter.Cyclist;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.OWLFactory;

public class CyclistImpl extends EntityImpl implements Cyclist {

    private project.Cyclist cyclist;

    public CyclistImpl(String id, project.Cyclist ontoCyclist, OntologyService ontologyService) {
        super(id, ontologyService);
        super.entity = this.cyclist = ontoCyclist;
    }
    @Override
    public WrappedIndividual getWrappedIndividual() {
        return cyclist;
    }
}
