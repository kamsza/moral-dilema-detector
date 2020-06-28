package visualization;

import generator.Model;

import javax.swing.*;
import java.awt.*;

class BottomBar extends JPanel {
    int width, height;
    Model model;

    BottomBar(int width, int height, Model model) {
        this.width = width;
        this.height = height;
        this.model = model;
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        String time = model.getTime().toString().replace("()", "");
        String weather = model.getWeather().toString().replace("()", "");
        String labelStr = "time: " + time + "          weather: " + weather;

        JLabel label = new JLabel(labelStr);
        label.setForeground(Color.WHITE);
        label.setBounds(20, 0, 960,100);
        label.setFont(new Font(label.getName(), Font.PLAIN, 30));
        this.add(label);
    }
}
