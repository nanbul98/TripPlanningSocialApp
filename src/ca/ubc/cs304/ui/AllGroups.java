package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AllGroupsDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
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


        JButton goBackMainWindow = new JButton(new AbstractAction("Back to Main Window") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                delegate.goToMainWindowFromGroups();
            }
        });



        //Creating the MenuBar and adding components
        JButton viewAllGroupsBtn = new JButton("View All Groups");
        viewAllGroupsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    List<String[]> res = delegate.viewAllGroups();
                    displayResult(res, scrollPane);

                } catch (Exception e) {
                    //displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }
            }
        });


        // View Group in detail
        JButton viewGroupDetail = new JButton("Group Info");
        viewGroupDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String res = promptInputSetGroupInfo();
                try {
                    List<String[]> group = delegate.getGroupInfo(res);
                    displayResult(group, scrollPane);

                } catch (Exception e) {
 //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });

        // View Group Members in detail - member count, view in detail
        JButton viewGroupMembers = new JButton("View Group Members");
        viewGroupMembers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String res = promptInputSetGroupMember();
                try {
                    // Shows total num of member in the group
                    int count = delegate.countAllMember(res);
                    System.out.println("Counted all the members " + count);
                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Total Num of Group Member: " + count));

                    // Detailed view of the group
                    JButton showDetails = new JButton("Details");
                    myPanel.add(showDetails);

                    showDetails.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            List<String[]> members = delegate.viewAllGroupMembers(res);
                            displayResult(members, scrollPane);

                        }
                    });

                    scrollPane.setViewportView(myPanel);

                } catch (Exception e) {
                    //displayErrorMsg(e.getMessage());
//                    System.out.println("SQL Exception: " + e.getMessage());
                }
            }
        });

        // View trip's all trips
        JButton viewGroupTrips = new JButton("View Trips");
        viewGroupTrips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String res = promptInputSetGroupMember();
                try {
                    List<String[]> group = delegate.viewGroupTrips(res);
                    displayResult(group, scrollPane);

                } catch (Exception e) {
                    //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });

        // View trips's all activity
        JButton viewTripActiviy = new JButton("View Activities");
        viewTripActiviy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] res = promptInputSetActivity();
                try {
                    List<String[]> group = delegate.viewTripActivity(res[0], res[1]);
                    displayResult(group, scrollPane);

                } catch (Exception e) {
                    //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });

        // find group that all the travellers are part of
        JButton groupWithEveryone = new JButton("Groups with all traveller");
        groupWithEveryone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    List<String[]> group = delegate.findGroupWithEveryOne();
                    displayResult(group, scrollPane);

                } catch (Exception e) {
                    //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });


        // Update activity's description
        JButton updateActivityDescrip = new JButton("Modify Activity");
        updateActivityDescrip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] res = promptInputSetActivityDescrip();
                try {
                    delegate.updateActivityDescrip(res[0], res[1]);
                    //displayResult(group, scrollPane);

                } catch (Exception e) {
                    //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });



        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenuBar menubarBottom = new JMenuBar();
        //menuBar.setOpaque(true);
        //menuBar.setPreferredSize(new Dimension(1000, 30));


        menuBar.add(viewAllGroupsBtn);
        menuBar.add(viewGroupDetail);
        menuBar.add(viewGroupMembers);
        menuBar.add(groupWithEveryone);
        menuBar.add(goBackMainWindow);
        menubarBottom.add(viewGroupTrips);
        menubarBottom.add(viewTripActiviy);
        menubarBottom.add(updateActivityDescrip);


        this.getContentPane().add(BorderLayout.NORTH, menuBar);
        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, menubarBottom);
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

    private String promptInputSetGroupInfo() {
        String res = null;
        JTextField groupTitle = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Group Title:"));
        myPanel.add(groupTitle);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Which group?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            res = groupTitle.getText();
        }

        System.out.println("GRAB SOETHING " + res);
        return res;
    }


    private String promptInputSetGroupMember() {
        String res = null;
        JTextField groupID = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Group ID: "));
        myPanel.add(groupID);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Which group?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            res = groupID.getText();
        }

        System.out.println("GRAB SOETHING " + res);
        return res;
    }

    private String[] promptInputSetActivity() {
        String[] res = new String[2];
        JTextField groupID = new JTextField(5);
        JTextField tripID = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Group ID: "));
        myPanel.add(groupID);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer
        myPanel.add(new JLabel("Trip ID: "));
        myPanel.add(tripID);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Insert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            res[0] = groupID.getText();
            res[1] = tripID.getText();
        }

        System.out.println("GRAB SOETHING " + res);
        return res;
    }

    private String[] promptInputSetActivityDescrip() {
        String[] res = new String[2];
        JTextField activityID = new JTextField(5);
        JTextField description = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Activity ID: "));
        myPanel.add(activityID);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer
        myPanel.add(new JLabel("New Description: "));
        myPanel.add(description);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please insert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            res[0] = activityID.getText();
            res[1] = description.getText();
        }

        System.out.println("GRAB SOETHING " + res);
        return res;
    }









}
