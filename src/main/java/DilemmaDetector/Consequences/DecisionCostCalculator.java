package DilemmaDetector.Consequences;

import project.*;

import java.util.HashMap;
import java.util.List;

public class DecisionCostCalculator {
    private IConsequenceContainer consequenceContainer;
    private MyFactory factory;
    private CustomPhilosophy customPhilosophy;


    public DecisionCostCalculator(IConsequenceContainer consequenceContainer, MyFactory factory, CustomPhilosophy customPhilosophy) {
        this.consequenceContainer = consequenceContainer;
        this.factory = factory;
        this.customPhilosophy = customPhilosophy;
    }

    public DecisionCostCalculator(IConsequenceContainer consequenceContainer, MyFactory factory) {
        this.consequenceContainer = consequenceContainer;
        this.factory = factory;
        customPhilosophy = CustomPhilosophy.getSimplestPhilosophy();
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
            HashMap<PhilosophyParameter, Integer> parameters = customPhilosophy.getParameters();
            if (factory.getHuman(victimName) != null) {
                switch (consequenceType) {
                    case KILLED:
                        result += parameters.get(PhilosophyParameter.HUMAN_LIFE_INSIDE_MAIN_VEHICLE);
                        break;
                    case SEVERELY_INJURED:
                        result += parameters.get(PhilosophyParameter.HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE);
                        break;
                    case LIGHTLY_INJURED:
                        result += parameters.get(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE);
                }
            } else if (factory.getAnimal(victimName) != null) {
                switch (consequenceType) {
                    case KILLED:
                        result += parameters.get(PhilosophyParameter.ANIMAL_LIFE);
                        break;
                    case SEVERELY_INJURED:
                        result += parameters.get(PhilosophyParameter.ANIMAL_SEVERE_INJURY);
                        break;
                    case LIGHTLY_INJURED:
                        result += parameters.get(PhilosophyParameter.ANIMAL_LIGHTLY_INJURY);
                }
            }
        }
        System.out.println(consequenceType.toString() + "  " + result);
        return result;
    }

}
