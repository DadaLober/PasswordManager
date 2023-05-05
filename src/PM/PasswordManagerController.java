package PM;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
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
    private JFXTextField textSearch;

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

    private Class<? extends ObservableList> selectedProd = null;

    private PreparedStatement statement;

    private final ObservableList<PasswordModel> apps = FXCollections.observableArrayList();

    public Class<? extends ObservableList> getSelectedProd() {
        return selectedProd;
    }

    public void setSelectedProd(Class<? extends ObservableList> selectedProd) {
        this.selectedProd = selectedProd;
    }

    FilteredList<PasswordModel> filter = new FilteredList(apps);

    public void closeButtonOnAction (ActionEvent e) {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void minimizeButtonOnAction (ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    public void addButtonOnAction (ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/AddProduct.fxml"));
            Scene scene = new Scene(root);
            Stage newWindow = new Stage();
            newWindow.initStyle(StageStyle.UNDECORATED);
            newWindow.setScene(scene);
            //newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
            newWindow.showAndWait();
            RefreshProdList();
            selectedProd = null;
            vItems.getChildren().clear();
            reset(filter);
        } catch (IOException | SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            System.out.println(ex.getMessage());
        }
    }
    public void updateButtonOnAction (ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/UpdateProduct.fxml"));
        Scene scene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setScene(scene);
        //newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
        newWindow.showAndWait();
        RefreshProdList();
        selectedProd = null;
        vItems.getChildren().clear();
        reset(filter);
        }catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

                try {
                    RefreshProdList();
                    textSearch.textProperty().addListener((obj, oldVal, newVal) -> {
                        filter.setPredicate(passwordModel  -> {
                            if (newVal.isBlank() || newVal.isEmpty() || newVal == null) {
                                return true;
                            }
                            String keyword = newVal.toLowerCase();

                            return passwordModel.getAppName().toLowerCase().contains(keyword)
                                    || String.valueOf(passwordModel.getAppEmail()).toLowerCase().contains(keyword);
                        });
                        vItems.getChildren().clear();
                        reset(filter);
                    });
                    reset(filter);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to Refresh Product List").show();
                }

    }
    private void RefreshProdList() throws SQLException {

        PM.Connection connectNow = new PM.Connection();
        Connection conn = connectNow.connectDB();

        statement = conn.prepareStatement("SELECT * FROM `password_db`");
        ResultSet result = statement.executeQuery();
        apps.clear();
        while (result.next()) {
            apps.add(new PasswordModel(result.getInt("pid"), result.getString("webname"), result.getString("email"), result.getString("password"), result.getString("notes"), result.getString("website"), result.getString("iconpath")));
        }
    }

    private void reset(FilteredList<PasswordModel> filteredList) {
        try {

            try {
                RefreshProdList();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to Refresh Product List").show();
            }

            Node[] nodes = new Node[filteredList.size()];
            for (int i = 0; i < nodes.length; i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("fxml/items.fxml"));
                nodes[i] = loader.load();

                isSelected = new boolean[filteredList.size()];

                final int h = i;

                itemsController controller = loader.getController();
                PasswordModel temp = filteredList.get(i);
                controller.setPasswordModel(temp);
                controller.setItemInfo(filteredList.get(i).getAppName(), filteredList.get(i).getAppEmail(), filteredList.get(i).getAppIcon());

                nodes[i].setOnMouseEntered(evt -> {
                    if (!isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #165DDB");
                    }
                });
                nodes[i].setOnMouseExited(evt -> {
                    if (isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #165DDB");
                    } else {
                        nodes[h].setStyle("-fx-background-color: #1E1E1E");
                    }
                });
                nodes[i].setOnMousePressed(evt -> {
                    Arrays.fill(isSelected, Boolean.FALSE);
                    isSelected[h] = true;
                    for (Node n : nodes) {
                        n.setStyle("-fx-background-color: #1E1E1E");
                    }

                    if (isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #165DDB");
                    }
                    SelectedProd.getINSTANCE().setSelectedProd(temp);
                    websiteLabel.setText(filteredList.get(h).getWebsite());
                    notesLabel.setText(filteredList.get(h).getNotes());
                    if (filteredList.get(h).getAppIcon().equalsIgnoreCase("DEFAULT")) {
                        websiteImage.setImage(new Image(String.valueOf(Main.class.getResource("icons/focus.png"))));
                    } else {
                        websiteImage.setImage(new Image(String.valueOf(Main.class.getResource(filteredList.get(h).getAppIcon()))));
                    }
                    sitename.setText(filteredList.get(h).getAppName());
                    emailTextField.setText(filteredList.get(h).getAppEmail());
                    passwordPasswordField.setText(filteredList.get(h).getPass());


                    });
                    vItems.getChildren().add(nodes[i]);

                    trashButton.setOnMousePressed(evt -> {
                        PM.Connection connectNow = new PM.Connection();
                        Connection conn = connectNow.connectDB();

                        SelectedProd.getINSTANCE().setSelectedProd(temp);
                        try {
                            statement = conn.prepareStatement("DELETE FROM `password_db` WHERE pid = ?");
                            statement.setInt(1, filteredList.get(h).getId());
                            statement.execute();
                            RefreshProdList();
                            selectedProd = null;
                            vItems.getChildren().clear();
                            reset(filter);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
            }
            }catch (IOException e){
                e.printStackTrace();
            }

    }
}

