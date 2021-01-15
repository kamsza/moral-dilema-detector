package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startPosition",
        "coordinateOffset"
})
public class AttributePointsElement {
    @JsonProperty("startPosition")
    public StartPosition startPosition;
    @JsonProperty("coordinateOffset")
    public Integer coordinateOffset;

}
