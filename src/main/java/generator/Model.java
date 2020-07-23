package generator;

import project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    public enum Side {LEFT, CENTER, RIGHT};
    private Scenario scenario;
    private Weather weather;
    private Time time;
    private Road_type roadType;
    private Driver driver;
    private Vehicle vehicle;

    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Map<Side, Surrounding> surrounding = new HashMap<>();
    private Map<Side, Map<Integer, Lane>> lanes = new HashMap<>();
    private Map<Lane, ArrayList<Entity>> entities = new HashMap<>();
    private Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();
    private Map<Lane, ArrayList<Pedestrian>> pedestrians = new HashMap<>();
    private Map<Lane, ArrayList<Animal>> animals = new HashMap<>();
    private Map<Decision, Action> actionByDecision = new HashMap<>();

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
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

    public Road_type getRoadType() {
        return roadType;
    }

    public void setRoadType(Road_type roadType) {
        this.roadType = roadType;
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

    public Map<Side, Surrounding> getSurrounding() {
        return surrounding;
    }

    public void setSurrounding(Map<Side, Surrounding> surrounding) {
        this.surrounding = surrounding;
    }

    public Map<Side, Map<Integer, Lane>> getLanes() {
        return lanes;
    }

    public void setLanes(Map<Side, Map<Integer, Lane>> lanes) {
        this.lanes = lanes;
    }

    public Map<Lane, ArrayList<Entity>> getEntities() {
        return entities;
    }

    public void setEntities(Map<Lane, ArrayList<Entity>> entities) {
        this.entities = entities;
    }

    public Map<Lane, ArrayList<Vehicle>> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<Lane, ArrayList<Vehicle>> vehicles) {
        this.vehicles = vehicles;
    }

    public Map<Lane, ArrayList<Pedestrian>> getPedestrians() {
        return pedestrians;
    }

    public void setPedestrians(Map<Lane, ArrayList<Pedestrian>> pedestrians) {
        this.pedestrians = pedestrians;
    }

    public Map<Lane, ArrayList<Animal>> getAnimals() {
        return animals;
    }

    public void setAnimals(Map<Lane, ArrayList<Animal>> animals) {
        this.animals = animals;
    }

    public Map<Decision, Action> getActionByDecision() {
        return actionByDecision;
    }

    public void setActionByDecision(Map<Decision, Action> actionByDecision) {
        this.actionByDecision = actionByDecision;
    }
}
