package gui;

import sloth.Sloth;
import sloth.TaskList;
import sloth.UI;
import sloth.Storage;
import sloth.command.Command;
import sloth.exception.SlothException;
import sloth.TaskParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sloth sloth;
    private TaskList tasks;
    private UI ui;
    private Storage storage;
    private TaskParser taskParser;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/cute_girl.jpeg"));
    private Image slothImage = new Image(this.getClass().getResourceAsStream("/images/cute_sloth.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setSloth(Sloth s) {
        sloth = s;
        ui = new UI();
        storage = new Storage();
        tasks = new TaskList(storage.load());
        taskParser = new TaskParser();
    }

    /** Shows the welcome message when the application starts */
    public void showWelcomeMessage() {
        String welcomeMessage = ui.showWelcome();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcomeMessage, slothImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input, userImage)
        );

        try {
            Command c = taskParser.parse(input);
            response = c.execute(tasks, ui, storage);

        } catch (SlothException e) {
            response = e.getMessage();
        } catch (IndexOutOfBoundsException e) {
            response = "sorry :( That index is out of my knowledge.";
        } catch (Exception e) {
            response = "An unexpected error occurred: " + e.getMessage();
        }

        // Add Sloth's response dialog
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(response, slothImage)
        );

        userInput.clear();
    }
}
