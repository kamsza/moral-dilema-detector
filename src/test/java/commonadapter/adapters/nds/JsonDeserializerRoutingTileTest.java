package commonadapter.adapters.nds;

import commonadapter.adapters.nds.routing.RoutingTile;
import commonadapter.adapters.nds.routing.fixedAttributes.RoutingAttr;
import commonadapter.adapters.nds.routing.fixedAttributes.SharedAttr;
import commonadapter.adapters.nds.routing.links.LinkData;
import commonadapter.adapters.nds.routing.simpleIntersection.IntersectionData;
import commonadapter.adapters.nds.routing.simpleIntersection.ObjectChoice;
import commonadapter.adapters.nds.routing.simpleIntersection.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonDeserializerRoutingTileTest {

    private RoutingTile routingTile;

    @Before
    public void setUp() throws Exception {
        String path = "src\\test\\resources\\nds\\routing\\routingTile_545554860.json";
        routingTile = JsonDeserializer.getDeserializedRoutingTile(path);
    }

    @Test
    public void isRoadCorrectlyDeserialized() {
        LinkData firstRoad = routingTile.links.link.data.get(0);
        assertEquals(20, firstRoad.startAngle, 0.0);
        assertEquals(49, firstRoad.endAngle, 0.0);
        assertEquals(98, firstRoad.averageSpeed, 0.0);
    }

    @Test
    public void isIntersectionPositionCorrectlyDeserialized() {
        Position firstIntersection = routingTile.simpleIntersection.simpleIntersection.data.get(0).position;
        assertEquals(1019, firstIntersection.dx, 0.0);
        assertEquals(-993, firstIntersection.dy, 0.0);
    }

    @Test
    public void areIntersectionRoadReferencesCorrectlyDeserialized() {
        IntersectionData firstIntersection = routingTile.simpleIntersection.simpleIntersection.data.get(0);
        ObjectChoice objectChoice = firstIntersection.connectedLinks.data.get(0).linkReferenceChoice.objectChoice;
        assertEquals(0, objectChoice.linkId, 0.0);
        assertEquals(true, objectChoice.positiveLinkDirection);
    }

    @Test
    public void areSharedAttributesCorrectlyDeserialized() {
        SharedAttr sharedAttributes = routingTile.fixedRoadAttributeSetList.attributeList.data.get(0).sharedAttr;
        assertEquals(false, sharedAttributes.bridge);
        assertEquals(true, sharedAttributes.controlledAccess);
        assertEquals(false, sharedAttributes.ferry);
        assertEquals(false, sharedAttributes.serviceArea);
        assertEquals(false, sharedAttributes.tunnel);
        assertEquals("IN_NO_DIRECTION", sharedAttributes.toll);

    }
    @Test
    public void areRoutingAttributesCorrectlyDeserialized() {
        RoutingAttr routingAttributes = routingTile.fixedRoadAttributeSetList.attributeList.data.get(0).routingAttr;
        assertEquals(true, routingAttributes.motorway);
        assertEquals(false, routingAttributes.urban);
    }
}