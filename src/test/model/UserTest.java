package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User("Polina");
    }

    @Test
    public void testUserConstructor() {
        assertEquals("Polina", user1.getName());
    }

    @Test
    public void setUserHomeCityTest() {
        user1.setUserHomeCity("Vancouver");
        assertEquals("Vancouver", user1.getUserHomeCity());
    }

}
