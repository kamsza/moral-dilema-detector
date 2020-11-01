
package commonadapter.adapters.nds.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "header",
        "metadata",
        "attributeMaps"
})
public class LaneTile {
    @JsonProperty("header")
    public Header header;
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("attributeMaps")
    public AttributeMaps attributeMaps;
}
