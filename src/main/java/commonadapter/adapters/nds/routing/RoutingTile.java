
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.externalTile.ExternalTileIdList;
import commonadapter.adapters.nds.routing.link2Tile.Link2TileList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "coordWidth",
    "header",
    "fixedRoadAttributeSetList",
    "simpleIntersection",
    "links",
    "externalTileIdList",
    "link2TileList",
    "externalTileIdList"
})
public class RoutingTile {
    @JsonProperty("coordWidth")
    public Integer coordWidth;
    @JsonProperty("header")
    public Header header;
    @JsonProperty("fixedRoadAttributeSetList")
    public FixedRoadAttributeSetList fixedRoadAttributeSetList = null;
    @JsonProperty("simpleIntersection")
    public SimpleIntersection simpleIntersection;
    @JsonProperty("links")
    public Links link;
    @JsonProperty("link2TileList")
    public Link2TileList link2TileList;
    @JsonProperty("externalTileIdList")
    public ExternalTileIdList externalTileIdList;
}
