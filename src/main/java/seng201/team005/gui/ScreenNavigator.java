package seng201.team005.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import seng201.team005.GameEnvironment;

import java.io.IOException;

/**
 * Class that handles navigation between various {@link ScreenController}s. This navigator
 * uses a {@link BorderPane} layout for the root pane. A launched screen is placed in the
 * center area of the border pane, replacing the previous screen if any.
 *
 * @author seng201 teaching team
 */
public class ScreenNavigator {

    private final Stage stage;

    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     *
     * @param stage The JavaFX stage
     */
    public ScreenNavigator(Stage stage) {
        this.stage = stage;

        // Use a border pane as the root component to allow children to be resizable.
        rootPane = new BorderPane();
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    /**
     * Replaces the root border pane's center component with the screen defined by the given
     * {@link ScreenController}.
     *
     * @param controller The JavaFX screen controller for the screen to be launched
     */
    public void launchScreen(ScreenController controller) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource(controller.getFxmlFile()));
            // Set a controller factory that returns the given ScreenController.
            // This allows us to have screen controllers that take argument(s) in their constructor.
            setupLoader.setControllerFactory(param -> controller);
            Parent setupParent  = setupLoader.load();

            rootPane.setCenter(setupParent);
            stage.setTitle(controller.getTitle());
            stage.setWidth(setupParent.prefWidth(-1) + 50);
            stage.setHeight(setupParent.prefHeight(-1) + 50);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
