package generatorGUI;

import javax.swing.*;
import java.awt.*;

public class SimpleAdditionalSettingsPanel extends ScenarioPanel {

    private JCheckBox setLanesNoCheckbox;

    private JSpinner maxLanesSpinner;

    private TextField maxLanesTextField;


    public SimpleAdditionalSettingsPanel() {
        this.setLayout(null);
        this.setBounds(40, 740, 900, 100);
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

        setComponentsBackground();
        disableInputFields();
    }

    public boolean getChangeLaneNoCheckbox() {
        return setLanesNoCheckbox.isSelected();
    }

    public int getMaxLanesSpinnerValue() { return getJSpinnerValue(maxLanesSpinner); }

}
