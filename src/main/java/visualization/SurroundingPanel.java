package visualization;

import project.Surrounding;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


enum SurroundingPos {
    TOP,
    BOTTOM
}

class SurroundingPanel extends JPanel {
    int width, height;
    SurroundingPos position;
    Surrounding surrounding;

    SurroundingPanel(int width, int height, Surrounding surrounding, SurroundingPos position) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.surrounding = surrounding;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage surrImg = ImageHandler.getImage(surrounding);
        int imHeight = position == SurroundingPos.BOTTOM ? 20 : height - 20 - surrImg.getHeight();
        g.drawImage(surrImg, width/2 - surrImg.getWidth()/2, imHeight, this);
    }
}
