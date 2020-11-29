package gui;


import DilemmaDetector.Consequences.CustomPhilosophy;
import DilemmaDetector.Consequences.PhilosophyParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CustomPhilosophyWindow extends JFrame implements ActionListener {

    //common
    private JTable jTable;
    private JScrollPane jScrollPane;
    private DashboardWindow dashboardWindow;

    //add new
    private JButton jButtonSave;
    private JButton jButtonDiscard;
    private JLabel jLabelPromptToEnterName;
    private JTextField jTextField;

    //show details/modify
    private JButton jButtonSaveChanges;
    private JButton jButtonReturn;
    private JButton jButtonDelete;

    private CustomPhilosophy customPhilosophy;


    private String[] columnNames = {"Parameter", "Moral value", ""};


    public void setBasicProperties(DashboardWindow dashboardWindow) {
        setSize(430, 450);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dashboardWindow = dashboardWindow;
    }


    public CustomPhilosophyWindow(DashboardWindow dashboardWindow, boolean isCreatingNewPhilosophy) {

        setBasicProperties(dashboardWindow);
        Object[][] data = prepareData(CustomPhilosophy.getSimplestPhilosophy());
        DefaultTableModel model = getDefaultTableModel(data);
        jTable = new JTable(model);
        prepareJTableToEditing(model);

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

    public CustomPhilosophyWindow(DashboardWindow dashboardWindow, CustomPhilosophy customPhilosophy) {
        setTitle("Custom philosophy " + customPhilosophy.getPhilosophyName());
        this.customPhilosophy = customPhilosophy;

        setBasicProperties(dashboardWindow);
        Object[][] data = prepareData(customPhilosophy);
        DefaultTableModel model = getDefaultTableModel(data);
        jTable = new JTable(model);
        prepareJTableToEditing(model);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 10, 400, 200);
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

    private Object[][] prepareData(CustomPhilosophy customPhilosophy) {
        HashMap<PhilosophyParameter, Integer> parameters = customPhilosophy.getParameters();
        Object[][] data = {
                {"Human life inside main vehicle", parameters.get(PhilosophyParameter.HUMAN_LIFE_INSIDE_MAIN_VEHICLE), "Info"},
                {"Human life outside main vehicle", parameters.get(PhilosophyParameter.HUMAN_LIFE_OUTSIDE_MAIN_VEHICLE), "Info"},
                {"Human severe injury inside main vehicle", parameters.get(PhilosophyParameter.HUMAN_SEVERE_INJURY_INSIDE_MAIN_VEHICLE), "Info"},
                {"Human severe injury outside main vehicle", parameters.get(PhilosophyParameter.HUMAN_SEVERE_INJURY_OUTSIDE_MAIN_VEHICLE), "Info"},
                {"Human lightly injury inside main vehicle", parameters.get(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_INSIDE_MAIN_VEHICLE), "Info"},
                {"Human lightly injury outside main vehicle", parameters.get(PhilosophyParameter.HUMAN_LIGHTLY_INJURY_OUTSIDE_MAIN_VEHICLE), "Info"},
                {"Animal life", parameters.get(PhilosophyParameter.ANIMAL_LIFE), "Info"},
                {"Animal severe injury", parameters.get(PhilosophyParameter.ANIMAL_SEVERE_INJURY), "Info"},
                {"Animal lightly injury", parameters.get(PhilosophyParameter.ANIMAL_LIGHTLY_INJURY), "Info"},
                {"Material damages per 1000$", parameters.get(PhilosophyParameter.MATERIAL_VALUE), "Info"},
                {"Taking action", parameters.get(PhilosophyParameter.TAKING_ACTION), "Info"},
                {"Dilemma threshold", parameters.get(PhilosophyParameter.DILEMMA_THRESHOLD), "Info"}
        };
        return data;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonSave) {
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

                    try (FileWriter file = new FileWriter(System.getProperty("user.dir") +
                            "\\src\\main\\resources\\gui\\customPhilosophies\\" + philosophyName + ".json")) {

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
        if (eventSource == jButtonDiscard || eventSource == jButtonReturn) {
            setVisible(false);
            dispose();
        }
        if (eventSource == jButtonSaveChanges) {
            HashMap<String, Integer> tableValues = getTableValues();
            customPhilosophy.setParametersFromHashMap(tableValues);
            String philosophyJSON = mapObjectToJSON(customPhilosophy);
            try (FileWriter file = new FileWriter(System.getProperty("user.dir") +
                    "\\src\\main\\resources\\gui\\customPhilosophies\\" + customPhilosophy.getPhilosophyName() + ".json")) {

                file.write(philosophyJSON);
                file.flush();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            dashboardWindow.updateCustomPhilosophiesList();
            setVisible(false);
            dispose();
        }
        if (eventSource == jButtonDelete) {
            File file = new File(System.getProperty("user.dir") +
                    "\\src\\main\\resources\\gui\\customPhilosophies\\" + customPhilosophy.getPhilosophyName() + ".json");
            file.delete();
            dashboardWindow.updateCustomPhilosophiesList();
            setVisible(false);
        }
    }

    public HashMap<String, Integer> getTableValues() {
        HashMap<String, Integer> result = new HashMap<>();

        for (int i = 0; i < jTable.getRowCount(); i++) {
            result.put((String) jTable.getValueAt(i, 0), (Integer) jTable.getValueAt(i, 1));
        }
        return result;
    }

    private DefaultTableModel getDefaultTableModel(Object[][] data) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 1;
            }
        };
        return defaultTableModel;
    }

    private void prepareJTableToEditing(DefaultTableModel model) {
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
    }

    private String mapObjectToJSON(Object object) {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = null;
        try {

            jsonStr = Obj.writeValueAsString(object);
            System.out.println(jsonStr);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return jsonStr;
    }
}

