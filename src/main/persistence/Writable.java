package persistence;

import org.json.JSONObject;

/**
 * Interface for JsonReader and JsonWriter classes
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
