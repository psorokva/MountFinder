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
 *   the distance to the mountain from there.
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
                addNewMountainNameMenu();
                break;
            case "c":
                browseCypress();
                break;
            case "s":
                browseSeymour();
                break;
            case "b":
                displayMtnSelectionMenu();
                break;
            default:
                processCommand3(command);
                break;
        }
    }

    private void processCommand3(String command) {
        switch (command) {
            case "1":
                // STUB
                break;
            case "2":
                // STUB
                break;
            case "3":
                // STUB
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
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
        System.out.println("\nYou selected " + user.getUserHomeCity());
        System.out.println("\nAdd a new mountain to the list:");
        System.out.println("\tn -> Add new mountain");
        System.out.println("\nOr select one of the existing mountains to browse:");
        System.out.println("\tc -> Cypress");
        System.out.println("\ts -> Seymour");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

    private void addNewMountainNameMenu() {
        System.out.println("Enter the name of the mountain:");
        System.out.println("\tq -> Quit");
    }

    private void addNewMountainPriceMenu() {
        System.out.println("Enter the lift ticket price:");
        System.out.println("\tq -> Quit");
    }

    private void addNewMountainRentalsMenu() {
        System.out.println("Select rental availability:");
        System.out.println("\ta -> Rentals are available");
        System.out.println("\tf -> Rentals are not available");
        System.out.println("\tq -> Quit");
    }

    private void browseCypress() throws Exception {
        Mountain cypress = mtnList.getMtn("Cypress");
        System.out.println("Cypress mountain");
        System.out.println("\tLift ticket price: $" + cypress.getLiftPrice());
        System.out.println("\tRentals are " +  cypress.rentalsAvailability());
        System.out.print("\tDistance from home is ");
        System.out.printf("%.1f", cypress.checkDistance(cypress, user.getUserHomeCity()));
        System.out.println(" km");
        System.out.println("\tb -> Select another mountain");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

    private void browseSeymour() throws Exception {
        Mountain seymour = mtnList.getMtn("Seymour");
        System.out.println("Seymour mountain");
        System.out.println("\tLift ticket price: $" + seymour.getLiftPrice());
        System.out.println("\tRentals are " + seymour.rentalsAvailability());
        System.out.print("\tDistance from home is ");
        System.out.printf("%.1f", seymour.checkDistance(seymour, user.getUserHomeCity()));
        System.out.println(" km");
        System.out.println("\tb -> Select another mountain");
        System.out.println("\th -> Change home city");
        System.out.println("\tq -> Quit");
    }

}
