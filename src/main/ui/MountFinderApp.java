package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Mountain Finder application is a console-based application. It displays options to the user
 * and receives user input. User can browse through the existing list of mountains or can add
 * a new mountain to the list (more details in MountainList class).
 * - First menu asks user to select home city from available options to later calculate
 * the distance to the mountain from there.
 * - User can quit application by selecting "q" command.
 */
public class MountFinderApp implements LogPrinter {
    private static final String JSON_STORE = "./data/mountainInfo.json";
    private MountainList mtnList;
    private Scanner input;
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private String[] cities;

    private boolean keepGoing;

    // EFFECTS: runs the application
    public MountFinderApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        init();
        //Uncomment to enable console functionality
//        startConsole();
    }

    // MODIFIES: this
    // EFFECTS: opens console window and processes input
    private void startConsole() {
        keepGoing = true;
        String command;
        while (keepGoing) {
            System.out.print("Choice: ");
            command = input.next();
            command = command.toLowerCase();
            processCommand1(command);
        }
        System.out.println("\nGoodbye!");
    }


    // EFFECTS: initializes the values
    private void init() {
        user = new User();
        cities = new String[]{"", "Vancouver", "UBC", "Richmond"};
        //Uncomment to enable console functionality
//        displayCitySelectionMenu();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        mtnList = new MountainList("List 1");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand1(String command) {
        switch (command) {
            case "v":
                user.setUserHomeCity("Vancouver");
                displayStartingMenu();
                break;
            case "r":
                user.setUserHomeCity("Richmond");
                displayStartingMenu();
                break;
            case "u":
                user.setUserHomeCity("UBC");
                displayStartingMenu();
                break;
            case "h":
                displayCitySelectionMenu();
                break;
            default:
                processCommand2(command);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand2(String command) {
        switch (command) {
            case "a":
                handleAddingNewMountain();
                break;
            case "c":
                if (mtnList.size() == 0) {
                    System.out.println("No mountains in the list");
                    displayStartingMenu();
                } else {
                    loadCurrentMtnList();
                }
                break;
            case "o":
                loadCurrentMtnList();
                break;
            case "q":
                askToSaveMenu();
                printLog();
                break;
            default:
                processCommand3(command);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand3(String command) {
        switch (command) {
            case "m":
                displayMainMenu();
                break;
            case "l":
                loadSavedList();
                displayMainMenu();
                break;
            case "s":
                handleSave(mtnList);
                displayMainMenu();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads current mountain list and collects user selection
    private void loadCurrentMtnList() {
        System.out.println("\nSelect a mountain to browse:");
        for (int i = 0; i < mtnList.size(); i++) {
            Mountain currentMtn = mtnList.getMtnAtIndex(i);
            System.out.println("\t" + i + " -> " + currentMtn.getMtnName());
        }
        System.out.println("\nReturn to main menu");
        System.out.println("\tm -> Go back to main menu");
        System.out.print("Choice: ");
        String selection = input.next();
        if (selection.equals("m")) {
            displayMainMenu();
        } else {
            try {
                handleMtnSelection(selection);
            } catch (NumberFormatException e) {
                System.out.println("Selection not valid...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads selected mountain if selection is valid
    private void handleMtnSelection(String selection) {
        int index = Integer.parseInt(selection);
        while (index >= mtnList.size()) {
            System.out.println("Selection not valid...");
            System.out.print("Choice: ");
            selection = input.next();
            index = Integer.parseInt(selection);
        }
        Mountain selectedMtn = mtnList.getMtnAtIndex(index);
        browseMountain(selectedMtn.getMtnName());
    }

    // MODIFIES: this, MountainList
    // EFFECTS: handles adding new mountain to the list
    private void handleAddingNewMountain() {
        addNewMountainNameMenu();
        String mountainName = input.next();
        Mountain newMountain = new Mountain(mountainName);
        mtnList.addMountain(newMountain);
        System.out.println("You added " + mountainName + " mountain");

        addNewMtnLiftPrice(newMountain);
        addNewMtnRentals(newMountain);
        addNewMtnDistanceFromHome(newMountain);
        System.out.println(mountainName + " mountain added successfully!");
        displayMainMenu();
    }

    // MODIFIES: Mountain, MountainList
    // EFFECTS: adds value for lift price for new mountain in $
    //          if input includes non-numeric values - removes those values
    //          if remainder is an empty string - asks for new input that is a number
    private void addNewMtnLiftPrice(Mountain newMountain) {
        addNewMountainPriceMenu();
        String inputValue = input.next();
        inputValue = inputValue.replaceAll("[^\\d.]", "");
        while (inputValue.equals("")) {
            System.out.println("Please enter a number");
            inputValue = input.next();
            inputValue = inputValue.replaceAll("[^\\d.]", "");
        }
        double liftPrice = Double.parseDouble(inputValue);
        newMountain.addLiftPrice(liftPrice);
        System.out.println("Lift price is $" + liftPrice);
    }

    // MODIFIES: Mountain, MountainList
    // EFFECTS: sets rental availability for new mountain
    private void addNewMtnRentals(Mountain newMountain) {
        addNewMountainRentalsMenu();
        String command = input.next();
        while (!command.equalsIgnoreCase("a")
                && !command.equalsIgnoreCase("f")) {
            System.out.println("Invalid option");
            System.out.print("Choice: ");
            command = input.next();
        }
        if (command.equals("a")) {
            newMountain.makeRentalsAvailable();
        } else {
            newMountain.makeRentalsNotAvailable();
        }
        System.out.println(newMountain.getRentalAvailabilityAnswer());
    }

    // MODIFIES: Mountain, MountainList, Distance
    // EFFECTS: sets distance from user's selected home city to the mountain
    //          if input includes non-numeric values - removes those values
    //          if remainder is an empty string - asks for new input that is a number
    private void addNewMtnDistanceFromHome(Mountain newMountain) {
        addNewMountainDistanceMenu();
        String city = user.getUserHomeCity();
        String inputValue = input.next();
        inputValue = inputValue.replaceAll("[^\\d.]", "");
        while (inputValue.equals("")) {
            System.out.println("Please enter a number");
            inputValue = input.next();
            inputValue = inputValue.replaceAll("[^\\d.]", "");
        }
        double distance = Double.parseDouble(inputValue);
        newMountain.setDistance(user.getUserHomeCity(), distance);
        System.out.println("The distance is " + newMountain.getDistance(city) + " km from " + city);
    }

    // EFFECTS: displays menu of city options to user
    private void displayCitySelectionMenu() {
        System.out.println("\nWelcome to Mountain Finder!");
        System.out.println("\nSelect your home city:");
        System.out.println("\tv -> Vancouver");
        System.out.println("\tr -> Richmond");
        System.out.println("\tu -> UBC");
        System.out.println("\nQuit:");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays main menu of options to user
    private void displayStartingMenu() {
        System.out.println("\nSelected home city: " + user.getUserHomeCity());
        System.out.println("\th -> Change home city");
        System.out.println("\nAdd a new mountain to the list:");
        System.out.println("\ta -> Add new mountain");
        System.out.println("\nBrowse current list of mountains:");
        System.out.println("\tc -> Browse current list");
        System.out.println("\nOr load mountains from the existing file:");
        System.out.println("\tl -> Load");
        System.out.println("\nQuit:");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelected home city: " + user.getUserHomeCity());
        System.out.println("\th -> Change home city");
        System.out.println("\nAdd a new mountain to the list:");
        System.out.println("\ta -> Add new mountain");
        System.out.println("\nBrowse current list of mountains:");
        System.out.println("\tc -> Browse current list");
        System.out.println("\nSave current list of mountains:");
        System.out.println("\ts -> Save");
        System.out.println("\nQuit:");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: displays prompt text for adding new mountain name
    private void addNewMountainNameMenu() {
        System.out.print("Enter the name of the mountain: ");
    }

    // EFFECTS: displays prompt text for adding new mountain lift price
    private void addNewMountainPriceMenu() {
        System.out.print("Enter the lift ticket price: ");
    }

    // EFFECTS: displays prompt text for adding new mountain rental availability
    private void addNewMountainRentalsMenu() {
        System.out.println("Select rental availability:");
        System.out.println("\ta -> Rentals are available");
        System.out.println("\tf -> Rentals are not available");
        System.out.print("Choice: ");
    }

    // EFFECTS: displays prompt text for adding new mountain distance from home
    private void addNewMountainDistanceMenu() {
        System.out.print("Enter distance in km from " + user.getUserHomeCity() + ": ");
    }

    // MODIFIES: this
    // EFFECTS: displays prompt text for options to save before quitting
    private void askToSaveMenu() {
        System.out.println("\nWould you like to save current list?");
        System.out.println("\ty -> Yes, save and quit");
        System.out.println("\tn -> No, quit without saving");
        System.out.print("Choice: ");
        String command = input.next();
        if (command.equals("y")) {
            handleSave(mtnList);
            keepGoing = false;
        } else if (command.equals("n")) {
            keepGoing = false;
        } else {
            processCommand1(command);
        }
    }

    // EFFECTS: displays all information for selected mountain
    //          if user changed home city after adding new mountain,
    //          asks to enter distance from this home city to the mountain
    private void browseMountain(String mountainName) {
        Mountain mountain = mtnList.getMtnByName(mountainName);
        if (mountain.getDistance(user.getUserHomeCity()) == 0) {
            addNewMtnDistanceFromHome(mountain);
        }
        System.out.println(mountainName + " mountain:");
        System.out.println("\tLift ticket price: $" + mountain.getLiftPrice());
        System.out.println("\t" + mountain.getRentalAvailabilityAnswer());
        System.out.print("\tDistance from home is ");
        System.out.printf("%.1f", mountain.getDistance(user.getUserHomeCity()));
        System.out.println(" km");
        System.out.println("\to -> Select another mountain");
        System.out.println("\tm -> Go back to main menu");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

    // Citation: adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: saves the mountain list to file
    public void handleSave(MountainList mtnList) {
        try {
            jsonWriter.open();
            jsonWriter.write(mtnList);
            jsonWriter.close();
            System.out.println("Saved " + mtnList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Citation: adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads mountain list from file
    public MountainList loadSavedList() {
        try {
            mtnList = jsonReader.read();
            System.out.println("Loaded " + mtnList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return mtnList;
    }

    public String[] getCities() {
        return cities;
    }

    /**
     * EFFECTS: Prints the log of events
     * Adapted from <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
     */
    @Override
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next);
        }
    }
}
