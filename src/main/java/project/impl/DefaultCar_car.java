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
 * Source Class: DefaultCar_car <br>
 * @version generated on Wed Oct 28 22:21:56 CET 2020 by Mateusz
 */
public class DefaultCar_car extends WrappedIndividualImpl implements Car_car {

    public DefaultCar_car(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/has_pedestrian
     */
     
    public Collection<? extends Pedestrian> getHas_pedestrian() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_PEDESTRIAN,
                                               DefaultPedestrian.class);
    }

    public boolean hasHas_pedestrian() {
	   return !getHas_pedestrian().isEmpty();
    }

    public void addHas_pedestrian(Pedestrian newHas_pedestrian) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_PEDESTRIAN,
                                       newHas_pedestrian);
    }

    public void removeHas_pedestrian(Pedestrian oldHas_pedestrian) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_PEDESTRIAN,
                                          oldHas_pedestrian);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/has_time
     */
     
    public Collection<? extends Time> getHas_time() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_TIME,
                                               DefaultTime.class);
    }

    public boolean hasHas_time() {
	   return !getHas_time().isEmpty();
    }

    public void addHas_time(Time newHas_time) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_TIME,
                                       newHas_time);
    }

    public void removeHas_time(Time oldHas_time) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_TIME,
                                          oldHas_time);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/has_vehicle
     */
     
    public Collection<? extends Vehicle> getHas_vehicle() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_VEHICLE,
                                               DefaultVehicle.class);
    }

    public boolean hasHas_vehicle() {
	   return !getHas_vehicle().isEmpty();
    }

    public void addHas_vehicle(Vehicle newHas_vehicle) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_VEHICLE,
                                       newHas_vehicle);
    }

    public void removeHas_vehicle(Vehicle oldHas_vehicle) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_VEHICLE,
                                          oldHas_vehicle);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/has_weather
     */
     
    public Collection<? extends Weather> getHas_weather() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_WEATHER,
                                               DefaultWeather.class);
    }

    public boolean hasHas_weather() {
	   return !getHas_weather().isEmpty();
    }

    public void addHas_weather(Weather newHas_weather) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_WEATHER,
                                       newHas_weather);
    }

    public void removeHas_weather(Weather oldHas_weather) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_WEATHER,
                                          oldHas_weather);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_cyclist
     */
     
    public Collection<? extends Cyclist> getHas_cyclist() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_CYCLIST,
                                               DefaultCyclist.class);
    }

    public boolean hasHas_cyclist() {
	   return !getHas_cyclist().isEmpty();
    }

    public void addHas_cyclist(Cyclist newHas_cyclist) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_CYCLIST,
                                       newHas_cyclist);
    }

    public void removeHas_cyclist(Cyclist oldHas_cyclist) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_CYCLIST,
                                          oldHas_cyclist);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_decision
     */
     
    public Collection<? extends Decision> getHas_decision() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_DECISION,
                                               DefaultDecision.class);
    }

    public boolean hasHas_decision() {
	   return !getHas_decision().isEmpty();
    }

    public void addHas_decision(Decision newHas_decision) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_DECISION,
                                       newHas_decision);
    }

    public void removeHas_decision(Decision oldHas_decision) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_DECISION,
                                          oldHas_decision);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_lane
     */
     
    public Collection<? extends Lane> getHas_lane() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_LANE,
                                               DefaultLane.class);
    }

    public boolean hasHas_lane() {
	   return !getHas_lane().isEmpty();
    }

    public void addHas_lane(Lane newHas_lane) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_LANE,
                                       newHas_lane);
    }

    public void removeHas_lane(Lane oldHas_lane) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_LANE,
                                          oldHas_lane);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_road
     */
     
    public Collection<? extends Road> getHas_road() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_ROAD,
                                               DefaultRoad.class);
    }

    public boolean hasHas_road() {
	   return !getHas_road().isEmpty();
    }

    public void addHas_road(Road newHas_road) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_ROAD,
                                       newHas_road);
    }

    public void removeHas_road(Road oldHas_road) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_ROAD,
                                          oldHas_road);
    }


    /* ***************************************************
     * Object Property http://webprotege.stanford.edu/project/BDGSqwMbfBgw7pUJ8IOnJ1#has_road_point
     */
     
    public Collection<? extends Road_point> getHas_road_point() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HAS_ROAD_POINT,
                                               DefaultRoad_point.class);
    }

    public boolean hasHas_road_point() {
	   return !getHas_road_point().isEmpty();
    }

    public void addHas_road_point(Road_point newHas_road_point) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HAS_ROAD_POINT,
                                       newHas_road_point);
    }

    public void removeHas_road_point(Road_point oldHas_road_point) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HAS_ROAD_POINT,
                                          oldHas_road_point);
    }


}
