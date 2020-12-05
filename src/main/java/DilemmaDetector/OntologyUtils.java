package DilemmaDetector;

import generator.BaseScenarioGenerator2;
import generator.DecisionGenerator;
import generator.Model;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.OWLFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class OntologyUtils {
    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static Model getModelFromGenerator(OWLFactory factory) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator2 generator = new BaseScenarioGenerator2(factory, baseIRI);
        Model model = generator.generate();
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static OWLFactory getMyFactoryInstance() throws OWLOntologyCreationException {
        return new OWLFactory(getOntologyInstance());
    }

    public static OWLOntology getOntologyInstance() throws OWLOntologyCreationException {
        // Create OWLOntology instance using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        return ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

    }
}
