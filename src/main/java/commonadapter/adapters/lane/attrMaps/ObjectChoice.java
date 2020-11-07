
package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "positiveLinkDirection",
        "linkId",
        "roadGeoLineId"
})
public class ObjectChoice {
    @JsonProperty("positiveLinkDirection")
    public String positiveLinkDirection;
    @JsonProperty("linkId")
    public Integer linkId;
    @JsonProperty("roadGeoLineId")
    public Integer roadGeoLineId;
}