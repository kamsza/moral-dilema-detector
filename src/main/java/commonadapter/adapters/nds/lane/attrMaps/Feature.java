package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "referenceType",
        "__objectChoice"

})
public class Feature {
    @JsonProperty("data")
    public List<FeatureElement> data;
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("__objectChoice")
    public JsonNode objectChoice;

}