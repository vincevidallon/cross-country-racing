package seng201.team005.unittests.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team005.models.Part;

public class PartTest {
    @Test
    void testPartConstructorWithName() {
        Part part = new Part("Turbo");
        assertEquals("Turbo", part.getName());
        assertTrue(part.getSpeed() >= -1 && part.getSpeed() < 4);
        assertTrue(part.getHandling() >= -1 && part.getHandling() < 4);
        assertTrue(part.getReliability() >= -1 && part.getReliability() < 4);
        assertTrue(part.getFuelEconomy() >= -1 && part.getFuelEconomy() < 4);
        assertEquals(part.getOverall(), (part.getSpeed() + part.getHandling() + part.getReliability() + part.getFuelEconomy()) / 4);
        assertTrue(part.getBuyValue() >= 3); // buyValue is at least 1, then +2
        assertTrue(part.getSellValue() >= 1);
        assertTrue(part.getSellValue() <= part.getBuyValue());
    }

    @Test
    void testPartDefaultConstructor() {
        Part part = new Part();
        assertNotNull(part.getName());
        assertTrue(part.getName().startsWith("Part"));
        assertEquals(6, part.getName().length()); // e.g., PartXY
        assertTrue(part.getSpeed() >= -1 && part.getSpeed() < 4);
        assertTrue(part.getHandling() >= -1 && part.getHandling() < 4);
        assertTrue(part.getReliability() >= -1 && part.getReliability() < 4);
        assertTrue(part.getFuelEconomy() >= -1 && part.getFuelEconomy() < 4);
    }

    @Test
    void testToStringReturnsName() {
        Part part = new Part("Exhaust");
        assertEquals("Exhaust", part.toString());
    }
}
