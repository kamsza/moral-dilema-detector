package visualization;

import generator.Model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Visualization {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final int LANE_HEIGHT = 54;
    public static final int LANE_DIST = 2500;
    public static final int BOTTOM_BAR_HEIGHT = 150;
    public static final int DISTANCE_BAR_HEIGHT = 30;

    private int lanesNum;

    JFrame frame;
    JPanel background;

    private Visualization(Model model) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH + 16, HEIGHT + 39);
        frame.setResizable(false);

        background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        lanesNum = model.getRoadType().getLanes_num().iterator().next();
        JPanel road = new RoadPanel(lanesNum * LANE_HEIGHT, model);

        int surroundingHeight = getSurroundingPanelHeight();
        JPanel surroundingUp = new SurroundingPanel(WIDTH, surroundingHeight, model, Model.Side.LEFT);

        JPanel surroundingDown = new SurroundingPanel(WIDTH, surroundingHeight, model, Model.Side.RIGHT);

        JPanel bottomBar = new BottomBar(WIDTH, BOTTOM_BAR_HEIGHT, model);
        JPanel distanceMeter = new DistanceScale(WIDTH, DISTANCE_BAR_HEIGHT);

        background.add(surroundingUp);
        background.add(road);
        background.add(distanceMeter);
        background.add(surroundingDown);
        background.add(bottomBar);

        frame.add(background);
        frame.setVisible(false);
    }

    private int getSurroundingPanelHeight() {
        int height = HEIGHT;
        height -= BOTTOM_BAR_HEIGHT;
        height -= DISTANCE_BAR_HEIGHT;
        height -= lanesNum * LANE_HEIGHT;
        height /= 2;
        return height;
    }

    /**
     * Static function that allows to generate .png image from Model object
     * file will be placed in resources/vis_out directory with name:
     * vis__dd_MM_yyyy__HH_mm_ss (where dd_MM_yyyy__HH_mm_ss is current date and time)
     */
    public static void getImage(Model model) {
        Visualization vs = new Visualization(model);

        vs.frame.pack();
        try {
            ImageHandler.saveImage(vs.background);
        } catch (IOException ex) {
            System.out.println("Unable to create visualization for: " + model.toString());
        } finally {
            vs.frame.dispose();
        }
    }
}