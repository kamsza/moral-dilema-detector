package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isExternalLinkReference",
        "__objectChoice"
})
public class LinkReferenceChoice {
    @JsonProperty("isExternalLinkReference")
    public Boolean isExternalLinkReference;
    @JsonProperty("__objectChoice")
    public ObjectChoice objectChoice;
}