package generatorGUI;

import generator.BaseScenarioGenerator;
import generator.Model;
import gui.DashboardWindow;
import gui.logic.OntologyLogic;
import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.OWLFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorWindowForDilemmaDetector extends JFrame implements ActionListener, ValueHandler {

    private ScenarioTypePanel scenarioTypePanel;
    private RandomObjectsPanel randomObjectsPanel;
    private SimpleAdditionalSettingsPanel simpleAdditionalSettingsPanel;
    private JButton generateButton;
    private DashboardWindow dashboardWindow;
    private OWLFactory factory;

    public GeneratorWindowForDilemmaDetector(DashboardWindow dashboardWindow, OWLFactory factory) {
        this.dashboardWindow = dashboardWindow;
        this.factory = factory;

        this.setTitle("Road scenario generator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min((int)(screenSize.width * 0.95), 1040);
        int height = Math.min((int)(screenSize.height * 0.95), 700);
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(1040, 880));

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 20));
        contentPanel.setPreferredSize(new Dimension(1000, 835));
        contentPanel.setBackground( new Color(209, 215, 230));
        this.add(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        this.setContentPane(scrollPane);

        scenarioTypePanel = new ScenarioTypePanel();
        scenarioTypePanel.setPreferredSize(new Dimension(900, 230));
        contentPanel.add(scenarioTypePanel);

        randomObjectsPanel = new RandomObjectsPanel();
        randomObjectsPanel.setPreferredSize(new Dimension(900, 170));
        contentPanel.add(randomObjectsPanel);

        simpleAdditionalSettingsPanel = new SimpleAdditionalSettingsPanel();
        simpleAdditionalSettingsPanel.setPreferredSize(new Dimension(900, 100));
        contentPanel.add(simpleAdditionalSettingsPanel);

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
        generate();
    }


    private void generate() {

        Model model = null;

        try {
            BaseScenarioGenerator generator;
            generator = new BaseScenarioGenerator(OntologyLogic.defaultPathToOntology);
            int lanesNumber = simpleAdditionalSettingsPanel.getMaxLanesSpinnerValue();
            model = generator.generate(lanesNumber);
            scenarioTypePanel.addScenario(model);
            randomObjectsPanel.addRandomElements(model);

            FileOutputStream outputStream = null;
            outputStream = FileUtils.openOutputStream(new File(OntologyLogic.defaultPathToOntology), false);
            factory.getOwlOntology().getOWLOntologyManager().saveOntology(factory.getOwlOntology(), outputStream);
            model.export(OntologyLogic.defaultPathToOntology, true);
        } catch (OWLOntologyCreationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | OWLOntologyStorageException ex) {
            showExceptionWindow(ex.getMessage());
        }
        dashboardWindow.getModelFromWrapper(model);
    }

}
