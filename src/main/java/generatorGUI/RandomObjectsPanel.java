package generatorGUI;

import generator.Model;
import generator.ModelBuilder;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class RandomObjectsPanel extends ScenarioPanel {
    private JSpinner maxRandomAnimalsSpinner;
    private JSpinner maxRandomPeopleSpinner;
    private JSpinner maxRandomObstaclesSpinner;
    private JSpinner maxRandomVehiclesSpinner;

    private TextField randomAnimalsProbabilityTextField;
    private TextField randomPeopleProbabilityTextField;
    private TextField randomObstaclesProbabilityTextField;
    private TextField randomVehiclesProbabilityTextField;

    private JCheckBox randomAnimalsCheckbox;
    private JCheckBox randomHumansCheckbox;
    private JCheckBox randomObstaclesCheckbox;
    private JCheckBox randomVehiclesCheckbox;

    public RandomObjectsPanel() {
        this.setLayout(null);
        this.setBounds(40, 510, 900, 210);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        JLabel randomModeLabel = new JLabel("Choose random elements in scenario", SwingConstants.CENTER);
        randomModeLabel.setBounds(0, 10, 400, 30);
        this.add(randomModeLabel);

        randomAnimalsCheckbox = new JCheckBox("random animals");
        randomAnimalsCheckbox.setBounds(50, 50, 300, 30);
        this.add(randomAnimalsCheckbox);

        randomObstaclesCheckbox = new JCheckBox("random obstacles");
        randomObstaclesCheckbox.setBounds(50, 90, 300, 30);
        this.add(randomObstaclesCheckbox);

        randomHumansCheckbox = new JCheckBox("random people");
        randomHumansCheckbox.setBounds(50, 130, 300, 30);
        this.add(randomHumansCheckbox);

        randomVehiclesCheckbox = new JCheckBox("random vehicles");
        randomVehiclesCheckbox.setBounds(50, 170, 300, 30);
        this.add(randomVehiclesCheckbox);

        JLabel probabilitiesLabel = new JLabel("Set probabilities", SwingConstants.CENTER);
        probabilitiesLabel.setBounds(450, 10, 450, 30);
        this.add(probabilitiesLabel);

        maxRandomAnimalsSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        randomAnimalsProbabilityTextField = new TextField();
        JPanel animalsProbabilityPanel = getProbabilitiesPanel(50, maxRandomAnimalsSpinner, randomAnimalsProbabilityTextField, randomAnimalsCheckbox);
        this.add(animalsProbabilityPanel);

        maxRandomObstaclesSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        randomObstaclesProbabilityTextField = new TextField();
        JPanel obstaclesProbabilityPanel = getProbabilitiesPanel(90, maxRandomObstaclesSpinner, randomObstaclesProbabilityTextField, randomObstaclesCheckbox);
        this.add(obstaclesProbabilityPanel);

        maxRandomPeopleSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        randomPeopleProbabilityTextField = new TextField();
        JPanel peopleProbabilityPanel = getProbabilitiesPanel(130, maxRandomPeopleSpinner, randomPeopleProbabilityTextField, randomHumansCheckbox);
        this.add(peopleProbabilityPanel);

        maxRandomVehiclesSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 15,1));
        randomVehiclesProbabilityTextField = new TextField();
        JPanel vehiclesProbabilityPanel = getProbabilitiesPanel(170, maxRandomVehiclesSpinner, randomVehiclesProbabilityTextField, randomVehiclesCheckbox);
        this.add(vehiclesProbabilityPanel);

        disableInputFields();
    }

    public void addRandomElements(Model model) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ModelBuilder modelBuilder = new ModelBuilder(model);
        if(randomAnimalsCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxRandomAnimalsSpinner);
            double[] probabilities = getProbabilities(randomAnimalsProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            modelBuilder.addAnimal(objectNumbers, probabilities);
        }
        if(randomHumansCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxRandomPeopleSpinner);
            double[] probabilities = getProbabilities(randomPeopleProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            modelBuilder.pedestrianJaywalking(objectNumbers, probabilities);
        }
        if(randomObstaclesCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxRandomObstaclesSpinner);
            double[] probabilities = getProbabilities(randomObstaclesProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            modelBuilder.addObstacle(objectNumbers, probabilities);
        }
        if(randomVehiclesCheckbox.isSelected()) {
            int[] objectNumbers = getObjectsNumbers(maxRandomVehiclesSpinner);
            double[] probabilities = getProbabilities(randomVehiclesProbabilityTextField);
            checkValidity(objectNumbers, probabilities);
            modelBuilder.addVehicles(objectNumbers, probabilities);
        }
    }
}
