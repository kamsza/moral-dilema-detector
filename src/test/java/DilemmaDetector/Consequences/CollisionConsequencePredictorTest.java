package DilemmaDetector.Consequences;

import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.FactoryWrapper;
import DilemmaDetector.Simulator.RigidBody;
import DilemmaDetector.Simulator.Vector2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.Decision;
import project.Living_entity;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CollisionConsequencePredictorTest {
    private double moneyLost = 0;
    private int killed = 0;
    private int severely_injured = 0;
    private int lightly_injured = 0;

    private CollisionConsequencePredictor getCollisionConsequencePredictor(List<Living_entity> livingEntitiesIn1Actor, boolean isPedestrian){
        IConsequenceContainer consequenceContainerMock = mock(ConsequenceContainer.class);
        doAnswer(i -> moneyLost += i.getArgument(2, Double.class)).when(consequenceContainerMock).addMaterialConsequence(any(), any(), anyDouble());
        doAnswer(invocation -> {
            ConsequenceType healthConsequenceType = invocation.getArgument(2);
            switch (healthConsequenceType){
                case KILLED:
                    killed++;
                    break;
                case SEVERELY_INJURED:
                    severely_injured++;
                    break;
                case LIGHTLY_INJURED:
                    lightly_injured++;
                    break;
            }
            return null;
        }).when(consequenceContainerMock).addHealthConsequence(any(), any(), any());
        FactoryWrapper factoryWrapperMock = mock(FactoryWrapper.class);
        when(factoryWrapperMock.getLivingEntitiesFromActor(any())).thenReturn(livingEntitiesIn1Actor);
        if(isPedestrian){
            when(factoryWrapperMock.isPedestrian(any())).thenReturn(false, true);
        }
        else {
            when(factoryWrapperMock.isPedestrian(any())).thenReturn(false);
        }
        return new CollisionConsequencePredictor(consequenceContainerMock, factoryWrapperMock);
    }

    private Actor createActorMock(double value, Vector2 speed){
        Actor actorMock = mock(Actor.class);
        when(actorMock.getValueInDollars()).thenReturn(value);
        when(actorMock.getRigidBody()).thenReturn(new RigidBody(Vector2.zero(), speed, Vector2.zero(), Vector2.zero()));
        return actorMock;
    }

    @Before
    public void resetConsequences(){
        moneyLost = 0;
        killed = 0;
        severely_injured = 0;
        lightly_injured = 0;
    }

    @Test
    public void createCollisionWithSurroundingConsequencesTest(){
        CollisionConsequencePredictor collisionConsequencePredictor = getCollisionConsequencePredictor(List.of(mock(Living_entity.class), mock(Living_entity.class)), false);
        Actor actorMock = createActorMock(1000, new Vector2(115,0));
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock);

        Assert.assertEquals(2, killed);
        Assert.assertEquals(1000, moneyLost, 0.1);
    }

    @Test
    public void createCollisionWithOtherActorConsequencesTest(){
        CollisionConsequencePredictor collisionConsequencePredictor = getCollisionConsequencePredictor(List.of(mock(Living_entity.class), mock(Living_entity.class), mock(Living_entity.class)), false);
        Actor actorMock1 = createActorMock(500, new Vector2(100,0));
        Actor actorMock2 = createActorMock(1000, new Vector2(-20,0));
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock1, actorMock2);
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock2, actorMock1);

        Assert.assertEquals(6, killed);
        Assert.assertEquals(1500, moneyLost, 0.1);
    }

    @Test
    public void highSpeedCollisionTest(){
        CollisionConsequencePredictor collisionConsequencePredictor = getCollisionConsequencePredictor(List.of(mock(Living_entity.class)), false);
        Actor actorMock1 = createActorMock(0, new Vector2(100,0));
        Actor actorMock2 = createActorMock(0, new Vector2(-20,0));
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock1, actorMock2);
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock2, actorMock1);

        Assert.assertEquals(2, killed);
        Assert.assertEquals(0, severely_injured);
        Assert.assertEquals(0, lightly_injured);
    }

    @Test
    public void lowSpeedCollisionTest(){
        CollisionConsequencePredictor collisionConsequencePredictor = getCollisionConsequencePredictor(List.of(mock(Living_entity.class)), false);
        Actor actorMock1 = createActorMock(0, new Vector2(10,0));
        Actor actorMock2 = createActorMock(0, new Vector2(0,0));
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock1, actorMock2);
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock2, actorMock1);

        Assert.assertEquals(0, killed);
        Assert.assertEquals(0, severely_injured);
        Assert.assertEquals(2, lightly_injured);
    }

    @Test
    public void pedestrianCollisionTest(){
        CollisionConsequencePredictor collisionConsequencePredictor = getCollisionConsequencePredictor(List.of(mock(Living_entity.class)), true);
        Actor actorMock1 = createActorMock(0, new Vector2(20,0));
        Actor actorMock2 = createActorMock(0, new Vector2(0,0));
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock1, actorMock2);
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock2, actorMock1);

        Assert.assertEquals(1, killed);
        Assert.assertEquals(0, severely_injured);
        Assert.assertEquals(1, lightly_injured);
    }
}
