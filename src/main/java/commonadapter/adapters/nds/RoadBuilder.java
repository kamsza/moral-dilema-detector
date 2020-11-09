package commonadapter.adapters.nds;

import adapter.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import commonadapter.adapters.nds.lane.LaneTile;
import commonadapter.adapters.nds.routing.RoutingTile;
import commonadapter.adapters.nds.routing.fixedAttributes.AttributeData;
import commonadapter.adapters.nds.routing.fixedAttributes.RoutingAttr;
import commonadapter.adapters.nds.routing.fixedAttributes.SharedAttr;
import commonadapter.adapters.nds.routing.links.LinkData;
import commonadapter.adapters.nds.routing.simpleIntersection.IntersectionData;
import commonadapter.adapters.waymo.CommunicationUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoadBuilder {

    private Communicator communicator;
    private ManagerPrx managerPrx;
    private List<RoadPrx> roadPrxList = new ArrayList<>();
    private List<Set<IntersectionPoint>> roadPointsList = new ArrayList<>();
    private List<Coordinates> startOfRoadCoordinates = new ArrayList<>();
    Logger logger = Logger.getLogger(RoadBuilder.class.getName());
    private static final float maxAngleDegree = 360f;
    private static final float angleCoefficient = 64f;
    private static final double metresInOneDegree = 111000;

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
        new RoadBuilder(args).buildRoads("src\\main\\resources\\nds\\routing\\routingTile_545554861.json");
    }

    public void buildRoads(String jsonFilePath) {
        try {
            RoutingTile routingTile = getDeserializedRoutingTile(jsonFilePath);
            AtomicInteger roadNumber = new AtomicInteger(0);
            routingTile
                    .links
                    .link
                    .data
                    .forEach(link -> {
                        addLink(link, roadNumber.getAndIncrement());
                    });

            String tileId = extractTileId("src\\main\\resources\\nds\\routing\\routingTile_545555100.json");
            routingTile
                    .simpleIntersection
                    .simpleIntersection
                    .data
                    .forEach(intersection -> addIntersection(intersection, tileId));

            roadNumber.set(0);
            roadPointsList.forEach(intersectionsOfRoad -> {
                addEndOfRoad(intersectionsOfRoad, roadNumber.getAndIncrement());
            });


            roadNumber.set(0);
            routingTile
                    .fixedRoadAttributeSetList
                    .attributeList
                    .data
                    .forEach(attributes -> {
                        addRoadAttributes(attributes, roadNumber.getAndIncrement());
                    });

            managerPrx.persist();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String extractTileId(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 14, size - 5);
    }

    private RoutingTile getDeserializedRoutingTile(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<RoutingTile>() {
        });
    }

    private static LaneTile getDeserializedLaneTile(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<LaneTile>() {
        });
    }

    private void addLink(LinkData link, int roadNumber) {
        String roadId = managerPrx.create(ItemType.ROAD);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(roadId));
        RoadPrx roadPrx = RoadPrx.checkedCast(basePrx);

        roadPrx.setStartAngle(calculateAngle(link.startAngle));
        roadPrx.setEndAngle(calculateAngle(link.endAngle));

        roadPrxList.add(roadNumber, roadPrx);
        roadPointsList.add(roadNumber, new HashSet<>());
        logger.log(Level.INFO, "Added road with number: " + roadNumber);
    }

    private void addIntersection(IntersectionData intersectionData, String tileId) {
        String junctionId = managerPrx.create(ItemType.JUNCTION);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(junctionId));
        JunctionPrx junctionPrx = JunctionPrx.checkedCast(basePrx);

        Coordinates tileCenterCoordinates = calculateTileCoordinates(tileId);
        Integer x_shift = intersectionData.position.dx;
        Integer y_shift = intersectionData.position.dy;
        Coordinates intersectionCoordinates = calculateIntersectionCoordinates(tileCenterCoordinates, x_shift, y_shift);

        junctionPrx.setLatitude(String.valueOf(intersectionCoordinates.getLatitude()));
        junctionPrx.setLongitude(String.valueOf(intersectionCoordinates.getLongitude()));

        intersectionData.connectedLinks.data.forEach(link -> {
            Integer roadNumber = link.linkReferenceChoice.objectChoice.linkId;
            Boolean isIntersectionStartOfRoad = link.linkReferenceChoice.objectChoice.positiveLinkDirection;
            if (isIntersectionStartOfRoad) {
                roadPrxList.get(roadNumber).setStarts(junctionId);
                startOfRoadCoordinates.set(roadNumber, intersectionCoordinates);
            }

            Set<IntersectionPoint> linksSet = roadPointsList.get(roadNumber);
            linksSet.add(new IntersectionPoint(junctionId, intersectionCoordinates));
            roadPointsList.set(roadNumber, linksSet);
        });
    }

    private void addEndOfRoad(Set<IntersectionPoint> intersectionsSet, int roadNumber) {
        IntersectionPoint endPoint = intersectionsSet.stream()
                .max(Comparator.comparing(intersection -> {
                    double latitude = intersection.getCoordinates().getLongitude();
                    double longitude = intersection.getCoordinates().getLongitude();
                    double startPointLatitude = startOfRoadCoordinates.get(roadNumber).getLatitude();
                    double startPointLongitude = startOfRoadCoordinates.get(roadNumber).getLongitude();
                    return Math.sqrt(Math.pow((latitude - startPointLatitude), 2) + Math.pow((longitude - startPointLongitude), 2));
                })).get();
        roadPrxList.get(roadNumber).setEnds(endPoint.getIntersectionId());
    }

    private void addRoadAttributes(AttributeData attributeData, int roadNumber) {
        String roadAttributesId = managerPrx.create(ItemType.ROADATTRIBUTES);
        ObjectPrx basePrx = communicator.stringToProxy(CommunicationUtils.getInternetAddress(roadAttributesId));
        RoadAttributesPrx roadAttributesPrx = RoadAttributesPrx.checkedCast(basePrx);

        assignSharedAttributes(roadAttributesPrx, attributeData.sharedAttr);
        assignRoutingAttributes(roadAttributesPrx, attributeData.routingAttr);

        roadPrxList.get(roadNumber).setRoadAttributes(roadAttributesId);
        logger.log(Level.INFO, "Added road attributes to road with number: " + roadNumber);
    }

    private static float calculateAngle(int number) {
        return (float) number * maxAngleDegree / angleCoefficient;
    }

    private static Coordinates calculateTileCoordinates(String tileId) {
        int tileDec = Integer.parseInt(tileId);
        String tileBin = Integer.toBinaryString(tileDec);

        //removing first three bits (tile level)
        StringBuilder sb = new StringBuilder(tileBin);
        for (int i = 0; i < 5; i++) {
            sb.deleteCharAt(0);
        }
        tileBin = sb.toString();

        //add "0"'s to tile number
        while (tileBin.length() < 27) {
            tileBin = "0" + tileBin;
        }

        //decoding Morton code
        String x = "";
        String y = "";
        for (int i = 0; i < tileBin.length(); i++) {
            if (i % 2 == 0) {
                x = x + tileBin.charAt(i);
            } else {
                y = y + tileBin.charAt(i);
            }
        }

        //shifting coordinates
        String transformedX = "";
        String transformedY = "";
        String newX = "";
        String newY = "";
        Boolean changedX = false;
        boolean changedY = false;

        if (String.valueOf(x.charAt(0)).equals("1")) {
            changedX = true;
            Long tmp = Long.parseLong(x, 2);
            tmp = tmp - 1;
            transformedX = Long.toBinaryString(tmp);
            for (int i = 0; i < transformedX.length(); i++) {
                if (String.valueOf(transformedX.charAt(i)).equals("0")) {
                    newX = newX + "1";
                } else {
                    newX = newX + "0";
                }
            }
        }

        if (String.valueOf(y.charAt(0)).equals("1")) {
            changedY = true;
            Long tmp2 = Long.parseLong(y, 2);
            tmp2 = tmp2 - 1;
            transformedY = Long.toBinaryString(tmp2);
            for (int i = 0; i < transformedY.length(); i++) {
                if (String.valueOf(transformedX.charAt(i)).equals("0")) {
                    newY = newY + "1";
                } else {
                    newY = newY + "0";
                }
            }
        }

        if (changedX) {
            x = newX;
        }

        if (changedY) {
            y = newY;
        }

        x = x + "000000000000000000";
        y = y + "000000000000000000";

        //convert to decimal
        Long decimalValueX = Long.parseLong(x, 2);
        Long decimalValueY = Long.parseLong(y, 2);

        //convert to coordinates
        double coorXratio = decimalValueX / 2147483648.0;
        double coorYratio = decimalValueY / 1073741824.0;

        double coorX = coorXratio * 180;
        double coorY = coorYratio * 90;


        if (changedX) {
            coorX = -coorX;
        }

        if (changedY) {
            coorY = -coorY;
        }

        return new Coordinates(coorX, coorY);
    }

    private static Coordinates calculateIntersectionCoordinates(Coordinates tileCenterCoordinates, Integer x_shift, Integer y_shift) {
        double longitude = tileCenterCoordinates.getLongitude() + x_shift / metresInOneDegree;
        double latitude = tileCenterCoordinates.getLatitude() + y_shift / metresInOneDegree;
        return new Coordinates(longitude, latitude);
    }

    private void assignSharedAttributes(RoadAttributesPrx roadAttrPrx, SharedAttr sharedAttr) {
        roadAttrPrx.setFerry(sharedAttr.ferry);
        roadAttrPrx.setTunnel(sharedAttr.tunnel);
        roadAttrPrx.setBridge(sharedAttr.bridge);
        roadAttrPrx.setToll(!sharedAttr.toll.equals("IN_NO_DIRECTION"));
        roadAttrPrx.setControlledAccess(sharedAttr.controlledAccess);
        roadAttrPrx.setServiceArea(sharedAttr.serviceArea);
    }

    private void assignRoutingAttributes(RoadAttributesPrx roadAttrPrx, RoutingAttr routingAttr) {
        roadAttrPrx.setUrban(routingAttr.urban);
        roadAttrPrx.setMotorway(routingAttr.motorway);
    }

}
