import DecisionGenerator.DecisionGenerator;
import DilemmaDetector.Modules.InjuredModule;
import DilemmaDetector.Modules.KilledModule;
import DilemmaDetector.Modules.MaterialValueModule;
import DilemmaDetector.MoralDilemmaDetector;
import generator.AnimalOnRoadSG;
import generator.BaseScenarioGenerator;
import generator.Model;
import project.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;

public class Main {

    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static Model getModelFromGenerator(MyFactory factory){
        BaseScenarioGenerator generator;
        generator = new AnimalOnRoadSG(factory, baseIRI);
//        generator = new CarApproachingSG(factory, baseIRI);
//        generator = new CarOvertakingSG(factory, baseIRI);
//        generator = new ObstacleOnRoadSG(factory, baseIRI);
//        generator = new PedestrianIllegallyCrossingSG(factory, baseIRI);
//        generator = new PedestrianOnCrosswalkSG(factory, baseIRI);
        return generator.generate();
    }

    public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, SQWRLException {
        // Create OWLOntology instance using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        //OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("Ontology/changed_ontology.owl"));
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);
        MoralDilemmaDetector.Builder builder = new MoralDilemmaDetector.Builder();

        //SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();

        Model scenarioModel = getModelFromGenerator(factory);
        Scenario scenario = scenarioModel.getScenario();
//        DecisionGenerator decisionGenerator = new DecisionGenerator(factory);
        ConsequencePredictor consequencePredictor = new ConsequencePredictor(factory);
        consequencePredictor.predict(scenarioModel);



//        for(Scenario scenario : factory.getAllScenarioInstances()) {
//            decisionGenerator.generate(scenario);
//            consequencePredictor.predict(scenario);
//        }




        MoralDilemmaDetector mdd = builder
                //.addModule(new SWRLInferredModule(ontology, factory))
                .addModule(new KilledModule(factory))
                .addModule(new InjuredModule(factory))
//                .addModule(new MaterialValueModule(factory))
                .build();

//        for(Scenario scenario : factory.getAllScenarioInstances()){
            System.out.println(scenario.getOwlIndividual());
            System.out.println(mdd.detectMoralDilemma(scenario));
//        }

    }

    public static void testQuery(OWLOntology ontology) throws SQWRLException {

        // Create SQWRL query engine using the SWRLAPI
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

        // Create and execute a SQWRL query using the SWRLAPI
        //SQWRLResult result = queryEngine.runSQWRLQuery("q1","webprotege:scenario(?s) -> sqwrl:select(?s)");

        System.out.println("ACT:");

        SQWRLResult result = queryEngine.runSQWRLQuery("KilledEntitiesAct");
        System.out.println("Killed entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }

        result = queryEngine.runSQWRLQuery("InjuredEntitiesAct");

        System.out.println("Injured entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }

        result = queryEngine.runSQWRLQuery("IntactEntitiesAct");

        System.out.println("Intact entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }

        System.out.println("STAY IDLE:");

        result = queryEngine.runSQWRLQuery("KilledEntitiesIdle");
        System.out.println("Killed entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }

        result = queryEngine.runSQWRLQuery("InjuredEntitiesIdle");

        System.out.println("Injured entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }

        result = queryEngine.runSQWRLQuery("IntactEntitiesIdle");

        System.out.println("Intact entities:");
        while (result.next()) {
            System.out.println(result.getNamedIndividual("s") + ": " + result.getLiteral("count(?e)").getInteger().toString());
        }
    }
}
