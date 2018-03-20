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
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteProduccion;
import mx.gob.sat.mat.tabacos.negocio.ProduccionCigarrosService;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.ReporteProduccionHelper;
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
@Controller("reporteProduccionMBean")
@Scope(value = "view")
public class ReporteProduccionMBean extends AbstractManagedBean {

    private static final Logger LOGGER = Logger.getLogger(ReporteProduccionMBean.class);

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("proveedorServiceImpl")
    private ProveedorService proveedorServiceImpl;

    @Autowired
    @Qualifier("produccionServiceImpl")
    private ProduccionCigarrosService produccionServiceImpl;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    private static final String MSG_ERROR_INFORMACION = "La información no es correcta";
    private static final String MSG_ERROR_FECHAS = "Sólo se puede buscar por fechas en un rango menor de 30 días";
    private static final String MSG_ERROR_REPORTE = "Al generar el reporte";

    private static final String LBL_LOTE = "Lote de producción";
    private static final String LBL_PLANTA = "Planta de producción";
    private static final String LBL_CANTIDAD_PROD = "Cantidad de producción";
    private static final String LBL_MAQUINA = "Máquina de producción";
    private static final String LBL_ORIGEN = "Origen";
    private static final String LBL_LINEA_PRODUCCION = "Linea de producción";

    private ReporteProduccion reporteProduccion;
    private List<ReporteProduccion> listaReporteProduccion;
    private ReporteProduccionHelper reporteProduccionHelper;
    private String[] seleccionOpcionChk;
    private List<String> listaValoresChk;

