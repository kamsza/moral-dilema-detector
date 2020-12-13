package gui;

import DilemmaDetector.Consequences.*;
import DilemmaDetector.Simulator.Actor;
import com.fasterxml.jackson.databind.ObjectMapper;
import generator.DecisionGenerator;
import generator.Model;
import generator.MyFactorySingleton;
import generatorGUI.GeneratorWindowForDilemmaDetector;
import gui.logic.DecisionCost;
import gui.logic.OntologyLogic;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.Decision;
import project.MyFactory;
import visualization.Visualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DashboardWindow extends JFrame implements ActionListener {

    private JButton jButtonLoadFromFile;
    private JTextField jTextFieldScenarioName;
    private JButton jButtonLoadScenario;
    private JComboBox jComboBoxScenarios;
    private JButton jButtonGenerateScenario;
    private JLabel jLabelImageScenario;
    private JButton jButtonAddCustomPhilosophy;
    private JComboBox jComboBoxCustomPhilosophies;
    private JButton jButtonCalculate;
    private JLabel jLabelBestDecision;
    private JButton jButtonCustomPhilosophyShowDetails;
    private JLabel jLabelSelectPhilosophyPrompt;
    private JScrollPane jScrollPaneWithResults;
    private JCheckBox jCheckBoxEnableBraking;
    private JCheckBox jCheckBoxSaveResultsInOntology;
    private JTable jTableWithResults;
    private JTable jTableWithMoralResult;
    private JScrollPane jScrollPaneWithMoralResult;
    private JTable jTableWithBestDecision;
    private JScrollPane jScrollPaneWithBestDecision;
    private JButton jButtonSetOrderOfDecisions;
    private GeneratorWindowForDilemmaDetector generatorGui;
    private JLabel jLabelStartingPrompt;


    /// CONST
    private final String NO_FILE_SELECTED = "No file selected";
    private final List<String> POSSIBLE_SCENARIOS_LIST = new ArrayList<String>(Arrays.asList("Simple scenario",
            "Scenario with animals",
            "Scenario with crosswalk"));
    private final int IMAGE_WIDTH = 820;
    private final int IMAGE_HEIGHT = 400;
    private final int CENTER_CUSTOM_PHILOSOPHIES = 90;
    private final String PATH_CUSTOM_PHILOSOPHIES = "\\src\\main\\resources\\gui\\customPhilosophies\\";
    private final String PATH_BLANK_SCENARIO = "\\src\\main\\resources\\gui\\Example_scenario.png";
    private final int oneRowJTableHeight = 48;


    //business logic variables
    private Map<Decision, Set<Actor>> collidedEntities;
    private Model scenarioModel;
    private MyFactory factory;
    private MyFactory factoryForCalculator;
    private String pictureName;
    private IConsequenceContainer consequenceContainer;
    private Map<String, Integer> decisionCosts = new HashMap<>();
    private boolean isAnyCustomPhilosophy = true;
    private String pathToOwlFile = System.getProperty("user.dir") + "\\" + OntologyLogic.defaultPathToOntology;


    public DashboardWindow() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (Exception e) {
            System.err.println("Problem with UI Manager");
        }
        factory = OntologyLogic.getFactory(OntologyLogic.defaultPathToOntology);

        setSize(880, 800);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);

        jButtonLoadFromFile = new JButton("Load scenario from file");
        jButtonLoadFromFile.setBounds(20, 10, 400, 30);
        jButtonLoadFromFile.setToolTipText("Default file is: " + OntologyLogic.defaultPathToOntology);
        jButtonLoadFromFile.addActionListener(this);
        add(jButtonLoadFromFile);

        jTextFieldScenarioName = new JTextField("");
        jTextFieldScenarioName.setBounds(20, 40, 300, 30);
        add(jTextFieldScenarioName);
        PromptSupport.setPrompt("Enter scenario name", jTextFieldScenarioName);

        jButtonLoadScenario = new JButton("Load");
        jButtonLoadScenario.setBounds(320, 40, 100, 30);
        jButtonLoadScenario.addActionListener(this);
        add(jButtonLoadScenario);

        /*
        jComboBoxScenarios = new JComboBox(POSSIBLE_SCENARIOS_LIST.toArray());
        jComboBoxScenarios.setBounds(440, 10, 400, 30);
        add(jComboBoxScenarios);
        */

        jButtonGenerateScenario = new JButton("Use scenario generator");
        jButtonGenerateScenario.setBounds(440, 40, 400, 30);
        jButtonGenerateScenario.addActionListener(this);
        add(jButtonGenerateScenario);

        jLabelImageScenario = new JLabel(getStartingImageIcon());
        jLabelImageScenario.setBounds(20, 80, IMAGE_WIDTH, IMAGE_HEIGHT);
        jLabelImageScenario.setLayout(new FlowLayout(FlowLayout.CENTER));

        jLabelStartingPrompt = new JLabel("Example scenario - load or generate scenario");
        jLabelImageScenario.add(jLabelStartingPrompt);
        add(jLabelImageScenario);


        jCheckBoxEnableBraking = new JCheckBox("Enable braking", true);
        jCheckBoxEnableBraking.setBounds(20, 500, 180, 20);
        add(jCheckBoxEnableBraking);

        jCheckBoxSaveResultsInOntology = new JCheckBox("Save results to ontology", true);
        jCheckBoxSaveResultsInOntology.setToolTipText("<html>It's recommended option to persist results. <br> Based on that You may create SWRL rules in Protege</html>");
        jCheckBoxSaveResultsInOntology.setBounds(20, 520, 180, 20);
        add(jCheckBoxSaveResultsInOntology);

        /*
        jLabelSelectPhilosophyPrompt = new JLabel("Select custom philosophy");
        jLabelSelectPhilosophyPrompt.setBounds(50, 515, 200, 30);
        add(jLabelSelectPhilosophyPrompt);
         */
        jButtonAddCustomPhilosophy = new JButton("Add new");
        jButtonAddCustomPhilosophy.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 570, 500, 150, 30);
        jButtonAddCustomPhilosophy.addActionListener(this);
        add(jButtonAddCustomPhilosophy);

        jComboBoxCustomPhilosophies = new JComboBox(getCustomPhilosophiesNames().toArray());
        jComboBoxCustomPhilosophies.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 220, 500, 200, 30);
        add(jComboBoxCustomPhilosophies);

        jButtonCustomPhilosophyShowDetails = new JButton("Show details");
        jButtonCustomPhilosophyShowDetails.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 420, 500, 150, 30);
        jButtonCustomPhilosophyShowDetails.addActionListener(this);
        add(jButtonCustomPhilosophyShowDetails);

        jButtonCalculate = new JButton("Calculate");
        jButtonCalculate.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 420, 530, 300, 30);
        jButtonCalculate.addActionListener(this);
        add(jButtonCalculate);

        jButtonSetOrderOfDecisions = new JButton("Set order of decision");
        jButtonSetOrderOfDecisions.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 220, 530, 200, 30);
        jButtonSetOrderOfDecisions.addActionListener(this);
        add(jButtonSetOrderOfDecisions);

        jLabelBestDecision = new JLabel("");
        jLabelBestDecision.setBounds(50, 600, 400, 30);
        jLabelBestDecision.setVisible(false);
        add(jLabelBestDecision);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonLoadFromFile) jButtonLoadFromFileAction();
        if (eventSource == jButtonLoadScenario) jButtonLoadScenarioAction();
        if (eventSource == jButtonCustomPhilosophyShowDetails) jButtonCustomPhilosophyShowDetailsAction();
        if (eventSource == jButtonGenerateScenario) jButtonGenerateScenarioAction();
        if (eventSource == jButtonAddCustomPhilosophy) jButtonAddCustomPhilosophyAction();
        if (eventSource == jButtonCalculate) jButtonCalculateAction();
        if (eventSource == jButtonSetOrderOfDecisions) jButtonSetOrderOfDecisionsAction();

    }

    private void jButtonCustomPhilosophyShowDetailsAction() {
        if (!isAnyCustomPhilosophy) {
            WarningWindow warningWindow = new WarningWindow(this, "No custom philosophies to display");
            warningWindow.setVisible(true);
        } else {
            String philosophyName = jComboBoxCustomPhilosophies.getSelectedItem().toString() + ".json";
            CustomPhilosophy customPhilosophy = getCustomPhilosophyByName(philosophyName);
            CustomPhilosophyWindow customPhilosophyWindow = new ModifyCustomPhilosophyWindow(this, customPhilosophy);
            customPhilosophyWindow.setVisible(true);
        }
    }

    private void jButtonLoadScenarioAction() {
        jLabelStartingPrompt.setVisible(false);
        if (StringUtils.isBlank(jTextFieldScenarioName.getText())) {
            WarningWindow warningWindow = new WarningWindow(this, "Enter scenario name");
            warningWindow.setVisible(true);
        } else {
            if (StringUtils.isBlank(pathToOwlFile)) {
                WarningWindow warningWindow = new WarningWindow(this, "Select owl file");
                warningWindow.setVisible(true);
            } else {
                factory = OntologyLogic.getFactory(pathToOwlFile);
                try {
                    scenarioModel = OntologyLogic.getModelFromOntology(factory, jTextFieldScenarioName.getText());
                } catch (IllegalArgumentException exception) {
                    WarningWindow warningWindow = new WarningWindow(this, "There is no such scenario in owl file");
                    warningWindow.setVisible(true);
                    return;
                }

                DecisionGenerator decisionGenerator = new DecisionGenerator(factory, OntologyLogic.baseIRI);
                decisionGenerator.generate(scenarioModel);

                pictureName = Visualization.getImage(scenarioModel);
                jLabelImageScenario.setIcon(
                        getImageIcon(System.getProperty("user.dir")
                                + "\\src\\main\\resources\\vis_out\\"
                                + pictureName));

                consequenceContainer = new ConsequenceContainer(factory);
                collidedEntities = OntologyLogic.getCollidedEntities(consequenceContainer, factory, scenarioModel);
                consequenceContainer.saveConsequencesToOntology();

                saveToOntologyAccordingToCheckbox();


            }
        }
    }

    private void saveToOntologyAccordingToCheckbox() {
        factoryForCalculator = factory;
        if (jCheckBoxSaveResultsInOntology.isSelected()) {
            FileOutputStream outputStream = null;
            try {
                outputStream = FileUtils.openOutputStream(new File(OntologyLogic.defaultPathToOntology), false);
                factory.getOwlOntology().getOWLOntologyManager().saveOntology(factory.getOwlOntology(), outputStream);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (OWLOntologyStorageException e) {
                e.printStackTrace();
            }
        } else {
            factory = OntologyLogic.getFactory(OntologyLogic.defaultPathToOntology);
        }
    }

    private void jButtonLoadFromFileAction() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setFileFilter(new FileNameExtensionFilter("OWL files", "owl"));

        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            pathToOwlFile = jFileChooser.getSelectedFile().getAbsolutePath();
            jButtonLoadFromFile.setToolTipText("File: " + pathToOwlFile + " is loaded");
        } else
            pathToOwlFile = "";
    }

    private void jButtonGenerateScenarioAction() {
        jLabelStartingPrompt.setVisible(false);
        this.generatorGui = new GeneratorWindowForDilemmaDetector(this, factory);
        generatorGui.setVisible(true);

    }


    public void getModelFromWrapper(Model model) {

        Model scenarioModel = model;
        DecisionGenerator decisionGenerator = null;
        try {
            factory = MyFactorySingleton.getFactory(OntologyLogic.defaultPathToOntology);
            decisionGenerator = new DecisionGenerator(factory, OntologyLogic.baseIRI);

            decisionGenerator.generate(model);
            pictureName = Visualization.getImage(scenarioModel);
            generatorGui.dispose();
            jLabelImageScenario.setIcon(
                    getImageIcon(System.getProperty("user.dir")
                            + "\\src\\main\\resources\\vis_out\\"
                            + pictureName));

            consequenceContainer = new ConsequenceContainer(factory);
            collidedEntities = OntologyLogic.getCollidedEntities(consequenceContainer, factory, scenarioModel);
            consequenceContainer.saveConsequencesToOntology();

            saveToOntologyAccordingToCheckbox();

        } catch (OWLOntologyCreationException | IOException ex) {
            System.err.println(ex);
        }


    }

    private void jButtonSetOrderOfDecisionsAction() {
        OrderOfDecisionsWindow orderOfDecisionsWindow = new OrderOfDecisionsWindow(this);
        orderOfDecisionsWindow.setVisible(true);
    }


    private void jButtonAddCustomPhilosophyAction() {
        CustomPhilosophyWindow customPhilosophyWindow = new AddCustomPhilosophyWindow(this);
        customPhilosophyWindow.setVisible(true);
    }

    private void jButtonCalculateAction() {
        if (collidedEntities == null) {
            WarningWindow warningWindow = new WarningWindow(this, "Load or generate scenario");
            warningWindow.setVisible(true);
        } else {
            if (!isAnyCustomPhilosophy) {
                WarningWindow warningWindow = new WarningWindow(this, "Add custom philosophy");
                warningWindow.setVisible(true);
            } else {
                // load selected philosophy
                decisionCosts = new HashMap<>();
                String philosophyName = jComboBoxCustomPhilosophies.getSelectedItem().toString() + ".json";
                CustomPhilosophy customPhilosophy = getCustomPhilosophyByName(philosophyName);


                DecisionCostCalculator decisionCostCalculator = new DecisionCostCalculator(consequenceContainer, factoryForCalculator, customPhilosophy);


                for (Decision decision : collidedEntities.keySet()) {
                    if (jCheckBoxEnableBraking.isSelected()) {
                        decisionCosts.put(getActionNameFromDecision(decision.toString()), decisionCostCalculator.getSummarizedCostForDecision(decision));
                    } else {
                        if (decision.toString().indexOf("stop") == -1) {
                            decisionCosts.put(getActionNameFromDecision(decision.toString()), decisionCostCalculator.getSummarizedCostForDecision(decision));
                        }
                    }
                }

                String bestDecision = OntologyLogic.getOptimumDecision(decisionCosts);
                int dilemmaThreshold = customPhilosophy.getParameters().get(PhilosophyParameter.DILEMMA_THRESHOLD);
                boolean isMoralDilemma = decisionCosts.get(bestDecision) > dilemmaThreshold ? true : false;
                String isMoralDilemmaString = isMoralDilemma ? "YES" : "NO";
                if (jTableWithMoralResult != null) {
                    refreshOneRowTable(isMoralDilemmaString, jTableWithMoralResult, jScrollPaneWithMoralResult);
                    refreshOneRowTable(prepareDecisionNameToDisplay(bestDecision), jTableWithBestDecision, jScrollPaneWithBestDecision);

                } else {
                    jTableWithMoralResult = new JTable(prepareDefaultModel("Moral dilemma", isMoralDilemmaString));
                    centerValuesInTable(jTableWithMoralResult);
                    jScrollPaneWithMoralResult = new JScrollPane(jTableWithMoralResult);
                    jScrollPaneWithMoralResult.setBounds(20, 600, 250, oneRowJTableHeight);
                    add(jScrollPaneWithMoralResult);

                    jTableWithBestDecision =
                            new JTable(prepareDefaultModel("Optimum decision", prepareDecisionNameToDisplay(bestDecision)));
                    centerValuesInTable(jTableWithBestDecision);
                    jScrollPaneWithBestDecision = new JScrollPane(jTableWithBestDecision);
                    jScrollPaneWithBestDecision.setBounds(20, 600 + oneRowJTableHeight, 250, oneRowJTableHeight);
                    add(jScrollPaneWithBestDecision);
                }

                int numberOfDecisions = decisionCosts.size();
                ArrayList<DecisionCost> sortedCosts = new ArrayList<>();
                for (String decisionName : decisionCosts.keySet()) {
                    int cost = decisionCosts.get(decisionName);
                    int i = 0;
                    while (i < sortedCosts.size()) {
                        if (sortedCosts.get(i).getDecisionCost() < cost) {
                            i++;
                        } else {
                            break;
                        }
                    }
                    sortedCosts.add(i, new DecisionCost(prepareDecisionNameToDisplay(decisionName), cost));
                }
                for (DecisionCost dc : sortedCosts) {
                    System.out.println(dc.getDecisionName() + " " + dc.getDecisionCost());
                }

                if (jTableWithResults != null) {
                    DefaultTableModel defaultTableModel = (DefaultTableModel) jTableWithResults.getModel();
                    while (defaultTableModel.getRowCount() > 0) {
                        defaultTableModel.removeRow(0);
                    }

                    for (DecisionCost decisionCost : sortedCosts) {
                        Object[] row = new Object[2];
                        row[0] = decisionCost.getDecisionName();
                        row[1] = decisionCost.getDecisionCost();
                        defaultTableModel.addRow(row);
                    }

                    jTableWithResults.repaint();
                    jScrollPaneWithResults.repaint();
                    this.repaint();

                } else {

                    String[] columnNames = {"Action", "Moral cost"};
                    Object[][] data = new Object[numberOfDecisions][2];
                    int i = 0;
                    for (DecisionCost decisionCost : sortedCosts) {
                        data[i][0] = decisionCost.getDecisionName();
                        data[i][1] = decisionCost.getDecisionCost();
                        i++;
                    }
                    DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    jTableWithResults = new JTable(defaultTableModel);
                    jScrollPaneWithResults = new JScrollPane(jTableWithResults);
                    int jScrollPanelHeight = (jTableWithResults.getRowCount() + 1) * jTableWithResults.getRowHeight() + 7;
                    if (jScrollPanelHeight > 150) jScrollPanelHeight = 150;
                    jScrollPaneWithResults.setBounds(CENTER_CUSTOM_PHILOSOPHIES + 220, 600, 400, jScrollPanelHeight);
                    add(jScrollPaneWithResults);
                }
            }
        }
    }

    public void updateCustomPhilosophiesList() {
        List<String> customPhilosophiesNames = getCustomPhilosophiesNames();
        int size = customPhilosophiesNames.size();
        String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = customPhilosophiesNames.get(i);
        }
        ComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(strings);
        jComboBoxCustomPhilosophies.setModel(comboBoxModel);
    }

    private ImageIcon getStartingImageIcon() {
        return getImageIcon(System.getProperty("user.dir") + PATH_BLANK_SCENARIO);
    }

    private ImageIcon getImageIcon(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT,
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    public List<String> getCustomPhilosophiesNames() {
        File f = new File(System.getProperty("user.dir") +
                PATH_CUSTOM_PHILOSOPHIES);
        List<String> customPhilosophiesNames = new ArrayList<>();
        for (String name : f.list()) {
            if (name.equals(".gitignore")) continue;
            customPhilosophiesNames.add(StringUtils.substringBefore(name, ".json"));
        }
        if (customPhilosophiesNames.size() == 0) {
            customPhilosophiesNames.add("No custom philosophies yet");
            isAnyCustomPhilosophy = false;
        }
        isAnyCustomPhilosophy = true;
        return customPhilosophiesNames;
    }

    private CustomPhilosophy getCustomPhilosophyByName(String name) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(System.getProperty("user.dir") +
                PATH_CUSTOM_PHILOSOPHIES +
                name);
        CustomPhilosophy customPhilosophy = null;
        try {
            customPhilosophy = objectMapper.readValue(file, CustomPhilosophy.class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return customPhilosophy;
    }

    private String getActionNameFromDecision(String decisionString) {
        String tmp = StringUtils.substringAfter(decisionString, "has_action: _");
        return StringUtils.substringBefore(tmp, ";");
    }

    private String prepareDecisionNameToDisplay(String s) {
        if (s.startsWith("stop")) return "Stop";
        if (s.startsWith("turn_right")) return "Turn right";
        if (s.startsWith("turn_left")) return "Turn left";
        if (s.startsWith("follow")) return "Follow";

        int byIndex = s.indexOf("by");
        String ending = s.substring(byIndex + 3);
        String correctNumber = StringUtils.substringBefore(ending, "_");
        String snakeCaseResult = StringUtils.substringBefore(s, "by") + "by_" + correctNumber;
        return changeSnakeCase(snakeCaseResult);
    }

    private String changeSnakeCase(String s) {
        s = s.replaceAll("_", " ");
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }


    private void refreshOneRowTable(String valueToRefresh, JTable jTable, JScrollPane jScrollPane) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable.getModel();
        defaultTableModel.removeRow(0);
        Object[] row = new Object[1];
        row[0] = valueToRefresh;
        defaultTableModel.addRow(row);
        jTable.repaint();
        jScrollPane.repaint();
        this.repaint();
    }

    private DefaultTableModel prepareDefaultModel(String columnName, String value) {
        String[] columnNames = {columnName};
        Object[][] data = new Object[1][1];
        data[0][0] = value;
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return defaultTableModel;
    }

    private void centerValuesInTable(JTable jTable) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }

}
