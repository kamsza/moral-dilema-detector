package DilemmaDetector.Consequences;

import project.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void setCustomPhilosophy(CustomPhilosophy customPhilosophy) {
        this.customPhilosophy = customPhilosophy;
    }

    public int getSummarizedCostForDecision(Decision decision) {
        int result = 0;
        result += calculateCostOfHealthConsequence(decision);
        result += calculateCostOfMaterialConsequence(decision);
        result += calculateCostOfTakingAction(decision);
        // dalej inne typy
        System.out.println("DECISION : " + decision.getOwlIndividual().getIRI().toString() + " | POINTS: " + result);
        return result;
    }

    private int calculateCostOfTakingAction(Decision decision) {
        int result = 0;
        System.out.println(decision.toString());
        if (decision.toString().indexOf("follow") == -1 && decision.toString().indexOf("stop") == -1) {
            result = customPhilosophy.getParameters().get(PhilosophyParameter.TAKING_ACTION);
        }
        return result;
    }

    private int calculateCostOfMaterialConsequence(Decision decision) {
        int sum = 0;
        HashMap<PhilosophyParameter, Integer> parameters = customPhilosophy.getParameters();
        Set<Map.Entry<String, Double>> consequences = consequenceContainer.getMaterialConsequences(decision);
        for (Map.Entry<String, Double> consequence : consequences) {
            sum += (int) Math.round(consequence.getValue());
        }
        return (int) (sum * parameters.get(PhilosophyParameter.MATERIAL_VALUE)) / 1000;
    }

    private int calculateCostOfHealthConsequence(Decision decision) {
        int result = 0;
        for (ConsequenceType consequenceType :
                List.of(ConsequenceType.KILLED, ConsequenceType.SEVERELY_INJURED, ConsequenceType.LIGHTLY_INJURED)) {
            result += calculateCostOfHealthConsequenceOfType(decision, consequenceType);
            System.out.println(calculateCostOfHealthConsequenceOfType(decision, consequenceType));
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDUPPPPPPPAAAAA");
        }
        return result;
    }

    private boolean humanInsideMainVehicle(Vehicle mainVehicle, String victimName) {
        if (mainVehicle == null)
            return false;
        return mainVehicle.getVehicle_has_passenger().stream().map(p -> ((Passenger) p).getOwlIndividual().getIRI().toString())
                .anyMatch(p -> p.equals(victimName));
    }

    private int calculateCostOfHealthConsequenceOfType(Decision decision, ConsequenceType consequenceType) {
        int result = 0;
        List<String> victims = consequenceContainer.getHealthConsequencesOfType(decision, consequenceType);
        for (String victimName : victims) {
            System.out.println("DDDDDDDD " + victimName);
            HashMap<PhilosophyParameter, Integer> parameters = customPhilosophy.getParameters();
            //extract scenario number to get main vehicle
            String scenarioNumber = victimName.split("_")[0];
            Vehicle mainVehicle = factory.getVehicle(scenarioNumber + "_vehicle_main");
            if (humanInsideMainVehicle(mainVehicle, victimName)) {
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
            } else if (factory.getHuman(victimName) != null) {
                switch (consequenceType) {
                    case KILLED:
                        result += parameters.get(PhilosophyParameter.HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE);
                        break;
                    case SEVERELY_INJURED:
                        result += parameters.get(PhilosophyParameter.HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE);
                        break;
                    case LIGHTLY_INJURED:
                        result += parameters.get(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE);
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

