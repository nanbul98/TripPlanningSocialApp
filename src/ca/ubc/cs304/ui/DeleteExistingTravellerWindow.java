package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.DeleteExistingTravellerWindowDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteExistingTravellerWindow  extends JFrame{
    private static final int TEXT_FIELD_WIDTH = 50;
    // delegate
    private DeleteExistingTravellerWindowDelegate delegate;
    public DeleteExistingTravellerWindow() {
        super("Delete Traveller");
    }
    private JTextField usernameField;

    public void showFrame(DeleteExistingTravellerWindowDelegate delegate) {
        this.delegate = delegate;
        //this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new MenuPane());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public class MenuPane extends JPanel implements ActionListener {

        public MenuPane() {
            setBorder(new EmptyBorder(300, 300, 300, 300));
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("<html><h2><strong>Delete Existing Traveller</strong></h2><hr></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel contentPane = new JPanel(new GridBagLayout());
            JLabel usernameLabel = new JLabel("Username: ");
            usernameField = new JTextField(TEXT_FIELD_WIDTH);

            // place the username label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            contentPane.add(usernameLabel,gbc);

            // place the text field for the username
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            contentPane.add(usernameField,gbc);
            JButton delete_user = new JButton("Delete User");
            gbc.weighty = 1;
            contentPane.add(delete_user, gbc);
            add(contentPane, gbc);

            delete_user.addActionListener(this);


        }

        @Override
        public void actionPerformed(ActionEvent e) {
            delegate.deleteTravellerIfExists(usernameField.getText());
        }


    }
}
