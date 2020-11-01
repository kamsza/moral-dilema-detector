package commonadapter.adapters.lane;
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numVx4"
})
public class StartPosition {
    @JsonProperty("numVx4")
    public Integer numVx4;
}