
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "referenceType",
        "__objectChoice"
})
public class Feature {
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("__objectChoice")
    public ObjectChoice objectChoice;
}