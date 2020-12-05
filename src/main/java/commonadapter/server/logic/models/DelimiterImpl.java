package commonadapter.server.logic.models;

import adapter.Delimiter;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.OWLFactory;

public class DelimiterImpl extends RoadPointImpl implements Delimiter {

    private project.Delimiter delimiter;

    public DelimiterImpl(String id, project.Delimiter ontoDelimiter, OntologyService ontologyService) {
        super(id, ontologyService);
        super.roadPoint = this.delimiter = ontoDelimiter;
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return delimiter;
    }
}