package DilemmaDetector.Consequences;

import project.Decision;
import project.Living_entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IConsequenceContainer {
    void saveConsequencesToOntology();
    void addHealthConsequence(Decision decision, Living_entity livingEntity, ConsequenceType consequenceType);
    void addMaterialConsequence(Decision decision, String damagedEntityName, double value);
    List<String> getHealthConsequencesOfType(Decision decision, ConsequenceType consequenceType);
    Set<Map.Entry<String, Double>> getMaterialConsequences(Decision decision);
}
