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


    public PedestrianPrx createPedestrianPrx() {

        String itemId = managerPrx.create(ItemType.PEDESTRIAN);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return PedestrianPrx.checkedCast(basePrx);
    }

    public VehiclePrx createVehiclePrx() {

        String itemId = managerPrx.create(ItemType.VEHICLE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return VehiclePrx.checkedCast(basePrx);
    }

    public CyclistPrx createCyclistPrx() {

        String itemId = managerPrx.create(ItemType.CYCLIST);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return CyclistPrx.checkedCast(basePrx);
    }

    public LanePrx createLanePrx() {

        String itemId = managerPrx.create(ItemType.LANE);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return LanePrx.checkedCast(basePrx);
    }

    public RoadPrx createRoadPrx() {

        String itemId = managerPrx.create(ItemType.ROAD);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(itemId));
        return RoadPrx.checkedCast(basePrx);
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
