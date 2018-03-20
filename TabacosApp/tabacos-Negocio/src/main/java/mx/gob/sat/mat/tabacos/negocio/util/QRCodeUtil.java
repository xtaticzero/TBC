/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import mx.gob.sat.mat.tabacos.negocio.excepcion.QRCodeUtilException;
import org.springframework.stereotype.Component;

@Component("QRCodeUtil")
public final class QRCodeUtil {

    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(QRCodeUtil.class);

    private QRCodeUtil() {
    }

    public static InputStream getQr(String folio, String rfc, int width, int height, String formato) throws QRCodeUtilException {

        String urlQr = "";
        try {
            urlQr = obtenerConfiguracion() + "?D1=20&D2=1&D3=" + folio + "_" + rfc;
        } catch (IOException ex) {
            LOGGER.error("no se pudo leer el archivo de configuracion para setear la url del codigo QR" + ex);
            throw new QRCodeUtilException("no se pudo leer el archivo de configuracion para setear la url del codigo QR" + ex);
        }

        Map<EncodeHintType, ErrorCorrectionLevel> hintMap
                = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {

            return getBitMatrix(qrCodeWriter, urlQr, width, height, hintMap, formato);

        } catch (QRCodeUtilException e) {
            LOGGER.error("no se pudo crear los bytes del codigo QR" + e);
            throw e;
        }
    }

    private static InputStream getBitMatrix(QRCodeWriter qrCodeWriter, String urlQr, int width, int height, Map<EncodeHintType, ErrorCorrectionLevel> hintMap, String formato) throws QRCodeUtilException {
        try {
            BitMatrix byteMatrix;
            byteMatrix = qrCodeWriter.encode(urlQr, BarcodeFormat.QR_CODE, width, height, hintMap);

            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();
            ig2.setColor(Color.WHITE);
            ig2.fillRect(0, 0, width, height);
            ig2.setColor(Color.BLACK);
            int d = width / width;
            int bitWidth = byteMatrix.getWidth();
            int bitHeight = byteMatrix.getHeight();
            for (int i = 0; i < bitHeight; i++) {
                for (int j = 0; j < bitWidth; j++) {
                    boolean isSet = byteMatrix.get(j, i);
                    if (isSet) {
                        ig2.fillRect(d * j, d * i, d, d);
                    }
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, formato, out);
            out.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (WriterException e) {
            LOGGER.error("no se pudo crear los bytes del codigo QR" + e);
            throw new QRCodeUtilException("no se pudo crear los bytes del codigo QR" + e);
        } catch (IOException e) {
            LOGGER.error("no se pudo crear la imagen del QR" + e);
            throw new QRCodeUtilException("no se pudo crear la imagen del QR" + e);
        }

    }

    public static String obtenerConfiguracion() throws IOException {
        InputStream is = null;
        try {
            Properties properties = new Properties();
            is = SelloDigitalUtil.class.getResourceAsStream("/urlConf.properties");
            properties.load(is);

            return properties.getProperty("url.redireccion");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Error cerrando InputStream");
                }
            }
        }
    }
}
