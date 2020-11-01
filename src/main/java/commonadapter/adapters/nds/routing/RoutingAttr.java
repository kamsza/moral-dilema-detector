
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "functionalRoadClass",
        "urban",
        "complexIntersection",
        "pluralJunction",
        "motorway"
})
public class RoutingAttr {
    @JsonProperty("functionalRoadClass")
    public Integer functionalRoadClass;
    @JsonProperty("urban")
    public Boolean urban;
    @JsonProperty("complexIntersection")
    public Boolean complexIntersection;
    @JsonProperty("pluralJunction")
    public Boolean pluralJunction;
    @JsonProperty("motorway")
    public Boolean motorway;
}
