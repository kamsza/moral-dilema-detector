package gui;

import DilemmaDetector.Consequences.CustomPhilosophy;
import DilemmaDetector.Consequences.DecisionCostCalculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.logic.FullSimulation;
import gui.logic.ReturnContainer;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class DashboardWindow extends JFrame implements ActionListener, ItemListener {

    private JButton jButtonLoadFromFile;
    private JLabel jLabelSelectedFile;
    private JButton jButtonLoadScenario;
    private JComboBox jComboBoxScenarios;
    private JLabel jLabelRandomScenarioType;
    private JButton jButtonGenerateScenario;
    private JLabel jLabelImageScenario;
    private JButton jButtonAddCustomPhilosophy;
    private JComboBox jComboBoxCustomPhilosophies;
    private JButton jButtonCalculate;

    private JPanel jPanelContainer;
    private JScrollPane jScrollPaneMain;

    /// CONST
    private String NO_FILE_SELECTED = "No file selected";
    private String NO_SCENARIO_TYPE_SELECTED = "No random scenario type selected";
    private String RANDOM_SCENARIO_TYPE = "Generated scenario type: ";
    private List<String> possibleScenariosList = new ArrayList<String>(Arrays.asList("Simple scenario",
            "Scenario with animals",
            "Scenario with crosswalk"));
    private int IMAGE_WIDTH = 1000;
    private int IMAGE_HEIGHT = 400;

    //business logic variables
    private ReturnContainer returnContainer = null;


    public DashboardWindow() {
        setSize(1200, 800);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);


        jButtonLoadFromFile = new JButton("Load scenario from file");
        jButtonLoadFromFile.setBounds(10, 10, 400, 30);
        jButtonLoadFromFile.addActionListener(this);
        add(jButtonLoadFromFile);

        jLabelSelectedFile = new JLabel(NO_FILE_SELECTED, SwingConstants.CENTER);
        jLabelSelectedFile.setBounds(10, 40, 400, 30);
        add(jLabelSelectedFile);

        jButtonLoadScenario = new JButton("Load");
        jButtonLoadScenario.setBounds(415, 10, 100, 60);
        jButtonLoadScenario.addActionListener(this);
        add(jButtonLoadScenario);


        jComboBoxScenarios = new JComboBox(possibleScenariosList.toArray());
        jComboBoxScenarios.setBounds(600, 10, 400, 30);
        jComboBoxScenarios.addItemListener(this);
        add(jComboBoxScenarios);

        jLabelRandomScenarioType = new JLabel(NO_SCENARIO_TYPE_SELECTED, SwingConstants.CENTER);
        jLabelRandomScenarioType.setBounds(600, 40, 400, 30);
        add(jLabelRandomScenarioType);

        jButtonGenerateScenario = new JButton("Generate");
        jButtonGenerateScenario.setBounds(1005, 10, 100, 60);
        jButtonGenerateScenario.addActionListener(this);
        add(jButtonGenerateScenario);

        jLabelImageScenario = new JLabel(getStartingImageIcon());
        jLabelImageScenario.setBounds(40, 80, IMAGE_WIDTH, IMAGE_HEIGHT);
        add(jLabelImageScenario);

        jButtonAddCustomPhilosophy = new JButton("Add custom philosophy");
        jButtonAddCustomPhilosophy.setBounds(10, 500, 200, 30);
        jButtonAddCustomPhilosophy.addActionListener(this);
        add(jButtonAddCustomPhilosophy);


        jComboBoxCustomPhilosophies = new JComboBox(getCustomPhilosophiesNames().toArray());
        jComboBoxCustomPhilosophies.setBounds(600, 500, 400, 30);
        jComboBoxCustomPhilosophies.addItemListener(this);
        add(jComboBoxCustomPhilosophies);

        jButtonCalculate = new JButton("Calculate");
        jButtonCalculate.setBounds(400, 500, 100, 60);
        jButtonCalculate.addActionListener(this);
        add(jButtonCalculate);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();

        if (eventSource == jButtonLoadFromFile) {
            JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                jLabelSelectedFile.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            } else
                jLabelSelectedFile.setText(NO_FILE_SELECTED);
        }

        if (eventSource == jButtonLoadScenario) {
            WarningWindow warningWindow = new WarningWindow(this, "Not implemented yet");
            warningWindow.setVisible(true);
        }

        if (eventSource == jButtonGenerateScenario) {
            // różne rodzaje w zależności od comboboxa z rodzajami, na razie na sztywno

            FullSimulation fullSimulation = new FullSimulation();

            try {
                returnContainer = fullSimulation.doEveryThing();
            } catch (OWLOntologyCreationException owlOntologyCreationException) {
                owlOntologyCreationException.printStackTrace();
            } catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }

            System.out.println(">>>>" + returnContainer.getPictureName());

            jLabelImageScenario.setIcon(
                    getImageIcon(System.getProperty("user.dir")
                            + "\\src\\main\\resources\\vis_out\\"
                            + returnContainer.getPictureName()));
        }


        if (eventSource == jButtonAddCustomPhilosophy) {
            CustomPhilosophyWindow customPhilosophyWindow = new CustomPhilosophyWindow();
            customPhilosophyWindow.setVisible(true);
        }


        if (eventSource == jButtonCalculate) {
            if (returnContainer == null) {
                WarningWindow warningWindow = new WarningWindow(this, "Load or generate scenario");
                warningWindow.setVisible(true);
            } else {
                // wczytujemy wybraną filozofię
                String philosophyName = jComboBoxCustomPhilosophies.getSelectedItem().toString();
                // TODO sprawdzić warunek czy nie null !!!
                CustomPhilosophy customPhilosophy = getCustomPhilosophyByName(philosophyName);
                System.out.println(philosophyName);


//                DecisionCostCalculator costCalculator = new DecisionCostCalculator(returnContainer.getConsequenceContainer(), factory);
//
//                for(Map.Entry<Decision, Set<Actor>> entry : collidedEntities.entrySet()) {
//                    System.out.println(entry.getKey().toString()+ "  " + costCalculator.calculateCostForDecision(entry.getKey()));
//                    for (Actor a : entry.getValue()) System.out.println(a.getEntity());
//                }


            }

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == jComboBoxScenarios) {
            jLabelRandomScenarioType.setText(RANDOM_SCENARIO_TYPE + jComboBoxScenarios.getSelectedItem());
        }

    }

    // poleci do innej klasy
    private ImageIcon getStartingImageIcon() {
        return getImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png");
    }

    // poleci do innej klasy
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

    //do innej klasy
    public List<String> getCustomPhilosophiesNames() {
        File f = new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\gui\\customPhilosophies\\");

        List<String> customPhilosophiesNames = Arrays.asList(f.list());
        for (String name : customPhilosophiesNames) {
            name.replace(".json", "");
        }
        return customPhilosophiesNames;
    }

    //do innej klasy
    public CustomPhilosophy getCustomPhilosophyByName(String name) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\gui\\customPhilosophies\\" +
                name);
        CustomPhilosophy customPhilosophy = null;
        try {
            customPhilosophy = objectMapper.readValue(file, CustomPhilosophy.class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return customPhilosophy;
    }

}
