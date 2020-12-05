package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.*;

import java.util.ArrayList;

// Module detecting if we cannot avoid lightly injuring someone
public class LightlyInjuredModule implements IMoralDilemmaDetectorModule {
    private OWLFactory factory;

    public LightlyInjuredModule(OWLFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        Scenario scenario = model.getScenario();
        ArrayList<Integer> lighlyInjured = new ArrayList<>();

        int index = 0;
        for(Decision decision : scenario.getHas_decision()){
            lighlyInjured.add(index, 0);
            for(Consequence consequence : decision.getHas_consequence()){
                if(factory.getLightly_injured(consequence.getOwlIndividual().getIRI().toString()) != null) {
                    for (Living_entity living_entity : factory.getHealth_consequence(consequence.getOwlIndividual().getIRI().toString()).getHealth_consequence_to()) {
                        lighlyInjured.set(index, lighlyInjured.get(index) + 1);
                    }
                }
            }
            index++;
        }

        for(int x : lighlyInjured){
            if(x == 0)
                return false;
        }
        return true;
    }
}
