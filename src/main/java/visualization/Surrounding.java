package visualization;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Surrounding extends JPanel {
    int width, height;
    BufferedImage tree;

    Surrounding(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
        String currentDirectory = System.getProperty("user.dir");
        try {
            tree = ImageIO.read(new File(currentDirectory + "/src/main/resources/img/tree3.png"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int treeX = 350;
        while (treeX < width) {
            g.drawImage(tree, treeX, 80, this);
            treeX += 170;
        }

    }
}
