/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public interface ArchivoRangosFolioService {

    List<RangoFolio> leerArchivoFolios(InputStream inputStream, String nombreArchivo) throws RangosException;
    List<RangoFolio> separaRangosPorSolicitud(List<RangoFolio> foliosTotales);
    List<List<RangoFolio>> rangosFolioSeparadosPorSolicitud (List<RangoFolio> foliosTotales);
    
}
