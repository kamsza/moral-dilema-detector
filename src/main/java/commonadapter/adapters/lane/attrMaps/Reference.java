package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numSourceFeatures",
        "numDestFeatures",
        "sourceGuidanceFeatureReference",
        "destGuidanceFeatureReference"
})
public class Reference {
    @JsonProperty("numSourceFeatures")
    public Integer numSourceFeatures;
    @JsonProperty("numDestFeatures")
    public Integer numDestFeatures;
    @JsonProperty("sourceGuidanceFeatureReference")
    public SourceGuidanceFeatureReference  sourceGuidanceFeatureReference;
    @JsonProperty("destGuidanceFeatureReference")
    public DestGuidanceFeatureReference destGuidanceFeatureReference;
}