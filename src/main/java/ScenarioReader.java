import DilemmaDetector.Simulator.Vector2;
import generator.Model;
import generator.ObjectNamer;
import generator.RandomPositioner;
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
    public static final String name = "http://www.w3.org/2003/11/";

    public ScenarioReader() throws OWLOntologyCreationException {
        this.ontologyManager = OWLManager.createOWLOntologyManager();
        this.ontology = this.ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));
        this.factory = factory = new MyFactory(ontology);
    }


    public Model getModel(int number ) {
        Scenario scenario = factory.getScenario(name + String.valueOf(number) + "_scenario");
        Weather weather = factory.getWeather(name + String.valueOf(number) + "_weather");
        Time time = factory.getTime(name + String.valueOf(number) + "_time");
        Road_type roadType = factory.getRoad_type(name + String.valueOf(number) + "_road_type");
        Vehicle vehicle = factory.getVehicle(name + String.valueOf(number) + "_vehicle_main");
        Driver driver = factory.getDriver(name + String.valueOf(number) + "_driver");
        ArrayList<Passenger> passengers = new ArrayList<>();
        Map<Model.Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
        Map<Model.Side, ArrayList<Surrounding>> surrounding = new HashMap<>();
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


        Lane lane_0 = factory.getLane(name + String.valueOf(number) + "_lane_0");
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
            Lane lane = factory.getLane(name + String.valueOf(number) + "_lane_right_" + i);
//            Lane lane = factory.createLane(ObjectNamer.getName("lane_right_" + i));
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
            Lane lane = factory.getLane(name + String.valueOf(number) + "_lane_left_" + i);
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
            if (!v.getOwlIndividual().getIRI().toString().contains("main_vehicle")) {
                Lane lane = null;
                for (Lane l : vehicle.getOn_lane()) {
                    lane = l;
                }
                vehicles.get(lane).add(v);
            }
        }


        ArrayList<Surrounding> left_surrounding = new ArrayList<>();
        ArrayList<Surrounding> right_surrounding = new ArrayList<>();

        left_surrounding.addAll(scenario.getHas_surrounding_left());
        right_surrounding.addAll(scenario.getHas_surrounding_right());

        surrounding.put(Model.Side.LEFT, left_surrounding);
        surrounding.put(Model.Side.RIGHT, right_surrounding);

        Model model = new Model();
        model.setScenario(scenario);
        model.setWeather(weather);
        model.setRoadType(roadType);
        model.setLanes(lanes);
        model.setTime(time);
        model.setDriver(driver);
        model.setVehicle(vehicle);
        model.setPassengers(passengers);
        model.setVehicles(vehicles);
        model.setEntities(entities);
        model.setObjects(objects);
        model.setSurrounding(surrounding);
        Visualization.getImage(model);
        return model;
    }

    public static void main(String[] args) throws OWLOntologyCreationException {
        ScenarioReader scenarioReader = new ScenarioReader();
        scenarioReader.getModel(196);
    }


}
