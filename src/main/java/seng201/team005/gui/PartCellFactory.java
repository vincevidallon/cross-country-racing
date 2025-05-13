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


public class PartCellFactory implements Callback<ListView<Part>, ListCell<Part>> {
    private final GarageService garageService;

    public PartCellFactory(GarageService garageService) {
        this.garageService = garageService;
    }

    @Override
    public ListCell<Part> call(ListView<Part> partListView) {
        ListCell<Part> listCell = new ListCell<>() {

            @Override
            public void updateItem(Part part, boolean empty) {
                super.updateItem(part, empty);
                if (empty || part == null) {
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label(part.garageString());
                    nameLabel.setTextAlignment(TextAlignment.CENTER);

                    HBox hBox = new HBox(nameLabel);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.hoverProperty().addListener((observable, oldValue, newValue)
                            -> garageService.getGarageController().onPartButtonHovered(part, newValue));

                    setGraphic(hBox);
                }
            }
        };

        listCell.setPadding(Insets.EMPTY);
        return listCell;
    }
}
