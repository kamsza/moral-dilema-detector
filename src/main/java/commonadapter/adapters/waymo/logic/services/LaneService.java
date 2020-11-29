package commonadapter.adapters.waymo.logic.services;

import adapter.EntityPrx;
import adapter.Lane;
import adapter.LanePrx;
import commonadapter.adapters.waymo.logic.lidardata.Label;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaneService {

    private final IceProxyService iceProxyService;

    private final Map<Integer, LanePrx> lanes;

    private final int FIXED_LANE_WIDTH = 375;

    public LaneService(IceProxyService proxyService) {

        this.iceProxyService = proxyService;
        lanes = new HashMap<>();
    }

    public LanePrx getLaneForEntity(EntityPrx entityPrx, Label label) {

        int laneNo = calculateLaneNumber(label.box.centerY);
        return lanes.get(laneNo);
    }

    public void initializeLanes(List<Label> labels) {

        Double maxY = labels.stream()
                .map(label -> label.box.centerY)
                .max(Double::compareTo)
                .get();

        Double minY = labels.stream()
                .map(label -> label.box.centerY)
                .min(Double::compareTo)
                .get();

        int minLaneNo = calculateLaneNumber(minY);
        int maxLaneNo = calculateLaneNumber(maxY);

        for (int i = minLaneNo; i <= maxLaneNo; i++) {

            LanePrx newLanePrx = iceProxyService.createLanePrx();
            newLanePrx.setWidth(FIXED_LANE_WIDTH);
            newLanePrx.setLaneNumber(i);
            lanes.put(i, newLanePrx);

            Logger.printLogMessage("created lane no. " + i + ": " + newLanePrx.getId(), LogMessageType.INFO);
        }
    }

    private int calculateLaneNumber(Double centerY) {

        boolean right = centerY < 0;

        double halfFixedLaneWidth = FIXED_LANE_WIDTH / 2.0;

        double abs = Math.abs(centerY);

        int laneNo = 0;

        while (abs - halfFixedLaneWidth > laneNo * FIXED_LANE_WIDTH) {

            laneNo++;
        }

        if (right)
            laneNo = -laneNo;

        return laneNo;
    }
}
