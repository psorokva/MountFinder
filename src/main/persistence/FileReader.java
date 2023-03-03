package persistence;

import model.Mountain;
import model.MountainList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads mountain list from JSON data stored in file
public class FileReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public FileReader(String source) {
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

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private MountainList parseMountainList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MountainList ml = new MountainList(name);
        addThingies(ml, jsonObject);
        return ml;
    }

    // MODIFIES: ml
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addThingies(MountainList ml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
        for (Object json : jsonArray) {
            JSONObject nextMountain = (JSONObject) json;
            addMountain(ml, nextMountain);
        }
    }

    // MODIFIES: ml
    // EFFECTS: parses mountain from JSON object and adds it to workroom
    private void addMountain(MountainList ml, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        //Category category = Category.valueOf(jsonObject.getString("category"));
        Mountain mountain = new Mountain(name);
        ml.addMountain(mountain);
    }
}
