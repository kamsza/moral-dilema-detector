package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class InfoWindow extends JFrame implements ActionListener {

    JButton bConfirm;
    JLabel lMessage;

    public InfoWindow(JFrame owner, ArrayList<ArrayList<String>> summaryList)
    {
        setSize(300, 300);
        setTitle("Results");
        setLayout(null);

        String bigString = "";
        for(ArrayList<String> list : summaryList){
            bigString += list.get(0) + " " + list.get(1) + " " + list.get(2);
        }



        Object[][] data = {{"Scenario name", "Ontology file", "Dilemma"}};





        DefaultTableModel model = getDefaultTableModel(data);
        model.removeRow(0);
        for(int i=0; i<summaryList.size(); i++){
            Vector<String> vector = new Vector<>();
            vector.add(summaryList.get(i).get(0));
            vector.add(summaryList.get(i).get(1).substring(summaryList.get(i).get(1).lastIndexOf("ontologies")));
            vector.add(summaryList.get(i).get(2));

            model.addRow(vector);
        }
        JTable jTable = new JTable(model);

        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(10, 40, 400, 200);
        add(jScrollPane);



        lMessage = new JLabel(bigString, SwingConstants.CENTER);
        lMessage.setBounds(30,30,250,50);
        add(lMessage);

        bConfirm = new JButton("ok");
        bConfirm.setBounds(100,100,100,100);
        bConfirm.addActionListener(this);
        add(bConfirm);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        if(eventSource == bConfirm) dispose();
    }


    protected DefaultTableModel getDefaultTableModel(Object[][] data) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, new String[]{"Scenario name","Ontology file", "Dilemma"}) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        return defaultTableModel;
    }

}


