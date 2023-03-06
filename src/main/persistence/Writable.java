package persistence;

import org.json.JSONObject;

/**
Todo
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
