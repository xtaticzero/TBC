/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dao.CommonDao;
import mx.gob.sat.mat.tabacos.modelo.dao.RangoFolioDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;
import mx.gob.sat.mat.tabacos.negocio.RetroCodigosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RetroCodigosException;
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
@Qualifier("retroCodigosService")
public class RetroCodigosServiceImpl implements RetroCodigosService {

    @Autowired
    private CommonDao commonDao;
    
    @Autowired
    @Qualifier("rangoFolioDaoImpl")
    private RangoFolioDao rangoFolioDaoImpl;

    private static final Logger LOGGER = Logger.getLogger(RetroCodigosServiceImpl.class);

    private static final String FORMATO_FOLIO = "%010d";

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String guardarProduccion(Map<ProduccionCigarros, List<RangoFolio>> mapProduccion) throws RetroCodigosException {
        return this.guardarProdDest(mapProduccion, TipoAcuse.PRODUCCION);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String guardarDestruccion(Map<ProduccionCigarros, List<RangoFolio>> mapDestruccion) throws RetroCodigosException {
        return this.guardarProdDest(mapDestruccion, TipoAcuse.DESPERDICIO);
    }

    private String guardarProdDest(Map<ProduccionCigarros, List<RangoFolio>> prodDest, TipoAcuse tipoAcuse) throws RetroCodigosException {
        try {
            Calendar cal = Calendar.getInstance();
            String formatAcuse = null;
            ProduccionCigarros produccionCigarro;
            for (Map.Entry<ProduccionCigarros, List<RangoFolio>> map : prodDest.entrySet()) {
                produccionCigarro = commonDao.createProduccionCigarro(map.getKey());

                for (RangoFolio rf : map.getValue()) {
                    rf.setFechaRetroalimentacion(cal.getTime());
                    rf.setIdEstatus(EstatusEnum.ALTA);
                    rf.setEstado(rf.getIdEstatus().intValue());
                    rf.setIdProdCigarro(produccionCigarro.getIdProdCigarro());
                    rangoFolioDaoImpl.createRangoFolio(rf);

                    formatAcuse = this.crearAcuse(tipoAcuse, rf.getIdSolicitud(), null, null);
                }
            }
            return formatAcuse;
        } catch (DaoException e) {
            String msgError = "ERROR AL GUARDAR RETROALIMENTACION: ";
            LOGGER.error(msgError + e.getMessage(), e);
            throw new RetroCodigosException(msgError, e);
        } catch (RangoFolioDaoException ex) {
            String msgError = "ERROR AL GUARDAR RETROALIMENTACION: ";
            LOGGER.error(msgError + ex.getMessage(), ex);
            throw new RetroCodigosException(msgError, ex);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public String guardarInvalido(Map<CodigoInvalido, List<RangoFolio>> mapInvalido) throws RetroCodigosException {
        try {
            Calendar cal = Calendar.getInstance();
            String formatAcuse = null;
            CodigoInvalido codigoInvalido;
            for (Map.Entry<CodigoInvalido, List<RangoFolio>> map : mapInvalido.entrySet()) {
                codigoInvalido = map.getKey();
                for (RangoFolio rf : map.getValue()) {
                    codigoInvalido.setIdRangoFolio(rf.getIdRangoFolios());
                    codigoInvalido.setFolioInicial(rf.getFolioInicial());
                    codigoInvalido.setFolioFinal(rf.getFolioFinal());
                    codigoInvalido.setFecRegistro(cal.getTime());
                    codigoInvalido.setFecCaptura(cal.getTime());
                    commonDao.createCodigoInvalido(codigoInvalido);

                    formatAcuse = this.crearAcuse(TipoAcuse.INVALIDO, rf.getIdSolicitud(), null, null);
                }
            }
            return formatAcuse;
        } catch (DaoException e) {
            String msgError = "ERROR AL GUARDAR CODIGOS NO VALIDOS: ";
            LOGGER.error(msgError + e.getMessage(), e);
            throw new RetroCodigosException(msgError, e);
        }
    }

    private String crearAcuse(TipoAcuse tipoAcuse, Long idSolicitud, String selloDigital, String cadenaOriginal) throws RetroCodigosException {
        try {
            Calendar cal = Calendar.getInstance();
            Acuse acuse = new Acuse();
            acuse.setSerieAcuse(tipoAcuse);
            acuse.setIdSolicitud(idSolicitud);
            acuse.setSelloDigital(selloDigital);
            acuse.setCadenaOriginal(cadenaOriginal);
            acuse.setFecCaptura(cal.getTime());

            acuse = commonDao.createAcuse(acuse);
            return this.formatAcuse(acuse.getSerieAcuse(), acuse.getFolioAcuse());
        } catch (DaoException e) {
            String msgError = "ERROR AL CREAR EL ACUSE: ";
            LOGGER.error(msgError + e.getMessage(), e);
            throw new RetroCodigosException(msgError, e);
        }
    }

    private String formatAcuse(String prefijo, Long folioAcuse) {
        return prefijo + String.format(FORMATO_FOLIO, folioAcuse);
    }
}
