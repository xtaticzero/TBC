/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public final class DownloadFileUtil {

    private static final Logger LOGGER = Logger.getLogger(DownloadFileUtil.class);
    private static final int CAPACIDAD_BYTE = 1024;
    private static final int CONSTANTE_BASE = 16;
    private static final int CONSTANTE_0XFF = 0xff;
    private static final int CONSTANTE_0X100 = 0x100;

    private DownloadFileUtil() {
    }

    public static byte[] getBytesArchivo(String archivo) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            File file = new File(archivo);
            fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            inputStream.close();
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return fileBytes;
    }

    public static String calcularCheckSum(String metodo, String archivo) {
        StringBuffer sb = null;
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance(metodo);
            fis = new FileInputStream(archivo);
            byte[] dataBytes;
            dataBytes = new byte[CAPACIDAD_BYTE];

            int nread;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }

            byte[] mdbytes = md.digest();

            sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & CONSTANTE_0XFF) + CONSTANTE_0X100, CONSTANTE_BASE).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            IOUtils.closeQuietly(fis);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    LOGGER.error(ex);
                }
            }
        }
        return (sb == null ? null : sb.toString());
    }
}
