package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("New Mountain added");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    // Adapted from https://stackoverflow.com/a/1671962
    @Test
    public void testEvent() {
        assertEquals("New Mountain added", e.getDescription());
        assertTrue((d.getTime() - e.getDate().getTime()) < 1000 || (e.getDate().getTime() - d.getTime()) < 1000 );
       // assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New Mountain added", e.toString());
    }
}
