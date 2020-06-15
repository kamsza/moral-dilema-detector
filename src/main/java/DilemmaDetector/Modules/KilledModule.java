package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.*;
import project.MyFactory;

import java.util.ArrayList;

// Module detecting if we cannot avoid killing someone
public class KilledModule implements IMoralDilemmaDetectorModule {
    private MyFactory factory;

    public KilledModule(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        Scenario scenario = model.getScenario();
        ArrayList<Integer> killed = new ArrayList<>();

        int index = 0;
        for(Decision decision : scenario.getHas_decision()){
            killed.add(index, 0);
            for(Consequence consequence : decision.getHas_consequence()){
                if(factory.getKilled(consequence.getOwlIndividual().getIRI().toString()) != null) {
                    for (Living_entity living_entity : factory.getHealth_consequence(consequence.getOwlIndividual().getIRI().toString()).getHealth_consequence_to()) {
                        killed.set(index, killed.get(index) + 1);
                    }
                }
            }
            index++;
        }

        System.out.println(killed.size());
        for(int x : killed){
            if(x == 0)
                return false;
        }
        return true;
    }
}
