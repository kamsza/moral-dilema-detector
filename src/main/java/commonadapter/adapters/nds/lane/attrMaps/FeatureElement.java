package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "referenceType",
        "__objectChoice"
})
public class FeatureElement {
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("__objectChoice")
    public JsonNode objectChoice;
}