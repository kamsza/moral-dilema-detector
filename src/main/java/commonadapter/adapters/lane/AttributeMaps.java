
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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