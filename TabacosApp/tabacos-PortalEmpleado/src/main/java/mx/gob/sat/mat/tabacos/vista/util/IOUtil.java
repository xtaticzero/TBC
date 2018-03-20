/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.mat.tabacos.vista.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Utiler&iacute;a para el menejo de flujos de entrada y/o salida
 * @author Rafael P&eacute;rez Rivera
 */
public final class IOUtil {
    /**
     * Logger de la clase.
     */
    protected static final Logger LOGGER = Logger.getLogger(IOUtil.class);

    private IOUtil() {
    }
    
    public static final String RUTA_REPORTE_RETRO_CONTRIB = "/siat/marbetes/";
    
    public static final String NOMBRE_REPORTE_RETRO_CONTRIB = "ReporteRetroContrib.xls";
    
    public static final String NOMBRE_REPORTE_DETALLE_RETRO_CONTRIB = "ReporteDetalleRetroContrib.xls";
    
    /**
     * Convierte un InputStream a FileInputStream
     * @param inputStream a convertir
     * @param nombreArchivo Nombre del archivo a aconvertir
     * @return FileInputStream con el contenido del archivo
     * @throws FileNotFoundException En caso que el archivo no se encuentre
     * @throws IOException En caso de que haya un problema al leer el flujo
     */
    public static FileInputStream getFileInputStreamToInputStream(InputStream inputStream,
                                                                        String nombreArchivo) throws IOException {
        OutputStream salida = null;
        File f = null;
        try {
            f = new File(nombreArchivo);
            salida= new FileOutputStream(f);
            int len;
            final int numByte = 1024;
            byte[] buf = new byte[numByte];

            while ((len = inputStream.read(buf)) > 0) {
                salida.write(buf, 0, len);
            }
            
            return new FileInputStream(f);

        } finally {
            IOUtil.closeOutputStream(salida);
            IOUtil.closeInputStream(inputStream);
            
            eliminaFile(f);
        }
    }
    
    public static void eliminaFile(final File file) {
        if(file != null) {
            boolean borrarArchivo = false;
            
            borrarArchivo = file.delete();
            
            if (!borrarArchivo) {
                LOGGER.error("Error al borrar el archivo");
            }
        }
    }

    public static void closeOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                LOGGER.error("Error cerrando OutputStream");
            }
        }
    }

    /**
     * Cierra un FileInputStream
     * @param fis a cerrar
     */
    public static void closeFileInputStream(FileInputStream fis) {
        if (fis != null) {
            closeInputStream(fis);
        }
    }

    /**
     * Cierra un InputStream
     * @param inputStream a cerrar
     */
    public static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error cerrando InputStream");
        }
    }
    
    public static String getValueFromPropertyQuartz(String key){
        
        Properties prop = new Properties();
        InputStream input = null;
        String valor = null;
         
        try {
            input = IOUtil.class.getResourceAsStream("IOUtil.properties");
            prop.load(input);
            valor = prop.getProperty(key);

        } catch (IOException ex) {
            LOGGER.error("Error al leer archivo IOUtil o una de sus llaves");
        } finally {
            if (input != null) {
                closeInputStream(input);
            }
        }
        return valor;
    }
    
    public static String getProperty(String property){
        String propiedad = null;
        Properties properties = new Properties();
        InputStream stream = null;
        try{
            stream = IOUtil.class.getResourceAsStream("IOUtil.properties");
            properties.load(stream);
            propiedad = properties.getProperty(property);
        }catch(IOException e){
            LOGGER.error("Hubo un error al cargar los properties",e);
        }finally{
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException ex) {
                    LOGGER.error("Hubo un error al cerrar el inputStream",ex);
                }
            }
        }
        return propiedad;
    }
}
