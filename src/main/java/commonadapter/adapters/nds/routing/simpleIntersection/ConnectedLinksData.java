package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalLinkReference",
        "linkReferenceChoice"
})
public class ConnectedLinksData {
    @JsonProperty("isExternalLinkReference")
    public Boolean isExternalLinkReference;
    @JsonProperty("linkReferenceChoice")
    public LinkReferenceChoice linkReferenceChoice;
}