package commonadapter.server.gui;

import gui.DashboardWindow;

import javax.swing.*;

public class ServerApp {

    public static void main(String[] args) {
        ServerDashboard serverDashboard = new ServerDashboard();
        serverDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverDashboard.setVisible(true);
    }
}
