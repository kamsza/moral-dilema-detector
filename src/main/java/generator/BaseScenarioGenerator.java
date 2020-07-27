package generator;

import project.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseScenarioGenerator {
    protected String baseIRI;
    protected MyFactory factory;
    protected Random rand = new Random();
    protected RandomSubclassGenerator subclassGenerator;

    public BaseScenarioGenerator(MyFactory factory, String baseIRI) {
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
        // create scenario
        Model model = new Model();
        Scenario scenario = factory.createScenario(ObjectNamer.getName("scenario"));
        model.setScenario(scenario);

        // add objects
        addEnvData(model);
        addRoad(model);
        addSurrounding(model);
        addMainVehicle(model);

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

    private void addEnvData(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Weather weather = subclassGenerator.generateWeatherSubclass(ObjectNamer.getName("weather"));
        Time time = subclassGenerator.generateTimeSubclass(ObjectNamer.getName("time"));

        model.setWeather(weather);
        model.setTime(time);

        model.getScenario().addHas_weather(weather);
        model.getScenario().addHas_time(time);
    }

    private void addRoad(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<Model.Side, Map<Integer, Lane>> lanes = new HashMap<>();
        Map<Lane, ArrayList<Entity>> entities = new HashMap<>();
        Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();
        Map<Lane, ArrayList<Animal>> animals = new HashMap<>();
        Map<Lane, ArrayList<Pedestrian>> pedestrians = new HashMap<>();

        int lanes_num = rand.nextInt(4) + 1;
        int veh_pos =  rand.nextInt((lanes_num + 1) / 2) ;

        // create objects
        Road_type roadType = subclassGenerator.generateRoadTypeSubclass(ObjectNamer.getName("road_type"));

        Lane lane_0 = factory.createLane(ObjectNamer.getName("lane_0"));
        entities.put(lane_0, new ArrayList<Entity>() {});
        vehicles.put(lane_0, new ArrayList<Vehicle>() {});
        animals.put(lane_0, new ArrayList<Animal>() {});
        pedestrians.put(lane_0, new ArrayList<Pedestrian>() {});
        lanes.put(Model.Side.CENTER, Map.of(0, lane_0));

        Map<Integer, Lane> lanes_right = new HashMap<>();
        for(int i = 1; i <= veh_pos; i++) {
            Lane lane = factory.createLane(ObjectNamer.getName("lane_right_" + i));
            lanes_right.put(i, lane);
            entities.put(lane, new ArrayList<Entity>() {});
            vehicles.put(lane, new ArrayList<Vehicle>() {});
        }
        lanes.put(Model.Side.RIGHT, lanes_right);

        Map<Integer, Lane> lanes_left = new HashMap<>();
        for(int i = 1; i <= lanes_num - veh_pos; i++) {
            Lane lane = factory.createLane(ObjectNamer.getName("lane_left_" + i));
            lanes_left.put(i, lane);
            entities.put(lane, new ArrayList<Entity>() {});
            vehicles.put(lane, new ArrayList<Vehicle>() {});
        }
        lanes.put(Model.Side.LEFT, lanes_left);

        // add to scenario
        model.getScenario().addHas_lane(lane_0);
        for(Lane lane : lanes_left.values()) model.getScenario().addHas_lane(lane);
        for(Lane lane : lanes_right.values()) model.getScenario().addHas_lane(lane);

        // add object properties

        // add data properties
        roadType.addHas_lanes(lanes_num);
        roadType.addHas_speed_limit_kmph(70);

        // add to model
        model.setRoadType(roadType);
        model.setLanes(lanes);
        model.setEntities(entities);
        model.setVehicles(vehicles);
        model.setAnimals(animals);
        model.setPedestrians(pedestrians);
    }

    private void addSurrounding(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<Model.Side, Surrounding> surrounding = new HashMap<>();

        // create objects
        Surrounding left_surrounding = subclassGenerator.generateSurroundingSubclass(ObjectNamer.getName("surrounding"));
        surrounding.put(Model.Side.LEFT, left_surrounding);
        Surrounding right_surrounding = subclassGenerator.generateSurroundingSubclass(ObjectNamer.getName("surrounding"));
        surrounding.put(Model.Side.RIGHT, right_surrounding);

        // add to model
        model.setSurrounding(surrounding);
    }

    private void addMainVehicle(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int pass_num = rand.nextInt() % 5;

        // create objects
        Vehicle vehicle = factory.createVehicle(ObjectNamer.getName("vehicle"));
        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < pass_num; i++)
            passengers.add(subclassGenerator.generatePassengerSubclass(ObjectNamer.getName("passenger")));

        // add to scenario
        model.getScenario().addHas_vehicle(vehicle);

        // add object properties
        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());
        vehicle.addDistance(0F);
        vehicle.addLength(500F);
        if(model.getLanes().get(Model.Side.RIGHT).isEmpty())
            vehicle.addHas_on_the_right(model.getSurrounding().get(Model.Side.RIGHT));
//        else
//            vehicle.addHas_on_the_right(model.getLanes().get(Model.Side.RIGHT).get(1));
        if(model.getLanes().get(Model.Side.LEFT).isEmpty())
            vehicle.addHas_on_the_right(model.getSurrounding().get(Model.Side.LEFT));
//        else
//            vehicle.addHas_on_the_right(model.getLanes().get(Model.Side.LEFT).get(1));
        for (Passenger passenger : passengers)
            vehicle.addVehicle_has_passenger(passenger);

        // add data properties
        vehicle.addVehicle_has_speed_kmph(70);

        // add to model
        model.setDriver(driver);
        model.setPassengers(passengers);
        model.setVehicle(vehicle);
    }


}
