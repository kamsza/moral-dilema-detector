package DilemmaDetector.Simulator;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import project.Weather;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class ChangeLineApplierTest {
    private static final double MOVING_TIME = 5.0;
    private static final double TIME_PART = 0.01;

    @Test
    public void changeLineTest(){
        ChangeLaneActionApplier changeLaneActionApplier = new ChangeLaneActionApplier();
        int startLaneNumber = 0;
        int endLaneNumber = 3;
        int laneWidth = 3;
        double currentTime = 0.0;
        RigidBody rigidBody = new RigidBody();
        rigidBody.setPosition(new Vector2(0,0));
        rigidBody.setSpeed(new Vector2(20, 0));
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            changeLaneActionApplier.CarChangeLanes(rigidBody, mock(Weather.class), startLaneNumber, endLaneNumber , laneWidth);
            rigidBody.update(TIME_PART);
        }
        Assert.assertEquals(endLaneNumber*laneWidth, rigidBody.getPosition().y,  0.1);

        endLaneNumber=0;
        rigidBody.setPosition(new Vector2(0,0));
        rigidBody.setSpeed(new Vector2(20, 0));
        currentTime = 0.0;
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            changeLaneActionApplier.CarChangeLanes(rigidBody, mock(Weather.class), startLaneNumber, endLaneNumber , laneWidth);
            rigidBody.update(TIME_PART);
        }
        Assert.assertEquals(endLaneNumber*laneWidth, rigidBody.getPosition().y,  0.1);
    }
}

