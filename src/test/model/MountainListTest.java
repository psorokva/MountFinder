package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class tests MountainList class.
 */
public class MountainListTest {
    private MountainList mtnList1;
    private Mountain m1;
    private Mountain m2;
    private Mountain m3;


    @BeforeEach
    public void setUp() {
        mtnList1 = new MountainList("TestList");
        m1 = new Mountain("M1");
        m2 = new Mountain("M2");
        m3 = new Mountain("M3");
    }

    @Test
    public void testMtnListConstructor() {
        assertEquals(0, mtnList1.size());
    }

    @Test
    public void addMountainTest() {
        assertEquals(0, mtnList1.size());
        mtnList1.addMountain(m1);
        assertEquals(1, mtnList1.size());
        mtnList1.addMountain(m2);
        mtnList1.addMountain(m3);
        assertEquals(3, mtnList1.size());
    }

    @Test
    public void getMtnByNameTest() {
        mtnList1.addMountain(m1);
        assertEquals("M1", mtnList1.getMtnByName("M1").getMtnName());
    }

    @Test
    public void mtnNotInTheListTest() {
        assertNull(mtnList1.getMtnByName("Grouse"));
    }

    @Test
    public void getMtnAtIndexTest() {
        mtnList1.addMountain(m2);
        assertEquals(m2, mtnList1.getMtnAtIndex(0));
    }


}
