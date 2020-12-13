package commonadapter.adapters.nds.routing.links;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "length",
        "attrSource",
        "attrInfo",
        "averageSpeed",
        "startAngle",
        "endAngle"
})
public class LinkData {
    @JsonProperty("length")
    public Integer length;
    @JsonProperty("attrSource")
    public String attrSource;
    @JsonProperty("attrInfo")
    public AttrInfo attrInfo;
    @JsonProperty("averageSpeed")
    public Integer averageSpeed;
    @JsonProperty("startAngle")
    public Integer startAngle;
    @JsonProperty("endAngle")
    public Integer endAngle;
}