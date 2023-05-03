package PM;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UpdateProductController {

    @FXML
    private Button addCancelButton;

    @FXML
    private JFXTextField newlinkTextField;

    @FXML
    private JFXTextField newnotesPasswordField;

    @FXML
    private JFXPasswordField newpasswordPasswordField;

    @FXML
    private JFXTextField newusernameTextField;

    @FXML
    private JFXTextField newwebsiteTextField;

    @FXML
    public void closeButtonOnAction (ActionEvent event) {
        Stage stage = (Stage) addCancelButton.getScene().getWindow();
        stage.close();
    }

}
