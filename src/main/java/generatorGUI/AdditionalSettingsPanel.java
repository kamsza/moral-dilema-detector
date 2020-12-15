package generatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AdditionalSettingsPanel extends ScenarioPanel implements ActionListener {

    private JCheckBox setLanesNoCheckbox;
    private JSpinner maxLanesSpinner;
    private TextField maxLanesTextField;

    private JCheckBox saveInOriginalFileCheckbox;
    private JCheckBox createVisualizationCheckbox;

    private JLabel ontologyOutDir;
    private JLabel visualizationOutDir;

    public AdditionalSettingsPanel() {
        this.setLayout(null);
        this.setBounds(40, 740, 900, 150);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        JLabel scenarioTypeLabel = new JLabel("Additional settings", SwingConstants.CENTER);
        scenarioTypeLabel.setBounds(0, 10, 900, 30);
        this.add(scenarioTypeLabel);

        setLanesNoCheckbox = new JCheckBox("set custom number of lanes");
        setLanesNoCheckbox.setBounds(50, 50, 300, 30);
        this.add(setLanesNoCheckbox);

        maxLanesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5,1));
        maxLanesTextField = new TextField();
        JPanel animalsProbabilityPanel = getProbabilitiesPanel(50, maxLanesSpinner, maxLanesTextField, setLanesNoCheckbox);
        this.add(animalsProbabilityPanel);

        saveInOriginalFileCheckbox = new JCheckBox("override original file");
        saveInOriginalFileCheckbox.setBounds(50, 80, 350, 30);
        this.add(saveInOriginalFileCheckbox);

        ontologyOutDir = new JLabel("aaaaaaaaaaa");
        JButton ontologyDirChangeButton = new JButton("change");
        ontologyDirChangeButton.addActionListener(this);
        this.add(getDirPanel(80, ontologyOutDir, ontologyDirChangeButton, saveInOriginalFileCheckbox));

        createVisualizationCheckbox = new JCheckBox("create visualization");
        createVisualizationCheckbox.setBounds(50, 110, 350, 30);
        this.add(createVisualizationCheckbox);

        visualizationOutDir = new JLabel("bbbbbbbbbbbbbbbbbbbbbbbb");
        JButton visualizationDirChangeButton = new JButton("change");
        visualizationDirChangeButton.addActionListener(this);
        this.add(getDirPanel(110, visualizationOutDir, visualizationDirChangeButton, createVisualizationCheckbox));

        disableInputFields();
    }


    public boolean getOverrideOriginalFileCheckbox() {
        return saveInOriginalFileCheckbox.isSelected();
    }

    public boolean getCreateVisualizationCheckbox() {
        return createVisualizationCheckbox.isSelected();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
