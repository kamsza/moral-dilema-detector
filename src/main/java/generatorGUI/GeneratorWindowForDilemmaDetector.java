package generatorGUI;

import DilemmaDetector.Consequences.ConsequenceContainer;
import DilemmaDetector.Consequences.CustomPhilosophy;
import DilemmaDetector.Consequences.DecisionCostCalculator;
import DilemmaDetector.Consequences.PhilosophyParameter;
import DilemmaDetector.Simulator.Actor;
import generator.BaseScenarioGenerator;
import generator.DecisionGenerator;
import generator.Model;
import generator.MyFactorySingleton;
import gui.DashboardWindow;
import gui.InfoWindow;
import gui.WarningWindow;
import gui.logic.OntologyLogic;
import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.Decision;
import project.OWLFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static gui.DashboardWindow.getActionNameFromDecision;

public class GeneratorWindowForDilemmaDetector extends JFrame implements ActionListener, ValueHandler {

    private ScenarioTypePanel scenarioTypePanel;
    private RandomObjectsPanel randomObjectsPanel;
    private SimpleAdditionalSettingsPanel simpleAdditionalSettingsPanel;
    private JButton generateButton;
    private DashboardWindow dashboardWindow;
    private OWLFactory factory;
    private CustomPhilosophy customPhilosophy;

    public GeneratorWindowForDilemmaDetector(DashboardWindow dashboardWindow, OWLFactory factory) {
        this.dashboardWindow = dashboardWindow;
        this.factory = factory;

        this.setTitle("Road scenario generator");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min((int) (screenSize.width * 0.95), 1040);
        int height = Math.min((int) (screenSize.height * 0.95), 700);
        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(1040, 880));

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 20));
        contentPanel.setPreferredSize(new Dimension(1000, 835));
        contentPanel.setBackground(new Color(209, 215, 230));
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
        simpleAdditionalSettingsPanel.setPreferredSize(new Dimension(900, 120));
        contentPanel.add(simpleAdditionalSettingsPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(900, 30));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        generateButton = new JButton("generate");
        generateButton.setPreferredSize(new Dimension(300, 30));
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


    public void setCustomPhilosophy(CustomPhilosophy customPhilosophy) {
        this.customPhilosophy = customPhilosophy;
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

        if (simpleAdditionalSettingsPanel.getEvaluateMultipleScenariosCheckboxValue()) {

            ArrayList<ArrayList<String>> summaryList = new ArrayList<>();

            int numberOfScenarios = simpleAdditionalSettingsPanel.getNumberOfScenariosSpinnerValue();


            for (int i = 0; i < numberOfScenarios; i++) {

                try {
                    BaseScenarioGenerator generator;
                    generator = new BaseScenarioGenerator(OntologyLogic.defaultPathToOntology);
                    int lanesNumber = simpleAdditionalSettingsPanel.getMaxLanesSpinnerValue();
                    model = generator.generate(lanesNumber);
                    scenarioTypePanel.addScenario(model);
                    randomObjectsPanel.addRandomElements(model);


                    DecisionGenerator decisionGenerator = new DecisionGenerator(factory, OntologyLogic.baseIRI);
                    decisionGenerator.generate(model);


                    ConsequenceContainer consequenceContainer = new ConsequenceContainer(factory);
                    Map<Decision, Set<Actor>> collidedEntities = OntologyLogic.getCollidedEntities(consequenceContainer, factory, model);
                    //consequenceContainer.saveConsequencesToOntology();

                    Map<String, Integer> decisionCosts = new HashMap<>();

                    DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(consequenceContainer, factory, customPhilosophy);


                    for (Decision decision : collidedEntities.keySet()) {
                            decisionCosts.put(getActionNameFromDecision(decision.toString()), decisionCostCalculator.getSummarizedCostForDecision(decision));


                    }

                    String bestDecision = OntologyLogic.getOptimumDecision(decisionCosts);
                    int dilemmaThreshold = customPhilosophy.getParameters().get(PhilosophyParameter.DILEMMA_THRESHOLD);
                    boolean isMoralDilemma = decisionCosts.get(bestDecision) > dilemmaThreshold ? true : false;
                    String isMoralDilemmaString = isMoralDilemma ? "YES" : "NO";

                    System.out.println(isMoralDilemmaString);


                    FileOutputStream outputStream = null;
                    outputStream = FileUtils.openOutputStream(new File(OntologyLogic.defaultPathToOntology), false);
                    factory.getOwlOntology().getOWLOntologyManager().saveOntology(factory.getOwlOntology(), outputStream);

                    String fileName = customExport( String.valueOf(i/10), false, "src\\main\\resources\\ontologies");

                    ArrayList<String> summary = new ArrayList<>();
                    summary.add("Nazwa scenariusza");
                    summary.add(fileName);
                    summary.add(isMoralDilemmaString);
                    summaryList.add(summary);

                } catch (OWLOntologyCreationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | OWLOntologyStorageException ex) {
                    showExceptionWindow(ex.getMessage());
                }
            }

            InfoWindow infoWindow = new InfoWindow(this, summaryList);
            infoWindow.setVisible(true);

        }
    }


    public String customExport(String name, boolean overrideFile, String outputDir) throws IOException, OWLOntologyCreationException, OWLOntologyStorageException {
        OWLFactory factory = MyFactorySingleton.getFactory();
        String exportFilepath = "placeholder";
        if (!overrideFile) {
            String extension = ".owl";
            String template = outputDir + "\\traffic_ontology";
            exportFilepath = template + "_" + name + extension;
            int index = 1;
            while (new File(exportFilepath).exists()) {
                exportFilepath = template + " (" + index + ")" + extension;
                index++;
            }
        }
        FileOutputStream outputStream = FileUtils.openOutputStream(new File(exportFilepath), false);
        factory.getOwlOntology().getOWLOntologyManager().saveOntology(factory.getOwlOntology(), outputStream);
        return exportFilepath;
    }
}
