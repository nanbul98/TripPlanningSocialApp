package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AllTravellersDelegate;
import ca.ubc.cs304.delegates.ForumPostDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;


public class ForumPostWindow extends JFrame {

        private ForumPostDelegate delegate;
        public ForumPostWindow() { super("Forum Posts");}

        boolean postIDBoxSelected = false;
        boolean titleBoxSelected = false;
        boolean bodyBoxSelected = false;
        boolean timePostedBoxSelected = false;
        boolean authorBoxSelected = false;
        boolean tripIDBoxSelected = false;


        public void showFrame(ForumPostDelegate delegate) {
            this.delegate = delegate;
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(1000, 600);
            //this.add(new AllGroups.MenuPane());
            //this.pack();
            //this.setLocationRelativeTo(null);
            //this.setVisible(true);

            // Text Area at the Center
            JTextArea textArea = new JTextArea();

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            textArea.setEditable(false);
            ForumPostWindow fp = new ForumPostWindow();


            //Creating the MenuBar and adding components
            JLabel enterTripID = new JLabel("Enter Trip ID");
            JTextField tripIDField = new JTextField();
            JCheckBox postIDBox = new JCheckBox("See Post ID");
            JCheckBox titleBox = new JCheckBox("See Titles");
            JCheckBox bodyBox = new JCheckBox("See Bodies");
            JCheckBox timePostedBox = new JCheckBox("See Time Posted");
            JCheckBox authorBox = new JCheckBox("See Authors");
            JCheckBox tripIDBox = new JCheckBox("See Trip IDs");

            postIDBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        postIDBoxSelected = true;
                    } else {
                        postIDBoxSelected = false;
                    }
                }
            });
            titleBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        titleBoxSelected = true;
                    } else {
                        titleBoxSelected = false;
                    }
                }
            });
            bodyBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        bodyBoxSelected = true;
                    } else {
                        bodyBoxSelected = false;
                    }
                }
            });
            timePostedBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        timePostedBoxSelected = true;
                    } else {
                        timePostedBoxSelected = false;
                    }
                }
            });
            authorBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        authorBoxSelected = true;
                    } else {
                        authorBoxSelected = false;
                    }
                }
            });
            tripIDBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == 1) {
                        tripIDBoxSelected = true;
                    } else {
                        tripIDBoxSelected = false;
                    }
                }
            });

            JButton submit = new JButton("Submit");
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        List<String[]> res = delegate.getForumPosts(postIDBoxSelected, titleBoxSelected, bodyBoxSelected, timePostedBoxSelected, authorBoxSelected, tripIDBoxSelected);
                        displayResult(res, scrollPane);

                    } catch (Exception e) {
                        //displayErrorMsg(e.getMessage());
                        System.out.println("SQL Exception: " + e.getMessage());
                    }
                }
            });

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(enterTripID);
            menuBar.add(tripIDField);
            menuBar.add(postIDBox);
            menuBar.add(titleBox);
            menuBar.add(bodyBox);
            menuBar.add(timePostedBox);
            menuBar.add(authorBox);
            menuBar.add(tripIDBox);

            this.getContentPane().add(BorderLayout.NORTH, menuBar);
            this.getContentPane().add(BorderLayout.CENTER, scrollPane);
            this.setVisible(true);

        }

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


