package commonadapter.adapters.nds;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commonadapter.adapters.nds.lane.LaneTile;
import commonadapter.adapters.nds.lane.attrMaps.ObjectChoice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonDeserializerLaneTileTest {

    private LaneTile laneTile;

    @Before
    public void setUp() throws Exception {
        String path = "src\\test\\resources\\lane\\laneTile_545555000.json";
        laneTile = JsonDeserializer.getDeserializedLaneTile(path);
    }

    @Test
    public void isRoadNumberCorrectlyDeserialized(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectChoice featureObjectChoice1 = mapper.convertValue(laneTile.attributeMaps.attrMap.data.get(0).values4OneFeature.data.get(0).feature.objectChoice, ObjectChoice.class);
        assertEquals(featureObjectChoice1.linkId.intValue(), 0);

        ObjectChoice featureObjectChoice2 = mapper.convertValue(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(0).feature.objectChoice, ObjectChoice.class);
        assertEquals(featureObjectChoice2.linkId.intValue(), 0);

        ObjectChoice featureObjectChoice3 = mapper.convertValue(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(1).feature.objectChoice, ObjectChoice.class);
        assertEquals(featureObjectChoice3.linkId.intValue(), 0);
    }

    @Test
    public void areLaneGroupsCorrectlyFoundandDeserialized(){
        assertEquals(laneTile.attributeMaps.attrMap.data.get(0).values4OneFeature.data.get(0).attrValList.values.data.get(0).attrType, "LANE_GROUP");
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(0).attrValList.values.data.get(1).attrType, "LANE_GROUP");
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(1).attrValList.values.data.get(1).attrType, "LANE_GROUP");
    }

    @Test
    public void areLanesCorrectlyFoundandDeserialized(){
        assertEquals(laneTile.attributeMaps.attrMap.data.get(0).values4OneFeature.data.get(0).attrValList.values.data.get(0).valueObjectChoice.laneConnectivityElements.data.get(0).laneType, "NORMAL_LANE");
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(0).attrValList.values.data.get(1).valueObjectChoice.laneConnectivityElements.data.get(0).laneType, "NORMAL_LANE");
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(1).attrValList.values.data.get(1).valueObjectChoice.laneConnectivityElements.data.get(0).laneType, "NORMAL_LANE");
    }

    @Test
    public void areLaneBoundariesCorrectlyChecked(){
        assertEquals(laneTile.attributeMaps.attrMap.data.get(0).values4OneFeature.data.get(0).attrValList.values.data.get(0).valueObjectChoice.hasLaneBoundaries, false);
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(0).attrValList.values.data.get(1).valueObjectChoice.hasLaneBoundaries, false);
        assertEquals(laneTile.attributeMaps.attrMap.data.get(1).values4OneFeature.data.get(1).attrValList.values.data.get(1).valueObjectChoice.hasLaneBoundaries, false);
    }

}
