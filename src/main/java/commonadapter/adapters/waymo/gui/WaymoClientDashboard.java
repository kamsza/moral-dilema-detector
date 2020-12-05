package commonadapter.adapters.waymo.gui;

import commonadapter.adapters.waymo.logic.WaymoScenarioBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaymoClientDashboard extends JFrame implements ActionListener {

    private String waymoJsonFilePath;

    private JButton selectWaymoDataButton;
    private JButton generateButton;
    private JTextField createdScenarioIdTextField;
    private JTextField roadIdTextField;



    public WaymoClientDashboard() {

        setSize(460, 500);
        setResizable(false);
        setTitle("Common Scenario Adapter - Waymo Client");
        setLayout(null);

        prepareDashboard();
    }

    private void prepareDashboard() {

        selectWaymoDataButton = new JButton("Select file with lidar labels");
        selectWaymoDataButton.setBounds(20, 20, 400, 50);
        selectWaymoDataButton.addActionListener(this);
        add(selectWaymoDataButton);

        roadIdTextField = new JFormattedTextField();
        roadIdTextField.setBounds(20, 90, 400, 50);
        roadIdTextField.setEditable(true);
        add(roadIdTextField);

        generateButton = new JButton("Generate");
        generateButton.setBounds(20, 160, 400, 50);
        generateButton.addActionListener(this);
        generateButton.setVisible(false);
        add(generateButton);

        createdScenarioIdTextField = new JFormattedTextField();
        createdScenarioIdTextField.setBounds(20, 230, 400, 50);
        createdScenarioIdTextField.setEditable(false);
        createdScenarioIdTextField.setVisible(false);
        add(createdScenarioIdTextField);

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
            generateButton.setVisible(true);
        }

    }

    private void generateAction() {

        WaymoScenarioBuilder builder = new WaymoScenarioBuilder(waymoJsonFilePath);

        String enteredText = roadIdTextField.getText();

        String createdScenarioId = "";

        if (enteredText.isEmpty()) {

            createdScenarioId = builder.createScenario(null);

        } else {

            createdScenarioId = builder.createScenario(roadIdTextField.getText());

        }

        createdScenarioIdTextField.setText(createdScenarioId);
        createdScenarioIdTextField.setVisible(true);

    }

}