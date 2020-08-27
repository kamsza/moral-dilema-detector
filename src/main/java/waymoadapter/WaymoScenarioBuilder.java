package waymoadapter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.*;
import waymoadapter.model.lidar.Label;

import java.io.File;

public class WaymoScenarioBuilder {

    private final MyFactory factory;

    private final Scenario scenario;

    private final Vehicle mainVehicle;

    private final Lane mainLane;

    private int nextVehicleId;

    private int nextPedestrianId;

    private int nextCyclistId;

    private final String versionTag;

    public WaymoScenarioBuilder(String ontologyFilePath, String resultFilePath, String versionTag) throws Exception {

        this.versionTag = versionTag;

        this.nextPedestrianId = 0;

        this.nextVehicleId = 0;

        this.nextCyclistId = 0;

        File original = new File(ontologyFilePath);
        File copied = new File(resultFilePath);

        com.google.common.io.Files.copy(original, copied);

        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(copied);

        this.factory = new MyFactory(ontology);

        // create scenario with one lane and main vehicle on it
        this.scenario = factory.createScenario(versionTag + "_waymo_scenario");
        this.mainLane = factory.createLane(versionTag + "_waymo_lane_0");
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

    public WaymoScenarioBuilder addPedestrian(Label label) {

        Pedestrian pedestrian = getNewPedestrianInstance();

        fillDataProperties(pedestrian, label);

        this.scenario.addHas_pedestrian(pedestrian);

        pedestrian.addIs_on_lane(this.mainLane); // TODO
                                                // temporarily each pedestrian is located on mainLane which
                                                // means that it is on the same lane as our autonomous vehicle

        return this;
    }

    private void fillDataProperties(Entity entity, Label label) {

        entity.addSpeedX(label.metadata.speedX.floatValue());
        entity.addSpeedY(label.metadata.speedY.floatValue());
        entity.addAccelerationX(label.metadata.accelX.floatValue());
        entity.addAccelerationY(label.metadata.accelY.floatValue());
        entity.addWidth(label.box.width.floatValue());
        entity.addLength(label.box.length.floatValue());

        // entity.addDistance(label.box.centerY);
    }

    public Scenario buildAndSave() throws OWLOntologyStorageException {
        this.factory.saveOwlOntology();
        return this.scenario;
    }
}
