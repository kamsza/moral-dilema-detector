package visualization;

import javax.swing.*;
import java.awt.*;

public class DistanceScale extends JPanel {
    private int width;
    private int height;
    private int length;
    private int step;

    DistanceScale(int width, int height, int length, int step) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.step = step;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int iterationNum = (length / step) + 1;
        int scaledStep = (width / 2) / iterationNum;
        int startX = width / 2;

        for (int i = 0; i < iterationNum; i++) {
            g.fillRect(startX, 15, 3, 15);
            JLabel label = new JLabel(i * step + " m");
            label.setForeground(Color.BLACK);
            label.setBounds(startX, 30, 50, 20);
            label.setFont(new Font(label.getName(), Font.PLAIN, 15));
            this.add(label);
            startX += scaledStep;
        }

        startX = width / 2;
        for (int i = 0; i <= iterationNum; i++) {
            g.fillRect(startX, 15, 3, 15);
            JLabel label = new JLabel(i * step + " m");
            label.setForeground(Color.BLACK);
            label.setBounds(startX, 30, 50, 20);
            label.setFont(new Font(label.getName(), Font.PLAIN, 15));
            this.add(label);
            startX -= scaledStep;
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, 15, width, 3);


    }
}
