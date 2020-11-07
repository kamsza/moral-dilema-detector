package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

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