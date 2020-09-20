package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Action;
import project.Decision;
import project.Follow;
import project.MyFactory;
import project.Turn_left;
import project.Turn_right;

import java.util.HashMap;

public class DecisionGenerator {
    String baseIRI;
    MyFactory factory;

    public DecisionGenerator(MyFactory factory, String baseIRI) {
        this.baseIRI = baseIRI;
        this.factory = factory;
    }

    public void generate(Model model){
        // adding decisions and actions
        Decision decision_1 = factory.createDecision(ObjectNamer.getName("decision"));
        Turn_left action_1 = factory.createTurn_left(ObjectNamer.getName("turn_left"));

        decision_1.addHas_action(action_1);

        Decision decision_2 = factory.createDecision(ObjectNamer.getName("decision"));
        Turn_right action_2 = factory.createTurn_right(ObjectNamer.getName("turn_right"));
        decision_2.addHas_action(action_2);

        Decision decision_3 = factory.createDecision(ObjectNamer.getName("decision"));
        Follow action_3 = factory.createFollow(ObjectNamer.getName("follow"));
        decision_3.addHas_action(action_3);
//
        model.getScenario().addHas_decision(decision_1);
        model.getScenario().addHas_decision(decision_2);
        model.getScenario().addHas_decision(decision_3);


        HashMap<Decision, Action> actionByDecision = new HashMap<>();
        actionByDecision.put(decision_1, action_1);
        actionByDecision.put(decision_2, action_2);
        actionByDecision.put(decision_3, action_3);

        model.setActionByDecision(actionByDecision);

    }
}