    private Date fechaMaxima;
    private Long opcionProveedor;
    private Long opcionTabacalera;
    private boolean deshabilitaBtnImprimir;
    private Map parametrosReporte;
    private String razonSocial;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());

        reporteProduccion = new ReporteProduccion();
        reporteProduccionHelper = new ReporteProduccionHelper();
        listaValoresChk = new ArrayList<String>();
        fechaMaxima = new Date();
        deshabilitaBtnImprimir = true;

        listaValoresChk.add(getMessageResourceString("lbl.lote.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.planta.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.cantidad.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.maquina.produccion"));
        listaValoresChk.add(getMessageResourceString("lbl.origen"));
        listaValoresChk.add(getMessageResourceString("lbl.linea.produccion"));

        consultaTabacalerasActivas();
    }

    public void generaReportesProduccion() {
        try {
            byte[] bytesArchivo;
            if (!habilitaBtnImprimir()) {
                consultaDatosProduccion();
                asignaParametrosReporte();

                if (produccionServiceImpl != null) {
                    if (listaReporteProduccion != null && !listaReporteProduccion.isEmpty()) {
                        if (reporteProduccionHelper.getVariableReporte().equals("PDF")) {
                            bytesArchivo = reporterService.makeReport(ReportesTabacosEnum.REPORTE_PRODUCCION, ARCHIVO_PDF, parametrosReporte, listaReporteProduccion);
                            if (bytesArchivo != null) {
                                generaDocumento(bytesArchivo, MIMETypesEnum.PDF, "reporteProduccion_", FileExtensionEnum.PDF);
                            }
                        } else if (reporteProduccionHelper.getVariableReporte().equals("EXCEL")) {
                            bytesArchivo = reporterService.makeReport(ReportesTabacosEnum.REPORTE_PRODUCCION, Constantes.EXCEL_ANTES_2007, parametrosReporte, listaReporteProduccion);
                            if (bytesArchivo != null) {
                                generaDocumento(bytesArchivo, MIMETypesEnum.EXCEL, "reporteProduccion_", FileExtensionEnum.EXCEL);
                            }
                        }
                    }
                } else {
                    super.addErrorMessage(ERROR, MSG_ERROR_INFORMACION);
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, MSG_ERROR_REPORTE);
            LOGGER.error("Error al generar el Reporte : ", e);
        }
    }

    public void consultaDatosProduccion() {
        try {
            reporteProduccion.setIdProveedor(opcionProveedor);

            if (produccionServiceImpl != null) {
                listaReporteProduccion = produccionServiceImpl.consultaReporteProduccion(reporteProduccion);
            }
        } catch (ProduccionServiceException e) {
            LOGGER.error("Error al consultar los datos del reporte de Produccion - " + e);
        }
    }

    public void asignaParametrosReporte() {
        parametrosReporte = new HashMap();
        Date fechaDescarga = new Date();
        consultaRazonSocial();
        boolean incluyeProveedor = reporteProduccion.getIdProveedor() != null;

        parametrosReporte.put("rfcContribuyente", reporteProduccion.getRfc());
        parametrosReporte.put("razonSocial", razonSocial);
        parametrosReporte.put("fechaDescarga", fechaDescarga);

        if (validaRFCExiste()) {
            parametrosReporte.put("fechaInicio", null);
            parametrosReporte.put("fechaFin", null);
        } else {
            parametrosReporte.put("fechaInicio", reporteProduccion.getFechaInicio());
            parametrosReporte.put("fechaFin", reporteProduccion.getFechaFin());
        }

        //Parametros que validan si se agrega la informacion al reporte en base a la seleccion del CheckBox
        parametrosReporte.put("incluyeLote", reporteProduccionHelper.isChkLoteProd());
        parametrosReporte.put("incluyePlanta", reporteProduccionHelper.isChkPlantaProd());
        parametrosReporte.put("incluyeCantidadProduccion", reporteProduccionHelper.isChkCantidadProd());
        parametrosReporte.put("inlcuyeMaquina", reporteProduccionHelper.isChkMaquinaProd());
        parametrosReporte.put("incluyeOrigen", reporteProduccionHelper.isChkOrigen());
        parametrosReporte.put("incluyeLineaProduccion", reporteProduccionHelper.isChkOrigen());
        parametrosReporte.put("incluyeProveedor", incluyeProveedor);
    }

    public void consultaProveedorPorRfc(String rfc) {
        try {
            reporteProduccionHelper.setListaProveedores(proveedorServiceImpl.consultaProveedoresPorRfc(reporteProduccion.getRfc()));
        } catch (ProveedorServiceException ex) {
            LOGGER.error(ex);
        }
    }

    public void consultaContribuyentePorRfc(String rfc) {
        try {
            reporteProduccionHelper.setListaTabacaleras(tababacaleraService.consultaContribuyentesPorRfc(reporteProduccion.getRfc()));
        } catch (TabacaleraServiceException e) {
            LOGGER.error("ERROR - Al consultar la lista de Contribuyente por RFC : " + e);
        }
    }

    public void consultaTabacalerasActivas() {
        try {
            reporteProduccionHelper.setListaTabacaleras(tababacaleraService.consultaTabacaleras());
        } catch (TabacaleraServiceException e) {
            LOGGER.error("ERROR - Al consultar las Tabacaleras : ", e);
        }
    }

    public void consultaRazonSocial() {
        try {
            razonSocial = reporteService.consultarRazonSocial(reporteProduccion.getRfc());
        } catch (ReporteServiceException e) {
            LOGGER.error("ERROR - Al consultar la razon social : " + e);
        }
    }

    public void validaRFC() {
        if (reporteProduccion.getRfc() != null && reporteProduccion.getRfc().length() > 0) {
            if (validaRFCExiste()) {
                consultaProveedorPorRfc(reporteProduccion.getRfc());
                consultaContribuyentePorRfc(reporteProduccion.getRfc());
                reporteProduccion.setIdContribuyente(opcionTabacalera);
            } else {
                setOpcionTabacalera(null);
                setOpcionProveedor(null);
                consultaTabacalerasActivas();
                reporteProduccion.setRfc("");
                addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
            }
        } else {
            consultaProveedorPorRfc(reporteProduccion.getRfc());
            consultaTabacalerasActivas();
            setOpcionProveedor(null);
            setOpcionTabacalera(null);
            seleccionOpcionChk = null;
            reporteProduccion.setFechaInicio(null);
            reporteProduccion.setFechaFin(null);
            deshabilitaBtnImprimir = true;
        }
    }

    public boolean validaRFCExiste() {
        boolean rfcExiste = reporteProduccion.getRfc() != null;

        if (rfcExiste) {
            consultaTabacalerasActivas();

            if (reporteProduccionHelper.getListaTabacaleras() != null && !reporteProduccionHelper.getListaTabacaleras().isEmpty()) {
                for (int i=0;i<reporteProduccionHelper.getListaTabacaleras().size();i++) {
                    if (reporteProduccion.getRfc().equals(reporteProduccionHelper.getListaTabacaleras().get(i).getRfc())) {
                        opcionTabacalera = reporteProduccionHelper.getListaTabacaleras().get(i).getIdTabacalera();
                        setOpcionProveedor(null);
                        return true;
                    }
                }
            }

        } else {
            setOpcionTabacalera(null);
            setOpcionProveedor(null);
            consultaTabacalerasActivas();
        }
        return false;
    }

    public boolean validaRangoFechas() {
        Date fecInicio = reporteProduccion.getFechaInicio();
        Date fecFin = reporteProduccion.getFechaFin();
        boolean banderaFecha = Utilerias.isPeriodoValidoMax30dias(fecInicio, fecFin);

        if (!banderaFecha) {
            super.addErrorMessage(ERROR, MSG_ERROR_FECHAS);
            this.reporteProduccion.setFechaInicio(null);
            this.reporteProduccion.setFechaFin(null);
        }
        return banderaFecha;
    }

    public void asignaValorCheck() {
        for (String checkSeleccionado : seleccionOpcionChk) {
            boolean valorCheck;

            valorCheck = (LBL_LOTE.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkLoteProd() && valorCheck) {
                reporteProduccionHelper.setChkLoteProd(valorCheck);
            }

            valorCheck = (LBL_PLANTA.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkPlantaProd() && valorCheck) {
                reporteProduccionHelper.setChkPlantaProd(valorCheck);
            }

            valorCheck = (LBL_CANTIDAD_PROD.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkCantidadProd() && valorCheck) {
                reporteProduccionHelper.setChkCantidadProd(valorCheck);
            }

            valorCheck = (LBL_MAQUINA.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkMaquinaProd() && valorCheck) {
                reporteProduccionHelper.setChkMaquinaProd(valorCheck);
            }

            valorCheck = (LBL_ORIGEN.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkOrigen() && valorCheck) {
                reporteProduccionHelper.setChkOrigen(valorCheck);
            }

            valorCheck = (LBL_LINEA_PRODUCCION.equals(checkSeleccionado));
            if (!reporteProduccionHelper.isChkLineaProd() && valorCheck) {
                reporteProduccionHelper.setChkLineaProd(valorCheck);
            }
        }
    }

    public void inicializaChks() {
        boolean flg = false;

        reporteProduccionHelper.setChkLoteProd(flg);
        reporteProduccionHelper.setChkPlantaProd(flg);
        reporteProduccionHelper.setChkCantidadProd(flg);
        reporteProduccionHelper.setChkMaquinaProd(flg);
        reporteProduccionHelper.setChkOrigen(flg);
        reporteProduccionHelper.setChkLineaProd(flg);
    }

    public boolean habilitaBtnImprimir() {
        deshabilitaBtnImprimir = false;

        if (reporteProduccion.getRfc() == null || reporteProduccion.getRfc().length() == 0) {
            deshabilitaBtnImprimir = true;
            if (reporteProduccion.getFechaInicio() != null && reporteProduccion.getFechaFin() != null && validaRangoFechas()) {
                deshabilitaBtnImprimir = false;
            }
        }
        if (reporteProduccion.getFechaInicio() == null || reporteProduccion.getFechaFin() == null) {
            deshabilitaBtnImprimir = true;
        }

        if (seleccionOpcionChk != null && seleccionOpcionChk.length > 0) {
            deshabilitaBtnImprimir = false;
            if (reporteProduccion.getRfc() == null && reporteProduccion.getFechaInicio() == null && reporteProduccion.getFechaFin() == null) {
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

    public ReporteProduccion getReporteProduccion() {
        return reporteProduccion;
    }

    public void setReporteProduccion(ReporteProduccion reporteProduccion) {
        this.reporteProduccion = reporteProduccion;
    }

    public List<ReporteProduccion> getListaReporteProduccion() {
        return listaReporteProduccion;
    }

    public void setListaReporteProduccion(List<ReporteProduccion> listaReporteProduccion) {
        this.listaReporteProduccion = listaReporteProduccion;
    }

    public ReporteProduccionHelper getReporteProduccionHelper() {
        return reporteProduccionHelper;
    }

    public void setReporteProduccionHelper(ReporteProduccionHelper reporteProduccionHelper) {
        this.reporteProduccionHelper = reporteProduccionHelper;
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

    public Long getOpcionProveedor() {
        return opcionProveedor;
    }

    public void setOpcionProveedor(Long opcionProveedor) {
        this.opcionProveedor = opcionProveedor;
    }

    public Long getOpcionTabacalera() {
        return opcionTabacalera;
    }

    public void setOpcionTabacalera(Long opcionTabacalera) {
        this.opcionTabacalera = opcionTabacalera;
    }

    public boolean isDeshabilitaBtnImprimir() {
        return deshabilitaBtnImprimir;
    }

    public void setDeshabilitaBtnImprimir(boolean deshabilitaBtnImprimir) {
        this.deshabilitaBtnImprimir = deshabilitaBtnImprimir;
    }
}
