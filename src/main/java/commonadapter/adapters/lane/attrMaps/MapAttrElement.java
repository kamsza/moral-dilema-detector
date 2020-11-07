
package commonadapter.adapters.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.lane.attrMaps.AttrRefHeader;
import commonadapter.adapters.lane.attrMaps.Values4OneFeature;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrRefHeader",
        "attrMapType",
        "numEntries",
        "values4OneFeature",
        "values4ManyFeatures"
})
public class MapAttrElement {
    @JsonProperty("attrRefHeader")
    public AttrRefHeader attrRefHeader;
    @JsonProperty("attrMapType")
    public String attrMapType;
    @JsonProperty("numEntries")
    public Integer numEntries;
    @JsonProperty("values4OneFeature")
    public Values4OneFeature values4OneFeature;
    @JsonProperty("values4ManyFeatures")
    public Values4ManyFeatures values4ManyFeatures;

}