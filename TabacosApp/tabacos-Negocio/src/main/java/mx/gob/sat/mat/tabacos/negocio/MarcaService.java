/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudAltaMarca;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.FileUtil;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface MarcaService {

    Marcas insertaMarca(Marcas info, InputStream contenidoArchivo, String nombreArchivo) throws MarcaServiceException;

    Marcas modificaMarca(Marcas info, String claveBusq) throws MarcaServiceException;

    int borraMarca(Marcas info) throws MarcaServiceException;

    List<SelectItem> consultaMarcas() throws MarcaServiceException;

    List<SelectItem> consultaTabacaleras();

    boolean existeMarca(Marcas info);

    String obtieneMarca(String idMarca);

    String getDirectorioBase();

    String getDirDoctosSolicitudAlta();

    FileUtil getFileUtil();

    boolean guardaArchivoMarca(InputStream contenidoArchivo, String rutaDirectorio, String nombreArchivo);

    SolicitudAltaMarca generaSolicitudAltaMarca(Marcas marcaSolicitada, InputStream contenidoArchivo, String nombreArchivo, String cadenaOriginal, String selloDigital) throws MarcaServiceException;

    String getRutaDocumentacion(String rfcTabacalera, String claveMarca);

    String generaCadenaOriginal(String rfc, String marca, String clave);

    byte[] generarAcuse(Marcas info);

    Marcas buscaMarca(String nombre) throws MarcaServiceException;

    boolean isClaveRepetida(String clave) throws MarcaServiceException;

    boolean isNombreRepetido(String nomMarca) throws MarcaServiceException;
    
    List<Marcas> getMarcasByRFCTabacalera(String rfcTabacalera) throws MarcaServiceException;

    List<Marcas> getMarcas() throws MarcaServiceException;
    
    List<Marcas> selectedMarcas(Long idTabacalera) throws MarcaServiceException;
}
