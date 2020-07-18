
package mapper.model.waymo.lidar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "speedX",
    "speedY",
    "accelX",
    "accelY"
})
public class Metadata {

    @JsonProperty("speedX")
    public Double speedX;
    @JsonProperty("speedY")
    public Double speedY;
    @JsonProperty("accelX")
    public Double accelX;
    @JsonProperty("accelY")
    public Double accelY;
}
