package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalRoadGeoLineReference",
        "__objectChoice"

})
public class RoadGeoLineReferenceChoice {
    @JsonProperty("isExternalRoadGeoLineReference")
    public Boolean isExternalRoadGeoLineReference;
    @JsonProperty("__objectChoice")
    public GeoObjectChoice objectChoice;
}