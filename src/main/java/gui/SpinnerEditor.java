package gui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.EventObject;

public class SpinnerEditor extends DefaultCellEditor {
    JSpinner spinner;
    JSpinner.DefaultEditor editor;
    JTextField textField;
    boolean valueSet;

    // Initializes the spinner.
    public SpinnerEditor() {
        super(new JTextField());
        spinner = new JSpinner();
        editor = ((JSpinner.DefaultEditor) spinner.getEditor());
        textField = editor.getTextField();
        textField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                //textField.setSelectionStart(0);
                //textField.setSelectionEnd(1);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (valueSet) {
                            textField.setCaretPosition(1);
                        }
                    }
                });
            }

            public void focusLost(FocusEvent fe) {
            }
        });
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                stopCellEditing();
            }
        });
    }

    // Prepares the spinner component and returns it.
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column
    ) {
        if (!valueSet) {
            spinner.setValue(value);
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textField.requestFocus();
            }
        });
        return spinner;
    }

    public boolean isCellEditable(EventObject eo) {
        if (eo instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) eo;
            textField.setText(String.valueOf(ke.getKeyChar()));


            //textField.select(1,1);
            //textField.setCaretPosition(1);
            //textField.moveCaretPosition(1);
            valueSet = true;
        } else {
            valueSet = false;
        }
        return true;
    }

    // Returns the spinners current value.
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public boolean stopCellEditing() {
        try {
            editor.commitEdit();
            spinner.commitEdit();

            int currentValue = (Integer) spinner.getValue();
            if (currentValue < 0) {
                spinner.setValue(0);
                JOptionPane.showMessageDialog(null,
                        "Value must not be negative");
            }

        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid value, discarding.");
        }
        return super.stopCellEditing();
    }
}
