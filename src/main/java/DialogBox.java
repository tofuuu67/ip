import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // NEW: basic readability + default user alignment
        setAlignment(Pos.TOP_RIGHT);                // user on the right by default
        dialog.setWrapText(true);                   // long lines wrap
        dialog.setMaxWidth(480);                    // keep bubbles from spanning the whole width

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        // NEW: user bubble styling (subtle blue, white text)
        db.dialog.setStyle(
                "-fx-background-color: #1d4ed8; " +   // blue
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 8 10 8 10;"
        );
        return db;
    }

    public static DialogBox getHeimerdingerDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip(); // bot on the left

        // Higher-contrast bot bubble (solid slate, white text)
        db.dialog.setStyle(
                "-fx-background-color: #4B5563; " +   // grey
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 8 10 8 10;"
        );
        return db;
    }

    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip(); // errors align like bot messages (left)
        db.dialog.setStyle(
                "-fx-background-color: #7f1d1d;" +  // deep red
                        "-fx-text-fill: #ffe4e6;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 8 10 8 10;"
        );
        return db;
    }
}
