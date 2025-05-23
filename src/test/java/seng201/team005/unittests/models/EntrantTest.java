package seng201.team005.unittests.models;

import org.junit.jupiter.api.Test;
import seng201.team005.models.Entrant;
import seng201.team005.models.Route;

import static org.junit.jupiter.api.Assertions.*;

public class EntrantTest {
    @Test
    void entrantDistanceIncreasesWhenAddingDistance() {
        Entrant entrant = new Entrant(Route.Terrain.HILLY, 6);
        entrant.addDistance(50);
        assertEquals(50, entrant.getDistance());
    }

    @Test
    void entrantFuelDecreasesWhenSetToLowerValue() {
        Entrant entrant = new Entrant(Route.Terrain.WINDY, 10);
        entrant.setFuel(80);
        assertEquals(80, entrant.getFuel());
    }

    @Test
    void entrantPositionStringFormatsCorrectlyForFirstPlace() {
        Entrant entrant = new Entrant(Route.Terrain.OFF_ROAD);
        entrant.setPosition(1);
        assertEquals("1st", entrant.positionString());
    }

    @Test
    void entrantPositionStringFormatsCorrectlyForEleventhPlace() {
        Entrant entrant = new Entrant(Route.Terrain.HILLY);
        entrant.setPosition(11);
        assertEquals("11th", entrant.positionString());
    }

    @Test
    void entrantStopsWhenSetToStopped() {
        Entrant entrant = new Entrant(Route.Terrain.WINDY);
        entrant.setStopped(true);
        assertTrue(entrant.isStopped());
    }

    @Test
    void entrantBreaksDownWhenSetToBrokenDown() {
        Entrant entrant = new Entrant(Route.Terrain.OFF_ROAD);
        entrant.setBrokenDown(true);
        assertTrue(entrant.isBrokenDown());
    }

    @Test
    void entrantFinishesRaceWhenSetToFinished() {
        Entrant entrant = new Entrant(Route.Terrain.HILLY);
        entrant.setFinished(true);
        assertTrue(entrant.isFinished());
    }

    @Test
    void entrantFuelStopsPassedIncrementsCorrectly() {
        Entrant entrant = new Entrant(Route.Terrain.WINDY);
        entrant.incrementFuelStopsPassed();
        assertEquals(1, entrant.getFuelStopsPassed());
    }

    @Test
    void entrantComparisonConsidersPositionWhenBothFinished() {
        Entrant entrant1 = new Entrant(Route.Terrain.OFF_ROAD);
        Entrant entrant2 = new Entrant(Route.Terrain.OFF_ROAD);
        entrant1.setPosition(1);
        entrant2.setPosition(2);
        entrant1.setFinished(true);
        entrant2.setFinished(true);
        assertTrue(entrant1.compareTo(entrant2) > 0);
    }

    @Test
    void entrantComparisonConsidersFinishedStatus() {
        Entrant entrant1 = new Entrant(Route.Terrain.HILLY);
        Entrant entrant2 = new Entrant(Route.Terrain.HILLY);
        entrant1.addDistance(100);
        entrant1.setFinished(true);
        entrant2.setFinished(false);
        assertTrue(entrant1.compareTo(entrant2) > 0);

        entrant1.setFinished(false);
        entrant2.setFinished(true);
        assertTrue(entrant1.compareTo(entrant2) < 0);
    }

    @Test
    void entrantComparisonConsidersDistanceWhenNotFinished() {
        Entrant entrant1 = new Entrant(Route.Terrain.WINDY);
        Entrant entrant2 = new Entrant(Route.Terrain.WINDY);
        entrant1.addDistance(100);
        entrant2.addDistance(50);
        assertTrue(entrant1.compareTo(entrant2) > 0);
    }

    @Test
    void testLeaderboardString() {
        Entrant entrant = new Entrant(Route.Terrain.HILLY);

        entrant.addDistance(100);

        entrant.setPosition(1);
        assertEquals("1st\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(11);
        assertEquals("11th\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(21);
        assertEquals("21st\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(2);
        assertEquals("2nd\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(3);
        assertEquals("3rd\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(12);
        assertEquals("12th\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());

        entrant.setPosition(13);
        assertEquals("13th\n" + entrant.getName() + "\n100 km", entrant.leaderboardString());
    }
}