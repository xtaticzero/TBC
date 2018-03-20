/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author MMMF
 */
public interface ResolucionDao {
    String SQL_SELECT_RESOLUCION_FOLIOS = "SELECT IDRESOLUCION, IDSOLICITUD, IDESTRESOLUCION, FECRESOLUCION,"
            + " NUMSERVIDORPUBLICO, FOLIOINICIAL, FOLIOFINAL, FECHAINICIO, FECHAFIN, IDESTCARGADOR "
            + " FROM tbct_resolucion"
            + " WHERE ( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL);";
    
    String SQL_SELECT_RESOLUCION_FOLIOS_IDSOLICITUD = "SELECT IDRESOLUCION, IDSOLICITUD, IDESTRESOLUCION, FECRESOLUCION,"
            + " NUMSERVIDORPUBLICO, FOLIOINICIAL, FOLIOFINAL, FECHAINICIO, FECHAFIN, IDESTCARGADOR "
            + " FROM tbct_resolucion"
            + " WHERE IDSOLICITUD = ? AND ( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL)";
    
    String SQL_SELECT_RESOLUCION_TODOS_INTERMEDIOS= "SELECT IDRESOLUCION, IDSOLICITUD, IDESTRESOLUCION, FECRESOLUCION,"
            + " NUMSERVIDORPUBLICO, FOLIOINICIAL, FOLIOFINAL, FECHAINICIO, FECHAFIN, IDESTCARGADOR "
            + " FROM tbct_resolucion"
            + " WHERE  IDSOLICITUD = ? AND ( FOLIOINICIAL BETWEEN ? AND ? OR FOLIOFINAL BETWEEN ? AND ?)";
    
    
    List <Resolucion> buscarPorFoliosLimites (Long folioInicia, Long folioFinal,Long idSolicitud) throws DaoException;
    
}
