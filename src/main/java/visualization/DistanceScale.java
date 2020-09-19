package visualization;

import javax.swing.*;
import java.awt.*;

public class DistanceScale extends JPanel {
    private static final int yGap = 3;
    private static final int barSize = 3;
    private static final int textSize = 15;

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
        g.setColor(Color.BLACK);

        int iterationNum = (length / 5);
        int scaledStep = (width / 2) / iterationNum;
        int startX = width / 2;
        int gapX = 0;
        int barHeight = height - 2*yGap - textSize;

        // draw scale
        for (int i = 0; i < iterationNum; i++) {
            g.fillRect(startX + gapX, yGap, barSize, barHeight);
            g.fillRect(startX - gapX, yGap, barSize, barHeight);

            if(i % 5 == 0) {
                JLabel labelR = new JLabel(i * 5 + " m");
                labelR.setFont(new Font(labelR.getName(), Font.PLAIN, textSize));
                labelR.setBounds(startX + gapX, barHeight + yGap, 50, textSize + yGap);
                this.add(labelR);

                JLabel labelL = new JLabel(i * 5 + " m");
                labelL.setFont(new Font(labelL.getName(), Font.PLAIN, textSize));
                labelL.setBounds(startX - gapX, barHeight + yGap, 50, textSize + yGap);
                this.add(labelL);
            }

            gapX += scaledStep;
        }

        // draw line
        g.setColor(Color.BLACK);
        g.fillRect(0, yGap, width, barSize);
    }
}