package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.*;

import java.util.ArrayList;

// Module detecting if we cannot avoid losing material value
public class MaterialValueModule implements IMoralDilemmaDetectorModule {
    private OWLFactory factory;

    public MaterialValueModule(OWLFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        Scenario scenario = model.getScenario();
        ArrayList<Long> value = new ArrayList<>();

        int index = 0;
        for(Decision decision : scenario.getHas_decision()){
            value.add(index, (long) 0);
            for(Consequence consequence : decision.getHas_consequence()){
                Material_consequence cons = factory.getMaterial_consequence(consequence.getOwlIndividual().getIRI().toString());
                if(cons != null) {
                    for(Object v : cons.getHas_material_value()) {
                        value.set(index, value.get(index) + Long.parseLong(v.toString().split("\"")[1]));
                    }
                }
            }
            index++;
        }

        for(long x : value){
            if(x == 0)
                return false;
        }
        return true;
    }
}
