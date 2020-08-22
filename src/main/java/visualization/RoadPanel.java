package visualization;

import generator.Model;
import project.Entity;
import project.Lane;
import project.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

class RoadPanel extends JPanel {
    int width, height, laneHeight;
    int mainVehicleLane;
    Model model;

    RoadPanel(int width, int height, int laneHeight, Model model) {
        this.width = width;
        this.height = height;
        this.laneHeight = laneHeight;
        this.model = model;

        this.mainVehicleLane = model.getLanes().get(Model.Side.LEFT).size();

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawRoad(g);
        drawMainLane(g);
    }

    private void drawRoad(Graphics g) {
        int horizontalStripeHeight = 4;
        int horizontalStripeWidth = 25;
        int horizontalStripesGap = 100;

        g.setColor(Color.WHITE);

        for (int y = laneHeight; y < height; y += laneHeight) {
            for (int x = 30; x < width; x += horizontalStripesGap) {
                g.fillRect(x, y - horizontalStripeHeight / 2, horizontalStripeWidth, horizontalStripeHeight);
            }
        }
    }

    private void drawMainLane(Graphics g) {
        // draw main car
        BufferedImage vehImg = ImageHandler.getImage("vehicle_main");
        int x_center = width / 2 - vehImg.getWidth() / 2;
        int y_center = mainVehicleLane * laneHeight + (laneHeight - vehImg.getHeight()) / 2;
        g.drawImage(vehImg, x_center, y_center, this);

        Lane mainLane = model.getLanes().get(Model.Side.CENTER).get(0);
        // draw cars
        ArrayList<Vehicle> vehicles = model.getVehicles().get(mainLane);
        for (Vehicle vehicle : vehicles) {
            BufferedImage img = ImageHandler.getImage(vehicle);
            float distance = vehicle.getDistance().iterator().next();
            if(distance == 0) continue;
            int x = (int) (width / 2 + distance * 0.32) - img.getWidth() / 2;
            int y = mainVehicleLane * laneHeight + (laneHeight - img.getHeight()) / 2;
            g.drawImage(img, x, y, this);
        }
    }
}