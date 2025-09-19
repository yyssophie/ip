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
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
/**
 * Controller for the main GUI window of the Sloth task management application.
 * Handles user interactions, manages dialog display, and coordinates between
 * the GUI components and the underlying Sloth application logic.
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

    /**
     * Initializes the GUI components and sets up automatic scrolling behavior.
     * Called automatically by JavaFX after loading the FXML file.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Sloth application instance and initializes related components.
     * Sets up the UI, storage, task list, and task parser for the GUI session.
     *
     * @param s the Sloth application instance to inject
     */
    public void setSloth(Sloth s) {
        sloth = s;
        ui = new UI();
        storage = new Storage();
        tasks = new TaskList(storage.load());
        taskParser = new TaskParser();
    }

    /**
     * Displays the welcome message when the application starts.
     * Shows the initial greeting in the dialog container with the sloth image.
     */
    public void showWelcomeMessage() {
        String welcomeMessage = ui.showWelcome();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcomeMessage, slothImage)
        );
    }

    /**
     * Handles user input from the text field and processes commands.
     * Creates dialog boxes for both user input and system responses.
     * Automatically closes the application window when a ByeCommand is executed.
     * Clears the user input field after processing.
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

            if (c instanceof sloth.command.ByeCommand) {
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, slothImage)
                );
                userInput.clear();

                // Close the window after a brief delay to show the goodbye message
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    Stage stage = (Stage) userInput.getScene().getWindow();
                    stage.close();
                }));
                timeline.play();
                return;
            }



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
