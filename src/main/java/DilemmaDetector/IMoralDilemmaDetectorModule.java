package DilemmaDetector;

import generator.Model;
import project.Scenario;

public interface IMoralDilemmaDetectorModule {
    boolean isMoralDilemma(Scenario scenario);
    boolean isMoralDilemma(Model model);
}
