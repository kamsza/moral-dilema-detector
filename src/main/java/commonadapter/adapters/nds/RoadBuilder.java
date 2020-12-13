package commonadapter.adapters.nds;

import adapter.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import commonadapter.adapters.nds.lane.LaneTile;
import commonadapter.adapters.nds.lane.attrMaps.*;
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
import java.util.stream.IntStream;

public class RoadBuilder {

    private List<RoadPrx> roadPrxList = new ArrayList<>();
    private List<LanePrx> lanePrxList = new ArrayList<>();
    private List<Set<IntersectionPoint>> roadPointsList = new ArrayList<>();
    private List<Coordinates> startOfRoadCoordinates = new ArrayList<>();
    private static final float maxAngleDegree = 360f;
    private static final float angleCoefficient = 64f;
    private IceProxyNds proxyService;
    private final String routingTileFilePath;
    private String laneTileFilePath;

    public RoadBuilder(String routingTileFilePath, String laneTileFilePath) {
        this.proxyService = new IceProxyNds();
        this.routingTileFilePath = routingTileFilePath;
        this.laneTileFilePath = laneTileFilePath;
    }

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
                    .forEach(link -> addLink(link, roadNumber.getAndIncrement()));

            String tileId = NdsUtils.extractTileId(this.routingTileFilePath);
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
        } catch (NullPointerException e) {
            Logger.printLogMessage("Bad input file", LogMessageType.ERROR);
            return null;
        }
        return roadPrxList.stream().map(BaseItemPrx::getId).collect(Collectors.toList());
    }

    public List<String> buildLanes() {
        try {
            LaneTile laneTile = JsonDeserializer.getDeserializedLaneTile(laneTileFilePath);
            AtomicInteger laneNumber = new AtomicInteger(0);
            AtomicInteger firstLaneInGroupNumber = new AtomicInteger(0);
            laneTile.attributeMaps.attrMap.data.stream()
                    .map(e -> e.values4OneFeature.data)
                    .flatMap(Collection::stream)
                    .filter(e -> e.feature.referenceType.equals("ROUTING_LINK_DIRECTED")
                            || e.feature.referenceType.equals("ROUTING_ROAD_GEO_LINE_DIRECTED"))
                    .forEach(element -> {
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectChoice featureObjectChoice = mapper.convertValue(element.feature.objectChoice, ObjectChoice.class);
                        Integer linkNumber = featureObjectChoice.linkId != null ?
                                featureObjectChoice.linkId : featureObjectChoice.roadGeoLineId;

                        if (linkNumber != null) {
                            element.attrValList.values.data.stream()
                                    .filter(e -> e.attrType.equals("LANE_GROUP"))
                                    .map(e -> e.valueObjectChoice)
                                    .forEach(c -> {
                                        laneNumber.set(addLanes(c.laneConnectivityElements, linkNumber, firstLaneInGroupNumber.get()));
                                        if (c.hasLaneBoundaries) {
                                            addLaneBoundaries(c.boundaryElements, firstLaneInGroupNumber.get());
                                        }
                                        firstLaneInGroupNumber.set(laneNumber.get());
                                    });
                        }
                    });

            proxyService.persistOntologyChanges();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            Logger.printLogMessage("Bad input file", LogMessageType.ERROR);
            return null;
        }
        return lanePrxList.stream().map(BaseItemPrx::getId).collect(Collectors.toList());
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

    private int addLanes(LaneConnectivityElements laneConnectivityElements, int roadNumber, int firstLaneInGroupNumber) {
        AtomicInteger laneNumber = new AtomicInteger(firstLaneInGroupNumber);
        String roadId = roadPrxList.get(roadNumber).getId();
        laneConnectivityElements.data.stream()
                .filter(e -> e.laneType.equals("NORMAL_LANE"))
                .forEach(e -> {
                    LanePrx lanePrx = proxyService.createLanePrx();
                    lanePrx.setRoad(roadId);
                    lanePrxList.add(laneNumber.getAndIncrement(), lanePrx);
                    Logger.printLogMessage("Added lane with number: " + laneNumber.get(), LogMessageType.INFO);
                });
        return laneNumber.get();
    }

    private void addLaneBoundaries(BoundaryElements boundaryElements, int firstLaneInGroupNumber) {
        final List<BoundaryElement> boundaryList = boundaryElements.data;
        for (int i=0; i<boundaryList.size(); i++) {
            if (boundaryList.get(i).laneBoundarySource.equals("INTERNAL_SHARED")) {
                BoundaryElement nextBoundary = boundaryList.get(i+1);
                boundaryList.set(i, nextBoundary);
            }
        }

        List<SequentialElement> sequentialElements = boundaryList.stream()
                .map(e -> e.parallelElements.data)
                .flatMap(Collection::stream)
                .map(p -> p.sequentialElements.data)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        AtomicInteger laneNumber = new AtomicInteger(firstLaneInGroupNumber);
        IntStream.range(0, (lanePrxList.size() - firstLaneInGroupNumber) * 2 - 1)
                .filter(n -> n % 2 == 0)
                .mapToObj(sequentialElements::get)
                .forEach(element -> {
                    LaneBoundaryPrx laneLeftBoundaryPrx = proxyService.createLaneBoundaryPrx();
                    laneLeftBoundaryPrx.setType(element.type);
                    laneLeftBoundaryPrx.setColor(element.color);
                    laneLeftBoundaryPrx.setMaterial(element.laneBoundaryMaterial);
                    lanePrxList.get(laneNumber.getAndIncrement()).setLeftSideBoundary(laneLeftBoundaryPrx.getId());
                    Logger.printLogMessage("Added left boundary to lane with number: " + laneNumber, LogMessageType.INFO);
                });

        laneNumber.set(firstLaneInGroupNumber);
        IntStream.range(0, (lanePrxList.size() - firstLaneInGroupNumber) * 2)
                .filter(n -> n % 2 == 1)
                .mapToObj(sequentialElements::get)
                .forEach(element -> {
                    LaneBoundaryPrx laneRightBoundaryPrx = proxyService.createLaneBoundaryPrx();
                    laneRightBoundaryPrx.setType(element.type);
                    laneRightBoundaryPrx.setColor(element.color);
                    laneRightBoundaryPrx.setMaterial(element.laneBoundaryMaterial);
                    lanePrxList.get(laneNumber.getAndIncrement()).setRightSideBoundary(laneRightBoundaryPrx.getId());
                    Logger.printLogMessage("Added right boundary to lane with number: " + laneNumber, LogMessageType.INFO);
                });
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
