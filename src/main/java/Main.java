import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import DilemmaDetector.Consequences.ConsequenceContainer;
import DilemmaDetector.Consequences.DecisionCostCalculator;
import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.ScenarioReader;
import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.SimulatorEngine;
import generator.*;
import project.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
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
        ScenarioReader scenarioReader = new ScenarioReader(factory);
        Model model = scenarioReader.getModelWithVisualisation(number);
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static Model getModelUsingModelBuilder(Model scenarioModel, MyFactory factory) throws FileNotFoundException, OWLOntologyCreationException {
        scenarioModel = new ScenarioFactory(scenarioModel, factory)
                    .pedestrianOnCrossing(new int[]{1}, new double[]{1}).getModel();
//                    .animalOnRoad(new int[]{1}, new double[]{1}).getModel();
        return scenarioModel;
    }

    public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, FileNotFoundException {
        // Create OWLOntology instance using the OWLAPI
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);

        //SWRLAPIFactory.createSWRLRuleEngine(ontology).infer();

        for(int i=0; i<1; i++) {
//            Model scenarioModel = getModelFromGenerator(factory);
            Model scenarioModel = getModelFromReader(factory,235);

//            new ScenarioFactory(scenarioModel, factory)
//                    .pedestrianOnCrossing(new int[]{10}, new double[]{1});

            Set leftLanes = scenarioModel.getMainRoad().getLanes().get(Model.Side.LEFT).entrySet();
            Set rightLanes =  scenarioModel.getMainRoad().getLanes().get(Model.Side.RIGHT).entrySet();

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

//            consequenceContainer.saveConsequencesToOntology();

//            try {
//                factory.saveOwlOntology();
//            } catch (OWLOntologyStorageException ignored) {
//            }
            Visualization.getImage(scenarioModel);
        }
    }
}
