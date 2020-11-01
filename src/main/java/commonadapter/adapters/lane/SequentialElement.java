package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "openToCurbSide",
        "openToMiddleSide",
        "hasColor",
        "hasBoundaryMaterial",
        "laneRange"
})
public class SequentialElement {
    @JsonProperty("type")
    public String type;
    @JsonProperty("openToCurbSide")
    public Boolean openToCurbSide;
    @JsonProperty("openToMiddleSide")
    public Boolean openToMiddleSide;
    @JsonProperty("hasColor")
    public Boolean hasColor;
    @JsonProperty("hasBoundaryMaterial")
    public Boolean hasBoundaryMaterial;
    @JsonProperty("laneRange")
    public LaneRange laneRange;
}