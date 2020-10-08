package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderWithLabel extends JPanel implements ChangeListener {

    private JSlider jSlider;
    private JLabel jLabel;
    private String category;


    public SliderWithLabel(String category) {
        super();
        this.category = category;

        jLabel = new JLabel();
        this.add(jLabel);

        jSlider = new JSlider(0, 100, 100);

        // paint the ticks and tarcks
        jSlider.setPaintTrack(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        // set spacing
        jSlider.setMajorTickSpacing(50);
        jSlider.setMinorTickSpacing(10);

        // setChangeListener
        jSlider.addChangeListener(this);

        // set orientation of slider
        jSlider.setOrientation(SwingConstants.HORIZONTAL);


        // add slider to panel
        this.add(jSlider);


        jLabel.setText(category + ": " + jSlider.getValue() + "%");


        setVisible(true);
    }

    public String getCategory() {
        return category;
    }

    public int getSliderValue() {
        return jSlider.getValue();
    }

    // if JSlider value is changed
    @Override

    public void stateChanged(ChangeEvent e) {
        jLabel.setText(category + ": " + jSlider.getValue() + "%");
    }

}
