
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.header.ContentMask;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contentMask",
    "fixedRoadAttributeSetListOffset",
    "simpleIntersectionOffset",
    "linksOffset",
    "link2TileListOffset",
    "externalTileIdListOffset"
})
public class Header {
    @JsonProperty("contentMask")
    public ContentMask contentMask;
    @JsonProperty("fixedRoadAttributeSetListOffset")
    public Integer fixedRoadAttributeSetListOffset;
    @JsonProperty("simpleIntersectionOffset")
    public Integer simpleIntersectionOffset;
    @JsonProperty("linksOffset")
    public Integer linksOffset;
    @JsonProperty("link2TileListOffset")
    public Integer link2TileListOffset;
    @JsonProperty("externalTileIdListOffset")
    public Integer externalTileIdListOffset;
}
