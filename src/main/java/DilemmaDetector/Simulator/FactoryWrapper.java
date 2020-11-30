package DilemmaDetector.Simulator;

import generator.MyFactorySingleton;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.drools.owl.properties.P;
import project.*;

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

    public boolean isSurrounding(Actor victimActor) {
        On_the_side surrounding = factory.getOn_the_side(victimActor.getEntity());
        if (surrounding == null)
            return false;
        System.out.println(victimActor.getEntityName());
        System.out.println(surrounding.getOwlIndividual().getIRI().toString());
        System.out.println(victimActor.getEntityName().equals(surrounding.getOwlIndividual().getIRI().toString()));
        return victimActor.getEntityName().equals(surrounding.getOwlIndividual().getIRI().toString());
    }

    public boolean isVehicle(Actor victimActor) {
        Vehicle vehicle = factory.getVehicle(victimActor.getEntity());
        if (vehicle == null)
            return false;
        return victimActor.getEntityName().equals(vehicle.getOwlIndividual().getIRI().toString());
    }

    public boolean isCollidableObstacle(String obstacleName )  {
        Concrete_barrier concrete_barrier = factory.getConcrete_barrier(obstacleName);
        Plastic_barrier plastic_barrier = factory.getPlastic_barrier(obstacleName);
        Rock rock = factory.getRock(obstacleName);

        Street_tidy street_tidy = factory.getStreet_tidy(obstacleName);
        Pedestrian_crossing pedestrian_crossing = factory.getPedestrian_crossing(obstacleName);
        Speed_bump speed_bump = factory.getSpeed_bump(obstacleName);

        if (concrete_barrier != null || plastic_barrier != null || rock != null)
            return true;
        else if(street_tidy != null || pedestrian_crossing != null || speed_bump != null)
            return false;
        return false;
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