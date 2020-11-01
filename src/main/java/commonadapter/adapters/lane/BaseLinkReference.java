package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalLinkReference",
        "linkReferenceChoice"
})
public class BaseLinkReference {
    @JsonProperty("isExternalLinkReference")
    public String isExternalLinkReference;
    @JsonProperty("linkReferenceChoice")
    public LinkReferenceChoice linkReferenceChoice;
}