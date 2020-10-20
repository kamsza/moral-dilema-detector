import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import DilemmaDetector.Consequences.ConsequenceContainer;
import DilemmaDetector.Consequences.DecisionCostCalculator;
import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.Modules.*;
import DilemmaDetector.MoralDilemmaDetector;
import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.RigidBodyMapper;
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
import java.util.List;
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
//                .addModule(new InjuredModule(factory))
                //.addModule(new MaterialValueModule(factory))
                .build();

        for(int i=0; i<1; i++) {
            Model scenarioModel = getModelFromGenerator(factory);
//
//            scenarioModel = new ScenarioFactory(scenarioModel)
//                    .animalOnRoad(new int[]{1}, new double[]{1}).getModel();

//            scenarioModel = new ScenarioFactory(scenarioModel)
//                    .carApproaching().getModel();
//            new ModelBuilder(scenarioModel)
//                    .addVehicles(new int[]{1}, new double[]{1.0});
//;
            Set leftLanes = scenarioModel.getLanes().get(Model.Side.LEFT).entrySet();
            Set rightLanes =  scenarioModel.getLanes().get(Model.Side.RIGHT).entrySet();

            int lastLeftLane  = leftLanes.size();
            int lastRightLane = rightLanes.size();


            Visualization.getImage(scenarioModel);

            System.out.println(scenarioModel.getScenario().getOwlIndividual());
            IConsequenceContainer consequenceContainer = new ConsequenceContainer(factory);
            CollisionConsequencePredictor collisionConsequencePredictor =
                    new CollisionConsequencePredictor(consequenceContainer, factory, scenarioModel);
            SimulatorEngine simulatorEngine = new SimulatorEngine(scenarioModel, collisionConsequencePredictor);
            Map<Decision, Set<Actor>> collidedEntities = simulatorEngine.simulateAll(lastLeftLane, lastRightLane);
            System.out.println("Collided entities:");
            for(Map.Entry<Decision, Set<Actor>> entry : collidedEntities.entrySet()){
                for(Actor actor : entry.getValue()){
                    System.out.println("ACTOR  " + actor.getEntity());
                }
            }

            DecisionCostCalculator costCalculator = new DecisionCostCalculator(consequenceContainer, factory);

            for(Map.Entry<Decision, Set<Actor>> entry : collidedEntities.entrySet()) {
                System.out.println(entry.getKey().toString()+ "  " + costCalculator.calculateCostForDecision(entry.getKey()));
                for (Actor a : entry.getValue()) System.out.println(a.getEntity());
            }

            System.out.println(mdd.detectMoralDilemma(scenarioModel));
//            scenarioModel.export();
            try {
                factory.saveOwlOntology();
            } catch (OWLOntologyStorageException ignored) {

            }

        }
    }

    public static void testQuery(OWLOntology ontology) throws SQWRLException, SWRLParseException {

        // Create SQWRL query engine using the SWRLAPI
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

        // Create and execute a SQWRL query using the SWRLAPI
        SQWRLResult result = queryEngine.runSQWRLQuery("q1","webprotege:scenario(?s) -> sqwrl:select(?s)");
    }
}
