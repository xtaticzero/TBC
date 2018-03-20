/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface AutorizarSolicitudDao {        
    List<SolicitudesRegistradas> obtenerSolicitudesRegistradas(
            int tipo, String valor1, Date valor2) throws DaoException;    
    List<SolitudAutorizada> obtenerSolRegActual(
            int tipo, String valor1, Date valor2);    
    List<SelectItem>        getCombos(final String strSQL, 
            final String id, final String name);       
    int autorizarSolicitud(SolitudAutorizada info) throws DaoException;        
}
