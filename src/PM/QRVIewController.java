/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package PM;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class QRVIewController implements Initializable {

    @FXML
    private ImageView imgQR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final AtomicReference<WritableImage> reference= new AtomicReference<>();
        reference.set(SwingFXUtils.toFXImage(QRStore.getINSTANCE().getBuffredImage(),reference.get()));
        imgQR.imageProperty().set(reference.get());
    }    
    
}
