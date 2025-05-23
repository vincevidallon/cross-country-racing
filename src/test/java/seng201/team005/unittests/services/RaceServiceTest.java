package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.gui.MenuRaceController;
import seng201.team005.models.Car;
import seng201.team005.models.Entrant;
import seng201.team005.models.Race;
import seng201.team005.models.Route;
import seng201.team005.services.RaceService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RaceServiceTest {

    private RaceService raceService;
    private MenuRaceController mockMenuRaceController;
    private Race testerRace;
    private Route testerRoute;
    private Entrant testerEntrant;

    @BeforeEach
    void setUp() {
        mockMenuRaceController = mock(MenuRaceController.class);

        testerRace = mock(Race.class);
        when(testerRace.getPrizeMoney()).thenReturn(350);
        when(testerRace.getEntries()).thenReturn(4);
        when(testerRace.getMaxDuration()).thenReturn(14);

        testerRoute = mock(Route.class);
        when(testerRoute.getDistance()).thenReturn(1000);
        when(testerRoute.getFuelStops()).thenReturn(2);
        when(testerRoute.getTerrain()).thenReturn(Route.Terrain.OFF_ROAD);

        Car testerCar = new Car();
        testerCar.setSpeed(6);
        testerCar.setHandling(5);
        testerCar.setReliability(3);
        testerCar.setFuelEconomy(4);

        testerEntrant = new Entrant(testerCar, Route.Terrain.WINDY);
        testerEntrant.setPosition(3);

        raceService = new RaceService(mockMenuRaceController, testerRace, testerRoute, testerEntrant);
    }


    @Test
    void calculatePrizeMoneyForFirstTest() {
        raceService.getPlayer().setPosition(1);
        assertEquals(350, raceService.calculatePrizeMoney());
    }

    @Test
    void calculatePrizeMoneyForSecondTest() {
        raceService.getPlayer().setPosition(2);
        assertEquals(175, raceService.calculatePrizeMoney());
    }

    @Test
    void calculatePrizeMoneyForOutOfTopThreeTest() {
        raceService.getPlayer().setPosition(4);
        assertEquals(0, raceService.calculatePrizeMoney());
    }

    @Test
    void carBreakdownChoiceTest() {
        Entrant testerEntrant = new Entrant(new Car(), Route.Terrain.WINDY);
        raceService.carBreakDownChoice(testerEntrant, true);
        assertTrue(testerEntrant.isStopped());
        assertFalse(testerEntrant.isBrokenDown());
    }

    @Test
    void carBreakdownChoiceTest2() {
        Entrant testerEntrant = new Entrant(new Car(), Route.Terrain.HILLY);
        raceService.carBreakDownChoice(testerEntrant, false);
        assertFalse(testerEntrant.isStopped());
        assertTrue(testerEntrant.isBrokenDown());
    }

    @Test
    void strandedTravelerChoiceTest() {
        Entrant testerEntrant = new Entrant(new Car(), Route.Terrain.WINDY);
        raceService.strandedTravelerChoice(testerEntrant, true);
        assertTrue(testerEntrant.isStopped());
    }

    @Test
    void driveStepDecreasesFuelTest() {
        Car testerCar = new Car();
        testerCar.setSpeed(6);
        testerCar.setHandling(5);
        testerCar.setReliability(3);
        testerCar.setFuelEconomy(4);

        testerEntrant = new Entrant(testerCar, Route.Terrain.HILLY);
        testerEntrant.setFuel(100);

        when(testerRoute.getDistance()).thenReturn(600);
        when(testerRoute.getFuelStops()).thenReturn(2);
        raceService.driveStep(testerEntrant);

        int remainingFuel = testerEntrant.getFuel();
        assertTrue(remainingFuel < 100, "Fuel should decrease after driving");
    }

    @Test
    void timeStepTestEndsRaceAtMaxDuration() {
        Car testerCar = new Car();
        testerCar.setSpeed(100);
        testerCar.setHandling(5);
        testerCar.setReliability(7);
        testerCar.setFuelEconomy(6);

        Entrant testerEntrant = new Entrant(testerCar, Route.Terrain.OFF_ROAD);
        testerEntrant.addDistance(700);

        when(testerRace.getMaxDuration()).thenReturn(1);
        when(testerRace.getPrizeMoney()).thenReturn(350);
        when(testerRace.getEntries()).thenReturn(0);
        when(testerRoute.getDistance()).thenReturn(500);

        raceService = new RaceService(mockMenuRaceController, testerRace, testerRoute, testerEntrant);
        raceService.initEntrantList();

        raceService.timeStep();

        verify(mockMenuRaceController).onEndReached();
        verify(mockMenuRaceController).addMoney(raceService.calculatePrizeMoney());

        Entrant newTesterEntrant = raceService.getPlayer();
        assertTrue(newTesterEntrant.isFinished(), "Entrant should be finished");
        assertEquals(1, newTesterEntrant.getPosition(), "Entrant should be in P1");
        assertEquals(1, raceService.getCurrentTime(), "The race time should be incremented to 1");
    }
}
