
package commonadapter.adapters.nds.routing;

        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "header",
        "fixedRoadAttributeSetList",
        "simpleIntersection",
        "externalTileIdList"
})
public class RoutingTile {

    @JsonProperty("header")
    public Header header;
    @JsonProperty("fixedRoadAttributeSetList")
    public FixedRoadAttributeSetList fixedRoadAttributeSetList = null;
}
