
package commonadapter.adapters.nds.lane.attrMaps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numAttrCodes",
        "attributeTypeCodes",
        "referenceType",
        "attrTypeOffset"
})
public class TypeRefAttrElement {
    @JsonProperty("numAttrCodes")
    public Integer numAttrCodes;
    @JsonProperty("attributeTypeCodes")
    public AttributeTypeCodes attributeTypesCodes;
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("attrTypeOffset")
    public Integer attrTypeOffset;
}