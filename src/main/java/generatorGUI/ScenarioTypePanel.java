package generatorGUI;

import generator.Model;
import generator.ScenarioFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

class ScenarioTypePanel extends ScenarioPanel {
    private JCheckBox animalScenarioCheckbox;
    private JCheckBox carApproachingCheckbox;
    private JCheckBox carOvertakingCheckbox;
    private JCheckBox humanIllegallyCrossingCheckbox;
    private JCheckBox humanOnPedestrianCrossingCheckbox;
    private JCheckBox obstacleCheckbox;

    private JSpinner maxAnimalsSpinner;
    private JSpinner maxIllegalHumansSpinner;
    private JSpinner maxHumansOnPedCrossSpinner;
    private JSpinner maxObstaclesSpinner;

    private TextField animalsProbabilityTextField;
    private TextField illegalHumansProbabilityTextField;
    private TextField humansOnPedCrossProbabilityTextField;
    private TextField obstaclesProbabilityTextField;
    private TextField carApproachingProbabilityTextField;
    private TextField carOvertakingProbabilityTextField;

    public ScenarioTypePanel() {
        this.setLayout(null);
        this.setBounds(40, 200, 900, 230);
        this.setBackground( new Color(237, 245, 252));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        JLabel scenarioTypeLabel = new JLabel("Choose type of scenario", SwingConstants.CENTER);
        scenarioTypeLabel.setBounds(0, 0, 400, 30);
        this.add(scenarioTypeLabel);

        animalScenarioCheckbox = new JCheckBox("animal on the road");
        animalScenarioCheckbox.setBounds(50, 40, 300, 30);
        this.add(animalScenarioCheckbox);

        carApproachingCheckbox = new JCheckBox("car approaching another vehicle");
        carApproachingCheckbox.setBounds(50, 70, 300, 30);
        this.add(carApproachingCheckbox);

        carOvertakingCheckbox = new JCheckBox("car overtaking another vehicle");
        carOvertakingCheckbox.setBounds(50, 100, 300, 30);
        this.add(carOvertakingCheckbox);

        humanIllegallyCrossingCheckbox = new JCheckBox("human illegaly crossing the road");
        humanIllegallyCrossingCheckbox.setBounds(50, 130, 300, 30);
        this.add(humanIllegallyCrossingCheckbox);

        humanOnPedestrianCrossingCheckbox = new JCheckBox("human on pedestrian crossing");
        humanOnPedestrianCrossingCheckbox.setBounds(50, 160, 300, 30);
        this.add(humanOnPedestrianCrossingCheckbox);

        obstacleCheckbox = new JCheckBox("obstacle on the road");
        obstacleCheckbox.setBounds(50, 190, 300, 30);
        this.add(obstacleCheckbox);

        JLabel probabilitiesLabel = new JLabel("Set probabilities", SwingConstants.CENTER);
        probabilitiesLabel.setBounds(450, 0, 450, 30);
        this.add(probabilitiesLabel);

        maxAnimalsSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        animalsProbabilityTextField = new TextField();
        JPanel animalsProbabilityPanel = getProbabilitiesPanel(40, maxAnimalsSpinner, animalsProbabilityTextField, animalScenarioCheckbox);
        this.add(animalsProbabilityPanel);

        carApproachingProbabilityTextField = new TextField();
        JPanel carApproachingProbabilityPanel = getProbabilityPanel(70, carApproachingProbabilityTextField, carApproachingCheckbox);
        this.add(carApproachingProbabilityPanel);

        carOvertakingProbabilityTextField = new TextField();
        JPanel carOvertakingProbabilityPanel = getProbabilityPanel(100, carOvertakingProbabilityTextField, carOvertakingCheckbox);
        this.add(carOvertakingProbabilityPanel);

        maxIllegalHumansSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        illegalHumansProbabilityTextField = new TextField();
        JPanel illegalHumansProbabilityPanel = getProbabilitiesPanel(130, maxIllegalHumansSpinner, illegalHumansProbabilityTextField, humanIllegallyCrossingCheckbox);
        this.add(illegalHumansProbabilityPanel);

        maxHumansOnPedCrossSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        humansOnPedCrossProbabilityTextField = new TextField();
        JPanel humansOnPedCrossProbabilityPanel = getProbabilitiesPanel(160, maxHumansOnPedCrossSpinner, humansOnPedCrossProbabilityTextField, humanOnPedestrianCrossingCheckbox);
        this.add(humansOnPedCrossProbabilityPanel);

        maxObstaclesSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        obstaclesProbabilityTextField = new TextField();
        JPanel obstaclesProbabilityPanel = getProbabilitiesPanel(190, maxObstaclesSpinner, obstaclesProbabilityTextField, obstacleCheckbox);
        this.add(obstaclesProbabilityPanel);

        setComponentsBackground();
        disableInputFields();
    }



    public void addScenario(Model model) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ScenarioFactory scenarioFactory = new ScenarioFactory(model);
        if(animalScenarioCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxAnimalsSpinner);
            double[] probabilities = getProbabilities(animalsProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            scenarioFactory.animalOnRoad(objectNumbers, probabilities);
        }
        if(carApproachingCheckbox.isSelected()) {
            double probability = getProbability(carApproachingProbabilityTextField);
            scenarioFactory.carApproaching(probability);
        }
        if(carOvertakingCheckbox.isSelected()) {
            double probability = getProbability(carOvertakingProbabilityTextField);
            scenarioFactory.carOvertaking(probability);
        }
        if(humanIllegallyCrossingCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxIllegalHumansSpinner);
            double[] probabilities = getProbabilities(illegalHumansProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            scenarioFactory.pedestrianJaywalking(objectNumbers, probabilities);
        }
        if(humanOnPedestrianCrossingCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxHumansOnPedCrossSpinner);
            double[] probabilities = getProbabilities(humansOnPedCrossProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            scenarioFactory.pedestrianOnCrossing(objectNumbers, probabilities);
        }
        if(obstacleCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxObstaclesSpinner);
            double[] probabilities = getProbabilities(obstaclesProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            scenarioFactory.obstacleOnRoad(objectNumbers, probabilities);
        }
    }
}
