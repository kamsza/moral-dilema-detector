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
        this.ontologyManager = OWLManager.createOWLOntologyManager();
        this.ontology = this.ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));
        this.factory = new MyFactory(ontology);
    }

    public Model getModel(int scenarioNumber) {
        Scenario scenario = getScenarioFromOntology(scenarioNumber);
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
        rightLanesCount = lanesCount - leftLanesCount;

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
        }
        lanes.put(Model.Side.LEFT, lanes_left);

        for (Vehicle v : scenario.getHas_vehicle()) {
            if (!v.getOwlIndividual().getIRI().toString().contains("vehicle_main")) {
                Lane lane = null;
                for (Lane l : v.getIs_on_lane()) {
                    lane = l;
                }
                vehicles.get(lane).add(v);
            }
        }

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

    private Map<Model.Side, ArrayList<Surrounding>> getSurroundingFromScenario(Scenario scenario) {
        Map<Model.Side, ArrayList<Surrounding>> surrounding = new HashMap<>();
        ArrayList<Surrounding> left_surrounding = new ArrayList<>();
        ArrayList<Surrounding> right_surrounding = new ArrayList<>();

        left_surrounding.addAll(scenario.getHas_surrounding_left());
        right_surrounding.addAll(scenario.getHas_surrounding_right());

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
        scenarioReader.getModel(197);
    }


}
