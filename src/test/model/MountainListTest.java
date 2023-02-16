package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests MountainList class.
 */
public class MountainListTest {
    private MountainList mtnList1;
    private Mountain m1;
    private Mountain m2;
    private Mountain m3;

    private Mountain mCypress;


    @BeforeEach
    public void setUp() {
        mtnList1 = new MountainList();
        m1 = new Mountain("M1");
        m2 = new Mountain("M2");
        m3 = new Mountain("M3");
        mCypress = new Mountain("Cypress");
    }

    @Test
    public void testMtnListConstructor() {
        assertEquals("Cypress", mtnList1.getMtn("Cypress").getMtnName());
        assertEquals("Seymour", mtnList1.getMtn("Seymour").getMtnName());
    }




}
