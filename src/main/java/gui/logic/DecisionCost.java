package gui.logic;

public class DecisionCost {
    private String decisionName;
    private int decisionCost;

    public DecisionCost(String decisionName, int decisionCost) {
        this.decisionName = decisionName;
        this.decisionCost = decisionCost;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public int getDecisionCost() {
        return decisionCost;
    }
}
