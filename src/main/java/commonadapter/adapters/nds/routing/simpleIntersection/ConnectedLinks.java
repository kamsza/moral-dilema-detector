package commonadapter.adapters.nds.routing.simpleIntersection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data"
})
public class ConnectedLinks {
    @JsonProperty("data")
    public List<ConnectedLinksData> data;
}