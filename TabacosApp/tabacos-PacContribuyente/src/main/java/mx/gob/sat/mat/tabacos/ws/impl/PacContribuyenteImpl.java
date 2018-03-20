/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.impl;

import java.util.Date;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.negocio.BitacoraTbcService;
import mx.gob.sat.mat.tabacos.negocio.PacService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.PacServiceException;
import mx.gob.sat.mat.tabacos.ws.PacContribuyente;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuRboSlc;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuseRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespDescarga;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSlcCodSeg;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSolDescarga;
import mx.gob.sat.mat.tabacos.ws.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author root
 */
@WebService(serviceName = "PacContribuyente", endpointInterface = "mx.gob.sat.mat.tabacos.ws.PacContribuyente")
@HandlerChain(file = "PacContribuyente_handler.xml")
public class PacContribuyenteImpl extends SpringBeanAutowiringSupport implements PacContribuyente {

    private static final Logger LOGGER = Logger.getLogger(PacContribuyenteImpl.class);
    
    @Resource
    private WebServiceContext wsContext;

    @Autowired
    private PacService pacService;

    @Autowired
    private BitacoraTbcService bitacoraTbcService;
    
    @Override
    public TBCAcuRboSlc solicitarCodigos(TBCSlcCodSeg tbcSlcCodSeg) throws ServiceException {
        try {
            registrarBitacora(MovimientosBitacoraEnum.SOLICITAR_CODIGOS_PAC);
            return pacService.solicitarCodigos(tbcSlcCodSeg);
        } catch (PacServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TBCRespConsulFolio consultarEstatusSolicitud(TBCConsulFolio tbcConsulFolio) throws ServiceException {
        try {
            return pacService.consultarEstatusSolicitud(tbcConsulFolio);
        } catch (PacServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TBCRespDescarga descargarCodigos(TBCSolDescarga tbcSolDescarga) throws ServiceException {
        try {
            return pacService.descargarCodigos(tbcSolDescarga);
        } catch (PacServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TBCAcuseRetroInfo retroalimentarCodigos(TBCRetroInfo tbcRetroInfo) throws ServiceException {
        try {
            registrarBitacora(MovimientosBitacoraEnum.PRODUCCION_CIGARRILLOS_PAC);
            return pacService.retroalimentarCodigos(tbcRetroInfo);
        } catch (PacServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void registrarBitacora(MovimientosBitacoraEnum movBitacora) {
        try {
            HttpSession session = ((HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST)).getSession();
            bitacoraTbcService.registroMovimientoBitacora(session, IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES, new Date(), new Date(), movBitacora);
        } catch (IllegalStateException ex) {
            LOGGER.error(ex);
        } catch (ClassCastException ex) {
            LOGGER.error(ex);
        } catch (Exception ex){
            LOGGER.error(ex);
        }
    }
}
