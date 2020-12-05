package commonadapter.adapters.nds.gui;

import javax.swing.*;

public class NdsClientApp {
    public static void main(String[] args) {
        NdsClientDashboard ndsClientDashboard = new NdsClientDashboard();
        ndsClientDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ndsClientDashboard.setVisible(true);
    }
}
