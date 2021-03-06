package commonadapter.server.logic.models;

import adapter.RoadAttributes;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;

public class RoadAttributesImpl extends BaseItemImpl implements RoadAttributes {

    private project.Road_attributes roadAttributes;

    public RoadAttributesImpl(String id, project.Road_attributes ontoRoadAttributes, OntologyService ontologyService) {
        super(id, ontologyService);
        this.roadAttributes = ontoRoadAttributes;
    }

    @Override
    public void setMotorway(boolean isMotorway, Current current) {

        this.roadAttributes.addMotorway(isMotorway);
    }

    @Override
    public void setUrban(boolean isUrban, Current current) {

        this.roadAttributes.addUrban(isUrban);
    }

    @Override
    public void setServiceArea(boolean isServiceArea, Current current) {

        this.roadAttributes.addService_area(isServiceArea);
    }

    @Override
    public void setControlledAccess(boolean isControlledAccess, Current current) {

        this.roadAttributes.addControlled_access(isControlledAccess);
    }

    @Override
    public void setToll(boolean isToll, Current current) {

        this.roadAttributes.addToll(isToll);
    }

    @Override
    public void setBridge(boolean isBridge, Current current) {

        this.roadAttributes.addBridge(isBridge);
    }

    @Override
    public void setTunnel(boolean isTunnel, Current current) {

        this.roadAttributes.addTunnel(isTunnel);
    }

    @Override
    public void setFerry(boolean isFerry, Current current) {

        this.roadAttributes.addFerry(isFerry);
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return roadAttributes;
    }
}
