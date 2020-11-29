package DilemmaDetector.Consequences;

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
    private Decision createDecisionMock(String decisionName){
        Decision decisionMock = mock(Decision.class);
        when(decisionMock.toString()).thenReturn(decisionName);
        OWLNamedIndividual owlNamedIndividualMock = mock(OWLNamedIndividual.class);
        IRI iriMock = mock(IRI.class);
        when(decisionMock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
        when(owlNamedIndividualMock.getIRI()).thenReturn(iriMock);
        when(iriMock.toString()).thenReturn(decisionName);
        return decisionMock;
    }

    @Test
    public void safeDecisionCostTest(){
        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(mock(ConsequenceContainer.class), mock(MyFactory.class));
        Decision decisionMock = createDecisionMock("follow");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(0, cost);
    }

    @Test
    public void takingActionDecisionCostTest(){
        DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(mock(ConsequenceContainer.class), mock(MyFactory.class));
        decisionCostCalculator.setCustomPhilosophy(CustomPhilosophy.getSimplestPhilosophyWithOnesForTest());
        Decision decisionMock = createDecisionMock("turn_left");
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
        Decision decisionMock = createDecisionMock("follow");
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
        Decision decisionMock = createDecisionMock("follow");
        int cost = decisionCostCalculator.getSummarizedCostForDecision(decisionMock);
        Assert.assertEquals(3, cost);
    }
}
