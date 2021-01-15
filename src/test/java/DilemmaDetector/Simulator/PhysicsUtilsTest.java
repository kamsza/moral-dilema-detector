package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Test;

public class PhysicsUtilsTest {

    @Test
    public void CmToMetersTest() {
        Assert.assertEquals(0, PhysicsUtils.CmToMeters(0), 0.01);
        Assert.assertEquals(10, PhysicsUtils.CmToMeters(1000), 0.01);
        Assert.assertEquals(9.9, PhysicsUtils.CmToMeters(990), 0.01);
    }

    @Test
    public void KmphToMetersTest() {
        Assert.assertEquals(0, PhysicsUtils.KmphToMeters(0), 0.01);
        Assert.assertEquals(20, PhysicsUtils.KmphToMeters(72), 0.01);
    }

    @Test
    public void MetersToKmphTest() {
        Assert.assertEquals(0, PhysicsUtils.MetersToKmph(0), 0.01);
        Assert.assertEquals(72, PhysicsUtils.MetersToKmph(20), 0.01);
    }

    @Test
    public void GetRelativeSpeedTest(){
        Assert.assertEquals(new Vector2(0,0), PhysicsUtils.GetRelativeSpeed(new Vector2(0,0), new Vector2(0,0)));
        Assert.assertEquals(new Vector2(10,5), PhysicsUtils.GetRelativeSpeed(new Vector2(10,5), new Vector2(0,0)));
        Assert.assertEquals(new Vector2(-10,-5), PhysicsUtils.GetRelativeSpeed(new Vector2(0,0), new Vector2(10,5)));
        Assert.assertEquals(new Vector2(20,20), PhysicsUtils.GetRelativeSpeed(new Vector2(40,10), new Vector2(20,-10)));
    }

    @Test
    public void Kmph2ToMps2Test(){
        Assert.assertEquals(0, PhysicsUtils.Kmph2ToMps2(0), 0);
        Assert.assertEquals(1, PhysicsUtils.Kmph2ToMps2(12960), 0);
        Assert.assertEquals(1082.76639, PhysicsUtils.Kmph2ToMps2(14032652.54), 0.01);
    }
}
