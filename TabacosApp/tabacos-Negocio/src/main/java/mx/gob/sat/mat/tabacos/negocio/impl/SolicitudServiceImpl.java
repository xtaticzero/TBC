/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucionEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao;
import mx.gob.sat.mat.tabacos.modelo.dao.TipoContribuyenteDao;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.SolicitudDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TipoContribuyenteDaoException;
import mx.gob.sat.mat.tabacos.negocio.SolicitudService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MMMF
 */
@Service
@Qualifier("solicitudService")
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    @Qualifier("solicitudDaoImpl")
    private SolicitudDao solicitudDao;

    @Autowired
    @Qualifier("paisDaoImpl")
    private PaisOrigenDao paisDaoImpl;

    @Autowired
    @Qualifier("tipoContribuyenteDao")
    private TipoContribuyenteDao tipoContribuyenteDao;

    private static final String CADENA_ORIGINAL_SEPARADOR = "|";

    @Override
    public List<Solicitud> buscarPorProveedor(Long idProveedor) throws SolicitudServiceException {
        List<Solicitud> solicitudes;
        try {
            solicitudes = solicitudDao.buscarPorProveedor(idProveedor);
        } catch (SolicitudDaoException e) {
            throw new SolicitudServiceException(e);
        }
        return solicitudes;
    }

    @Override
    public List<Solicitud> buscarPorTabacalera(Long idTabacalera) throws SolicitudServiceException {
        List<Solicitud> solicitudes;
        try {
            solicitudes = solicitudDao.buscarPorTabacalera(idTabacalera);
        } catch (SolicitudDaoException e) {
            throw new SolicitudServiceException(e);
        }
        return solicitudes;
    }

    public SolicitudDao getSolicitudDao() {
        return solicitudDao;
    }

    public void setSolicitudDao(SolicitudDao solicitudDao) {
        this.solicitudDao = solicitudDao;
    }

    @Override
    public List<SolicitudResolucion> buscarSolicitudesTabacalera(String rfcTabacalera) throws SolicitudServiceException {
        try {
            return solicitudDao.buscarSolicitudes(rfcTabacalera);
        } catch (SolicitudDaoException ex) {
            throw new SolicitudServiceException(ex);
        }
    }

    @Override
    public List<AutorizacionResol> buscaSolicitudAutorizada(String rfcTabacalera, Long idSolicitud, EstadoResolucionEnum estadoResolucion) throws SolicitudServiceException {
        try {
            return solicitudDao.buscarSolicitudesAutorizadas(rfcTabacalera, idSolicitud, estadoResolucion.getKey());
        } catch (SolicitudDaoException daoex) {
            throw new SolicitudServiceException(daoex);
        }
    }

    @Override
    public Integer saveSolicitud(Solicitud solicitud) throws SolicitudServiceException {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<PaisOrigen> getLstOrigen() throws SolicitudServiceException {
        try {
            return paisDaoImpl.selectedOrigen();
        } catch (PaisDaoException pe) {
            throw new SolicitudServiceException(pe);
        }
    }

    @Override
    public List<TipoContribuyente> getLstTipoContribuyente() throws SolicitudServiceException {
        try {
            return tipoContribuyenteDao.getLstTipoContribuyente();
        } catch (TipoContribuyenteDaoException pe) {
            throw new SolicitudServiceException(pe);
        }
    }

    @Override
    public String generaCadenaOriginal(String rfc,Long cantidadSolicitada,String nombreArchivo) throws SolicitudServiceException {
        String cadenaOriginal = "";
        
        if (rfc != null && cantidadSolicitada != null) {
            cadenaOriginal = cadenaOriginal.concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal = cadenaOriginal.concat(rfc).concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal = cadenaOriginal.concat(FechaUtil.formatFecha(new Date(), FechaUtil.FORMATO_CADENA_ORIGINAL)).concat(CADENA_ORIGINAL_SEPARADOR);
            cadenaOriginal = cadenaOriginal.concat(cantidadSolicitada.toString()).concat(CADENA_ORIGINAL_SEPARADOR);
        }
        if(nombreArchivo!=null){
            cadenaOriginal = cadenaOriginal.concat(nombreArchivo).concat(CADENA_ORIGINAL_SEPARADOR);
        }

        return cadenaOriginal;
    }

    @Override
    public List<PaisOrigen> getLstOrigen(Long... idPaises) throws SolicitudServiceException {
        try {
            if (idPaises != null) {
                return paisDaoImpl.getLstPaisOrigen(idPaises);
            }
        } catch (PaisDaoException pe) {
            throw new SolicitudServiceException(pe);
        }
        return null;
    }
    
    @Override
    public List<SolicitudResolucion> asignarPaisOrigen(List<PaisOrigen> lstPaisOrigen,List<SolicitudResolucion> lstSolicitudes){
        List<SolicitudResolucion> lstSolicitud = new ArrayList<SolicitudResolucion>();
        
        for(SolicitudResolucion solicitud:lstSolicitudes){
            for(PaisOrigen pais:lstPaisOrigen){
                if(solicitud.getIdPaisOrigen().equals(pais.getIdPais())){
                    solicitud.setPaisOrigen(pais.getDescCorta());
                    lstSolicitud.add(solicitud);
                }
            }
        }
        return lstSolicitud;
    }
}
