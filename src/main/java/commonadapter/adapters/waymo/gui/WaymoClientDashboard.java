package commonadapter.adapters.waymo.gui;

import commonadapter.adapters.waymo.logic.WaymoScenarioBuilder;
import commonadapter.server.logic.Server;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaymoClientDashboard extends JFrame implements ActionListener {

    private Server server;
    private Thread serverThread;
    private String waymoJsonFilePath;

    private JButton selectWaymoDataButton;
    private JComboBox<String> availableScenarioIdsComboBox;
    private JButton generateButton;


    public WaymoClientDashboard() {

        setSize(460, 300);
        setResizable(false);
        setTitle("Common Scenario Adapter - Waymo Client");
        setLayout(null);

        prepareDashboard();
    }

    private void prepareDashboard() {

        selectWaymoDataButton = new JButton("Select file with ontology");
        selectWaymoDataButton.setBounds(20, 20, 400, 50);
        selectWaymoDataButton.addActionListener(this);
        add(selectWaymoDataButton);

        availableScenarioIdsComboBox = new JComboBox<String>(new String[] { "345", "678", "Michal" });
        availableScenarioIdsComboBox.setBounds(20, 90, 400, 50);
        add(availableScenarioIdsComboBox);

        generateButton = new JButton("Generate");
        generateButton.setBounds(20, 160, 400, 50);
        generateButton.addActionListener(this);
        //generateButton.setVisible(true);
        add(generateButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object eventSource = e.getSource();

        if (eventSource == selectWaymoDataButton) selectWaymoDataAction();

        if (eventSource == generateButton) generateAction();

    }


    private void selectWaymoDataAction() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Projected Lidar Labels Waymo JSON", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            waymoJsonFilePath = chooser.getSelectedFile().getAbsolutePath();
        }

    }

    private void generateAction() {

        WaymoScenarioBuilder builder = new WaymoScenarioBuilder(waymoJsonFilePath);

        builder.createScenario();

    }

}