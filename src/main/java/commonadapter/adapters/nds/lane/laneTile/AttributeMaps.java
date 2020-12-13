
package commonadapter.adapters.nds.lane.laneTile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.lane.attrMaps.AttrMap;
import commonadapter.adapters.nds.lane.attrMaps.AttrTypeRef;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numMaps",
        "attrTypeRef",
        "attrMap"
})
public class AttributeMaps {
    @JsonProperty("numMaps")
    public Integer numMaps;
    @JsonProperty("attrTypeRef")
    public AttrTypeRef attrTypeRef;
    @JsonProperty("attrMap")
    public AttrMap attrMap;
}