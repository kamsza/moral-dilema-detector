package generatorGUI;

import generator.Model;
import generator.ModelBuilder;
import generator.ProbRand;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class AdditionalSettingsPanel extends ScenarioPanel implements ActionListener {

    private JCheckBox setLanesNoCheckbox;
    private JCheckBox addJunctionCheckbox;
    private JCheckBox saveInOriginalFileCheckbox;
    private JCheckBox createVisualizationCheckbox;

    private JSpinner maxLanesSpinner;
    private JSpinner junctionSpinner;

    private TextField maxLanesTextField;
    private TextField junctionTextField;

    private JLabel ontologyOutDirLabel;
    private JLabel visualizationOutDirLabel;

    private JButton ontologyDirChangeButton;
    private JButton visualizationDirChangeButton;

    private String ontologyOutDir = "src\\main\\resources\\ontologies";
    private String visualizationOutDir = "src\\main\\resources\\vis_out";

    public AdditionalSettingsPanel() {
        this.setLayout(null);
        this.setBounds(40, 740, 900, 200);
        this.setBackground(new Color(237, 245, 252));
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        JLabel scenarioTypeLabel = new JLabel("Additional settings", SwingConstants.CENTER);
        scenarioTypeLabel.setBounds(0, 0, 900, 30);
        this.add(scenarioTypeLabel);

        setLanesNoCheckbox = new JCheckBox("set custom number of lanes");
        setLanesNoCheckbox.setBounds(50, 40, 300, 30);
        this.add(setLanesNoCheckbox);

        maxLanesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        maxLanesTextField = new TextField();
        JPanel maxLanesPanel = getProbabilitiesPanel(40, maxLanesSpinner, maxLanesTextField, setLanesNoCheckbox);
        this.add(maxLanesPanel);

        addJunctionCheckbox = new JCheckBox("add junction");
        addJunctionCheckbox.setBounds(50, 70, 300, 30);
        this.add(addJunctionCheckbox);

        junctionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        junctionTextField = new TextField();
        JPanel junctionPanel = getProbabilitiesPanel(70, junctionSpinner, junctionTextField, addJunctionCheckbox);
        this.add(junctionPanel);

        saveInOriginalFileCheckbox = new JCheckBox("override original file");
        saveInOriginalFileCheckbox.setBounds(50, 100, 350, 30);
        this.add(saveInOriginalFileCheckbox);

        JCheckBox changeOntologyOutDirCheckbox = new JCheckBox("change output directory");
        changeOntologyOutDirCheckbox.setBounds(50, 130, 350, 30);
        this.add(changeOntologyOutDirCheckbox);

        saveInOriginalFileCheckbox.addItemListener(e -> {
            changeOntologyOutDirCheckbox.setSelected(false);
            changeOntologyOutDirCheckbox.setEnabled(!saveInOriginalFileCheckbox.isSelected());
        });

        ontologyOutDirLabel = new JLabel(ontologyOutDir);
        ontologyDirChangeButton = new JButton("change");
        ontologyDirChangeButton.addActionListener(this);
        this.add(getDirPanel(130, ontologyOutDirLabel, ontologyDirChangeButton, changeOntologyOutDirCheckbox));

        createVisualizationCheckbox = new JCheckBox("create visualization");
        createVisualizationCheckbox.setBounds(50, 160, 350, 30);
        this.add(createVisualizationCheckbox);

        visualizationOutDirLabel = new JLabel(visualizationOutDir);
        visualizationDirChangeButton = new JButton("change");
        visualizationDirChangeButton.addActionListener(this);
        this.add(getDirPanel(160, visualizationOutDirLabel, visualizationDirChangeButton, createVisualizationCheckbox));

        setComponentsBackground();
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

    public int getMaxLanesSpinnerValue() {
        return getJSpinnerValue(maxLanesSpinner);
    }

    public double[] getMaxLanesTextFieldValue() {
        String errMsg = "Probabilities in lanes count field must sum up to 1";
        return getProbabilities(maxLanesTextField, true, errMsg);
    }

    public String getOntologyOutputDir() {
        return ontologyOutDir;
    }

    public String getVisualizationOutDir() {
        return visualizationOutDir;
    }

    public boolean getJunctionCheckbox() {
        return addJunctionCheckbox.isSelected();
    }

    public void addJunction(Model model) throws FileNotFoundException, OWLOntologyCreationException {
        int roadsNum = ProbRand.randInt(getJSpinnerValue(junctionSpinner),
                getProbabilities(junctionTextField, true, "Probabilities in lanes count field must sum up to 1"));
        ModelBuilder mb = new ModelBuilder(model);
        mb.addJunction(roadsNum);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object eventSource = actionEvent.getSource();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose output directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String dirPath = chooser.getSelectedFile().getAbsolutePath();
            String shortenDirPath = (dirPath.length() > 25) ? "... " + dirPath.substring(dirPath.length() - 25) : dirPath;
            if (eventSource == ontologyDirChangeButton) {
                ontologyOutDirLabel.setText(shortenDirPath);
                ontologyOutDir = dirPath;
            } else if (eventSource == visualizationDirChangeButton) {
                visualizationOutDirLabel.setText(shortenDirPath);
                visualizationOutDir = dirPath;
            }
        }
    }
}
