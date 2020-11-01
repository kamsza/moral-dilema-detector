package DilemmaDetector.Consequences;

import project.Decision;
import project.Living_entity;
import project.MyFactory;

import java.util.ArrayList;
import java.util.List;

public class DecisionCostEvaluator {
    private IConsequenceContainer consequenceContainer;
    private MyFactory factory;
    private CustomPhilosophy customPhilosophy;


    public DecisionCostEvaluator(IConsequenceContainer consequenceContainer, MyFactory factory, CustomPhilosophy customPhilosophy) {
        this.consequenceContainer = consequenceContainer;
        this.factory = factory;
        this.customPhilosophy = customPhilosophy;
    }


    public int getSummarizedCostForDecision(Decision decision) {
        int result = 0;
        result += calculateCostOfHealthConsequence(decision);
        // dalej inne typy
        System.out.println("DECISION : " + decision.getOwlIndividual().getIRI().toString() + " | POINTS: " + result);
        return result;
    }

    private int calculateCostOfHealthConsequence(Decision decision) {
        int result = 0;
        for (ConsequenceType consequenceType :
                List.of(ConsequenceType.KILLED, ConsequenceType.SEVERELY_INJURED, ConsequenceType.LIGHTLY_INJURED)) {
            result += calculateCostOfHealthConsequenceOfType(decision, consequenceType);
        }
        return result;
    }


    // TODO sprawdzenie czy paseżer z głównego pojazdu
    private int calculateCostOfHealthConsequenceOfType(Decision decision, ConsequenceType consequenceType) {
        int result = 0;
        List<String> victims = consequenceContainer.getHealthConsequencesOfType(decision, consequenceType);
        for (String victimName : victims) {
            if (factory.getHuman(victimName) != null) {
                switch (consequenceType) {
                    case KILLED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.HUMAN_LIFE_INSIDE_MAIN_VEHICLE);
                        break;
                    case SEVERELY_INJURED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE);
                        break;
                    case LIGHTLY_INJURED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE);
                }
            } else if (factory.getAnimal(victimName) != null) {
                switch (consequenceType) {
                    case KILLED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.ANIMAL_LIFE);
                        break;
                    case SEVERELY_INJURED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.ANIMAL_SEVERE_INJURY);
                        break;
                    case LIGHTLY_INJURED:
                        result += customPhilosophy.getParameters().get(CustomPhilosophy.ANIMAL_LIGHTLY_INJURY);
                }            }
        }
        System.out.println(consequenceType.toString() + "  " + result);
        return result;
    }

}
