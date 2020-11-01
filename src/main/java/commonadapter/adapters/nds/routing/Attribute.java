
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sharedAttr",
    "routingAttr"
})
public class Attribute {
    @JsonProperty("sharedAttr")
    public SharedAttr sharedAttr;
    @JsonProperty("routingAttr")
    public RoutingAttr routingAttr;
}
