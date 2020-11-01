import DilemmaDetector.Modules.*;
import DilemmaDetector.MoralDilemmaDetector;
import generator.*;
import org.swrlapi.parser.SWRLParseException;
import project.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static Model getModelFromGenerator(MyFactory factory) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator generator = new BaseScenarioGenerator(factory, baseIRI);
        Model model = generator.generate();
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Create OWLOntology instance using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);
        MoralDilemmaDetector.Builder builder = new MoralDilemmaDetector.Builder();

        //SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();

        ConsequenceGenerator consequenceGenerator = new ConsequenceGenerator(factory);

        MoralDilemmaDetector mdd = builder
                //.addModule(new SWRLInferredModule(ontology, factory))
                .addModule(new KilledModule(factory))
                .addModule(new LightlyInjuredModule(factory))
                .addModule(new SeverelyInjuredModule(factory))
//                .addModule(new InjuredModule(factory))
                //.addModule(new MaterialValueModule(factory))
                .build();

        for(int i=0; i<10; i++) {
            Model scenarioModel = getModelFromGenerator(factory);
            consequenceGenerator.predict(scenarioModel);
            System.out.println(scenarioModel.getScenario().getOwlIndividual());
            System.out.println(mdd.detectMoralDilemma(scenarioModel));
        }
    }

    public static void testQuery(OWLOntology ontology) throws SQWRLException, SWRLParseException {

        // Create SQWRL query engine using the SWRLAPI
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

        // Create and execute a SQWRL query using the SWRLAPI
        SQWRLResult result = queryEngine.runSQWRLQuery("q1","webprotege:scenario(?s) -> sqwrl:select(?s)");
    }
}