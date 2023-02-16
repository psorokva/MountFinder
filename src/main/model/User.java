package model;

/**
 * This class creates a user when the application is launched and temporarily stores
 * user home city value (until user quits application).
 */
public class User {
    private String userHomeCity;

    // EFFECTS: creates a user having a name
    public User() {
        userHomeCity = "";
    }

    // REQUIRES: city is one of: Downtown, UBC, Vancouver, Richmond, Burnaby, Surrey, North Vancouver
    // MODIFIES: this
    // EFFECTS: sets user's home city to one of the available options
    public void setUserHomeCity(String city) {
        this.userHomeCity = city;
    }

    public String getUserHomeCity() {
        return this.userHomeCity;
    }
}
