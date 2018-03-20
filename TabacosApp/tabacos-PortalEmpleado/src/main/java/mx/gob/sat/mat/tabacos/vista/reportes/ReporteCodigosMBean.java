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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import static mx.gob.sat.mat.tabacos.constants.Constantes.EXCEL_ANTES_2007;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.negocio.CodigosFalsoseInvalidosService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.INFO;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRango;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MMMF
 */
@Controller("reportesCodigosMB")
@Scope(value = "view")
public class ReporteCodigosMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(ReporteCodigosMBean.class);
    private final DateFormat convert = new SimpleDateFormat("dd/MM/yyyy");

    private static final int REPORTEPDF = 1;
    private static final int REPORTEEXCEL = 2;
    private static final int MIN_TAMANIO_RFC = 11;

    private static final String MSG_FECHAS = "Sólo se puede buscar por fechas en un rango menor de 30 días";
    private static final String MSG_REPORTE = "No contiene información el reporte";
    private static final String MSG_OPCION = "Debe seleccionar por lo menos una opción";
    private static final String MSG_FECHAS_FILTRO = "Debe de seleccionar un rango de fechas";
    private static final String MSG_BUSQUEDA_PERMITIDA = "Debe ingresar RFC o fecha";
    private static final String NOMBRE_ARCHIVO = "reporteCodigos";
    private static final String INVALIDOS = "Invalidos";
    private static final String FALSOS = "Falsos";
    private List<String> lstTabacalerasXRfc;
    private boolean flgBtnReportes;

    private Date today;
    private ReportesParametrosBase codigoFiltro;
    private Map parameters;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private CodigosFalsoseInvalidosService codigosFalsoseInvalidosService;
    
    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());
        codigoFiltro = new ReportesParametrosBase();
        parameters = new HashMap();
        today = new Date();
        parameters.put("fechaDescarga", convert.format(new Date()));
        try {
            lstTabacalerasXRfc = tababacaleraService.buscaTabacalerasActivas();
        } catch (TabacaleraServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void btnGenerarReporteCodigoPDF() {

        ReportesFiltroBase filtro = llenaFiltro(this.codigoFiltro);

        llenaParametrosReporte(filtro);
        //si adelanta al dia siguiente, la busqueda del between toma el fechaFin-1
        if (filtro.getFechaFin() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(filtro.getFechaFin().getTime());
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
            filtro.setFechaFin(new Date(cal.getTimeInMillis()));
        }

        String opcion = this.codigoFiltro.getSeleccion();
        int tipoCodigo = obtieneOpcionTipoCodigo(opcion);

        boolean camposVacios = camposVaciosCodigos(filtro);
        boolean busquedaPermitida = validaCamposFalsosInvalidos(filtro, tipoCodigo);
        boolean fechasValidas = validaFechas(filtro.getFechaInicio(), filtro.getFechaFin());

        if (!camposVacios && fechasValidas && busquedaPermitida) {
            generaReportePdf(tipoCodigo, filtro);
        } else if (camposVacios) {
            addErrorMessage(ERROR, MSG_BUSQUEDA_PERMITIDA);
        }

    }

    /**
     *
     */
    public void btnGenerarReporteCodigoExcel() {

        ReportesFiltroBase filtro = llenaFiltro(this.codigoFiltro);

        llenaParametrosReporte(filtro);
        //si adelanta al dia siguiente, la busqueda del between toma el fechaFin-1
        if (filtro.getFechaFin() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(filtro.getFechaFin().getTime());
            cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
            filtro.setFechaFin(new Date(cal.getTimeInMillis()));
        }

        String opcion = this.codigoFiltro.getSeleccion();
        int tipoCodigo = obtieneOpcionTipoCodigo(opcion);

        boolean camposVacios = camposVaciosCodigos(filtro);
        boolean busquedaPermitida = validaCamposFalsosInvalidos(filtro, tipoCodigo);
        boolean fechasValidas = validaFechas(filtro.getFechaInicio(), filtro.getFechaFin());

        if (!camposVacios && fechasValidas && busquedaPermitida) {
            generaReporteExcel(tipoCodigo, filtro);
        } else if (camposVacios) {
            addErrorMessage(ERROR, MSG_BUSQUEDA_PERMITIDA);
        }

    }

    private ReportesFiltroBase llenaFiltro(ReportesParametrosBase parametrosBase) {
        ReportesFiltroBase filtro = null;
        if (parametrosBase != null) {
            filtro = new ReportesFiltroBase();
            filtro.setRfc(parametrosBase.getRfc() != null ? parametrosBase.getRfc() : "");
            filtro.setFechaInicio(parametrosBase.getFechainicio());
            filtro.setFechaFin(parametrosBase.getFechafin());
            try {
                filtro.setRazonSocial(reporteService.consultarRazonSocial(filtro.getRfc()));
            } catch (ReporteServiceException bse) {
                LOGGER.error(bse);
            }
        }
        return filtro;
    }

    public void validaRFC() {
        if (this.codigoFiltro.getRfc() != null && this.codigoFiltro.getRfc().trim().length() > 0) {
            if (!validaRFCExiste(this.codigoFiltro.getRfc())) {
                this.codigoFiltro.setRfc("");
                addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
                validaParametrosReportes();
            } else {
                validaParametrosReportes();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formCodigo");
            }
        } else {
            validaParametrosReportes();
        }
    }

    public boolean validaRFCExiste(String rfc) {
        if ((rfc != null) && (rfc.length() > 0)) {
            for (String rfcTab : this.lstTabacalerasXRfc) {
                if (rfc.equals(rfcTab)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void validaParametrosReportes() {
        if (validarRequeridos()) {
            flgBtnReportes = true;
        } else {
            flgBtnReportes = false;
        }
    }

    public void validaFormatoFechas() {

        Date fechaIni = this.codigoFiltro.getFechainicio();
        Date fechaFin = this.codigoFiltro.getFechafin();

        if (ambasFechas(fechaIni, fechaFin)) {
            if (!ValidaRango.isRangoDiasValido(fechaIni, fechaFin) && fechaIni.compareTo(fechaFin)!= 0) {
                super.addErrorMessage(ERROR, getMessageResourceString("msg.error.fechas.rango"));
                return;
            }
            validaParametrosReportes();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formCodigo");
        } else if (soloTieneUnaFecha(fechaIni, fechaFin)) {
            addErrorMessage(ERROR, MSG_FECHAS_FILTRO);
        }

    }

    public boolean validarRequeridos() {
        boolean flgRFC = (this.codigoFiltro.getRfc() != null && this.codigoFiltro.getRfc().trim().length() > MIN_TAMANIO_RFC);
        boolean flgFecha = ambasFechas(this.codigoFiltro.getFechainicio(), this.codigoFiltro.getFechafin())
                && !this.codigoFiltro.getFechafin().before(this.codigoFiltro.getFechainicio());
        boolean flgTipoRep = (this.codigoFiltro.getSeleccion() != null && this.codigoFiltro.getSeleccion().length() > 0);

        return ((flgTipoRep) && (flgFecha || flgRFC));
    }

    private void generaReportePdf(int tipoCodigo, ReportesFiltroBase filtro) {
        try {
            if (tipoCodigo == 1) {
                generaReporte(REPORTEPDF, INVALIDOS, codigosFalsoseInvalidosService.getCodigosInvalidos(filtro));
            } else if (tipoCodigo == 2) {
                generaReporte(REPORTEPDF, FALSOS, codigosFalsoseInvalidosService.getCodigosFalsos(filtro));
            }
        } catch (CodigosFalsoseInvalidosException e) {
            LOGGER.error(e);
        } catch (ReporterJasperException ex) {
            LOGGER.error(ex);
        }

    }

    private void generaReporteExcel(int tipoCodigo, ReportesFiltroBase filtro) {
        try {
            if (tipoCodigo == 1) {
                generaReporte(REPORTEEXCEL, INVALIDOS, codigosFalsoseInvalidosService.getCodigosInvalidos(filtro));
            } else if (tipoCodigo == 2) {
                generaReporte(REPORTEEXCEL, FALSOS, codigosFalsoseInvalidosService.getCodigosFalsos(filtro));
            }
        } catch (CodigosFalsoseInvalidosException e) {
            LOGGER.error(e);
        } catch (ReporterJasperException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * @param tipoDeArchivo. 1 es pdf, 2 es excel
     */
    private byte[] generaReporte(int tipoDeArchivo, String tipoCodigo, List<Codigo> codigos) throws ReporterJasperException {
        byte[] bytesFile = null;
        if (reporterService != null) {
            if (codigos != null && !codigos.isEmpty()) {
                switch (tipoDeArchivo) {
                    //PDF
                    case 1:
                        bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_CODIGOS, ARCHIVO_PDF, this.parameters, codigos);
                        generaDocumento(bytesFile, MIMETypesEnum.PDF, NOMBRE_ARCHIVO + tipoCodigo, FileExtensionEnum.PDF);
                        break;
                    //EXCEL
                    case 2:
                        bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_CODIGOS, EXCEL_ANTES_2007, this.parameters, codigos);
                        generaDocumento(bytesFile, MIMETypesEnum.EXCEL, NOMBRE_ARCHIVO + tipoCodigo, FileExtensionEnum.EXCEL);
                        break;

                    default:
                        break;
                }
            } else {
                addMessage(INFO, MSG_REPORTE);
            }
        }
        return bytesFile;
    }

    private boolean validaCamposFalsosInvalidos(ReportesFiltroBase filtro, int tipoCodigo) {
        boolean retorno = false;
        if (filtro != null && tipoCodigo > 0) {
            boolean ambasFechas = ambasFechas(filtro.getFechaInicio(), filtro.getFechaFin());
            //codigos invalidos, solo rfc O fechas
            if ((filtro.getRfc() != null && !filtro.getRfc().isEmpty() && filtro.getRfc().trim().length() > 0) || ambasFechas) {
                retorno = true;
            } else {
                addMessage(INFO, MSG_BUSQUEDA_PERMITIDA);
            }
        }
        return retorno;
    }

    private boolean validaFechas(Date fechaIni, Date fechaFin) {
        if (ambasFechas(fechaIni, fechaFin)) {
            if (!ValidaRango.isRangoDiasValido(fechaIni, fechaFin) && fechaIni.compareTo(fechaFin)!= 0) {
                addMessage(INFO, MSG_FECHAS);
                return false;
            }
        } else if (soloTieneUnaFecha(fechaIni, fechaFin)) {
            addErrorMessage(ERROR, MSG_FECHAS_FILTRO);
            return false;
        }
        return true;
    }

    private int obtieneOpcionTipoCodigo(String opcion) {
        int tipoCodigo = 0;
        try {
            tipoCodigo = Integer.parseInt(opcion);
        } catch (NumberFormatException nbe) {
            LOGGER.error(nbe.getMessage());
        }
        if (tipoCodigo == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR, MSG_OPCION));
        }
        return tipoCodigo;
    }

    private boolean ambasFechas(Date fechaIni, Date fechaFin) {
        if (fechaIni != null && fechaFin != null) {
            return true;
        }
        return false;
    }

    private boolean soloTieneUnaFecha(Date fechaIni, Date fechaFin) {
        if ((fechaIni == null && fechaFin != null)
                || (fechaIni != null && fechaFin == null)) {
            return true;
        }
        return false;
    }

    private boolean camposVaciosCodigos(ReportesFiltroBase filtro) {
        boolean esVacio = filtro.getRfc() == null || filtro.getRfc().isEmpty() || filtro.getRfc().trim().length() == 0;
        if (esVacio && (filtro.getFechaInicio() == null && filtro.getFechaFin() == null)) {
            return true;
        }
        return false;
    }

    private void llenaParametrosReporte(ReportesFiltroBase filtro) {
        if (filtro != null) {
            parameters.put("fechainicio", filtro.getFechaInicio() != null ? convert.format(filtro.getFechaInicio()) : "");
            parameters.put("fechafin", filtro.getFechaFin() != null ? convert.format(filtro.getFechaFin()) : "");
            parameters.put("rfcContribuyente", filtro.getRfc());
            parameters.put("nombreRazonSocial", filtro.getRazonSocial());
        }

    }

    public Date getToday() {
        return (today != null) ? (Date) today.clone() : null;
    }

    public ReportesParametrosBase getCodigoFiltro() {
        return codigoFiltro;
    }

    public void setCodigoFiltro(ReportesParametrosBase codigoFiltro) {
        this.codigoFiltro = codigoFiltro;
    }

    public boolean isFlgBtnReportes() {
        return flgBtnReportes;
    }

    public void setFlgBtnReportes(boolean flgBtnReportes) {
        this.flgBtnReportes = flgBtnReportes;
    }
}
