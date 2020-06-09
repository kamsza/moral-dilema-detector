package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.*;

import java.util.ArrayList;

// Module detecting if we cannot avoid injuring someone
public class InjuredModule implements IMoralDilemmaDetectorModule {
    private MyFactory factory;

    public InjuredModule(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Scenario scenario) {
        ArrayList<Integer> injured = new ArrayList<>();

        int index = 0;
        for(Decision decision : scenario.getHas_decision()){
            injured.add(index, 0);
            for(Consequence consequence : decision.getHas_consequence()){
                if(factory.getInjured(consequence.getOwlIndividual().getIRI().toString()) != null) {
                    for (Living_entity living_entity : factory.getHealth_consequence(consequence.getOwlIndividual().getIRI().toString()).getHealth_consequence_to()) {
                        injured.set(index, injured.get(index) + 1);
                    }
                }
            }
            index++;
        }

        for(int x : injured){
            if(x > 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        return isMoralDilemma(model.getScenario());    // we need consequences in model
    }
}
