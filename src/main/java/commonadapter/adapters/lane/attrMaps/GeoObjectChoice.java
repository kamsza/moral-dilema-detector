package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "extTileIdx",
        "tileExternalRoadGeoLineReference",
        "positiveLinkDirection",
        "roadGeoLineId"
})
public class GeoObjectChoice {
    @JsonProperty("extTileIdx")
    public Integer extTileIdx;
    @JsonProperty("tileExternalRoadGeoLineReference")
    public TileExternalRoadGeoLineRef tileExternalRoadGeoLineRef;
    @JsonProperty("positiveLinkDirection")
    public Boolean positiveLinkDirection;
    @JsonProperty("roadGeoLineId")
    public Integer roadGeoLineId;
}