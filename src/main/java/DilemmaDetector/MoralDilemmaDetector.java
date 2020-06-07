package DilemmaDetector;

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
    public boolean detectMoralDilemma(Scenario scenario){
        for(IMoralDilemmaDetectorModule module : detectorModules){
            if(module.isMoralDilemma(scenario)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Boolean> detectMoralDilemmas(Collection<? extends Scenario> scenarios){
        ArrayList<Boolean> result = new ArrayList<>();

        for(Scenario scenario : scenarios){
            boolean dilemma = false;
            for(IMoralDilemmaDetectorModule module : detectorModules){
                if(module.isMoralDilemma(scenario)){
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
