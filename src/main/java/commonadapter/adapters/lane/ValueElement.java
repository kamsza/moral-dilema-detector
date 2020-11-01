package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrType",
        "__objectChoice"
})
public class ValueElement {
    @JsonProperty("attrType")
    public String attrType;
    @JsonProperty("__objectChoice")
    public ValueObjectChoice valueObjectChoice;
}