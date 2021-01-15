package commonadapter.server.logic.models;

import adapter.LaneBoundary;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;

public class LaneBoundaryImpl extends BaseItemImpl implements LaneBoundary {

    private project.Lane_boundary laneBoundary;

    public LaneBoundaryImpl(String id, project.Lane_boundary ontoLaneBoundary, OntologyService ontologyService) {
        super(id, ontologyService);
        this.laneBoundary = ontoLaneBoundary;
    }

    @Override
    public void setType(String type, Current current) {

        this.laneBoundary.addType(type);
    }

    @Override
    public void setColor(String color, Current current) {

        this.laneBoundary.addColor(color);
    }

    @Override
    public void setMaterial(String material, Current current) {

        this.laneBoundary.addMaterial(material);
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return laneBoundary;
    }
}
