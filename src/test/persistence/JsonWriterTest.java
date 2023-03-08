package persistence;

import model.Mountain;
import model.MountainList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class tests JsonWriter class.
 * Citation: This class was adapted from JsonWriterTest class in this app:
 * <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">...</a>
 */
public class JsonWriterTest extends JsonTest {

    Mountain mountain1 = new Mountain("Mountain 1");
    Mountain mountain2 = new Mountain("Mountain 2");

    @BeforeEach
    public void setUp() {
        mountain1.addLiftPrice(99.0);
        mountain1.makeRentalsAvailable();
        mountain1.setDistance("UBC", 120.3);
        mountain2.addLiftPrice(150.0);
        mountain2.makeRentalsNotAvailable();
        mountain2.setDistance("UBC", 45.0);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            MountainList ml = new MountainList("My mountain list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            MountainList ml = new MountainList("My mountain list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(ml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            ml = reader.read();
            assertEquals("My mountain list", ml.getName());
            assertEquals(0, ml.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MountainList ml = new MountainList("My mountain list");
            ml.addMountain(mountain1);
            ml.addMountain(mountain2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMountainList.json");
            writer.open();
            writer.write(ml);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMountainList.json");
            ml = reader.read();
            assertEquals("My mountain list", ml.getName());
            List<Mountain> mountains = ml.getMountains();
            assertEquals(2, mountains.size());
            checkMountain("Mountain 1", "99.0", "true", mountains.get(0));
            checkMountain("Mountain 2", "150.0", "false", mountains.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
