
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "positiveLinkDirection",
        "linkId"
})
public class ObjectChoice {
    @JsonProperty("positiveLinkDirection")
    public String positiveLinkDirection;
    @JsonProperty("linkId")
    public Integer linkId;
}