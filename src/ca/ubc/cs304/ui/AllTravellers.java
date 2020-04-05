package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AllGroupsDelegate;
import ca.ubc.cs304.delegates.AllTravellersDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AllTravellers extends JFrame {

    private AllTravellersDelegate delegate;
    public AllTravellers() { super("List of All Travellers");}


    public void showFrame(AllTravellersDelegate delegate) {
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


            //Creating the MenuBar and adding components
            JButton viewAllUsersBtn = new JButton("View All Users");
            viewAllUsersBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        List<String[]> res = delegate.viewAllUsers();
                        displayResult(res, scrollPane);

                    } catch (Exception e) {
                        //displayErrorMsg(e.getMessage());
                        System.out.println("SQL Exception: " + e.getMessage());
                    }
                }
            });

            JButton goToAddTravellers = new JButton(new AbstractAction("Insert +") {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    delegate.goAddNewTraveller();
                }
            });
            JButton goToDeleteTravellers = new JButton(new AbstractAction("Delete -") {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    delegate.goDeleteTraveller();
                }
            });
        JButton goBackMainWindow = new JButton(new AbstractAction("Back to Main Window") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                delegate.goToMainWindow();
            }
        });
        // View Group in detail
        JButton searchUserByName = new JButton("Search User By Name");
        searchUserByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String res = promptInputSetUserInfo();
                try {
                    List<String[]> group = delegate.getTravellerInfoBasedOnTitle(res);
                    displayResult(group, scrollPane);

                } catch (Exception e) {
                    //                   displayErrorMsg(e.getMessage());
                    System.out.println("SQL Exception: " + e.getMessage());
                }

            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(viewAllUsersBtn);
        menuBar.add(searchUserByName);
        menuBar.add(goToAddTravellers);
        menuBar.add(goToDeleteTravellers);
        menuBar.add(goBackMainWindow);

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

    private String promptInputSetUserInfo() {
        String res = null;
        JTextField userTitle = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Search User by Name:"));
        myPanel.add(userTitle);
        myPanel.add(Box.createHorizontalStrut(10)); // a spacer

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            res = userTitle.getText();
        }

        System.out.println("GRAB SOETHING " + res);
        return res;
    }

//    public class MenuPane extends JPanel {
//
//        public MenuPane() {
//            setBorder(new EmptyBorder(300, 300, 300, 300));
//            setLayout(new GridBagLayout());
//
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gbc.anchor = GridBagConstraints.NORTH;
//
//            add(new JLabel("<html><h2><strong>List of All Travellers</strong></h2><hr></html>"), gbc);
//
//            gbc.anchor = GridBagConstraints.CENTER;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//
//
//            JPanel page = new JPanel(new GridBagLayout());

//            gbc.weighty = 1;
//            add(goToAddTravellers, gbc);
//            add(goToDeleteTravellers, gbc);
//
//
//
//        }
//
//    }

}
