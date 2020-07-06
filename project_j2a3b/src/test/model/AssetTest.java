package model;

import model.incomes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {
    Assets a;

    @BeforeEach
    void runBefore() {
        a = new Assets("Car", 2000.50);
    }

    @Test
    void testConstructor() {
        Assets newAsset = new Assets("Jewellery", 1250.78);
    }

    @Test
    void testGetters() {
        assertEquals("Car", a.getName());
        assertEquals(2000.50, a.getPrice());
    }

    @Test
    void testSetters() {
        a.setName("Jewellery");
        a.setPrice(2500);
        assertEquals("Jewellery", a.getName());
        assertEquals(2500, a.getPrice());
    }
}
