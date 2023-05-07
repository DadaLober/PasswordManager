package PM;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class UpdateProductController implements Initializable {

    @FXML
    private Button addCancelButton;

    @FXML
    private Button UpdateButton;

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

    private PasswordModel selectedProd;

    private PreparedStatement statement;

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) addCancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            selectedProd = SelectedProd.getINSTANCE().getSelectedProd();
            newlinkTextField.setText(selectedProd.getWebsite());
            newnotesPasswordField.setText(String.valueOf(selectedProd.getNotes()));
            newpasswordPasswordField.setText(String.valueOf(selectedProd.getPass()));
            newusernameTextField.setText(selectedProd.getAppEmail());
            newwebsiteTextField.setText(selectedProd.getAppName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateButtonOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE, "", new ButtonType("Try Again"));
        final String INVALID_INPUT = "Invalid Input";
        Boolean invalidInp = false;
        if (newlinkTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Website URL\n");
        }
        if (newnotesPasswordField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Notes\n");
        }
        if (newpasswordPasswordField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Password\n");
        }
        if (newusernameTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Username\n");
        }
        if (newwebsiteTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Website Name\n");
        }
        if (invalidInp) {
            alert.show();
            return;
        }

        PM.Connection connectNow = new PM.Connection();
        java.sql.Connection conn = connectNow.connectDB();

        String website, webname, email, password, notes;
        website = newlinkTextField.getText();
        webname = newwebsiteTextField.getText();
        email = newusernameTextField.getText();
        password = newpasswordPasswordField.getText();
        notes = newnotesPasswordField.getText();

        new Alert(Alert.AlertType.CONFIRMATION, "Do you want to update this product?").showAndWait().ifPresent(new Consumer < ButtonType > () {
            @Override
            public void accept(ButtonType response) {
                if (response == ButtonType.OK) {
                    try {
                        String sql = "UPDATE `password_db` SET `webname`=?,`email`=?,`password`=?,`notes`=?,`website`=? WHERE `pid`=?";
                        statement = conn.prepareStatement(sql);
                        statement.setString(1, webname);
                        statement.setString(2, email);
                        statement.setString(3, password);
                        statement.setString(4, notes);
                        statement.setString(5, website);
                        statement.setInt(6, selectedProd.getId());
                        statement.executeUpdate();
                        new Alert(Alert.AlertType.INFORMATION, "Successfully Updated!").showAndWait();

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}