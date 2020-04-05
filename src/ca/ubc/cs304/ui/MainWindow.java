package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.MainWindowDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{

    // delegate
    private MainWindowDelegate delegate;
    public MainWindow() {
        super("Main Window");
    }

    public void showFrame(MainWindowDelegate delegate) {
        this.delegate = delegate;
        //this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new MenuPane());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }



    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

    }



    public class MenuPane extends JPanel implements ActionListener {

        public MenuPane() {
            setBorder(new EmptyBorder(300, 300, 300, 300));
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("<html><h2><strong>Hi Admin, Welcome to Travelling Social App</strong></h2><hr></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel buttons = new JPanel(new GridBagLayout());
            JButton allTravellers = new JButton("See All Travellers");
            JButton allGroups = new JButton("See All Groups");
            JButton allInterests = new JButton("See All Interests");
            allTravellers.setActionCommand("travellers");
            allGroups.setActionCommand("groups");
            allInterests.setActionCommand("interests");
            buttons.add(allTravellers, gbc);
            buttons.add(allGroups, gbc);
            buttons.add(allInterests, gbc);
            gbc.weighty = 1;
            add(buttons, gbc);

            allTravellers.addActionListener(this);
            allGroups.addActionListener(this);
            allInterests.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "travellers":
                    delegate.goToTravellersPage();
                    break;
                case "groups":
                    delegate.goToGroupsPage();
                    break;
                case "interests":
                    delegate.goToInterestsPage();
                    break;
            }
        }
    }







}
