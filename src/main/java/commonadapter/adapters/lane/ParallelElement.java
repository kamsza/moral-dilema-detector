package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numSequentialElements",
        "sequentialElements",
        "hasGeometry"
})
public class ParallelElement {
    @JsonProperty("numSequentialElements")
    public Integer numSequentialElements;
    @JsonProperty("sequentialElements")
    public SequentialElements sequentialElements;
    @JsonProperty("hasGeometry")
    public Boolean hasGeometry;
}
