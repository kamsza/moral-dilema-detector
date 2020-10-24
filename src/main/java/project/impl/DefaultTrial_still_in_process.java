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
 * Source Class: DefaultTrial_still_in_process <br>
 * @version generated on Sat Oct 17 17:24:30 CEST 2020 by Michał Barczyk
 */
public class DefaultTrial_still_in_process extends WrappedIndividualImpl implements Trial_still_in_process {

    public DefaultTrial_still_in_process(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/has_type_of_sentence
     */
     
    public Collection<? extends WrappedIndividual> getHas_type_of_sentence() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_TYPE_OF_SENTENCE,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasHas_type_of_sentence() {
	   return !getHas_type_of_sentence().isEmpty();
    }

    public void addHas_type_of_sentence(WrappedIndividual newHas_type_of_sentence) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_TYPE_OF_SENTENCE,
                                       newHas_type_of_sentence);
    }

    public void removeHas_type_of_sentence(WrappedIndividual oldHas_type_of_sentence) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_TYPE_OF_SENTENCE,
                                          oldHas_type_of_sentence);
    }


    /* ***************************************************
     * Data Property http://webprotege.stanford.edu/criminal_sentence_properties
     */
     
    public Collection<? extends Object> getCriminal_sentence_properties() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_CRIMINAL_SENTENCE_PROPERTIES, Object.class);
    }

    public boolean hasCriminal_sentence_properties() {
		return !getCriminal_sentence_properties().isEmpty();
    }

    public void addCriminal_sentence_properties(Object newCriminal_sentence_properties) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_CRIMINAL_SENTENCE_PROPERTIES, newCriminal_sentence_properties);
    }

    public void removeCriminal_sentence_properties(Object oldCriminal_sentence_properties) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_CRIMINAL_SENTENCE_PROPERTIES, oldCriminal_sentence_properties);
    }


    /* ***************************************************
     * Data Property http://webprotege.stanford.edu/judgement_passing_date
     */
     
    public Collection<? extends Object> getJudgement_passing_date() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_JUDGEMENT_PASSING_DATE, Object.class);
    }

    public boolean hasJudgement_passing_date() {
		return !getJudgement_passing_date().isEmpty();
    }

    public void addJudgement_passing_date(Object newJudgement_passing_date) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_JUDGEMENT_PASSING_DATE, newJudgement_passing_date);
    }

    public void removeJudgement_passing_date(Object oldJudgement_passing_date) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_JUDGEMENT_PASSING_DATE, oldJudgement_passing_date);
    }


}
