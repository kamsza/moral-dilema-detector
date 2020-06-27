package visualization;

import org.swrlapi.drools.owl.individuals.I;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Road extends JPanel {
    int width, height;

    Road(int width, int height) {
        this.width = width;
        this.height = height;
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
        String currentDirectory = System.getProperty("user.dir");
        BufferedImage car;
        try {
            car = ImageIO.read(new File(currentDirectory + "/src/main/resources/img/suvR.png"));
            g.drawImage(car, width/2 - car.getWidth()/2, height/2, this);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }


}
