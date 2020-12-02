package DilemmaDetector;


import DilemmaDetector.Consequences.*;
import DilemmaDetector.Simulator.SimulatorEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import generator.DecisionGenerator;
import generator.Model;
import gui.logic.OntologyLogic;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.Decision;
import project.MyFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class DilemmaDetector {
    public static final String BASE_IRI = "http://webprotege.stanford.edu/";

    private Model model = null;
    private CustomPhilosophy philosophy = new CustomPhilosophy();
    private MyFactory factory = null;

    private ScenarioReader scenarioReader = null;
    private SimulatorEngine simulatorEngine = null;
    private IConsequenceContainer consequenceContainer = null;
    private CollisionConsequencePredictor collisionConsequencePredictor = null;

    private DecisionGenerator decisionGenerator = null;
    private DecisionCostCalculator decisionCostCalculator = null;
    private Map<Decision, Integer> decisionCosts = new HashMap<>();

    Decision bestDecision = null;

    public DilemmaDetector (){}

    public DilemmaDetector (CustomPhilosophy philosophy){
        this.philosophy = philosophy;
    }

    public CustomPhilosophy getPhilosophy() {
        return philosophy;
    }

    public void setPhilosophy(CustomPhilosophy philosophy) {
        this.philosophy = philosophy;
        decisionCostCalculator.setCustomPhilosophy(philosophy);
    }

    public CustomPhilosophy loadPhilosophyFromJson(String philosophyPath){
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(philosophyPath);
        try {
            setPhilosophy(objectMapper.readValue(file, CustomPhilosophy.class));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return philosophy;
    }

    public void loadOntology () throws OWLOntologyCreationException  {
        scenarioReader = new ScenarioReader();
        updateFactory();
    }

    public void loadOntology (String ontologyPath) throws OWLOntologyCreationException {
        scenarioReader = new ScenarioReader(ontologyPath);
        updateFactory();
    }

    public void calculateMorality(int scenarioNumber) {
        loadScenarioByName(scenarioNumber);
        decisionGenerator.generate(model);

        simulatorEngine.simulateAll();

        calculateDecisionCosts();
    }

    public void calculateDecisionCosts() {
        Set<Decision> decisions = OntologyLogic.getCollidedEntities(consequenceContainer, factory, model).keySet();

        DecisionCostCalculator decisionCostCalculator =
                new DecisionCostCalculator(consequenceContainer, factory, philosophy);

        decisionCosts.clear();

        for (Decision decision : decisions) {
            decisionCosts.put(decision, decisionCostCalculator.getSummarizedCostForDecision(decision));
        }
    }

    public boolean containsMoralDilemma(){
        int dilemmaThreshold = philosophy.getParameters().get(PhilosophyParameter.DILEMMA_THRESHOLD);
        bestDecision = getBestDecision(dilemmaThreshold);
        return bestDecision == null;
    }

    public Decision getBestDecision() {
        return getBestDecision(Integer.MAX_VALUE);
    }

    public Integer getBestCost() {
        return decisionCosts.get(getBestDecision());
    }

    private void updateFactory(){
        factory = scenarioReader.getFactory();
        consequenceContainer = new ConsequenceContainer(factory);
        decisionGenerator = new DecisionGenerator(factory, BASE_IRI);
        decisionCostCalculator =
                new DecisionCostCalculator(consequenceContainer, factory, philosophy);
    }

    private void loadScenarioByName(int scenarioNumber) {
        if (scenarioReader == null){
            System.out.println("ERROR: Ontology not loaded, call loadOntology() first.");
        }
        model = scenarioReader.getModel(scenarioNumber);
        collisionConsequencePredictor = new CollisionConsequencePredictor(consequenceContainer, factory);
        simulatorEngine = new SimulatorEngine(model, collisionConsequencePredictor, factory);
    }

    private Map<String, Integer> getDecisionCostMapByString(){
        Map<String, Integer> costs = new HashMap<>();
        for(Map.Entry<Decision, Integer> cost: decisionCosts.entrySet()){
            costs.put(cost.getKey().toString(), cost.getValue());
        }
        return costs;
    }

    private Decision getBestDecision(int dilemmaThreshold){
        String bestDecisionName = OntologyLogic.getOptimumDecision(getDecisionCostMapByString());
        Set<Decision> decisions = decisionCosts.keySet();
        Decision bestDecision = decisions.stream().filter(s ->
                s.toString().equals(bestDecisionName)
        ).findFirst().orElse(null);
        return bestDecision;
    }
}
