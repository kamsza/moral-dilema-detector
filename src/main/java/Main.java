import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import DilemmaDetector.Consequences.ConsequenceContainer;
import DilemmaDetector.Consequences.DecisionCostCalculator;
import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.Modules.*;
import DilemmaDetector.MoralDilemmaDetector;
import DilemmaDetector.ScenarioReader;
import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.SimulatorEngine;
import generator.*;
import org.swrlapi.parser.SWRLParseException;
import project.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import visualization.Visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class Main {

    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static Model getModelFromGenerator(MyFactory factory) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator2 generator = new BaseScenarioGenerator2(factory, baseIRI);
        Model model = generator.generate();
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static Model getModelFromReader(MyFactory factory, int number) throws OWLOntologyCreationException {
        ScenarioReader scenarioReader = new ScenarioReader();
        Model model = scenarioReader.getModel(number);
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static Model getModelUsingModelBuilder(Model scenarioModel) throws FileNotFoundException, OWLOntologyCreationException {
        scenarioModel = new ScenarioFactory(scenarioModel)
                    .pedestrianOnCrossing(new int[]{1}, new double[]{1}).getModel();
//                    .animalOnRoad(new int[]{1}, new double[]{1}).getModel();
        return scenarioModel;
    }

    public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException {
        // Create OWLOntology instance using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);
        MoralDilemmaDetector.Builder builder = new MoralDilemmaDetector.Builder();

        //SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();

        MoralDilemmaDetector mdd = builder
//                .addModule(new SWRLInferredModule(ontology, factory))
                .addModule(new KilledModule(factory))
                .addModule(new LightlyInjuredModule(factory))
                .addModule(new SeverelyInjuredModule(factory))
                .addModule(new InjuredModule(factory))
                //.addModule(new MaterialValueModule(factory))
                .build();

        for(int i=0; i<1; i++) {
            Model scenarioModel = getModelFromGenerator(factory);
//            Model scenarioModel = getModelFromReader(factory,197);

            Set leftLanes = scenarioModel.getLanes().get(Model.Side.LEFT).entrySet();
            Set rightLanes =  scenarioModel.getLanes().get(Model.Side.RIGHT).entrySet();

            Visualization.getImage(scenarioModel);

            System.out.println(scenarioModel.getScenario().getOwlIndividual());
            IConsequenceContainer consequenceContainer = new ConsequenceContainer(factory);
            CollisionConsequencePredictor collisionConsequencePredictor =
                    new CollisionConsequencePredictor(consequenceContainer, factory);

            SimulatorEngine simulatorEngine = new SimulatorEngine(scenarioModel, collisionConsequencePredictor, factory);
            Map<Decision, Set<Actor>> collidedEntities = simulatorEngine.simulateAll();
            System.out.println("Collided entities:");
            for(Map.Entry<Decision, Set<Actor>> entry : collidedEntities.entrySet()){
                for(Actor actor : entry.getValue()){
                    System.out.println("ACTOR  " + actor.getEntity());
                }
            }

            DecisionCostCalculator costCalculator = new DecisionCostCalculator(consequenceContainer, factory);

            for(Map.Entry<Decision, Set<Actor>> entry : collidedEntities.entrySet()) {
                System.out.println(entry.getKey().toString()+ "  " + costCalculator.getSummarizedCostForDecision(entry.getKey()));
                for (Actor a : entry.getValue()) System.out.println(a.getEntity());
            }

            consequenceContainer.saveConsequencesToOntology();
            System.out.println(mdd.detectMoralDilemma(scenarioModel));

            try {
                factory.saveOwlOntology();
            } catch (OWLOntologyStorageException ignored) {
            }
            Visualization.getImage(scenarioModel);
        }
    }


    public static void testQuery(OWLOntology ontology) throws SQWRLException, SWRLParseException {

        // Create SQWRL query engine using the SWRLAPI
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

        // Create and execute a SQWRL query using the SWRLAPI
        SQWRLResult result = queryEngine.runSQWRLQuery("q1","webprotege:scenario(?s) -> sqwrl:select(?s)");
    }
}
