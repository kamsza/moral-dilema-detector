package commonadapter.adapters.waymo.gui;

import javax.swing.*;

public class WaymoClientApp {
    public static void main(String[] args) {
        WaymoClientDashboard waymoClientDashboard = new WaymoClientDashboard();
        waymoClientDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        waymoClientDashboard.setVisible(true);
    }
}
