/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion;

/**
 *
 * @author MMMF
 */
public interface RangoFolioService {

    List<RangoFolio> validaRango(long folioInicial, long folioFinal, Long idResolucion) throws RangoFolioServiceExcepcion;

    RangoFolio validaRangoFolioPorRfc(String rfcTabacalera, long folioInicial, long folioFinal) throws RangoFolioServiceExcepcion;

    void guardar(RangoFolio rangoFolio) throws RangoFolioServiceExcepcion;
    
    List<RangoFolio> obtenerRangoFoliosListActivosCancelados(final List<RangoFolio> rangoFoliosCanceladosList,
            final long folioInicial,
            final long folioFinal) throws RangoFolioServiceExcepcion;
    
    Map<String, Object>  validaRangoSerie (String rfcTabacalera, List<RangoFolio> rangos)throws RangoFolioServiceExcepcion ;
    
    List<RangoFolio> guardarRangosFolios(List<RangoFolio> rangoFoliosListAux, ProduccionCigarros produccion) throws RangoFolioServiceExcepcion;

}
