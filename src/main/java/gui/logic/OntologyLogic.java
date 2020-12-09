package gui.logic;

import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.ScenarioReader;
import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.SimulatorEngine;
import generator.*;
import gui.OrderOfDecisionsWindow;
import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.Decision;
import project.MyFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

public class OntologyLogic {

    public static final String baseIRI = "http://webprotege.stanford.edu/";
    public static final String defaultPathToOntology = "src/main/resources/traffic_ontology.owl";


    public static MyFactory getFactory(String pathToOwlFile) {
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = null;
        try {
            ontology = ontologyManager.loadOntologyFromOntologyDocument(new File(pathToOwlFile));
        } catch (OWLOntologyCreationException e) {
            System.err.println("Problem during loading ontology");
            e.printStackTrace();
        }
        return new MyFactory(ontology);
    }

    public static Model getModelFromOntology(MyFactory factory, String scenarioName) {

        ScenarioReader scenarioReader = new ScenarioReader(factory);
        int scenarioNumber = Integer.parseInt(StringUtils.substringBefore(scenarioName, "_"));
        Model model = scenarioReader.getModel(scenarioNumber);
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }


    // na razie na sztywno korzystamy z BaseScenarioGenerator
    public static Model getModelFromGenerator(MyFactory factory) {
        BaseScenarioGenerator generator = new BaseScenarioGenerator(factory, baseIRI);
        Model model = null;
        try {
            model = generator.generate();
        } catch (NoSuchMethodException e) {
            System.err.println("Problem during generating scenario");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Problem during generating scenario");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Problem during generating scenario");
            e.printStackTrace();
        }

        try {
            new ScenarioFactory(model, factory)
                    .pedestrianOnCrossing(new int[]{10}, new double[]{1});
        }catch (FileNotFoundException e){
            System.err.println("File not found in generating");
            e.printStackTrace();
        }
        catch (OWLOntologyCreationException e){
            System.err.println("Cannot create ontology");
            e.printStackTrace();
        }

        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static Map<Decision, Set<Actor>> getCollidedEntities(IConsequenceContainer consequenceContainer, MyFactory factory, Model scenarioModel) {
        CollisionConsequencePredictor collisionConsequencePredictor =
                new CollisionConsequencePredictor(consequenceContainer, factory);
        SimulatorEngine simulatorEngine = new SimulatorEngine(scenarioModel, collisionConsequencePredictor, factory);
        Map<Decision, Set<Actor>> collidedEntities = simulatorEngine.simulateAll();

        return collidedEntities;
    }

    public static void saveOwlOntology(MyFactory factory){
        try {
            factory.saveOwlOntology();
        } catch (OWLOntologyStorageException e) {
            System.err.println("Problem during saving ontology");
            e.printStackTrace();
        }
    }

    public static String getOptimumDecision(Map<String, Integer> decisionCosts) {
        ArrayList<String> decisionsWithMinimalCost = new ArrayList<>();
        int currentMinimum = Integer.MAX_VALUE;
        for (String decisionName : decisionCosts.keySet()) {
            Integer cost = decisionCosts.get(decisionName);
            if (cost < currentMinimum) {
                currentMinimum = cost;
                decisionsWithMinimalCost = new ArrayList<>();
                decisionsWithMinimalCost.add(decisionName);
            }
            if (cost == currentMinimum) {
                decisionsWithMinimalCost.add(decisionName);
            }
        }
        if (decisionsWithMinimalCost.size() == 1) {
            return decisionsWithMinimalCost.get(0);
        }

        List<String> orderFromFile = OrderOfDecisionsWindow.getOrderFromFile(OrderOfDecisionsWindow.pathToCustomOrder);


        List<String> preferableOrderOfDecisions = new ArrayList<>();
        for(String decision : orderFromFile){
            switch (decision){
                case "Follow":
                    preferableOrderOfDecisions.add("follow");
                    break;
                case "Stop":
                    preferableOrderOfDecisions.add("stop");
                    break;
                case "Change lanes by right":
                    preferableOrderOfDecisions.add("change_lane_right_by_");
                    break;
                case "Change lanes by left":
                    preferableOrderOfDecisions.add("change_lane_left_by_");
                    break;
                case "Turn right":
                    preferableOrderOfDecisions.add("turn_right");
                    break;
                case "Turn left":
                    preferableOrderOfDecisions.add("turn_left");
                    break;
                default:
            }
        }

        for (String pattern : preferableOrderOfDecisions) {
            String decision = getDecisionThatSatisfyPattern(decisionsWithMinimalCost, pattern);
            if (StringUtils.isNotBlank(decision)) {
                return decision;
            }
        }
        return decisionsWithMinimalCost.get(0);
    }

    public static String getDecisionThatSatisfyPattern(List<String> decisions, String pattern) {
        return decisions.stream()
                .filter(s -> {
                    if (s.startsWith(pattern)) {
                        return true;
                    } else return false;
                })
                .sorted()
                .findFirst()
                .orElse(null);
    }

}
