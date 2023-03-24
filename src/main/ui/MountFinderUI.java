package ui;

import model.Mountain;
import model.MountainList;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents application's main window frame.
 */
public class MountFinderUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JLabel label;
    private JComboBox<String> cityDropdown;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JTextField mtnNameField;
    private JTextField mtnLiftPriceField;
    private JRadioButton mtnRentalsAvailable;
    private JTextField mtnDistanceField;

    private MountFinderApp mfApp;
    private User user;
    private Mountain mountain;
    private MountainList mountainList;
    private Boolean citySelected = false;
    protected Boolean mainMenuVisible = false;


    /**
     * Constructor sets up _______. Todo
     */
    public MountFinderUI() {
        super("MountFinder App");
        mfApp = new MountFinderApp();
        user = new User();
        mountainList = new MountainList("List 1");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        openCitySelectionMenu();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void openCitySelectionMenu() {
        label = new JLabel("Select city");
        cityDropdown = new JComboBox<>(mfApp.getCities());
        add(label);
        add(cityDropdown);
        add(new JButton(new ShowMenuOptionsAction()));

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
    }

//    This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("ShowMenuOptions")) {
//            processShowMenuOptions(e);
//        }
    }

    private void showAddNewMountainMenu() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(5,1));
        JLabel mtnNameLabel = new JLabel("Name:");
        mtnNameField = new JTextField(10);
        JLabel mtnLiftPriceLabel = new JLabel("Lift price:");
        mtnLiftPriceField = new JTextField(10);
        // Adapted from: https://stackoverflow.com/questions/20541230/allow-only-numbers-in-jtextfield
        acceptOnlyNumbers(mtnLiftPriceField);
        JLabel mtnRentalsLabel = new JLabel("Rentals availability:");
        JLabel emptyLabel = new JLabel("");
        ButtonGroup mtnRentalsOptions = new ButtonGroup();
        mtnRentalsAvailable = new JRadioButton("Available", null, true);
        JRadioButton mtnRentalsNotAvailable = new JRadioButton("Not Available");
        mtnRentalsOptions.add(mtnRentalsAvailable);
        mtnRentalsOptions.add(mtnRentalsNotAvailable);
        JLabel mtnDistanceLabel = new JLabel("Distance in km from city:");
        mtnDistanceField = new JTextField(10);
        acceptOnlyNumbers(mtnDistanceField);

        tablePanel.add(mtnNameLabel);
        tablePanel.add(mtnNameField);
        tablePanel.add(mtnLiftPriceLabel);
        tablePanel.add(mtnLiftPriceField);
        tablePanel.add(mtnRentalsLabel);
        tablePanel.add(emptyLabel);
        tablePanel.add(mtnRentalsAvailable);
        tablePanel.add(mtnRentalsNotAvailable);
        tablePanel.add(mtnDistanceLabel);
        tablePanel.add(mtnDistanceField);
        tablePanel.add(new JButton(new SaveNewMountainAction()));
        add(tablePanel);
        repaint();
        revalidate();
    }

    private void acceptOnlyNumbers(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    private void processShowMenuOptions() {
        String selected = (String) this.cityDropdown.getSelectedItem();
        assert selected != null;
        if (!selected.equals("")) {
            citySelected = true;
            user.setUserHomeCity(selected);
            if (!mainMenuVisible) {
                showMainMenu();
                mainMenuVisible = true;
            }
        } else {
            citySelected = false;
            user.setUserHomeCity("");
            remove(buttonPanel);
            mainMenuVisible = false;
        }
        repaint();
        revalidate();
    }

    private void showMainMenu() {
        buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new AddNewMountainAction()));
        buttonPanel.add(new JButton(new BrowseCurrentListAction()));
        buttonPanel.add(new JButton(new LoadListFromFileAction()));

        add(buttonPanel);
    }

    private class ShowMenuOptionsAction extends AbstractAction {

        ShowMenuOptionsAction() {
            super("Ok");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
//            JOptionPane popup = new JOptionPane("City selected");
//            final JDialog dialog = popup.createDialog("title");
//            Timer timer = new Timer(1500, new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    dialog.setVisible(false);
//                }
//            });
//            timer.setRepeats(false);
//            timer.start();
//            dialog.setVisible(true);
//            popup.showMessageDialog(null, "City selected");
            processShowMenuOptions();
        }
    }


    private class AddNewMountainAction extends AbstractAction {

        AddNewMountainAction() {
            super("Add new mountain");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            showAddNewMountainMenu();
        }
    }

    private class SaveNewMountainAction extends AbstractAction {

        SaveNewMountainAction() {
            super("Save mountain");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processSaveNewMountain();
            addNewMountainToList(mountain);
            JOptionPane popup = new JOptionPane("New mountain added!");
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(new File("data/images/6700_4_06.jpg"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = bufferedImage.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(image);
            popup.setIcon(icon);
            final JDialog dialog = popup.createDialog("title");
            // Adapted from https://stackoverflow.com/questions/5966005/create-a-plain-message-box-that-disappears-after-a-few-seconds-in-java
            //Uncomment to make popup disappear after 3 sec
//            Timer timer = new Timer(3000, e -> dialog.setVisible(false));
//            timer.setRepeats(false);
//            timer.start();
            dialog.setVisible(true);
            remove(tablePanel);//todo Display list
            repaint();
            revalidate();
        }
    }

    private void processSaveNewMountain() {
        String mtnName = String.valueOf(mtnNameField.getText());
        double liftPrice = Double.parseDouble(String.valueOf(mtnLiftPriceField.getText()));
        boolean rentalAvailable = mtnRentalsAvailable.getAccessibleContext() != null;
        double distance = Double.parseDouble(String.valueOf(mtnDistanceField.getText()));

        mountain = new Mountain(mtnName);
        mountain.addLiftPrice(liftPrice);
        if (rentalAvailable) {
            mountain.makeRentalsAvailable();
        } else {
            mountain.makeRentalsNotAvailable();
        }
        mountain.setDistance(user.getUserHomeCity(), distance);
    }

    private void addNewMountainToList(Mountain mountain) {
        mountainList.addMountain(mountain);
    }

    private class BrowseCurrentListAction extends AbstractAction {

        BrowseCurrentListAction() {
            super("Browse current list");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            displayCurrentList();
        }
    }

    private void displayCurrentList() {
        JPanel mountainListPanel = new JPanel();
        mountainListPanel.setLayout(new BoxLayout(mountainListPanel, BoxLayout.PAGE_AXIS));
        for (Mountain m : mountainList.getMountains()) {
            mountainListPanel.add(new JButton(m.getMtnName()));
        }
        add(mountainListPanel);
        repaint();
        revalidate();
    }

    private class LoadListFromFileAction extends AbstractAction {

        LoadListFromFileAction() {
            super("Load list");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            //TODO
        }
    }
}
