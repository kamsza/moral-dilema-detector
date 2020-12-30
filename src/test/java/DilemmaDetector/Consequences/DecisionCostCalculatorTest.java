package DilemmaDetector.Consequences;

import DilemmaDetector.GeneratedClassesMocks;
import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import project.Decision;
import project.Human;
import project.MyFactory;

import java.util.*;

import static org.mockito.Mockito.*;

public class DecisionCostCalculatorTest {
    private GeneratedClassesMocks generatedClassesMocks = new GeneratedClassesMocks();

    @Test
    public void safeDecisionCostTest(){
        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(mock(ConsequenceContainer.class), mock(MyFactory.class));
        Decision decisionMock = generatedClassesMocks.createWrappedIndividualMock("follow", "Decision");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(0, cost);
    }

    @Test
    public void takingActionDecisionCostTest(){
        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(mock(ConsequenceContainer.class), mock(MyFactory.class));
        decisionCostCalculator.setCustomPhilosophy(CustomPhilosophy.getSimplestPhilosophyWithOnesForTest());
        Decision decisionMock = generatedClassesMocks.createWrappedIndividualMock("turn_left", "Decision");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(1, cost);
    }

    @Test
    public void decisionWithMaterialConsequencesCostTest(){
        IConsequenceContainer consequenceContainerMock = mock(ConsequenceContainer.class);
        Map<String, Double> map = new HashMap<>();
        map.put("vehicle", 10000.0);
        when(consequenceContainerMock.getMaterialConsequences(any())).thenReturn(map.entrySet());

        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(consequenceContainerMock, mock(MyFactory.class));
        decisionCostCalculator.setCustomPhilosophy(CustomPhilosophy.getSimplestPhilosophyWithOnesForTest());
        Decision decisionMock = generatedClassesMocks.createWrappedIndividualMock("follow", "Decision");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(10000/1000, cost);
    }

    @Test
    public void decisionWithHealthConsequencesCostTest(){
        IConsequenceContainer consequenceContainerMock = mock(ConsequenceContainer.class);
        when(consequenceContainerMock.getHealthConsequencesOfType(any(), any())).thenReturn(List.of("pedestrian"));
        MyFactory myFactoryMock = mock(MyFactory.class);
        when(myFactoryMock.getHuman(anyString())).thenReturn(mock(Human.class));

        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(consequenceContainerMock, myFactoryMock);
        decisionCostCalculator.setCustomPhilosophy(CustomPhilosophy.getSimplestPhilosophyWithOnesForTest());
        Decision decisionMock = generatedClassesMocks.createWrappedIndividualMock("follow", "Decision");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(3, cost);
    }
}
