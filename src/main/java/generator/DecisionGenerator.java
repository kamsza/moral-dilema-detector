package generator;

import project.*;

import java.util.HashMap;
import java.util.Map;

public class DecisionGenerator {
    String baseIRI;
    OWLFactory factory;
    private Map<Decision, Action> actionByDecision;

    public DecisionGenerator(OWLFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;
        actionByDecision = new HashMap<>();
    }

    public void generate(Model model){
        actionByDecision.clear();
        model.setActionByDecision(this.actionByDecision);

        Follow follow = factory.createFollow(ObjectNamer.getName("follow"));
        createDecision(follow, model);
        Turn_left turn_left = factory.createTurn_left(ObjectNamer.getName("turn_left"));
        createDecision(turn_left, model);
        Turn_right turn_right = factory.createTurn_right(ObjectNamer.getName("turn_right"));
        createDecision(turn_right, model);
        Stop stop = factory.createStop(ObjectNamer.getName("stop"));
        createDecision(stop, model);

//        adding decisions and actions for changing lanes
        for(Model.Side side : model.getMainRoad().getLanes().keySet()){
            for(Integer lane_number : model.getMainRoad().getLanes().get(side).keySet()){
                if(lane_number != 0) {
                    String action_name = "change_lane_" + side.toString().toLowerCase() + "_by_" + lane_number;
                    Change_lane action = factory.createChange_lane(ObjectNamer.getName(action_name));
                    if(side == Model.Side.LEFT)
                        action.addLane_change_by(lane_number);
                    else if(side == Model.Side.RIGHT)
                        action.addLane_change_by(-lane_number);
                    createDecision(action, model);
                }
            }
        }
    }

    private void createDecision(Action action, Model model){
        Decision decision = factory.createDecision(ObjectNamer.getName("decision"));
        decision.addHas_action(action);
        model.getScenario().addHas_decision(decision);
        this.actionByDecision.put(decision, action);
    }

    public Map<Decision, Action> getActionByDecision() {
        return actionByDecision;
    }
}
