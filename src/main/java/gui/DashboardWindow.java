package gui;

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


    /// CONST
    private String NO_FILE_SELECTED = "No file selected";
    private String NO_SCENARIO_TYPE_SELECTED = "No random scenario type selected";
    private String RANDOM_SCENARIO_TYPE = "Generated scenario type: ";
    private List<String> possibleScenariosList = new ArrayList<String>(Arrays.asList("Simple scenario",
            "Scenario with animals",
            "Scenario with crosswalk"));
    private int IMAGE_WIDTH = 1000;
    private int IMAGE_HEIGHT = 400;


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


//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image dimg = img.getScaledInstance(150, 200,
//                Image.SCALE_SMOOTH);
//        ImageIcon icon = new ImageIcon(dimg);
//        jLabelImageScenario = new JLabel(icon);

        jLabelImageScenario = new JLabel(getStartingImageIcon());
        jLabelImageScenario.setBounds(40, 80, IMAGE_WIDTH, IMAGE_HEIGHT);
        add(jLabelImageScenario);


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

//        if(eventSource == jButtonLoadScenario){
//            WarningWindow warningWindow = new WarningWindow(this, "Not implemented yet");
//            warningWindow.setVisible(true);
//        }


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
}
