package commonadapter.adapters.nds.routing.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrSource",
        "__objectChoice"
})
public class AttrInfo {
    @JsonProperty("attrSource")
    public String attrSource;
    @JsonProperty("__objectChoice")
    public Integer objectChoice;
}