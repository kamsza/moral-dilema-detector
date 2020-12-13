package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numBits",
        "dx",
        "dy"
})
public class Position {
    @JsonProperty("numBits")
    public Integer numBits;
    @JsonProperty("dx")
    public Integer dx;
    @JsonProperty("dy")
    public Integer dy;
}