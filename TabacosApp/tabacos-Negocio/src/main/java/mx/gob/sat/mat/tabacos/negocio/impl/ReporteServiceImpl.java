/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import static mx.gob.sat.mat.tabacos.constants.Constantes.EXCEL_ANTES_2007;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import mx.gob.sat.mat.tabacos.modelo.dao.ReportesDAO;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.RepDesperdicio;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.RepRegistro;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesDesperdicios;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesContribuyentes;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ConfiguracionJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.ReporteJasperUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Service
@Qualifier("reporteService")
public class ReporteServiceImpl implements ReporteService {

    private static final Logger LOGGER = Logger.getLogger(ReporteServiceImpl.class);
    private static final String VACIO = "";
    private static final int DAY_10 = 10;
    private Map parameters;
    private static final int REPORTEPDF = 1;
    private Map<Long, String> paises;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private ReportesDAO reportesDAO;

    @Autowired
    @Qualifier("paisOrigenDaoImpl")
    private PaisOrigenDao paisDAO;

    private void initReporte(ReportesFiltroBase filtro) {
        if (paises == null) {
            try {
                paises = new HashMap<Long, String>();
                List<PaisOrigen> paisesLista = paisDAO.selectedPais();

                for (PaisOrigen item : paisesLista) {
                    paises.put(item.getIdPais(), item.getDescCorta());
                }
            } catch (PaisDaoException ex) {
                LOGGER.error(ex);
            }
        }

        if (parameters != null) {
            parameters = null;
        }

        Calendar fecha = new GregorianCalendar();
        parameters = new HashMap();
        String dia = ((fecha.get(Calendar.DAY_OF_MONTH) < DAY_10)
                ? "0".concat(String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)))
                : String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)));
        String mes = ((fecha.get(Calendar.MONTH) + 1) < DAY_10)
                ? "0".concat(String.valueOf(fecha.get(Calendar.MONTH) + 1))
                : String.valueOf(fecha.get(Calendar.MONTH) + 1);
        String fechaActual = dia + "/" + mes + "/" + fecha.get(Calendar.YEAR);

        parameters.put("rfcContribuyente", filtro.getRfc());
        parameters.put("fechainicio", filtro.convertDateToString(filtro.getFechaInicio()));
        parameters.put("fechafin", filtro.convertDateToString(filtro.getFechaFin()));
        parameters.put("fechaDescarga", fechaActual);
        parameters.put("nombreRazonSocial", filtro.getRazonSocial());
    }

    /**
     *
     * @param detalle
     * @param parameters
     * @param reporte
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException
     */
    public byte[] makeReportPDF(List<?> detalle, Map parameters,
            ReportesTabacosEnum reporte) throws ReporterJasperException {
        byte[] retorno = null;

        if (reporterService != null) {
            retorno = reporterService.makeReport(reporte, ARCHIVO_PDF,
                    parameters, detalle);
        }

        return retorno;
    }

    /**
     *
     * @param detalle
     * @param parameters
     * @param reporte
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException
     */
    public byte[] makeReportExcel(List<?> detalle, Map parameters,
            ReportesTabacosEnum reporte) throws ServiceException {
        byte[] retorno = null;
        try {
            if (reporterService != null) {
                retorno = reporterService.makeReport(reporte, EXCEL_ANTES_2007,
                        parameters, detalle);
            }
        } catch (ReporterJasperException ex) {
            LOGGER.error("Error al generar el reporte pdf" + ex.getMessage(), ex);
            throw new ServiceException(ex);
        }

        return retorno;
    }

    /**
     *
     * @param filtro
     * @return
     * @throws ConfiguracionJasperException
     */
    @Override
    public byte[] makeReportContriyentes(ReportesFiltroBase filtro) throws ReporteServiceException {
        ReporteJasperUtil reporteDaniel = new ReporteJasperUtil();
        byte[] retorno = null;
        initReporte(filtro);
        ReportesContribuyentes item = reportesDAO.getContriyentes(filtro);

        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            reporteDaniel.agregarParametro((String) e.getKey(), e.getValue());
        }

        reporteDaniel.setFormatoReporte(ReporteJasperUtil.PDF);
        reporteDaniel.setReporteJasper("classpath:/reports/ReporteContribuyentes.jasper");
        reporteDaniel.setDatosReporte(item.getContribuyentes());

        try {
            retorno = reporteDaniel.generarBytesReporte();
        } catch (ConfiguracionJasperException e) {
            throw new ReporteServiceException("Ocurrió un error al intentar generar el reporte.", e);
        }

        return retorno;
    }

    /**
     *
     * @param reporte
     * @param filtro
     * @param tipo
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public byte[] makeReportContriyentes(ReportesTabacosEnum reporte,
            ReportesFiltroBase filtro, int tipo) throws ReporteServiceException {
        byte[] retorno = null;
        initReporte(filtro);
        ReportesContribuyentes item = reportesDAO.getContriyentes(filtro);
        parameters.put("opcionReporte", filtro.opcionReporte(" DEL CONTRIBUYENTE"));

        if (!item.getContribuyentes().isEmpty()) {
            if (tipo == REPORTEPDF) {
                try {
                    retorno = makeReportPDF(item.getContribuyentes(), parameters, reporte);
                } catch (ReporterJasperException ex) {
                    throw new ReporteServiceException(ex);
                }
            } else {
                try {
                    retorno = makeReportExcel(item.getContribuyentes(), parameters, reporte);
                } catch (ServiceException ex) {
                    throw new ReporteServiceException(ex);
                }
            }
        }
        return retorno;
    }

    /**
     *
     * @param reporte
     * @param filtro
     * @param tipo
     * @return
     */
    @Override
    public byte[] makeReportRegistros(ReportesTabacosEnum reporte,
            ReportesRegistrosYDespedicios filtro, int tipo) throws ReporteServiceException {
        byte[] retorno = null;
        initReporte(filtro);

        ReportesRegistros item = reportesDAO.getRegistros(filtro);

        if (!item.getRegistros().isEmpty()) {
            for (RepRegistro info : item.getRegistros()) {
                Long busq;
                if (info.getOrigen() == null) {
                    info.setOrigen(VACIO);
                } else {
                    busq = new Long(!info.getOrigen().equals(VACIO)
                            ? info.getOrigen() : "-1");
                    if (busq.intValue() > 0) {
                        info.setOrigen(paises.get(busq));
                    } else {
                        info.setOrigen(VACIO);
                    }
                }
                if (info.getPais() == null) {
                    info.setPais(VACIO);
                } else {
                    busq = new Long(!info.getPais().equals(VACIO) ? info.getPais() : "-1");

                    if (busq.intValue() > 0) {
                        info.setPais(paises.get(busq));
                    } else {
                        info.setPais(VACIO);
                    }
                }
            }

            if (tipo == REPORTEPDF) {
                try {
                    retorno = makeReportPDF(item.getRegistros(), parameters, reporte);
                } catch (ReporterJasperException ex) {
                    throw new ReporteServiceException(ex);
                }
            } else {
                try {
                    retorno = makeReportExcel(item.getRegistros(), parameters, reporte);
                } catch (ServiceException ex) {
                    throw new ReporteServiceException(ex);
                }
            }
        }

        return retorno;
    }

    /**
     *
     * @param reporte
     * @param filtro
     * @param tipo
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public byte[] makeReportDesperdicios(ReportesTabacosEnum reporte,
            ReportesRegistrosYDespedicios filtro, int tipo) throws ReporteServiceException {
        byte[] retorno = null;
        initReporte(filtro);

        ReportesDesperdicios item = reportesDAO.getDesperdicios(filtro);

        if (!item.getDesperdicios().isEmpty()) {

            for (RepDesperdicio info : item.getDesperdicios()) {
                Long busq;
                if (info.getOrigen() == null) {
                    info.setOrigen(VACIO);
                } else {
                    busq = new Long(!info.getOrigen().equals(VACIO)
                            ? info.getOrigen() : "-1");
                    if (busq.intValue() > 0) {
                        info.setOrigen(paises.get(busq));
                    } else {
                        info.setOrigen(VACIO);
                    }
                }
            }

            if (tipo == REPORTEPDF) {
                try {
                    retorno = makeReportPDF(item.getDesperdicios(), parameters, reporte);
                } catch (ReporterJasperException ex) {
                    throw new ReporteServiceException(ex);
                }
            } else {
                try {
                    retorno = makeReportExcel(item.getDesperdicios(), parameters, reporte);
                } catch (ServiceException ex) {
                    throw new ReporteServiceException(ex);
                }
            }
        }

        return retorno;
    }

    /**
     *
     * @param strSQL
     * @param id
     * @param name
     * @param param1
     * @return
     */
    @Override
    public List<SelectItem> consultaCombos(String strSQL, String id,
            String name, String param1) {
        return reportesDAO.getCombos(strSQL, id, name, param1);
    }

    /**
     *
     * @param strSQL
     * @param id
     * @param name
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public List<SelectItem> consultaCombos(String strSQL, String id,
            String name) throws ReporteServiceException {
        try {
            return reportesDAO.getCombos(strSQL, id, name);
        } catch (ReporteDaoException ex) {
            throw new ReporteServiceException(ex);
        }
    }

    /**
     *
     * @return @throws
     * mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public List<SelectItem> consultaCombosMarcasAutorizadas() throws ReporteServiceException {
        try {
            return reportesDAO.getCombosMarcaAutorizadas();
        } catch (ReporteDaoException ex) {
            throw new ReporteServiceException(ex);
        }
    }

    /**
     *
     * @param rfc
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public List<SelectItem> consultaCboMarcasAutorizadasXRfc(String rfc) throws ReporteServiceException {
        try {
            return reportesDAO.getMarcaAutorizadasXRfc(rfc);
        } catch (ReporteDaoException ex) {
            throw new ReporteServiceException(ex);
        }
    }

    /**
     *
     * @param rfc
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException
     */
    @Override
    public String consultarRazonSocial(final String rfc) throws ReporteServiceException {
        try {
            return reportesDAO.getRazonSocial(rfc);
        } catch (ReporteDaoException ex) {
            throw new ReporteServiceException(ex);
        }
    }

    /**
     *
     * @param rfc
     * @return
     */
    @Override
    public String consultarRazonSocialProv(final String rfc) {
        return reportesDAO.getRazonSocialProv(rfc);
    }
}
