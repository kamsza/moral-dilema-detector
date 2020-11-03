
package commonadapter.adapters.lane;

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
    public ContentMask ContentMask;
    @JsonProperty("attributeMapsOffset")
    public Integer attributeMapsOffset;
}
