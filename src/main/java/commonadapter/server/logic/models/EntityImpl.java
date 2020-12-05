package commonadapter.server.logic.models;

import adapter.Entity;
import adapter.ItemType;
import com.zeroc.Ice.Current;
import commonadapter.server.logic.services.OntologyService;
import org.protege.owl.codegeneration.WrappedIndividual;
import project.Lane;

public abstract class EntityImpl extends BaseItemImpl implements Entity {

    protected project.Entity entity;

    public EntityImpl(String id, OntologyService ontologyService) {
        super(id, ontologyService);
    }

    @Override
    public void setLane(String laneId, Current current) {

        project.Lane lane = (Lane) ontologyService.loadItem(laneId, ItemType.LANE).getWrappedIndividual();
        this.entity.addIs_on_lane(lane);
    }

    @Override
    public void setDistance(float distance, Current current) {

        this.entity.addDistance(distance);
    }

    @Override
    public void setAccelerationX(float accelerationX, Current current) {
        this.entity.addAccelerationX(accelerationX);
    }

    @Override
    public void setAccelerationY(float accelerationY, Current current) {
        this.entity.addAccelerationY(accelerationY);
    }

    @Override
    public void setLength(float length, Current current) {
        this.entity.addLength(length);
    }

    @Override
    public void setWidth(float width, Current current) {
        this.entity.addWidth(width);
    }

    @Override
    public void setSpeedX(float speedX, Current current) {
        this.entity.addSpeedX(speedX);
    }

    @Override
    public void setSpeedY(float speedY, Current current) {
        this.entity.addSpeedY(speedY);
    }

    @Override
    public WrappedIndividual getWrappedIndividual() {
        return entity;
    }
}
