package seng201.team005.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import seng201.team005.models.Part;

public class PartCellFactory implements Callback<ListView<Part>, ListCell<Part>> {

    @Override
    public ListCell<Part> call(ListView<Part> partListView) {
        return new ListCell<>() {
            @Override
            public void updateItem(Part part, boolean empty) {
                super.updateItem(part, empty);
                if (empty || part == null) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    Label nameLabel = new Label(part.getName());

                    //TODO: make this hover bullshit work
                    hBox.hoverProperty().addListener((observable, oldValue, newValue) -> System.out.println("Hovered!"));

                    hBox.getChildren().addAll(nameLabel);
                    setGraphic(hBox);
                }
            }
        };
    }
}
