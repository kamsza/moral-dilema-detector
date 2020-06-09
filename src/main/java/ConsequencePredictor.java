import generator.Model;
import project.*;
import project.MyFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConsequencePredictor {
    MyFactory factory;

    public ConsequencePredictor(MyFactory factory){
        this.factory = factory;
    }

    public void predict(Model scenarioModel){
        boolean slippery_road = false;

        if (scenarioModel.getWeather() instanceof Heavy_rain){
            slippery_road = true;
        }

        boolean big_speed = false;

        Vehicle vehicle = scenarioModel.getVehicle();
        if (Long.parseLong(vehicle.getVehicle_has_speed_kmph().toArray()[0].toString()) > 50)
            big_speed = true;

        for (Map.Entry<Decision, Action> entry : scenarioModel.getActionByDecision().entrySet()) {
            Decision decision = entry.getKey();
            Action action = entry.getValue();

            HashSet<Living_entity> victims = detectCollisions(action, scenarioModel);

            if (slippery_road && big_speed) {
                Killed killed = factory.createKilled("killed_1");
                for (Living_entity living_entity : victims) {
                    killed.addHealth_consequence_to(living_entity);
                }
                decision.addHas_consequence(killed);
            } else if (slippery_road || big_speed) {
                Injured injured = factory.createInjured("injured_1");
                for (Living_entity living_entity : victims) {
                    injured.addHealth_consequence_to(living_entity);
                }
                decision.addHas_consequence(injured);
            } else {
                Intact intact = factory.createIntact("intact_1");
                for (Living_entity living_entity : victims) {
                    intact.addHealth_consequence_to(living_entity);
                }
                decision.addHas_consequence(intact);
            }
        }
    }

    public void predict(Scenario scenario) {
        boolean slippery_road = false;
        for (Weather weather : scenario.getHas_weather()) {
            if (factory.getHeavy_rain(weather.getOwlIndividual().getIRI().toString()) != null) {
                slippery_road = true;
                break;
            }
        }

        boolean big_speed = false;
        for (Vehicle vehicle : scenario.getHas_vehicle()) {
            try {
                if (Long.parseLong(vehicle.getVehicle_has_speed_kmph().toArray()[0].toString()) > 50) {
                    big_speed = true;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }

        for (Decision decision : scenario.getHas_decision()) {
            for (Action action : decision.getHas_action()) {
                HashSet<Living_entity> victims = detectCollisions(action);

                if (slippery_road && big_speed) {
                    Killed killed = factory.createKilled("killed_1");
                    for (Living_entity living_entity : victims) {
                        killed.addHealth_consequence_to(living_entity);
                    }
                    decision.addHas_consequence(killed);
                } else if (slippery_road || big_speed) {
                    Injured injured = factory.createInjured("injured_1");
                    for (Living_entity living_entity : victims) {
                        injured.addHealth_consequence_to(living_entity);
                    }
                    decision.addHas_consequence(injured);
                } else {
                    Intact intact = factory.createIntact("intact_1");
                    for (Living_entity living_entity : victims) {
                        intact.addHealth_consequence_to(living_entity);
                    }
                    decision.addHas_consequence(intact);
                }
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

    private HashSet<Living_entity> detectCollisions(Action action){
        HashSet<Living_entity> result = new HashSet<>();

        Vehicle main_vehicle = factory.getVehicle("http://www.w3.org/2003/11/0_vehicle");

        if(factory.getTurn_left(action.getOwlIndividual().getIRI().toString()) != null) {
            for(Entity entity : main_vehicle.getHas_on_the_left()){
                updateVictims(entity, result, main_vehicle);
            }
        }
        else if(factory.getTurn_right(action.getOwlIndividual().getIRI().toString()) != null){
            for(Entity entity : main_vehicle.getHas_on_the_right()){
                updateVictims(entity, result, main_vehicle);
            }
        }
        else if(factory.getFollow(action.getOwlIndividual().getIRI().toString()) != null){
            for(Entity entity : main_vehicle.getHas_in_the_front()){
                updateVictims(entity, result, main_vehicle);
            }
        }

        return result;
    }

    private void updateVictims(Entity entity, HashSet<Living_entity> result, Vehicle main_vehicle){
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
        result.addAll(main_vehicle.getVehicle_has_passenger());
        result.addAll(main_vehicle.getVehicle_has_driver());
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
}
