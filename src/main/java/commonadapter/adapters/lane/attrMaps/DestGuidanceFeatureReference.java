package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})
public class DestGuidanceFeatureReference {
    @JsonProperty("data")
    public List<DestGuidanceFeatureRefElement> data;
}
