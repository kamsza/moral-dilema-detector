package gui;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ShowPicture {
    public static void main(String args[]) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(150, 200,
                Image.SCALE_SMOOTH);
//        imageIconScenario = new ImageIcon(dimg);




        JFrame frame = new JFrame();
        //ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png");
        ImageIcon icon = new ImageIcon(dimg);
        JLabel label = new JLabel(icon);
        frame.add(label);
        frame.setDefaultCloseOperation
                (JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
