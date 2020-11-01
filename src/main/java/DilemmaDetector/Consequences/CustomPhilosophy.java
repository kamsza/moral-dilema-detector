package DilemmaDetector.Consequences;

import java.util.HashMap;

public class CustomPhilosophy {
    private String philosophyName;

    private HashMap<String, Integer> parameters = new HashMap<>();

    public static final String HUMAN_LIFE_INSIDE_MAIN_VEHICLE = "humanLifeInsideMainVehicle";
    public static final String HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE = "humanLifeOutsideMainVehicle";
    public static final String HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE = "humanSevereInjuryInsideMainVehicle";
    public static final String HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE = "humanSevereInjuryOutsideMainVehicle";
    public static final String HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE = "humanLightlyInjuryInsideMainVehicle";
    public static final String HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE = "humanLightlyInjuryOutsideMainVehicle";
    public static final String ANIMAL_LIFE = "animalLife";
    public static final String ANIMAL_SEVERE_INJURY = "animalSevereInjury";
    public static final String ANIMAL_LIGHTLY_INJURY = "animalLightlyInjury";
    public static final String MATERIAL_VALUE_TABLE = "materialValue";
    public static final String BREAKING_THE_LAW_TABLE = "breakingTheLaw";


    public String getPhilosophyName() {
        return philosophyName;
    }

    public HashMap<String, Integer> getParameters() {
        return parameters;
    }

    public void setPhilosophyName(String philosophyName) {
        this.philosophyName = philosophyName;
    }


    public void setParametersFromHashMap(HashMap<String, Integer> tableValues) {
        parameters.put(HUMAN_LIFE_INSIDE_MAIN_VEHICLE, tableValues.get("Human life inside main vehicle"));
        parameters.put(HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human life outside main vehicle"));
        parameters.put(HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE, tableValues.get("Human severe injury inside main vehicle"));
        parameters.put(HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human severe injury outside main vehicle"));
        parameters.put(HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE, tableValues.get("Human lightly injury inside main vehicle"));
        parameters.put(HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE, tableValues.get("Human lightly injury outside main vehicle"));
        parameters.put(ANIMAL_LIFE, tableValues.get("Animal life"));
        parameters.put(ANIMAL_SEVERE_INJURY, tableValues.get("Animal severe injury"));
        parameters.put(ANIMAL_LIGHTLY_INJURY, tableValues.get("Animal lightly injury"));
        parameters.put(MATERIAL_VALUE_TABLE, tableValues.get("Material damages per 1000$"));
        parameters.put(BREAKING_THE_LAW_TABLE, tableValues.get("Breaking the law"));
    }


    @Override
    public String toString() {
        return "CustomPhilosophy{" +
                "philosophyName='" + philosophyName + '\'' +
                ", parameters=" + parameters +
                '}';
    }

}
