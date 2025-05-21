package seng201.team005.unittests.models;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import seng201.team005.models.Race;
import seng201.team005.models.Route;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class RaceModelTest {

    @RepeatedTest(10)
    void normalConstructorTest() {
        Race race = new Race(1);

        assertTrue(race.getMaxDuration() >= 10 && race.getMaxDuration() < 15,
                () -> "Normal race maxDuration out of range: " + race.getMaxDuration());

        assertTrue(race.getEntries() >= 3 & race.getEntries() < 9,
                () -> "Normal race entries out of range: " + race.getEntries());

        assertTrue(race.getPrizeMoney() >= 6 && race.getPrizeMoney() < 10,
                () -> "Normal race prizeMoney out of range: " + race.getPrizeMoney());

        List<Route> raceRoutes = race.getRouteList();
        assertTrue(!raceRoutes.isEmpty() && raceRoutes.size() < 3,
                () -> "Normal race routeList out of range: " + raceRoutes.size());
        assertTrue(raceRoutes.stream().allMatch(route -> route instanceof Route && route != null),
                "Elements in normal routeList should be non-null Route instances");
    }


    @RepeatedTest(10)
    void hardConstructorTest() {
        Race race = new Race(2);

        assertTrue(race.getMaxDuration() >= 15 && race.getMaxDuration() < 21,
                () -> "Hard race maxDuration out of range: " + race.getMaxDuration());

        assertTrue(race.getEntries() >= 5 && race.getEntries() < 13,
                () -> "Hard race entrants out of range: " + race.getEntries());

        assertTrue(race.getPrizeMoney() >= 10 && race.getPrizeMoney() < 16,
                () -> "Hard race prizeMoney out of range: " + race.getPrizeMoney());

        List<Route> raceRoutes = race.getRouteList();
        assertTrue(raceRoutes.size() >= 2 && raceRoutes.size() < 4,
                () -> "Hard race routeList out of range: " + raceRoutes.size());

        assertTrue(raceRoutes.stream().allMatch(route -> route instanceof Route && route != null),
                "Elements in hard routeList should be non-null Route instances");
        }
}

