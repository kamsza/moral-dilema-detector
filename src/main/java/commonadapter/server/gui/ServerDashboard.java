package commonadapter.server.gui;

import com.zeroc.IceInternal.Ex;
import commonadapter.server.logic.Server;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerDashboard extends JFrame implements ActionListener {

    private Server server;
    private Thread serverThread;
    private String owlFilePath;

    private JButton selectOntologyButton;
    private JButton startServerButton;
    private JButton stopServerButton;


    public ServerDashboard() {

        setSize(460, 300);
        setResizable(false);
        setTitle("Common Scenario Adapter - Server");
        setLayout(null);

        prepareDashboard();
    }

    private void prepareDashboard() {

        selectOntologyButton = new JButton("Select file with ontology");
        selectOntologyButton.setBounds(20, 20, 400, 50);
        selectOntologyButton.addActionListener(this);
        add(selectOntologyButton);

        startServerButton = new JButton("Start Server");
        startServerButton.setBounds(20, 90, 400, 50);
        startServerButton.addActionListener(this);
        startServerButton.setVisible(false);
        add(startServerButton);

        stopServerButton = new JButton("Stop Server");
        stopServerButton.setBounds(20, 160, 400, 50);
        stopServerButton.addActionListener(this);
        stopServerButton.setVisible(false);
        add(stopServerButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object eventSource = e.getSource();

        if (eventSource == selectOntologyButton) selectOntologyAction();

        if (eventSource == startServerButton) startServerAction();

        if (eventSource == stopServerButton) stopServerAction();

    }


    private void selectOntologyAction() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "OWL Ontology Files", "owl");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            owlFilePath = chooser.getSelectedFile().getAbsolutePath();
        }

        startServerButton.setVisible(true);

    }

    private void startServerAction() {

        server = new Server(owlFilePath);

        serverThread = new Thread(server);

        serverThread.start();

        stopServerButton.setVisible(true);
    }

    private void stopServerAction() {

        server.shutdown();

    }

}
