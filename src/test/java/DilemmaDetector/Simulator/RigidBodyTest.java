package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RigidBodyTest {
    private RigidBody rigidBody;

    @Before
    public void init(){
        Vector2 position = Vector2.zero();
        Vector2 speed = Vector2.zero();
        Vector2 acceleration = Vector2.zero();
        Vector2 previousPosition = Vector2.zero();
        rigidBody = new RigidBody(position, speed, acceleration, previousPosition);
    }

    @Test
    public void standingStillUpdateTest() {
        rigidBody.update(1.0);
        Assert.assertEquals(Vector2.zero(), rigidBody.getSpeed());
        Assert.assertEquals(Vector2.zero(), rigidBody.getPosition());
        Assert.assertEquals(Vector2.zero(), rigidBody.getAcceleration());
        Assert.assertEquals(Vector2.zero(), rigidBody.getPreviousPosition());
    }

    @Test
    public void uniformMotionUpdateTest() {
        rigidBody.setSpeed(new Vector2(10, -10));

        rigidBody.update(0.5);
        Assert.assertEquals(new Vector2(10, -10), rigidBody.getSpeed());
        Assert.assertEquals(new Vector2(5, -5), rigidBody.getPosition());
        Assert.assertEquals(Vector2.zero(), rigidBody.getAcceleration());
        Assert.assertEquals(Vector2.zero(), rigidBody.getPreviousPosition());

        rigidBody.update(0.5);
        Assert.assertEquals(new Vector2(10, -10), rigidBody.getSpeed());
        Assert.assertEquals(new Vector2(10, -10), rigidBody.getPosition());
        Assert.assertEquals(Vector2.zero(), rigidBody.getAcceleration());
        Assert.assertEquals(new Vector2(5, -5), rigidBody.getPreviousPosition());
    }

    @Test
    public void acceleratedMotionUpdateTest() {
        rigidBody.setSpeed(new Vector2(10, -10));
        rigidBody.setAcceleration(new Vector2(10, 20));

        rigidBody.update(1.0);
        Assert.assertEquals(new Vector2(20, 10), rigidBody.getSpeed());
        Assert.assertEquals(new Vector2(15, 0), rigidBody.getPosition());
        Assert.assertEquals(new Vector2(10, 20), rigidBody.getAcceleration());
        Assert.assertEquals(Vector2.zero(), rigidBody.getPreviousPosition());

        rigidBody.update(1.0);
        Assert.assertEquals(new Vector2(30, 30), rigidBody.getSpeed());
        Assert.assertEquals(new Vector2(40, 20), rigidBody.getPosition());
        Assert.assertEquals(new Vector2(10, 20), rigidBody.getAcceleration());
        Assert.assertEquals(new Vector2(15, 0), rigidBody.getPreviousPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDeltaTimeUpdateTest() {
        rigidBody.update(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroDeltaTimeUpdateTest() {
        rigidBody.update(0);
    }
}
