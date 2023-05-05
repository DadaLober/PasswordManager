package PM;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class itemsController implements Initializable {

    @FXML
    private Label appMaillabel;

    @FXML
    private Label appNamelabel;

    @FXML
    private ImageView ivicon;

    private PasswordModel passwordModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public PasswordModel getPasswordModel() {
        return passwordModel;
    }

    public void setPasswordModel(PasswordModel passwordModel) {
        this.passwordModel = passwordModel;
    }

    public void setItemInfo (String appName, String appMail,String appIconUrl){

            appNamelabel.setText(appName);
            appMaillabel.setText(appMail);
            if(!appIconUrl.equalsIgnoreCase("DEFAULT")) {
                ivicon.setImage(new Image(String.valueOf(Main.class.getResource(appIconUrl))));
            }
    }

}
