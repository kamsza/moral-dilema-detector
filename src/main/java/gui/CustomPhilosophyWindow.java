package gui;


import DilemmaDetector.Consequences.CustomPhilosophy;
import DilemmaDetector.Consequences.PhilosophyParameter;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.io.IOException;
import java.util.HashMap;

public abstract class CustomPhilosophyWindow extends JFrame {

    protected JTable jTable;
    protected JScrollPane jScrollPane;
    protected DashboardWindow dashboardWindow;
    private String[] columnNames = {"Parameter", "Moral value", ""};

    public CustomPhilosophyWindow(DashboardWindow dashboardWindow){
        setSize(430, 450);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dashboardWindow = dashboardWindow;
    }

    protected Object[][] prepareData(CustomPhilosophy customPhilosophy) {
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


    protected HashMap<String, Integer> getTableValues() {
        HashMap<String, Integer> result = new HashMap<>();

        for (int i = 0; i < jTable.getRowCount(); i++) {
            result.put((String) jTable.getValueAt(i, 0), (Integer) jTable.getValueAt(i, 1));
        }
        return result;
    }

    protected DefaultTableModel getDefaultTableModel(Object[][] data) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 1;
            }
        };
        return defaultTableModel;
    }

    protected void prepareJTableToEditing(DefaultTableModel model) {
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

    protected String mapObjectToJSON(Object object) {
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

    protected String getFullPathOfFileForGivenPhilosophyName(String philosophyName ){
          return  System.getProperty("user.dir") +
            "\\src\\main\\resources\\gui\\customPhilosophies\\" + philosophyName + ".json";
    }

    protected void closeWindowWithoutAction(){
        setVisible(false);
        dispose();
    }



}

