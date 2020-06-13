package DilemmaDetector;

import generator.Model;
import project.*;

import java.util.ArrayList;
import java.util.Collection;

public class MoralDilemmaDetector{
    private ArrayList<IMoralDilemmaDetectorModule> detectorModules;

    public MoralDilemmaDetector() {
        detectorModules = new ArrayList<>();
    }

    public static class Builder{
        private MoralDilemmaDetector detector;

        public Builder(){
            detector = new MoralDilemmaDetector();
        }

        public Builder addModule(IMoralDilemmaDetectorModule module){
            detector.detectorModules.add(module);
            return this;
        }

        public MoralDilemmaDetector build(){
            return detector;
        }
    }

    // If any module detects moral dilemma then we return true
    public boolean detectMoralDilemma(Model model){
        for(IMoralDilemmaDetectorModule module : detectorModules){
            if(module.isMoralDilemma(model)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Boolean> detectMoralDilemmas(Collection<Model> models){
        ArrayList<Boolean> result = new ArrayList<>();

        for(Model model : models){
            boolean dilemma = false;
            for(IMoralDilemmaDetectorModule module : detectorModules){
                if(module.isMoralDilemma(model)){
                    result.add(true);
                    dilemma = true;
                    break;
                }
            }
            if(!dilemma)
                result.add(false);
        }

        return result;
    }
}
