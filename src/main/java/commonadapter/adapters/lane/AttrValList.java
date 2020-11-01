
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attrRefHeader",
        "values"
})
public class AttrValList {
    @JsonProperty("attrRefHeader")
    public AttrRefHeader attrRefHeader;
    @JsonProperty("values")
    public Values values;
}