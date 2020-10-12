package DilemmaDetector.Consequences;

import project.Decision;
import project.Living_entity;

public interface IConsequenceContainer {
    void saveConsequencesToOntology();
    void addHealthConsequence(Decision decision, Living_entity livingEntity, ConsequenceType consequenceType);
    void addMaterialConsequence(Decision decision, String damagedEntityName, double value);

}
