package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AllTravellersDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllTravellers extends JFrame {

    private AllTravellersDelegate delegate;
    public AllTravellers() { super("List of All Travellers");}

    public void showFrame(AllTravellersDelegate delegate) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new AllTravellers.MenuPane());
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

            add(new JLabel("<html><h2><strong>List of All Travellers</strong></h2><hr></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;



        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }


    }

}
