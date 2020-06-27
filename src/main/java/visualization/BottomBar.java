package visualization;

import javax.swing.*;
import java.awt.*;

class BottomBar extends JPanel {
    int width, height;

    BottomBar(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        JLabel label = new JLabel("time: daytime          weather: sunny");
        label.setForeground(Color.WHITE);
        label.setBounds(20, 0, 960,100);
        label.setFont(new Font(label.getName(), Font.PLAIN, 30));
        this.add(label);
    }
}
