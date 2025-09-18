import heimerdinger.Heimerdinger;
import heimerdinger.HeimerdingerException;
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

    private Heimerdinger heimerdinger;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image heimerdingerImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setHeimerdinger(Heimerdinger h) {
        heimerdinger = h;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            String response = heimerdinger.getResponse(input);
            dialogContainer.getChildren().add(
                    DialogBox.getHeimerdingerDialog(response, heimerdingerImage)
            );
        } catch (HeimerdingerException ex) {
            dialogContainer.getChildren().add(
                    DialogBox.getErrorDialog(ex.getMessage(), heimerdingerImage)
            );
        }
        userInput.clear();
    }
}
