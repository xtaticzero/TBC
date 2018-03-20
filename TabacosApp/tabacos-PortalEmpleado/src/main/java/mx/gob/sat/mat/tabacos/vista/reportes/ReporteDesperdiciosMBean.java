package mx.gob.sat.mat.tabacos.vista.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteDesperdiciosDTO;
import mx.gob.sat.mat.tabacos.negocio.ProduccionCigarrosService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.ReporteDesperdiciosHelper;
import mx.gob.sat.mat.tabacos.vista.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("reporteDesperdiciosMBean")
@Scope(value = "view")
public class ReporteDesperdiciosMBean extends AbstractManagedBean {

    private static final Logger LOGGER = Logger.getLogger(ReporteDesperdiciosMBean.class);

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("produccionServiceImpl")
    private ProduccionCigarrosService produccionServiceImpl;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    private static final String MSG_ERROR_INFORMACION = "La información no es correcta";
    private static final String MSG_ERROR_FECHAS = "Sólo se puede buscar por fechas en un rango menor de 30 días";
    private static final String MSG_ERROR_REPORTE = "Al generar el reporte";

    private static final String LBL_PRODUCTO = "Producto";
    private static final String LBL_PLANTA = "Planta de producción";
    private static final String LBL_CANTIDAD_PROD = "Cantidad de producción";
    private static final String LBL_ORIGEN = "Origen";
    private static final String LBL_MAQUINA = "Máquina de producción";
    private static final String LBL_LOTE = "Lote de producción";
    private static final String LBL_FECHA = "Fecha y Hora";
    private static final String LBL_CANDITAD_DEST = "Cantidad de destrucción";
    private static final String LBL_NUMERO = "Número de registro";

    private ReporteDesperdiciosDTO reporteDesperdicios;
    private List<ReporteDesperdiciosDTO> reporteDesperdiciosList;
    private ReporteDesperdiciosHelper reporteDesperdiciosHelper;
    private Date fechaMaxima;

