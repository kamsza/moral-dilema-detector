package project.impl;

import project.*;


import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultSnow <br>
 * @version generated on Sun Oct 25 17:23:25 CET 2020 by Mateusz
 */
public class DefaultSnow extends WrappedIndividualImpl implements Snow {

    public DefaultSnow(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/weather_is
     */
     
    public Collection<? extends Shower> getWeather_is() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_WEATHER_IS,
                                               DefaultShower.class);
    }

    public boolean hasWeather_is() {
	   return !getWeather_is().isEmpty();
    }

    public void addWeather_is(Shower newWeather_is) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_WEATHER_IS,
                                       newWeather_is);
    }

    public void removeWeather_is(Shower oldWeather_is) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_WEATHER_IS,
                                          oldWeather_is);
    }


}
