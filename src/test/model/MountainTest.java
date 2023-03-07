package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests Mountain class.
 */
class MountainTest {
    private Mountain m1;
    private Mountain m2;
    private User user1;

    @BeforeEach
    public void setUp() {
        m1 = new Mountain("Cypress");
        m2 = new Mountain("Seymour");
        user1 = new User();
    }

    @Test
    public void testMtnConstructor() {
        assertEquals("Cypress", m1.getMtnName());
        assertEquals("Seymour", m2.getMtnName());
    }

    @Test
    public void rentalsAvailabilityTest() {
        assertNull(m1.getRentalAvailabilityAnswer());
        m1.makeRentalsAvailable();
        assertEquals("Rentals are available", m1.getRentalAvailabilityAnswer());
        m1.makeRentalsNotAvailable();
        assertEquals("Rentals are not available", m1.getRentalAvailabilityAnswer());
    }

    @Test
    public void addLiftPriceTest() {
        m1.addLiftPrice(95);
        assertEquals(95, m1.getLiftPrice());
        m1.addLiftPrice(1.34);
        assertEquals(1.34, m1.getLiftPrice());
    }

    @Test
    public void setDistanceTest() {
        m1.setDistance("Vancouver", 1);
        m1.setDistance("Richmond", 56.5);
        m1.setDistance("UBC", 788.5);
        assertEquals(1, m1.getDistance("Vancouver"));
        assertEquals(56.5, m1.getDistance("Richmond"));
        assertEquals(788.5, m1.getDistance("UBC"));
    }

    @Test
    public void distanceNotSet() {
        m1.setDistance("Richmond", 56.5);
        assertEquals(0, m1.getDistance("UBC"));
    }

    @Test
    public void checkDistanceFromUserHome() {
        m2.setDistance("Vancouver", 130);
        m2.setDistance("UBC", 230);
        user1.setUserHomeCity("UBC");
        assertEquals(230, m2.getDistance(user1.getUserHomeCity()));
    }


}