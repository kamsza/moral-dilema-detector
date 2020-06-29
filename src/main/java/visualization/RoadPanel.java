package visualization;

import project.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class RoadPanel extends JPanel {
    int width, height;
    Vehicle vehicle;

    RoadPanel(int width, int height, Vehicle vehicle) {
        this.width = width;
        this.height = height;
        this.vehicle = vehicle;
        System.out.println(vehicle.getHas_in_the_front().iterator().next().getClass().toString());
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawRoad(g);

        drawEntities(g);
    }

    private void drawRoad(Graphics g) {
        g.setColor(Color.WHITE);
        int xStart = 30;
        while (xStart < width) {
            g.fillRect(xStart, (height / 2) - 5, 50, 10);
            xStart += 100;
        }
    }

    private void drawEntities(Graphics g) {
        BufferedImage vehImg = ImageHandler.getImage(vehicle);
        int x = (int)(width/2 - vehImg.getWidth()/2);
        int y = (int)(0.75*height - vehImg.getHeight()/2);
        g.drawImage(vehImg, x, y, this);

        BufferedImage animal = ImageHandler.getImage("Animal");
        int x2 = (int)(width/2 - vehImg.getWidth()/2 + 200);
        int y2 = (int)(height - animal.getHeight());
        g.drawImage(animal, x2, y2, this);
    }


}
