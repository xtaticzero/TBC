/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface TabacaleraService {

    Tabacalera getTabacaleraByRfc(String rfc) throws TabacaleraServiceException;

    List<Tabacalera> getTabacaleras() throws TabacaleraServiceException;

    List<String> buscaTabacalerasLikeRfc(String rfc) throws TabacaleraServiceException;

    List<String> buscaTabacalerasActivas() throws TabacaleraServiceException;

    List<Tabacalera> buscaTabacaleras() throws TabacaleraServiceException;

    List<Tabacalera> consultaTabacaleras() throws TabacaleraServiceException;
    
    List<Tabacalera> consultaContribuyentesPorRfc(String rfc) throws TabacaleraServiceException;
    
    List<Tabacalera> selectedRfcClienteAlta() throws TabacaleraServiceException;

    List<Tabacalera> selectedRfcClienteCambio(Proveedor proveedor) throws TabacaleraServiceException;
    
    List<Tabacalera> buscaReporteTabacalera(String rfc,Date fechaInicio,Date fechafin,ABCEnum tipoReporte) throws TabacaleraServiceException;  
}
