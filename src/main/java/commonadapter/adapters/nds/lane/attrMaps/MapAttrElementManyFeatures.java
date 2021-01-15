package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrRefHeader",
        "numFeatures",
        "feature",
        "attrValList"
})
public class MapAttrElementManyFeatures {
    @JsonProperty("attrRefHeader")
    public AttrRefHeader attrRefHeader;
    @JsonProperty("numFeatures")
    public Integer numFeatures;
    @JsonProperty("feature")
    public Feature feature;
    @JsonProperty("attrValList")
    public AttrValList attrValList;
}