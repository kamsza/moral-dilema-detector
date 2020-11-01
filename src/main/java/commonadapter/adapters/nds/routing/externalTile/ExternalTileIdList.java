
package commonadapter.adapters.nds.routing.externalTile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.fixedAttributes.RoutingAttr;
import commonadapter.adapters.nds.routing.fixedAttributes.SharedAttr;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "numTileIds",
    "tileId"
})
public class ExternalTileIdList {
    @JsonProperty("numTileIds")
    public Integer numTileIds;
    @JsonProperty("tileId")
    public TileId tileId;
}
