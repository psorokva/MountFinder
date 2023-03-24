package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingMenuUI extends JFrame implements ActionListener {
    private JLabel label;

    private String[] cities = {"Vancouver", "UBC", "Richmond"};

    public StartingMenuUI() {
        label = new JLabel("Select city");
        JComboBox cityDropdown = new JComboBox(cities);
        cityDropdown.addActionListener(this);
        JButton btn = new JButton("Start App");
        btn.setActionCommand("openMainMenu");
        btn.addActionListener(this);

        add(label);
        add(cityDropdown);
        add(btn);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
    }

    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("openMainMenu")) {
            //todo
        }
    }

}
