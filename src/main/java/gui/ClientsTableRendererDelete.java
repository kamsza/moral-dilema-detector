package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientsTableRendererDelete extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row, col;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane jScrollPane;
    private JFrame owner;


    public void setjScrollPane(JScrollPane jScrollPane)
    {
        this.jScrollPane = jScrollPane;
    }

    public void setOwner(JFrame owner) {
        this.owner = owner;
    }

    /// do usunięcia
    private String[] columnNames = {"Parameter", "Cost value", "Info", "Delete"};
    private Object[][] data = {{"Dummy", 12, "Info", "Remove"},
            {"Dummy", 12, "Info", "Remove"}};
    private DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int row, int column) {
            return column == 3 || column == 2 || column == 1;
        }
    };
    /////



    public ClientsTableRendererDelete(JCheckBox checkBox, DefaultTableModel tableModel) {
        super(checkBox);
        this.tableModel = tableModel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        this.col = column;

        button.setForeground(Color.black);
        button.setBackground(UIManager.getColor("Button.background"));
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            System.out.println("usuwanie");

            table.setVisible(false);

            table = new JTable(model);
            table.setSurrendersFocusOnKeystroke(true);
            table.getColumnModel().getColumn(1).setCellEditor(new SpinnerEditor());

            table.getColumnModel().getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
            table.getColumnModel().getColumn(2).setCellEditor(new ClientsTableRendererInfo(new JCheckBox(), model));

            table.getColumnModel().getColumn(3).setCellRenderer(new ClientsTableButtonRenderer());
            table.getColumnModel().getColumn(3).setCellEditor(new ClientsTableRendererDelete(new JCheckBox(), model));

            jScrollPane = new JScrollPane(table);
//            tableModel.removeRow(row);
//            table.getModel().re
           // JOptionPane.showMessageDialog(button, "Column with Value: " + table.getValueAt(row, 1) + " -  Clicked!");
        }
        clicked = false;
        return new String(label);
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
