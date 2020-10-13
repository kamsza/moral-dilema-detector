package DilemmaDetector.Consequences;

import generator.ObjectNamer;
import project.*;

import java.util.*;

public class ConsequenceContainer implements IConsequenceContainer {
    private Map<String, Map<ConsequenceType, Set<String>>> healthConsequences = new HashMap<>(); //<Decision, <Consequence, Set<VictimName>>>
    private Map<String, Map<String, Double>> materialConsequences = new HashMap<>();
    private MyFactory factory;

    public ConsequenceContainer(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public void saveConsequencesToOntology() {
        for(String decisionName : healthConsequences.keySet()) {
            Decision decision = factory.getDecision(decisionName);
            Killed killed = factory.createKilled(ObjectNamer.getName("killed"));
            decision.addHas_consequence(killed);
            Severly_injured severelyInjured = factory.createSeverly_injured(ObjectNamer.getName("severely_injured"));
            decision.addHas_consequence(severelyInjured);
            Lightly_injured lightlyInjured = factory.createLightly_injured(ObjectNamer.getName("lightly_injured"));
            decision.addHas_consequence(lightlyInjured);
            for(String victimName : healthConsequences.get(decisionName).get(ConsequenceType.KILLED)) {
                Living_entity livingEntity = factory.getLiving_entity(victimName);
                killed.addHealth_consequence_to(livingEntity);
            }
            for(String victimName : healthConsequences.get(decisionName).get(ConsequenceType.SEVERELY_INJURED)) {
                Living_entity livingEntity = factory.getLiving_entity(victimName);
                severelyInjured.addHealth_consequence_to(livingEntity);
            }
            for(String victimName : healthConsequences.get(decisionName).get(ConsequenceType.LIGHTLY_INJURED)) {
                Living_entity livingEntity = factory.getLiving_entity(victimName);
                lightlyInjured.addHealth_consequence_to(livingEntity);
            }
        }
    }

    @Override
    public void addHealthConsequence(Decision decision, Living_entity livingEntity, ConsequenceType consequenceType) {
        addNewDecision(decision);
        String victimName = livingEntity.getOwlIndividual().toStringID();
        Map<ConsequenceType, Set<String>> decisionHealthConsequences
                = healthConsequences.get(decision.getOwlIndividual().toStringID());
        //override existing injury with more serious injury
        if (consequenceType.equals(ConsequenceType.KILLED)) {
            decisionHealthConsequences.get(ConsequenceType.LIGHTLY_INJURED).remove(victimName);
            decisionHealthConsequences.get(ConsequenceType.SEVERELY_INJURED).remove(victimName);
        } else if (consequenceType.equals(ConsequenceType.SEVERELY_INJURED)) {
            if (decisionHealthConsequences.get(ConsequenceType.KILLED).contains(victimName)) {
                return;
            }
            decisionHealthConsequences.get(ConsequenceType.LIGHTLY_INJURED).remove(victimName);
        } else if (consequenceType.equals(ConsequenceType.LIGHTLY_INJURED))
        {
            if (decisionHealthConsequences.get(ConsequenceType.KILLED).contains(victimName)
                || decisionHealthConsequences.get(ConsequenceType.SEVERELY_INJURED).contains(victimName)) {
                return;
            }
        }else {
            return;
        }
        decisionHealthConsequences.get(consequenceType).add(victimName);
    }

    @Override
    public void addMaterialConsequence(Decision decision, String damagedEntityName, double value) {
        if (materialConsequences.get(decision.getOwlIndividual().toStringID()).get(damagedEntityName) < value)
            materialConsequences.get(decision.getOwlIndividual().toStringID()).put(damagedEntityName, value);
    }

    @Override
    public List<String> getHealthConsequencesOfType(Decision decision, ConsequenceType consequenceType) {
        return new LinkedList<>(healthConsequences.get(decision.getOwlIndividual().toStringID()).get(consequenceType));
    }

    @Override
    public Set<Map.Entry<String, Double>> getMaterialConsequences(Decision decision) {
        return materialConsequences.get(decision.getOwlIndividual().toStringID()).entrySet();
    }

    private void addNewDecision(Decision decision) {
        String decisionName = decision.getOwlIndividual().toStringID();
        if (!materialConsequences.containsKey(decisionName)) {
            materialConsequences.put(decisionName, new HashMap<>());
        }
        if (!healthConsequences.containsKey(decisionName)) {
            healthConsequences.put(decisionName, new HashMap<>());
            healthConsequences.get(decisionName).put(ConsequenceType.KILLED, new HashSet<>());
            healthConsequences.get(decisionName).put(ConsequenceType.SEVERELY_INJURED, new HashSet<>());
            healthConsequences.get(decisionName).put(ConsequenceType.LIGHTLY_INJURED, new HashSet<>());
        }
    }
}
