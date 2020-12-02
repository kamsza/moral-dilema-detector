package DilemmaDetector.Consequences;

public enum PhilosophyParameter {


    HUMAN_LIFE_INSIDE_MAIN_VEHICLE("humanLifeInsideMainVehicle"),
    HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE("humanLifeOutsideMainVehicle"),
    HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE("humanSevereInjuryInsideMainVehicle"),
    HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE("humanSevereInjuryOutsideMainVehicle"),
    HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE("humanLightlyInjuryInsideMainVehicle"),
    HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE("humanLightlyInjuryOutsideMainVehicle"),
    ANIMAL_LIFE("animalLife"),
    ANIMAL_SEVERE_INJURY("animalSevereInjury"),
    ANIMAL_LIGHTLY_INJURY("animalLightlyInjury"),
    MATERIAL_VALUE("materialValue"),
    BREAKING_THE_LAW("breakingTheLaw"),
    TAKING_ACTION("takingAction"),
    DILEMMA_THRESHOLD("dilemmaThreshold");

    private String parameter;

    PhilosophyParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
