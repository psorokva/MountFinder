package ui;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents application's main window frame.
 */
public class MountFinderUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JLabel label;
    private JComboBox cityDropdown;
    private JButton okBtn;
    private JButton addMtnBtn;
    private JButton browseCurrentListBtn;
    private JButton loadListBtn;

    private MountFinderApp mfApp;
    private User user;
    private String[] state = { "Start", "Main", "ListView" };
//    private String currentState;
    private Boolean citySelected = false;
    protected Boolean mainMenuVisible = false;


    /**
     * Constructor sets up _______. Todo
     */
    public MountFinderUI() {
        super("MountFinder App");
        mfApp = new MountFinderApp();
        user = new User();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        openCitySelectionMenu();
//        switch (currentState) {
//            case "Start":
//                openCitySelectionMenu();
//                break;
//            case "Main":
//                openMainMenu();
//                break;
//        }

        pack();
        setLocationRelativeTo(null);
//        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void openCitySelectionMenu() {
        label = new JLabel("Select city");
        cityDropdown = new JComboBox(mfApp.getCities());
        cityDropdown.setActionCommand("ShowMenuOptions");
        cityDropdown.addActionListener(this);
        add(label);
        add(cityDropdown);
//        okBtn = new JButton("Ok");
//        okBtn.setActionCommand("ShowMenuOptions");
//        okBtn.addActionListener(this);
//        add(okBtn);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
    }

    //This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ShowMenuOptions")) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String selected = comboBox.getSelectedItem().toString();
            if (!selected.equals("")) {
                citySelected = true;
                user.setUserHomeCity(selected);
//                JLabel cityLabel = new JLabel(selected + " selected");
//                add(cityLabel);
                if (!mainMenuVisible) {
                    showMainMenu();
                    mainMenuVisible = true;
                }
            } else {
                citySelected = false;
                remove(addMtnBtn);
                remove(browseCurrentListBtn);
                remove(loadListBtn);
                mainMenuVisible = false;
                repaint();
                revalidate();
            }
        }
    }

    private void showMainMenu() {
        addMtnBtn = new JButton("Add new mountain");
        browseCurrentListBtn = new JButton("Browse current list");
        loadListBtn = new JButton("Load list");

        add(addMtnBtn);
        add(browseCurrentListBtn);
        add(loadListBtn);
    }


}
