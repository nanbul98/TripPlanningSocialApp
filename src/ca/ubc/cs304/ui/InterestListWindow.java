package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.InterestListWindowDelegate;
import ca.ubc.cs304.model.GroupModel;
import ca.ubc.cs304.model.InterestModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterestListWindow extends JFrame implements ActionListener {
    private InterestModel[] interestList;
    private InterestListWindowDelegate delegate;

    private String selectedInterest;

    public InterestListWindow() {
        super("List of All Interests");
    }

    public void showFrame(InterestListWindowDelegate delegate) {
        this.delegate = delegate;
        interestList = delegate.getAllInterests();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(300, 300, 300, 300));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.PAGE_START;

        JLabel title = new JLabel("<html><h1><strong>Interests</strong></h2><hr></html>");
        contentPane.add(title, c);

        JLabel header = new JLabel("<html><h2>Select an interest to search for related groups.</h2></html>");
        contentPane.add(header, c);

        c.anchor = GridBagConstraints.LINE_START;
        ButtonGroup interestButtons = new ButtonGroup();
        for (InterestModel interest : interestList) {
            JRadioButton interestButton = new JRadioButton(interest.getName() + interest.getDescription());
            interestButton.setActionCommand(interest.getName());
            interestButtons.add(interestButton);
            interestButton.addActionListener(this);
            contentPane.add(interestButton, c);
        }

        c.gridheight = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.PAGE_END;
        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        searchButton.addActionListener(this);
        contentPane.add(searchButton, c);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("search")) {
            GroupModel[] groupResults = delegate.findGroupsWithInterest(selectedInterest);
            delegate.goToInterestGroupsPage(groupResults);
        } else {
            selectedInterest = e.getActionCommand();
        }
    }
}
