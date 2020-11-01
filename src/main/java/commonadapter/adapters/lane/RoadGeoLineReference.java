package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalRoadGeoLineReference",
        "roadGeoLineReferenceChoice"

})
public class RoadGeoLineReference {
    @JsonProperty("isExternalRoadGeoLineReference")
    public Boolean isExternalRoadGeoLineReference;
    @JsonProperty("roadGeoLineReferenceChoice")
    public RoadGeoLineReferenceChoice roadGeoLineReferenceChoice;
}