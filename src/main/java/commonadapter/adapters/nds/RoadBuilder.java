package commonadapter.adapters.nds;

import adapter.BaseItemPrx;
import adapter.JunctionPrx;
import adapter.RoadAttributesPrx;
import adapter.RoadPrx;
import com.google.common.collect.Lists;
import commonadapter.adapters.nds.lane.LaneTile;
import commonadapter.adapters.nds.lane.attrMaps.MapAttrElement;
import commonadapter.adapters.nds.lane.attrMaps.ValueObjectChoice;
import commonadapter.adapters.nds.routing.RoutingTile;
import commonadapter.adapters.nds.routing.fixedAttributes.AttributeData;
import commonadapter.adapters.nds.routing.fixedAttributes.RoutingAttr;
import commonadapter.adapters.nds.routing.fixedAttributes.SharedAttr;
import commonadapter.adapters.nds.routing.links.LinkData;
import commonadapter.adapters.nds.routing.simpleIntersection.IntersectionData;
import commonadapter.adapters.nds.services.IceProxyNds;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoadBuilder {

    private List<RoadPrx> roadPrxList = new ArrayList<>();
    private List<Set<IntersectionPoint>> roadPointsList = new ArrayList<>();
    private List<Coordinates> startOfRoadCoordinates = new ArrayList<>();
    private static final float maxAngleDegree = 360f;
    private static final float angleCoefficient = 64f;
    private IceProxyNds proxyService;
    private final String routingTileFilePath;

    public RoadBuilder(String routingTileFilePath) {
        this.proxyService = new IceProxyNds();
        this.routingTileFilePath = routingTileFilePath;
    }

    public List<String> buildRoads() {
        try {
            RoutingTile routingTile = JsonDeserializer.getDeserializedRoutingTile(this.routingTileFilePath);
            AtomicInteger roadNumber = new AtomicInteger(0);
            routingTile
                    .links
                    .link
                    .data
                    .forEach(link ->  addLink(link, roadNumber.getAndIncrement()));

            String tileId = extractTileId(this.routingTileFilePath);
            routingTile
                    .simpleIntersection
                    .simpleIntersection
                    .data
                    .forEach(intersection -> addIntersection(intersection, tileId));

            roadNumber.set(0);
            roadPointsList.forEach(intersectionsOfRoad ->
                addEndOfRoad(intersectionsOfRoad, roadNumber.getAndIncrement()));


            roadNumber.set(0);
            routingTile
                    .fixedRoadAttributeSetList
                    .attributeList
                    .data
                    .forEach(attributes -> addRoadAttributes(attributes, roadNumber.getAndIncrement()));

            proxyService.persistOntologyChanges();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return roadPrxList.stream().map(BaseItemPrx::getId).collect(Collectors.toList());
    }


    public void buildRoadsLaneTile(String jsonFilePath) {
        try {
            LaneTile laneTile = JsonDeserializer.getDeserializedLaneTile(jsonFilePath);
            AtomicInteger laneNumber = new AtomicInteger(0);
            List<MapAttrElement> data = laneTile.attributeMaps.attrMap.data;
            List<LaneRep> laneReps = data.stream()
                    .map(e->e.values4OneFeature.data)
                    .flatMap(d->d.stream())
                    .map(f->f.attrValList.values.data)
                    .flatMap(v->v.stream())
                    .filter(e->e.attrType.equals("LANE_GROUP"))
                    .map(e->e.valueObjectChoice)
                    .map(c -> new LaneRep(c.laneConnectivityElements, c.hasLaneBoundaries, c.boundaryElements))
                    .collect(Collectors.toList());

         laneReps.forEach(rep->{

         });

            proxyService.persistOntologyChanges();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static String extractTileId(String jsonFilePath) {
        int size = jsonFilePath.length();
        return jsonFilePath.substring(size - 14, size - 5);
    }

    private void addLaneGroup() {

    }

    private void addLane(){

    }


    private void addLaneBoundary(){

    }


    private void addLink(LinkData link, int roadNumber) {
        RoadPrx roadPrx = proxyService.createRoadPrx();

        roadPrx.setStartAngle(calculateAngle(link.startAngle));
        roadPrx.setEndAngle(calculateAngle(link.endAngle));
        roadPrx.setAverageSpeed(link.averageSpeed);

        roadPrxList.add(roadNumber, roadPrx);
        roadPointsList.add(roadNumber, new HashSet<>());
        Logger.printLogMessage("Added road with number: " + roadNumber, LogMessageType.INFO);
    }

    private void addIntersection(IntersectionData intersectionData, String tileId) {
        JunctionPrx junctionPrx = proxyService.createJunctionPrx();

        Coordinates tileCenterCoordinates = Coordinates.calculateTileCoordinates(tileId);
        int x_shift = intersectionData.position.dx;
        int y_shift = intersectionData.position.dy;
        Coordinates intersectionCoordinates = Coordinates.calculateIntersectionCoordinates(tileCenterCoordinates, x_shift, y_shift);

        junctionPrx.setLatitude(String.valueOf(intersectionCoordinates.getLatitude()));
        junctionPrx.setLongitude(String.valueOf(intersectionCoordinates.getLongitude()));

        intersectionData.connectedLinks.data.forEach(link -> {
            Integer roadNumber = link.linkReferenceChoice.objectChoice.linkId;
            Boolean isIntersectionStartOfRoad = link.linkReferenceChoice.objectChoice.positiveLinkDirection;
            if (isIntersectionStartOfRoad) {
                roadPrxList.get(roadNumber).setStarts(junctionPrx.getId());
                startOfRoadCoordinates.add(roadNumber, intersectionCoordinates);
                Logger.printLogMessage("Added start of road with number: " + roadNumber, LogMessageType.INFO);
            }

            Set<IntersectionPoint> linksSet = roadPointsList.get(roadNumber);
            linksSet.add(new IntersectionPoint(junctionPrx.getId(), intersectionCoordinates));
            roadPointsList.set(roadNumber, linksSet);
        });
    }

    private void addEndOfRoad(Set<IntersectionPoint> intersectionsSet, int roadNumber) {
        IntersectionPoint endPoint = intersectionsSet.stream()
                .max(Comparator.comparing(intersection -> {
                    double latitude = intersection.getCoordinates().getLatitude();
                    double longitude = intersection.getCoordinates().getLongitude();
                    double startPointLatitude = startOfRoadCoordinates.get(roadNumber).getLatitude();
                    double startPointLongitude = startOfRoadCoordinates.get(roadNumber).getLongitude();
                    return Math.sqrt(Math.pow((latitude - startPointLatitude), 2) + Math.pow((longitude - startPointLongitude), 2));
                })).get();
        roadPrxList.get(roadNumber).setEnds(endPoint.getIntersectionId());
        Logger.printLogMessage("Added end of road with number: " + roadNumber, LogMessageType.INFO);
    }

    private void addRoadAttributes(AttributeData attributeData, int roadNumber) {
        RoadAttributesPrx roadAttrPrx = proxyService.createRoadAttributesPrx();

        assignSharedAttributes(roadAttrPrx, attributeData.sharedAttr);
        assignRoutingAttributes(roadAttrPrx, attributeData.routingAttr);

        roadPrxList.get(roadNumber).setRoadAttributes(roadAttrPrx.getId());
        Logger.printLogMessage("Added road attributes to road with number: " + roadNumber, LogMessageType.INFO);
    }

    private static float calculateAngle(int number) {
        return (float) number * maxAngleDegree / angleCoefficient;
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
