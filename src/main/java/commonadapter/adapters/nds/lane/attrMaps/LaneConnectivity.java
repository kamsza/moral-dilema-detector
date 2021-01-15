package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sourceLaneConnectorId",
        "destLaneConnectorId"
})
public class LaneConnectivity {
    @JsonProperty("sourceLaneConnectorId")
    public Integer sourceLaneConnectorId;
    @JsonProperty("destLaneConnectorId")
    public Integer destLaneConnectorId;
}