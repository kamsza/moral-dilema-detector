package DilemmaDetector.Consequences;

import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.FactoryWrapper;
import DilemmaDetector.Simulator.RigidBody;
import DilemmaDetector.Simulator.Vector2;
import generator.Model;
import org.junit.Assert;
import org.junit.Test;
import project.Decision;
import project.Living_entity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CollisionConsequencePredictorTest {
    @Test
    public void createCollisionWithSurroundingConsequencesTest(){
        IConsequenceContainer consequenceContainerMock = mock(ConsequenceContainer.class);
        final var ref = new Object() {
            double moneyLost = 0;
            int injuredPeople = 0;
        };

        doAnswer(i -> ref.moneyLost += i.getArgument(2, Double.class)).when(consequenceContainerMock).addMaterialConsequence(any(), any(), anyDouble());
        doAnswer(i -> ref.injuredPeople++).when(consequenceContainerMock).addHealthConsequence(any(), any(), any());

        List<Living_entity> livingEntities = List.of(mock(Living_entity.class), mock(Living_entity.class));
        Actor actorMock = mock(Actor.class);
        when(actorMock.getValueInDollars()).thenReturn(1000.0);
        when(actorMock.getRigidBody()).thenReturn(new RigidBody(Vector2.zero(), new Vector2(115, 0), Vector2.zero(), Vector2.zero()));
        FactoryWrapper factoryWrapperMock = mock(FactoryWrapper.class);
        when(factoryWrapperMock.getLivingEntitiesFromActor(any())).thenReturn(livingEntities);

        CollisionConsequencePredictor collisionConsequencePredictor =
                new CollisionConsequencePredictor(consequenceContainerMock, mock(Model.class));
        collisionConsequencePredictor.setFactoryWrapper(factoryWrapperMock);
        collisionConsequencePredictor.createCollisionConsequences(mock(Decision.class), actorMock);
        Assert.assertEquals(2, ref.injuredPeople);
        Assert.assertEquals(1000, ref.moneyLost, 0.1);
    }
}
