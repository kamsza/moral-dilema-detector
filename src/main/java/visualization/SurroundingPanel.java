package visualization;

import generator.Model;
import project.Surrounding;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class SurroundingPanel extends JPanel {
    int width, height;
    Model model;
    Model.Side side;

    SurroundingPanel(int width, int height, Model model, Model.Side side) {
        this.width = width;
        this.height = height;
        this.side = side;
        this.model = model;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Surrounding surrounding = model.getSurrounding().get(side);

        BufferedImage surrImg = ImageHandler.getImage(surrounding);
        int imHeight = side == Model.Side.RIGHT ? 20 : height - 20 - surrImg.getHeight();
        g.drawImage(surrImg, width/2 - surrImg.getWidth()/2, imHeight, this);
    }
}