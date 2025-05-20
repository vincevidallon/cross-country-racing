package seng201.team005.gui;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Entrant;
import seng201.team005.services.EntrantCellFactory;
import seng201.team005.services.RaceService;

public class MenuRaceController extends ScreenController {

    @FXML
    private Button nextButton, yesButton, noButton;

    @FXML
    private ListView<Entrant> leaderboardListView;

    @FXML
    private Text timeText, distanceText, fuelText, positionText, carText, routeLengthText, timeLimitText, broadcastLabelText, eventPromptText;

    @FXML
    private VBox broadcastVBox;

    private final RaceService raceService;

    protected MenuRaceController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
        raceService = new RaceService(this, getGameEnvironment().getSelectedRace(),
                getGameEnvironment().getSelectedRoute(), new Entrant(getGameEnvironment().getSelectedCar()));
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_race.fxml";
    }

    @Override
    protected String getTitle() {
        return "Cross Country Racing | Race";
    }


    public void displayEventBroadcast(String broadcast) {
        displayEventBroadcast(broadcast, "");
    }

    public void displayEventBroadcast(String broadcast, String style) {
        Text broadcastText = new Text(broadcast);
        broadcastText.setWrappingWidth(353);
        broadcastText.setStyle(style);

        if (broadcastVBox.getChildren().size() > 12) broadcastVBox.getChildren().remove(1);

        broadcastVBox.getChildren().add(broadcastText);
    }

    public void onCurrentEvent(RaceService.RaceEvent event) {
        boolean isEvent = event != null;
        nextButton.setVisible(!isEvent);
        yesButton.setVisible(isEvent);
        noButton.setVisible(isEvent);
        broadcastVBox.setVisible(!isEvent);
        eventPromptText.setVisible(isEvent);

        switch (event) {
            case STRANDED_TRAVELER -> onStrandedTravelerEvent();
            case FUEL_STOP -> onFuelStopEvent();
            case null, default -> eventPromptText.setText("");
        }
    }

    public void onStrandedTravelerEvent() {

        eventPromptText.setText("""
                You come across a stranded traveler on the side of the road.
                
                Do you offer them a ride?""");

        yesButton.setOnAction(event -> raceService.strandedTravelerChoice(raceService.getPlayer(), true));
        noButton.setOnAction(event -> raceService.strandedTravelerChoice(raceService.getPlayer(), false));
    }

    public void onFuelStopEvent() {

        eventPromptText.setText("""
                You come across a fuel stop.
                
                Do you refuel your vehicle?""");

        yesButton.setOnAction(event -> raceService.fuelStopChoice(raceService.getPlayer(), true));
        noButton.setOnAction(event -> raceService.fuelStopChoice(raceService.getPlayer(), false));
    }

    private void onNextButtonClicked() {
        raceService.timeStep();

        Entrant player = raceService.getPlayer();
        timeText.setText(raceService.getCurrentTime() + " hours passed");
        distanceText.setText(player.getDistance() + " km");
        fuelText.setText(player.getFuel() + " L");
        positionText.setText(player.positionString() + " place");

        leaderboardListView.setItems(raceService.getEntrantList());
        leaderboardListView.scrollTo(raceService.getPlayer());
    }

    private void onGoButtonClicked() {
        nextButton.setText("Next >");
        nextButton.setOnAction(event -> onNextButtonClicked());

        raceService.initEntrantList();
        onNextButtonClicked();
    }

    public void initialize() {
        nextButton.setOnAction(event -> onGoButtonClicked());
        carText.setText(raceService.getPlayer().getName());

        routeLengthText.setText(raceService.getRoute().getDistance() + " km");
        timeLimitText.setText(raceService.getRace().getMaxDuration() + " hours");

        leaderboardListView.setCellFactory(new EntrantCellFactory(raceService));
        leaderboardListView.setItems(raceService.getEntrantList());
        leaderboardListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Entrant>)
                change -> leaderboardListView.getSelectionModel().clearSelection());

        broadcastVBox.getChildren().remove(1);
        broadcastLabelText.setText("Race updates:");
    }
}
