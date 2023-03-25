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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents application's main window frame.
 */
public class MountFinderUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JComboBox<String> cityDropdown;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JPanel mountainListPanel;
    private JPanel mountainDetailsPanel;
    private JTextField mtnNameField;
    private JTextField mtnLiftPriceField;
    private JRadioButton mtnRentalsAvailable;
    private JTextField mtnDistanceField;

    private final MountFinderApp mfApp;
    private final User user;
    private Mountain mountain;
    private MountainList mountainList;

    protected boolean mainMenuVisible = false;
    private boolean addNewMtnFormVisible = false;
    private boolean mtnListVisible = false;
    private boolean mtnDetailsMenuVisible = false;


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
        JLabel label = new JLabel("Select city");
        cityDropdown = new JComboBox<>(mfApp.getCities());
        add(label);
        add(cityDropdown);
        add(new JButton(new ShowMenuOptionsAction()));

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("methodlength")
    private void showAddNewMountainMenu() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(5, 1));
        mtnNameField = new JTextField(10);
        mtnLiftPriceField = new JTextField(10);
        acceptOnlyNumbers(mtnLiftPriceField);
        ButtonGroup mtnRentalsOptions = new ButtonGroup();
        mtnRentalsAvailable = new JRadioButton("Available", null, true);
        JRadioButton mtnRentalsNotAvailable = new JRadioButton("Not Available");
        mtnRentalsOptions.add(mtnRentalsAvailable);
        mtnRentalsOptions.add(mtnRentalsNotAvailable);
        mtnDistanceField = new JTextField(10);
        acceptOnlyNumbers(mtnDistanceField);

        tablePanel.add(new JLabel("Name:"));
        tablePanel.add(mtnNameField);
        tablePanel.add(new JLabel("Lift price:"));
        tablePanel.add(mtnLiftPriceField);
        tablePanel.add(new JLabel("Rentals availability:"));
        tablePanel.add(new JLabel(""));
        tablePanel.add(mtnRentalsAvailable);
        tablePanel.add(mtnRentalsNotAvailable);
        tablePanel.add(new JLabel("Distance in km from city:"));
        tablePanel.add(mtnDistanceField);
        tablePanel.add(new JButton(new SaveNewMountainAction()));
        add(tablePanel);
        repaint();
        revalidate();
    }

    private void processShowMenuOptions() {
        String selected = (String) this.cityDropdown.getSelectedItem();
        assert selected != null;
        user.setUserHomeCity(selected);
        if (!selected.equals("")) {
            if (!mainMenuVisible) {
                showMainMenu();
                mainMenuVisible = true;
            }
            // if city is unselected
        } else {
            if (mainMenuVisible) {
                remove(buttonPanel);
                mainMenuVisible = false;
            }
            removeOtherPanels();
        }
        repaint();
        revalidate();
    }

    private void removeOtherPanels() {
        if (addNewMtnFormVisible) {
            remove(tablePanel);
            addNewMtnFormVisible = false;
        }
        if (mtnListVisible) {
            remove(mountainListPanel);
            mtnListVisible = false;
        }
        if (mtnDetailsMenuVisible) {
            remove(mountainDetailsPanel);
            mtnDetailsMenuVisible = false;
        }
    }

    private void showMainMenu() {
        buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new AddNewMountainAction()));
        buttonPanel.add(new JButton(new BrowseCurrentListAction()));
        buttonPanel.add(new JButton(new LoadListFromFileAction()));
        buttonPanel.add(new JButton(new SaveToFileAction()));
        //todo add grey-out if list is empty

        add(buttonPanel);
    }

    private class ShowMenuOptionsAction extends AbstractAction {

        ShowMenuOptionsAction() {
            super("Ok");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processShowMenuOptions();
        }
    }


    private class AddNewMountainAction extends AbstractAction {

        AddNewMountainAction() {
            super("Add new mountain");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            removeOtherPanels();
            if (!addNewMtnFormVisible) {
                showAddNewMountainMenu();
                addNewMtnFormVisible = true;
            }
        }
    }

    private class SaveNewMountainAction extends AbstractAction {

        SaveNewMountainAction() {
            super("Save mountain");
        }

        //Image credit: <a href="https://www.freepik.com/free-vector/cute-astronaut-playing-snowboard-cartoon-vector-icon-illustration-science-sport-icon-isolated_33777470.htm#query=snowboarding&position=2&from_view=keyword&track=sph#position=2&query=snowboarding">Image by catalyststuff</a> on Freepik
        @Override
        public void actionPerformed(ActionEvent evt) {
            processSaveNewMountain();
            addNewMountainToList(mountain);
            JOptionPane popup = new JOptionPane("New mountain added!");
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(new File("data/images/6700_4_06.jpg"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image image = bufferedImage.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(image);
            popup.setIcon(icon);
            final JDialog dialog = popup.createDialog("Success");
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
            removeOtherPanels();
            if (!mtnListVisible) {
                displayCurrentList();
                mtnListVisible = true;
            }
        }
    }

    private void displayCurrentList() {
        mountainListPanel = new JPanel();
        mountainListPanel.setLayout(new BoxLayout(mountainListPanel, BoxLayout.PAGE_AXIS));
        for (Mountain m : mountainList.getMountains()) {
            JButton btn = new JButton(new DisplayMtnInfoAction(m));
            btn.setText(m.getMtnName());
            mountainListPanel.add(btn);
        }
        add(mountainListPanel);
        repaint();
        revalidate();
    }

    private class DisplayMtnInfoAction extends AbstractAction {
        Mountain selectedMtn;

        DisplayMtnInfoAction(Mountain m) {
            selectedMtn = m;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            removeOtherPanels();
            if (!mtnDetailsMenuVisible) {
                processDisplayingMtnDetails(selectedMtn);
                mtnDetailsMenuVisible = true;
            }
        }
    }

    private void processDisplayingMtnDetails(Mountain mountain) {
        mountainDetailsPanel = new JPanel();
        mountainDetailsPanel.add(new JLabel("Name:"));
        mountainDetailsPanel.add(new JLabel(mountain.getMtnName()));
        mountainDetailsPanel.add(new JLabel("Lift ticket price:"));
        mountainDetailsPanel.add(new JLabel("$" + mountain.getLiftPrice()));
        mountainDetailsPanel.add(new JLabel(mountain.getRentalAvailabilityAnswer()));
        mountainDetailsPanel.add(new JLabel("Distance from " + user.getUserHomeCity() + " :"));
        mountainDetailsPanel.add((new JLabel(mountain.getDistance(user.getUserHomeCity()) + " km")));
        add(mountainDetailsPanel);
        repaint();
        revalidate();
    }

    private class LoadListFromFileAction extends AbstractAction {

        LoadListFromFileAction() {
            super("Load list from file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processLoadingDataFromFile();
        }
    }

    private void processLoadingDataFromFile() {
        mountainList = mfApp.loadSavedList();
        JOptionPane popup = new JOptionPane("Loaded List 1!");
        // Adapted from https://stackoverflow.com/questions/5966005/create-a-plain-message-box-that-disappears-after-a-few-seconds-in-java
        final JDialog dialog = popup.createDialog("Success");
        Timer timer = new Timer(1200, e -> dialog.setVisible(false));
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    private class SaveToFileAction extends AbstractAction {

        SaveToFileAction() {
            super("Save list to file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processSavingDataToFile();
        }
    }

    private void processSavingDataToFile() {
        mfApp.handleSave(mountainList);
        JOptionPane popup = new JOptionPane("Saved as List 1!");
        // Adapted from https://stackoverflow.com/questions/5966005/create-a-plain-message-box-that-disappears-after-a-few-seconds-in-java
        final JDialog dialog = popup.createDialog("Success");
        Timer timer = new Timer(1200, e -> dialog.setVisible(false));
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    // Adapted from: https://stackoverflow.com/questions/20541230/allow-only-numbers-in-jtextfield
    private void acceptOnlyNumbers(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            final Pattern regEx = Pattern.compile("\\d*");

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
}
