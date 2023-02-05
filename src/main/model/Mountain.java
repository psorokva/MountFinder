package model;

public class Mountain {
    private final String name;
    private int height;
    private Integer liftPrice;             // in cents
    private boolean gearRental;        // true if gear rental available


    // REQUIRES: name has a non-zero length
    // EFFECTS: constructs a mountain having a name
    public Mountain(String mtnName) {
        this.name = mtnName;
    }

    // REQUIRES: height > 0
    // MODIFIES: this
    // EFFECTS: adds value for the height of the mountain in meters
    public void setHeight(Integer height) {
        this.height = height;
    }

    // MODIFIES: this
    // EFFECTS: changes gearRental status to available
    public void rentalsAvailable() {
        this.gearRental = true;
    }

    // MODIFIES: this
    // EFFECTS: changes gearRental status to not available
    public void rentalsNotAvailable() {
        this.gearRental = false;
    }

    // REQUIRES: liftPrice > 0
    // MODIFIES: this
    // EFFECTS: adds value for the lift price in cents
    public void addLiftPrice(Integer liftPrice) {
        this.liftPrice = liftPrice;
    }

    // REQUIRES: city is one of: Downtown, UBC, Vancouver, Richmond, Burnaby, Surrey, North Vancouver
    // EFFECTS: returns distance in km from the city to the mountain
    public double checkDistance(String city) throws Exception {
        switch (city) {
            case "Downtown":
                return 27.9f;
            case "UBC":
                return 38.7f;
            case "Vancouver":
                return 30.6f;
            case "Richmond":
                return 53.3f;
            case "Burnaby":
                return 39.0f;
            case "Surrey":
                return 60.0f;
            case "North Vancouver":
                return 26.1f;
            default:
                throw new Exception("Unknown value. Select a valid city");
        }
    }

    public String getName() {
        return this.name;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Boolean areRentalsAvailable() {
        return this.gearRental;
    }

    public Integer getLiftPrice() {
        return this.liftPrice;
    }
}
