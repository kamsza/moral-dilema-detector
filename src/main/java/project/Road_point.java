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
 * Source Class: Road_point <br>
 * @version generated on Tue Nov 03 21:42:21 CET 2020 by Mateusz
 */

public interface Road_point extends WrappedIndividual {

    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#latitude
     */
     
    /**
     * Gets all property values for the latitude property.<p>
     * 
     * @returns a collection of values for the latitude property.
     */
    Collection<? extends String> getLatitude();

    /**
     * Checks if the class has a latitude property value.<p>
     * 
     * @return true if there is a latitude property value.
     */
    boolean hasLatitude();

    /**
     * Adds a latitude property value.<p>
     * 
     * @param newLatitude the latitude property value to be added
     */
    void addLatitude(String newLatitude);

    /**
     * Removes a latitude property value.<p>
     * 
     * @param oldLatitude the latitude property value to be removed.
     */
    void removeLatitude(String oldLatitude);



    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#longitude
     */
     
    /**
     * Gets all property values for the longitude property.<p>
     * 
     * @returns a collection of values for the longitude property.
     */
    Collection<? extends String> getLongitude();

    /**
     * Checks if the class has a longitude property value.<p>
     * 
     * @return true if there is a longitude property value.
     */
    boolean hasLongitude();

    /**
     * Adds a longitude property value.<p>
     * 
     * @param newLongitude the longitude property value to be added
     */
    void addLongitude(String newLongitude);

    /**
     * Removes a longitude property value.<p>
     * 
     * @param oldLongitude the longitude property value to be removed.
     */
    void removeLongitude(String oldLongitude);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
