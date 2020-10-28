package gui;


import DilemmaDetector.Consequences.CustomPhilosophy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CustomPhilosophyWindow extends JFrame implements ActionListener {


    private JTable jTable;
    private JScrollPane jScrollPane;
    private JButton jButtonSave;
    private JButton jButtonDiscard;
    private JLabel jLabelPromptToEnterName;
    private JTextField jTextField;

    private String[] columnNames = {"Parameter", "Moral value", ""};
    private Object[][] data = {
            {"Human life inside main vehicle", 1, "Info"},
            {"Human life outside main vehicle", 1, "Info"},
            {"Human severe injury inside main vehicle", 1, "Info"},
            {"Human severe injury outside main vehicle", 1, "Info"},
            {"Human lightly injury inside main vehicle", 1, "Info"},
            {"Human lightly injury outside main vehicle", 1, "Info"},
            {"Animal life", 1, "Info"},
            {"Animal severe injury", 1, "Info"},
            {"Animal lightly injury", 1, "Info"},
            {"Material damages per 1000$", 0, "Info"},
            {"Breaking the law", 0, "Info"}
    };
    private DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int row, int column) {
            return column == 2 || column == 1;
        }
    };


    public CustomPhilosophyWindow() {
        setSize(430, 450);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        jTable = new JTable(model);
        jTable.setSurrendersFocusOnKeystroke(true);
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        tableColumnModel.getColumn(1).setCellEditor(new SpinnerEditor());

        tableColumnModel.getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
        ClientsTableRendererInfo clientsTableRendererInfo = new ClientsTableRendererInfo(new JCheckBox(), model);
        tableColumnModel.getColumn(2).setCellEditor(clientsTableRendererInfo);

        tableColumnModel.getColumn(0).setPreferredWidth(250);
        tableColumnModel.getColumn(1).setPreferredWidth(75);


        jTable.setPreferredScrollableViewportSize(jTable.getPreferredSize());
        jTable.setShowHorizontalLines(true);
        jTable.setShowVerticalLines(false);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 10, 400, 200);
        add(jScrollPane);


        jLabelPromptToEnterName = new JLabel("Enter cutom philosophy name");
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
            String philosophyName = jTextField.getText();

            //jeszcze sprawdzenie czy nazwy się nie powtarzają
            if (StringUtils.isNotBlank(philosophyName)) {
                CustomPhilosophy customPhilosophy = new CustomPhilosophy();
                customPhilosophy.setPhilosophyName(philosophyName);
                HashMap<String, Integer> tableValues = getTableValues();
                customPhilosophy.setParametersFromHashMap(tableValues);
                System.out.println(customPhilosophy.toString());
                // Creating Object of ObjectMapper define in Jakson Api
                ObjectMapper Obj = new ObjectMapper();
                String jsonStr = null;
                try {

                    jsonStr = Obj.writeValueAsString(customPhilosophy);


                    // Displaying JSON String
                    System.out.println(jsonStr);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                //Write JSON file
                try (FileWriter file = new FileWriter(System.getProperty("user.dir") +
                        "\\src\\main\\resources\\gui\\customPhilosophies\\" + philosophyName + ".json")) {

                    file.write(jsonStr);
                    file.flush();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                setVisible(false);
                dispose();


            } else {
                WarningWindow warningWindow = new WarningWindow(this, "Please enter philosophy name");
                warningWindow.setVisible(true);
            }
        }
        if (eventSource == jButtonDiscard) {
            setVisible(false);
            dispose();
        }
    }

    public HashMap<String, Integer> getTableValues() {
        HashMap<String, Integer> result = new HashMap<>();

        for (int i = 0; i < jTable.getRowCount(); i++) {
            result.put((String) jTable.getValueAt(i, 0), (Integer) jTable.getValueAt(i, 1));
        }
        return result;
    }
}
