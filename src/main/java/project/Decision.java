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
 * Source Class: Decision <br>
 * @version generated on Sat Oct 17 17:24:30 CEST 2020 by Michał Barczyk
 */

public interface Decision extends State_change {

    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_action
     */
     
    /**
     * Gets all property values for the has_action property.<p>
     * 
     * @returns a collection of values for the has_action property.
     */
    Collection<? extends Action> getHas_action();

    /**
     * Checks if the class has a has_action property value.<p>
     * 
     * @return true if there is a has_action property value.
     */
    boolean hasHas_action();

    /**
     * Adds a has_action property value.<p>
     * 
     * @param newHas_action the has_action property value to be added
     */
    void addHas_action(Action newHas_action);

    /**
     * Removes a has_action property value.<p>
     * 
     * @param oldHas_action the has_action property value to be removed.
     */
    void removeHas_action(Action oldHas_action);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_consequence
     */
     
    /**
     * Gets all property values for the has_consequence property.<p>
     * 
     * @returns a collection of values for the has_consequence property.
     */
    Collection<? extends Consequence> getHas_consequence();

    /**
     * Checks if the class has a has_consequence property value.<p>
     * 
     * @return true if there is a has_consequence property value.
     */
    boolean hasHas_consequence();

    /**
     * Adds a has_consequence property value.<p>
     * 
     * @param newHas_consequence the has_consequence property value to be added
     */
    void addHas_consequence(Consequence newHas_consequence);

    /**
     * Removes a has_consequence property value.<p>
     * 
     * @param oldHas_consequence the has_consequence property value to be removed.
     */
    void removeHas_consequence(Consequence oldHas_consequence);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_certainty
     */
     
    /**
     * Gets all property values for the has_certainty property.<p>
     * 
     * @returns a collection of values for the has_certainty property.
     */
    Collection<? extends Object> getHas_certainty();

    /**
     * Checks if the class has a has_certainty property value.<p>
     * 
     * @return true if there is a has_certainty property value.
     */
    boolean hasHas_certainty();

    /**
     * Adds a has_certainty property value.<p>
     * 
     * @param newHas_certainty the has_certainty property value to be added
     */
    void addHas_certainty(Object newHas_certainty);

    /**
     * Removes a has_certainty property value.<p>
     * 
     * @param oldHas_certainty the has_certainty property value to be removed.
     */
    void removeHas_certainty(Object oldHas_certainty);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
