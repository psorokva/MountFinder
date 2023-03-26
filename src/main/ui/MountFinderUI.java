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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Mountain Finder application main window frame.
 * Contains all functionality related to displaying UI components.
 */
public class MountFinderUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final Color black = Color.BLACK;
    private final Color green = Color.GREEN;
    private final Color red = Color.RED;

    private JComboBox<String> cityDropdown;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JPanel mountainListPanel;
    private JPanel mountainDetailsPanel;
    private JTextField mtnNameField;
    private JTextField mtnLiftPriceField;
    private JRadioButton mtnRentalsAvailable;
    private JTextField mtnDistanceField;
    private JFrame checkBoxFrame;

    private final MountFinderApp mfApp;
    private final User user;
    private Mountain mountain;
    private MountainList mountainList;

    protected boolean mainMenuVisible = false;
    private boolean addNewMtnFormVisible = false;
    private boolean mtnListVisible = false;
    private boolean mtnDetailsMenuVisible = false;


    /**
     * Constructor sets up MountFinderUI starting screen.
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

    // MODIFIES: this
    // EFFECTS: opens city selection menu based on cities specified in MountFinderApp class
    private void openCitySelectionMenu() {
        JPanel citySelectionPanel = new JPanel();

        JLabel label = new JLabel("Select city");
        cityDropdown = new JComboBox<>(mfApp.getCities());
        citySelectionPanel.add(label);
        citySelectionPanel.add(cityDropdown);
        citySelectionPanel.add(new JButton(new ShowMenuOptionsAction()));

        pack();
        add(citySelectionPanel);
        setVisible(true);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
    }

    // EFFECTS: handles the action associated with showing menu options after city is selected
    private class ShowMenuOptionsAction extends AbstractAction {

        //Todo ask if need documentation
        ShowMenuOptionsAction() {
            super("Ok");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processShowMenuOptions();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays menu buttons if city is selected
    //          hides menu buttons if city is unselected
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
                remove(mountainListPanel);
                mainMenuVisible = false;
                mtnListVisible = false;
            }
            removeOtherPanels();
        }
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: hides Add New Mountain and Mountain Details panels
    private void removeOtherPanels() {
        if (addNewMtnFormVisible) {
            remove(tablePanel);
            addNewMtnFormVisible = false;
        }
        if (mtnDetailsMenuVisible) {
            remove(mountainDetailsPanel);
            mtnDetailsMenuVisible = false;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and displayed main menu buttons through buttonPanel
    private void showMainMenu() {
        buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new AddNewMountainAction()));
        buttonPanel.add(new JButton(new BrowseCurrentListAction()));
        buttonPanel.add(new JButton(new CompareTwoMtnsAction()));
        buttonPanel.add(new JButton(new LoadListFromFileAction()));
        buttonPanel.add(new JButton(new SaveToFileAction()));

        add(buttonPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: created form fields for adding new mountain
    @SuppressWarnings("methodlength") //Todo ask if ok
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

    // EFFECTS: handles the action associated with adding new mountain
    private class AddNewMountainAction extends AbstractAction {

        AddNewMountainAction() {
            super("Add new mountain");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            remove(mountainListPanel);
            removeOtherPanels();
            showAddNewMountainMenu();
            addNewMtnFormVisible = true;
        }
    }

    // EFFECTS: handles the action associated with saving newly added mountain in the list
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
            remove(tablePanel);
            displayCurrentList();
            repaint();
            revalidate();
        }
    }

    // MODIFIES: Mountain
    // EFFECTS: creates new mountain using data entered through Add New Mountain form
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

    // MODIFIES: MountainList
    // EFFECTS: adds newly created mountain to the mountain list
    private void addNewMountainToList(Mountain mountain) {
        mountainList.addMountain(mountain);
    }

    // EFFECTS: handles the action associated with displaying mountain list
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

    // MODIFIES: this
    // EFFECTS: displays current list of mountains
    private void displayCurrentList() {
        mountainListPanel = new JPanel();
        mountainListPanel.setLayout(new BoxLayout(mountainListPanel, BoxLayout.PAGE_AXIS));
        mountainListPanel.add(new JLabel("Current List:"));

        for (Mountain m : mountainList.getMountains()) {
            JButton btn = new JButton(new DisplayMtnInfoAction(m));
            btn.setText(m.getMtnName());
            mountainListPanel.add(btn);
        }
        add(mountainListPanel, BorderLayout.LINE_START);
        repaint();
        revalidate();
    }

    // EFFECTS: handles the action associated with displaying information for specific mountain
    private class DisplayMtnInfoAction extends AbstractAction {
        Mountain selectedMtn;

        DisplayMtnInfoAction(Mountain m) {
            selectedMtn = m;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            removeOtherPanels();
            if (!mtnDetailsMenuVisible) {
                processDisplayingMtnDetails(selectedMtn, black, black);
                mtnDetailsMenuVisible = true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays information about the specified mountain
    //          price and distance text color can be changed, default in the app is black
    private void processDisplayingMtnDetails(Mountain mountain, Color priceColor, Color distanceColor) {
        mountainDetailsPanel = new JPanel();
        mountainDetailsPanel.setLayout(new GridLayout(4, 2));
        mountainDetailsPanel.add(new JLabel("Name:"));
        mountainDetailsPanel.add(new JLabel(mountain.getMtnName()));
        mountainDetailsPanel.add(new JLabel("Lift ticket price:"));
        JLabel liftPriceLabel = new JLabel("$" + mountain.getLiftPrice());
        liftPriceLabel.setForeground(priceColor);
        mountainDetailsPanel.add(liftPriceLabel);
        mountainDetailsPanel.add(new JLabel(mountain.getRentalAvailabilityAnswer()));
        mountainDetailsPanel.add(new JLabel(""));
        mountainDetailsPanel.add(new JLabel("Distance from " + user.getUserHomeCity() + " :"));
        JLabel distanceLabel = new JLabel(mountain.getDistance(user.getUserHomeCity()) + " km");
        distanceLabel.setForeground(distanceColor);
        mountainDetailsPanel.add(distanceLabel);
        add(mountainDetailsPanel);
        repaint();
        revalidate();
    }

    // EFFECTS: handles the action associated with comparing two mountains
    private class CompareTwoMtnsAction extends AbstractAction {

        CompareTwoMtnsAction() {
            super("Compare two mountains");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            removeOtherPanels();
            loadCheckBoxFrame();

        }
    }

    // MODIFIES: this
    // EFFECTS: loads JCheckBox for each mountain in the list
    private void loadCheckBoxFrame() {
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        checkBoxFrame = new JFrame("Compare");
        JLabel compareMtnsLabel = new JLabel("Select two mountains to compare:");
        compareMtnsLabel.setBounds(50, 70, 300, 20);
        checkBoxFrame.add(compareMtnsLabel);
        for (int i = 0; i < mountainList.getMountains().size(); i++) {
            JCheckBox checkBox = new JCheckBox(mountainList.getMtnAtIndex(i).getMtnName());
            checkBox.setBounds(50, 100 + (30 * i), 150, 20);
            checkBoxFrame.add(checkBox);
            checkBoxes.add(checkBox);
        }
        JButton compareBtn = new JButton("Compare");
        compareBtn.setBounds(50, 100 + (30 * mountainList.size()), 100, 20);
        compareBtn.setAction(new PerformComparisonAction(checkBoxes));
        checkBoxFrame.add(compareBtn);
        checkBoxFrame.setSize(400, 400);
        checkBoxFrame.setLayout(null);
        checkBoxFrame.setVisible(true);
        add(checkBoxFrame);
    }

    // EFFECTS: handles the action associated with comparing two selected mountains
    private class PerformComparisonAction extends AbstractAction {
        ArrayList<JCheckBox> checkBoxes;

        PerformComparisonAction(ArrayList<JCheckBox> checkBoxes) {
            super("Compare");
            this.checkBoxes = checkBoxes;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            ArrayList<Mountain> selectedMtns = new ArrayList<>();
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    String selectedMtnName = checkBox.getText();
                    selectedMtns.add(mountainList.getMtnByName(selectedMtnName));
                }
            }
            Mountain m1 = selectedMtns.get(0);
            Mountain m2 = selectedMtns.get(1);

            processDisplayingMtnDetails(m1, changePriceColor(m1, m2).get(0), changeDistanceColor(m1, m2).get(0));
            processDisplayingMtnDetails(m2, changePriceColor(m1, m2).get(1), changeDistanceColor(m1, m2).get(1));
        }
    }

    // EFFECTS: helper method to compare lift prices and re-color them accordingly
    private ArrayList<Color> changePriceColor(Mountain m1, Mountain m2) {
        ArrayList<Color> priceColors = new ArrayList<>();
        Color mtn1Price = black;
        Color mtn2Price = black;

        if (m1.getLiftPrice() < m2.getLiftPrice()) {
            mtn1Price = green;
            mtn2Price = red;
        } else if (m1.getLiftPrice() > m2.getLiftPrice()) {
            mtn1Price = red;
            mtn2Price = green;
        }

        priceColors.add(mtn1Price);
        priceColors.add(mtn2Price);

        return priceColors;
    }

    // EFFECTS: helper method to compare distances and re-color them accordingly
    private ArrayList<Color> changeDistanceColor(Mountain m1, Mountain m2) {
        ArrayList<Color> distColors = new ArrayList<>();
        String city = user.getUserHomeCity();
        Color mtn1Dist = black;
        Color mtn2Dist = black;

        if (m1.getDistance(city) < m2.getDistance(city)) {
            mtn1Dist = green;
            mtn2Dist = red;
        } else if (m1.getDistance(city) > m2.getDistance(city)) {
            mtn1Dist = red;
            mtn2Dist = green;
        }

        distColors.add(mtn1Dist);
        distColors.add(mtn2Dist);

        return distColors;
    }

    // EFFECTS: handles the action associated with loading mountain list from file
    private class LoadListFromFileAction extends AbstractAction {

        LoadListFromFileAction() {
            super("Load list from file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processLoadingDataFromFile();
        }
    }

    // MODIFIES: this, MountainList
    // EFFECTS: loads mountain list from saved file
    private void processLoadingDataFromFile() {
        mountainList = mfApp.loadSavedList();
        JOptionPane popup = new JOptionPane("Loaded List 1!");
        // Adapted from https://stackoverflow.com/questions/5966005/create-a-plain-message-box-that-disappears-after-a-few-seconds-in-java
        final JDialog dialog = popup.createDialog("Success");
        Timer timer = new Timer(1200, e -> dialog.setVisible(false));
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
        displayCurrentList();
    }

    // EFFECTS: handles the action associated with saving current mountain list to file
    private class SaveToFileAction extends AbstractAction {

        SaveToFileAction() {
            super("Save list to file");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            processSavingDataToFile();
        }

    }

    // MODIFIES: this
    // EFFECTS: saved current mountain list to file
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

    // EFFECTS: helper function to restrict input only to numbers for price and distance fields
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




