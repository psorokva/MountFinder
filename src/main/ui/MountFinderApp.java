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
    public MountFinderApp() throws Exception {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() throws Exception {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
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

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand1(String command) throws Exception {
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

    private void processCommand2(String command) throws Exception {
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

    private void loadCurrentMtnList() throws Exception {
        System.out.println("\nSelect a mountain to browse:");
        for (int i = 0; i < mtnList.size(); i++) {
            Mountain currentMtn = mtnList.get(i);
            System.out.println("\t" + i + " -> " + currentMtn.getMtnName());
        }
        String selection = input.next();
        int index = Integer.parseInt(selection);
        Mountain selectedMtn = mtnList.get(index);
        browseMountain(selectedMtn.getMtnName());
    }

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

    private void addNewMtnDistanceFromHome(Mountain newMountain) {
        addNewMountainDistanceMenu();
        String inputValue = input.next();
        inputValue = inputValue.replaceAll("[^\\d.]", "");
        while (inputValue.equals("")) {
            System.out.println("Please enter a number");
            inputValue = input.next();
            inputValue = inputValue.replaceAll("[^\\d.]", "");
        }
        double distance = Double.parseDouble(inputValue);
        newMountain.setDistance(distance);
        System.out.println("The distance is " + distance + " km from " + user.getUserHomeCity());
    }

    // EFFECTS: initializes the values
    private void init() {
        user = new User();
        displayStartMenu();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        mtnList = new MountainList();
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

    // EFFECTS: displays menu of mountain options to user
    private void displayMtnSelectionMenu() {
        System.out.println("\nSelected home city: " + user.getUserHomeCity());
        System.out.println("\th -> Change home city");
        System.out.println("\nAdd a new mountain to the list:");
        System.out.println("\tn -> Add new mountain");
        System.out.println("\nOr select one of the existing mountains:");
        System.out.println("\tc -> Browse current list");
        System.out.println("\tq -> Quit");
    }

    private void addNewMountainNameMenu() {
        System.out.println("Enter the name of the mountain:");
    }

    private void addNewMountainPriceMenu() {
        System.out.println("Enter the lift ticket price:");
    }

    private void addNewMountainRentalsMenu() {
        System.out.println("Select rental availability:");
        System.out.println("\ta -> Rentals are available");
        System.out.println("\tf -> Rentals are not available");
    }

    private void addNewMountainDistanceMenu() {
        System.out.println("Enter distance in km from " + user.getUserHomeCity());
    }

    private void browseMountain(String mountainName) throws Exception {
        Mountain mountain = mtnList.getMtnByName(mountainName);
        System.out.println(mountainName + " mountain:");
        System.out.println("\tLift ticket price: $" + mountain.getLiftPrice());
        System.out.println("\t" + mountain.rentalsAvailability());
        System.out.print("\tDistance from home is ");
        System.out.printf("%.1f", mountain.checkDistance(mountain, user.getUserHomeCity()));
        System.out.println(" km");
        System.out.println("\tb -> Select another mountain");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

}
