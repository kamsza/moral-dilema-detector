package commonadapter.adapters.waymo;

import commonadapter.adapters.waymo.logic.services.IceProxyService;
import commonadapter.adapters.waymo.logic.services.LaneService;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;

public class LaneAssignmentTest {

    private LaneService laneService;
    private IceProxyService proxyService;

    @Before
    public void setup() {
        proxyService = mock(IceProxyService.class);
        laneService = new LaneService(proxyService);
    }

    @Test
    public void shouldCalculateLaneNumberForEntitiesOnTheLeft() {
        Double centerY0 = 400d; // left side
        Double centerY1 = 755d; // left side
        Double centerY2 = 1022d; // left side
        
        int laneNo0 = laneService.calculateLaneNumber(centerY0);
        int laneNo1 = laneService.calculateLaneNumber(centerY1);
        int laneNo2 = laneService.calculateLaneNumber(centerY2);

        assertEquals(1, laneNo0);
        assertEquals(2, laneNo1);
        assertEquals(3, laneNo2);
    }

    @Test
    public void shouldCalculateLaneNumberForEntitiesOnTheRight() {

        Double centerY0 = -424d; // right side
        Double centerY1 = -733d; // right side
        Double centerY2 = -1077d; // right side

        int laneNo0 = laneService.calculateLaneNumber(centerY0);
        int laneNo1 = laneService.calculateLaneNumber(centerY1);
        int laneNo2 = laneService.calculateLaneNumber(centerY2);

        assertEquals(-1, laneNo0);
        assertEquals(-2, laneNo1);
        assertEquals(-3, laneNo2);
    }

}