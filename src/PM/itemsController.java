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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setItemInfo (String appName, String appMail, String appIconUrl){
            appNamelabel.setText(appName);
            appMaillabel.setText(appMail);
            ivicon.setImage(new Image(String.valueOf(Main.class.getResource(appIconUrl))));
    }
}
