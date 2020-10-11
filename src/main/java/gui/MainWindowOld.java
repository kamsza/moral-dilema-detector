package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWindowOld extends JFrame implements ActionListener {

    private List<SliderWithLabel> SliderWithLabelList = new ArrayList<>();
    private JPanel containerForSliders;
    private JPanel pictureContainer;
    private JPanel mainContainer;
    private JButton jButtonStart;

    public MainWindowOld() {
        setSize(1000, 600);
        setTitle("Moral dilemma detector");
        setDefaultLookAndFeelDecorated(true);

        containerForSliders = new JPanel();
        containerForSliders.setLayout(new BoxLayout(containerForSliders, BoxLayout.X_AXIS));

        List<String> categories = Arrays.asList("Humans", "Animals", "Law", "Money");


        for (String category : categories) {
            SliderWithLabelList.add(new SliderWithLabel(category));
        }

        for (SliderWithLabel sliderWithLabel : SliderWithLabelList) {
            containerForSliders.add(sliderWithLabel);
        }
        containerForSliders.setBounds(0,0,1000, 300);
        add(containerForSliders);


        pictureContainer = new JPanel();
        BufferedImage myPicture = getImage("Forest");
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(0,400, 1000, 300);
        picLabel.setVisible(true);
        pictureContainer.add(picLabel);

        mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        mainContainer.add(containerForSliders);

        jButtonStart = new JButton("Start simulation");
        jButtonStart.addActionListener(this);
        mainContainer.add(jButtonStart);

        mainContainer.add(pictureContainer);

        add(mainContainer);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if(eventSource == jButtonStart)
        {
            System.out.println("SIMULATION SHOULD START");
            for(SliderWithLabel slider : SliderWithLabelList)
            {
                System.out.println(slider.getCategory() + " -> " + slider.getSliderValue() + "%");
            }
        }
    }

    /* method from ImageHandler class <- this class isn't public */
    public static BufferedImage getImage(String name) {
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/src/main/resources/img/" + name + ".png";
        BufferedImage image = null;

        try {
            File imageFile = new File(filePath);

            if(imageFile.exists())
                image = ImageIO.read(imageFile);
            else {
                String defaultFilePath = currentDirectory + "/src/main/resources/img/no_image.png";
                File defaultImageFile = new File(defaultFilePath);
                image = ImageIO.read(defaultImageFile);
                System.out.println("VISUALIZER: no image for: " + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
