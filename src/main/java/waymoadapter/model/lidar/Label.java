
package waymoadapter.model.lidar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "box",
    "metadata",
    "type",
    "id",
        "detectionDifficultyLevel",
        "trackingDifficultyLevel"
})
public class Label {

    @JsonProperty("box")
    public Box box;
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("type")
    public Type type;
    @JsonProperty("id")
    public String id;
    @JsonProperty("detectionDifficultyLevel")
    public String detectionDifficultyLevel;
    @JsonProperty("trackingDifficultyLevel")
    public String trackingDifficultyLevel;
}
