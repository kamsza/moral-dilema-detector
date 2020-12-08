package commonadapter.adapters.nds.gui;

import commonadapter.adapters.nds.NdsUtils;
import commonadapter.adapters.nds.RoadBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NdsClientDashboard extends JFrame implements ActionListener {
    private String roadTileFilePath;
    private String laneTileFilePath;

    private final int marginSize = 20;
    private final int blockHeight = 50;
    private final int blockWidth = 400;
    private final int baseWidth = 460;
    private final int baseHeight = 6 * marginSize + 3 * blockHeight;
    private final int labelHeight = 25;

    private JButton selectRoutingTileButton;
    private JButton selectLaneTileButton;
    private JButton generateButton;
    private JTextArea createdRoadTextField;
    private JTextArea createdLaneTextField;
    private JLabel label;

    public NdsClientDashboard() {
        setSize(baseWidth, baseHeight);
        setResizable(false);
        setTitle("Common Scenario Adapter - NDS Client");
        setLayout(null);

        prepareBasicDashboard();
    }

    private JButton createButton(String description, Position pos) {
        JButton button = new JButton(description);
        button.setBounds(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
        button.addActionListener(this);
        add(button);
        return button;
    }

    private JTextArea createArea(Position pos) {
        JTextArea field = new JTextArea();
        field.setBounds(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
        add(field);
        return field;
    }

    private JLabel createLabel(String description, Position pos, Color color) {
        JLabel label = new JLabel();
        label.setBounds(pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());
        label.setText(description);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
        add(label);
        return label;
    }

    private void prepareBasicDashboard() {
        selectRoutingTileButton = createButton("Select file with NDS RoutingTile",
                new Position(marginSize, marginSize, blockWidth, blockHeight));
        selectLaneTileButton = createButton("Select file with NDS LaneTile",
                new Position(marginSize, 2 * marginSize + blockHeight, blockWidth, blockHeight));
        generateButton = createButton("Generate",
                new Position(marginSize, 3 * marginSize + 2 * blockHeight, blockWidth, blockHeight));
        generateButton.setEnabled(false);
    }

    private void prepareErrorDashboard() {
        setSize(baseWidth, baseHeight + labelHeight + marginSize * 2);

        label = createLabel("RoutingTile and LaneTile must have the same ID",
                new Position(marginSize, 4 * marginSize + 3 * blockHeight, blockWidth, blockHeight), Color.RED);
    }

    private void prepareNewDashboard(int numberOfRoadIds, int numberOfLaneIds) {
        int singleLineSize = 20;
        setSize(baseWidth, baseHeight + labelHeight + singleLineSize * (numberOfRoadIds + numberOfLaneIds) + marginSize * 3);

        label = createLabel("GUIDs of generated roads and lanes",
                new Position(marginSize, 4 * marginSize + 3 * blockHeight, blockWidth, blockHeight), Color.BLACK);
        createdRoadTextField = createArea(new Position(marginSize, 5 * marginSize + 3 * blockHeight + labelHeight,
                blockWidth, singleLineSize * numberOfRoadIds));
        createdLaneTextField = createArea(new Position(marginSize, 6 * marginSize + 3 * blockHeight + labelHeight + singleLineSize * numberOfRoadIds,
                blockWidth, singleLineSize * numberOfLaneIds));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == selectRoutingTileButton) selectRoutingTileAction();
        if (eventSource == selectLaneTileButton) selectLaneTileAction();
        if (eventSource == generateButton) generateAction();
    }

    private String selectNdsFileAction(String description) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, "json");
        chooser.setFileFilter(filter);

        int path = chooser.showOpenDialog(this);
        if (path == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private void updateGenerateButton() {
        if (roadTileFilePath != null && laneTileFilePath != null) generateButton.setEnabled(true);
    }

    private void selectRoutingTileAction() {
        this.roadTileFilePath = selectNdsFileAction("NDS routing tile");
        updateGenerateButton();
    }

    private void selectLaneTileAction() {
        this.laneTileFilePath = selectNdsFileAction("NDS lane tile");
        updateGenerateButton();
    }

    private void refresh() {
        if (label != null) {
            remove(label);
        }
        if (createdRoadTextField != null) {
            remove(createdRoadTextField);
        }
        if (createdLaneTextField != null) {
            remove(createdLaneTextField);
        }
    }

    private boolean arePathsValid() {
        String roadTileId = NdsUtils.extractTileId(roadTileFilePath);
        String laneTileId = NdsUtils.extractTileId(laneTileFilePath);
        return (roadTileId.equals(laneTileId));
    }

    private void generateAction() {
        refresh();
        if (!arePathsValid()) {
            prepareErrorDashboard();
            return;
        }

        RoadBuilder builder = new RoadBuilder(roadTileFilePath, laneTileFilePath);
        List<String> createdRoadIds = builder.buildRoads();
        List<String> createdLaneIds = builder.buildLanes();
        prepareNewDashboard(createdRoadIds.size(), createdLaneIds.size());

        String createdRoadsIdString = createdRoadIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdRoadTextField.setText(createdRoadsIdString);

        String createdLaneIdString = createdLaneIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdLaneTextField.setText(createdLaneIdString);
    }
}