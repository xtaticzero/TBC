/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public interface ValidadorRangosService {
    String RANGO_YA_CARGADO = "ERROR: EL RANGO YA HABIA SIDO CARGADO";
    String INCORRECTO_INFERIOR_SUPERIOR = "ERROR: INCORRECTA LA ASIGNACION DE RANGO INFERIOR Y SUPERIOR";
    String INTERSECCION = "ERROR: LOS RANGOS SE INTERSECTAN";
    String NO_PERTENECE = "ERROR: NO PERTENECE AL RANGO";
    
    List<RangoFolio> generarRangosProduccion(String rfcTabacalera, String rfcProveedor, List<RangoFolio> lstACargar) throws RangosException;
    
    List<RangoFolio> generarRangosProduccion(String rfcTabacalera, List<RangoFolio> lstACargar) throws RangosException;
    
    List<RangoFolio> generarRangosInvalidos(String rfcTabacalera, String rfcProveedor, List<RangoFolio> lstACargar) throws RangosException;
    
    List<RangoFolio> generarRangosInvalidos(String rfcTabacalera, List<RangoFolio> lstACargar) throws RangosException;
}
