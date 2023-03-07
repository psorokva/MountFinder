package persistence;

import model.Mountain;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is used by JsonReaderTest and JsonWriterTest to verify
 * a mountain from mountain list.
 */
public class JsonTest {

    protected void checkMountain(String name,
                                 String liftPrice,
                                 String rentalAvailability,
                                 Mountain mountain) {
        String currentAnswer;
        if (rentalAvailability.equals("true")) {
            currentAnswer = "Rentals are available";
        } else if (rentalAvailability.equals("false")) {
            currentAnswer = "Rentals are not available";
        } else {
            currentAnswer = "";
        }

        assertEquals(name, mountain.getMtnName());
        assertEquals(Double.parseDouble(liftPrice), mountain.getLiftPrice());
        assertEquals(currentAnswer, mountain.getRentalAvailabilityAnswer());
    }
}
