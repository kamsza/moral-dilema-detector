package waymoadapter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.*;
import waymoadapter.model.lidar.Label;

import java.io.File;

public class WaymoScenarioBuilder {

    private final OWLFactory factory;

    private final Scenario scenario;

    private final Vehicle mainVehicle;

    private final Lane mainLane;

    private int nextVehicleId;

    private int nextPedestrianId;

    private int nextCyclistId;

    private final String versionTag;

    private Map<Integer, Lane> lanes = new LinkedHashMap<>();

    public WaymoScenarioBuilder(String ontologyFilePath, String resultFilePath, String versionTag) throws Exception {

        this.versionTag = versionTag;

        this.nextPedestrianId = 0;

        this.nextVehicleId = 0;

        this.nextCyclistId = 0;

        this.nextLaneId = 0;

        File original = new File(ontologyFilePath);
        File copied = new File(resultFilePath);

        com.google.common.io.Files.copy(original, copied);

        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(copied);

        this.factory = new OWLFactory(ontology);

        // create scenario with one lane and main vehicle on it
        this.scenario = factory.createScenario(versionTag + "_waymo_scenario");
        this.mainLane = factory.createLane(versionTag + "_waymo_lane_0");
        lanes.put(0, this.mainLane);
        this.mainVehicle = factory.createVehicle(versionTag + "_waymo_vehicle_main");
        this.scenario.addHas_vehicle(this.mainVehicle);
        this.scenario.addHas_lane(this.mainLane);
        this.mainVehicle.addIs_on_lane(this.mainLane);

    }

    private Pedestrian getNewPedestrianInstance() {
        return factory.createPedestrian(versionTag + "_waymo_pedestrian_" + (nextPedestrianId++));
    }

    private Vehicle getNewVehicleInstance() {
        return factory.createVehicle(versionTag + "_waymo_vehicle_" + (nextVehicleId++));
    }

    private Cyclist getNewCyclistInstance() {
        return factory.createCyclist(versionTag + "_waymo_cyclist_" + (nextCyclistId++));
    }

    private Lane getNewLaneInstance(){
        return factory.createLane(versionTag + "_waymo_lane_" + (nextLaneId++));
    }


    public WaymoScenarioBuilder addPedestrian(Label label) {

        Pedestrian pedestrian = getNewPedestrianInstance();

        fillDataProperties(pedestrian, label);

        this.scenario.addHas_pedestrian(pedestrian);

        pedestrian.addIs_on_lane(checkOnWhichLane(pedestrian, label));
        return this;
    }


    public WaymoScenarioBuilder addVehicle(Label label){

        Vehicle vehicle = getNewVehicleInstance();

        fillDataProperties(vehicle, label);

        this.scenario.addHas_vehicle(vehicle);

        vehicle.addIs_on_lane(checkOnWhichLane(vehicle, label));

        return this;
    }

    public WaymoScenarioBuilder addCyclist(Label label){

        Cyclist cyclist = getNewCyclistInstance();

        fillDataProperties(cyclist, label);

        this.scenario.addHas_cyclist(cyclist);

        cyclist.addIs_on_lane(checkOnWhichLane(cyclist, label));

        return this;
    }

    private void fillDataProperties(Entity entity, Label label) {

        entity.addSpeedX(label.metadata.speedX.floatValue());
        entity.addSpeedY(label.metadata.speedY.floatValue());
        entity.addAccelerationX(label.metadata.accelX.floatValue());
        entity.addAccelerationY(label.metadata.accelY.floatValue());
        entity.addWidth(label.box.width.floatValue());
        entity.addLength(label.box.length.floatValue());
        entity.addDistance(label.box.centerY);
    }

    public Lane checkOnWhichLane(Entity entity, Label label){

        Integer laneSize = 300;

        if(label.box.centerX > -150 && label.box.centerX < 150){
            return lanes.get(0);
        }

        if(label.box.centerX > 150 && label.box.centerX < 450){
            Lane lane = getNewLaneInstance();
            lanes.put(1, lane);
            return lanes.get(1);
        }

        if(label.box.centerX > 450 && label.box.centerX < 750){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            lanes.put(1, lane);
            lanes.put(2, lane);
            return lanes.get(2);
        }

        if(label.box.centerX > 750 && label.box.centerX < 1050){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            lanes.put(1, lane);
            lanes.put(2, lane);
            lanes.put(3, lane);
            return lanes.get(3);
        }

        if(label.box.centerX > 1050 && label.box.centerX < 1350){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            lanes.put(1, lane);
            lanes.put(2, lane);
            lanes.put(3, lane);
            lanes.put(4, lane);
            return lanes.get(4);
        }

        if(label.box.centerX > 1350 && label.box.centerX < 1650){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            Lane lane5 = getNewLaneInstance();
            lanes.put(1, lane);
            lanes.put(2, lane);
            lanes.put(3, lane);
            lanes.put(4, lane);
            lanes.put(5, lane);
            return lanes.get(5);
        }

        if(label.box.centerX > 1650 && label.box.centerX < 1950){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            Lane lane5 = getNewLaneInstance();
            Lane lane6 = getNewLaneInstance();
            lanes.put(1, lane);
            lanes.put(2, lane);
            lanes.put(3, lane);
            lanes.put(4, lane);
            lanes.put(5, lane);
            lanes.put(6, lane);
            return lanes.get(6);
        }

        if(label.box.centerX > -450 && label.box.centerX < -150){
            Lane lane = getNewLaneInstance();
            lanes.add(-1, lane);
            return lanes.get(-1);
        }

        if(label.box.centerX > -750 && label.box.centerX < -450){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            lanes.put(-1, lane1);
            lanes.put(-2, lane2);
            return lanes.get(-2);
        }

        if(label.box.centerX > -1050 && label.box.centerX < -750){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            lanes.put(-1, lane1);
            lanes.put(-2, lane2);
            lanes.put(-3, lane3);
            return lanes.get(-3);
        }


        if(label.box.centerX > -1350 && label.box.centerX < -1050){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            lanes.put(-1, lane1);
            lanes.put(-2, lane2);
            lanes.put(-3, lane3);
            lanes.put(-4, lane4);
            return lanes.get(-4);
        }

        if(label.box.centerX > -1650 && label.box.centerX < -1350){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            Lane lane5 = getNewLaneInstance();
            lanes.put(-1, lane1);
            lanes.put(-2, lane2);
            lanes.put(-3, lane3);
            lanes.put(-4, lane4);
            lanes.put(-5, lane5);
            return lanes.get(-5);
        }

        if(label.box.centerX > -1950 && label.box.centerX < -1650){
            Lane lane1 = getNewLaneInstance();
            Lane lane2 = getNewLaneInstance();
            Lane lane3 = getNewLaneInstance();
            Lane lane4 = getNewLaneInstance();
            Lane lane5 = getNewLaneInstance();
            Lane lane6 = getNewLaneInstance();
            lanes.put(-1, lane1);
            lanes.put(-2, lane2);
            lanes.put(-3, lane3);
            lanes.put(-4, lane4);
            lanes.put(-5, lane5);
            lanes.put(-6, lane6);
            return lanes.get(-6);
        }
    }

    public Scenario buildAndSave() throws OWLOntologyStorageException {
        this.factory.saveOwlOntology();
        return this.scenario;
    }
}
