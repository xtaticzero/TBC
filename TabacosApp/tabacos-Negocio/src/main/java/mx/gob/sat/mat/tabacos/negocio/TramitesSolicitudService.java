/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface TramitesSolicitudService {
    List<PaisOrigen> getLstOrigen() throws ServiceException;
    List<TipoContribuyente> getLstTipoContribuyente() throws ServiceException;
}
