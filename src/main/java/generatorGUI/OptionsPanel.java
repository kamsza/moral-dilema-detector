package generatorGUI;

import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class OptionsPanel extends JPanel implements ActionListener, ValueHandler {
    private String ontologyFilepath = "src/main/resources/traffic_ontology.owl";

    private JLabel ontologyFileNameLabel;
    private JSpinner scenariosNoSpinner;

    private JCheckBox saveInOriginalFileCheckbox;
    private JCheckBox createVisualizationCheckbox;

    public OptionsPanel() {
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setSize(900, 140);

        JButton loadOntologyButton = new JButton("change");
        loadOntologyButton.setBounds(450, 13, 90, 24);
        loadOntologyButton.addActionListener(this);
        this.add(loadOntologyButton);

        JLabel ontologyFileTitleLabel = new JLabel("Ontology file:", SwingConstants.LEFT);
        ontologyFileTitleLabel.setBounds(50, 10, 100, 30);
        this.add(ontologyFileTitleLabel);

        ontologyFileNameLabel = new JLabel("traffic_ontology.owl", SwingConstants.LEFT);
        ontologyFileNameLabel.setBounds(150, 10, 250, 30);
        this.add(ontologyFileNameLabel);

        JLabel scenariosNoLabel = new JLabel("Number of scenarios to create:", SwingConstants.LEFT);
        scenariosNoLabel.setBounds(50, 40, 200, 30);
        this.add(scenariosNoLabel);

        SpinnerModel scenariosNoValue = new SpinnerNumberModel(1, 0, 10000000,1);
        scenariosNoSpinner = new JSpinner(scenariosNoValue);
        scenariosNoSpinner.setBounds(250, 43, 100, 24);
        this.add(scenariosNoSpinner);

        saveInOriginalFileCheckbox = new JCheckBox("override original file");
        saveInOriginalFileCheckbox.setBounds(50, 70, 500, 30);
        this.add(saveInOriginalFileCheckbox);

        createVisualizationCheckbox = new JCheckBox("create visualization");
        createVisualizationCheckbox.setBounds(50, 100, 400, 30);
        this.add(createVisualizationCheckbox);
    }

    @Override
    // load ontology from file
    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String fileExtension = Files.getFileExtension(jFileChooser.getSelectedFile().getAbsolutePath());
            if(!fileExtension.equals("owl")) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "Chosen file with wrong extension: " + fileExtension +
                                "\nChoose ontology file with .owl extension",
                        "Wrong file extension",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                String filepath = jFileChooser.getSelectedFile().getAbsolutePath();
                String shortenFilepath = (filepath.length() > 35) ? "... " + filepath.substring(filepath.length()-35) : filepath;
                ontologyFileNameLabel.setText(shortenFilepath);
                ontologyFilepath = filepath;
            }
        }
    }

    public String getOntologyFilepath() {
        return ontologyFilepath;
    }

    public int getScenariosNo() {
        return getJSpinnerValue(scenariosNoSpinner);
    }

    public boolean getOverrideOriginalFileCheckbox() {
        return saveInOriginalFileCheckbox.isSelected();
    }

    public boolean getCreateVisualizationCheckbox() {
        return createVisualizationCheckbox.isSelected();
    }

    public void disableComponents() {
        Arrays.stream(this.getComponents())
                .forEach(c -> c.setEnabled(false));
    }

    public void enableComponents() {
        Arrays.stream(this.getComponents())
                .forEach(c -> c.setEnabled(true));
    }
}
