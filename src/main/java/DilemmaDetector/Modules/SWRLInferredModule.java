package DilemmaDetector.Modules;

import DilemmaDetector.IMoralDilemmaDetectorModule;
import generator.Model;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;
import project.Scenario;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.factory.SWRLAPIFactory;

public class SWRLInferredModule implements IMoralDilemmaDetectorModule {
    MyFactory factory;
    OWLOntology ontology;

    public SWRLInferredModule(OWLOntology ontology, MyFactory factory) {
        this.factory = factory;
        this.ontology = ontology;
    }

    @Override
    public boolean isMoralDilemma(Model model) {
        try {
            factory.saveOwlOntology();
        }catch (OWLOntologyStorageException ignored){

        }
        SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();
        return factory.getMoral_dilemma(model.getScenario().getOwlIndividual().getIRI().toString()) != null;
    }
}
