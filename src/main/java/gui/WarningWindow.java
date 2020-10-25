package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarningWindow extends JFrame implements ActionListener {

    JButton bConfirm;
    JLabel lMessage;

    public WarningWindow(JFrame owner, String message)
    {
        setSize(300, 300);
        setTitle("Warning!");
        setLayout(null);

        lMessage = new JLabel(message, SwingConstants.CENTER);
        lMessage.setBounds(30,30,250,50);
        add(lMessage);

        bConfirm = new JButton("ok");
        bConfirm.setBounds(100,100,100,100);
        bConfirm.addActionListener(this);
        add(bConfirm);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if(eventSource == bConfirm) dispose();
    }
}
