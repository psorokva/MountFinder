package persistence;

import org.json.JSONObject;

/**
 * Interface for JsonReader and JsonWriter classes.
 * Citation: This class was adapted from Writable interface in this app:
 * <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">...</a>
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
