/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucionEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;

/**
 *
 * @author MMMF
 */
public interface SolicitudService {

    List<Solicitud> buscarPorProveedor(Long idProveedor) throws SolicitudServiceException;

    List<Solicitud> buscarPorTabacalera(Long idTabacalera) throws SolicitudServiceException;

    List<SolicitudResolucion> buscarSolicitudesTabacalera(String rfcTabacalera) throws SolicitudServiceException;

    List<AutorizacionResol> buscaSolicitudAutorizada(String rfcTabacalera, Long idSolicitud, EstadoResolucionEnum estadoResolucion) throws SolicitudServiceException;

    List<PaisOrigen> getLstOrigen() throws SolicitudServiceException;

    List<TipoContribuyente> getLstTipoContribuyente() throws SolicitudServiceException;

    Integer saveSolicitud(Solicitud solicitud) throws SolicitudServiceException;
    
    String generaCadenaOriginal(String rfc,Long cantidadSolicitada,String nombreArchivo) throws SolicitudServiceException;
    
    List<PaisOrigen> getLstOrigen(Long... idPaises) throws SolicitudServiceException;
    
    List<SolicitudResolucion> asignarPaisOrigen(List<PaisOrigen> lstPaisOrigen,List<SolicitudResolucion> lstSolicitudes);
}
