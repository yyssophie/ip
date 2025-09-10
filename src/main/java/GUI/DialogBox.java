package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import javafx.geometry.Pos;

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    public DialogBox(String text, Image img) {
        try {
            URL url = DialogBox.class.getResource("/view/DialogBox.fxml");
            if (url == null) {
                throw new IllegalStateException("FXML not found at /view/DialogueBox.fxml");
            }
            FXMLLoader loader = new FXMLLoader(url);  // location is set here
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_RIGHT); // User dialog: text on left, image on right, aligned right
        return db;
    }

    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip(); // Sloth dialog: image on left, text on right, aligned left
        return db;
    }
}
