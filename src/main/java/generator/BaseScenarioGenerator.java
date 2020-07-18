package generator;

import project.Driver;
import project.Lane;
import project.OWLFactory;
import project.Passenger;
import project.Road_type;
import project.Scenario;
import project.Surrounding;
import project.Time;
import project.Vehicle;
import project.Weather;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseScenarioGenerator {
    protected String baseIRI;
    protected OWLFactory factory;
    protected Random rand = new Random();
    protected RandomSubclassGenerator subclassGenerator;

    public BaseScenarioGenerator(OWLFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;
        this.subclassGenerator = new RandomSubclassGenerator(factory);

        ObjectNamer.setInitScenarioId(factory.getAllScenarioInstances().size());
    }

    /**
     * ancillary method to create many scenarios
     */
    // generate basic scenario with id
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // create model
        Model model = new Model();

        // get new individuals
        Scenario scenario = factory.createScenario(ObjectNamer.getName("scenario"));

        Weather weather = subclassGenerator.generateWeatherSubclass(ObjectNamer.getName("weather"));

        Time time = subclassGenerator.generateTimeSubclass(ObjectNamer.getName("time"));

        Road_type roadType = subclassGenerator.generateRoadTypeSubclass(ObjectNamer.getName("road_type"));

        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        Lane lane = factory.createLane(ObjectNamer.getName("lane"));

        Map<String, Surrounding> surrounding = new HashMap<>();
        surrounding.put("LEFT", subclassGenerator.generateSurroundingSubclass(ObjectNamer.getName("surrounding")));
        surrounding.put("RIGHT", subclassGenerator.generateSurroundingSubclass(ObjectNamer.getName("surrounding")));


        Vehicle vehicle = factory.createVehicle(ObjectNamer.getName("vehicle"));

        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < rand.nextInt(5); i++)
            passengers.add(subclassGenerator.generatePassengerSubclass(ObjectNamer.getName("passenger")));

        // set object properties
        scenario.addHas_vehicle(vehicle);
        scenario.addHas_weather(weather);
        scenario.addHas_time(time);

        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(roadType);
        vehicle.addHas_on_the_right(surrounding.get("RIGHT"));
        // vehicle.addHas_on_the_left(lane);
        for (Passenger passenger : passengers)
            vehicle.addVehicle_has_passenger(passenger);

        // set data properties
        vehicle.addVehicle_has_speed_kmph(70);

        roadType.addHas_speed_limit_kmph(70);
        roadType.addHas_lanes(2);

        // save in a model
        model.setScenario(scenario);
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

    public ArrayList<Model> generate(int numOfScenarios) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<Model> models = new ArrayList<>();

        for (int i = 0; i < numOfScenarios; i++)
            models.add(generate());

        return models;
    }

}
