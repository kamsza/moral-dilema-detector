package visualization;

import project.Surrounding;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class SurroundingPanel extends JPanel {
    int width, height;
    Surrounding surrounding;

    SurroundingPanel(int width, int height, Surrounding surrounding) {
        this.width = width;
        this.height = height;
        this.surrounding = surrounding;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage surrImg = ImageHandler.getImage(surrounding);
        g.drawImage(surrImg, width/2 - surrImg.getWidth()/2, height/2 - surrImg.getHeight()/2, this);
    }
}
