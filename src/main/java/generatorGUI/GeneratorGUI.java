package generatorGUI;

import generator.BaseScenarioGenerator;
import generator.Model;
import generator.ProbRand;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import visualization.Visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorGUI extends JFrame implements ActionListener, ValueHandler  {
    public static void main(String[] args) {
        new GeneratorGUI();
    }

    private OptionsPanel optionsPanel;
    private ScenarioTypePanel scenarioTypePanel;
    private RandomObjectsPanel randomObjectsPanel;
    private AdditionalSettingsPanel additionalSettingsPanel;
    private JButton generateButton;

    public GeneratorGUI() {
        this.setTitle("Road scenario generator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min((int)(screenSize.width * 0.95), 1040);
        int height = Math.min((int)(screenSize.height * 0.95), 880);
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(1040, 880));

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 20));
        contentPanel.setPreferredSize(new Dimension(1000, 835));
        contentPanel.setBackground( new Color(209, 215, 230));
        this.add(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        this.setContentPane(scrollPane);

        optionsPanel = new OptionsPanel();
        optionsPanel.setPreferredSize(new Dimension(900, 90));
        contentPanel.add(optionsPanel);

        scenarioTypePanel = new ScenarioTypePanel();
        scenarioTypePanel.setPreferredSize(new Dimension(900, 230));
        contentPanel.add(scenarioTypePanel);

        randomObjectsPanel = new RandomObjectsPanel();
        randomObjectsPanel.setPreferredSize(new Dimension(900, 170));
        contentPanel.add(randomObjectsPanel);

        additionalSettingsPanel = new AdditionalSettingsPanel();
        additionalSettingsPanel.setPreferredSize(new Dimension(900, 200));
        contentPanel.add(additionalSettingsPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(900, 30));
        buttonPanel.setBackground( new Color(0,0,0,0));
        generateButton = new JButton("generate");
        generateButton.setPreferredSize(new Dimension( 300, 30));
        generateButton.addActionListener(this);
        buttonPanel.add(generateButton);
        contentPanel.add(buttonPanel);

        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    // generate button pressed
    public void actionPerformed(ActionEvent e) {
        disableComponents();
        runGenerateWorker();
    }

    private void runGenerateWorker() {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                generate();
                return null;
            }

            @Override
            protected void done() {
                enableComponents();
            }
        };

        swingWorker.execute();
    }
    private void generate() {
        Model model = null;
        int scenariosNo = optionsPanel.getScenariosNo();
        String ontologyFilepath = optionsPanel.getOntologyFilepath();

        try {
            for (int i = 0; i < scenariosNo; i++) {
                BaseScenarioGenerator generator;
                generator = new BaseScenarioGenerator(ontologyFilepath);
                if(additionalSettingsPanel.getChangeLaneNoCheckbox()) {
                    int lanesNo = ProbRand.randInt(additionalSettingsPanel.getMaxLanesSpinnerValue(), additionalSettingsPanel.getMaxLanesTextFieldValue());
                    model = generator.generate(lanesNo);
                }
                else {
                    model = generator.generate();
                }

                scenarioTypePanel.addScenario(model);
                randomObjectsPanel.addRandomElements(model);
                if (additionalSettingsPanel.getCreateVisualizationCheckbox())
                    Visualization.getImage(model, additionalSettingsPanel.getVisualizationOutDir());
            }
            if(scenariosNo > 0)
                model.export(ontologyFilepath, additionalSettingsPanel.getOverrideOriginalFileCheckbox(), additionalSettingsPanel.getOntologyOutputDir());
        } catch (OWLOntologyCreationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | OWLOntologyStorageException ex) {
            showExceptionWindow(ex.getMessage());
        }
    }

    private void disableComponents() {
        generateButton.setText("generating in progress ...");
        generateButton.setEnabled(false);

        optionsPanel.disableComponents();
        scenarioTypePanel.disableComponents();
        randomObjectsPanel.disableComponents();
        additionalSettingsPanel.disableComponents();
    }

    private void enableComponents() {
        generateButton.setText("generate");
        generateButton.setEnabled(true);

        optionsPanel.enableComponents();
        scenarioTypePanel.enableComponents();
        randomObjectsPanel.enableComponents();
        additionalSettingsPanel.enableComponents();
    }
}
