package commonadapter.adapters.waymo.logic.services;

import adapter.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.CommunicationUtils;

public class IceProxyService {

    private Communicator communicator;
    private ManagerPrx managerPrx;

    public IceProxyService() {

        try {

            initializeConnection();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ScenarioPrx createScenarioPrx() {

        String itemId = managerPrx.create(ItemType.SCENARIO);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return ScenarioPrx.checkedCast(basePrx);
    }

    public ScenarioPrx getScenarioPrx(String itemId) {

        String loadedItemId = managerPrx.load(itemId, ItemType.SCENARIO);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(loadedItemId));
        return ScenarioPrx.checkedCast(basePrx);
    }

    public PedestrianPrx createPedestrianPrx() {

        String itemId = managerPrx.create(ItemType.PEDESTRIAN);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return PedestrianPrx.checkedCast(basePrx);
    }

    public PedestrianPrx getPedestrianPrx(String itemId) {

        String loadedItemId = managerPrx.load(itemId, ItemType.PEDESTRIAN);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(loadedItemId));
        return PedestrianPrx.checkedCast(basePrx);
    }

    public VehiclePrx createVehiclePrx() {

        String itemId = managerPrx.create(ItemType.VEHICLE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return VehiclePrx.checkedCast(basePrx);
    }

    public VehiclePrx getVehiclePrx(String itemId) {

        String loadedItemId = managerPrx.load(itemId, ItemType.VEHICLE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(loadedItemId));
        return VehiclePrx.checkedCast(basePrx);
    }

    public CyclistPrx createCyclistPrx() {

        String itemId = managerPrx.create(ItemType.CYCLIST);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return CyclistPrx.checkedCast(basePrx);
    }

    public CyclistPrx getCyclistPrx(String itemId) {

        String loadedItemId = managerPrx.load(itemId, ItemType.CYCLIST);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(loadedItemId));
        return CyclistPrx.checkedCast(basePrx);
    }

    public LanePrx createLanePrx() {

        String itemId = managerPrx.create(ItemType.LANE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return LanePrx.checkedCast(basePrx);
    }

    public LanePrx getLanePrx(String itemId) {

        String loadedItemId = managerPrx.load(itemId, ItemType.LANE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(loadedItemId));
        return LanePrx.checkedCast(basePrx);
    }

    public void persistOntologyChanges() {

        managerPrx.persist();
    }

    private void initializeConnection() {

        communicator = Util.initialize(new String[0]);

        ObjectPrx base = communicator.stringToProxy(CommunicationUtils.getInternetAddress("manager"));

        this.managerPrx = ManagerPrx.checkedCast(base);
    }

}
