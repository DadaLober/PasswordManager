package PM;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class rootController {

    @FXML
    private Button closeButton;

    @FXML
    private JFXPasswordField lbl_password;

    @FXML
    private Label loginCheckLabel;

    double x, y = 0;

    @FXML
    void loginLoginButtonOnAction(ActionEvent event) {


    }
    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void btn_login(ActionEvent evt) throws SQLException, IOException {
        PM.Connection connectNow = new PM.Connection();
        java.sql.Connection conn = connectNow.connectDB();
        try{
            PreparedStatement statement = conn.prepareStatement("select * from accounts_db");
            ResultSet result = statement.executeQuery();

            while (result.next()){
                String encryptedPassword = result.getString("rootpass");

                if(rootEnryptor.encryptString(lbl_password.getText()).equals(encryptedPassword)){
                      Parent root = FXMLLoader.load(getClass().getResource("fxml/PasswordManager.fxml"));
                      Scene scene = new Scene(root);
                      Stage newWindow = new Stage();
                      newWindow.initStyle(StageStyle.UNDECORATED);
                      newWindow.setScene(scene);

                        root.setOnMousePressed(event -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });
                        root.setOnMouseDragged(event -> {
                            newWindow.setX(event.getScreenX() - x);
                            newWindow.setY(event.getScreenY() - y);
                        });

                        newWindow.show();
                        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                        stage.close();
                    } else {
                    Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
                    stage.close();
                }

            }
        }catch (IOException | SQLException | NoSuchAlgorithmException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            System.out.println(e.getMessage());
            System.out.println("catch nagrun");
            e.printStackTrace();
        }
    }

}

