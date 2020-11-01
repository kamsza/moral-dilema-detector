package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})
public class SourceGuidanceFeatureReference {
    @JsonProperty("data")
    public List<SourceGuidanceFeatureRef> data;
}