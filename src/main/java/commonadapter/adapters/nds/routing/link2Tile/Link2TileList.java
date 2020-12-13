package commonadapter.adapters.nds.routing.link2Tile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numLinks",
        "tileRef"
})
public class Link2TileList {
    @JsonProperty("numLinks")
    public Integer numLinks;
    @JsonProperty("tileRef")
    public TileRef tileRef;
}