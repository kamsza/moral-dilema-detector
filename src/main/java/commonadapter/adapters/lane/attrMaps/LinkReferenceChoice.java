package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.lane.attrMaps.ObjectChoice;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalLinkReference",
        "__objectChoice"
})
public class LinkReferenceChoice {
    @JsonProperty("isExternalLinkReference")
    public String isExternalLinkReference;
    @JsonProperty("__objectChoice")
    public ObjectChoice objectChoice;
}
