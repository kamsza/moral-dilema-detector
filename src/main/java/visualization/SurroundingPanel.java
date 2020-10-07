package visualization;

import generator.Model;
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

        if(surrounding.size() == 1) {
            BufferedImage surrImg = ImageHandler.getImage(surrounding.get(0));
            int y = side == Model.Side.RIGHT ? 20 : height - 20 - surrImg.getHeight();
            g.drawImage(surrImg, width / 2 - surrImg.getWidth() / 2, y, this);
        }
        else {
            for (Surrounding s : surrounding) {
                BufferedImage surrImg = ImageHandler.getImage(surrounding.get(0));
                int y = side == Model.Side.RIGHT ? 20 : height - 20 - surrImg.getHeight();
                int x = (int)(s.getDistance().iterator().next() - s.getLength().iterator().next() / 2);
                g.drawImage(surrImg, x, y, this);
            }
        }
    }

}