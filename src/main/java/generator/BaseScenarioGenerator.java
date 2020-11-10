package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Animal;
import project.Driver;
import project.Lane;
import project.Living_entity;
import project.MyFactory;
import project.Non_living_entity;
import project.On_the_lane;
import project.On_the_road;
import project.Passenger;
import project.Person;
import project.Road_type;
import project.Scenario;
import project.Surrounding;
import project.Time;
import project.Vehicle;
import project.Weather;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class BaseScenarioGenerator {
    private String baseIRI;
    private MyFactory factory;

    private Random rand;
    private RandomSubclassGenerator subclassGenerator;

    private int lanesCount;
    private int mainVehicleLaneId;
    private float roadDist = 6400F;
    private float surroundingMaxLength = 100000F;

    public BaseScenarioGenerator() throws FileNotFoundException, OWLOntologyCreationException {
        this(MyFactorySingleton.getFactory(), MyFactorySingleton.baseIRI);
    }

    public BaseScenarioGenerator(MyFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;
        this.rand = new Random();
        this.subclassGenerator = new RandomSubclassGenerator(factory);

        ObjectNamer.setInitScenarioId(factory.getAllScenarioInstances().size());
    }

    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // create scenario
        Model model = new Model();
        Scenario scenario = factory.createScenario(ObjectNamer.getName("scenario"));
        model.setScenario(scenario);

        // add objects
        addRoad(model);
        addEnvData(model);
        addSurrounding(model);
        addMainVehicle(model);

        return model;
    }

    private void addVehicle(Model model) {
        Vehicle vehicle1;
        float entitySize;

        vehicle1 = factory.createTruck(ObjectNamer.getName("vehicle"));
        SizeManager sizeManager = model.getSizeManager();
        entitySize = sizeManager.getLength("truck");

        int laneNo = model.getRoadType().getMain_vehicle_lane_id().iterator().next();

        Driver driver1 = factory.createDriver(ObjectNamer.getName("driver"));

        model.getScenario().addHas_vehicle(vehicle1);

        vehicle1.addVehicle_has_driver(driver1);
        vehicle1.addVehicle_has_location(model.getRoadType());

        float vehicleSpeed = (float) (0);

        Lane lane = model.getLanes().get(Model.Side.CENTER).get(0);


        vehicle1.addDistance(3000F);
        vehicle1.addLength(500F);
        vehicle1.addWidth(200F);


        vehicle1.addSpeedX(vehicleSpeed);
        vehicle1.addSpeedY(0F);
        vehicle1.addAccelerationY(0F);
        vehicle1.addAccelerationX(0F);
        vehicle1.addValueInDollars(1000000F);
        vehicle1.addIs_on_lane(lane);
        lane.addLane_has_vehicle(vehicle1);

        model.getVehicles().get(lane).add(vehicle1);

    }

    private void addPedestrian(Model model) {
        Person person = factory.createPerson(ObjectNamer.getName("person"));
        person.addSpeedY(0F);
        person.addSpeedX(0F);
        person.addAccelerationX(0F);
        person.addAccelerationY(0F);
        person.addWidth(50F);
        person.addLength(50F);
        person.addDistance(3000F);
//        person.addValueInDollars(10000F);
        Lane lane = model.getLanes().get(Model.Side.CENTER).get(0);
        person.addIs_on_lane(lane);
        lane.addLane_has_pedestrian(person);
        model.getEntities().get(lane).add(person);
    }

    private void addEnvData(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // create objects
        Weather weather = subclassGenerator.generateWeatherSubclass(ObjectNamer.getName("weather"));
        Time time = subclassGenerator.generateTimeSubclass(ObjectNamer.getName("time"));

        // add to scenario
        model.getScenario().addHas_weather(weather);
        model.getScenario().addHas_time(time);

        // add to model
        model.setWeather(weather);
        model.setTime(time);
    }

    private void addRoad(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<Model.Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
        Map<Lane, ArrayList<Living_entity>> entities = new HashMap<>();
        Map<Lane, ArrayList<Non_living_entity>> objects = new HashMap<>();
        Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();

        lanesCount = rand.nextInt(4) + 1;
        model.setLanesCount(lanesCount);

        model.setRandomPositioner(new RandomPositioner(lanesCount));
        mainVehicleLaneId = lanesCount / 2 + rand.nextInt((lanesCount + 1)/2);
        int carLeftLanesCount = mainVehicleLaneId;
        int carRightLanesCount = lanesCount - carLeftLanesCount - 1;

        // create objects
        Road_type roadType = subclassGenerator.generateRoadTypeSubclass(ObjectNamer.getName("road_type"));

        // main lane
        Lane lane_0 = factory.createLane(ObjectNamer.getName("lane_0"));
        entities.put(lane_0, new ArrayList<Living_entity>() {
        });
        objects.put(lane_0, new ArrayList<Non_living_entity>() {
        });
        vehicles.put(lane_0, new ArrayList<Vehicle>() {
        });
        TreeMap<Integer, Lane> lane_center = new TreeMap<>() {{
            put(0, lane_0);
        }};
        lanes.put(Model.Side.CENTER, lane_center);

        // right lanes
        TreeMap<Integer, Lane> lanes_right = new TreeMap<>();
        for (int i = 1; i <= carRightLanesCount; i++) {
            Lane lane = factory.createLane(ObjectNamer.getName("lane_right_" + i));
            lanes_right.put(i, lane);
            entities.put(lane, new ArrayList<Living_entity>() {
            });
            objects.put(lane, new ArrayList<Non_living_entity>() {
            });
            vehicles.put(lane, new ArrayList<Vehicle>() {
            });
        }
        lanes.put(Model.Side.RIGHT, lanes_right);

        // left lanes
        TreeMap<Integer, Lane> lanes_left = new TreeMap<>();
        for (int i = 1; i <= carLeftLanesCount; i++) {
            Lane lane = factory.createLane(ObjectNamer.getName("lane_left_" + i));
            lanes_left.put(i, lane);
            entities.put(lane, new ArrayList<Living_entity>() {
            });
            objects.put(lane, new ArrayList<Non_living_entity>() {
            });
            vehicles.put(lane, new ArrayList<Vehicle>() {
            });
        }
        lanes.put(Model.Side.LEFT, lanes_left);

        // add to scenario
        model.getScenario().addHas_lane(lane_0);
        for (Lane lane : lanes_left.values()) model.getScenario().addHas_lane(lane);
        for (Lane lane : lanes_right.values()) model.getScenario().addHas_lane(lane);


        // add data properties
        roadType.addHas_speed_limit_kmph(50 + 10 * rand.nextInt(9));
        roadType.addLanes_count(lanesCount);
        roadType.addMain_vehicle_lane_id(lanes_left.size());
        roadType.addLeft_lanes_count(lanes_left.size());
        roadType.addRight_lanes_count(lanesCount - lanes_left.size());

        // add to model
        model.setRoadType(roadType);
        model.setLanes(lanes);
        model.setEntities(entities);
        model.setObjects(objects);
        model.setVehicles(vehicles);
    }

    private void addSurrounding(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<Model.Side, ArrayList<Surrounding>> surrounding = new HashMap<>();

        // create objects
        ArrayList<Surrounding> left_surrounding = createSurroundingList();
        surrounding.put(Model.Side.LEFT, left_surrounding);
        ArrayList<Surrounding> right_surrounding = createSurroundingList();
        surrounding.put(Model.Side.RIGHT, right_surrounding);
        for (Surrounding s: left_surrounding) {
            model.getScenario().addHas_surrounding_left(s);
        }
        for (Surrounding s: right_surrounding) {
            model.getScenario().addHas_surrounding_right(s);
        }

        // add to model
        model.setSurrounding(surrounding);
    }

    private ArrayList<Surrounding> createSurroundingList() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<Surrounding> surroundingList = new ArrayList<>();


        // is alongside whole road
        if(rand.nextInt(10) <= 7)  {
            Surrounding surrounding = createSurrounding(0F, surroundingMaxLength);
            surroundingList.add(surrounding);
        }
        // is on part of the road
        else {
            for(float x = -1 * roadDist/2; x < roadDist/2;){
                float length = 500F + 4800 * rand.nextFloat();
                if(x + length > roadDist/2)
                    length = surroundingMaxLength;
                float dist = x + length/2;
                surroundingList.add(createSurrounding(dist, length));
                x += length;
            }
        }

        return surroundingList;
    }

    private Surrounding createSurrounding(float dist, float length) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Surrounding surrounding = subclassGenerator.generateSurroundingSubclass(ObjectNamer.getName("surrounding"));
        surrounding.addDistance(dist);
        surrounding.addLength(length);
        surrounding.addWidth(rand.nextFloat() * 200);
        surrounding.addDistanceToRoad(rand.nextFloat() * 40);
        return surrounding;
    }

    private void addMainVehicle(Model model) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int pass_count = rand.nextInt(6);

        // create objects
        Vehicle vehicle = factory.createCar(ObjectNamer.getName("vehicle_main"));
        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < pass_count; i++)
            passengers.add(subclassGenerator.generatePassengerSubclass(ObjectNamer.getName("passenger")));

        // add to scenario
        model.getScenario().addHas_vehicle(vehicle);

        // add object properties
        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());
        for (Passenger passenger : passengers)
            vehicle.addVehicle_has_passenger(passenger);

        // add data properties
        SizeManager sizeManager = model.getSizeManager();
        RandomPositioner randomPositioner = model.getRandomPositioner();
        vehicle.addSpeedX((float) (50 + rand.nextInt(90)));
        vehicle.addSpeedY(0F);
        vehicle.addDistance(0F);
        vehicle.addAccelerationY(0F);
        vehicle.addAccelerationX(0F);
        vehicle.addLength(sizeManager.getLength("car"));
        vehicle.addWidth(sizeManager.getWidth("car"));
        vehicle.addValueInDollars(20000F);

        // add to model
        randomPositioner.addMainVehicle(mainVehicleLaneId, sizeManager.getLength("car"));
        Lane lane = model.getLanes().get(Model.Side.CENTER).get(0);
        vehicle.addIs_on_lane(lane);
        lane.addLane_has_vehicle(vehicle);
        model.getVehicles().get(lane).add(vehicle);
        model.setDriver(driver);
        model.setPassengers(passengers);
        model.setVehicle(vehicle);
    }
}