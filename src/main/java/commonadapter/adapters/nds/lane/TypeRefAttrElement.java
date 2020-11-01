
package commonadapter.adapters.nds.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    public AttributeTypesCodes attributeTypesCodes;
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("attrTypeOffset")
    public Integer attrTypeOffset;
}