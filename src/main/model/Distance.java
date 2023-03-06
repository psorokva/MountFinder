package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * This class records distance from the given city.
 * Mountain class uses this class to store information about distances from multiple cities
 * to one mountain.
 * Currently, the options for cities are limited to Vancouver, Richmond and UBC.
 */
public class Distance implements Writable {
    private String city;
    private double distanceFromCity;

    // REQUIRES: city is one of: UBC, Vancouver, Richmond
    //           distance is > 0
    // EFFECTS: saves distance from given city in km
    public Distance(String city, double distanceFromCity) {
        this.city = city;
        this.distanceFromCity = distanceFromCity;
    }

    // EFFECTS: returns city name
    public String getCity() {
        return this.city;
    }

    // EFFECTS: returns distance from the city
    public double getDistanceFromCity() {
        return this.distanceFromCity;
    }

    // TODO
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("city", city);
        json.put("distanceFromCity", distanceFromCity);
        return json;
    }
}
