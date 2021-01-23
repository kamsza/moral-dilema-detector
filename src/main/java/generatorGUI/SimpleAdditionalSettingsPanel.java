package generatorGUI;

import javax.swing.*;
import java.awt.*;

public class SimpleAdditionalSettingsPanel extends ScenarioPanel {

    private JCheckBox setLanesNoCheckbox;

    private JSpinner maxLanesSpinner;

    private TextField maxLanesTextField;

    private JCheckBox evaluateMultipleScenarios;

    private JSpinner numberOfScenariosSpinner;

    private JTextField numberOfScenariosTextField;


    public SimpleAdditionalSettingsPanel() {
        this.setLayout(null);
        this.setBounds(40, 740, 900, 120);
        this.setBackground( new Color(237, 245, 252));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        JLabel scenarioTypeLabel = new JLabel("Additional settings", SwingConstants.CENTER);
        scenarioTypeLabel.setBounds(0, 0, 900, 30);
        this.add(scenarioTypeLabel);

        setLanesNoCheckbox = new JCheckBox("set custom number of lanes");
        setLanesNoCheckbox.setBounds(50, 40, 300, 30);
        this.add(setLanesNoCheckbox);

        maxLanesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5,1));
        maxLanesTextField = new TextField();
        JPanel maxLanesPanel = getProbabilitiesPanel(40, maxLanesSpinner, maxLanesTextField, setLanesNoCheckbox);
        this.add(maxLanesPanel);

        evaluateMultipleScenarios = new JCheckBox("evaluate multiple scenarios");
        evaluateMultipleScenarios.setBounds(50, 80, 300, 30);
        this.add(evaluateMultipleScenarios);

        numberOfScenariosSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100,1));
        numberOfScenariosSpinner.setBounds(530, 80, 50, 25);
        this.add(numberOfScenariosSpinner);

        setComponentsBackground();
        disableInputFields();
    }

    public boolean getChangeLaneNoCheckbox() {
        return setLanesNoCheckbox.isSelected();
    }

    public int getMaxLanesSpinnerValue() { return getJSpinnerValue(maxLanesSpinner); }

    public boolean getEvaluateMultipleScenariosCheckboxValue() {return evaluateMultipleScenarios.isSelected();}

    public int getNumberOfScenariosSpinnerValue() { return getJSpinnerValue(numberOfScenariosSpinner); }


}
