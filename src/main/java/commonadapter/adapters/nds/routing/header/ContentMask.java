
package commonadapter.adapters.nds.routing.header;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "hasFixedRoadAttributeSetList",
        "hasSimpleIntersectionList",
        "hasLinkList",
        "hasRouteUpLinkList",
        "hasRouteDownLinkList",
        "hasLinkToTileList",
        "hasLinkIdRangeList",
        "hasSimpleIntersectionIdRangeList",
        "hasAttributeMaps",
        "hasExternalTileIdList"
})
public class ContentMask {
    @JsonProperty("hasFixedRoadAttributeSetList")
    public Boolean hasFixedRoadAttributeSetList;
    @JsonProperty("hasSimpleIntersectionList")
    public Boolean hasSimpleIntersectionList;
    @JsonProperty("hasLinkList")
    public Boolean hasLinkList;
    @JsonProperty("hasRouteUpLinkList")
    public Boolean hasRouteUpLinkList;
    @JsonProperty("hasRouteDownLinkList")
    public Boolean hasRouteDownLinkList;
    @JsonProperty("hasLinkToTileList")
    public Boolean hasLinkToTileList;
    @JsonProperty("hasLinkIdRangeList")
    public Boolean hasLinkIdRangeList;
    @JsonProperty("hasSimpleIntersectionIdRangeList")
    public Boolean hasSimpleIntersectionIdRangeList;
    @JsonProperty("hasAttributeMaps")
    public Boolean hasAttributeMaps;
    @JsonProperty("hasExternalTileIdList")
    public Boolean hasExternalTileIdList;
}
