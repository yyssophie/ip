package GUI;

import sloth.Sloth;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Sloth using FXML.
 */
public class Main extends Application {

    private Sloth sloth = new Sloth();

    @Override
    public void start(Stage stage) {
        try {
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
