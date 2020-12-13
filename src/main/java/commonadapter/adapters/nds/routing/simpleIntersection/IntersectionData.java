package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coordWidth",
        "position",
        "numLinks",
        "connectedLinks"
})
public class IntersectionData {
    @JsonProperty("coordWidth")
    public Integer coordWidth;
    @JsonProperty("position")
    public Position position;
    @JsonProperty("numLinks")
    public Integer numLinks;
    @JsonProperty("connectedLinks")
    public ConnectedLinks connectedLinks;
}