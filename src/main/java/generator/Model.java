package generator;

import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private Scenario scenario;
    private int lanesCount;
    private Weather weather;
    private Time time;
    private Driver driver;
    private Vehicle vehicle;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Map<Side, ArrayList<Surrounding>> surrounding = new HashMap<>();
    private Map<Decision, Action> actionByDecision = new HashMap<>();

    private Junction junction;
    private RoadModel mainRoad;
    private ArrayList<RoadModel> otherRoads = new ArrayList<>();


//    private Map<Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
//    private Map<Lane, ArrayList<Living_entity>> entities = new HashMap<>();
//    private Map<Lane, ArrayList<Non_living_entity>> objects = new HashMap<>();
//    private Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();


    private RandomPositioner randomPositioner;
    private SizeManager sizeManager = new SizeManager();

    public static final class Builder {
        private Scenario scenario;
        private int lanesCount;
        private Weather weather;
        private Time time;
        private Driver driver;
        private Vehicle vehicle;
        private ArrayList<Passenger> passengers = new ArrayList<>();
        private Map<Side, ArrayList<Surrounding>> surrounding = new HashMap<>();
        private Map<Decision, Action> actionByDecision = new HashMap<>();
        private Junction junction;
        private RoadModel mainRoad;
        private ArrayList<RoadModel> otherRoads = new ArrayList<>();

        private RandomPositioner randomPositioner;
        private SizeManager sizeManager = new SizeManager();


        public Builder setScenario(Scenario scenario) {
            this.scenario = scenario;
            return this;
        }

        public Builder setLanesCount(int lanesCount) {
            this.lanesCount = lanesCount;
            return this;
        }

        public Builder setWeather(Weather weather) {
            this.weather = weather;
            return this;
        }

        public Builder setTime(Time time) {
            this.time = time;
            return this;
        }

        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setPassengers(ArrayList<Passenger> passengers) {
            this.passengers = passengers;
            return this;
        }

        public Builder setSurrounding(Map<Side, ArrayList<Surrounding>> surrounding) {
            this.surrounding = surrounding;
            return this;
        }

        public Builder setActionByDecision(Map<Decision, Action> actionByDecision) {
            this.actionByDecision = actionByDecision;
            return this;
        }

        public Builder setJunction(Junction junction) {
            this.junction = junction;
            return this;
        }

        public Builder setMainRoad(RoadModel mainRoad) {
            this.mainRoad = mainRoad;
            return this;
        }

        public Builder setOtherRoads(ArrayList<RoadModel> otherRoads) {
            this.otherRoads = otherRoads;
            return this;
        }

        public Builder setRandomPositioner(RandomPositioner randomPositioner) {
            this.randomPositioner = randomPositioner;
            return this;
        }

        public Builder setSizeManager(SizeManager sizeManager) {
            this.sizeManager = sizeManager;
            return this;
        }

        public Model build() {
            if (scenario == null) {
                throw new IllegalArgumentException("Scenario cannot be empty");
            }

            Model model = new Model();
            model.scenario = this.scenario;

            model.lanesCount = this.lanesCount;
            model.weather = this.weather;
            model.time = this.time;
            model.driver = this.driver;
            model.vehicle = this.vehicle;
            model.passengers = this.passengers;
            model.surrounding = this.surrounding;
            model.actionByDecision = this.actionByDecision;
            model.junction = this.junction;
            model.mainRoad = this.mainRoad;
            model.otherRoads = this.otherRoads;

            model.randomPositioner = this.randomPositioner;
            model.sizeManager = this.sizeManager;
            return model;
        }
    }


    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public RandomPositioner getRandomPositioner() {
        return randomPositioner;
    }

    public void setRandomPositioner(RandomPositioner randomPositioner) {
        this.randomPositioner = randomPositioner;
    }

    public SizeManager getSizeManager() {
        return sizeManager;
    }

    public int getLanesCount() {
        return lanesCount;
    }

    public void setLanesCount(int lanesCount) {
        this.lanesCount = lanesCount;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Map<Side, ArrayList<Surrounding>> getSurrounding() {
        return surrounding;
    }

    public void setSurrounding(Map<Side, ArrayList<Surrounding>> surrounding) {
        this.surrounding = surrounding;
    }

    public Map<Decision, Action> getActionByDecision() {
        return actionByDecision;
    }

    public void setActionByDecision(Map<Decision, Action> actionByDecision) {
        this.actionByDecision = actionByDecision;
    }

    public Junction getJunction() {
        return junction;
    }

    public void setJunction(Junction junction) {
        this.junction = junction;
    }

    public RoadModel getMainRoad() {
        return mainRoad;
    }

    public void setMainRoad(RoadModel mainRoad) {
        this.mainRoad = mainRoad;
    }

    public ArrayList<RoadModel> getOtherRoads() {
        return otherRoads;
    }

    public void setOtherRoads(ArrayList<RoadModel> otherRoads) {
        this.otherRoads = otherRoads;
    }

    public void export() throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        export(false);
    }

    public void export(boolean overrideFile) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        String template = "src\\main\\resources\\ontologies\\traffic_ontology.owl";
        export(template, overrideFile);
    }

    public void export(String filepath, boolean overrideFile) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        String outDir = "src\\main\\resources\\ontologies";
        export(filepath, overrideFile, outDir);
    }


    public void export(String filepath, boolean overrideFile, String outputDir) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        MyFactory factory = MyFactorySingleton.getFactory();
        String exportFilepath = filepath;
        if (!overrideFile) {
            String extension = ".owl";
            String template = outputDir + "\\traffic_ontology";
            exportFilepath = template + extension;
            int index = 1;
            while (new File(exportFilepath).exists()) {
                exportFilepath = template + " (" + index + ")" + extension;
                index++;
            }
        }
        FileOutputStream outputStream = FileUtils.openOutputStream(new File(exportFilepath), false);
        factory.getOwlOntology().getOWLOntologyManager().saveOntology(factory.getOwlOntology(), outputStream);
    }

    public enum Side {LEFT, CENTER, RIGHT}

    @Override
    public String toString() {
        return "Model{" +
                "\nscenario=" + scenario +
                "\nweather=" + weather +
                "\ntime=" + time +
                "\ndriver=" + driver +
                "\nvehicle=" + vehicle +
                "\npassengers=" + passengers +
                "\nsurrounding=" + surrounding +
                "\njunction=" + junction +
                "\nmainRoad=" + mainRoad +
                "\notherRoads=" + otherRoads +
                '}';
    }
}