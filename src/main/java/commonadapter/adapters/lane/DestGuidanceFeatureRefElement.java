package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "guidanceFeatureType",
        "baseLinkReference",
        "roadGeoLineReference"
})
public class DestGuidanceFeatureRefElement {
    @JsonProperty("guidanceFeatureType")
    public String guidanceFeatureType;
    @JsonProperty("baseLinkReference")
    public BaseLinkReference baseLinkReference;
    @JsonProperty("roadGeoLineReference")
    public RoadGeoLineReference roadGeoLineReference;
}