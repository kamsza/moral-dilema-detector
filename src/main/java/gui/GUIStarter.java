package gui;

import javax.swing.*;

public class GUIStarter {
    public static void main(String[] args) {
        DashboardWindow dashboardWindow = new DashboardWindow();
        dashboardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardWindow.setVisible(true);
    }
}
