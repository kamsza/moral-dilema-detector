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

        Follow follow = factory.createFollow(ObjectNamer.getName("follow"));
        createBasicDecision(follow, model, actionByDecision);
        Turn_left turn_left = factory.createTurn_left(ObjectNamer.getName("turn_left"));
        createBasicDecision(turn_left, model, actionByDecision);
        Turn_right turn_right = factory.createTurn_right(ObjectNamer.getName("turn_right"));
        createBasicDecision(turn_right, model, actionByDecision);
        Stop stop = factory.createStop(ObjectNamer.getName("stop"));
        createBasicDecision(stop, model, actionByDecision);

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

    private void createBasicDecision(Action action, Model model, HashMap<Decision, Action> actionByDecision){
        Decision decision = factory.createDecision(ObjectNamer.getName("decision"));
        decision.addHas_action(action);
        model.getScenario().addHas_decision(decision);
        actionByDecision.put(decision, action);
    }
}
