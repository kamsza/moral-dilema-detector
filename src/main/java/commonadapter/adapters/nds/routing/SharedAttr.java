
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "priorityRoadClass",
        "linkType",
        "travelDirection",
        "ferry",
        "tunnel",
        "bridge",
        "toll",
        "controlledAccess",
        "serviceArea"
})
public class SharedAttr {
    @JsonProperty("priorityRoadClass")
    public Integer priorityRoadClass;
    @JsonProperty("linkType")
    public String linkType;
    @JsonProperty("travelDirection")
    public String travelDirection;

    @JsonProperty("ferry")
    public Boolean ferry;
    @JsonProperty("tunnel")
    public Boolean tunnel;
    @JsonProperty("bridge")
    public Boolean bridge;
    @JsonProperty("toll")
    public String toll;
    @JsonProperty("controlledAccess")
    public Boolean controlledAccess;
    @JsonProperty("serviceArea")
    public Boolean serviceArea;
}
