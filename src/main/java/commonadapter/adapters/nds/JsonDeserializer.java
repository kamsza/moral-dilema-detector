package commonadapter.adapters.nds;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commonadapter.adapters.nds.lane.LaneTile;
import commonadapter.adapters.nds.routing.RoutingTile;

import java.io.File;
import java.io.IOException;

public class JsonDeserializer {

    public static RoutingTile getDeserializedRoutingTile(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<RoutingTile>() {
        });
    }

    public static LaneTile getDeserializedLaneTile(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonFilePath), new TypeReference<LaneTile>() {
        });
    }

}
