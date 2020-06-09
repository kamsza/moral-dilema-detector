package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import project.MyFactory;
import project.Scenario;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.factory.SWRLAPIFactory;

public class SWRLInferredModule implements IMoralDilemmaDetectorModule {
    MyFactory factory;

    public SWRLInferredModule(OWLOntology ontology, MyFactory factory){
        SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();
        this.factory = factory;
    }

    @Override
    public boolean isMoralDilemma(Scenario scenario) {
        return factory.getMoral_dilemma(scenario.getOwlIndividual().getIRI().toString()) != null;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        return isMoralDilemma(model.getScenario());
    }
}
