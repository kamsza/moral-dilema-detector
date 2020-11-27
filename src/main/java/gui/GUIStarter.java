package gui;

import javax.swing.*;

public class GUIStarter {
    public static void main(String[] args) {

//        double a = 5.0 + 1.0;
//        System.out.println(a);
//        System.out.println( a == 6.0);
        DashboardWindow dashboardWindow = new DashboardWindow();
        dashboardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardWindow.setVisible(true);
    }
}
