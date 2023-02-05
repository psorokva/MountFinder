package model;

public class User {
    private final String username;
    private String userHomeCity;

    // REQUIRES: name has a non-zero length
    // EFFECTS: create a user having a name
    public User(String username) {
        this.username = username;
    }

    // REQUIRES: city is one of: Downtown, UBC, Vancouver, Richmond, Burnaby, Surrey, North Vancouver
    // MODIFIES: this
    // EFFECTS: sets user's home city to one of the available options
    public void setUserHomeCity(String city) {
        this.userHomeCity = city;
    }

    public String getName() {
        return this.username;
    }

    public String getUserHomeCity() {
        return this.userHomeCity;
    }
}
