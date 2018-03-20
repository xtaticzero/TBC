/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import static mx.gob.sat.mat.tabacos.constants.Constantes.EXCEL_ANTES_2007;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.MarcaReporteDao;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesMarcas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;
import mx.gob.sat.mat.tabacos.negocio.MarcaReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author emmanuel
 */
@Service
@Qualifier("marcaReporteService")
public class MarcaReporteServiceImpl implements MarcaReporteService {

    private static final Logger LOGGER = Logger.getLogger(MarcaReporteServiceImpl.class);
    @Autowired
    private ReporterService reporterService;
    @Autowired
    private MarcaReporteDao marcaReporteDAO;
    private Map parameters;

    private static final int DAY_10 = 10;
    private static final int DAY_1 = 1;

    private void initReporte(ReportesFiltroBase filtro) {

        Calendar fecha = new GregorianCalendar();
        parameters = new HashMap();
        String dia = ((fecha.get(Calendar.DAY_OF_MONTH) < DAY_10)
                ? "0".concat(String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)))
                : String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)));
        String mes = ((fecha.get(Calendar.MONTH) + 1) < DAY_10)
                ? "0".concat(String.valueOf(fecha.get(Calendar.MONTH) + DAY_1))
                : String.valueOf(fecha.get(Calendar.MONTH) + 1);
        String fechaActual = dia + "/" + mes + "/" + fecha.get(Calendar.YEAR);
        parameters.put("rfcContribuyente", filtro.getRfc());

        if (filtro.getFechaInicio() == null || filtro.getFechaFin() == null||!filtro.getRfc().isEmpty()) {
            parameters.put("fechainicio", null);
            parameters.put("fechafin", null);
        }else{
            parameters.put("fechainicio", filtro.convertDateToString(filtro.getFechaInicio()));
            parameters.put("fechafin", filtro.convertDateToString(filtro.getFechaFin()));
        }

        parameters.put("fechaDescarga", fechaActual);
        parameters.put("nombreRazonSocial", filtro.getRazonSocial());
    }

    @Override
    public byte[] makeReportMarcas(ReportesTabacosEnum reporte, ReportesFiltroBase filtro, int tipo)
            throws ServiceException {
        byte[] retorno = null;
        try {
            initReporte(filtro);
            ReportesMarcas item = null;

            switch (filtro.getMovimiento()) {
                //Alta
                case ALTA:
                    item = marcaReporteDAO.getAltaMarcas(filtro);
                    break;
                //Modificacion
                case CAMBIO:
                    item = marcaReporteDAO.getModificacionMarcas(filtro);
                    break;
                //Baja
                case BAJA:
                    item = marcaReporteDAO.getBajaMarcas(filtro);
                    break;
                default:
                    break;
            }

            parameters.put("opcionReporte", filtro.opcionReporte(" DE MARCAS"));
            if (item != null && !item.getMarcas().isEmpty()) {
                //PDF
                if (tipo == 1) {
                    try {
                        retorno = reporterService.makeReport(reporte, ARCHIVO_PDF, parameters, item.getMarcas());
                    } catch (ReporterJasperException ex) {
                        LOGGER.error(ex);
                    }
                } else {
                    try {
                        retorno = reporterService.makeReport(reporte, EXCEL_ANTES_2007, parameters, item.getMarcas());
                    } catch (ReporterJasperException ex) {
                        LOGGER.error(ex);
                    }
                }
            }

        } catch (ReporteDaoException ex) {
            LOGGER.error(ex);
            throw new ServiceException(ex);
        }
        return retorno;
    }

}
