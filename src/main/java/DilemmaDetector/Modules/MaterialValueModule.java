package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import project.*;

import java.util.ArrayList;

public class MaterialValueModule implements IMoralDilemmaDetectorModule {
    private MyFactory factory;

    public MaterialValueModule(MyFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Scenario scenario) {
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
            if(x > 0)
                return true;
        }
        return false;
    }
}
