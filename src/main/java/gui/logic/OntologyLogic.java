package gui.logic;

import DilemmaDetector.Consequences.CollisionConsequencePredictor;
import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.SimulatorEngine;
import generator.BaseScenarioGenerator2;
import generator.DecisionGenerator;
import generator.Model;
import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.function.Str;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.Decision;
import project.MyFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class OntologyLogic {

    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static MyFactory getFactory() {
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = null;
        try {
            ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));
        } catch (OWLOntologyCreationException e) {
            System.err.println("Problem during loading ontology");
            e.printStackTrace();
        }
        return new MyFactory(ontology);
    }

    // na razie na sztywno korzystamy z BaseScenarioGenerator2
    public static Model getModelFromGenerator(MyFactory factory) {
        BaseScenarioGenerator2 generator = new BaseScenarioGenerator2(factory, baseIRI);
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
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }

    public static Map<Decision, Set<Actor>> getCollidedEntities(IConsequenceContainer consequenceContainer, MyFactory factory, Model scenarioModel) {
        CollisionConsequencePredictor collisionConsequencePredictor =
                new CollisionConsequencePredictor(consequenceContainer, factory, scenarioModel);
        SimulatorEngine simulatorEngine = new SimulatorEngine(scenarioModel, collisionConsequencePredictor);
        Set leftLanes = scenarioModel.getLanes().get(Model.Side.LEFT).entrySet();
        int lastLeftLane = leftLanes.size();
        Set rightLanes = scenarioModel.getLanes().get(Model.Side.RIGHT).entrySet();
        int lastRightLane = rightLanes.size();
        Map<Decision, Set<Actor>> collidedEntities = simulatorEngine.simulateAll(lastLeftLane, lastRightLane);

        // pytanie czy może to być w tym miejscu, bo umieszczenie tego później miałoby sens,
        // jeśli korzytalibyśmy z MoralDilemmaDetector
        try {
            factory.saveOwlOntology();
        } catch (OWLOntologyStorageException e) {
            System.err.println("Problem during saving ontology");
            e.printStackTrace();
        }
        return collidedEntities;
    }


    // optymalnie jechać cały nie wykonywać manewrów,
    // jeśli konieczny to preferowane są w prawo ze względu na ruch prawostronny
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

        List<String> preferableOrderOfDecisions = List.of("follow",
                "change_lane_right_by_", "change_lane_left_by_", "turn_right", "turn_left");

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
