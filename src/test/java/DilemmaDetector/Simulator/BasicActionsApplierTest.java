package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.*;

import static org.mockito.Mockito.mock;

public class BasicActionsApplierTest {
    private RigidBody rigidBody;
    private Weather weather;
    private double gravity = BasicActionsApplier.getGRAVITY();
    private final double deltaTime = 0.01;

    @Before
    public void init(){
        Vector2 position = Vector2.zero();
        Vector2 speed = Vector2.zero();
        Vector2 acceleration = Vector2.zero();
        Vector2 previousPosition = Vector2.zero();
        rigidBody = new RigidBody(position, speed, acceleration, previousPosition);
        weather = mock(Sunny.class);
    }

    @Test
    public void carBrakingTest(){
        rigidBody.setSpeed(new Vector2(gravity*0.9, 0));
        BasicActionsApplier.CarBraking(rigidBody, weather);
        rigidBody.update(1);
        Assert.assertEquals(Vector2.zero(), rigidBody.getSpeed());
    }

    @Test
    public void notMovingAfterFullBrakeTest(){
        rigidBody.setSpeed(new Vector2(gravity*0.9, 0));
        for(int i=0; i<102; i++){
            BasicActionsApplier.CarBraking(rigidBody, weather);
            rigidBody.update(deltaTime);
        }
        Assert.assertEquals(Vector2.zero(), rigidBody.getSpeed());
    }

    @Test
    public void carTurningLeftTest(){
        rigidBody.setSpeed(new Vector2(10, 0));
        for(int i=0; i<100; i++){
            BasicActionsApplier.CarTurning(rigidBody, weather, false);
            rigidBody.update(deltaTime);
        }
        Assert.assertTrue(rigidBody.getPosition().y > 4);
        Assert.assertTrue(rigidBody.getSpeed().y > 0);
        Assert.assertEquals(10, rigidBody.getSpeed().getMagnitude(), 0.1);
    }

    @Test
    public void carTurningRightTest(){
        rigidBody.setSpeed(new Vector2(10, 0));
        for(int i=0; i<100; i++){
            BasicActionsApplier.CarTurning(rigidBody, weather, true);
            rigidBody.update(deltaTime);
        }
        Assert.assertTrue(rigidBody.getPosition().y < 4);
        Assert.assertTrue(rigidBody.getSpeed().y < 0);
        Assert.assertEquals(10, rigidBody.getSpeed().getMagnitude(), 0.1);
    }

    @Test
    public void weatherConditionsTest(){
        rigidBody.setSpeed(new Vector2(10, 0));
        BasicActionsApplier.CarBraking(rigidBody, weather);
        Assert.assertEquals(new Vector2(-gravity*0.9, 0), rigidBody.getAcceleration());

        BasicActionsApplier.CarBraking(rigidBody, mock(Fog.class));
        Assert.assertEquals(new Vector2(-gravity*0.5, 0), rigidBody.getAcceleration());

        BasicActionsApplier.CarBraking(rigidBody, mock(Shower.class));
        Assert.assertEquals(new Vector2(-gravity*0.3, 0), rigidBody.getAcceleration());

        BasicActionsApplier.CarBraking(rigidBody, mock(Heavy_rain.class));
        Assert.assertEquals(new Vector2(-gravity*0.3, 0), rigidBody.getAcceleration());

        BasicActionsApplier.CarBraking(rigidBody, mock(Snow.class));
        Assert.assertEquals(new Vector2(-gravity*0.2, 0), rigidBody.getAcceleration());

        BasicActionsApplier.CarBraking(rigidBody, mock(Glaze.class));
        Assert.assertEquals(new Vector2(-gravity*0.13, 0), rigidBody.getAcceleration());
    }
}
