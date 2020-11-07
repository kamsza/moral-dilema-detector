package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "positiveLinkDirection",
        "linkId"
})
public class TileExternalLinkReference {
    @JsonProperty("positiveLinkDirection")
    public Boolean positiveLinkDirection;
    @JsonProperty("linkId")
    public Integer linkId;
}