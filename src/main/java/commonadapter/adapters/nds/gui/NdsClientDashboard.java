package commonadapter.adapters.nds.gui;

import commonadapter.adapters.nds.NdsUtils;
import commonadapter.adapters.nds.RoadBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class NdsClientDashboard extends JFrame implements ActionListener {
    private String roadTileFilePath;
    private String laneTileFilePath;

    private final int marginSize = 20;
    private final int blockHeight = 50;
    private final int blockWidth = 400;
    private final int baseWidth = 460;
    private final int baseHeight = 7 * marginSize + 4 * blockHeight;
    private final int labelHeight = 25;

    private JButton selectRoutingTileButton;
    private JButton selectLaneTileButton;
    private JButton generateRoadsButton;
    private JButton generateFullButton;
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
        generateRoadsButton = createButton("Generate roads",
                new Position(marginSize, 3 * marginSize + 2 * blockHeight, blockWidth, blockHeight));
        generateFullButton = createButton("Generate roads and lanes",
                new Position(marginSize, 4 * marginSize + 3 * blockHeight, blockWidth, blockHeight));
        generateRoadsButton.setEnabled(false);
        generateFullButton.setEnabled(false);
    }

    private void prepareErrorDashboard(String errorMessage) {
        setSize(baseWidth, baseHeight + labelHeight + marginSize * 2);

        label = createLabel(errorMessage,
                new Position(marginSize, 5 * marginSize + 4 * blockHeight, blockWidth, blockHeight), Color.RED);
    }

    private void prepareRoadsDashboard(int numberOfRoadIds) {
        int singleLineSize = 20;
        setSize(baseWidth, baseHeight + labelHeight + singleLineSize * numberOfRoadIds + marginSize * 3);

        label = createLabel("GUIDs of generated roads",
                new Position(marginSize, 5 * marginSize + 4 * blockHeight, blockWidth, blockHeight), Color.BLACK);
        createdRoadTextField = createArea(new Position(marginSize, 6 * marginSize + 4 * blockHeight + labelHeight,
                blockWidth, singleLineSize * numberOfRoadIds));
    }

    private void prepareFullDashboard(int numberOfRoadIds, int numberOfLaneIds) {
        int singleLineSize = 20;
        setSize(baseWidth, baseHeight + labelHeight + singleLineSize * (numberOfRoadIds + numberOfLaneIds) + marginSize * 3);

        label = createLabel("GUIDs of generated roads and lanes",
                new Position(marginSize, 5 * marginSize + 4 * blockHeight, blockWidth, blockHeight), Color.BLACK);
        createdRoadTextField = createArea(new Position(marginSize, 6 * marginSize + 4 * blockHeight + labelHeight,
                blockWidth, singleLineSize * numberOfRoadIds));
        createdLaneTextField = createArea(new Position(marginSize, 7 * marginSize + 4 * blockHeight + labelHeight + singleLineSize * numberOfRoadIds,
                blockWidth, singleLineSize * numberOfLaneIds));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == selectRoutingTileButton) selectRoutingTileAction();
        if (eventSource == selectLaneTileButton) selectLaneTileAction();
        if (eventSource == generateRoadsButton) generateRoadsAction();
        if (eventSource == generateFullButton) generateFullAction();
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

    private void selectRoutingTileAction() {
        this.roadTileFilePath = selectNdsFileAction("NDS routing tile");
        updateGenerateRoadsButton();
        updateGenerateFullButton();
    }

    private void selectLaneTileAction() {
        this.laneTileFilePath = selectNdsFileAction("NDS lane tile");
        updateGenerateFullButton();
    }

    private void updateGenerateRoadsButton() {
        if (roadTileFilePath != null) generateRoadsButton.setEnabled(true);
    }

    private void updateGenerateFullButton() {
        if (roadTileFilePath != null && laneTileFilePath != null) generateFullButton.setEnabled(true);
    }

    private void generateRoadsAction() {
        refresh();
        if (!validateRoadFile()) {
            return;
        }

        RoadBuilder builder = new RoadBuilder(roadTileFilePath);
        List<String> createdRoadIds = builder.buildRoads();
        prepareRoadsDashboard(createdRoadIds.size());

        String createdRoadsIdString = createdRoadIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdRoadTextField.setText(createdRoadsIdString);
    }

    private void generateFullAction() {
        refresh();
        if (!validateFullFiles()) {
            return;
        }
        if (!validateTileIds()) {
            return;
        }

        RoadBuilder builder = new RoadBuilder(roadTileFilePath, laneTileFilePath);
        List<String> createdRoadIds = builder.buildRoads();
        List<String> createdLaneIds = builder.buildLanes();
        prepareFullDashboard(createdRoadIds.size(), createdLaneIds.size());

        String createdRoadsIdString = createdRoadIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdRoadTextField.setText(createdRoadsIdString);

        String createdLaneIdString = createdLaneIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        createdLaneTextField.setText(createdLaneIdString);
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
        repaint();
        revalidate();
    }

    private boolean validateRoadFile() {
        String maybeRoutingTile = NdsUtils.extractRoadTileName(roadTileFilePath);

        if (maybeRoutingTile.equals("routingTile")) {
            return true;
        } else {
            prepareErrorDashboard("Wrong file chosen for routing tile");
            return false;
        }
    }

    private boolean validateFullFiles() {
        String maybeRoutingTile = NdsUtils.extractRoadTileName(roadTileFilePath);
        String maybeLaneTile = NdsUtils.extractLaneTileName(laneTileFilePath);

        if (maybeRoutingTile.equals("routingTile") && maybeLaneTile.equals("laneTile")) {
            return true;
        } else if (!maybeRoutingTile.equals("routingTile") && !maybeLaneTile.equals("laneTile")) {
            prepareErrorDashboard("Wrong files chosen for both routing and lane tile");
            return false;
        } else if (!maybeRoutingTile.equals("routingTile") && maybeLaneTile.equals("laneTile")) {
            prepareErrorDashboard("Wrong file chosen for routing tile");
            return false;
        } else if (maybeRoutingTile.equals("routingTile") && !maybeLaneTile.equals("laneTile")) {
            prepareErrorDashboard("Wrong file chosen for lane tile");
            return false;
        }
        return false;
    }

    private boolean validateTileIds() {
        String roadTileId = NdsUtils.extractTileId(roadTileFilePath);
        String laneTileId = NdsUtils.extractTileId(laneTileFilePath);
        if (!roadTileId.equals(laneTileId)) {
            prepareErrorDashboard("RoutingTile and LaneTile must have the same ID");
            return false;
        }
        return true;
    }
}