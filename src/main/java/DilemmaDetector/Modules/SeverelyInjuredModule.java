package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.*;

import java.util.ArrayList;

// Module detecting if we cannot avoid severely injuring someone
public class SeverelyInjuredModule implements IMoralDilemmaDetectorModule {
    private MyFactory factory;

    public SeverelyInjuredModule(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        Scenario scenario = model.getScenario();
        ArrayList<Integer> severelyInjured = new ArrayList<>();

        int index = 0;
        for(Decision decision : scenario.getHas_decision()){
            severelyInjured.add(index, 0);
            for(Consequence consequence : decision.getHas_consequence()){
                if(factory.getSeverly_injured(consequence.getOwlIndividual().getIRI().toString()) != null) {
                    for (Living_entity living_entity : factory.getHealth_consequence(consequence.getOwlIndividual().getIRI().toString()).getHealth_consequence_to()) {
                        severelyInjured.set(index, severelyInjured.get(index) + 1);
                    }
                }
            }
            index++;
        }

        for(int x : severelyInjured){
            if(x == 0)
                return false;
        }
        return true;
    }
}
