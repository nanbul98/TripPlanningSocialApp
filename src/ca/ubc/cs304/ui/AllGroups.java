package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AllGroupsDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AllGroups extends JFrame {

    private AllGroupsDelegate delegate;
    public AllGroups() { super("List of All Groups");}

    public void showFrame(AllGroupsDelegate delegate) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,600);
        //this.add(new AllGroups.MenuPane());
        //this.pack();
        //this.setLocationRelativeTo(null);
        //this.setVisible(true);

        // Text Area at the Center
        JTextArea textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setEditable(false);

        //Creating the MenuBar and adding components
        JButton viewAllGroupsBtn = new JButton("View All Groups");
        viewAllGroupsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //String[] res = promptInputSetofAllGroups()
                try {
                    List<String[]> res = delegate.viewAllGroups();
                    displayResult(res, scrollPane);
                } catch (Exception e) {
                    //displayErrorMsg(e.getMessage());
                }
            }
        });

        JButton viewGroupDetail = new JButton("Group Info");




        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        //menuBar.setOpaque(true);
        //menuBar.setPreferredSize(new Dimension(1000, 30));


        menuBar.add(viewAllGroupsBtn);
        menuBar.add(viewGroupDetail);
        //menuBar.add(interestsMenu);



        this.getContentPane().add(BorderLayout.NORTH, menuBar);
        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.setVisible(true);

    }



    /*
    public class MenuPane extends JPanel implements ActionListener {

        public MenuPane() {
            setBorder(new EmptyBorder(300, 300, 300, 300));
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("<html><h2><strong>List of All Groupss</strong></h2><hr></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;



        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }


    }
     */


    private void displayResult(List<String[]> res, JScrollPane scrollPane) {
        if (res.size() == 0) {
            return;
        }
        String[] columnNames = new String[res.get(0).length];
        String[][] data = new String[res.size() - 1][res.get(0).length];
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(0).length; j++) {
                if (i == 0) {
                    columnNames[j] = res.get(0)[j];
                } else {
                    data[i - 1][j] = res.get(i)[j];
                }
            }
        }
        // Initializing the JTable
        JTable jTable;
        jTable = new JTable(data, columnNames);
        jTable.setBounds(30, 40, 200, 300);
        scrollPane.setViewportView(jTable);
    }


}
