package visualization;

import generator.Model;
import generator.SizeManager;
import project.Surrounding;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

        ArrayList<Surrounding> surrounding = model.getSurrounding().get(side);

        for (Surrounding s : surrounding) {
            BufferedImage surrImg = ImageHandler.getImage(s);

            int dist = (int)(0.01 * s.getDistance().iterator().next() * SizeManager.METERS_TO_PX);
            int length = (int)(0.01 * s.getLength().iterator().next() * SizeManager.METERS_TO_PX);
            int width = surrImg.getHeight();

            int x = dist - length/2 + Visualization.WIDTH/2;
            int y = side == Model.Side.RIGHT ? 20 : height - 20 - surrImg.getHeight();

            drawImage(g, surrImg, x, y, length, width);
        }

    }

    private void drawImage(Graphics g, BufferedImage img, int x, int y, int length, int width) {
        int imgLength = Math.min(img.getWidth(), length);
        int imgHeight = Math.min(img.getHeight(), width);

        if(x != 0) x += 10;
        if(length != Visualization.WIDTH) length -= 10;

        for(int x1 = x; x1 < x + length; x1 += imgLength) {
            int x2 = Math.min(x1 + imgLength, x + length);
            int y2 = y + imgHeight;
            g.drawImage(img, x1, y, x2, y2, 0, 0, imgLength, imgHeight, this);
        }
    }
}