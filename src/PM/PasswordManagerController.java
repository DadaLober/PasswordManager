package PM;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PasswordManagerController implements Initializable {

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton allItemsButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXButton favoritesButton;

    @FXML
    private Label notesLabel;

    @FXML
    private JFXPasswordField passwordPasswordField;

    @FXML
    private JFXButton trashButton;

    @FXML
    private JFXButton updateButton;

    @FXML
    private Label websiteLabel;

    @FXML
    private Label sitename;

    @FXML
    private VBox vItems;

    @FXML
    private ImageView websiteImage;

    private boolean [] isSelected;

    public void closeButtonOnAction (ActionEvent e) {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void minimizeButtonOnAction (ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    public void addButtonOnAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/AddProduct.fxml"));
        Scene scene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setScene(scene);
        //newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
        newWindow.showAndWait();
    }
    public void updateButtonOnAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/UpdateProduct.fxml"));
        Scene scene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setScene(scene);
        //newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
        newWindow.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            try {

                List<PasswordModel> apps = new ArrayList<>();
                apps.add(new PasswordModel("Adobe", "mail@adobe.com", "icons/icons8-adobe-64.png","www.adobe.com","Creativity for all."));
                apps.add(new PasswordModel("Telegram", "mail@telegram.com","icons/icons8-telegram-app-48.png","www.telegram.com","The People's Paper."));
                apps.add(new PasswordModel("Facebook", "mail@facebook.com","icons/icons8-facebook-48.png","www.facebook.com","Connect with love ones."));
                apps.add(new PasswordModel("Twitter", "mail@twitter.com","icons/icons8-twitter-48.png","www.twitter.com","We believe real change starts with conversation."));
                apps.add(new PasswordModel("Instagram", "mail@instagram.com","icons/icons8-instagram-48.png","www.instagram.com","Life is the biggest party you'll ever be at."));


                Node[] nodes = new Node [apps.size()];
                for(int i = 0; i< nodes.length; i++){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("fxml/items.fxml"));
                    nodes[i] = loader.load();

                    isSelected = new boolean[apps.size()];

                    final int h = i;

                    itemsController controller = loader.getController();
                    controller.setItemInfo(apps.get(i).getAppName(), apps.get(i).getAppEmail(), apps.get(i).getAppIcon());

                    nodes[i].setOnMouseEntered(evt -> {
                        if(!isSelected[h]){
                            nodes[h].setStyle("-fx-background-color: #165DDB");
                        }
                    });
                    nodes[i].setOnMouseExited(evt -> {
                        if(isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #165DDB");
                        }else {
                            nodes[h].setStyle("-fx-background-color: #1E1E1E");
                        }
                    });
                    nodes[i].setOnMousePressed(evt -> {
                        Arrays.fill(isSelected, Boolean.FALSE);
                        isSelected[h] = true;
                        for (Node n:nodes){
                            n.setStyle("-fx-background-color: #1E1E1E");
                        }

                        if (isSelected[h]){
                            nodes[h].setStyle("-fx-background-color: #165DDB");
                        }

                        websiteLabel.setText(apps.get(h).getWebsite());
                        notesLabel.setText(apps.get(h).getNotes());
                        websiteImage.setImage(new Image(String.valueOf(Main.class.getResource(apps.get(h).getAppIcon()))));
                        sitename.setText(apps.get(h).getAppName());


                    });
                    vItems.getChildren().add(nodes[i]);

                    //some other func
                }
            }catch (IOException e){
                e.printStackTrace();
            }
    }
}
