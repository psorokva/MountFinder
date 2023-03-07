package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class creates a list of mountains.
 * To provide some default information to a user this list already includes 2 mountains
 * (Cypress and Seymour) with all their fields populated with information.
 * User can create and add new mountains to this list, but this information will not be stored
 * after the user quits application.
 */
public class MountainList implements Writable {
    private String listName;
    private List<Mountain> mtnList;


    // EFFECTS: constructs new mountain list with Cypress and Seymour mountains in it
    public MountainList(String listName) {
        this.listName = listName;
        mtnList = new LinkedList<>();
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", listName);
        json.put("mountains", mountainsToJson());
        return json;
    }

    // TODO
    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray mountainsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Mountain m : mtnList) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

    public String getName() {
        return listName;
    }

    // EFFECTS: returns an unmodifiable list of mountains in this mountain list
    public List<Mountain> getMountains() {
        return Collections.unmodifiableList(mtnList);
    }

}
