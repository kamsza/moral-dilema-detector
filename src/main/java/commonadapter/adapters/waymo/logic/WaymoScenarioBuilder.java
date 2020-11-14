package commonadapter.adapters.waymo.logic;

import adapter.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.CommunicationUtils;
import commonadapter.adapters.waymo.logic.lidardata.Label;
import commonadapter.adapters.waymo.logic.lidardata.LidarView;
import commonadapter.adapters.waymo.logic.services.IceProxyService;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;
import org.swrlapi.drools.owl.individuals.I;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WaymoScenarioBuilder {

    private String waymoJsonFilePath;
    private IceProxyService proxyService;

    public WaymoScenarioBuilder(String waymoJsonFilePath) {

        this.waymoJsonFilePath = waymoJsonFilePath;
        this.proxyService = new IceProxyService();
    }

    public void createScenario()  {

        ScenarioPrx scenarioPrx = proxyService.createScenarioPrx();

        try {
            List<LidarView> lidarViews = getDeserializedLidarViews(waymoJsonFilePath);
            lidarViews.stream()
                    .flatMap(lidarView -> lidarView.labels.stream())
                    .forEach(label -> addEntityBasedOnLabel(scenarioPrx, label));

            proxyService.persistOntologyChanges();

            Logger.printLogMessage("CREATED SCENARIO ID = " + scenarioPrx.getId(), LogMessageType.INFO);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    private void addMainVehicleToScenario(ScenarioPrx scenarioPrx) {

        VehiclePrx mainVehiclePrx = proxyService.createVehiclePrx();

    }

    private void addEntityBasedOnLabel(ScenarioPrx scenarioPrx, Label label) {

        EntityPrx entityPrx = null;

        switch (label.type) {

            case TYPE_PEDESTRIAN:
                entityPrx = addPedestrianToScenario(scenarioPrx);
                break;
            case TYPE_VEHICLE:
                entityPrx = addVehicleToScenario(scenarioPrx);
                break;
            case TYPE_CYCLIST:
                entityPrx = addCyclistToScenario(scenarioPrx);
                break;
        }

        assignLane(entityPrx, label);
        assignDataProperties(entityPrx, label);
    }

    private PedestrianPrx addPedestrianToScenario(ScenarioPrx scenarioPrx) {

        PedestrianPrx pedestrianPrx = proxyService.createPedestrianPrx();
        scenarioPrx.addPedestrian(pedestrianPrx.getId());

        return pedestrianPrx;
    }

    private VehiclePrx addVehicleToScenario(ScenarioPrx scenarioPrx) {

        VehiclePrx vehiclePrx = proxyService.createVehiclePrx();
        scenarioPrx.addVehicle(vehiclePrx.getId());

        return vehiclePrx;
    }

    private CyclistPrx addCyclistToScenario(ScenarioPrx scenarioPrx) {

        CyclistPrx cyclistPrx = proxyService.createCyclistPrx();
        scenarioPrx.addCyclist(cyclistPrx.getId());

        return cyclistPrx;
    }

    private void assignLane(EntityPrx entityPrx, Label label) { // TODO

        LanePrx lanePrx = proxyService.createLanePrx();
        entityPrx.setLane(lanePrx.getId());
    }

    private void assignDataProperties(EntityPrx entityPrx, Label label) {

        entityPrx.setAccelerationX(label.metadata.accelX.floatValue());
        entityPrx.setAccelerationY(label.metadata.accelY.floatValue());
        entityPrx.setSpeedX(label.metadata.speedX.floatValue());
        entityPrx.setSpeedY(label.metadata.speedY.floatValue());

        entityPrx.setWidth(label.box.width.floatValue());
        entityPrx.setLength(label.box.length.floatValue());
        entityPrx.setDistance(label.box.centerX.floatValue());

    }

    private List<LidarView> getDeserializedLidarViews(String jsonFilePath) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<List<LidarView>>(){});
    }

    private Label getMainVehicleArtificialLabel() {
        return new Label();
    }

}
