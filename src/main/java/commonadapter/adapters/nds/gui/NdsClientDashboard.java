package commonadapter.adapters.nds.gui;

import commonadapter.adapters.nds.RoadBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class NdsClientDashboard extends JFrame implements ActionListener {
    private String ndsRoadJsonFilePath;

    private final int baseHeight = 200;
    private final int baseWidth = 460;
    private final int marginSize = 20;
    private final int blockHeight = 50;
    private final int blockWidth = 400;

    private JButton selectNdsDataButton;
    private JButton generateButton;
    private JTextArea createdRoadTextField;

    public NdsClientDashboard() {
        setSize(baseWidth, baseHeight);
        setResizable(false);
        setTitle("Common Scenario Adapter - NDS Client");
        setLayout(null);

        prepareDashboard();
    }

    private void prepareDashboard() {
        selectNdsDataButton = new JButton("Select file with ontology");

        selectNdsDataButton.setBounds(marginSize, marginSize, blockWidth, blockHeight);
        selectNdsDataButton.addActionListener(this);
        add(selectNdsDataButton);

        generateButton = new JButton("Generate");
        generateButton.setBounds(marginSize, 2 * marginSize + blockHeight, blockWidth, blockHeight);
        generateButton.addActionListener(this);
        add(generateButton);
    }

    private void prepareNewDashboard(int numberOfIds) {
        int singleLineSize = 20;
        setSize(baseWidth, baseHeight + singleLineSize * numberOfIds + marginSize);

        createdRoadTextField = new JTextArea();
        createdRoadTextField.setBounds(marginSize, 3 * marginSize + 2 * blockHeight, blockWidth, singleLineSize * numberOfIds);
        add(createdRoadTextField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == selectNdsDataButton) selectNdsDataAction();
        if (eventSource == generateButton) generateAction();
    }


    private void selectNdsDataAction() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "NDS routing tile", "json");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ndsRoadJsonFilePath = chooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void generateAction() {
        RoadBuilder builder = new RoadBuilder(ndsRoadJsonFilePath);
        List<String> createdRoadsId = builder.buildRoads();
        prepareNewDashboard(createdRoadsId.size());
        String createdRoadsIdString = createdRoadsId.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdRoadTextField.setText(createdRoadsIdString);
    }

}