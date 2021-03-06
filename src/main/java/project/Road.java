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
 * Source Class: Road <br>
 * @version generated on Wed Dec 02 14:25:02 CET 2020 by Mateusz
 */

public interface Road extends WrappedIndividual {

    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#ends
     */
     
    /**
     * Gets all property values for the ends property.<p>
     * 
     * @returns a collection of values for the ends property.
     */
    Collection<? extends Road_point> getEnds();

    /**
     * Checks if the class has a ends property value.<p>
     * 
     * @return true if there is a ends property value.
     */
    boolean hasEnds();

    /**
     * Adds a ends property value.<p>
     * 
     * @param newEnds the ends property value to be added
     */
    void addEnds(Road_point newEnds);

    /**
     * Removes a ends property value.<p>
     * 
     * @param oldEnds the ends property value to be removed.
     */
    void removeEnds(Road_point oldEnds);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_road_attributes
     */
     
    /**
     * Gets all property values for the has_road_attributes property.<p>
     * 
     * @returns a collection of values for the has_road_attributes property.
     */
    Collection<? extends Road_attributes> getHas_road_attributes();

    /**
     * Checks if the class has a has_road_attributes property value.<p>
     * 
     * @return true if there is a has_road_attributes property value.
     */
    boolean hasHas_road_attributes();

    /**
     * Adds a has_road_attributes property value.<p>
     * 
     * @param newHas_road_attributes the has_road_attributes property value to be added
     */
    void addHas_road_attributes(Road_attributes newHas_road_attributes);

    /**
     * Removes a has_road_attributes property value.<p>
     * 
     * @param oldHas_road_attributes the has_road_attributes property value to be removed.
     */
    void removeHas_road_attributes(Road_attributes oldHas_road_attributes);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#starts
     */
     
    /**
     * Gets all property values for the starts property.<p>
     * 
     * @returns a collection of values for the starts property.
     */
    Collection<? extends Road_point> getStarts();

    /**
     * Checks if the class has a starts property value.<p>
     * 
     * @return true if there is a starts property value.
     */
    boolean hasStarts();

    /**
     * Adds a starts property value.<p>
     * 
     * @param newStarts the starts property value to be added
     */
    void addStarts(Road_point newStarts);

    /**
     * Removes a starts property value.<p>
     * 
     * @param oldStarts the starts property value to be removed.
     */
    void removeStarts(Road_point oldStarts);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#average_speed
     */
     
    /**
     * Gets all property values for the average_speed property.<p>
     * 
     * @returns a collection of values for the average_speed property.
     */
    Collection<? extends Integer> getAverage_speed();

    /**
     * Checks if the class has a average_speed property value.<p>
     * 
     * @return true if there is a average_speed property value.
     */
    boolean hasAverage_speed();

    /**
     * Adds a average_speed property value.<p>
     * 
     * @param newAverage_speed the average_speed property value to be added
     */
    void addAverage_speed(Integer newAverage_speed);

    /**
     * Removes a average_speed property value.<p>
     * 
     * @param oldAverage_speed the average_speed property value to be removed.
     */
    void removeAverage_speed(Integer oldAverage_speed);



    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#end_angle
     */
     
    /**
     * Gets all property values for the end_angle property.<p>
     * 
     * @returns a collection of values for the end_angle property.
     */
    Collection<? extends Float> getEnd_angle();

    /**
     * Checks if the class has a end_angle property value.<p>
     * 
     * @return true if there is a end_angle property value.
     */
    boolean hasEnd_angle();

    /**
     * Adds a end_angle property value.<p>
     * 
     * @param newEnd_angle the end_angle property value to be added
     */
    void addEnd_angle(Float newEnd_angle);

    /**
     * Removes a end_angle property value.<p>
     * 
     * @param oldEnd_angle the end_angle property value to be removed.
     */
    void removeEnd_angle(Float oldEnd_angle);



    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#speed_limit
     */
     
    /**
     * Gets all property values for the speed_limit property.<p>
     * 
     * @returns a collection of values for the speed_limit property.
     */
    Collection<? extends Integer> getSpeed_limit();

    /**
     * Checks if the class has a speed_limit property value.<p>
     * 
     * @return true if there is a speed_limit property value.
     */
    boolean hasSpeed_limit();

    /**
     * Adds a speed_limit property value.<p>
     * 
     * @param newSpeed_limit the speed_limit property value to be added
     */
    void addSpeed_limit(Integer newSpeed_limit);

    /**
     * Removes a speed_limit property value.<p>
     * 
     * @param oldSpeed_limit the speed_limit property value to be removed.
     */
    void removeSpeed_limit(Integer oldSpeed_limit);



    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#start_angle
     */
     
    /**
     * Gets all property values for the start_angle property.<p>
     * 
     * @returns a collection of values for the start_angle property.
     */
    Collection<? extends Float> getStart_angle();

    /**
     * Checks if the class has a start_angle property value.<p>
     * 
     * @return true if there is a start_angle property value.
     */
    boolean hasStart_angle();

    /**
     * Adds a start_angle property value.<p>
     * 
     * @param newStart_angle the start_angle property value to be added
     */
    void addStart_angle(Float newStart_angle);

    /**
     * Removes a start_angle property value.<p>
     * 
     * @param oldStart_angle the start_angle property value to be removed.
     */
    void removeStart_angle(Float oldStart_angle);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
