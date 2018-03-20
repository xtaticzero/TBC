/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusTabacaleraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.HorasDeConsultaEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.TabacaleraDao;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TabacaleraDaoException;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Service
@Qualifier("tababacaleraService")
public class TabacaleraServiceImpl implements TabacaleraService {

    private static final Logger LOGGER = Logger.getLogger(TabacaleraServiceImpl.class);
    
    private static final int CASE_ALTA = 1;
    private static final int CASE_BAJA = 2;
    private static final int CASE_CAMBIO = 3;
    private static final int MINIMO_TAMANIO = 10;

    @Autowired
    @Qualifier("tabacaleraDaoImpl")
    private TabacaleraDao tabacaleraDaoImpl;

    /**
     *
     * @param rfc
     * @return
     * @throws TabacaleraServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Tabacalera getTabacaleraByRfc(String rfc) throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.getTabacaleraByRFC(rfc);
        } catch (TabacaleraDaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new TabacaleraServiceException(e);
        }
    }

    @Override
    public List<Tabacalera> getTabacaleras() throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.getTabacaleras();
        } catch (TabacaleraDaoException e) {
            LOGGER.error(e);
            throw new TabacaleraServiceException(e);
        }
    }

    @Override
    public List<String> buscaTabacalerasLikeRfc(String rfc) throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.buscaTabacalerasLikeRfc(rfc, null);
        } catch (TabacaleraDaoException ex) {
            throw new TabacaleraServiceException(ex);
        }
    }

    @Override
    public List<String> buscaTabacalerasActivas() throws TabacaleraServiceException {
        try {
            List<String> lstTabXRfc = new ArrayList<String>();
            for(Tabacalera tab:tabacaleraDaoImpl.buscaTabacaleras(EstatusTabacaleraEnum.ACTIVO)){
                lstTabXRfc.add(tab.getRfc());
            }
            return lstTabXRfc;
        } catch (TabacaleraDaoException ex) {
            throw new TabacaleraServiceException(ex);
        }
    }

    /**
     *
     * @return
     * @throws TabacaleraServiceException
     */
    @Override
    public List<Tabacalera> buscaTabacaleras() throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.buscaTabacaleras(null);
        } catch (TabacaleraDaoException ex) {
            throw new TabacaleraServiceException(ex);
        }
    }
    
    /**
     * 
     * @return List<Tabacalera> 
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Tabacalera> consultaTabacaleras() throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.consultaTabacaleras();
        } catch (TabacaleraDaoException e) {
            LOGGER.error("ERROR - Al consultar las Tabacaleras : ", e);
            throw new TabacaleraServiceException(e);
        }
    }
    
    
    /**
     * 
     * @param rfc
     * @return 
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Tabacalera> consultaContribuyentesPorRfc(String rfc) throws TabacaleraServiceException {
         try {
            return tabacaleraDaoImpl.consultaContribuyentesPorRfc(rfc);
        } catch (TabacaleraDaoException e) {
            LOGGER.error("ERROR - Al consultar los Contribuyentes por RFC : ", e);
            throw new TabacaleraServiceException(e);
        }
    }
    
    /**
     *
     * @return 
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Tabacalera> selectedRfcClienteAlta() throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.selectedRfcClienteAlta();
        } catch (TabacaleraDaoException e) {
            LOGGER.error("ERROR - Al consultar los RFC del Cliente - Alta Proveedor " + e.getMessage(), e);
            throw new TabacaleraServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List<Tabacalera>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Tabacalera> selectedRfcClienteCambio(Proveedor proveedor) throws TabacaleraServiceException {
        try {
            return tabacaleraDaoImpl.selectedRfcClienteCambio(proveedor);
        } catch (TabacaleraDaoException e) {
            LOGGER.error("ERROR - Al consultar los RFC del cliente - Cambio Proveedor" + e.getMessage(), e);
            throw new TabacaleraServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<Tabacalera> buscaReporteTabacalera(String rfc, Date fechaInicio, Date fechafin, ABCEnum tipoReporte) throws TabacaleraServiceException {
        try {
            boolean flgRepXRfc = evaluaRepXRFC(rfc, tipoReporte);
            boolean flgRepXFechas = evaluaRepXFechas(fechaInicio, fechafin, tipoReporte);

            if (flgRepXRfc) {
                return obtenerDatosReporte(rfc, null, null, tipoReporte);
            }
            if (flgRepXFechas) {
                Date fInicio;
                Date fFin;

                fInicio = Utilerias.chageFecha(fechaInicio, HorasDeConsultaEnum.INICIO_DE_DIA);
                fFin = Utilerias.chageFecha(fechafin, HorasDeConsultaEnum.FIN_DE_DIA);

                return obtenerDatosReporte(null, fInicio, fFin, tipoReporte);
            } else {
                throw new ReporteServiceException("Los parametros minimos de busqueda no son correctos");
            }
        } catch (ReporteServiceException sex) {
            throw new TabacaleraServiceException("Los parametros minimos de busqueda no son correctos", sex);
        }
    }

    private boolean evaluaRepXRFC(String rfc, ABCEnum tipoReporte) {
        return (rfc != null && rfc.length() > MINIMO_TAMANIO && tipoReporte != null);
    }

    private boolean evaluaRepXFechas(Date fechaInicio, Date fechafin, ABCEnum tipoReporte) {
        return (fechaInicio != null && fechafin != null && tipoReporte != null);
    }

    private List<Tabacalera> obtenerDatosReporte(String rfc, Date fechaInicio, Date fechafin, ABCEnum tipoReporte) throws ReporteServiceException {
        try {
            switch (tipoReporte.getId()) {
                case CASE_ALTA:
                    return tabacaleraDaoImpl.buscaTabacalerasXMovimiento(rfc, fechaInicio, fechafin, ABCEnum.ALTA, EstatusTabacaleraEnum.ACTIVO);

                case CASE_BAJA:
                    return tabacaleraDaoImpl.buscaTabacalerasXMovimiento(rfc, fechaInicio, fechafin, ABCEnum.BAJA, EstatusTabacaleraEnum.BAJA);

                case CASE_CAMBIO:
                    return tabacaleraDaoImpl.buscaTabacalerasXMovimiento(rfc, fechaInicio, fechafin, ABCEnum.CAMBIO, EstatusTabacaleraEnum.ACTIVO);

                default:
                    return new ArrayList<Tabacalera>();

            }
        } catch (TabacaleraDaoException ex) {
            throw new ReporteServiceException(ex);
        }
    }

}
