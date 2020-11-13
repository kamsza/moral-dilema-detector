package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.*;

import java.util.HashMap;

public class DecisionGenerator {
    String baseIRI;
    MyFactory factory;

    public DecisionGenerator(MyFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;
    }

    public void generate(Model model){
        HashMap<Decision, Action> actionByDecision = new HashMap<>();
        model.setActionByDecision(actionByDecision);

        createBasicDecision("turn_left", model, actionByDecision);
        createBasicDecision("turn_right", model, actionByDecision);
        createBasicDecision("follow", model, actionByDecision);
        createBasicDecision("stop", model, actionByDecision);

//        adding decisions and actions for changing lanes
        for(Model.Side side : model.getLanes().keySet()){
            for(Integer lane_number : model.getLanes().get(side).keySet()){
                if(lane_number != 0) {
                    Decision decision = factory.createDecision(ObjectNamer.getName("decision"));
                    String action_name = "change_lane_" + side.toString().toLowerCase() + "_by_" + lane_number;
                    Change_lane action = factory.createChange_lane(ObjectNamer.getName(action_name));
                    if(side == Model.Side.LEFT)
                        action.addLane_change_by(lane_number);
                    else if(side == Model.Side.RIGHT)
                        action.addLane_change_by(-lane_number);
                    decision.addHas_action(action);
                    model.getScenario().addHas_decision(decision);
                    actionByDecision.put(decision, action);
                }
            }
        }
    }

    private void createBasicDecision(String name, Model model, HashMap<Decision, Action> actionByDecision){
        Decision decision = factory.createDecision(ObjectNamer.getName("decision"));
        Action action;
        switch(name){
            case "follow":
                action = factory.createFollow(ObjectNamer.getName("follow"));
                break;
            case "turn_left":
                action = factory.createTurn_left(ObjectNamer.getName("turn_left"));
                break;
            case "turn_right":
                action = factory.createTurn_right(ObjectNamer.getName("turn_right"));
                break;
            default:
                action = factory.createStop(ObjectNamer.getName("stop"));
                break;
        }
        decision.addHas_action(action);
        model.getScenario().addHas_decision(decision);
        actionByDecision.put(decision, action);
    }
}
