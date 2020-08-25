package visualization;

import generator.Model;
import project.Entity;
import project.Lane;
import project.Living_entity;
import project.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RoadPanel extends JPanel {
    private final int WIDTH, HEIGHT, LANE_HEIGHT;
    private final int MAIN_VEHICLE_LANE;
    private final float SCALE;
    Model model;


    RoadPanel(int height, Model model) {
        this.WIDTH = Visualization.WIDTH;
        this.HEIGHT = height;
        this.LANE_HEIGHT = Visualization.LANE_HEIGHT;
        this.MAIN_VEHICLE_LANE = model.getLanes().get(Model.Side.LEFT).size();
        this.SCALE = (float)WIDTH / (2*Visualization.LANE_DIST);

        this.model = model;

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(WIDTH, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawRoad(g);
        drawMainLane(g);
        drawLeftLanes(g);
        drawRightLanes(g);
    }

    private void drawRoad(Graphics g) {
        int horizontalStripeHeight = 4;
        int horizontalStripeWidth = 25;
        int horizontalStripesGap = 100;

        g.setColor(Color.WHITE);

        for (int y = LANE_HEIGHT; y < HEIGHT; y += LANE_HEIGHT) {
            for (int x = 30; x < WIDTH; x += horizontalStripesGap) {
                g.fillRect(x, y - horizontalStripeHeight / 2, horizontalStripeWidth, horizontalStripeHeight);
            }
        }
    }

    private void drawMainLane(Graphics g) {
        Lane mainLane = model.getLanes().get(Model.Side.CENTER).get(0);
        int Y = MAIN_VEHICLE_LANE * LANE_HEIGHT;

        ArrayList<Vehicle> vehicles = model.getVehicles().get(mainLane);
        drawVehicles(g, vehicles, Y);

        ArrayList<Living_entity> entities = model.getEntities().get(mainLane);
        drawEntities(g, entities, Y);

        drawMainCar(g, Y);
    }

    private void drawMainCar(Graphics g,  int Y) {
        BufferedImage vehImg = ImageHandler.getImage("vehicle_main");
        int x = (WIDTH - vehImg.getWidth()) / 2;
        int y = MAIN_VEHICLE_LANE * LANE_HEIGHT + (LANE_HEIGHT - vehImg.getHeight()) / 2;
        g.drawImage(vehImg, x, y, this);
    }

    private void drawLeftLanes(Graphics g) {
        Map<Integer, Lane> lanes = model.getLanes().get(Model.Side.LEFT);
        int Y = (MAIN_VEHICLE_LANE - 1) * LANE_HEIGHT;

        for (Lane lane : lanes.values()) {
            ArrayList<Vehicle> vehicles = model.getVehicles().get(lane);
            drawVehicles(g, vehicles, Y);

            ArrayList<Living_entity> entities = model.getEntities().get(lane);
            drawEntities(g, entities, Y);

            Y -= LANE_HEIGHT;
        }
    }

    private void drawRightLanes(Graphics g) {
        Map<Integer, Lane> lanes = model.getLanes().get(Model.Side.RIGHT);
        int Y = (MAIN_VEHICLE_LANE + 1) * LANE_HEIGHT;

        for (Lane lane : lanes.values()) {
            ArrayList<Vehicle> vehicles = model.getVehicles().get(lane);
            drawVehicles(g, vehicles, Y);

            ArrayList<Living_entity> entities = model.getEntities().get(lane);
            drawEntities(g, entities, Y);

            Y += LANE_HEIGHT;
        }
    }

    private void drawVehicles(Graphics g, List<Vehicle> vehicles, int Y) {
        for (Vehicle vehicle : vehicles) {
            BufferedImage img;
            if (vehicle.getSpeedY().iterator().next() > 0)
                img = ImageHandler.getImage("vehicle");
            else
                img = ImageHandler.getImage("vehicleL");
            float distance = vehicle.getDistance().iterator().next();
            int x = (int) ((WIDTH - img.getWidth()) / 2 + distance * SCALE);
            int y = Y + (LANE_HEIGHT - img.getHeight()) / 2;
            g.drawImage(img, x, y, this);
        }
    }

    private void drawEntities(Graphics g, List<Living_entity> entities, int Y) {
        for (Entity entity : entities) {
            BufferedImage img = ImageHandler.getImage(entity);
            float distance = entity.getDistance().iterator().next();
            int x = (int) ((WIDTH - img.getWidth()) / 2 + distance * SCALE);
            int y = Y + (LANE_HEIGHT - img.getHeight()) / 2;
            g.drawImage(img, x, y, this);
        }
    }
}