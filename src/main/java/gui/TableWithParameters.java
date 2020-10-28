package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.HashMap;

public class TableWithParameters extends JPanel {

    JTable jTable;

    static String[] columnNames = {
            "Name", "Value"
    };
    static Object[][] data = {
            {"Human life", 1},
            {"Human severe injury", 0},
            {"Human lightly injury", 0},
            {"Animal life", 0},
            {"Animal severe injury", 0},
            {"Animal lightly injury", 0},
            {"Material damages per 1000$", 0},
            {"Breaking the law", 0}
    };

    public TableWithParameters() {
        super();
        jTable = new JTable(data, columnNames);
        jTable.setSurrendersFocusOnKeystroke(true);
        TableColumnModel tableColumnModel = jTable.getColumnModel();

        tableColumnModel.getColumn(1).setCellEditor(new SpinnerEditor());
        tableColumnModel.getColumn(0).setPreferredWidth(150);
        tableColumnModel.getColumn(1).setPreferredWidth(50);
        add(jTable);
    }


    public HashMap<String, Integer> getTableValues() {
        HashMap<String, Integer> result = new HashMap<>();

        for (int i = 0; i < jTable.getRowCount(); i++) {
            result.put((String) jTable.getValueAt(i, 0), (Integer) jTable.getValueAt(i, 1));
        }
        System.out.println(jTable.getRowHeight());
        return result;
    }
}
