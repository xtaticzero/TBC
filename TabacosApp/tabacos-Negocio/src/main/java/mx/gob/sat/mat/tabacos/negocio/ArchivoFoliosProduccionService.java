package mx.gob.sat.mat.tabacos.negocio;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ArchivoFoliosServiceException;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public interface ArchivoFoliosProduccionService {

    List<RangoFolio> leerArchivoFolios(InputStream inputStream, String nombreArchivo) throws ArchivoFoliosServiceException;
    
}
