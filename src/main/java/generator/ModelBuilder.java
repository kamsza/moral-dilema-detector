package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

public class ModelBuilder {
    private SizeManager sizeManager = new SizeManager();
    private Random rand = new Random();
    private RandomSubclassGenerator subclassGenerator;
    private Model model;
    private RandomPositioner randomPositioner;
    private MyFactory factory;

    public ModelBuilder(Model model) throws FileNotFoundException, OWLOntologyCreationException {
        this.model = model;
        this.factory = MyFactorySingleton.getFactory();
        this.subclassGenerator = new RandomSubclassGenerator(factory);
        this.randomPositioner = new RandomPositioner(model.getLanesCount());
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

        model.getEntities().get(lane).add(animal);

        return this;
    }

    // CAR - CAR SCENARIOS
    public ModelBuilder addVehicles(int[] objectsNum, double[] prob) {
        int N = ProbRand.randInt(objectsNum, prob);

        for(int i = 0; i < N; i++) {
            int r = ProbRand.randInt(new int[]{1, 2, 3, 4}, new double[]{0.6, 0.2, 0.1, 0.1});
            Vehicle vehicle;
            float entitySize;

            switch (r) {
                case 1:
                    vehicle = factory.createTruck(ObjectNamer.getName("vehicle"));
                    entitySize = sizeManager.getLength("truck");
                    break;
                case 2:
                    vehicle = factory.createMotorcycle(ObjectNamer.getName("vehicle"));
                    entitySize = sizeManager.getLength("motorbike");
                    break;
                case 3:
                    vehicle = factory.createBicycle(ObjectNamer.getName("vehicle"));
                    entitySize = sizeManager.getLength("bike");
                    break;
                default:
                    vehicle = factory.createCar(ObjectNamer.getName("vehicle"));
                    entitySize = sizeManager.getLength("car");
                    break;
            }

            int laneNo = randomPositioner.getRandomLaneNumber(entitySize);
            float distance = randomPositioner.getRandomDistance(laneNo, entitySize);

            this.addVehicle(vehicle, laneNo, distance);
        }
        return this;
    }

    public ModelBuilder addOvertakenVehicle() {
        // TODO
        return this;
    }

    public ModelBuilder addApproachedVehicle() {
        int r = ProbRand.randInt(new int[]{1, 2, 3, 4}, new double[]{0.6, 0.2, 0.1, 0.1});
        Vehicle vehicle;
        float entitySize;

        switch(r) {
            case 1:
                vehicle = factory.createTruck(ObjectNamer.getName("vehicle"));
                entitySize = sizeManager.getLength("truck");
                break;
            case 2:
                vehicle = factory.createMotorcycle(ObjectNamer.getName("vehicle"));
                entitySize = sizeManager.getLength("motorbike");
                break;
            case 3:
                vehicle = factory.createBicycle(ObjectNamer.getName("vehicle"));
                entitySize = sizeManager.getLength("bike");
                break;
            default:
                vehicle = factory.createCar(ObjectNamer.getName("vehicle"));
                entitySize = sizeManager.getLength("car");
                break;
        }

        int laneNo = model.getRoadType().getMain_vehicle_lane_id().iterator().next();
        float distance =  randomPositioner.getRandomDistance(laneNo, entitySize, false);

        return addVehicle(vehicle, laneNo, distance);
    }

    public ModelBuilder addVehicle(Vehicle vehicle, int laneNo, float distance) {
        Lane vehicleLane = randomPositioner.getLane(model, laneNo);

        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        model.getScenario().addHas_vehicle(vehicle);

        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());

        float vehicleSpeed = (float) (50 + rand.nextInt(90));

        if(laneNo < model.getRoadType().getLanes_lu_num().iterator().next()) {
            vehicleSpeed *= -1;
        }

        vehicle.addDistance(distance);
        vehicle.addLength(500F);
        vehicle.addSpeedY(vehicleSpeed);
        vehicle.addSpeedX(0F);
        vehicle.addAccelerationY(0F);
        vehicle.addAccelerationX(0F);

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

        for (Map.Entry<Integer, Lane> lane : model.getLanes().get(Model.Side.LEFT).entrySet())
            model.getObjects().get(lane.getValue()).add(pedestrianCrossing);

        for (Map.Entry<Integer, Lane> lane : model.getLanes().get(Model.Side.CENTER).entrySet())
            model.getObjects().get(lane.getValue()).add(pedestrianCrossing);

        for (Map.Entry<Integer, Lane> lane : model.getLanes().get(Model.Side.RIGHT).entrySet())
            model.getObjects().get(lane.getValue()).add(pedestrianCrossing);

        while (peopleCount > 0) {
            int laneNo = randomPositioner.getRandomLaneNumber(1F);
            Lane lane = randomPositioner.getLane(model, laneNo);
            float personDistance = distance - (width / 2) + rand.nextInt((int) width);
            peopleCount -= 1;

            Person person = factory.createPerson(ObjectNamer.getName("person"));
            person = fillDataProps(person, personDistance, "person", 7, 4);
            model.getEntities().get(lane).add(person);
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

        model.getEntities().get(lane).add(person);

        return this;
    }

    // AUXILIARY FUNCTIONS
    public Model getModel() {
        return this.model;
    }


    private <T extends Entity> T fillDataProps(T entity, float distance, String entityName, int maxSpeedX, int maxSpeedY) {
        entity = fillDataProps(entity, distance, entityName);
        entity.addSpeedY((float) rand.nextInt(2 * maxSpeedY) - maxSpeedY);
        entity.addSpeedX((float) rand.nextInt(2 * maxSpeedX) - maxSpeedX);
        entity.addAccelerationY(0F);
        entity.addAccelerationX(0F);

        return entity;
    }

    private <T extends Entity> T fillDataProps(T entity, float distance, String entityName) {
        entity.addDistance(distance);
        entity.addLength(sizeManager.getLength(entityName));
        entity.addWidth(sizeManager.getWidth(entityName));

        return entity;
    }

    private int getLaneNo() {
        int mainCarLane = model.getRoadType().getMain_vehicle_lane_id().iterator().next();
        int lanes = model.getRoadType().getLanes_num().iterator().next();
        int laneNo = mainCarLane - 1 + rand.nextInt(3);
        if(laneNo < 0 || laneNo >= lanes)
            laneNo = mainCarLane;
        return laneNo;
    }
}
