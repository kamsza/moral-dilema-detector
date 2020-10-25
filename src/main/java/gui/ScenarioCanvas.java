package gui;


import javax.swing.*;
import java.awt.*;

public class ScenarioCanvas extends Canvas {
    public void paint(Graphics g) {

//        Toolkit t=Toolkit.getDefaultToolkit();
//        Image i=t.getImage(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png");
//        i.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
//
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\gui\\Blank_scenario.png");
        Image scaleImage = icon.getImage().getScaledInstance(28, 28,Image.SCALE_DEFAULT);


        g.drawImage(scaleImage, 10,10,this);

    }
    public static void main(String[] args) {
        ScenarioCanvas m=new ScenarioCanvas();
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(400,400);
        f.setVisible(true);
    }
}
