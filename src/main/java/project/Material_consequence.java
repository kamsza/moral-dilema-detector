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
 * Source Class: Material_consequence <br>
 * @version generated on Wed Dec 02 14:25:02 CET 2020 by Mateusz
 */

public interface Material_consequence extends Consequence {

    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#material_consequence_to
     */
     
    /**
     * Gets all property values for the material_consequence_to property.<p>
     * 
     * @returns a collection of values for the material_consequence_to property.
     */
    Collection<? extends Entity> getMaterial_consequence_to();

    /**
     * Checks if the class has a material_consequence_to property value.<p>
     * 
     * @return true if there is a material_consequence_to property value.
     */
    boolean hasMaterial_consequence_to();

    /**
     * Adds a material_consequence_to property value.<p>
     * 
     * @param newMaterial_consequence_to the material_consequence_to property value to be added
     */
    void addMaterial_consequence_to(Entity newMaterial_consequence_to);

    /**
     * Removes a material_consequence_to property value.<p>
     * 
     * @param oldMaterial_consequence_to the material_consequence_to property value to be removed.
     */
    void removeMaterial_consequence_to(Entity oldMaterial_consequence_to);


    /* ***************************************************
     * Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_material_value
     */
     
    /**
     * Gets all property values for the has_material_value property.<p>
     * 
     * @returns a collection of values for the has_material_value property.
     */
    Collection<? extends Object> getHas_material_value();

    /**
     * Checks if the class has a has_material_value property value.<p>
     * 
     * @return true if there is a has_material_value property value.
     */
    boolean hasHas_material_value();

    /**
     * Adds a has_material_value property value.<p>
     * 
     * @param newHas_material_value the has_material_value property value to be added
     */
    void addHas_material_value(Object newHas_material_value);

    /**
     * Removes a has_material_value property value.<p>
     * 
     * @param oldHas_material_value the has_material_value property value to be removed.
     */
    void removeHas_material_value(Object oldHas_material_value);



    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