    private String[] seleccionOpcionChk;
    private List<String> listaValoresChk;
    private String variableReporte;
    private boolean deshabilitaBtnImprimir;
    private Map parametrosReporte;
    private String razonSocial;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());

        reporteDesperdicios = new ReporteDesperdiciosDTO();
        reporteDesperdiciosHelper = new ReporteDesperdiciosHelper();
        fechaMaxima = new Date();
        listaValoresChk = new ArrayList<String>();
        deshabilitaBtnImprimir = true;

        listaValoresChk.add(getMessageResourceString("lbl.producto"));
        listaValoresChk.add(getMessageResourceString("lbl.planta.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.cantidad.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.origen"));
        listaValoresChk.add(getMessageResourceString("lbl.maquina.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.lote.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.fecha.hora"));
        listaValoresChk.add(getMessageResourceString("lbl.cantidad.destruccion"));
        listaValoresChk.add(getMessageResourceString("lbl.numero.registro"));

        consultaTabacaleras();
    }

    public void generaReportesDesperdicios() {
        try {
            byte[] bytesArchivo;

            if (!habilitaBtnImprimir()) {
                consultaDatosDesperdicios();
                asignaParametrosReporte();

                if (produccionServiceImpl != null) {
                    if (reporteDesperdiciosList != null && reporteDesperdiciosList.size() > 0) {
                        if (variableReporte.equals("PDF")) {
                            bytesArchivo = reporterService.makeReport(ReportesTabacosEnum.REPORTE_DESPERDICIOS, ARCHIVO_PDF, parametrosReporte, reporteDesperdiciosList);
                            if (bytesArchivo != null) {
                                generaDocumento(bytesArchivo, MIMETypesEnum.PDF, "ReporteDesperdiciosyDestrucción_", FileExtensionEnum.PDF);
                            }
                        } else if (variableReporte.equals("EXCEL")) {
                            bytesArchivo = reporterService.makeReport(ReportesTabacosEnum.REPORTE_DESPERDICIOS, Constantes.EXCEL_ANTES_2007, parametrosReporte, reporteDesperdiciosList);
                            if (bytesArchivo != null) {
                                generaDocumento(bytesArchivo, MIMETypesEnum.EXCEL, "ReporteDesperdiciosyDestrucción_", FileExtensionEnum.EXCEL);
                            }
                        }
                    } else {
                        super.addErrorMessage(ERROR, MSG_ERROR_INFORMACION);
                    }
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, MSG_ERROR_REPORTE);
            LOGGER.error("Error al generar el Reporte : ", e);
        }
    }

    public void asignaParametrosReporte() {
        parametrosReporte = new HashMap();
        Date fechaDescarga = new Date();
        consultaRazonSocial();

        parametrosReporte.put("rfcContribuyente", reporteDesperdicios.getRfc());
        parametrosReporte.put("razonSocial", razonSocial);
        parametrosReporte.put("fechaDescarga", fechaDescarga);

        if (validaRFCExiste()) {
            parametrosReporte.put("fechaInicio", null);
            parametrosReporte.put("fechaFin", null);
        } else {
            parametrosReporte.put("fechaInicio", reporteDesperdicios.getFechaInicio());
            parametrosReporte.put("fechaFin", reporteDesperdicios.getFechaFin());
        }

        //Parametros para validar que aparescan las columnas del reporte
        parametrosReporte.put("incluyeProducto", reporteDesperdiciosHelper.isChkProducto());
        parametrosReporte.put("incluyePlanta", reporteDesperdiciosHelper.isChkPlantaProd());
        parametrosReporte.put("incluyeCantidadProduccion", reporteDesperdiciosHelper.isChkCantidadProd());
        parametrosReporte.put("incluyeOrigen", reporteDesperdiciosHelper.isChkOrigen());
        parametrosReporte.put("incluyeMaquina", reporteDesperdiciosHelper.isChkMaquinaProd());
        parametrosReporte.put("incluyeLote", reporteDesperdiciosHelper.isChkLoteProd());
        parametrosReporte.put("incluyeFecha", reporteDesperdiciosHelper.isChkFechaHora());
        parametrosReporte.put("incluyeCantidadDestruccion", reporteDesperdiciosHelper.isChkCantidadDestruccion());
        parametrosReporte.put("incluyeNumeroRegistro", reporteDesperdiciosHelper.isChkNumeroRegistro());
    }

    public void consultaDatosDesperdicios() {
        try {
            if (produccionServiceImpl != null) {
                reporteDesperdiciosList = produccionServiceImpl.consultaReporteDesperdicios(reporteDesperdicios);
            }
        } catch (ProduccionServiceException e) {
            LOGGER.error("Error al consultar los datos del reporte de Desperdicios y Destruccion - " + e);
        }
    }

    public void consultaRazonSocial() {
        try {
            razonSocial = reporteService.consultarRazonSocial(reporteDesperdicios.getRfc());
        } catch (ReporteServiceException e) {
            LOGGER.error("ERROR - Al consultar la razon social : " + e);
        }
    }

    public void consultaTabacaleras() {
        try {
            reporteDesperdiciosHelper.setListTabacaleraXRfc(tababacaleraService.buscaTabacalerasActivas());
        } catch (TabacaleraServiceException se) {
            LOGGER.error("ERROR - Al consultar las Tabacaleras : " + se);
        }
    }

    @Override
    public List<String> autocompletarRFC(String query) {
        List<String> results = new ArrayList<String>();
        try {
            if ((query != null) && (query.length() > 0)) {
                results.clear();
                for (String tab : tababacaleraService.buscaTabacalerasLikeRfc(query)) {
                    results.add(tab);
                }
            }
        } catch (TabacaleraServiceException e) {
            LOGGER.error("ERROR - Al obtener las Tabacaleras - ", e);
        }
        return results;
    }

    public void validaRFC() {
        boolean rfcVacio = (reporteDesperdicios.getRfc() != null && reporteDesperdicios.getRfc().length() > 0);
        if (rfcVacio && (!validaRFCExiste())) {
            reporteDesperdicios.setRfc("");
            addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
        }
    }

    public boolean validaRFCExiste() {
        boolean rfcExiste = reporteDesperdicios.getRfc() != null;
        if ((rfcExiste) && (reporteDesperdiciosHelper.getListTabacaleraXRfc() != null) && (!reporteDesperdiciosHelper.getListTabacaleraXRfc().isEmpty())) {
            for (int i = 0; i < reporteDesperdiciosHelper.getListTabacaleraXRfc().size(); i++) {
                if (reporteDesperdicios.getRfc().equals(reporteDesperdiciosHelper.getListTabacaleraXRfc().get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validaRangoFechas() {
        Date fecInicio = reporteDesperdicios.getFechaInicio();
        Date fecFin = reporteDesperdicios.getFechaFin();
        boolean banderaFecha = Utilerias.isPeriodoValidoMax30dias(fecInicio, fecFin);

        if (!banderaFecha) {
            banderaFecha = true;
            super.addErrorMessage(ERROR, MSG_ERROR_FECHAS);
            this.reporteDesperdicios.setFechaInicio(null);
            this.reporteDesperdicios.setFechaFin(null);
        }
        return banderaFecha;
    }

    public void asignaValorCheck() {
        for (String checkSelected : seleccionOpcionChk) {
            boolean flg;

            flg = (LBL_PRODUCTO.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkProducto() && flg) {
                reporteDesperdiciosHelper.setChkProducto(flg);
            }

            flg = (LBL_PLANTA.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkPlantaProd() && flg) {
                reporteDesperdiciosHelper.setChkPlantaProd(flg);
            }

            flg = (LBL_CANTIDAD_PROD.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkCantidadProd() && flg) {
                reporteDesperdiciosHelper.setChkCantidadProd(flg);
            }

            flg = (LBL_ORIGEN.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkOrigen() && flg) {
                reporteDesperdiciosHelper.setChkOrigen(flg);
            }

            flg = (LBL_MAQUINA.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkMaquinaProd() && flg) {
                reporteDesperdiciosHelper.setChkMaquinaProd(flg);
            }

            flg = (LBL_LOTE.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkLoteProd() && flg) {
                reporteDesperdiciosHelper.setChkLoteProd(flg);
            }

            flg = (LBL_FECHA.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkFechaHora() && flg) {
                reporteDesperdiciosHelper.setChkFechaHora(flg);
            }

            flg = (LBL_CANDITAD_DEST.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkCantidadDestruccion() && flg) {
                reporteDesperdiciosHelper.setChkCantidadDestruccion(flg);
            }

            flg = (LBL_NUMERO.equals(checkSelected));
            if (!reporteDesperdiciosHelper.isChkNumeroRegistro() && flg) {
                reporteDesperdiciosHelper.setChkNumeroRegistro(flg);
            }
        }
    }

    public void inicializaChks() {
        boolean flg = false;
        reporteDesperdiciosHelper.setChkProducto(flg);
        reporteDesperdiciosHelper.setChkPlantaProd(flg);
        reporteDesperdiciosHelper.setChkCantidadProd(flg);
        reporteDesperdiciosHelper.setChkOrigen(flg);
        reporteDesperdiciosHelper.setChkMaquinaProd(flg);
        reporteDesperdiciosHelper.setChkLoteProd(flg);
        reporteDesperdiciosHelper.setChkFechaHora(flg);
        reporteDesperdiciosHelper.setChkCantidadDestruccion(flg);
        reporteDesperdiciosHelper.setChkNumeroRegistro(flg);
    }

    public boolean habilitaBtnImprimir() {
        deshabilitaBtnImprimir = false;

        if (reporteDesperdicios.getRfc() == null || reporteDesperdicios.getRfc().length() == 0) {
            deshabilitaBtnImprimir = true;
            if (reporteDesperdicios.getFechaInicio() != null && reporteDesperdicios.getFechaFin() != null && validaRangoFechas()) {
                deshabilitaBtnImprimir = false;
            }
        }
        if (reporteDesperdicios.getFechaInicio() == null || reporteDesperdicios.getFechaFin() == null) {
            deshabilitaBtnImprimir = true;
        }
        if (seleccionOpcionChk != null && seleccionOpcionChk.length > 0) {
            deshabilitaBtnImprimir = false;
            if (reporteDesperdicios.getRfc() == null && reporteDesperdicios.getFechaInicio() == null && reporteDesperdicios.getFechaFin() == null) {
                deshabilitaBtnImprimir = true;
            }
        }
        if (seleccionOpcionChk == null || seleccionOpcionChk.length == 0) {
            deshabilitaBtnImprimir = true;
        } else {
            inicializaChks();
            asignaValorCheck();
        }
        return deshabilitaBtnImprimir;
    }

    public ReporteDesperdiciosDTO getReporteDesperdicios() {
        return reporteDesperdicios;
    }

    public void setReporteDesperdicios(ReporteDesperdiciosDTO reporteDesperdicios) {
        this.reporteDesperdicios = reporteDesperdicios;
    }

    public List<ReporteDesperdiciosDTO> getReporteDesperdiciosList() {
        return reporteDesperdiciosList;
    }

    public void setReporteDesperdiciosList(List<ReporteDesperdiciosDTO> reporteDesperdiciosList) {
        this.reporteDesperdiciosList = reporteDesperdiciosList;
    }

    public ReporteDesperdiciosHelper getReporteDesperdiciosHelper() {
        return reporteDesperdiciosHelper;
    }

    public void setReporteDesperdiciosHelper(ReporteDesperdiciosHelper reporteDesperdiciosHelper) {
        this.reporteDesperdiciosHelper = reporteDesperdiciosHelper;
    }

    public void setFechaMaxima(final Date fechaMaxima) {
        this.fechaMaxima = (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public Date getFechaMaxima() {
        return (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public String[] getSeleccionOpcionChk() {
        return (seleccionOpcionChk != null) ? (String[]) seleccionOpcionChk.clone() : null;
    }

    public void setSeleccionOpcionChk(String[] seleccionOpcionChk) {
        this.seleccionOpcionChk = (seleccionOpcionChk != null) ? (String[]) seleccionOpcionChk.clone() : null;
    }

    public List<String> getListaValoresChk() {
        return listaValoresChk;
    }

    public void setListaValoresChk(List<String> listaValoresChk) {
        this.listaValoresChk = listaValoresChk;
    }

    public String getVariableReporte() {
        return variableReporte;
    }

    public void setVariableReporte(String variableReporte) {
        this.variableReporte = variableReporte;
    }

    public boolean isDeshabilitaBtnImprimir() {
        return deshabilitaBtnImprimir;
    }

    public void setDeshabilitaBtnImprimir(boolean deshabilitaBtnImprimir) {
        this.deshabilitaBtnImprimir = deshabilitaBtnImprimir;
    }

}
