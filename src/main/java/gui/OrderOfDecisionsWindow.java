package gui;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderOfDecisionsWindow extends JFrame implements ActionListener {

    private DashboardWindow dashboardWindow;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private JButton jButtonMoveDown;
    private JButton jButtonMoveUp;
    private JButton jButtonDiscard;
    private JButton jButtonSave;
    private JButton jButtonReturnToDefaultOrder;

    public static final String pathToCustomOrder = System.getProperty("user.dir") +
            "\\src\\main\\resources\\gui\\orderOfDecisions\\CustomOrder.json";
    private final String pathToDefaultOrder = System.getProperty("user.dir") +
            "\\src\\main\\resources\\gui\\orderOfDecisions\\DefaultOrder.json";

    private DefaultTableModel tableModel;

    public OrderOfDecisionsWindow(DashboardWindow dashboardWindow) {
        setSize(430, 450);
        setResizable(false);
        setTitle("Moral dilemma detector");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dashboardWindow = dashboardWindow;

        Object[][] data = prepareData();
        tableModel = getDefaultTableModel(data);

        jTable = new JTable(tableModel);
        jTable.setRowSelectionInterval(0, 0);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 10, 400, 160);
        add(jScrollPane);

        jButtonMoveDown = new JButton("Move down");
        jButtonMoveDown.setBounds(80, 200, 100, 30);
        jButtonMoveDown.addActionListener(this);
        add(jButtonMoveDown);


        jButtonMoveUp = new JButton("Move up");
        jButtonMoveUp.setBounds(180, 200, 100, 30);
        jButtonMoveUp.addActionListener(this);
        add(jButtonMoveUp);

        jButtonReturnToDefaultOrder = new JButton("Return to default  order");
        jButtonReturnToDefaultOrder.setBounds(80, 250, 200, 30);
        jButtonReturnToDefaultOrder.addActionListener(this);
        add(jButtonReturnToDefaultOrder);

        jButtonDiscard = new JButton("Discard");
        jButtonDiscard.setBounds(80, 300, 100, 30);
        jButtonDiscard.addActionListener(this);
        add(jButtonDiscard);

        jButtonSave = new JButton("Save");
        jButtonSave.setBounds(180, 300, 100, 30);
        jButtonSave.addActionListener(this);
        add(jButtonSave);

    }

    private Object[][] prepareData() {
        ArrayList<String> customOrder = getOrderFromFile(pathToCustomOrder);
        Object[][] data = {
                {customOrder.get(0)},
                {customOrder.get(1)},
                {customOrder.get(2)},
                {customOrder.get(3)},
                {customOrder.get(4)},
                {customOrder.get(5)},
        };
        return data;

    }

    protected DefaultTableModel getDefaultTableModel(Object[][] data) {
        String[] columnNames = {"Type of action"};

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int col) {

                return false;
            }
        };
        return defaultTableModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if (eventSource == jButtonMoveDown) jButtonMoveDownAction(true);
        if (eventSource == jButtonMoveUp) jButtonMoveDownAction(false);
        if (eventSource == jButtonSave) jButtonSaveAction();
        if (eventSource == jButtonDiscard) closeWindowAction();
        if (eventSource == jButtonReturnToDefaultOrder) jButtonReturnToDefaultOrderAction();


    }

    private void jButtonReturnToDefaultOrderAction() {
        ArrayList<String> order = getOrderFromFile(pathToDefaultOrder);
        for (int i = 0; i < order.size(); i++) {
            tableModel.setValueAt(order.get(i), i, 0);
        }
        tableModel.fireTableRowsUpdated(0, tableModel.getRowCount() - 1);

    }

    private void jButtonSaveAction() {
        String pathToFile = System.getProperty("user.dir") +
                "\\src\\main\\resources\\gui\\orderOfDecisions\\CustomOrder.json";
        List<String> currentOrder = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            currentOrder.add((String) tableModel.getValueAt(i, 0));
        }
        String orderInString = mapObjectToJSON(currentOrder);

        try (FileWriter file = new FileWriter(pathToFile)) {
            file.write(orderInString);
            file.flush();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        closeWindowAction();

    }

    private String mapObjectToJSON(Object object) {
        ObjectMapper Obj = new ObjectMapper();
        Obj.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonStr = null;
        try {

            jsonStr = Obj.writeValueAsString(object);
            System.out.println(jsonStr);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return jsonStr;
    }

    protected void closeWindowAction() {
        setVisible(false);
        dispose();
    }


    private void jButtonMoveDownAction(boolean down) {
        int selectedRowIndex = jTable.getSelectedRow();
        if (selectedRowIndex == 0 && down == false) return;
        if (selectedRowIndex == tableModel.getRowCount() - 1 && down == true) return;
        int changeIndex = (down == true ? -1 : 1);
        tableModel.moveRow(selectedRowIndex, selectedRowIndex, selectedRowIndex - changeIndex);
        jTable.setRowSelectionInterval(selectedRowIndex - changeIndex, selectedRowIndex - changeIndex);
        jTable.repaint();
    }


    public static ArrayList<String> getOrderFromFile(String pathToFile) {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(pathToFile);
        ArrayList<String> customOrder = null;
        try {
            customOrder = objectMapper.readValue(file, ArrayList.class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return customOrder;
    }
}
