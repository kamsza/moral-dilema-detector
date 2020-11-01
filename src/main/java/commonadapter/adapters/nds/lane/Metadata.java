
package commonadapter.adapters.nds.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "baseTileHeight"
})
public class Metadata {
    @JsonProperty("baseTileHeight")
    public Integer baseTileHeight;
}
