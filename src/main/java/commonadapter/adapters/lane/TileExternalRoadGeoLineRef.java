package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "positiveLinkDirection",
        "roadGeoLineId"
})
public class TileExternalRoadGeoLineRef {
    @JsonProperty("positiveLinkDirection")
    public Integer positiveLinkDirection;
    @JsonProperty("roadGeoLineId")
    public Integer roadGeoLineId;
}