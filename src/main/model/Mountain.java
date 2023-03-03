package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * This class creates a mountain with:
 * name,
 * lift price (in dollars),
 * gear rental (available or not),
 * distances fields.
 * The distances are set through Distance class relative to a specific city (Vancouver, Richmond, UBC).
 */
public class Mountain implements Writable {
    private final String name;
    private double liftPrice;             // in dollars
    private boolean gearRental;           // true if gear rental available
    private ArrayList<Distance> distances;


    // REQUIRES: name has a non-zero length
    // EFFECTS: constructs a mountain having a name
    public Mountain(String mtnName) {
        this.name = mtnName;
        this.distances = new ArrayList<>();
    }

    // EFFECTS: returns mountain name
    public String getMtnName() {
        return this.name;
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

    // EFFECTS: returns lift ticket price in dollars
    public double getLiftPrice() {
        return this.liftPrice;
    }

    // REQUIRES: City is one of: Vancouver, Richmond, UBC
    //           distance is > 0
    // MODIFIES: this
    // EFFECTS: sets new distance value for the specified city
    //          and adds it to distances list for this mountain
    public void setDistance(String city, double distance) {
        Distance d1 = new Distance(city, distance);
        distances.add(d1);
    }

    // REQUIRES: City is one of: Vancouver, Richmond, UBC
    // EFFECTS: returns the distance to the mountain
    public double getDistance(String city) {
        for (Distance d : distances) {
            if (d.getCity().equals(city)) {
                return d.getDistanceFromCity();
            }
        }
        return 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        //json.put("category", category);
        return json;
    }
}
