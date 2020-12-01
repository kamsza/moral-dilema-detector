package DilemmaDetector;

import generator.Model;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.*;
import visualization.Visualization;

import java.io.File;
import java.util.*;

public class ScenarioReader {
    private OWLOntologyManager ontologyManager;
    private OWLOntology ontology;
    private MyFactory factory;
    public static final String IRI_PREFIX = "http://www.w3.org/2003/11/";

    public ScenarioReader() throws OWLOntologyCreationException {
        this("src/main/resources/traffic_ontology.owl");
    }

    public ScenarioReader(String ontologyPath) throws OWLOntologyCreationException {
        this.ontologyManager = OWLManager.createOWLOntologyManager();
        this.ontology = this.ontologyManager.loadOntologyFromOntologyDocument(new File(ontologyPath));
        this.factory = new MyFactory(ontology);
    }

    public ScenarioReader(MyFactory factory){
        this.factory = factory;
    }

    public MyFactory getFactory() {
        return factory;
    }

    public Model getModel(int scenarioNumber) {
        Scenario scenario = getScenarioFromOntology(scenarioNumber);
        System.out.println(scenario);
        if (scenario == null){
            throw new IllegalArgumentException("No scenario with number " + scenarioNumber);
        }

        Weather weather = getWeatherFromOntology(scenarioNumber);
        Time time = getTimeFromOntology(scenarioNumber);
        Road_type roadType = getRoadTypeFromOntology(scenarioNumber);
        Vehicle mainVehicle = getVehicleFromOntology(scenarioNumber);
        Driver driver = getDriverFromOntology(scenarioNumber);

        ArrayList<Passenger> passengers = new ArrayList<>();
        Map<Model.Side, ArrayList<Surrounding>> surrounding = getSurroundingFromScenario(scenario);

        Map<Model.Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
        Map<Lane, ArrayList<Living_entity>> entities = new HashMap<>();
        Map<Lane, ArrayList<Non_living_entity>> objects = new HashMap<>();
        Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();

        int lanesCount = 0;
        int leftLanesCount = 0;
        int rightLanesCount = 0;
        Iterator<? extends Integer> iterator = roadType.getLanes_count().iterator();
        while (iterator.hasNext()) {
            lanesCount = (int) iterator.next();
        }
        iterator = roadType.getLeft_lanes_count().iterator();
        while (iterator.hasNext()) {
            leftLanesCount = (int) iterator.next();
        }
        rightLanesCount = lanesCount - leftLanesCount - 1;

        Lane lane_0 = getLane0FromOntology(scenarioNumber);
        entities.put(lane_0, new ArrayList<Living_entity>() {
        });
        objects.put(lane_0, new ArrayList<Non_living_entity>() {
        });
        vehicles.put(lane_0, new ArrayList<Vehicle>() {
        });
        TreeMap<Integer, Lane> lane_center = new TreeMap<>() {{
            put(0, lane_0);
        }};

        for(Vehicle v: lane_0.getLane_has_vehicle()) {
            String vehicleName = v.getOwlIndividual().getIRI().toString();
            if (!vehicleName.equals(mainVehicle.getOwlIndividual().getIRI().toString())) {
                v = getVehicleAsSpecificClass(vehicleName);
                vehicles.get(lane_0).add(v);
            }
        }

        for(Living_entity entity: lane_0.getLane_has_pedestrian()){
            String entityName = entity.getOwlIndividual().getIRI().toString();
            entity = getEntityAsSpecificClass(entityName);
            entities.get(lane_0).add(entity);
        }

        for(Non_living_entity object: lane_0.getLane_has_object()){
            String objectName = object.getOwlIndividual().getIRI().toString();
            object = getObjectAsSpecificClass(objectName);
            objects.get(lane_0).add(object);
        }

        lanes.put(Model.Side.CENTER, lane_center);


        TreeMap<Integer, Lane> lanes_right = new TreeMap<>();
        for (int i = 1; i <= rightLanesCount; i++) {
            Lane lane = getLaneRightFromOntology(scenarioNumber, i);
            lanes_right.put(i, lane);
            entities.put(lane, new ArrayList<Living_entity>() {
            });
            objects.put(lane, new ArrayList<Non_living_entity>() {
            });
            vehicles.put(lane, new ArrayList<Vehicle>() {
            });

            for(Vehicle v: lane.getLane_has_vehicle()) {
                String vehicleName = v.getOwlIndividual().getIRI().toString();
                if (!vehicleName.equals(mainVehicle.getOwlIndividual().getIRI().toString())) {
                    v = getVehicleAsSpecificClass(vehicleName);
                    vehicles.get(lane).add(v);
                }
            }

            for(Living_entity entity: lane.getLane_has_pedestrian()){
                String entityName = entity.getOwlIndividual().getIRI().toString();
                entity = getEntityAsSpecificClass(entityName);
                entities.get(lane).add(entity);
            }

            for(Non_living_entity object: lane.getLane_has_object()){
                String objectName = object.getOwlIndividual().getIRI().toString();
                object = getObjectAsSpecificClass(objectName);
                objects.get(lane).add(object);
            }
        }
        lanes.put(Model.Side.RIGHT, lanes_right);

        // left lanes
        TreeMap<Integer, Lane> lanes_left = new TreeMap<>();
        for (int i = 1; i <= leftLanesCount; i++) {
            Lane lane = getLaneLeftFromOntology(scenarioNumber, i);
            lanes_left.put(i, lane);
            entities.put(lane, new ArrayList<Living_entity>() {
            });
            objects.put(lane, new ArrayList<Non_living_entity>() {
            });
            vehicles.put(lane, new ArrayList<Vehicle>() {
            });

            for(Vehicle v: lane.getLane_has_vehicle()) {
                String vehicleName = v.getOwlIndividual().getIRI().toString();
                if (!vehicleName.equals(mainVehicle.getOwlIndividual().getIRI().toString())) {
                    v = getVehicleAsSpecificClass(vehicleName);
                    vehicles.get(lane).add(v);
                }
            }

            for(Living_entity entity: lane.getLane_has_pedestrian()){
                String entityName = entity.getOwlIndividual().getIRI().toString();
                entity = getEntityAsSpecificClass(entityName);
                entities.get(lane).add(entity);
            }

            for(Non_living_entity object: lane.getLane_has_object()){
                String objectName = object.getOwlIndividual().getIRI().toString();
                object = getObjectAsSpecificClass(objectName);
                objects.get(lane).add(object);
            }
        }
        lanes.put(Model.Side.LEFT, lanes_left);

        Model model = new Model.Builder().
                setScenario(scenario).
                setWeather(weather).
                setRoadType(roadType).
                setLanes(lanes).
                setTime(time).
                setDriver(driver).
                setVehicle(mainVehicle).
                setPassengers(passengers).
                setVehicles(vehicles).
                setEntities(entities).
                setObjects(objects).
                setSurrounding(surrounding).build();

        Visualization.getImage(model);

        return model;
    }

