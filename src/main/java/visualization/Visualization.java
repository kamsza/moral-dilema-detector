package visualization;

import generator.Model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Visualization {
    private int width, height;

    JFrame frame;
    JPanel background;

    private Visualization(Model model) {
        int panel_height = 190;
        int bar_height = 80;
        int scaleHeight = 50;

        width = 1000;
        height = 3 * panel_height + bar_height + scaleHeight;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width + 16, height + 39);
        frame.setResizable(false);

        background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        background.setPreferredSize(new Dimension(width, height));

        JPanel surroundingUp = new SurroundingPanel(width, panel_height, model.getSurrounding().get(Model.Side.LEFT), SurroundingPos.TOP);
        JPanel road = new RoadPanel(width, panel_height, model.getVehicle());
        JPanel surroundingDown = new SurroundingPanel(width, panel_height, model.getSurrounding().get(Model.Side.RIGHT), SurroundingPos.BOTTOM);
        JPanel bottomBar = new BottomBar(width, bar_height, model);
        JPanel distanceMeter = new DistanceScale(width, scaleHeight, 15, 5);

        background.add(surroundingUp);
        background.add(road);
        background.add(distanceMeter);
        background.add(surroundingDown);
        background.add(bottomBar);

        frame.add(background);
        frame.setVisible(false);
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
