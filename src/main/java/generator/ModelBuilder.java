package generator;

import javafx.util.Pair;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

public class ModelBuilder {
    private SizeManager sizeManager;
    private Random rand = new Random();
    private RandomSubclassGenerator subclassGenerator;
    private Model model;
    private RandomPositioner randomPositioner;
    private MyFactory factory;

    public ModelBuilder(Model model, MyFactory factory) throws FileNotFoundException, OWLOntologyCreationException {
        this.model = model;
        this.factory = factory;
        this.subclassGenerator = new RandomSubclassGenerator(factory);
        this.randomPositioner = model.getRandomPositioner();
        this.sizeManager = model.getSizeManager();
    }

    // CAR - ANIMAL SCENARIOS
    public ModelBuilder addAnimal(int[] objectsNum, double[] prob) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return addAnimal(objectsNum, prob, false);
    }

    public ModelBuilder addAnimal(int[] objectsNum, double[] prob, boolean beforeMainCar) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int N = ProbRand.randInt(objectsNum, prob);
        for(int i = 0; i < N; i++)
            this.addAnimal(beforeMainCar);
        return this;
    }

    public ModelBuilder addAnimal(boolean beforeMainCar) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        float entitySize = sizeManager.getLength("animal");
        int laneNo;
        float distance;

        if(beforeMainCar) {
            laneNo = getLaneNo();
            distance = randomPositioner.getRandomDistance(laneNo, entitySize, false);
        }
        else{
            laneNo = randomPositioner.getRandomLaneNumber(entitySize);
            distance = randomPositioner.getRandomDistance(laneNo, entitySize);
        }

        return addAnimal(laneNo, distance);
    }

    public ModelBuilder addAnimal(int laneNo, float distance) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Lane lane = randomPositioner.getLane(model, laneNo);

        Animal animal = subclassGenerator.generateAnimalSubclass(ObjectNamer.getName("animal"));
        animal = fillDataProps(animal, distance, "animal", 10, 5);
        animal.addValueInDollars(100F);
        animal.addIs_on_lane(lane);
        lane.addLane_has_pedestrian(animal);
        model.getEntities().get(lane).add(animal);

        return this;
    }

    // CAR - CAR SCENARIOS
    private Pair<String, Vehicle> getRandomVehicle() {
        int r = ProbRand.randInt(new int[]{1, 2, 3}, new double[]{0.2, 0.1, 0.1});
        Vehicle vehicle;
        String name;

        switch (r) {
            case 1:
                vehicle = factory.createTruck(ObjectNamer.getName("vehicle"));
                name = "truck";
                break;
            case 2:
                vehicle = factory.createMotorcycle(ObjectNamer.getName("vehicle"));
                name= "motorcycle";
                break;
            case 3:
                vehicle = factory.createBicycle(ObjectNamer.getName("vehicle"));
                name = "bike";
                break;
            default:
                vehicle = factory.createCar(ObjectNamer.getName("vehicle"));
                name = "car";
                break;
        }

        return new Pair(name, vehicle);
    }

    public ModelBuilder addVehicles(int[] objectsNum, double[] prob) {
        int N = ProbRand.randInt(objectsNum, prob);

        for(int i = 0; i < N; i++) {
            Pair<String, Vehicle>  randomVehicle = getRandomVehicle();
            String name = randomVehicle.getKey();
            Vehicle vehicle = randomVehicle.getValue();
            float entitySize = sizeManager.getLength(name);
            int laneNo = randomPositioner.getRandomLaneNumber(entitySize);
            float distance = randomPositioner.getRandomDistance(laneNo, entitySize);

            vehicle = fillDataProps(vehicle, distance, name);
            this.addVehicle(vehicle, laneNo, distance);
        }
        return this;
    }

    public ModelBuilder addOvertakenVehicle() {
        if(model.getRoadType().getLanes_count().iterator().next() == 1 || model.getRoadType().getLanes_count().iterator().next() -1 == model.getRoadType().getMain_vehicle_lane_id().iterator().next())
            return this;

        int lanesMovingLeft = model.getRoadType().getLeft_lanes_count().iterator().next();
        int lanesMovingRight = model.getRoadType().getRight_lanes_count().iterator().next();
        model.getRoadType().removeLeft_lanes_count(lanesMovingLeft);
        model.getRoadType().removeRight_lanes_count(lanesMovingRight);
        model.getRoadType().addLeft_lanes_count(lanesMovingLeft + 1);
        model.getRoadType().addRight_lanes_count(lanesMovingRight - 1);

        Pair<String, Vehicle>  randomVehicle = getRandomVehicle();
        String name = randomVehicle.getKey();
        Vehicle vehicle = randomVehicle.getValue();

        int laneNo = model.getRoadType().getMain_vehicle_lane_id().iterator().next() + 1;
        float distance = rand.nextFloat() * 200 - 100F;

        vehicle = fillDataProps(vehicle, distance, name);
        return addVehicle(vehicle, laneNo, distance);
    }

    public ModelBuilder addApproachedVehicle() {
        Pair<String, Vehicle>  randomVehicle = getRandomVehicle();
        String name = randomVehicle.getKey();
        Vehicle vehicle = randomVehicle.getValue();
        float entitySize = sizeManager.getLength(name);

        int laneNo = model.getRoadType().getMain_vehicle_lane_id().iterator().next();
        float distance =  randomPositioner.getRandomDistance(laneNo, entitySize, false);

        vehicle = fillDataProps(vehicle, distance, name);
        return addVehicle(vehicle, laneNo, distance);
    }

    public ModelBuilder addVehicle(Vehicle vehicle, int laneNo, float distance) {
        Lane vehicleLane = randomPositioner.getLane(model, laneNo);

        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        model.getScenario().addHas_vehicle(vehicle);

        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());

        float vehicleSpeed = (float) (50 + rand.nextInt(90));

        if(laneNo < model.getRoadType().getLeft_lanes_count().iterator().next()) {
            vehicleSpeed *= -1;
        }

        vehicle.addDistance(distance);
        vehicle.addLength(500F);
        vehicle.addSpeedX(vehicleSpeed);
        vehicle.addSpeedY(0F);
        vehicle.addAccelerationX(0F);
        vehicle.addAccelerationY(0F);

        vehicle.addIs_on_lane(vehicleLane);
        vehicleLane.addLane_has_vehicle(vehicle);
        model.getVehicles().get(vehicleLane).add(vehicle);

        return this;
    }

    // CAR - OBSTACLE SCENARIOS
    public ModelBuilder addObstacle(int[] objectsNum, double[] prob) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int N = ProbRand.randInt(objectsNum, prob);
        for(int i = 0; i < N; i++)
            this.addObstacle(false);
        return this;
    }

    public ModelBuilder addObstacle(int[] objectsNum, double[] prob, boolean beforeMainCar) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int N = ProbRand.randInt(objectsNum, prob);
        for(int i = 0; i < N; i++)
            this.addObstacle(beforeMainCar);
        return this;
    }

    public ModelBuilder addObstacle(boolean beforeMainCar) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        float entitySize = sizeManager.getLength("obstacle");
        int laneNo;
        float distance;

        if(beforeMainCar) {
            laneNo = getLaneNo();
            distance = randomPositioner.getRandomDistance(laneNo, entitySize, false);
        }
        else{
            laneNo = randomPositioner.getRandomLaneNumber(entitySize);
            distance = randomPositioner.getRandomDistance(laneNo, entitySize);
        }

        return addObstacle(laneNo, distance);
    }

    public ModelBuilder addObstacle(int laneNo, float distance) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Lane lane = randomPositioner.getLane(model, laneNo);

        On_the_lane object = subclassGenerator.generateSurroundingOnLaneSubclass(ObjectNamer.getName("surrounding"));
        object = fillDataProps(object, distance, "obstacle");
        object.addIs_on_lane(lane);
        lane.addLane_has_object(object);
        model.getObjects().get(lane).add(object);

        return this;
    }

    // CAR - PERSON SCENARIOS
    public ModelBuilder addPedestrianCrossing(int[] objectsNum, double[] prob) {
        int N = ProbRand.randInt(objectsNum, prob);
        return addPedestrianCrossing(N);
    }

    public ModelBuilder addPedestrianCrossing(int peopleCount) {
        if(peopleCount == 0)
            return this;

        float distance = randomPositioner.getRandomDistance();
        float width = sizeManager.getWidth("pedestrian_crossing");

        On_the_road pedestrianCrossing = factory.createPedestrian_crossing(ObjectNamer.getName("surrounding"));
        pedestrianCrossing.addDistance(distance);
        pedestrianCrossing = fillDataProps(pedestrianCrossing, distance, "pedestrian_crossing");

        for (Model.Side side : Model.Side.values()) {
            addPedestrianCrossingOnLanes(pedestrianCrossing, side);
        }

        while (peopleCount > 0) {
            int laneNo = randomPositioner.getRandomLaneNumber(1F);
            Lane lane = randomPositioner.getLane(model, laneNo);
            float personDistance = distance - (width / 2) + rand.nextInt((int) width);
            peopleCount -= 1;

            Person person = factory.createPerson(ObjectNamer.getName("person"));
            person = fillDataProps(person, personDistance, "person", 7, 4);
            model.getEntities().get(lane).add(person);
            lane.addLane_has_pedestrian(person);
            person.addIs_on_lane(lane);
        }
        return this;
    }

    public ModelBuilder pedestrianJaywalking(int[] objectsNum, double[] prob) {
        pedestrianJaywalking(objectsNum, prob, false);
        return this;
    }

    public ModelBuilder pedestrianJaywalking(int[] objectsNum, double[] prob, boolean beforeMainCar) {
        int N = ProbRand.randInt(objectsNum, prob);
        for(int i = 0; i < N; i++)
            pedestrianJaywalking(beforeMainCar);
        return this;
    }

    public ModelBuilder pedestrianJaywalking(boolean beforeMainCar) {
        float entitySize = sizeManager.getLength("person");
        int laneNo;
        float distance;

        if(beforeMainCar) {
            laneNo = getLaneNo();
            distance = randomPositioner.getRandomDistance(laneNo, entitySize, false);
        }
        else{
            laneNo = randomPositioner.getRandomLaneNumber(entitySize);
            distance = randomPositioner.getRandomDistance(laneNo, entitySize);
        }

        return pedestrianJaywalking(laneNo, distance);
    }

    public ModelBuilder pedestrianJaywalking(int laneNo, float distance){
        Lane lane = randomPositioner.getLane(model, laneNo);

        Person person = factory.createPerson(ObjectNamer.getName("person"));
        person = fillDataProps(person, distance, "person", 7, 4);
        lane.addLane_has_pedestrian(person);
        person.addIs_on_lane(lane);
        model.getEntities().get(lane).add(person);


        return this;
    }

    // AUXILIARY FUNCTIONS
    public Model getModel() {
        return this.model;
    }


    private  <T extends Entity> T fillDataProps(T entity, float distance, String entityName, int maxSpeedX, int maxSpeedY) {
        entity = fillDataProps(entity, distance, entityName);
        entity.addSpeedX((float) rand.nextInt(2 * maxSpeedY) - maxSpeedY);
        entity.addSpeedY((float) rand.nextInt(2 * maxSpeedX) - maxSpeedX);
        entity.addAccelerationX(0F);
        entity.addAccelerationY(0F);
        entity.addValueInDollars(0F);
        return entity;
    }

    private <T extends Entity> T fillDataProps(T entity, float distance, String entityName) {
        entity.addDistance(distance);
        entity.addLength(sizeManager.getLength(entityName));
        entity.addWidth(sizeManager.getWidth(entityName));
        entity.addValueInDollars(50000F);
        return entity;
    }

    private int getLaneNo() {
        int mainCarLane = model.getRoadType().getMain_vehicle_lane_id().iterator().next();
        int lanes = model.getRoadType().getLanes_count().iterator().next();
        int laneNo = mainCarLane - 1 + rand.nextInt(3);
        if(laneNo < 0 || laneNo >= lanes)
            laneNo = mainCarLane;
        return laneNo;
    }

    private void addPedestrianCrossingOnLanes(On_the_road pedestrianCrossing, Model.Side side){
        for (Map.Entry<Integer, Lane> lane : model.getLanes().get(side).entrySet()) {
            Lane l = lane.getValue();
            l.addLane_has_object(pedestrianCrossing);
            pedestrianCrossing.addIs_on_lane(l);
            model.getObjects().get(lane.getValue()).add(pedestrianCrossing);
        }
    }
}
