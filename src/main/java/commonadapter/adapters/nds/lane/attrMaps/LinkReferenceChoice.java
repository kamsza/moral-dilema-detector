package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
