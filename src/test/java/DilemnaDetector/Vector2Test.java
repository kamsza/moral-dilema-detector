package DilemnaDetector;

import DilemmaDetector.Simulator.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vector2Test {

    @Test
    public void addTest()
    {
        Vector2 v1 = new Vector2(1,1);
        Vector2 v2 = new Vector2(2,-1);
        assertEquals(v1.add(v2), new Vector2(3,0));
    }
}
