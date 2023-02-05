package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MountainTest {
    private Mountain m1;
    private Mountain m2;
    private User user1;

    @BeforeEach
    public void setUp() {
        m1 = new Mountain("Cypress");
        m2 = new Mountain("Seymour");
        user1 = new User("John");
    }

    @Test
    public void testConstructor() {
        assertEquals("Cypress", m1.getName());
        assertEquals("Seymour", m2.getName());
    }

    @Test
    public void setHeightTest() {
        m1.setHeight(900);
        assertEquals(900, m1.getHeight());
    }

    @Test
    public void rentalsAvailableTest() {
        assertFalse(m1.areRentalsAvailable());
        m1.rentalsAvailable();
        assertTrue(m1.areRentalsAvailable());
    }

    @Test
    public void rentalsNotAvailableTest() {
        m1.rentalsAvailable();
        assertTrue(m1.areRentalsAvailable());
        m1.rentalsNotAvailable();
        assertFalse(m1.areRentalsAvailable());
    }

    @Test
    public void addLiftPriceTest() {
        m1.addLiftPrice(950);
        assertEquals(950, m1.getLiftPrice());
        m1.addLiftPrice(850);
        assertEquals(850, m1.getLiftPrice());
    }

    @Test
    public void checkDistanceTest() throws Exception {
        assertEquals(27.9f, m1.checkDistance("Downtown"));
        assertEquals(38.7f, m1.checkDistance("UBC"));
        assertEquals(30.6f, m1.checkDistance("Vancouver"));
        assertEquals(53.3f, m1.checkDistance("Richmond"));
        assertEquals(39.0f, m1.checkDistance("Burnaby"));
        assertEquals(60.0f, m1.checkDistance("Surrey"));
        assertEquals(26.1f, m1.checkDistance("North Vancouver"));
    }

    @Test
    public void checkDistanceFromHomeTest() throws Exception {
        user1.setUserHomeCity("UBC");
        assertEquals(38.7f, m1.checkDistance(user1.getUserHomeCity()));
    }


}