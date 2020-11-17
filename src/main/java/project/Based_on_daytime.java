package project;

import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Based_on_daytime <br>
 * @version generated on Sun Nov 15 23:20:32 CET 2020 by Michał Barczyk
 */

public interface Based_on_daytime extends Speed_limit {

    /* ***************************************************
     * Property http://webprotege.stanford.edu/traffic_code_has
     */
     
    /**
     * Gets all property values for the traffic_code_has property.<p>
     * 
     * @returns a collection of values for the traffic_code_has property.
     */
    Collection<? extends Traffic_type> getTraffic_code_has();

    /**
     * Checks if the class has a traffic_code_has property value.<p>
     * 
     * @return true if there is a traffic_code_has property value.
     */
    boolean hasTraffic_code_has();

    /**
     * Adds a traffic_code_has property value.<p>
     * 
     * @param newTraffic_code_has the traffic_code_has property value to be added
     */
    void addTraffic_code_has(Traffic_type newTraffic_code_has);

    /**
     * Removes a traffic_code_has property value.<p>
     * 
     * @param oldTraffic_code_has the traffic_code_has property value to be removed.
     */
    void removeTraffic_code_has(Traffic_type oldTraffic_code_has);


    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
