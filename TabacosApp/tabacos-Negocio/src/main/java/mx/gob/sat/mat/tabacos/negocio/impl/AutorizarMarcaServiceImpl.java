/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.MarcaDao;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException;
import mx.gob.sat.mat.tabacos.negocio.AutorizarMarcaService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Service
@Qualifier("autorizarMarcaService")
public class AutorizarMarcaServiceImpl implements AutorizarMarcaService {

    @Autowired
    @Qualifier("marcaDaoImpl")
    private MarcaDao marcaDaoImpl;

    @Autowired
    private ReporterService reporterService;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(
            AutorizarMarcaServiceImpl.class);

    /**
     *
     * @param strSQL
     * @param id
     * @param valor
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException
     */
    @Override
    public List<SelectItem> obtenerCombos(String strSQL, String id,
            String valor) throws ServiceException {
        try {
            return marcaDaoImpl.getCombosMarcas(strSQL, id, valor);
        } catch (MarcaDaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     *
     * @param tipo
     * @param valor1
     * @param valor2
     * @return
     */
    @Override
    public List<SolicitudesRegistradas> obtenerSolicitudes(int tipo,
            String valor1, Date valor2) {
        return marcaDaoImpl.
                obtenerSolicitudesRegistradas(tipo, valor1, valor2);
    }

    /**
     *
     * @param info
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException
     */
    @Override
    public int autorizarSolicitud(MarcaResolucion info) throws ServiceException {
        try {
            return marcaDaoImpl.autorizarSolicitud(info);
        } catch (MarcaDaoException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     *
     * @param info
     * @return
     */
    @Override
    public byte[] generarAcuse(MarcaResolucion info) {
        try {
            Map parameteros = new HashMap();

            parameteros.put("clave", info.getClave());
            parameteros.put("rfc", info.getRfc());
            parameteros.put("fecha", info.getFechaInicio());
            parameteros.put("servpub", info.getServidorPublicoAutorizanteInfo());
            parameteros.put("estatus", info.getEstatusResolucionInfo());
            parameteros.put("marca", info.getMarca());
            parameteros.put("razonSocial", info.getRazonsocial());

            return reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_AUTMARCA, ARCHIVO_PDF, parameteros, null);
        } catch (ReporterJasperException ex) {
            LOGGER.error(ex);
            return null;
        }
    }

    @Override
    public MarcaResolucion buscaMarcaXClave(String clave) throws ServiceException {
        try {
            List<MarcaResolucion> lstMarcas = marcaDaoImpl.buscaMarcaResolucion(clave, null, null, null);

            for (MarcaResolucion item : lstMarcas) {
                return item;
            }
            
            return null;

        } catch (MarcaDaoException ex) {
            LOGGER.error(ex);
            throw new ServiceException(ex);
        }
    }
}
