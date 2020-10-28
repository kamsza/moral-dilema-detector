package gui;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TableWithButtonDemo {
    private JFrame frame = new JFrame("Table Demo");
    private String[] columnNames = {"Parameter", "Cost value", "Info", "Delete"};
    private Object[][] data = {{"Dummy", 12, "Info", "Remove"},
            {"Dummy", 12, "Info", "Remove"}};
    private DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int row, int column) {
            return column == 3 || column == 2 || column == 1;
        }
    };
    private JTable table;

    public TableWithButtonDemo() {
        table = new JTable(model);
        table.setSurrendersFocusOnKeystroke(true);
        table.getColumnModel().getColumn(1).setCellEditor(new SpinnerEditor());

        table.getColumnModel().getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
        ClientsTableRendererInfo clientsTableRendererInfo = new ClientsTableRendererInfo(new JCheckBox(), model);
        table.getColumnModel().getColumn(2).setCellEditor(clientsTableRendererInfo);

        table.getColumnModel().getColumn(3).setCellRenderer(new ClientsTableButtonRenderer());
        ClientsTableRendererDelete clientsTableRendererDelete = new ClientsTableRendererDelete(new JCheckBox(), model);
        table.getColumnModel().getColumn(3).setCellEditor(clientsTableRendererDelete);
//

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        JScrollPane scroll = new JScrollPane(table);
        clientsTableRendererDelete.setjScrollPane(scroll);
//        clientsTableRendererDelete.setOwner(this);
//


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scroll);
        frame.pack();
        frame.setLocation(150, 150);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableWithButtonDemo();
            }
        });
    }
}
