package DilemmaDetector.Consequences;

import java.util.HashMap;

public class CustomPhilosophy {
    private String philosophyName;
    private int humanLifeInsideMainVehicle;
    private int humanLifeOutsideMainVehicle;
    private int humanSevereInjuryInsideMainVehicle;
    private int humanLightlyInjuryInsideMainVehicle;
    private int humanSevereInjuryOutsideMainVehicle;
    private int humanLightlyInjuryOutsideMainVehicle;
    private int animalLife;
    private int animalSevereInjury;
    private int animalLightlyInjury;
    private int materialValue;
    private int breakingTheLaw;

    public String getPhilosophyName() {
        return philosophyName;
    }

    public int getHumanLifeInsideMainVehicle() {
        return humanLifeInsideMainVehicle;
    }

    public int getHumanLifeOutsideMainVehicle() {
        return humanLifeOutsideMainVehicle;
    }

    public int getHumanSevereInjuryInsideMainVehicle() {
        return humanSevereInjuryInsideMainVehicle;
    }

    public int getHumanLightlyInjuryInsideMainVehicle() {
        return humanLightlyInjuryInsideMainVehicle;
    }

    public int getHumanSevereInjuryOutsideMainVehicle() {
        return humanSevereInjuryOutsideMainVehicle;
    }

    public int getHumanLightlyInjuryOutsideMainVehicle() {
        return humanLightlyInjuryOutsideMainVehicle;
    }

    public int getAnimalLife() {
        return animalLife;
    }

    public int getMaterialValue() {
        return materialValue;
    }

    public int getBreakingTheLaw() {
        return breakingTheLaw;
    }

    public void setAnimalSevereInjury(int animalSevereInjury) {
        this.animalSevereInjury = animalSevereInjury;
    }

    public void setAnimalLightlyInjury(int animalLightlyInjury) {
        this.animalLightlyInjury = animalLightlyInjury;
    }

    public int getAnimalSevereInjury() {
        return animalSevereInjury;
    }

    public int getAnimalLightlyInjury() {
        return animalLightlyInjury;
    }

    public void setPhilosophyName(String philosophyName) {
        this.philosophyName = philosophyName;
    }

    public void setHumanLifeInsideMainVehicle(int humanLifeInsideMainVehicle) {
        this.humanLifeInsideMainVehicle = humanLifeInsideMainVehicle;
    }

    public void setHumanLifeOutsideMainVehicle(int humanLifeOutsideMainVehicle) {
        this.humanLifeOutsideMainVehicle = humanLifeOutsideMainVehicle;
    }

    public void setHumanSevereInjuryInsideMainVehicle(int humanSevereInjuryInsideMainVehicle) {
        this.humanSevereInjuryInsideMainVehicle = humanSevereInjuryInsideMainVehicle;
    }

    public void setHumanLightlyInjuryInsideMainVehicle(int humanLightlyInjuryInsideMainVehicle) {
        this.humanLightlyInjuryInsideMainVehicle = humanLightlyInjuryInsideMainVehicle;
    }

    public void setHumanSevereInjuryOutsideMainVehicle(int humanSevereInjuryOutsideMainVehicle) {
        this.humanSevereInjuryOutsideMainVehicle = humanSevereInjuryOutsideMainVehicle;
    }

    public void setHumanLightlyInjuryOutsideMainVehicle(int humanLightlyInjuryOutsideMainVehicle) {
        this.humanLightlyInjuryOutsideMainVehicle = humanLightlyInjuryOutsideMainVehicle;
    }

    public void setAnimalLife(int animalLife) {
        this.animalLife = animalLife;
    }

    public void setMaterialValue(int materialValue) {
        this.materialValue = materialValue;
    }

    public void setBreakingTheLaw(int breakingTheLaw) {
        this.breakingTheLaw = breakingTheLaw;
    }


    public void setParametersFromHashMap(HashMap<String, Integer> tableValues) {
        this.humanLifeInsideMainVehicle = tableValues.get("Human life inside main vehicle");
        this.humanLifeOutsideMainVehicle = tableValues.get("Human life inside main vehicle");
        this.humanSevereInjuryInsideMainVehicle = tableValues.get("Human severe injury inside main vehicle");
        this.humanSevereInjuryOutsideMainVehicle = tableValues.get("Human severe injury outside main vehicle");
        this.humanLightlyInjuryInsideMainVehicle = tableValues.get("Human lightly injury inside main vehicle");
        this.humanLightlyInjuryOutsideMainVehicle = tableValues.get("Human lightly injury outside main vehicle");
        this.animalLife = tableValues.get("Animal life");
        this.animalSevereInjury = tableValues.get("Animal severe injury");
        this.animalLightlyInjury = tableValues.get("Animal lightly injury");
        this.materialValue = tableValues.get("Material damages per 1000$");
        this.breakingTheLaw = tableValues.get("Breaking the law");
    }

    @Override
    public String toString() {
        return "CustomPhilosophy{" +
                "philosophyName='" + philosophyName + '\'' +
                ", humanLifeInsideMainVehicle=" + humanLifeInsideMainVehicle +
                ", humanLifeOutsideMainVehicle=" + humanLifeOutsideMainVehicle +
                ", humanSevereInjuryInsideMainVehicle=" + humanSevereInjuryInsideMainVehicle +
                ", humanLightlyInjuryInsideMainVehicle=" + humanLightlyInjuryInsideMainVehicle +
                ", humanSevereInjuryOutsideMainVehicle=" + humanSevereInjuryOutsideMainVehicle +
                ", humanLightlyInjuryOutsideMainVehicle=" + humanLightlyInjuryOutsideMainVehicle +
                ", animalLife=" + animalLife +
                ", animalSevereInjury=" + animalSevereInjury +
                ", animalLightlyInjury=" + animalLightlyInjury +
                ", materialValue=" + materialValue +
                ", breakingTheLaw=" + breakingTheLaw +
                '}';
    }
}
