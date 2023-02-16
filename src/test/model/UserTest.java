package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests User class.
 */
public class UserTest {
    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User();
    }

    @Test
    public void testUserConstructor() {
        assertEquals("", user1.getUserHomeCity());
    }

    @Test
    public void setUserHomeCityTest() {
        user1.setUserHomeCity("Vancouver");
        assertEquals("Vancouver", user1.getUserHomeCity());
    }

    @Test
    public void changeUserHomeCityTest() {
        user1.setUserHomeCity("Vancouver");
        assertEquals("Vancouver", user1.getUserHomeCity());
        user1.setUserHomeCity("Richmond");
        assertEquals("Richmond", user1.getUserHomeCity());
    }

}
