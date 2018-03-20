/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Component("fileUtil")
public class FileUtil {

    private static final org.apache.log4j.Logger LOG
            = org.apache.log4j.Logger.getLogger(FileUtil.class);

    private File archivo;
    private File folder;
    private static final int ONE = 1;

    private static final int DIRECTORIO_WINDOWS = 1;
    private static final int DIRECTORIO_UNIX = 2;

    public boolean cearArchivo(InputStream contenidoArchivo, String rutaDirectorio, String nombreArchivo) {
        OutputStream outStream = null;
        boolean flgArchivoExiste = false;
        try {
            folder = new File(rutaDirectorio);
            flgArchivoExiste = folder.exists();
            LOG.error("La estructura de directorios " + rutaDirectorio + " existe:" + folder.exists());
            if (!flgArchivoExiste) {
                boolean fuecrado = folder.mkdirs();
                if (fuecrado) {
                    LOG.debug("La estructura de directorios " + rutaDirectorio + " se creo correctamente :");
                } else {
                    LOG.error("La estructura de directorios " + rutaDirectorio + " no pudo ser creada correctamente :");
                }
                creaRutaArchivo(rutaDirectorio, nombreArchivo);
            } else {
                creaRutaArchivo(rutaDirectorio, nombreArchivo);
            }
            byte[] buffer = new byte[contenidoArchivo.available()];
            contenidoArchivo.read(buffer);
            outStream = new FileOutputStream(archivo);
            outStream.write(buffer);
            return true;
        } catch (FileNotFoundException ex) {
            LOG.error("no se encuentra el archivo el la ruta especificada", ex);
            return false;
        } catch (IOException ex) {
            LOG.error("Error al procesar el archivo", ex);
            return false;
        } finally {
            try {
                IOUtils.closeQuietly(outStream);
                IOUtils.closeQuietly(contenidoArchivo);

                contenidoArchivo.close();
            } catch (IOException ex) {
                LOG.error("No se pudo cerrar el archivo correctamente ", ex);
            }
        }
    }

    public void creaRutaArchivo(String rutaArchivo, String nombreArchivo) {
        String[] carpetas = rutaArchivo.split("/");
        boolean flgArchivoCreado = false;
        try {
            if (carpetas != null && carpetas.length > 0) {
                archivo = new File(rutaArchivo + "/" + nombreArchivo);
                flgArchivoCreado = archivo.createNewFile();
            } else {
                /*Windows*/
                archivo = new File(rutaArchivo + "\\" + nombreArchivo);
                flgArchivoCreado = archivo.createNewFile();
            }

            if (flgArchivoCreado) {
                LOG.error("El archivo " + nombreArchivo + " se creo cprrectamente");
            }

        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    public String getRutaArchivoValida(String ruta, String... directorios) {

        boolean flgLastChar;
        char ultimocaracter;
        int tipoDeDirectorio;

        String rutaArchivoValida = ruta;

        ultimocaracter = rutaArchivoValida.charAt(rutaArchivoValida.length() - 1);

        tipoDeDirectorio = tipoDirectorio(rutaArchivoValida);

        switch (tipoDeDirectorio) {
            case DIRECTORIO_WINDOWS:
                flgLastChar = ultimocaracter == '\\';
                rutaArchivoValida = rutaArchivoValida.replace("c:", "");
                rutaArchivoValida = rutaArchivoValida.replace("C:", "");
                rutaArchivoValida = rutaArchivoValida.replace("\\", "/");
                return concatenarDirectoriosUnix(flgLastChar, rutaArchivoValida, directorios);

            case DIRECTORIO_UNIX:
                flgLastChar = ultimocaracter == '/';
                return concatenarDirectoriosUnix(flgLastChar, rutaArchivoValida, directorios);
            default:
                return rutaArchivoValida;

        }
    }

    private int tipoDirectorio(String rutaDirectorio) {
        String[] tipoDirectorio;
        String rutaArchivoValida = rutaDirectorio;

        tipoDirectorio = rutaArchivoValida.split("/");

        if (((tipoDirectorio != null) && (tipoDirectorio.length > ONE))) {
            return DIRECTORIO_UNIX;
        } else {
            tipoDirectorio = rutaArchivoValida.split("\\\\");
            if (((tipoDirectorio != null) && (tipoDirectorio.length > ONE))) {
                return DIRECTORIO_WINDOWS;
            } else {
                return 0;
            }
        }
    }

    private String concatenarDirectoriosUnix(boolean separadorDirectorio, String directorio, String... directorios) {
        StringBuilder rutaArchivoValida = new StringBuilder(directorio);
        
        if (separadorDirectorio) {
            for (String dir : directorios) {
                rutaArchivoValida.append(dir).append("/");
            }
            return rutaArchivoValida.toString();
        } else {
            rutaArchivoValida.append("/");
            for (String dir : directorios) {
                rutaArchivoValida.append(dir).append("/");
            }
            return rutaArchivoValida.toString();
        }

    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

}
