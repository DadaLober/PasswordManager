package PM;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddProductController implements Initializable {

    @FXML
    private Button addCancelButton;

    @FXML
    private JFXTextField linkTextField;

    @FXML
    private JFXTextField notesTextField;

    @FXML
    private JFXPasswordField passwordPasswordField;

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXTextField websiteTextField;

    private PreparedStatement statement;

    public void closeButtonOnAction (ActionEvent e) {

        Stage stage = (Stage) addCancelButton.getScene().getWindow();
        stage.close();

    }

    public void btn_addProduct(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.NONE, "", new ButtonType("Try Again"));
        final String INVALID_INPUT = "Invalid Input";
        Boolean invalidInp = false;
        if (linkTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Website Link\n");
        }
        if (websiteTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Website Name\n");
        }if (usernameTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Email\n");
        }
        if (passwordPasswordField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Password\n");
        }
        if (notesTextField.getText().isBlank()) {
            invalidInp = true;
            alert.setTitle(INVALID_INPUT);
            alert.setContentText(alert.getContentText() + "Missing Notes\n");
        }
        if (invalidInp) {
            alert.show();
            return;
        }


        PM.Connection connectNow = new PM.Connection();
        Connection conn = connectNow.connectDB();

        String website, webname, email,password,notes;
        website = linkTextField.getText();
        webname = websiteTextField.getText();
        email = usernameTextField.getText();
        password = passwordPasswordField.getText();
        notes = notesTextField.getText();

        new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this data?").showAndWait().ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType response) {
                if (response == ButtonType.OK) {
                    try {
                        String sql = "INSERT INTO `password_db`(`webname`,`email`,`password`,`notes`,`website`) VALUES ('" + webname + "','" + email + "','" + password + "','" + notes + "','" + website + "')";
                        statement = conn.prepareStatement(sql);
                        statement.executeUpdate();
                        new Alert(Alert.AlertType.INFORMATION, "Successfully added!").show();
                        clear();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }
    public void clear()
    {
        websiteTextField.setText("");
        linkTextField.setText("");
        usernameTextField.setText("");
        passwordPasswordField.setText("");;
        notesTextField.setText("");;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
