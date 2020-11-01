
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
<<<<<<< HEAD
        "attrRefHeader",
        "attrMapType",
        "numEntries",
        "values4OneFeature"
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
}