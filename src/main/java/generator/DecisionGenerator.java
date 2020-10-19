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
//
        // adding decisions and actions for basic actions
        Decision decision_1 = factory.createDecision(ObjectNamer.getName("decision"));
        Turn_left action_1 = factory.createTurn_left(ObjectNamer.getName("turn_left"));
        decision_1.addHas_action(action_1);
        model.getScenario().addHas_decision(decision_1);
        actionByDecision.put(decision_1, action_1);

        Decision decision_2 = factory.createDecision(ObjectNamer.getName("decision"));
        Turn_right action_2 = factory.createTurn_right(ObjectNamer.getName("turn_right"));
        decision_2.addHas_action(action_2);
        model.getScenario().addHas_decision(decision_2);
        actionByDecision.put(decision_2, action_2);

        Decision decision_3 = factory.createDecision(ObjectNamer.getName("decision"));
        Follow action_3 = factory.createFollow(ObjectNamer.getName("follow"));
        decision_3.addHas_action(action_3);
        model.getScenario().addHas_decision(decision_3);
        actionByDecision.put(decision_3, action_3);

//        adding decisions and actions for changing lanes
        for(Model.Side side : model.getLanes().keySet()){
            for(Integer lane_number : model.getLanes().get(side).keySet()){
                if(lane_number != 0) {
                    Decision decision = factory.createDecision(ObjectNamer.getName("decision"));
                    String action_name = "change_lane_" + side.toString().toLowerCase() + "_by_" + lane_number;
                    Action action = factory.createAction(ObjectNamer.getName(action_name));
                    decision.addHas_action(action);
                    model.getScenario().addHas_decision(decision);
                    actionByDecision.put(decision, action);
                }
            }
        }
    }
}
