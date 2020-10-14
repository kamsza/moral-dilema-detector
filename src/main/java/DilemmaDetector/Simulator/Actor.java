package DilemmaDetector.Simulator;

import generator.Model;
import project.Entity;

import java.util.Iterator;

public class Actor{
    public static final int LANE_WIDTH = 300;

    private RigidBody rigidBody;
    private String entityName;

    public Actor(Entity entity, RigidBody rigidBody) {
        this.entityName = entity.getOwlIndividual().getIRI().toString();
        this.rigidBody = rigidBody;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntity() {
        return entityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        return entityName.equals(actor.entityName);
    }

    @Override
    public int hashCode() {
        return entityName.hashCode();
    }
}
