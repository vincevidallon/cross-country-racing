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

        Car testerCar = new Car("TesterCar");
        testerCar.setSpeed(6);
        testerCar.setHandling(5);
        testerCar.setReliability(3);
        testerCar.setFuelEconomy(4);

        testerEntrant = new Entrant(testerCar);
        testerEntrant.setPosition(3);

        raceService = new RaceService(mockMenuRaceController, testerRace, testerRoute, testerEntrant);
    }


    @Test
    void calculatePrizeMoneyForFirstTest() {
        testerEntrant.setPosition(1);
        assertEquals(350, raceService.calculatePrizeMoney());
    }

    @Test
    void calculatePrizeMoneyForSecondTest() {
        testerEntrant.setPosition(2);
        assertEquals(175, raceService.calculatePrizeMoney());
    }

    @Test
    void calculatePrizeMoneyForOutOfTopThreeTest() {
        testerEntrant.setPosition(4);
        assertEquals(0, raceService.calculatePrizeMoney());
    }

    @Test
    void carBreakdownChoiceTest() {
        Entrant testerEntrant = new Entrant(new Car("Testing"));
        raceService.carBreakDownChoice(testerEntrant, true);
        assertTrue(testerEntrant.isStopped());
        assertFalse(testerEntrant.isBrokenDown());
    }

    @Test
    void carBreakdownChoiceTest2() {
        Entrant testerEntrant = new Entrant(new Car("Testing"));
        raceService.carBreakDownChoice(testerEntrant, false);
        assertFalse(testerEntrant.isStopped());
        assertTrue(testerEntrant.isBrokenDown());
    }

    @Test
    void strandedTravelerChoiceTest() {
        Entrant testerEntrant = new Entrant(new Car("Testing"));
        raceService.strandedTravelerChoice(testerEntrant, true);
        assertTrue(testerEntrant.isStopped());
    }

    @Test
    void driveStepDecreasesFuelTest() {
        Car testerCar = new Car("TesterCar");
        testerCar.setSpeed(6);
        testerCar.setHandling(5);
        testerCar.setReliability(3);
        testerCar.setFuelEconomy(4);

        testerEntrant = new Entrant(testerCar);
        testerEntrant.setFuel(100);

        when(testerRoute.getDistance()).thenReturn(600);
        when(testerRoute.getFuelStops()).thenReturn(2);
        raceService.driveStep(testerEntrant);

        int remainingFuel = testerEntrant.getFuel();
        assertTrue(remainingFuel < 100, "Fuel should decrease after driving");
    }

    @Test
    void timeStepTestEndsRaceAtMaxDuration() {
        Car testerCar = new Car("TesterCar");
        testerCar.setSpeed(100);
        testerCar.setHandling(5);
        testerCar.setReliability(7);
        testerCar.setFuelEconomy(6);

        Entrant testerEntrant = new Entrant(testerCar);
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

        assertTrue(testerEntrant.isFinished(), "Entrant should be finished");
        assertEquals(1, testerEntrant.getPosition(), "Entrant should be in P1");
        assertEquals(1, raceService.getCurrentTime(), "The race time should be incremented to 1");
    }
}
