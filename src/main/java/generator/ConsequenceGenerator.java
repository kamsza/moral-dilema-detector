package generator;

import generator.Model;
import project.*;
import project.MyFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class ConsequenceGenerator {
    MyFactory factory;
    Random random;

    public ConsequenceGenerator(MyFactory factory){
        this.factory = factory;
        random = new Random();
    }

    public void predict(Model scenarioModel) {
        boolean slippery_road = false;

        if (scenarioModel.getWeather() instanceof Heavy_rain) {
            slippery_road = true;
        }

        Vehicle vehicle = scenarioModel.getVehicle();

        int speed_limit = 50;
        for (Integer limit : scenarioModel.getRoadType().getHas_speed_limit_kmph()) {
            speed_limit = limit;
        }

        double severInjuryProbability = severInjuryProbability(speed_limit);
        double minorInjuryProbability = minorInjuryProbability(speed_limit);
        double fatalInjuryProbability = fatalInjuryProbability(speed_limit);

        for (Map.Entry<Decision, Action> entry : scenarioModel.getActionByDecision().entrySet()) {
            Decision decision = entry.getKey();
            Action action = entry.getValue();
            System.out.println(decision.getOwlIndividual());


            HashSet<Living_entity> victims = detectCollisions(action, scenarioModel);
            HashSet<Living_entity> victimsCopy = new HashSet<Living_entity>(victims);

            Killed killed = factory.createKilled(ObjectNamer.getName("killed"));
            Severly_injured severelyInjured = factory.createSeverly_injured(ObjectNamer.getName("severely_injured"));
            Lightly_injured lightlyInjured = factory.createLightly_injured(ObjectNamer.getName("lightly_injured"));
            Intact intact = factory.createIntact(ObjectNamer.getName("intact"));


            for (Iterator<Living_entity> iterator = victimsCopy.iterator(); iterator.hasNext(); ) {
                Living_entity living_entity = iterator.next();
                if (random.nextInt() % 100 + 1 < fatalInjuryProbability) {
                    System.out.println("In killed");
                    killed.addHealth_consequence_to(living_entity);
                    iterator.remove();
                }
            }

            for (Iterator<Living_entity> iterator = victimsCopy.iterator(); iterator.hasNext(); ) {
                Living_entity living_entity = iterator.next();
                if (random.nextInt() % 100 + 1 < severInjuryProbability) {
                    System.out.println("In sev injured");
                    severelyInjured.addHealth_consequence_to(living_entity);
                    iterator.remove();
                }
            }
            for (Iterator<Living_entity> iterator = victimsCopy.iterator(); iterator.hasNext(); ) {
                Living_entity living_entity = iterator.next();
                if (random.nextInt() % 100 + 1 < minorInjuryProbability) {
                    System.out.println("In light injured");
                    lightlyInjured.addHealth_consequence_to(living_entity);
                    iterator.remove();
                }
            }

            for (Iterator<Living_entity> iterator = victimsCopy.iterator(); iterator.hasNext(); ) {
                Living_entity living_entity = iterator.next();
                System.out.println("In intact");
                intact.addHealth_consequence_to(living_entity);
                iterator.remove();
                }
            }
    }


    private HashSet<Living_entity> detectCollisions(Action action, Model scenarioModel){
        HashSet<Living_entity> result = new HashSet<>();

        Vehicle main_vehicle = scenarioModel.getVehicle();

        if (action instanceof Turn_left){
            for(Entity entity : main_vehicle.getHas_on_the_left()){
                updateVictims(entity, result, scenarioModel);
            }
        }
        else if(action instanceof Turn_right){
            for(Entity entity : main_vehicle.getHas_on_the_right()){
                updateVictims(entity, result, scenarioModel);
            }
        }
        else if(action instanceof Follow){
            for(Entity entity : main_vehicle.getHas_in_the_front()){
                updateVictims(entity, result, scenarioModel);
            }
        }

        return result;
    }

    private void updateVictims(Entity entity, HashSet<Living_entity> result, Model scenarioModel){
        Vehicle vehicle = factory.getVehicle(entity.getOwlIndividual().getIRI().toString());

        if (vehicle != null) {
            result.addAll(vehicle.getVehicle_has_passenger());
            result.addAll(vehicle.getVehicle_has_driver());
        }
        else{
            Living_entity living_entity = factory.getLiving_entity(entity.getOwlIndividual().getIRI().toString());
            if(living_entity != null){
                result.add(living_entity);
            }
        }
        result.addAll(scenarioModel.getPassengers());
        result.add(scenarioModel.getDriver());
    }

    private double minorInjuryProbability(int speed){
        double probability;
        if (speed < 30) {
            probability = getProbability(0, 0, 30, 82.5, speed);
        }
        else if(speed < 50){
            probability = getProbability(30, 82.5, 50, 75, speed);
        }
        else if(speed < 70){
            probability = getProbability(50, 75, 70, 54.9, speed);
        }
        else if(speed < 90){
            probability = getProbability(70, 54.9, 90, 32.3, speed);
        }
        else if(speed < 115){
            probability = getProbability(90, 32.3, 115, 33.3, speed);
        }
        else{
            probability = getProbability(115, 33.3, speed, 33.3, speed);
        }
        return probability;
    }

    private double severInjuryProbability(int speed){
        double probability;
        if (speed < 30) {
            probability = getProbability(0, 0, 30, 14.7, speed);
        }
        else if(speed < 50){
            probability = getProbability(30, 14.7, 50, 21.9, speed);
        }
        else if(speed < 70){
            probability = getProbability(50, 21.9, 70, 33.3, speed);
        }
        else if(speed < 90){
            probability = getProbability(70, 33.3, 90, 30.6, speed);
        }
        else if(speed < 115){
            probability = getProbability(90, 30.6, 115, 26.7, speed);
        }
        else{
            probability = getProbability(115, 26.7, speed, 26.7, speed);
        }
        return probability;
    }

    private double fatalInjuryProbability(int speed){
        double probability;
        if (speed < 30) {
            probability = getProbability(0, 0, 30, 2.7, speed);
        }
        else if(speed < 50){
            probability = getProbability(30, 2.7, 50, 3.1, speed);
        }
        else if(speed < 70){
            probability = getProbability(50, 3.1, 70, 11.8, speed);
        }
        else if(speed < 90){
            probability = getProbability(70, 11.8, 90, 37.1, speed);
        }
        else if(speed < 115){
            probability = getProbability(90, 37.1, 115, 40.0, speed);
        }
        else{
            probability = getProbability(115, 40.0, speed, 40.0, speed);
        }
        return probability;
    }

    private double getProbability(double x1, double y1, double x2, double y2, double speed){
        return (y2 - y1)/(x2 - x1)*(speed - x1) + y1;
    }
}
