package persistence;

import model.Mountain;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkMountain(String name, String liftPrice, Mountain mountain) {
        assertEquals(name, mountain.getMtnName());
        assertEquals(Double.parseDouble(liftPrice), mountain.getLiftPrice());
    }

}
