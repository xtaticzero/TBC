/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import mx.gob.sat.mat.tabacos.ws.exception.ServiceException;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuRboSlc;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuseRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespDescarga;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSlcCodSeg;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSolDescarga;

/**
 *
 * @author root
 */
@WebService
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public interface PacContribuyente {

    @WebMethod(action = "solicitarCodigos")
    @WebResult(name = "TBCAcuRboSlc", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud/Acuse")
    TBCAcuRboSlc solicitarCodigos(@WebParam(name = "TBCSlcCodSeg", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud") TBCSlcCodSeg tbcSlcCodSeg) throws ServiceException;

    @WebMethod(action = "consultarEstatusSolicitud")
    @WebResult(name = "TBCRespConsulFolio", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta/Resp")
    TBCRespConsulFolio consultarEstatusSolicitud(@WebParam(name = "TBCConsulFolio", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta") TBCConsulFolio tbcConsulFolio) throws ServiceException;

    @WebMethod(action = "descargarCodigos")
    @WebResult(name = "TBCRespDescarga", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga/Resp")
    TBCRespDescarga descargarCodigos(@WebParam(name = "TBCSolDescarga", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga") TBCSolDescarga tbcSolDescarga) throws ServiceException;

    @WebMethod(action = "retroalimentarCodigos")
    @WebResult(name = "TBCAcuseRetroInfo", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion/Acuse")
    TBCAcuseRetroInfo retroalimentarCodigos(@WebParam(name = "TBCRetroInfo", targetNamespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion") TBCRetroInfo tbcRetroInfo) throws ServiceException;
}
