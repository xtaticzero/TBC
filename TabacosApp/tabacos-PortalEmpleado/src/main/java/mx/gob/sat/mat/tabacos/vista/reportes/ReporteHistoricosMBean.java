/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.reportes;

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
import mx.gob.sat.mat.tabacos.negocio.BusquedasService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import org.apache.log4j.Logger;

import org.primefaces.component.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 *
 * @author MMMF
 */
@Controller("reportesHistoricosMB")
@Scope(value = "view")
public class ReporteHistoricosMBean extends AbstractManagedBean {
    protected static final Logger LOGGER = Logger.getLogger(ReporteHistoricosMBean.class);
    
    private static final int ROWS_PAGE = 14;
    private int numberPage;

    public static final String ERROR = "Error";
    private List<Historico> historicos;
    private Map parameters;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private BusquedasService busquedasService;

    @PostConstruct
    public void init() {
        try {
            parameters = new HashMap();
                    
            validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());

            Date fechaActual = new Date();
            String fechaAct = FechaUtil.formatFecha(fechaActual, FechaUtil.FORMATO_DEFAULT);
            
            numberPage = 0;
            historicos = busquedasService.listarHistoricosContribuyente();
            //Asignacion de los parametros
            parameters.put("fechaDescarga", fechaAct);

        } catch (BusinessException bexc) {
            LOGGER.error(bexc);
            FacesMessage msg = new FacesMessage("Se ha producido un error al cargar historicos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public void btnGenerarReportePDF() {
        LOGGER.info("Generando PDF Historicos");

        try {
            byte[] bytesFile;

            if (reporterService != null) {

                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_HISTORICOS, ARCHIVO_PDF,
                        this.parameters, recuperaHistoricosPorPagina());

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.PDF, "reporteHistoricos", FileExtensionEnum.PDF);
            }
        } catch (ReporterJasperException e) {
            LOGGER.error(e);
            super.addErrorMessage(ERROR, e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    private List<Historico> recuperaHistoricosPorPagina() {
        List<Historico> histPagina = null;
        final DataTable datatableHistoricos = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
                .findComponent("formHist:tbHist");

        if (datatableHistoricos != null) {
            numberPage = datatableHistoricos.getPage();
            LOGGER.info("Se obtuvo numero de pagina formHist:tbHist " + numberPage);

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
        LOGGER.info("Generando EXCEL Historico Empleados");
        try {
            byte[] bytesFile;

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_HISTORICOS, EXCEL_ANTES_2007,
                        this.parameters, recuperaHistoricosPorPagina());
                
                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.EXCEL, "reporteHistoricos", FileExtensionEnum.EXCEL);
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, e.getMessage());
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    
}
