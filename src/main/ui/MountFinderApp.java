package ui;

import model.Mountain;
import model.MountainList;
import model.User;

import java.util.Scanner;

/**
 * Mountain Finder application is a console-based application. It displays options to the user
 * and receives user input. User can browse through the existing list of mountains or can add
 * a new mountain to the list (more details in MountainList class).
 * - First menu asks user to select home city from available options to later calculate
 * the distance to the mountain from there.
 * - User can quit application by selecting "q" command.
 */
public class MountFinderApp {
    private MountainList mtnList;
    private Scanner input;
    private User user;

    // EFFECTS: runs the application
    public MountFinderApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            System.out.print("Choice: ");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand1(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: initializes the values
    private void init() {
        user = new User();
        displayStartMenu();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        mtnList = new MountainList("List 1");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand1(String command) {
        switch (command) {
            case "v":
                user.setUserHomeCity("Vancouver");
                displayMtnSelectionMenu();
                break;
            case "r":
                user.setUserHomeCity("Richmond");
                displayMtnSelectionMenu();
                break;
            case "u":
                user.setUserHomeCity("UBC");
                displayMtnSelectionMenu();
                break;
            case "h":
                displayStartMenu();
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
            case "n":
                handleAddingNewMountain();
                break;
            case "c":
                loadCurrentMtnList();
                break;
            case "b":
                displayMtnSelectionMenu();
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
        System.out.print("Choice: ");
        String selection = input.next();
        int index = Integer.parseInt(selection);
        while (index >= mtnList.size()) {
            System.out.println("Selection not valid...");
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
        displayMtnSelectionMenu();
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
            command = input.next();
        }
        if (command.equals("a")) {
            newMountain.makeRentalsAvailable();
        } else {
            newMountain.makeRentalsNotAvailable();
        }
        System.out.println(newMountain.rentalsAvailability());
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
    private void displayStartMenu() {
        System.out.println("\nWelcome to Mountain Finder!");
        System.out.println("\nSelect your home city:");
        System.out.println("\tv -> Vancouver");
        System.out.println("\tr -> Richmond");
        System.out.println("\tu -> UBC");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays main menu of options to user
    private void displayMtnSelectionMenu() {
        System.out.println("\nSelected home city: " + user.getUserHomeCity());
        System.out.println("\th -> Change home city");
        System.out.println("\nAdd a new mountain to the list:");
        System.out.println("\tn -> Add new mountain");
        System.out.println("\nOr select one of the existing mountains:");
        System.out.println("\tc -> Browse current list");
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
        System.out.println("\t" + mountain.rentalsAvailability());
        System.out.print("\tDistance from home is ");
        System.out.printf("%.1f", mountain.getDistance(user.getUserHomeCity()));
        System.out.println(" km");
        System.out.println("\tb -> Select another mountain");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

}
