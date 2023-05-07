package PM;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
    private JFXButton addFavorite;

    @FXML
    private JFXTextField textSearch;

    @FXML
    private JFXTextField showPasstf;

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
    private JFXButton removeFavorite;

    @FXML
    private JFXCheckBox showPassword;

    @FXML
    private Label websiteLabel;

    @FXML
    private Label sitename;

    @FXML
    private VBox vItems;

    @FXML
    private ImageView websiteImage;

    private boolean[] isSelected;

    private Class < ? extends ObservableList > selectedProd = null;

    private PreparedStatement statement;

    private final ObservableList < PasswordModel > apps = FXCollections.observableArrayList();

    FilteredList < PasswordModel > filter = new FilteredList(apps);

    public void closeButtonOnAction(ActionEvent e) {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    public void addButtonOnAction(ActionEvent event) throws IOException {
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
    public void updateButtonOnAction(ActionEvent event) {
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

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void trashButtonOnAction(ActionEvent event) {
        try {
            PasswordModel pm = SelectedProd.getINSTANCE().getSelectedProd();
            PM.Connection connectNow = new PM.Connection();
            Connection conn = connectNow.connectDB();

            new Alert(Alert.AlertType.CONFIRMATION, "Do you REALLY want to delete this data?").showAndWait().ifPresent(new Consumer < ButtonType > () {
                public void accept(ButtonType response) {
                    if (response == ButtonType.OK) {
                        try {
                            statement = conn.prepareStatement("DELETE FROM `password_db` WHERE `pid` = ?");
                            statement.setInt(1, pm.getId());
                            statement.execute();


                            RefreshProdList();
                            selectedProd = null;
                            vItems.getChildren().clear();
                            reset(filter);

                            new Alert(Alert.AlertType.INFORMATION, "Successfully removed!").show();
                        } catch (SQLException e) {

                            System.out.println(e.getMessage());
                            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                            System.out.println(e.getMessage());

                        }
                    }
                }
            });

            RefreshProdList();
            selectedProd = null;
            vItems.getChildren().clear();
            reset(filter);
            disablebuttons();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void allItemsButtonOnAction(ActionEvent event) throws SQLException {

        filter.setPredicate(passwordModel -> true);
        RefreshProdList();
        selectedProd = null;
        vItems.getChildren().clear();
        reset(filter);
        cleartext();
        disablebuttons();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            RefreshProdList();
            textSearch.textProperty().addListener((obj, oldVal, newVal) -> {
                filter.setPredicate(passwordModel -> {
                    if (newVal.isBlank() || newVal.isEmpty() || newVal == null) {
                        return true;
                    }
                    String keyword = newVal.toLowerCase();

                    return passwordModel.getAppName().toLowerCase().contains(keyword) ||
                            String.valueOf(passwordModel.getAppEmail()).toLowerCase().contains(keyword);
                });

                vItems.getChildren().clear();
                reset(filter);
            });
            reset(filter);
            removeFavorite.setVisible(false);
            disablebuttons();

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
            apps.add(new PasswordModel(result.getInt("pid"), result.getString("webname"), result.getString("email"), result.getString("password"), result.getString("notes"), result.getString("website"), result.getString("iconpath"), result.getInt("favorite")));
        }
    }

    public void setShowPassword (ActionEvent e) {
        if (showPassword.isSelected()) {
            String show = passwordPasswordField.getText();
            showPasstf.setText(show);
            showPasstf.setVisible(true);
            showPasstf.setDisable(false);
            passwordPasswordField.setVisible(false);
        } else {
            passwordPasswordField.setText(showPasstf.getText());
            passwordPasswordField.setVisible(true);
            showPasstf.setVisible(false);
        }
    }
    private void reset(FilteredList < PasswordModel > filteredList) {
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

                    updateButton.setDisable(false);
                    showPassword.setDisable(false);
                    trashButton.setDisable(false);
                    showPasstf.setVisible(false);
                    passwordPasswordField.setDisable(false);
                    emailTextField.setDisable(false);

                    Arrays.fill(isSelected, Boolean.FALSE);
                    isSelected[h] = true;
                    for (Node n: nodes) {
                        n.setStyle("-fx-background-color: #1E1E1E");
                    }

                    if (isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #165DDB");
                    }

                    SelectedProd.getINSTANCE().setSelectedProd(temp);

                    if (filteredList.get(h).getAppIcon().equalsIgnoreCase("DEFAULT")) {
                        websiteImage.setImage(new Image(String.valueOf(Main.class.getResource("icons/focus.png"))));
                    } else {
                        websiteImage.setImage(new Image(String.valueOf(Main.class.getResource(filteredList.get(h).getAppIcon()))));
                    }

                    sitename.setText(filteredList.get(h).getAppName());
                    emailTextField.setText(filteredList.get(h).getAppEmail());
                    passwordPasswordField.setText(filteredList.get(h).getPass());
                    websiteLabel.setText(filteredList.get(h).getWebsite());
                    notesLabel.setText(filteredList.get(h).getNotes());

                });
                vItems.getChildren().add(nodes[i]);

                favoritesButton.setOnMousePressed(event -> {

                    filter.setPredicate(passwordModel -> passwordModel.getFavorites() == 1);
                    vItems.getChildren().clear();
                    reset(filter);
                    cleartext();
                    disablebuttons();
                    addFavorite.setVisible(false);
                    removeFavorite.setVisible(true);


                });
                addFavorite.setOnMousePressed(event -> {
                    try {
                        PasswordModel pm = SelectedProd.getINSTANCE().getSelectedProd();
                        PM.Connection connectNow = new PM.Connection();
                        Connection conn = connectNow.connectDB();
                        statement = conn.prepareStatement("UPDATE `password_db` SET favorite = 1 WHERE `pid`=?");
                        statement.setInt(1, pm.getId());
                        statement.executeUpdate();
                        apps.clear();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                removeFavorite.setOnMousePressed(event -> {
                    try {
                        PasswordModel pm = SelectedProd.getINSTANCE().getSelectedProd();
                        PM.Connection connectNow = new PM.Connection();
                        Connection conn = connectNow.connectDB();
                        statement = conn.prepareStatement("UPDATE `password_db` SET favorite = 0 WHERE `pid`=?");
                        statement.setInt(1, pm.getId());
                        statement.executeUpdate();
                        apps.clear();
                        vItems.getChildren().clear();
                        reset(filter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disablebuttons(){
        updateButton.setDisable(true);
        showPassword.setDisable(true);
        trashButton.setDisable(true);
        emailTextField.setDisable(true);
        passwordPasswordField.setDisable(true);
        showPasstf.setDisable(true);
    }

    public void cleartext() {
        sitename.setText("Default");
        emailTextField.setText("Email");
        passwordPasswordField.setText("");
        showPasstf.setText("Password");
        websiteLabel.setText("www.default.com");
        notesLabel.setText("Select an account to get started.");
        showPassword.setSelected(false);
        addFavorite.setVisible(true);
        removeFavorite.setVisible(false);
    }

    public void generateQR(ActionEvent e){
        PasswordModel pm = SelectedProd.getINSTANCE().getSelectedProd();

        String mgToSend = pm.getAppEmail() + ":::" +pm.getPass();

        try {
            BufferedImage bi = QRGen.generateQR(Encryptor.encrypt(mgToSend,"millerweak"),500);
            QRStore.getINSTANCE().setBuffredImage(bi);
            Parent root = FXMLLoader.load(getClass().getResource("fxml/QRView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) e.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void importWithQR (ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/QRScanner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) e.getSource()).getScene().getWindow());
            stage.showAndWait();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
    }
}