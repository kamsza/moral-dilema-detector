package gui;

import DilemmaDetector.Consequences.CustomPhilosophy;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AddCustomPhilosophyWindow extends CustomPhilosophyWindow implements ActionListener {

    private JButton jButtonSave;
    private JButton jButtonDiscard;
    private JLabel jLabelPromptToEnterName;
    private JTextField jTextField;

    public AddCustomPhilosophyWindow(DashboardWindow dashboardWindow) {
        super(dashboardWindow);

        Object[][] data = prepareData(CustomPhilosophy.getSimplestPhilosophy());
        DefaultTableModel model = getDefaultTableModel(data);
        jTable = new JTable(model);
        prepareJTableToEditing(model);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 10, 400, 200);
        add(jScrollPane);

        jLabelPromptToEnterName = new JLabel("Enter custom philosophy name");
        jLabelPromptToEnterName.setBounds(80, 220, 250, 30);
        add(jLabelPromptToEnterName);

        jTextField = new JTextField();
        jTextField.setBounds(80, 250, 250, 20);
        add(jTextField);

        jButtonDiscard = new JButton("Discard");
        jButtonDiscard.setBounds(80, 300, 100, 60);
        jButtonDiscard.addActionListener(this);
        add(jButtonDiscard);

        jButtonSave = new JButton("Save");
        jButtonSave.setBounds(230, 300, 100, 60);
        jButtonSave.addActionListener(this);
        add(jButtonSave);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonSave) {
            jButtonSaveAction();
        }
        if (eventSource == jButtonDiscard) {
            closeWindowWithoutAction();
        }
    }

    private void jButtonSaveAction() {
        String philosophyName = jTextField.getText();
        if (StringUtils.isNotBlank(philosophyName)) {
            List<String> savedPhilosophiesNames = dashboardWindow.getCustomPhilosophiesNames();
            if (savedPhilosophiesNames.contains(philosophyName)) {
                WarningWindow warningWindow = new WarningWindow(this, "This philosophy name is not unique");
                warningWindow.setVisible(true);
            } else {
                CustomPhilosophy customPhilosophy = new CustomPhilosophy();
                customPhilosophy.setPhilosophyName(philosophyName);
                HashMap<String, Integer> tableValues = getTableValues();
                customPhilosophy.setParametersFromHashMap(tableValues);
                String philosophyJSON = mapObjectToJSON(customPhilosophy);

                try (FileWriter file = new FileWriter(getFullPathOfFileForGivenPhilosophyName(philosophyName))) {

                    file.write(philosophyJSON);
                    file.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                dashboardWindow.updateCustomPhilosophiesList();
                setVisible(false);
                dispose();
            }
        } else {
            WarningWindow warningWindow = new WarningWindow(this, "Please enter philosophy name");
            warningWindow.setVisible(true);
        }
    }
}
