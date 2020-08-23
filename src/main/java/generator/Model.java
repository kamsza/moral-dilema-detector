package generator;

import project.Action;
import project.Decision;
import project.Driver;
import project.Entity;
import project.Lane;
import project.Living_entity;
import project.Non_living_entity;
import project.Passenger;
import project.Road_type;
import project.Scenario;
import project.Surrounding;
import project.Time;
import project.Vehicle;
import project.Weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Model {
    private Scenario scenario;

    private Weather weather;
    private Time time;
    private Road_type roadType;
    private Driver driver;
    private Vehicle vehicle;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Map<Side, Surrounding> surrounding = new HashMap<>();
    private Map<Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
    private Map<Lane, ArrayList<Living_entity>> entities = new HashMap<>();
    private Map<Lane, ArrayList<Non_living_entity>> objects = new HashMap<>();
    private Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();
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

    public Map<Side, TreeMap<Integer, Lane>> getLanes() {
        return lanes;
    }

    public void setLanes(Map<Side, TreeMap<Integer, Lane>> lanes) {
        this.lanes = lanes;
    }

    public Map<Lane, ArrayList<Living_entity>> getEntities() {
        return entities;
    }

    public void setEntities(Map<Lane, ArrayList<Living_entity>> entities) {
        this.entities = entities;
    }

    public Map<Lane, ArrayList<Non_living_entity>> getObjects() {
        return objects;
    }

    public void setObjects(Map<Lane, ArrayList<Non_living_entity>> objects) {
        this.objects = objects;
    }

    public Map<Lane, ArrayList<Vehicle>> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<Lane, ArrayList<Vehicle>> vehicles) {
        this.vehicles = vehicles;
    }

    public Map<Decision, Action> getActionByDecision() {
        return actionByDecision;
    }

    public void setActionByDecision(Map<Decision, Action> actionByDecision) {
        this.actionByDecision = actionByDecision;
    }

    public enum Side {LEFT, CENTER, RIGHT}
}