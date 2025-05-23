package seng201.team005.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import seng201.team005.models.Part;
import seng201.team005.services.GarageService;

/**
 * Factory class for creating custom ListCell instances to display Part objects in a ListView.
 * <p>
 * This class customizes the appearance and behavior of each cell in the ListView
 * by displaying the part's name and handling hover events to interact with the GarageService.
 * </p>
 *
 * @author sha378
 */
public class PartCellFactory implements Callback<ListView<Part>, ListCell<Part>> {

    // Service for managing garage-related functionality.
    private final GarageService garageService;

    /**
     * Constructs a PartCellFactory with the specified GarageService.
     *
     * @param garageService The GarageService instance used for handling hover events.
     */
    public PartCellFactory(GarageService garageService) {
        this.garageService = garageService;
    }

    /**
     * Creates a new ListCell for displaying Part objects in the ListView.
     *
     * @param partListView The ListView containing the Part objects.
     * @return A customized ListCell for displaying Part objects.
     */
    @Override
    public ListCell<Part> call(ListView<Part> partListView) {
        ListCell<Part> listCell = new ListCell<>() {

            /**
             * Updates the content and appearance of the ListCell based on the provided Part object.
             *
             * @param part  The Part object to display in the cell.
             * @param empty Whether the cell is empty.
             */
            @Override
            public void updateItem(Part part, boolean empty) {
                super.updateItem(part, empty);
                if (!empty && part != null) {
                    // Create a label to display the part's name.
                    Label nameLabel = new Label(part.garageString());
                    nameLabel.setTextAlignment(TextAlignment.CENTER);

                    // Create an HBox to hold the label and center-align it.
                    HBox hBox = new HBox(nameLabel);
                    hBox.setAlignment(Pos.CENTER);

                    // Add a hover listener to interact with the GarageService.
                    hBox.hoverProperty().addListener((observable, oldValue, newValue)
                            -> garageService.getGarageController().onPartButtonHovered(part, newValue));

                    setGraphic(hBox); // Set the HBox as the graphic for the cell.
                }
            }
        };

        // Remove padding from the cell.
        listCell.setPadding(Insets.EMPTY);
        return listCell;
    }
}