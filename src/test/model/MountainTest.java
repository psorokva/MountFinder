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
    public void setHeightTest() {
        m1.setHeight(900);
        assertEquals(900, m1.getHeight());
    }

    @Test
    public void rentalsAvailabilityTest() {
        assertEquals("not available", m1.rentalsAvailability());
        m1.makeRentalsAvailable();
        assertEquals("available", m1.rentalsAvailability());
        m1.makeRentalsNotAvailable();
        assertEquals("not available", m1.rentalsAvailability());
    }

//    @Test Todo delete if coverage is fine
//    public void rentalsNotAvailableTest() {
//        m1.makeRentalsAvailable();
//        //assertTrue(m1.rentalsNotAvailable());
//        m1.makeRentalsNotAvailable();
//        //assertFalse(m1.rentalsNotAvailable());
//    }

    @Test
    public void addLiftPriceTest() {
        m1.addLiftPrice(95);
        assertEquals(95, m1.getLiftPrice());
        m1.addLiftPrice(1);
        assertEquals(1, m1.getLiftPrice());
    }

    @Test
    public void checkDistanceCypressTest() throws Exception {
        assertEquals(39.9f, m1.checkDistance(m1, "UBC"));
        assertEquals(30.8f, m1.checkDistance(m1, "Vancouver"));
        assertEquals(54.8f, m1.checkDistance(m1, "Richmond"));
    }

    @Test
    public void checkDistanceSeymourTest() throws Exception {
        assertEquals(37.1f, m2.checkDistance(m2, "UBC"));
        assertEquals(26.4f, m2.checkDistance(m2, "Vancouver"));
        assertEquals(39.2f, m2.checkDistance(m2, "Richmond"));
    }

    //to test exception - use try catch with the option "failure" that would say "did not catch error"

    @Test
    public void checkDistanceFromHomeTest() throws Exception {
        user1.setUserHomeCity("UBC");
        assertEquals(39.9f, m1.checkDistance(m1, user1.getUserHomeCity()));
    }


}