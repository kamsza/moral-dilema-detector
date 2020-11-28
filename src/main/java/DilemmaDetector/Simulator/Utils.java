package DilemmaDetector.Simulator;

import generator.MyFactorySingleton;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Living_entity;
import project.MyFactory;

import java.io.FileNotFoundException;

public class Utils {
    private static MyFactory factory;

    static {
        try {
            factory = MyFactorySingleton.getFactory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
    ;

    public static boolean isPedestrian(Actor victimActor){
        /*
        Get living entity from actor using factory. If living entity name is equal to victim actor entity name it means that
        it has to be pedestrian
         It's a hack because of problems with ontology classes factory.getPedestrian(victim) will not work properly
       */
        Living_entity victim = factory.getLiving_entity(victimActor.getEntity());
        if (victim == null)
            return false;
        return victimActor.getEntityName().equals(victim.getOwlIndividual().getIRI().toString());
    }
}
