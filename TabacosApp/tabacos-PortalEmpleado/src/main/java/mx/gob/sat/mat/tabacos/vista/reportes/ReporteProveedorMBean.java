/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.reportes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import static mx.gob.sat.mat.tabacos.constants.Constantes.EXCEL_ANTES_2007;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.HorasDeConsultaEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.INFO;
import mx.gob.sat.mat.tabacos.vista.helper.ReporteProveedorHelper;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRango;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MMMF
 */
@Controller("reporteProveedorMB")
@Scope(value = "view")
public class ReporteProveedorMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(ReporteProveedorMBean.class);

    private static final int REPORTEPDF = 1;
    private static final int REPORTEEXCEL = 2;

    private static final String MSG_REPORTE = "No contiene información el reporte";
    private static final String MSG_OPCION = "Debe seleccionar por lo menos una opción";

    private static final String MSG_FECHAS_FILTRO = "Debe de seleccionar un rango de fechas";
    private static final String MSG_BUSQUEDA_PERMITIDA = "Debe ingresar RFC o fecha";
    private static final String NOMBRE_ARCHIVO = "reporteProveedor";
    private static final int MIN_TAMANIO_RFC = 11;

    private final DateFormat convert = new SimpleDateFormat("dd/MM/yyyy");
    private Date today;
    private Map parameters;
    private boolean flgBtnReportes;

    private ReporteProveedorHelper reporteProveedorHelper;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ProveedorService proveedorService;

    @PostConstruct
    public void init() {

        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());
        today = new Date();
        reporteProveedorHelper = new ReporteProveedorHelper();
        parameters = new HashMap();

        try {
            reporteProveedorHelper.setProveedores(proveedorService.getProveedoresTodos());
        } catch (ProveedorServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            getLogger().error(ex);
        }

    }

    public void btnGenerarReporteProveedorPDF() {
        try {
            ReportesFiltroBase filtro = llenaFiltro(this.reporteProveedorHelper);

            if (!validarCasos(filtro, this.reporteProveedorHelper.getTipoReporte())) {
                return;
            }

            llenaParametros(filtro);

            //si adelanta al dia siguiente, la busqueda del between toma el fechaFin-1
            if (filtro.getFechaFin() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(filtro.getFechaFin().getTime());
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
                filtro.setFechaFin(new Date(cal.getTimeInMillis()));
            }

            List<Proveedor> proveedores = proveedorService.recuperaProveedores(filtro);
            if (proveedores != null && !proveedores.isEmpty()) {
                generaReporteProveedor(proveedores, REPORTEPDF);

            } else {
                addMessage(INFO, MSG_REPORTE);
            }
        } catch (ProveedorServiceException bse) {
            LOGGER.error(bse);
        }
    }

    public void btnGenerarReporteProveedorExcel() {
        try {
            ReportesFiltroBase filtro = llenaFiltro(this.reporteProveedorHelper);

            if (!validarCasos(filtro, this.reporteProveedorHelper.getTipoReporte())) {
                return;
            }

            llenaParametros(filtro);

            //si adelanta al dia siguiente, la busqueda del between toma el fechaFin-1
            if (filtro.getFechaFin() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(filtro.getFechaFin().getTime());
                cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
                filtro.setFechaFin(new Date(cal.getTimeInMillis()));
            }

            List<Proveedor> proveedores = proveedorService.recuperaProveedores(filtro);
            if (proveedores != null && !proveedores.isEmpty()) {
                generaReporteProveedor(proveedores, REPORTEEXCEL);

            } else {
                addMessage(INFO, MSG_REPORTE);
            }
        } catch (ProveedorServiceException e) {
            super.addErrorMessage(ERROR, e.getMessage());
            LOGGER.error(e.getMessage().trim());
        }
    }

    /**
     * @param tipoArchivo. 1 es pdf, 2 es excel
     */
    private void generaReporteProveedor(List<Proveedor> proveedores, int tipoArchivo) {
        byte[] reporte;
        try {
            if (reporterService != null) {
                switch (tipoArchivo) {
                    case REPORTEPDF:
                        reporte = reporterService.makeReport(ReportesTabacosEnum.REPORT_PROVEEDORES, ARCHIVO_PDF, parameters, proveedores);
                        if (reporte != null) {
                            generaDocumento(reporte, MIMETypesEnum.PDF, NOMBRE_ARCHIVO + "_" + reporteProveedorHelper.getTipoReporte(), FileExtensionEnum.PDF);
                        }
                        break;
                    case REPORTEEXCEL:
                        reporte = reporterService.makeReport(ReportesTabacosEnum.REPORT_PROVEEDORES, EXCEL_ANTES_2007, parameters, proveedores);
                        if (reporte != null) {
                            generaDocumento(reporte, MIMETypesEnum.EXCEL, NOMBRE_ARCHIVO + "_" + reporteProveedorHelper.getTipoReporte(), FileExtensionEnum.EXCEL);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (ReporterJasperException se) {
            LOGGER.error(se);
        }
    }

    private void llenaParametros(ReportesFiltroBase filtro) {
        if (filtro != null) {
            parameters = new HashMap();
            parameters.put("opcionReporte", this.reporteProveedorHelper.getTipoReporte() + " de proveedor");
            parameters.put("fechainicio", filtro.getFechaInicio() != null ? convert.format(filtro.getFechaInicio()) : "");
            parameters.put("fechafin", filtro.getFechaFin() != null ? convert.format(filtro.getFechaFin()) : "");
            parameters.put("fechaDescarga", convert.format(new Date()));
            parameters.put("rfcContribuyente", filtro.getRfc());
            parameters.put("nombreRazonSocial", filtro.getRazonSocial());
        }

    }

    private ReportesFiltroBase llenaFiltro(ReporteProveedorHelper proveedorHelper) {
        ReportesFiltroBase filtro = null;

        if (proveedorHelper != null) {
            filtro = new ReportesFiltroBase();
            if (proveedorHelper.getRfc() != null) {
                filtro.setRfc(proveedorHelper.getRfc());
            }

            if (proveedorHelper.getFechaInicio() != null && proveedorHelper.getFechaFin() != null) {
                filtro.setFechaInicio(Utilerias.chageFecha(proveedorHelper.getFechaInicio(), HorasDeConsultaEnum.INICIO_DE_DIA));
                filtro.setFechaFin(Utilerias.chageFecha(proveedorHelper.getFechaFin(), HorasDeConsultaEnum.FIN_DE_DIA));
            }

            try {
                filtro.setRazonSocial(reporteService.consultarRazonSocial(proveedorHelper.getRfc()));
            } catch (ReporteServiceException e) {
                LOGGER.error(e);
            }
        }
        return filtro;
    }

    /**
     *
     * @param filtro
     * @param seleccion
     * @return
     */
    public boolean validarCasos(ReportesFiltroBase filtro, String seleccion) {

        boolean validaCampos = validaCampos(filtro);

        if (!asignaMovimiento(seleccion, filtro)) {
            addErrorMessage(ERROR, MSG_OPCION);
            return false;
        }
        if ((filtro.getRfc() == null || filtro.getRfc().isEmpty())
                && filtro.getFechaInicio() == null && filtro.getFechaFin() == null) {
            addErrorMessage(ERROR, MSG_BUSQUEDA_PERMITIDA);
            return false;
        }

        return validaCampos;
    }

    private boolean asignaMovimiento(String seleccion, ReportesFiltroBase filtro) {
        boolean result = true;

        //alta (1), modificacion (2), baja (3). Asi lo manejaron para las consultas en BD
        final int alta = 1;
        final int modificacion = 2;
        final int baja = 3;
        if (seleccion != null && !seleccion.isEmpty()) {
            if (seleccion.equals(reporteProveedorHelper.getTipoAlta().getDescripcion())) {
                filtro.setMovimiento(alta);
            } else if (seleccion.equals(reporteProveedorHelper.getTipoBaja().getDescripcion())) {
                filtro.setMovimiento(baja);
            } else if (seleccion.equals(reporteProveedorHelper.getTipoCambio().getDescripcion())) {
                filtro.setMovimiento(modificacion);
            }
        } else {
            result = false;
        }

        return result;
    }

    private boolean validaCampos(ReportesFiltroBase filtro) {
        boolean retorno = false;
        if (filtro != null) {
            boolean ambasFechas = ambasFechas(filtro.getFechaInicio(), filtro.getFechaFin());
            boolean unaFecha = soloTieneUnaFecha(filtro.getFechaInicio(), filtro.getFechaFin());
            boolean fechasVal = (ambasFechas) || unaFecha;
            //codigos invalidos, solo rfc O fechas
            if ((filtro.getRfc() != null && !filtro.getRfc().isEmpty()) || fechasVal) {
                retorno = true;
            }
        }
        return retorno;
    }

    private boolean ambasFechas(Date fechaIni, Date fechaFin) {
        return (fechaIni != null && fechaFin != null);
    }

    private boolean soloTieneUnaFecha(Date fechaIni, Date fechaFin) {
        return (fechaIni != null ^ fechaFin != null);
    }

    public void validaRFC() {
        if (reporteProveedorHelper.getRfc() != null && reporteProveedorHelper.getRfc().trim().length() > 0) {
            if (!validaRFCExiste(reporteProveedorHelper.getRfc())) {
                reporteProveedorHelper.setRfc("");
                addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
                validaParametrosReportes();
            } else {
                validaParametrosReportes();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formProveedor");
            }
        } else {
            validaParametrosReportes();
        }
    }

    public boolean validaRFCExiste(String rfc) {
        if ((rfc != null) && (rfc.length() > 0)) {
            for (Proveedor prov : reporteProveedorHelper.getProveedores()) {
                if (rfc.equals(prov.getRfc())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void validaParametrosReportes() {
        flgBtnReportes = validarRequeridos();
    }

    public void validaFormatoFechas() {

        Date fechaIni = reporteProveedorHelper.getFechaInicio();
        Date fechaFin = reporteProveedorHelper.getFechaFin();

        if (ambasFechas(fechaIni, fechaFin)) {
            if (!ValidaRango.isRangoDiasValido(fechaIni, fechaFin) && fechaIni.compareTo(fechaFin) != 0) {
                super.addErrorMessage(ERROR, getMessageResourceString("msg.error.fechas.rango"));
                return;
            }
            validaParametrosReportes();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formProveedor");
        } else if (soloTieneUnaFecha(fechaIni, fechaFin)) {
            super.addErrorMessage(ERROR, MSG_FECHAS_FILTRO);
        }

    }

    public boolean validarRequeridos() {
        boolean flgRFC = (reporteProveedorHelper.getRfc() != null && reporteProveedorHelper.getRfc().trim().length() > MIN_TAMANIO_RFC);
        boolean flgFecha = ambasFechas(reporteProveedorHelper.getFechaInicio(), reporteProveedorHelper.getFechaFin())
                && !reporteProveedorHelper.getFechaFin().before(reporteProveedorHelper.getFechaInicio());
        boolean flgTipoRep = (reporteProveedorHelper.getTipoReporte() != null && reporteProveedorHelper.getTipoReporte().length() > 0);

        return ((flgTipoRep) && (flgFecha || flgRFC));
    }

    public ReporteProveedorHelper getReporteProveedorHelper() {
        return reporteProveedorHelper;
    }

    public void setReporteProveedorHelper(ReporteProveedorHelper reporteProveedorHelper) {
        this.reporteProveedorHelper = reporteProveedorHelper;
    }

    public Date getToday() {
        return (today != null) ? (Date) today.clone() : null;
    }

    public boolean isFlgBtnReportes() {
        return flgBtnReportes;
    }

    public void setFlgBtnReportes(boolean flgBtnReportes) {
        this.flgBtnReportes = flgBtnReportes;
    }
}
