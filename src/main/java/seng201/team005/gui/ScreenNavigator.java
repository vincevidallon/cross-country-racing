package seng201.team005.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that handles navigation between various {@link ScreenController}s. This navigator
 * uses a {@link BorderPane} layout for the root pane. A launched screen is placed in the
 * center area of the border pane, replacing the previous screen if any.
 *
 * @author seng201 teaching team
 */
public class ScreenNavigator {

    // The primary stage of the JavaFX application.
    private final Stage stage;

    // The root pane that holds the current screen in its center.
    private final BorderPane rootPane;

    /**
     * Creates a ScreenNavigator with the given stage.
     * <p>
     * Initializes the root pane as a {@link BorderPane} and sets it as the scene's root.
     * The stage is displayed immediately after initialization.
     * </p>
     *
     * @param stage The JavaFX stage to be used for displaying screens.
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
     * <p>
     * This method loads the FXML file associated with the provided controller, sets the
     * controller factory to use the given controller instance, and updates the stage's
     * title and dimensions based on the loaded screen.
     * </p>
     *
     * @param controller The JavaFX screen controller for the screen to be launched.
     */
    public void launchScreen(ScreenController controller) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource(controller.getFxmlFile()));
            // Set a controller factory that returns the given ScreenController.
            // This allows us to have screen controllers that take argument(s) in their constructor.
            setupLoader.setControllerFactory(param -> controller);
            Parent setupParent  = setupLoader.load();

            // Set the loaded screen in the center of the root pane.
            rootPane.setCenter(setupParent);

            // Update the stage's title and dimensions based on the new screen.
            stage.setTitle(controller.getTitle());
            stage.setWidth(setupParent.prefWidth(-1) + 50);
            stage.setHeight(setupParent.prefHeight(-1) + 50);
            stage.centerOnScreen();

        } catch (IOException e) {
            // Print the stack trace if an error occurs while loading the FXML file.
            e.printStackTrace();
        }
    }
}