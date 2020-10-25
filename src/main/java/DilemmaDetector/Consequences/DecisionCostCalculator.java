package DilemmaDetector.Consequences;

import DilemmaDetector.ParameterizedPhilosophy;
import project.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecisionCostCalculator {
    private IConsequenceContainer consequenceContainer;
    private MyFactory factory;

    public DecisionCostCalculator(IConsequenceContainer consequenceContainer, MyFactory factory) {
        this.consequenceContainer = consequenceContainer;
        this.factory = factory;
    }

    public int calculateCostForDecision(Decision decision){
        int healthConsequenceResult = calculateCostOfHealthConsequence(decision);
        int materialConsequenceResult = calculateCostOfMaterialConsequence(decision);
        int result = healthConsequenceResult + materialConsequenceResult;
        System.out.println("DECISION : " + decision.getOwlIndividual().getIRI().toString() + " | POINTS: " + result +
                " | = (HEALTH: " + healthConsequenceResult+  " MATERIAL: " + materialConsequenceResult + ")");
        return result;
    }

    private int calculateCostOfMaterialConsequence(Decision decision) {
        int sum = 0;
        Set<Map.Entry<String, Double>> consequences = consequenceContainer.getMaterialConsequences(decision);
        for(Map.Entry<String, Double> consequence : consequences){
            sum += (int) Math.round(consequence.getValue());
        }
        return (int) (sum * ParameterizedPhilosophy.materialFactor * ParameterizedPhilosophy.materialFactor) / 1000  ;
    }

    private int calculateCostOfHealthConsequence(Decision decision){
        int result = 0;
        for(ConsequenceType consequenceType :
                new ConsequenceType[]{
                        ConsequenceType.KILLED,
                        ConsequenceType.SEVERELY_INJURED,
                        ConsequenceType.LIGHTLY_INJURED}) {
            result += calculateCostOfHealthConsequenceOfType(decision, consequenceType);
        }
        return result;
    }

    private int calculateCostOfHealthConsequenceOfType(Decision decision, ConsequenceType consequenceType) {
        int result = 0;
        List<String> victims = consequenceContainer.getHealthConsequencesOfType(decision, consequenceType);
        for (String victimName : victims) {
            Living_entity livingEntity = factory.getLiving_entity(victimName);
            if (factory.getHuman(victimName) != null ) {
                result += ParameterizedPhilosophy.healthValue.get(consequenceType) * ParameterizedPhilosophy.humanLifeFactor;
            } else if (factory.getAnimal(victimName) != null) {
                result += ParameterizedPhilosophy.healthValue.get(consequenceType) * ParameterizedPhilosophy.animalLifeFactor;
            }
        }
        System.out.println(consequenceType.toString() + "  " + result);
        return result;
    }

}
