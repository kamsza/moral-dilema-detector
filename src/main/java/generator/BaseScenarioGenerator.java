package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseScenarioGenerator {
    protected String baseIRI;
    protected MyFactory factory;
    protected Random rand = new Random();

    public BaseScenarioGenerator(MyFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;

        ObjectNamer.setInitScenarioId(factory.getAllScenarioInstances().size());
    }

    /**
     * ancillary method to create many scenarios
     */
    // generate basic scenario with id
    public Model generate() {
        // get new individuals
        Scenario scenario = factory.createScenario(ObjectNamer.getName("scenario"));

        Weather weather = factory.createWeatherSubclass(ObjectNamer.getName("weather"));

        Time time = factory.createTimeSubclass(ObjectNamer.getName("time"));

        Road_type roadType = factory.createRoad_typeSubclass(ObjectNamer.getName("road_type"));

        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        Map<String, Surrounding> surrounding = new HashMap<>();
        surrounding.put("LEFT", factory.createSurroundingSubclass(ObjectNamer.getName("surrounding")));
        surrounding.put("RIGHT", factory.createSurroundingSubclass(ObjectNamer.getName("surrounding")));

        Vehicle vehicle = factory.createVehicle(ObjectNamer.getName("vehicle"));

        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 0; i < rand.nextInt(5); i++)
            passengers.add(factory.createPassengerSubclass(ObjectNamer.getName("passenger")));

        // set object properties
        scenario.addHas_vehicle(vehicle);
        scenario.addHas_weather(weather);
        scenario.addHas_time(time);

        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(roadType);
        vehicle.addHas_on_the_right(surrounding.get("RIGHT"));
        vehicle.addHas_on_the_left(surrounding.get("LEFT"));
        for(Passenger passenger : passengers)
            vehicle.addVehicle_has_passenger(passenger);

        // set data properties
        vehicle.addVehicle_has_speed_kmph(70);

        roadType.addHas_speed_limit_kmph(70);
        roadType.addHas_lanes(2);

        // save in a model
        Model model = new Model();

        model.setScenario(scenario) ;
        model.setWeather(weather);
        model.setTime(time);
        model.setRoadType(roadType);
        model.setDriver(driver);
        model.setSurrounding(surrounding);
        model.setVehicle(vehicle);
        model.setPassengers(passengers);

        return model;
    }

    /**
     * generate numOfScenarios basic scenario, with IDs starting from startingId
     */

    public ArrayList<Model> generate(int numOfScenarios) {
        ArrayList<Model> models = new ArrayList<>();

        for(int i = 0; i < numOfScenarios; i++)
            models.add(generate());

        return models;
    }

}
