package gui;

import javax.swing.*;

public class GUIStarter {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
