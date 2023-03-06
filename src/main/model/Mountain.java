package model;

import org.json.JSONArray;
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
    private String rentalAvailabilityAnswer;


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
        rentalAvailabilityAnswer = "Rentals are available";
    }

    // MODIFIES: this
    // EFFECTS: changes gearRental status to not available
    public void makeRentalsNotAvailable() {
        this.gearRental = false;
        rentalAvailabilityAnswer = "Rentals are not available";
    }

    // EFFECTS: returns the availability of gear rental
    public String getRentalAvailabilityAnswer() {
        return this.rentalAvailabilityAnswer;
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

    public void setDistances(ArrayList<Distance> distances) {
        this.distances = distances;
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

    // TODO
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject(); //main file

        json.put("name", name);
        json.put("lift price", Double.toString(liftPrice));
        json.put("rental availability", Boolean.toString(gearRental));
        json.put("distances", distancesToJson());
        return json;
    }

    // TODO fix this
    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray distancesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Distance d : distances) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
