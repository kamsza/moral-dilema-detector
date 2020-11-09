package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "laneType",
        "hasLaneConnectivity",
        "laneConnectivity",
        "hasGeometry"
})
public class LaneConnectivityElement {
    @JsonProperty("laneType")
    public String laneType;
    @JsonProperty("hasLaneConnectivity")
    public Boolean hasLaneConnectivity;
    @JsonProperty("laneConnectivity")
    public LaneConnectivity laneConnectivity;
    @JsonProperty("hasGeometry")
    public Boolean hasGeometry;
}