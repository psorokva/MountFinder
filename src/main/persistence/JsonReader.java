package persistence;

import model.Distance;
import model.Mountain;
import model.MountainList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Represents a reader that reads mountain list from JSON data stored in file
 */
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads mountain list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MountainList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMountainList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private MountainList parseMountainList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MountainList ml = new MountainList(name);
        addMountainList(ml, jsonObject);
        return ml;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addMountainList(MountainList ml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mountains");
        for (Object json : jsonArray) {
            JSONObject nextMountain = (JSONObject) json;
            addMountain(ml, nextMountain);
        }
    }

    // MODIFIES: ml
    // EFFECTS: parses mountain from JSON object and adds it to workroom
    private void addMountain(MountainList ml, JSONObject jsonObject) {

        String name = jsonObject.getString("name");
        Mountain mountain = new Mountain(name);
        double liftPrice = Double.parseDouble(jsonObject.getString("lift price"));
        String rentalAvailability = jsonObject.getString("rental availability");
        ArrayList<Distance> distances = addDistances(jsonObject);

        mountain.addLiftPrice(liftPrice);
        if (rentalAvailability.equals("true")) {
            mountain.makeRentalsAvailable();
        } else {
            mountain.makeRentalsNotAvailable();
        }
        mountain.setDistances(distances);

        ml.addMountain(mountain);
    }

    // Todo add effects
    private ArrayList<Distance> addDistances(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("distances");
        ArrayList<Distance> distances = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextDistance = (JSONObject) json;
            distances.add(addDistance(nextDistance));
        }
        return distances;
    }

    // Todo add effects
    private Distance addDistance(JSONObject jsonObject) {
        String city = jsonObject.getString("city");
        double distanceFromCity = jsonObject.getDouble("distanceFromCity");
        return new Distance(city, distanceFromCity);
    }

}
