package generatorGUI;

import generator.BaseScenarioGenerator;
import generator.Model;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import visualization.Visualization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorGUI extends JFrame implements ActionListener, ValueHandler  {
    public static void main(String[] args) {
        GeneratorGUI guiWindow = new GeneratorGUI();
        guiWindow.setVisible(true);
    }

    private OptionsPanel optionsPanel;
    private ScenarioTypePanel scenarioTypePanel;
    private RandomObjectsPanel randomObjectsPanel;
    private JButton generateButton;

    public GeneratorGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 850);
        this.setResizable(false);
        this.setTitle("Road scenario generator");
        this.setLayout(null);

        optionsPanel = new OptionsPanel();
        optionsPanel.setBounds(40, 40, 900, 140);
        this.add(optionsPanel);

        scenarioTypePanel = new ScenarioTypePanel();
        scenarioTypePanel.setBounds(40, 200, 900, 290);
        this.add(scenarioTypePanel);

        randomObjectsPanel = new RandomObjectsPanel();
        randomObjectsPanel.setBounds(40, 510, 900, 210);
        this.add(randomObjectsPanel);

        generateButton = new JButton("generate");
        generateButton.setBounds(340, 740, 300, 30);
        generateButton.addActionListener(this);
        this.add(generateButton);
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
                model = generator.generate();

                scenarioTypePanel.addScenario(model);
                randomObjectsPanel.addRandomElements(model);
                if (optionsPanel.getCreateVisualizationCheckbox())
                    Visualization.getImage(model);
            }
            if(scenariosNo > 0)
                model.export(ontologyFilepath, optionsPanel.getOverrideOriginalFileCheckbox());
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
    }

    private void enableComponents() {
        generateButton.setText("generate");
        generateButton.setEnabled(true);

        optionsPanel.enableComponents();
        scenarioTypePanel.enableComponents();
        randomObjectsPanel.enableComponents();
    }
}
