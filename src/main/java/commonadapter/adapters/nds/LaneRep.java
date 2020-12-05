package commonadapter.adapters.nds;

import com.fasterxml.jackson.annotation.JsonProperty;
import commonadapter.adapters.nds.lane.attrMaps.BoundaryElements;
import commonadapter.adapters.nds.lane.attrMaps.LaneConnectivityElements;

public class LaneRep {
    public LaneConnectivityElements laneConnectivityElements;
    public Boolean hasLaneBoundaries;
    public BoundaryElements boundaryElements;

    public LaneRep(LaneConnectivityElements laneConnectivityElements, Boolean hasLaneBoundaries, BoundaryElements boundaryElements){
        this.laneConnectivityElements = laneConnectivityElements;
        this.hasLaneBoundaries = hasLaneBoundaries;
        this.boundaryElements = boundaryElements;
    }
}
