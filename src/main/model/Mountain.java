package model;

/**
 * This class creates a mountain with name, height, lift price, gear rental and distance fields.
 * The distance can be set relative to a specific city (Vancouver, Richmond, UBC).
 */
public class Mountain {
    private final String name;
    private int height;
    private double liftPrice;             // in dollars
    private boolean gearRental;        // true if gear rental available
    private double distance;           // variable for distance


    // REQUIRES: name has a non-zero length
    // EFFECTS: constructs a mountain having a name
    public Mountain(String mtnName) {
        this.name = mtnName;
    }

    // REQUIRES: height > 0
    // MODIFIES: this
    // EFFECTS: adds value for the height of the mountain in meters
    public void setHeight(int height) {
        this.height = height;
    }

    // MODIFIES: this
    // EFFECTS: changes gearRental status to available
    public void makeRentalsAvailable() {
        this.gearRental = true;
    }

    // MODIFIES: this
    // EFFECTS: changes gearRental status to not available
    public void makeRentalsNotAvailable() {
        this.gearRental = false;
    }

    // EFFECTS: returns the availability of gear rental in a user-friendly form
    public String rentalsAvailability() {
        String answer;
        if (!this.gearRental) {
            answer = "Rentals are not available";
            return answer;
        }
        answer = "Rentals are available";
        return answer;
    }

    // REQUIRES: liftPrice > 0
    // MODIFIES: this
    // EFFECTS: adds value for the lift price in dollars
    public void addLiftPrice(double liftPrice) {
        this.liftPrice = liftPrice;
    }

    // REQUIRES: city is one of: UBC, Vancouver, Richmond
    // MODIFIES: this
    // EFFECTS: returns distance in km from the given city to the chosen mountain
    public double checkDistance(Mountain mtn, String city) throws Exception {
        if (city.equals("UBC") && mtn.getMtnName().equals("Cypress")) {
            return distance = 39.9f;
        } else if (city.equals("UBC") && mtn.getMtnName().equals("Seymour")) {
            return distance = 37.1f;
        } else if (city.equals("Vancouver") && mtn.getMtnName().equals("Cypress")) {
            return distance = 30.8f;
        } else if (city.equals("Vancouver") && mtn.getMtnName().equals("Seymour")) {
            return distance = 26.4f;
        } else if (city.equals("Richmond") && mtn.getMtnName().equals("Cypress")) {
            return distance = 54.8f;
        } else if (city.equals("Richmond") && mtn.getMtnName().equals("Seymour")) {
            return distance = 39.2f;
        }
        throw new Exception("Unknown value. Select a valid city");
    }

    // EFFECTS: returns mountain name
    public String getMtnName() {
        return this.name;
    }

    // EFFECTS: returns mountain height
    public int getHeight() {
        return this.height;
    }

    // EFFECTS: returns lift ticket price in dollars
    public double getLiftPrice() {
        return this.liftPrice;
    }

    // EFFECTS: returns the distance to the mountain
    public double getDistance() {
        return distance;
    }

    // REQUIRES: distance is > 0
    // MODIFIES: this
    // EFFECTS: sets a distance to the mountain
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
