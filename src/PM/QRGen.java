/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PM;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author ACER
 */
public class QRGen {
    public static BufferedImage generateQR(String strToEnc , int size) throws WriterException{
        HashMap<EncodeHintType,ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrWriter = new QRCodeWriter();
        
        BitMatrix bm = qrWriter.encode(strToEnc, BarcodeFormat.QR_CODE, size, size);
        BufferedImage bi = new BufferedImage(bm.getWidth(),bm.getWidth(),BufferedImage.TYPE_INT_RGB);
        bi.createGraphics();
        Graphics2D graphics =(Graphics2D) bi.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, bm.getWidth(), bm.getWidth());
        
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < bm.getWidth(); i++) {
            for (int j = 0; j < bm.getWidth(); j++) {
                if (bm.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return bi;
    }
    
}
