/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PM;

import java.awt.image.BufferedImage;

/**
 *
 * @author ACER
 */
public class QRStore {
    private QRStore(){
        
    }
    private static final QRStore INSTANCE = new QRStore();
    private BufferedImage buffredImage;

    public static QRStore getINSTANCE() {
        return INSTANCE;
    }

    public BufferedImage getBuffredImage() {
        return buffredImage;
    }

    public void setBuffredImage(BufferedImage buffredImage) {
        this.buffredImage = buffredImage;
    }
    
    
    
}
