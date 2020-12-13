package gui;

import DilemmaDetector.Consequences.CustomPhilosophy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ModifyCustomPhilosophyWindow extends CustomPhilosophyWindow implements ActionListener {
    private JButton jButtonSaveChanges;
    private JButton jButtonReturn;
    private JButton jButtonDelete;
    private JLabel jLabelPhilosophyName;

    private CustomPhilosophy customPhilosophy;


    public ModifyCustomPhilosophyWindow(DashboardWindow dashboardWindow, CustomPhilosophy customPhilosophy) {
        super(dashboardWindow);
        this.customPhilosophy = customPhilosophy;

        jLabelPhilosophyName = new JLabel("Philosophy: " + customPhilosophy.getPhilosophyName());
        jLabelPhilosophyName.setBounds(10, 10, 400, 30);
        add(jLabelPhilosophyName);

        Object[][] data = prepareData(customPhilosophy);
        DefaultTableModel model = getDefaultTableModel(data);
        jTable = new JTable(model);
        prepareJTableToEditing();

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 40, 400, 200);
        add(jScrollPane);

        jButtonSaveChanges = new JButton("Save changes");
        jButtonSaveChanges.setBounds(50, 300, 100, 60);
        jButtonSaveChanges.addActionListener(this);
        add(jButtonSaveChanges);

        jButtonReturn = new JButton("Don't save");
        jButtonReturn.setBounds(150, 300, 100, 60);
        jButtonReturn.addActionListener(this);
        add(jButtonReturn);

        jButtonDelete = new JButton("Delete");
        jButtonDelete.setBounds(250, 300, 100, 60);
        jButtonDelete.addActionListener(this);
        add(jButtonDelete);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonReturn) {
            closeWindowWithoutAction();
        }
        if (eventSource == jButtonSaveChanges) {
            jButtonSaveChangesAction();
        }
        if (eventSource == jButtonDelete) {
            jButtonDeleteAction();
        }
    }

    private void jButtonSaveChangesAction() {
        HashMap<String, Integer> tableValues = getTableValues();
        customPhilosophy.setParametersFromHashMap(tableValues);
        String philosophyJSON = mapObjectToJSON(customPhilosophy);
        try (FileWriter file = new FileWriter(getFullPathOfFileForGivenPhilosophyName(customPhilosophy.getPhilosophyName()))) {
            file.write(philosophyJSON);
            file.flush();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        setVisible(false);
        dispose();
    }

    private void jButtonDeleteAction() {
        File file = new File(getFullPathOfFileForGivenPhilosophyName(customPhilosophy.getPhilosophyName()));
        file.delete();
        dashboardWindow.updateCustomPhilosophiesList();
        setVisible(false);
    }
}
