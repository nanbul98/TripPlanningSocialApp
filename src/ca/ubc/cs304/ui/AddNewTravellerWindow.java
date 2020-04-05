package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AddNewTravellerWindowDelegate;
import ca.ubc.cs304.model.TravellerModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: Clean up the code and maybe fix title
public class AddNewTravellerWindow extends JFrame {
    private static final int TEXT_FIELD_WIDTH = 50;
    private AddNewTravellerWindowDelegate delegate;
    public AddNewTravellerWindow() { super("Add New Traveller");}

    // components of the add traveller window
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField countryField;
    private JTextField provinceField;
    private JTextField cityField;
    private JTextField genderField;
    private JTextField birthDateField;

    public void showFrame(AddNewTravellerWindowDelegate delegate) {
        this.delegate = delegate;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new MenuPane());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // place the cursor in the text field for the username
        usernameField.requestFocus();
    }

    public class MenuPane extends JPanel implements ActionListener {

        public MenuPane() {
            JLabel usernameLabel = new JLabel("Username: ");
            JLabel nameLabel = new JLabel("Name: ");
            JLabel countryLabel = new JLabel("Country: ");
            JLabel provinceLabel = new JLabel("Province: ");
            JLabel cityLabel = new JLabel("City: ");
            JLabel genderLabel = new JLabel("Gender: ");
            JLabel birthDateLabel = new JLabel("Birthday: ");
            usernameField = new JTextField(TEXT_FIELD_WIDTH);
            nameField = new JTextField(TEXT_FIELD_WIDTH);
            countryField = new JTextField(TEXT_FIELD_WIDTH);
            provinceField = new JTextField(TEXT_FIELD_WIDTH);
            cityField = new JTextField(TEXT_FIELD_WIDTH);
            genderField = new JTextField(TEXT_FIELD_WIDTH);
            birthDateField = new JTextField(TEXT_FIELD_WIDTH);

            setBorder(new EmptyBorder(300, 300, 300, 300));
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();


//            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("<html><h2><strong>Add New Traveller</strong></h2><hr></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JPanel contentPane = new JPanel(new GridBagLayout());
            // place the username label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(usernameLabel, gbc);
            contentPane.add(usernameLabel,gbc);

            // place the text field for the username
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(usernameField, gbc);
            contentPane.add(usernameField,gbc);

            // place the name label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(nameLabel, gbc);
            contentPane.add(nameLabel,gbc);

            // place the text field for the name
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(nameField, gbc);
            contentPane.add(nameField,gbc);

            // place the country label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(countryLabel, gbc);
            contentPane.add(countryLabel,gbc);

            // place the text field for the country
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(countryField, gbc);
            contentPane.add(countryField,gbc);

            // place the province label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(provinceLabel, gbc);
            contentPane.add(provinceLabel,gbc);

            // place the text field for the province
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(provinceField, gbc);
            contentPane.add(provinceField,gbc);

            // place the city label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(cityLabel, gbc);
            contentPane.add(cityLabel,gbc);

            // place the text field for the city
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(cityField, gbc);
            contentPane.add(cityField,gbc);

            // place the gender label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(genderLabel, gbc);
            contentPane.add(genderLabel,gbc);

            // place the text field for the gender
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(genderField, gbc);
            contentPane.add(genderField,gbc);

            // place the birthday label
            gbc.gridwidth = GridBagConstraints.RELATIVE;
//            gb.setConstraints(birthDateLabel, gbc);
            contentPane.add(birthDateLabel,gbc);

            // place the text field for the birthday
            gbc.gridwidth = GridBagConstraints.REMAINDER;
//            gb.setConstraints(birthDateField, gbc);
            contentPane.add(birthDateField,gbc);

            JButton addTravellerButton = new JButton("Add New Traveller");
            contentPane.add(addTravellerButton, gbc);
            gbc.weighty = 1;
            add(contentPane,gbc);

            // register login button with action event handler
            addTravellerButton.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TravellerModel travellerModel = new TravellerModel(usernameField.getText(),nameField.getText(),countryField.getText(),provinceField.getText(),cityField.getText(),genderField.getText(),birthDateField.getText());
            delegate.insertNewTraveller(travellerModel);
        }


    }


}
