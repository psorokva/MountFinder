package persistence;

import model.Mountain;
import model.MountainList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MountainList ml = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyList.json");
        try {
            MountainList ml = reader.read();
            assertEquals("Empty List", ml.getName());
            assertEquals(0, ml.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderSampleMountainList.json");
        try {
            MountainList ml = reader.read();
            assertEquals("Sample Test List", ml.getName());
            List<Mountain> mountains = ml.getMountains();
            assertEquals(2, mountains.size());
            checkMountain("Cypress", "80.0", mountains.get(0));
            checkMountain("Seymour", "70.0", mountains.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

