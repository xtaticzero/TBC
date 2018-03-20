/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;

/**
 *
 * @author MMMF
 */
public interface ResolucionService {
    
    
    List <Resolucion> buscarPorFoliosLimites (Long folioInicia, Long folioFinal, Long idSolicitud ) throws BusinessException;
    
}
