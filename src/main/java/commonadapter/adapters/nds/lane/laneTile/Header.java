
package commonadapter.adapters.nds.lane.laneTile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contentMask",
        "attributeMapsOffset"
})
public class Header {
    @JsonProperty("contentMask")
    public commonadapter.adapters.nds.lane.header.ContentMask ContentMask;
    @JsonProperty("attributeMapsOffset")
    public Integer attributeMapsOffset;
}
