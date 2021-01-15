package DilemmaDetector.Consequences;

import DilemmaDetector.GeneratedClassesMocks;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.Decision;
import project.Living_entity;
import project.OWLFactory;

import java.util.Map;
import java.util.Set;

public class ConsequenceContainerTest {
    OWLFactory factory;
    Decision decision;
    Living_entity livingEntity;
    GeneratedClassesMocks generatedClassesMocks = new GeneratedClassesMocks();

    @Before
    public void Init() {
        livingEntity = generatedClassesMocks.createWrappedIndividualMock("living_entity", "Living_entity");
        factory = generatedClassesMocks.createFactoryMock();
        decision = generatedClassesMocks.createWrappedIndividualMock("follow", "Decision");
    }

    @Test
    public void ConsequenceContainerShouldAddMaterialConsequencesToDecisions() {
        ConsequenceContainer container = new ConsequenceContainer(factory);
        String entityName = "damaged_entity";
        String entityName2 = "damaged_entity2";
        container.addMaterialConsequence(decision, entityName, 1000);
        container.addMaterialConsequence(decision, entityName2, 2000);
        Set<Map.Entry<String, Double>> consequences = container.getMaterialConsequences(decision);
        Assert.assertEquals(2, consequences.size());
    }

    @Test
    public void ConsequenceContainerShouldOverrideMaterialConsequencesWithConsequenceWithBiggerValue() {
        ConsequenceContainer container = new ConsequenceContainer(factory);
        String entityName = "damaged_entity";
        float biggerValue = 2000;
        container.addMaterialConsequence(decision, entityName, biggerValue / 2.);
        container.addMaterialConsequence(decision, entityName, biggerValue);
        container.addMaterialConsequence(decision, entityName, biggerValue / 2.);
        Set<Map.Entry<String, Double>> consequences = container.getMaterialConsequences(decision);
        Assert.assertEquals(1, consequences.size());
        for (Map.Entry<String, Double> entry : consequences) {
            if (entry.getKey().equals(entityName)) {
                Assert.assertEquals(biggerValue, entry.getValue(), 1);
            }
        }
    }

    @Test
    public void ConsequenceContainerShouldAddHealthConsequencesToDecisions() {
        ConsequenceContainer container = new ConsequenceContainer(factory);
        Living_entity livingEntity2 = generatedClassesMocks.createWrappedIndividualMock("living_entity2", "Living_entity");
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.LIGHTLY_INJURED);
        container.addHealthConsequence(decision, livingEntity2, ConsequenceType.SEVERELY_INJURED);
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.LIGHTLY_INJURED).size());
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.SEVERELY_INJURED).size());
    }

    @Test
    public void ConsequenceContainerShouldOverrideHealthConsequencesWithConsequenceMoreSeriousVariousValue() {
        ConsequenceContainer container = new ConsequenceContainer(factory);
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.LIGHTLY_INJURED);
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.LIGHTLY_INJURED).size());
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.SEVERELY_INJURED);
        Assert.assertEquals(0, container.getHealthConsequencesOfType(decision, ConsequenceType.LIGHTLY_INJURED).size());
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.SEVERELY_INJURED).size());
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.KILLED);
        Assert.assertEquals(0, container.getHealthConsequencesOfType(decision, ConsequenceType.SEVERELY_INJURED).size());
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.KILLED).size());
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.SEVERELY_INJURED);
        Assert.assertEquals(0, container.getHealthConsequencesOfType(decision, ConsequenceType.SEVERELY_INJURED).size());
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.KILLED).size());
        container.addHealthConsequence(decision, livingEntity, ConsequenceType.LIGHTLY_INJURED);
        Assert.assertEquals(0, container.getHealthConsequencesOfType(decision, ConsequenceType.LIGHTLY_INJURED).size());
        Assert.assertEquals(1, container.getHealthConsequencesOfType(decision, ConsequenceType.KILLED).size());
    }
}
