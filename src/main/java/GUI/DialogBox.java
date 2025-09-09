package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;

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

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getDukeDialog(String text, Image img) {
        return new DialogBox(text, img);
    }
}
