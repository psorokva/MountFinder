package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests Distance class.
 */
public class DistanceTest {
    private Distance distance1;

    @BeforeEach
    public void setUp() {
        String city = "TestCity";
        double distanceFromCity1 = 100;
        distance1 = new Distance(city, distanceFromCity1);
    }

    @Test
    public void testDistanceConstructor() {
        assertEquals("TestCity", distance1.getCity());
        assertEquals(100, distance1.getDistanceFromCity());
    }
}
