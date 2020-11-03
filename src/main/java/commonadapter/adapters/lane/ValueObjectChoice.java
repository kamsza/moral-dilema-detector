package commonadapter.adapters.lane;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startPosition",
        "endPosition",
        "id",
        "hasReferenceAcrossFeatures",
        "reference",
        "numLaneConnectivityElements",
        "laneConnectivityElements",
        "hasLaneBoundaries",
        "boundaryElements",
        "hasLaneGroupId",
        "laneGroupId",
        "numLanes",
        "ranges",
        "numVertices",
        "attributePoints"
})
public class ValueObjectChoice {
    @JsonProperty("startPosition")
    public StartPosition startPosition;
    @JsonProperty("endPosition")
    public EndPosition endPosition;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("hasReferenceAcrossFeatures")
    public Boolean hasReferenceAcrossFeatures;
    @JsonProperty("reference")
    public Reference reference;
    @JsonProperty("numLaneConnectivityElements")
    public Integer numLaneConnectivityElements;
    @JsonProperty("laneConnectivityElements")
    public LaneConnectivityElements laneConnectivityElements;
    @JsonProperty("hasLaneBoundaries")
    public Boolean hasLaneBoundaries;
    @JsonProperty("boundaryElements")
    public BoundaryElements boundaryElements;
    @JsonProperty("hasLaneGroupId")
    public Boolean hasLaneGroupId;
    @JsonProperty("laneGroupId")
    public Integer laneGroupId;
    @JsonProperty("numLanes")
    public Integer numLanes;
    @JsonProperty("ranges")
    public Ranges ranges;
    @JsonProperty("numVertices")
    public NumVertices numVertices;
    @JsonProperty("attributePoints")
    public AttributePoints attributePoints ;
}