    private Scenario getScenarioFromOntology(int number) {
        return factory.getScenario(IRI_PREFIX + String.valueOf(number) + "_scenario");
    }

    private Weather getWeatherFromOntology(int number) {
        return factory.getWeather(IRI_PREFIX + String.valueOf(number) + "_weather");
    }

    private Time getTimeFromOntology(int number) {
        return factory.getTime(IRI_PREFIX + String.valueOf(number) + "_time");
    }

    private Road_type getRoadTypeFromOntology(int number) {
        return factory.getRoad_type(IRI_PREFIX + String.valueOf(number) + "_road_type");
    }

    private Vehicle getVehicleFromOntology(int number) {
        return factory.getVehicle(IRI_PREFIX + String.valueOf(number) + "_vehicle_main");
    }

    private Driver getDriverFromOntology(int number) {
        return factory.getDriver(IRI_PREFIX + String.valueOf(number) + "_driver");
    }

    private void addSurroundingToList(List <Surrounding> surroundingList, Scenario scenario, Model.Side side){
        Collection<? extends Surrounding> surrounding = null;
        if (side == Model.Side.LEFT)
            surrounding = scenario.getHas_surrounding_left();
        else
            surrounding = scenario.getHas_surrounding_right();

        for (Surrounding s: surrounding){
            String surroundingName = s.getOwlIndividual().getIRI().toString();
            s = getSurroundingAsSpecificClass(surroundingName);
            surroundingList.add(s);
        }
    }

    private Map<Model.Side, ArrayList<Surrounding>> getSurroundingFromScenario(Scenario scenario) {
        Map<Model.Side, ArrayList<Surrounding>> surrounding = new HashMap<>();
        ArrayList<Surrounding> left_surrounding = new ArrayList<>();
        ArrayList<Surrounding> right_surrounding = new ArrayList<>();

        addSurroundingToList(left_surrounding, scenario, Model.Side.LEFT);
        addSurroundingToList(right_surrounding, scenario, Model.Side.RIGHT);

        surrounding.put(Model.Side.LEFT, left_surrounding);
        surrounding.put(Model.Side.RIGHT, right_surrounding);
        return surrounding;
    }

