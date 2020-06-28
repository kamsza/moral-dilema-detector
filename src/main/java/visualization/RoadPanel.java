package visualization;

import generator.Model;
import org.swrlapi.drools.owl.individuals.I;
import project.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class RoadPanel extends JPanel {
    int width, height;
    Vehicle vehicle;

    RoadPanel(int width, int height, Vehicle vehicle) {
        this.width = width;
        this.height = height;
        this.vehicle = vehicle;
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
    }


}
