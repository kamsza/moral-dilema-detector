package generator;

import project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RoadModel {

    private Road road;
    private Road_type roadType;
    private Map<Model.Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();
    private Map<Lane, ArrayList<Living_entity>> entities = new HashMap<>();
    private Map<Lane, ArrayList<Non_living_entity>> objects = new HashMap<>();
    private Map<Lane, ArrayList<Vehicle>> vehicles = new HashMap<>();

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Road_type getRoadType() {
        return roadType;
    }

    public void setRoadType(Road_type roadType) {
        this.roadType = roadType;
    }

    public Map<Model.Side, TreeMap<Integer, Lane>> getLanes() {
        return lanes;
    }

    public void setLanes(Map<Model.Side, TreeMap<Integer, Lane>> lanes) {
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

    @Override
    public String toString() {
        return "RoadModel{" +
                "road=" + road +
                ", roadType=" + roadType +
                ", lanes=" + lanes +
                ", entities=" + entities +
                ", objects=" + objects +
                ", vehicles=" + vehicles +
                '}';
    }
}
