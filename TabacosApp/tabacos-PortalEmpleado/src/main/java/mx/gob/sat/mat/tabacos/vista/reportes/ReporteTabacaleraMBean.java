/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.reportes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import static mx.gob.sat.mat.tabacos.constants.Constantes.EXCEL_ANTES_2007;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import mx.gob.sat.mat.tabacos.vista.helper.ReporteTabacaleraHelper;
import mx.gob.sat.mat.tabacos.vista.util.Utilerias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Controller("reporteTabacaleraMB")
@Scope(value = "view")
public class ReporteTabacaleraMBean extends AbstractManagedBean {

    private static final int MIN_TAMANIO_RFC = 11;
    private ReporteTabacaleraHelper reporteTabacaleraHelper;

    private static final int ALTA = 1;
    private static final int BAJA = 2;
    private static final int CAMBIO = 3;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    private boolean flgBtnReportes;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REPORTES.getDescripcion());
        reporteTabacaleraHelper = new ReporteTabacaleraHelper();
        reporteTabacaleraHelper.setFechaMaxima(new Date());

        try {
            reporteTabacaleraHelper.setLstTabacaleras(tababacaleraService.buscaTabacaleras());
        } catch (TabacaleraServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            getLogger().error(ex);
        }
    }

    public boolean validaFormatoFechas() {
        
        boolean flgFechasValidas =  Utilerias.isPeriodoValidoMax30dias(reporteTabacaleraHelper.getFechaInicio(),reporteTabacaleraHelper.getFechaFin());
        
        if(!flgFechasValidas){
            addErrorMessage(ERROR, getMessageResourceString("msg.error.fechas.rango"));
        }
        
        return flgFechasValidas;
    }

    public void validaRFC() {
        if (reporteTabacaleraHelper.getRfcContribuyente() != null && reporteTabacaleraHelper.getRfcContribuyente().length() > 0) {
            if (!validaRFCExiste(reporteTabacaleraHelper.getRfcContribuyente())) {
                addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
                reporteTabacaleraHelper.setRfcContribuyente("");
                validaParametrosReportes();
            }
            validaParametrosReportes();
        } else {
            validaParametrosReportes();
        }
    }

    public void validaParametrosReportes() {
        flgBtnReportes = validarRequeridos();
    }

    public boolean validarRequeridos() {
        boolean flgRFC = (reporteTabacaleraHelper.getRfcContribuyente() != null && reporteTabacaleraHelper.getRfcContribuyente().length() > MIN_TAMANIO_RFC);
        boolean flgFecha;
        if(!flgRFC){
            flgFecha = (reporteTabacaleraHelper.getFechaInicio() != null && reporteTabacaleraHelper.getFechaFin() != null && validaFormatoFechas());
        }else{
            flgFecha = false;
        }
        boolean flgTipoRep = (reporteTabacaleraHelper.getTipoReporte() != null && reporteTabacaleraHelper.getTipoReporte().getDescripcion().length() > 0);

        return ((flgTipoRep) && (flgFecha || flgRFC));
    }

    public boolean validaRFCExiste(String rfc) {
        if ((rfc != null) && (rfc.length() > 0)) {
            for (Tabacalera tab : reporteTabacaleraHelper.getLstTabacaleras()) {
                if (rfc.equals(tab.getRfc())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void estadoBtnReportes(boolean estado) {
        flgBtnReportes = estado;
    }

    public void btnGenerarReporteContribuyentePDF() {
        try {
            byte[] bytesFile;
            Map parameters = new HashMap();

            parameters = configuraParametrosRep(parameters);

            List<Tabacalera> detalleRep = tababacaleraService.buscaReporteTabacalera(reporteTabacaleraHelper.getRfcContribuyente(),
                    reporteTabacaleraHelper.getFechaInicio(),
                    reporteTabacaleraHelper.getFechaFin(),
                    reporteTabacaleraHelper.getTipoReporte());

            if (reporterService != null && detalleRep != null && !detalleRep.isEmpty()) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_CONTRIBUYENTES,
                        ARCHIVO_PDF,
                        parameters,
                        detalleRep);

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.PDF, "ReporteABCContribuyente", FileExtensionEnum.PDF);
            } else {
                addErrorMessage(ERROR, getMessageResourceString("msg.error.reporte.info"));
            }

        } catch (ReporterJasperException e) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.generar.reporte"));
            getLogger().error(e);
        } catch (TabacaleraServiceException ex) {
            Logger.getLogger(ReporteTabacaleraMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnGenerarReporteContribuyenteExcel() {
        try {
            byte[] bytesFile;
            Map parameters = new HashMap();

            parameters = configuraParametrosRep(parameters);

            List<Tabacalera> detalleRep = tababacaleraService.buscaReporteTabacalera(reporteTabacaleraHelper.getRfcContribuyente(),
                    reporteTabacaleraHelper.getFechaInicio(),
                    reporteTabacaleraHelper.getFechaFin(),
                    reporteTabacaleraHelper.getTipoReporte());

            if (reporterService != null && detalleRep != null && !detalleRep.isEmpty()) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_CONTRIBUYENTES,
                        EXCEL_ANTES_2007,
                        parameters,
                        detalleRep);

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.EXCEL, "ReporteABCContribuyente", FileExtensionEnum.EXCEL);
            } else {
                addErrorMessage(ERROR, getMessageResourceString("msg.error.reporte.info"));
            }

        } catch (TabacaleraServiceException e) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.generar.reporte"));
            getLogger().error(e);
        } catch (ReporterJasperException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.generar.reporte"));
            getLogger().error(ex);
        }
    }

    public Map configuraParametrosRep(Map parameters) {
        parameters.put("opcionReporte", getTipoReporte(reporteTabacaleraHelper.getTipoReporte()));
        if (reporteTabacaleraHelper.getRfcContribuyente() != null) {
            parameters.put("rfcContribuyente", reporteTabacaleraHelper.getRfcContribuyente());
            parameters.put("nombreRazonSocial", getRazonSocialReporte(reporteTabacaleraHelper.getRfcContribuyente()));

            parameters.put("fechainicio", null);
            parameters.put("fechafin", null);

        } else {
            parameters.put("rfcContribuyente", null);
            parameters.put("nombreRazonSocial", null);

            parameters.put("fechainicio", reporteTabacaleraHelper.getFechaInicio());
            parameters.put("fechafin", reporteTabacaleraHelper.getFechaFin());

        }
        return parameters;
    }

    public String getRazonSocialReporte(String rfc) {
        if (rfc != null) {
            for (Tabacalera tab : reporteTabacaleraHelper.getLstTabacaleras()) {
                if (rfc.equals(tab.getRfc())) {
                    return tab.getRazonSocial();
                }
            }
        }
        return "";
    }

    public String getTipoReporte(ABCEnum tipoConsulta) {
        switch (tipoConsulta.getId()) {
            case ALTA:
                return getMessageResourceString("label.contribuyente.alta");

            case BAJA:
                return getMessageResourceString("label.contribuyente.baja");

            case CAMBIO:
                return getMessageResourceString("label.contribuyente.mod");

            default:
                return "";
        }
    }

    public ReporteTabacaleraHelper getReporteTabacaleraHelper() {
        return reporteTabacaleraHelper;
    }

    public void setReporteTabacaleraHelper(ReporteTabacaleraHelper reporteTabacaleraHelper) {
        this.reporteTabacaleraHelper = reporteTabacaleraHelper;
    }

    public boolean isFlgBtnReportes() {
        return flgBtnReportes;
    }

    public void setFlgBtnReportes(boolean flgBtnReportes) {
        this.flgBtnReportes = flgBtnReportes;
    }
}
