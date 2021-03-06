
package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrRefHeader",
        "feature",
        "attrValList"
})
public class MapAttrElementFeatures {
    @JsonProperty("attrRefHeader")
    public AttrRefHeader attrRefHeader;
    @JsonProperty("feature")
    public Feature feature;
    @JsonProperty("attrValList")
    public AttrValList attrValList;
}