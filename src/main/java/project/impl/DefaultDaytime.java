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
 * Source Class: DefaultDaytime <br>
 * @version generated on Tue Dec 01 08:24:37 CET 2020 by Mateusz
 */
public class DefaultDaytime extends WrappedIndividualImpl implements Daytime {

    public DefaultDaytime(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/time_is
     */
     
    public Collection<? extends Sunrise> getTime_is() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_TIME_IS,
                                               DefaultSunrise.class);
    }

    public boolean hasTime_is() {
	   return !getTime_is().isEmpty();
    }

    public void addTime_is(Sunrise newTime_is) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_TIME_IS,
                                       newTime_is);
    }

    public void removeTime_is(Sunrise oldTime_is) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_TIME_IS,
                                          oldTime_is);
    }


}
