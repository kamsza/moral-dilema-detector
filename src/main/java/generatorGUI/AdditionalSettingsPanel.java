package generatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdditionalSettingsPanel extends ScenarioPanel implements ActionListener {

    private JCheckBox setLanesNoCheckbox;
    private JSpinner maxLanesSpinner;
    private TextField maxLanesTextField;

    private JCheckBox saveInOriginalFileCheckbox;
    private JCheckBox createVisualizationCheckbox;

    private JLabel ontologyOutDirLabel;
    private JLabel visualizationOutDirLabel;

    private JButton ontologyDirChangeButton;
    private JButton visualizationDirChangeButton;

    private String ontologyOutDir = "src\\main\\resources\\ontologies";
    private String visualizationOutDir = "src\\main\\resources\\vis_out";

    public AdditionalSettingsPanel() {
        this.setLayout(null);
        this.setBounds(40, 740, 900, 180);
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

        JCheckBox changeOntologyOutDirCheckbox = new JCheckBox("change output directory");
        changeOntologyOutDirCheckbox.setBounds(50, 110, 350, 30);
        this.add(changeOntologyOutDirCheckbox);

        ontologyOutDirLabel = new JLabel(ontologyOutDir);
        ontologyDirChangeButton = new JButton("change");
        ontologyDirChangeButton.addActionListener(this);
        this.add(getDirPanel(110, ontologyOutDirLabel, ontologyDirChangeButton, changeOntologyOutDirCheckbox));

        createVisualizationCheckbox = new JCheckBox("create visualization");
        createVisualizationCheckbox.setBounds(50, 140, 350, 30);
        this.add(createVisualizationCheckbox);

        visualizationOutDirLabel = new JLabel(visualizationOutDir);
        visualizationDirChangeButton = new JButton("change");
        visualizationDirChangeButton.addActionListener(this);
        this.add(getDirPanel(140, visualizationOutDirLabel, visualizationDirChangeButton, createVisualizationCheckbox));

        disableInputFields();
    }

    public boolean getChangeLaneNoCheckbox() {
        return setLanesNoCheckbox.isSelected();
    }

    public boolean getOverrideOriginalFileCheckbox() {
        return saveInOriginalFileCheckbox.isSelected();
    }

    public boolean getCreateVisualizationCheckbox() {
        return createVisualizationCheckbox.isSelected();
    }

    public int getMaxLanesSpinnerValue() { return getJSpinnerValue(maxLanesSpinner); }

    public double[] getMaxLanesTextFieldValue() { return getProbabilities(maxLanesTextField); }

    public String getOntologyOutputDir() { return ontologyOutDir; }

    public String getVisualizationOutDir() { return visualizationOutDir; }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object eventSource = actionEvent.getSource();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose output directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String dirPath = chooser.getSelectedFile().getAbsolutePath();
            String shortenDirPath = (dirPath.length() > 25) ? "... " + dirPath.substring(dirPath.length()-25) : dirPath;
            if (eventSource == ontologyDirChangeButton) {
                ontologyOutDirLabel.setText(shortenDirPath);
                ontologyOutDir = dirPath;
            }

            else if (eventSource == visualizationDirChangeButton) {
                visualizationOutDirLabel.setText(shortenDirPath);
                visualizationOutDir = dirPath;
            }
        }
    }
}
