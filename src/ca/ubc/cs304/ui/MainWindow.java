package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.MainWindowDelegate;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame{

    // delegate
    private MainWindowDelegate delegate;
    public MainWindow() {
        super("Main Window");
    }

    public void showFrame(MainWindowDelegate delegate) {
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

    public class MenuPane extends JPanel {

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
            buttons.add(new JButton("See All Travellers"), gbc);
            buttons.add(new JButton("See All Groups"), gbc);
            buttons.add(new JButton("See All Interests"), gbc);
            gbc.weighty = 1;
            add(buttons, gbc);
        }

    }







}
