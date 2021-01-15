package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.simpleIntersection.SimpleIntersections;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coordWidth",
        "numIntersections",
        "simpleIntersection"
})
public class SimpleIntersection {
    @JsonProperty("coordWidth")
    public Integer coordWidth;
    @JsonProperty("numIntersections")
    public Integer numIntersections;
    @JsonProperty("simpleIntersection")
    public SimpleIntersections simpleIntersection;
}