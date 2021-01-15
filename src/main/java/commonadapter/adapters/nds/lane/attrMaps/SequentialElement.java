package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "openToCurbSide",
        "openToMiddleSide",
        "hasColor",
        "color",
        "hasBoundaryMaterial",
        "laneBoundaryMaterial",
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
    @JsonProperty("color")
    public String color;
    @JsonProperty("hasBoundaryMaterial")
    public Boolean hasBoundaryMaterial;
    @JsonProperty("laneBoundaryMaterial")
    public String laneBoundaryMaterial;
    @JsonProperty("laneRange")
    public LaneRange laneRange;
}