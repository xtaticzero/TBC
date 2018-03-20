/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Calendar;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusCargador;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusResolucion;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dao.CommonDao;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import mx.gob.sat.mat.tabacos.modelo.dao.RangoFolioDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.AcuseTransaccion;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
@Service
@Qualifier("commonService")
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private PaisOrigenDao paisOrigenDao;
    
    @Autowired
    @Qualifier("rangoFolioDaoImpl")
    private RangoFolioDao rangoFolioDaoImpl;
    
    private static final Logger LOGGER = Logger.getLogger(CommonServiceImpl.class);

    private static final String FORMATO_FOLIO = "%010d";

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public AcuseTransaccion crearSolicitud(Solicitud solicitud, String selloDigital, String cadenaOriginal) throws CommonServiceException {
        try {
            Solicitud solCreada = commonDao.createSolicitud(solicitud);
            solicitud.setIdSolicitud(solCreada.getIdSolicitud());

            Resolucion resolucion = new Resolucion();
            resolucion.setIdSolicitud(solicitud.getIdSolicitud());
            resolucion.setFecResolucion(solicitud.getFecSolicitud());
            resolucion.setFechaFin(solicitud.getFecSolicitud());
            resolucion.setFechaInicio(solicitud.getFecSolicitud());
            resolucion.setFolioFinal(0L);
            resolucion.setFolioInicial(0L);
            resolucion.setIdEstCargador(EstatusCargador.ESPERA.getKey().longValue());
            resolucion.setIdEstResolucion(EstatusResolucion.SOLICITADO.getKey().longValue());
            commonDao.createResolucion(resolucion);

            AcuseTransaccion at = new AcuseTransaccion();
            at.setFolioTransaccion(this.formatAcuse("", solicitud.getIdSolicitud()));

            Acuse acuse = new Acuse();
            acuse.setSerieAcuse(TipoAcuse.SOLICITUD);
            acuse.setIdSolicitud(solicitud.getIdSolicitud());
            acuse.setSelloDigital(selloDigital);
            acuse.setCadenaOriginal(cadenaOriginal);
            acuse.setFecCaptura(solicitud.getFecSolicitud());

            acuse = commonDao.createAcuse(acuse);
            at.setFolioAcuse(this.formatAcuse(acuse.getSerieAcuse(), acuse.getFolioAcuse()));
            at.setFechaTransaccion(solicitud.getFecSolicitud());

            return at;
        } catch (DaoException e) {
            String msgError = "ERROR AL SOLICITAR CODIGOS";
            LOGGER.error(msgError, e);
            throw new CommonServiceException(msgError, e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String crearAcuse(Acuse acuse) throws CommonServiceException {
        try {
            Acuse acuseCrado = commonDao.createAcuse(acuse);
            return this.formatAcuse(acuse.getSerieAcuse(), acuseCrado.getFolioAcuse());
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommonServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public List<Archivo> getArchivos(Long idSolicitud) throws CommonServiceException {
        try {
            return commonDao.getArchivos(idSolicitud);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommonServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public PaisOrigen getPaisById(Long idPais) throws CommonServiceException {
        try {
            return paisOrigenDao.getPaisById(idPais);
        } catch (PaisDaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommonServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public PaisOrigen getPaisByCodigo(String codigo) throws CommonServiceException {
        try {
            return paisOrigenDao.getPaisByCodigo(codigo);
        } catch (PaisDaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommonServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public RelacionTabProv getRelacionTabProv(String rfcProveedor, String rfcTabacalera) throws CommonServiceException {
        try {
            return commonDao.getRelacionTabProv(rfcProveedor, rfcTabacalera);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommonServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public AcuseTransaccion crearProduccionCigarro(ProduccionCigarros produccionCigarro, List<RangoFolio> lstRangoFolio) throws CommonServiceException {
        try {
            ProduccionCigarros produccionCigarroCreado = commonDao.createProduccionCigarro(produccionCigarro);
            produccionCigarro.setIdProdCigarro(produccionCigarroCreado.getIdProdCigarro());
            
            AcuseTransaccion at = new AcuseTransaccion();
            at.setFolioTransaccion(this.formatAcuse("", produccionCigarro.getIdProdCigarro()));

            Calendar cal = Calendar.getInstance();
            Resolucion resolucion;
            String formatAcuse = null;
            Acuse acuse;
            for (RangoFolio rf : lstRangoFolio) {
                rf.setIdProdCigarro(produccionCigarro.getIdProdCigarro());
                rangoFolioDaoImpl.createRangoFolio(rf);

                resolucion = commonDao.getResolucion(rf.getIdResolucion());
                acuse = new Acuse();
                acuse.setSerieAcuse(TipoAcuse.PRODUCCION);
                acuse.setIdSolicitud(resolucion.getIdSolicitud());
                acuse.setFecCaptura(cal.getTime());
                acuse = commonDao.createAcuse(acuse);
                formatAcuse = this.formatAcuse(acuse.getSerieAcuse(), acuse.getFolioAcuse());
            }
            at.setFolioAcuse(formatAcuse);

            return at;
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new CommonServiceException(e);
        } catch (RangoFolioDaoException ex) {
            LOGGER.error(ex);
            throw new CommonServiceException(ex);
        }
    }

    private String formatAcuse(String prefijo, Long folioAcuse) {
        return prefijo + String.format(FORMATO_FOLIO, folioAcuse);
    }
}
