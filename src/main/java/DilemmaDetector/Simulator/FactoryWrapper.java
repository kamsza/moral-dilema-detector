package DilemmaDetector.Simulator;

import generator.MyFactorySingleton;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Living_entity;
import project.MyFactory;
import project.Vehicle;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FactoryWrapper {
    private final MyFactory factory;

    public FactoryWrapper(MyFactory factory) {
        this.factory = factory;
    }

    /**
     * Get living entity from actor using factory.
     * If living entity name is equal to victim actor entity name it means that it has to be pedestrian
     * It's a hack because of problems with ontology classes factory.getPedestrian(victim) will not work properly
     **/
    public boolean isPedestrian(Actor victimActor) {
        Living_entity victim = factory.getLiving_entity(victimActor.getEntity());
        if (victim == null)
            return false;
        return victimActor.getEntityName().equals(victim.getOwlIndividual().getIRI().toString());
    }

    public List<Living_entity> getLivingEntitiesFromActor(Actor actor) {
        Vehicle vehicle = factory.getVehicle(actor.getEntity());
        Living_entity living_entity = factory.getLiving_entity(actor.getEntity());
        List<Living_entity> result = new ArrayList<>();

        if (vehicle != null) {
            result.addAll(vehicle.getVehicle_has_passenger());
            result.addAll(vehicle.getVehicle_has_driver());
        } else if (living_entity != null) {
            result.add(living_entity);
        }
        return result;
    }

}