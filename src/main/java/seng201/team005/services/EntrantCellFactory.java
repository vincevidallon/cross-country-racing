package seng201.team005.services;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import seng201.team005.models.Entrant;


public class EntrantCellFactory implements Callback<ListView<Entrant>, ListCell<Entrant>> {
    private final RaceService raceService;

    public EntrantCellFactory(RaceService raceService) {
        this.raceService = raceService;
    }

    @Override
    public ListCell<Entrant> call(ListView<Entrant> leaderboardListView) {
        ListCell<Entrant> listCell = new ListCell<>() {

            @Override
            public void updateItem(Entrant entrant, boolean empty) {
                super.updateItem(entrant, empty);
                if (!empty && entrant != null) {

                    // I don't know why, but I need to re-set the entrant's position here
                    // for it to display properly on the leaderboard. :(
                    entrant.setPosition(raceService.getEntrantList().indexOf(entrant) + 1);

                    Label nameLabel = new Label(entrant.leaderboardString());
                    nameLabel.setTextAlignment(TextAlignment.CENTER);
                    if (entrant.equals(raceService.getPlayer())) {
                        nameLabel.setStyle("-fx-font-weight: bold");
                    }

                    HBox hBox = new HBox(nameLabel);
                    hBox.setAlignment(Pos.CENTER);

                    setGraphic(hBox);
                }
            }
        };

        listCell.setPadding(Insets.EMPTY);
        return listCell;
    }
}
