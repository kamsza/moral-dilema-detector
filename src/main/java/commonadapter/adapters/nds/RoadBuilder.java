package commonadapter.adapters.nds;

import adapter.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.adapters.lane.LaneTile;
import commonadapter.adapters.lane.attrMaps.Feature;
import commonadapter.adapters.nds.routing.RoutingTile;
import commonadapter.adapters.nds.routing.fixedAttributes.AttributeData;
import commonadapter.adapters.nds.routing.fixedAttributes.RoutingAttr;
import commonadapter.adapters.nds.routing.fixedAttributes.SharedAttr;
import commonadapter.adapters.waymo.CommunicationUtils;

import java.io.File;
import java.io.IOException;

public class RoadBuilder {

    private Communicator communicator;
    private ManagerPrx managerPrx;
    private RoadPrx roadPrx;
    private RoadAttributesPrx roadAttributesPrx;

    public RoadBuilder(String[] args) {

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
        //new RoadBuilder(args).buildRoad("src\\main\\resources\\nds\\routing\\routingTile_545554860.json");
        new RoadBuilder(args).buildRoad("src\\main\\resources\\nds\\lane\\laneTile_545555000.json");
    }

    public void buildRoad(String jsonFilePath)  {

        String roadId = managerPrx.create(ItemType.ROAD);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(roadId));
        roadPrx = RoadPrx.checkedCast(basePrx);

        try {
           // RoutingTile routingTile = getDeserializedRoutingTile(jsonFilePath);
            LaneTile laneTile = getDeserializedLaneTile(jsonFilePath);
//            routingTile
//                    .fixedRoadAttributeSetList
//                    .attributeList
//                    .data
//                    .forEach(this::addRoadAttributes);
//
//            managerPrx.persist();
            System.out.println("CREATED ROAD ID = " + roadId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private RoutingTile getDeserializedRoutingTile(String jsonFilePath) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<RoutingTile>(){});
    }

    private LaneTile getDeserializedLaneTile(String jsonFilePath) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<LaneTile>(){});
    }

    private void addRoadAttributes(AttributeData attributeData) {

        String roadAttributesId = managerPrx.create(ItemType.ROADATTRIBUTES);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(roadAttributesId));
        roadAttributesPrx = RoadAttributesPrx.checkedCast(basePrx);

        roadPrx.setRoadAttributes(roadAttributesId);

        assignSharedAttributes(attributeData.sharedAttr);
        assignRoutingAttributes(attributeData.routingAttr);

    }

    private void assignSharedAttributes(SharedAttr sharedAttr) {

        roadAttributesPrx.setFerry(sharedAttr.ferry);
        roadAttributesPrx.setTunnel(sharedAttr.tunnel);
        roadAttributesPrx.setBridge(sharedAttr.bridge);
        roadAttributesPrx.setToll(!sharedAttr.toll.equals("IN_NO_DIRECTION"));
        roadAttributesPrx.setControlledAccess(sharedAttr.controlledAccess);
        roadAttributesPrx.setServiceArea(sharedAttr.serviceArea);
    }

    private void assignRoutingAttributes(RoutingAttr routingAttr) {

        roadAttributesPrx.setUrban(routingAttr.urban);
        roadAttributesPrx.setMotorway(routingAttr.motorway);
    }


}
