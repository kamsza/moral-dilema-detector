
package commonadapter.adapters.nds.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import commonadapter.adapters.nds.routing.fixedAttributes.AttributeList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "numAttributeSets",
    "attributeList"
})
public class FixedRoadAttributeSetList {

    @JsonProperty("numAttributeSets")
    public Integer numAttributeSets;
    @JsonProperty("attributeList")
    public AttributeList attributeList;
}
