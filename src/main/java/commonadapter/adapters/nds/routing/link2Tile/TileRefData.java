package commonadapter.adapters.nds.routing.link2Tile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "linkId",
        "extTileColl"
})
public class TileRefData {
    @JsonProperty("linkId")
    public Integer linkId;
    @JsonProperty("extTileColl")
    public ExtTileColl extTileColl;
}