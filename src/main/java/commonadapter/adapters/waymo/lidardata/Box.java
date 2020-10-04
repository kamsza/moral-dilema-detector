
package commonadapter.adapters.waymo.lidardata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "centerX",
    "centerY",
    "width",
    "length"
})
public class Box {

    @JsonProperty("centerX")
    public Double centerX;
    @JsonProperty("centerY")
    public Double centerY;
    @JsonProperty("width")
    public Double width;
    @JsonProperty("length")
    public Double length;

}
