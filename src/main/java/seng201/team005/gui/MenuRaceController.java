package seng201.team005.gui;

import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Entrant;
import seng201.team005.models.Race;
import seng201.team005.services.RaceService;

public class MenuRaceController extends ScreenController {

    private RaceService raceService;
    private Race race;

    protected MenuRaceController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
        raceService = new RaceService(getGameEnvironment().getSelectedRace(), new Entrant(getGameEnvironment().getSelectedCar()));
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_race.fxml";
    }

    @Override
    protected String getTitle() {
        return "Cross Country Racing | Race";
    }

    public void initialize() {

    }
}
