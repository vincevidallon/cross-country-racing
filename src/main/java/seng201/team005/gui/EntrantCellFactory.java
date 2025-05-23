package seng201.team005.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import seng201.team005.models.Entrant;
import seng201.team005.services.RaceService;

/**
 * A custom cell factory for displaying Entrants in a ListView.
 * Each cell displays the entrant's leaderboard string.
 *
 * @author sha378
 */
public class EntrantCellFactory implements Callback<ListView<Entrant>, ListCell<Entrant>> {

    // Service for managing race-related functionality.
    private final RaceService raceService;

    /**
     * Constructs an EntrantCellFactory with the specified RaceService.
     *
     * @param raceService The RaceService instance used to retrieve race data.
     */
    public EntrantCellFactory(RaceService raceService) {
        this.raceService = raceService;
    }

    /**
     * Creates a custom ListCell for displaying Entrants in the leaderboard ListView.
     *
     * @param leaderboardListView The ListView to which the cells belong.
     * @return A custom ListCell for displaying Entrants.
     */
    @Override
    public ListCell<Entrant> call(ListView<Entrant> leaderboardListView) {
        ListCell<Entrant> listCell = new ListCell<>() {

            /**
             * Updates the content of the ListCell with the given Entrant.
             * Displays the entrant's leaderboard string and highlights the player's entry.
             *
             * @param entrant The Entrant to display in the cell.
             * @param empty   Whether the cell is empty.
             */
            @Override
            public void updateItem(Entrant entrant, boolean empty) {
                super.updateItem(entrant, empty);
                if (!empty && entrant != null) {

                    // Re-set the entrant's position for proper leaderboard display.
                    // I don't know why this is necessary, but it's the only way I could figure out
                    // how to stop the leaderboard's positions from updating one turn behind the race.
                    entrant.setPosition(raceService.getEntrantList().indexOf(entrant) + 1);

                    // Create a label to display the entrant's leaderboard string.
                    Label nameLabel = new Label(entrant.leaderboardString());
                    nameLabel.setTextAlignment(TextAlignment.CENTER);

                    // Highlight the player's entry with bold text.
                    if (entrant.equals(raceService.getPlayer())) {
                        nameLabel.setStyle("-fx-font-weight: bold");
                    }

                    // Create an HBox to center-align the label.
                    HBox hBox = new HBox(nameLabel);
                    hBox.setAlignment(Pos.CENTER);

                    // Set the HBox as the graphic for the cell.
                    setGraphic(hBox);
                }
            }
        };

        // Remove padding from the cell.
        listCell.setPadding(Insets.EMPTY);
        return listCell;
    }
}