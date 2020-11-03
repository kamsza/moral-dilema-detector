package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "laneBoundarySource",
        "numParallelElements",
        "parallelElements"
})
public class BoundaryElement {
    @JsonProperty("laneBoundarySource")
    public String laneBoundarySource;
    @JsonProperty("numParallelElements")
    public Integer numParallelElements;
    @JsonProperty("parallelElements")
    public ParallelElements parallelElements;
}