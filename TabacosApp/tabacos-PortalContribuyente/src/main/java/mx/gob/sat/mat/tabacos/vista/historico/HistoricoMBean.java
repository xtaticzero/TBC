/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.historico;

import java.util.ArrayList;
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
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.BusquedasService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import org.primefaces.component.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MMMF
 */
@Controller("historicoMB")
@Scope(value = "view")
public class HistoricoMBean extends AbstractManagedBean {

    private static final int ROWS_PAGE = 14;
    private int numberPage;

    public static final String ERROR = "Error";
    private List<Historico> historicos = new ArrayList<Historico>();
    private final Map parameters = new HashMap();

    private String rfcTabacalera;
    private String rsTabacalera;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private BusquedasService genericoService;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
            ValidarAccesoRespuesta accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);
            Date fechaActual = new Date();
            String fechaAct = FechaUtil.formatFecha(fechaActual, FechaUtil.FORMATO_DEFAULT);

            numberPage = 0;
            rfcTabacalera = "";
            rsTabacalera = "";
            obtenerRFCRS(accesoRespuesta);
            historicos = genericoService.listarHistoricosContribuyente(rfcTabacalera);
            //Asignacion de los parametros
            parameters.put("rfcContribuyente", rfcTabacalera);
            parameters.put("nombreRazonSocial", rsTabacalera);
            parameters.put("fechaDescarga", fechaAct);

        } catch (BusinessException bexc) {
            FacesMessage msg = new FacesMessage("Se ha producido un error al cargar historicos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private void obtenerRFCRS(ValidarAccesoRespuesta accesoRespuesta) {
        try {
            boolean flgTebacaleras = accesoRespuesta.getTabacaleras() != null && !accesoRespuesta.getTabacaleras().isEmpty();
            if (flgTebacaleras) {
                rfcTabacalera = accesoRespuesta.getTabacaleras().get(0).getRfc();
                rsTabacalera = accesoRespuesta.getTabacaleras().get(0).getRazonSocial();
            } else {
                rfcTabacalera = "";
                rsTabacalera = "";
            }
        } catch (IndexOutOfBoundsException ex) {
            rfcTabacalera = "";
            rsTabacalera = "";
        }
    }

    public List<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    public ReporterService getReporterService() {
        return reporterService;
    }

    public void setReporterService(ReporterService reporterService) {
        this.reporterService = reporterService;
    }

    public void postProcessXLS(Object document) {
        getLogger().info("\nEntro al manejador!!! \n");
    }

    public void btnGenerarReportePDF() {
        getLogger().info("Generando PDF Historicos");

        try {
            byte[] bytesFile = null;

            if (reporterService != null) {

                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_HISTORICOS, ARCHIVO_PDF,
                        this.parameters, recuperaHistoricosPorPagina());

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.PDF, "reporte_historicos", FileExtensionEnum.PDF);
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, e.getMessage());
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }

    }

    private List<Historico> recuperaHistoricosPorPagina() {
        List<Historico> histPagina = null;
        final DataTable datatableHistoricos = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
                .findComponent("formHist:tbHistoricos");

        if (datatableHistoricos != null) {
            numberPage = datatableHistoricos.getPage();
            getLogger().info("Se obtuvo numero de pagina formHist:tbHistoricos " + numberPage);

            //Historicos por pagina
            Historico hist = null;
            int indexInicial = numberPage * ROWS_PAGE;
            int indexFinal = indexInicial + ROWS_PAGE;
            if (this.historicos != null) {
                histPagina = new ArrayList<Historico>();
                for (int i = indexInicial; i < indexFinal; i++) {
                    if (i < this.historicos.size()) {
                        hist = this.historicos.get(i);
                        histPagina.add(hist);
                    }
                }
            }
        }

        return histPagina;
    }

    public void btnGenerarReporteExcel() {
        getLogger().info("Generando EXCEL Historico");
        try {

            byte[] bytesFile = null;

            if (reporterService != null) {

                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_HISTORICOS, EXCEL_ANTES_2007, this.parameters, recuperaHistoricosPorPagina());
                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.EXCEL, "reporte_historicos", FileExtensionEnum.EXCEL);
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, e.getMessage());
            getLogger().error(e.getMessage());
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }

    }

    public String getRfcTabacalera() {
        return rfcTabacalera;
    }

    public void setRfcTabacalera(String rfcTabacalera) {
        this.rfcTabacalera = rfcTabacalera;
    }

    public String getRsTabacalera() {
        return rsTabacalera;
    }

    public void setRsTabacalera(String rsTabacalera) {
        this.rsTabacalera = rsTabacalera;
    }

}
