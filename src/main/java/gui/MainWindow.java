package gui;

import generator.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MainWindow extends JFrame implements ActionListener {


    private JButton jButtonStart;
    private TableWithParameters tableWithParameters;
    private JPanel pictureContainer;


    private Model scenarioModel;

    public MainWindow() {
        setSize(1000, 600);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);

        tableWithParameters = new TableWithParameters();
        tableWithParameters.setBounds(10, 50, 200, 130);
        add(tableWithParameters);

        jButtonStart = new JButton("Start simulation");
        jButtonStart.setBounds(10, 180, 200, 30);
        jButtonStart.addActionListener(this);
        add(jButtonStart);

        BufferedImage myPicture = getImage("Forest");
        // na razie stałe zdjęcie, bo klasa ImageHandler jest package private
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(250, 50, 400, 160);
        picLabel.setVisible(true);
        add(picLabel);



    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonStart) {
            System.out.println("SIMULATION SHOULD START");
            for (Map.Entry<String, Integer> entry : tableWithParameters.getTableValues().entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
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

            if (imageFile.exists())
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
