package commonadapter.adapters.nds.services;

import adapter.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.CommunicationUtils;

public class IceProxyNds {

    private Communicator communicator;
    private ManagerPrx managerPrx;

    public IceProxyNds() {
        try {
            initializeConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public LanePrx createLanePrx() {
        String laneId = managerPrx.create(ItemType.LANE);
        ObjectPrx basePrx = getBasePrx(laneId);
        return LanePrx.checkedCast(basePrx);
    }

    public RoadPrx createRoadPrx() {
        String roadId = managerPrx.create(ItemType.ROAD);
        ObjectPrx basePrx = getBasePrx(roadId);
        return RoadPrx.checkedCast(basePrx);
    }

    public RoadPrx getRoadPrx(String roadId) {
        String loadedRoadId = managerPrx.load(roadId, ItemType.ROAD);
        ObjectPrx basePrx = getBasePrx(loadedRoadId);
        return RoadPrx.checkedCast(basePrx);
    }

    public JunctionPrx createJunctionPrx() {
        String junctionId = managerPrx.create(ItemType.JUNCTION);
        ObjectPrx basePrx = getBasePrx(junctionId);
        return JunctionPrx.checkedCast(basePrx);
    }

    public JunctionPrx getJunctionPrx(String junctionId) {
        String loadedJunctionId = managerPrx.create(ItemType.JUNCTION);
        ObjectPrx basePrx = getBasePrx(loadedJunctionId);
        return JunctionPrx.checkedCast(basePrx);
    }

    public RoadAttributesPrx createRoadAttributesPrx() {
        String roadAttributesId = managerPrx.create(ItemType.ROADATTRIBUTES);
        ObjectPrx basePrx = getBasePrx(roadAttributesId);
        return RoadAttributesPrx.checkedCast(basePrx);
    }

    public RoadAttributesPrx getRoadAttributesPrx(String roadAttrId) {
        String loadedRoadAttributesId = managerPrx.load(roadAttrId, ItemType.ROADATTRIBUTES);
        ObjectPrx basePrx = getBasePrx(loadedRoadAttributesId);
        return RoadAttributesPrx.checkedCast(basePrx);
    }

    public void persistOntologyChanges() {
        managerPrx.persist();
    }

    private void initializeConnection() {
        communicator = Util.initialize(new String[0]);
        ObjectPrx base = communicator.stringToProxy(CommunicationUtils.getInternetAddress("manager"));
        this.managerPrx = ManagerPrx.checkedCast(base);
    }

    private ObjectPrx getBasePrx(String id) {
        return communicator.stringToProxy(CommunicationUtils.getInternetAddress(id));
    }

}
