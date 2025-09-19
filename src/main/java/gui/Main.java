package gui;

import sloth.Sloth;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX Application class for the Sloth task management system.
 * Loads the FXML layout, initializes the main window controller,
 * and manages the primary stage of the GUI application.
 */
public class Main extends Application {

    private Sloth sloth = new Sloth();

    /**
     * Starts the JavaFX application by setting up the primary stage.
     * Loads the FXML layout, creates the scene, injects the Sloth instance
     * into the controller, displays the welcome message, and shows the stage.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Sloth");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setSloth(sloth);  // inject the Sloth instance
            controller.showWelcomeMessage();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
