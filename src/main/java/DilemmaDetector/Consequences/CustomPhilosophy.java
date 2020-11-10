package DilemmaDetector.Consequences;

import java.util.HashMap;

public class CustomPhilosophy {
    private String philosophyName;

    private HashMap<PhilosophyParameter, Integer> parameters = new HashMap<>();


    public String getPhilosophyName() {
        return philosophyName;
    }

    public HashMap<PhilosophyParameter, Integer> getParameters() {
        return parameters;
    }

    public void setPhilosophyName(String philosophyName) {
        this.philosophyName = philosophyName;
    }


    public void setParametersFromHashMap(HashMap<String, Integer> tableValues) {
        parameters.put(PhilosophyParameter.HUMAN_LIFE_INSIDE_MAIN_VEHICLE, tableValues.get("Human life inside main vehicle"));
        parameters.put(PhilosophyParameter.HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human life outside main vehicle"));
        parameters.put(PhilosophyParameter.HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE, tableValues.get("Human severe injury inside main vehicle"));
        parameters.put(PhilosophyParameter.HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human severe injury outside main vehicle"));
        parameters.put(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE, tableValues.get("Human lightly injury inside main vehicle"));
        parameters.put(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human lightly injury outside main vehicle"));
        parameters.put(PhilosophyParameter.ANIMAL_LIFE, tableValues.get("Animal life"));
        parameters.put(PhilosophyParameter.ANIMAL_SEVERE_INJURY, tableValues.get("Animal severe injury"));
        parameters.put(PhilosophyParameter.ANIMAL_LIGHTLY_INJURY, tableValues.get("Animal lightly injury"));
        parameters.put(PhilosophyParameter.MATERIAL_VALUE, tableValues.get("Material damages per 1000$"));
//        parameters.put(PhilosophyParameter.BREAKING_THE_LAW, tableValues.get("Breaking the law"));
        parameters.put(PhilosophyParameter.TAKING_ACTION,tableValues.get("Taking action"));
        parameters.put(PhilosophyParameter.DILEMMA_THRESHOLD, tableValues.get("Dilemma threshold"));
    }

    public static CustomPhilosophy getSimplestPhilosophy() {
        CustomPhilosophy customPhilosophy = new CustomPhilosophy();
        customPhilosophy.setPhilosophyName("Simple");
        for (PhilosophyParameter philosophyParameter : PhilosophyParameter.values()) {
            customPhilosophy.getParameters().put(philosophyParameter, 1);
        }
        return customPhilosophy;
    }

    @Override
    public String toString() {
        return "CustomPhilosophy{" +
                "philosophyName='" + philosophyName + '\'' +
                ", parameters=" + parameters +
                '}';
    }

}
