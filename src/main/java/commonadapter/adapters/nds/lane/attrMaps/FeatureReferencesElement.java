package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "guidanceFeatureType",
        "baseLinkReference",
        "id"
})
public class FeatureReferencesElement {
    @JsonProperty("guidanceFeatureType")
    public String guidanceFeatureType;
    @JsonProperty("baseLinkReference")
    public BaseLinkReference baseLinkReference;
    @JsonProperty("id")
    public Integer id;
}