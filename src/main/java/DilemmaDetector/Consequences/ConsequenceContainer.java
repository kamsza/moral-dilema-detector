package DilemmaDetector.Consequences;

import com.github.jsonldjava.utils.Obj;
import generator.ObjectNamer;
import project.*;

import java.util.*;

public class ConsequenceContainer implements IConsequenceContainer {
    private Map<String, Map<ConsequenceType, Set<String>>> healthConsequencesByDecisionMap = new HashMap<>(); //<Decision, <Consequence, Set<VictimName>>>
    private Map<String, Map<String, Double>> materialConsequencesByDecisionMap = new HashMap<>();
    private MyFactory factory;

    public ConsequenceContainer(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public void saveConsequencesToOntology() {
        for(String decisionName : healthConsequencesByDecisionMap.keySet()) {
            Killed killed = factory.createKilled(ObjectNamer.getName("killed"));
            Severly_injured severelyInjured = factory.createSeverly_injured(ObjectNamer.getName("severely_injured"));
            Lightly_injured lightlyInjured = factory.createLightly_injured(ObjectNamer.getName("lightly_injured"));

            saveHealthConsequence(decisionName, killed, ConsequenceType.KILLED);
            saveHealthConsequence(decisionName, severelyInjured, ConsequenceType.SEVERELY_INJURED);
            saveHealthConsequence(decisionName, lightlyInjured, ConsequenceType.LIGHTLY_INJURED);
        }

        for (String decisionName: materialConsequencesByDecisionMap.keySet()){
            Set<Map.Entry<String, Double>> entries = materialConsequencesByDecisionMap.get(decisionName).entrySet();
            for(Map.Entry<String, Double> entityConsequence : entries){
                Material_consequence material_consequence = factory.createMaterial_consequence(ObjectNamer.getName("material_consequence"));
                saveMaterialConsequence(decisionName, material_consequence, entityConsequence.getKey(), entityConsequence.getValue());
            }

        }
    }

    private void saveMaterialConsequence(String decisionName, Material_consequence consequence, String entityName, double value){
        Decision decision = factory.getDecision(decisionName);
        decision.addHas_consequence(consequence);
        Set<Map.Entry<String, Double>> entries = materialConsequencesByDecisionMap.get(decisionName).entrySet();
        for(Map.Entry<String, Double> entityConsequence : entries ){
            Entity entity = factory.getEntity(entityConsequence.getKey());
            consequence.addHas_material_value((float)value);
            consequence.addMaterial_consequence_to(entity);
        }

    }

    private void saveHealthConsequence(String decisionName, Health_consequence consequence, ConsequenceType consequenceType){
        Decision decision = factory.getDecision(decisionName);
        decision.addHas_consequence(consequence);
        for(String victimName : healthConsequencesByDecisionMap.get(decisionName).get(consequenceType)) {
            Living_entity livingEntity = factory.getLiving_entity(victimName);
            consequence.addHealth_consequence_to(livingEntity);
        }
    }

    @Override
    public void addHealthConsequence(Decision decision, Living_entity livingEntity, ConsequenceType consequenceType) {
        addNewDecision(decision);
        String victimName = livingEntity.getOwlIndividual().getIRI().toString();
        Map<ConsequenceType, Set<String>> decisionHealthConsequences
                = healthConsequencesByDecisionMap.get(decision.getOwlIndividual().getIRI().toString());
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
        if (materialConsequencesByDecisionMap.get(decision.getOwlIndividual().getIRI().toString()).get(damagedEntityName) == null){
            Map<String, Double> entityConsequence = new HashMap<>();
            entityConsequence.put(damagedEntityName, value);
            materialConsequencesByDecisionMap.put(decision.getOwlIndividual().getIRI().toString(), entityConsequence);
        }
        else {
            materialConsequencesByDecisionMap.get(decision.getOwlIndividual().getIRI().toString()).put(damagedEntityName, value);
        }
    }

    @Override
    public List<String> getHealthConsequencesOfType(Decision decision, ConsequenceType consequenceType) {
        if (!healthConsequencesByDecisionMap.containsKey(decision.getOwlIndividual().getIRI().toString())){
            return new LinkedList<>();
        }
        return new LinkedList<>(healthConsequencesByDecisionMap.get(decision.getOwlIndividual().getIRI().toString()).get(consequenceType));
    }

    @Override
    public Set<Map.Entry<String, Double>> getMaterialConsequences(Decision decision) {
        return materialConsequencesByDecisionMap
                .getOrDefault(decision.getOwlIndividual().getIRI().toString(), new HashMap<>())
                .entrySet();
    }

    private void addNewDecision(Decision decision) {
        String decisionName = decision.getOwlIndividual().getIRI().toString();
        if (!materialConsequencesByDecisionMap.containsKey(decisionName)) {
            materialConsequencesByDecisionMap.put(decisionName, new HashMap<>());
        }
        if (!healthConsequencesByDecisionMap.containsKey(decisionName)) {
            healthConsequencesByDecisionMap.put(decisionName, new HashMap<>());
            healthConsequencesByDecisionMap.get(decisionName).put(ConsequenceType.KILLED, new HashSet<>());
            healthConsequencesByDecisionMap.get(decisionName).put(ConsequenceType.SEVERELY_INJURED, new HashSet<>());
            healthConsequencesByDecisionMap.get(decisionName).put(ConsequenceType.LIGHTLY_INJURED, new HashSet<>());
        }
    }
}
