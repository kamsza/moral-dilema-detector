
package mapper.model.waymo.camera;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "labels"
})
public class CameraLabel {

    @JsonProperty("name")
    public String name;
    @JsonProperty("labels")
    public List<Label> labels = null;

}
