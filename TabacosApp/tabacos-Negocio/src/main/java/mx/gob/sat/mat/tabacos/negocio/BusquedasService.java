/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.DatosBusqueda;
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;

/**
 *
 * @author MMMF
 */
public interface BusquedasService {

    List<Historico> listarHistoricosContribuyente() throws BusinessException;

    List<Historico> listarHistoricosContribuyente(String rfc) throws BusinessException;

    List<DatosBusqueda> busquedaPorFiltros(DatosBusqueda filtro) throws BusinessException;
}
