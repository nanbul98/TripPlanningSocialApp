package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.InterestListWindowDelegate;
import ca.ubc.cs304.model.GroupModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterestGroupListWindow extends JFrame {
    private GroupModel[] groupResults;

    public InterestGroupListWindow(GroupModel[] groupResults) {
        super("Group Results");
        this.groupResults = groupResults;
    }

    public void showFrame(InterestListWindowDelegate delegate) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.PAGE_START;

        JLabel title = new JLabel("<html><h1><strong>Groups matching Interest</strong></h1></title>");
        contentPane.add(title, c);

        c.gridheight = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable resultTable = new JTable(tableModel);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableModel.addColumn("Title");
        tableModel.addColumn("Description");
        tableModel.addColumn("GroupID");
        tableModel.addColumn("Owner");

        for (int i = 0; i < groupResults.length; i++) {
            tableModel.insertRow(i, groupResults[i].toRowData());
        }

        contentPane.add(new JScrollPane(resultTable), c);
        resultTable.setPreferredScrollableViewportSize(resultTable.getPreferredSize());

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
