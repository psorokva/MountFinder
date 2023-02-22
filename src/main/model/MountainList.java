package model;

import java.util.LinkedList;
import java.util.List;

/**
 * This class creates a list of mountains.
 * To provide some default information to a user this list already includes 2 mountains
 * (Cypress and Seymour) with all their fields populated with information.
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

        cypress.addLiftPrice(80);
        cypress.makeRentalsAvailable();
        cypress.setDistance("Vancouver", 30.8f);
        cypress.setDistance("Richmond", 54.8f);
        cypress.setDistance("UBC", 39.9f);

        seymour.addLiftPrice(70);
        seymour.makeRentalsNotAvailable();
        seymour.setDistance("Vancouver", 26.4f);
        seymour.setDistance("Richmond", 39.2f);
        seymour.setDistance("UBC", 37.1f);
    }

    // REQUIRES: mountain is not already in the list
    // MODIFIES: this
    // EFFECTS: adds given mountain to existing list
    public void addMountain(Mountain m) {
        this.mtnList.add(m);
    }

    // REQUIRES: mountain list is not empty
    // EFFECTS: looks for a mountain with given name in the list
    // and returns it if it is in the list, otherwise returns null
    public Mountain getMtnByName(String mountain) {
        for (Mountain m : mtnList) {
            if (m.getMtnName().equals(mountain)) {
                return m;
            }
        }
        return null;
    }

    // EFFECTS: returns the size of Mountain List
    public int size() {
        return this.mtnList.size();
    }

    // EFFECTS: returns Mountain at position i from the Mountain List
    public Mountain getMtnAtIndex(int i) {
        return this.mtnList.get(i);
    }
}
