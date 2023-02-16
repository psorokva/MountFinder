package model;

import java.util.LinkedList;
import java.util.List;

/**
 * This class creates a list of mountains.
 * To provide some default information to a user this list already includes 2 mountains
 * with all their fields populated with information.
 * User can create and add new mountains to this list, but this information will not be stored
 * after the user quits application.
 */
public class MountainList {
    private List<Mountain> mtnList;


    // EFFECTS: constructs new mountain list with Cypress and Seymour mountains in it
    public MountainList() {
        Mountain cypress = new Mountain("Cypress");
        Mountain seymour = new Mountain("Seymour");

        mtnList = new LinkedList<>();
        mtnList.add(cypress);
        mtnList.add(seymour);

        cypress.setHeight(900);
        cypress.addLiftPrice(80);
        cypress.makeRentalsAvailable();

        seymour.setHeight(1449);
        seymour.addLiftPrice(70);
        seymour.makeRentalsNotAvailable();
    }

    // MODIFIES: this
    // EFFECTS: adds given mountain to existing list
    public void addMountain(Mountain m) {
        this.mtnList.add(m);
    }

    // EFFECTS: returns a list of mountains currently in the list
    public List<Mountain> getMtnList() {
        return mtnList;
    }

    // REQUIRES: mountain list is not empty
    // EFFECTS: looks for a mountain with given name in the list
    // and returns it if it is in the list, otherwise returns null
    public Mountain getMtn(String mountain) {
        for (Mountain m : mtnList) {
            if (m.getMtnName().equals(mountain)) {
                return m;
            }
        }
        return null;
    }
}
