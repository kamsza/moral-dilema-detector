package visualization;

import generator.SizeManager;

import javax.swing.*;
import java.awt.*;

public class DistanceScale extends JPanel {
    private static final int yGap = 3;
    private static final int barSize = 3;
    private static final int textSize = 15;

    private int width;
    private int height;
    private int length;

    DistanceScale(int width, int height) {
        this.width = width;
        this.height = height;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int scaledStep = SizeManager.METERS_TO_PX;
        int startX = width / 2;
        int iterationNum = startX / scaledStep;
        int gapX = 0;
        int barHeight = height - 2*yGap - textSize;

        // draw scale
        for (int i = 0; i < iterationNum; i++) {
            g.fillRect(startX + gapX, yGap, barSize, barHeight);
            g.fillRect(startX - gapX, yGap, barSize, barHeight);

            if(i % 5 == 0) {
                JLabel labelR = new JLabel(i + " m");
                labelR.setFont(new Font(labelR.getName(), Font.PLAIN, textSize));
                labelR.setBounds(startX + gapX, barHeight + yGap, 50, textSize + yGap);
                this.add(labelR);

                JLabel labelL = new JLabel(i + " m");
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