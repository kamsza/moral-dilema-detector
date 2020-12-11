package generatorGUI;

import generator.BaseScenarioGenerator;
import generator.Model;
import gui.DashboardWindow;
import gui.logic.OntologyLogic;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.MyFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorWindowForDilemmaDetector extends JFrame implements ActionListener, ValueHandler {

    private ScenarioTypePanel scenarioTypePanel;
    private RandomObjectsPanel randomObjectsPanel;
    private JButton generateButton;
    private DashboardWindow dashboardWindow;
    private MyFactory factory;

    public GeneratorWindowForDilemmaDetector(DashboardWindow dashboardWindow, MyFactory factory) {
        this.dashboardWindow = dashboardWindow;
        this.factory = factory;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setTitle("Road scenario generator");
        this.setLayout(null);

        scenarioTypePanel = new ScenarioTypePanel();
        scenarioTypePanel.setBounds(40, 40, 900, 290);
        this.add(scenarioTypePanel);

        randomObjectsPanel = new RandomObjectsPanel();
        randomObjectsPanel.setBounds(40, 350, 900, 210);
        this.add(randomObjectsPanel);

        generateButton = new JButton("generate");
        generateButton.setBounds(340, 580, 300, 30);
        generateButton.addActionListener(this);
        this.add(generateButton);
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
            generator = new BaseScenarioGenerator(factory, OntologyLogic.baseIRI);
            model = generator.generate();
            scenarioTypePanel.addScenario(model);
            randomObjectsPanel.addRandomElements(model);


        } catch (OWLOntologyCreationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException ex) {
            showExceptionWindow(ex.getMessage());
        }
        dashboardWindow.getModelFromWrapper(model);
    }

}
