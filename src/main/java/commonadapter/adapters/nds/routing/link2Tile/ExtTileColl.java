package commonadapter.adapters.nds.routing.link2Tile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numExtTileIds",
        "extTileIdx"
})
public class ExtTileColl {
    @JsonProperty("numExtTileIds")
    public Integer numExtTileIds;
    @JsonProperty("extTileIdx")
    public ExtTileIdx extTileIdx;
}