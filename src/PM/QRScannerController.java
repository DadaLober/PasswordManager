package PM;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryService;
import com.github.sarxos.webcam.WebcamException;
import com.google.zxing.*;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class QRScannerController implements Initializable {

    @FXML
    private ImageView webcamView;

    ObjectProperty<Image> imageObjectProperty = new SimpleObjectProperty<>();
    Webcam webcam;
    @FXML
    private Label testResult;

    private PreparedStatement statement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        webcamView.setTranslateZ(webcamView.getBoundsInLocal().getWidth() / 2.0);
        webcamView.setRotationAxis(Rotate.Y_AXIS);
        webcamView.setRotate(180);
        initWebcam();
//        webcam.setViewSize(new Dimension(400,400));

    }

    private void initWebcam() {
        Task<Void> webcamCapture = new Task<Void>() {
            @Override
            protected Void call() throws SQLException {

                try{webcam = Webcam.getDefault();} catch (WebcamException e) {
                    e.printStackTrace();
                }
                webcam.open();
                final AtomicReference<WritableImage> reference = new AtomicReference<>();
                BufferedImage bfi;
                boolean onGoing = true;

                while (onGoing) {
                    if ((bfi = webcam.getImage()) != null) {
                        reference.set(SwingFXUtils.toFXImage(bfi, reference.get()));
                        bfi.flush();
                        Platform.runLater(() -> imageObjectProperty.set(reference.get()));

                        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bfi);
                        BinaryBitmap bmp = new BinaryBitmap(new HybridBinarizer(luminanceSource));

                        try {
                            Result result = new MultiFormatReader().decode(bmp);
                            if (result.getText() != null) {try {
                                    
                                    String beforeSplit = Encryptor.decrypt(result.getText(), "millerweak");
                                System.out.println(beforeSplit);
                                    String[] temp = beforeSplit.split(":::");
                                    Platform.runLater(() -> {
                                        testResult.setText(beforeSplit);
                                    });
                                    PasswordModel insert = new PasswordModel(temp[0],temp[1],temp[2],temp[3],temp[4]);

                                   PM.Connection connectNow = new PM.Connection();
                                   java.sql.Connection conn = connectNow.connectDB();

                                   String sql = "INSERT INTO `password_db`(`webname`, `email`, `password`, `notes`, `website`) VALUES (?,?,?,?,?)";
                                   statement = conn.prepareStatement(sql);
                                   statement.setString(1,insert.getWebsite());
                                   statement.setString(2,insert.getAppEmail());
                                   statement.setString(3,insert.getPass());
                                   statement.setString(4,insert.getNotes());
                                   statement.setString(5,insert.getAppName());
                                   statement.execute();


                                    break;
                                    
                                } catch (UnsupportedEncodingException | NumberFormatException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                                    Platform.runLater(()->{new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK).show();});
                                }
                            }

                        } catch (NotFoundException ex) {
                        }

                    }
                }
                Platform.runLater(()->{
                    ((Stage)(webcamView.getScene().getWindow())).close();
                });
                return null;
            }
        };
        Thread thread = new Thread(webcamCapture);
        thread.setDaemon(true);
        thread.start();
        webcamView.imageProperty().bind(imageObjectProperty);
        
    }
}
