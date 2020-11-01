
package commonadapter.adapters.nds.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "hasAttributeMaps",
        "hasExternalTileIdList"
})
public class ContentMask {
    @JsonProperty("hasAttributeMaps")
    public Boolean hasAttributeMaps;
    @JsonProperty("hasExternalTileIdList")
    public Boolean hasExternalTileIdList;
}