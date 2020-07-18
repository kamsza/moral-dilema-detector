
package mapper.model.waymo.camera;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "box",
    "type",
    "id"
})
public class Label {

    @JsonProperty("box")
    public Box box;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;
}
