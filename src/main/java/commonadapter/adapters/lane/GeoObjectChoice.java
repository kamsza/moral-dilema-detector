package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "extTileIdx",
        "tileExternalRoadGeoLineReference"
})
public class GeoObjectChoice {
    @JsonProperty("extTileIdx")
    public Integer extTileIdx;
    @JsonProperty("tileExternalRoadGeoLineReference")
    public TileExternalRoadGeoLineRef tileExternalRoadGeoLineRef;
}