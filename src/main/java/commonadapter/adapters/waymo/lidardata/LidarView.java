
package commonadapter.adapters.waymo.lidardata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "labels"
})
public class LidarView {

    @JsonProperty("name")
    public VehicleSide name;
    @JsonProperty("labels")
    public List<Label> labels = null;
}
