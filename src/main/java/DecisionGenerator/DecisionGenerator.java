package DecisionGenerator;

import project.*;
import project.MyFactory;

public class DecisionGenerator {
    MyFactory factory;

    public DecisionGenerator(MyFactory factory){
        this.factory = factory;
    }

    public void generate(Scenario scenario){
        Decision decision_1 = factory.createDecision("decision_1");
        Turn_left action_1 = factory.createTurn_left("turn_left_1");
        decision_1.addHas_action(action_1);

        Decision decision_2 = factory.createDecision("decision_2");
        Turn_right action_2 = factory.createTurn_right("turn_right_1");
        decision_2.addHas_action(action_2);

        Decision decision_3 = factory.createDecision("decision_3");
        Follow action_3 = factory.createFollow("follow_1");
        decision_3.addHas_action(action_3);

        scenario.addHas_decision(decision_1);
        scenario.addHas_decision(decision_2);
        scenario.addHas_decision(decision_3);
    }
}