    private Lane getLane0FromOntology(int number){
        return factory.getLane(IRI_PREFIX + String.valueOf(number) + "_lane_0");
    }

    private Lane getLaneLeftFromOntology(int number, int laneNumber){
        return factory.getLane(IRI_PREFIX +String.valueOf(number)+"_lane_left_"+laneNumber);
    }

    private Lane getLaneRightFromOntology(int number, int laneNumber){
        return factory.getLane(IRI_PREFIX +String.valueOf(number)+"_lane_right_"+laneNumber);
    }

    public static void main(String[] args) throws OWLOntologyCreationException {
        ScenarioReader scenarioReader = new ScenarioReader();
        scenarioReader.getModel(230);
    }

    private Vehicle getVehicleAsSpecificClass(String vehicleName){
        Vehicle v = factory.getVehicle(vehicleName);

        if (factory.getTruck(vehicleName) != null)
            v = factory.getTruck(vehicleName);
        else if (factory.getBicycle(vehicleName) != null)
            v = factory.getBicycle(vehicleName);
        else if (factory.getMotorcycle(vehicleName) != null)
            v = factory.getMotorcycle(vehicleName);
        else if (factory.getCar(vehicleName) != null)
            v = factory.getCar(vehicleName);

        return v;
    }

    private Surrounding getSurroundingAsSpecificClass(String surroundingName){
        Surrounding s = factory.getSurrounding(surroundingName);

        if (factory.getBushes(surroundingName) != null)
            s = factory.getBushes(surroundingName);
        else if (factory.getCycling_path(surroundingName) != null)
            s = factory.getCycling_path(surroundingName);
        else if (factory.getDitch(surroundingName) != null)
            s = factory.getDitch(surroundingName);
        else if (factory.getEdge(surroundingName) != null)
            s = factory.getEdge(surroundingName);
        else if (factory.getField(surroundingName) != null)
            s = factory.getField(surroundingName);
        else if (factory.getForest(surroundingName) != null)
            s = factory.getForest(surroundingName);
        else if (factory.getNoise_barrier(surroundingName) != null)
            s = factory.getNoise_barrier(surroundingName);
        else if (factory.getPavement(surroundingName) != null)
            s = factory.getPavement(surroundingName);
        else if (factory.getRailway(surroundingName) != null)
            s = factory.getRailway(surroundingName);
        else if (factory.getRoad_sign_post(surroundingName) != null)
            s = factory.getRoad_sign_post(surroundingName);
        else if (factory.getSecurity_side_barrier(surroundingName) != null)
            s = factory.getSecurity_side_barrier(surroundingName);
        else if (factory.getStreet_lamp(surroundingName) != null)
            s = factory.getStreet_lamp(surroundingName);
        else if (factory.getTree(surroundingName) != null)
            s = factory.getTree(surroundingName);
        else if (factory.getWall(surroundingName) != null)
            s = factory.getWall(surroundingName);

        return s;
    }

    private Living_entity getEntityAsSpecificClass(String entityName){
        Living_entity e = factory.getLiving_entity(entityName);
        if (factory.getPerson(entityName) != null)
            e = factory.getPerson(entityName);
        else if (factory.getStock(entityName) != null)
            e = factory.getStock(entityName);
        else if (factory.getPet(entityName) != null)
            e = factory.getPet(entityName);
        else if (factory.getWild(entityName) != null)
            e = factory.getWild(entityName);

        return e;
    }

    private Non_living_entity getObjectAsSpecificClass(String objectName){
        Non_living_entity object = factory.getNon_living_entity(objectName);
        if( factory.getConcrete_barrier(objectName) != null)
            object = factory.getConcrete_barrier(objectName);
        else if (factory.getPlastic_barrier(objectName) != null)
            object = factory.getPlastic_barrier(objectName);
        else if (factory.getRock(objectName) != null)
            object = factory.getRock(objectName);
        else if (factory.getStreet_tidy(objectName) != null)
            object = factory.getStreet_tidy(objectName);
        else if (factory.getPedestrian_crossing(objectName) != null)
            object = factory.getPedestrian_crossing(objectName);
        else if (factory.getSpeed_bump(objectName) != null)
            object = factory.getSpeed_bump(objectName);

        return object;
    }
}
