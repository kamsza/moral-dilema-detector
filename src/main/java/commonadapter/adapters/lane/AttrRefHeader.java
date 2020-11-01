
package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numAttrCodes",
        "attributeTypeCodes",
        "data",
        "referenceType",
        "attrTypeOffset"
})
public class AttrRefHeader {
    @JsonProperty("numAttrCodes")
    public Integer numAttrCodes;
    @JsonProperty("attributeTypeCodes")
    public AttributeTypeCodes attributeTypeCodes;
    @JsonProperty("referenceType")
    public String referenceType;
    @JsonProperty("attrTypeOffset")
    public Integer attrTypeOffset;
}