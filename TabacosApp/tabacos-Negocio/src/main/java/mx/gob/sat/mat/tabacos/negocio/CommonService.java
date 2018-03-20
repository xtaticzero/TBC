/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.AcuseTransaccion;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public interface CommonService {

    AcuseTransaccion crearSolicitud(Solicitud solicitud, String selloDigital, String cadenaOriginal) throws CommonServiceException;

    String crearAcuse(Acuse acuse) throws CommonServiceException;

    List<Archivo> getArchivos(Long idSolicitud) throws CommonServiceException;

    PaisOrigen getPaisById(Long idPais) throws CommonServiceException;

    PaisOrigen getPaisByCodigo(String codigo) throws CommonServiceException;

    RelacionTabProv getRelacionTabProv(String rfcProveedor, String rfcTabacalera) throws CommonServiceException;

    AcuseTransaccion crearProduccionCigarro(ProduccionCigarros produccionCigarro, List<RangoFolio> lstRangoFolio) throws CommonServiceException;

}
