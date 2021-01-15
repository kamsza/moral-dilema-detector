package commonadapter.server.logic.models;

import adapter.BaseItem;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;

public abstract class BaseItemImpl implements BaseItem {

    protected String id;
    protected OntologyService ontologyService;

    public BaseItemImpl(String id, OntologyService ontologyService) {
        this.id = id;
        this.ontologyService = ontologyService;
    }

    @Override
    public String getId(Current current) {
        return id;
    }

    public String getId() {
        return id;
    }

    public abstract WrappedIndividual getWrappedIndividual();
}
