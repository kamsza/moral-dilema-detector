package commonadapter.adapters.waymo;

import adapter.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.adapters.waymo.lidardata.Label;
import commonadapter.adapters.waymo.lidardata.LidarView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScenarioBuilder {

    private Communicator communicator;
    private ManagerPrx managerPrx;
    private ScenarioPrx scenarioPrx;

    public ScenarioBuilder(String[] args) {

        try {
            initializeConnection(args);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initializeConnection(String[] args) {

        communicator = Util.initialize(args);

        ObjectPrx base = communicator.stringToProxy("factory/factory1:tcp -h localhost -p 10000");

        this.managerPrx = ManagerPrx.checkedCast(base);
    }


    public static void main(String[] args) {
        new ScenarioBuilder(args).buildScenario("src\\main\\resources\\waymo\\waymo-projected-lidar-labels-short.json");
    }

    public void buildScenario(String jsonFilePath)  {

        String scenarioId = managerPrx.create(ItemType.SCENARIO);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(scenarioId));
        scenarioPrx = ScenarioPrx.checkedCast(basePrx);

        try {
            List<LidarView> lidarViews = getDeserializedLidarViews(jsonFilePath);
            lidarViews.stream()
                    .flatMap(lidarView -> lidarView.labels.stream())
                    .forEach(this::addEntityBasedOnLabel);

            managerPrx.persist();

            System.out.println("CREATED SCENARIO ID = " + scenarioId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void addEntityBasedOnLabel(Label label) {

        String entityId = null;

        switch (label.type) {

            case TYPE_PEDESTRIAN:
                entityId = addPedestrian();
                break;
            case TYPE_VEHICLE:
                entityId = addVehicle();
                break;
            case TYPE_CYCLIST:
                entityId = addCyclist();
                break;
        }

        assignLane(entityId, label);
        assignDataProperties(entityId, label);
    }

    private String addPedestrian() {

        String pedestrianId = managerPrx.create(ItemType.PEDESTRIAN);
        scenarioPrx.addPedestrian(pedestrianId);

        return pedestrianId;
    }

    private String addVehicle() {

        String vehicleId = managerPrx.create(ItemType.VEHICLE);
        scenarioPrx.addVehicle(vehicleId);

        return vehicleId;
    }

    private String addCyclist() {

        String cyclistId = managerPrx.create(ItemType.CYCLIST);
        scenarioPrx.addCyclist(cyclistId);

        return cyclistId;
    }

    private void assignLane(String entityId, Label label) { // TODO

        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(entityId));
        EntityPrx entityPrx = EntityPrx.checkedCast(basePrx);

        String laneId = managerPrx.create(ItemType.LANE);
        scenarioPrx.addLane(laneId);

        entityPrx.setLane(laneId);
    }

    private void assignDataProperties(String entityId, Label label) {

        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(entityId));
        EntityPrx entityPrx = EntityPrx.checkedCast(basePrx);

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

}
