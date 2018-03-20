/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.OpcionReportesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.negocio.MarcaReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.INFO;
import mx.gob.sat.mat.tabacos.vista.util.Expresiones;
import mx.gob.sat.mat.tabacos.vista.util.Utilerias;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRFC;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author emmanuel
 */
@Controller("reporteMarcaMB")
@Scope(value = "view")
public class ReporteMarcaManagedBean extends AbstractManagedBean {

    private static final Logger LOGGER = Logger.getLogger(ReporteMarcaManagedBean.class);

    private static final int REPORTEPDF = 1;
    private static final int REPORTEEXCEL = 2;
    private static final int MIN_TAMANIO_RFC = 11;
    private static final String MSG_REPORTE = "No contiene información el reporte";

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private MarcaReporteService marcaReposteService;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    private List<String> lstTabacaleras;
    private List<SelectItem> marcas;
    private String marca;
    private ReportesParametrosBase marcaFiltro;
    private boolean flgBtnReportes;

    @PostConstruct
    public void init() {
        try {
            marcaFiltro = new ReportesParametrosBase();
            marcas = reporteService.consultaCombosMarcasAutorizadas();
            obtenerLstNombreTabacaleras();
        } catch (TabacaleraServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            LOGGER.error(ex.getMessage());
        } catch (ReporteServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            LOGGER.error(ex.getMessage());
        }
    }

    private void obtenerLstNombreTabacaleras() throws TabacaleraServiceException{
        lstTabacaleras = new ArrayList<String>();
        try {
            if (tababacaleraService.buscaTabacaleras() != null && !tababacaleraService.buscaTabacaleras().isEmpty()) {
                for (int i=0;i<tababacaleraService.buscaTabacaleras().size();i++) {
                    lstTabacaleras.add(tababacaleraService.buscaTabacaleras().get(i).getRfc());
                }
            }
        } catch (TabacaleraServiceException ex) {
            LOGGER.error(ex.getMessage());
            throw new TabacaleraServiceException(ex);
        } catch (IndexOutOfBoundsException ex){
            LOGGER.error(ex.getMessage());
            throw new TabacaleraServiceException(ex);
        }
    }

    public void validaRFC() {
        if (marcaFiltro.getRfc() != null && marcaFiltro.getRfc().length() > 0) {
            if (!validaRFCExiste(marcaFiltro.getRfc())) {
                addErrorMessage(ERROR, getMessageResourceString("msg.error.rfc.invalido"));
                marcaFiltro.setRfc("");
                validaParametrosReportes();
            }
            validaParametrosReportes();
        } else {
            validaParametrosReportes();
        }
    }

    public boolean validaRFCExiste(String rfc) {
        if ((rfc != null) && (rfc.length() > 0)) {
            for (String tab : lstTabacaleras) {
                if (rfc.equals(tab)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void validaParametrosReportes() {
        flgBtnReportes = validarRequeridos();
    }

    public boolean validarRequeridos() {
        boolean flgRFC = (marcaFiltro.getRfc() != null && marcaFiltro.getRfc().length() > MIN_TAMANIO_RFC);
        boolean flgFecha;
        if (!flgRFC) {
            flgFecha = (marcaFiltro.getFechainicio() != null && marcaFiltro.getFechafin() != null && validaFormatoFechas());
        } else {
            flgFecha = false;
        }
        boolean flgTipoRep = (marcaFiltro.getSeleccion() != null && marcaFiltro.getSeleccion().length() > 0);

        return ((flgTipoRep) && (flgRFC || flgFecha));
    }

    public boolean validaFormatoFechas() {

        boolean flgFechasValidas = Utilerias.isPeriodoValidoMax30dias(marcaFiltro.getFechainicio(), marcaFiltro.getFechafin());

        if (!flgFechasValidas) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.fechas.rango"));
        }

        return flgFechasValidas;
    }

    public void btnGenerarReporteMarcaPDF() {
        try {
            ReportesFiltroBase filtro = new ReportesFiltroBase();
            marcaFiltro.setFechainicio(marcaFiltro.getFechainicio());

            boolean flgRfcVacio = marcaFiltro.getRfc() == null || marcaFiltro.getRfc().isEmpty();

            if (flgRfcVacio) {
                filtro.setRfc("");
            } else {
                filtro.setRfc(marcaFiltro.getRfc());
            }
            filtro.setFechaInicio(marcaFiltro.getFechainicio());
            filtro.setFechaFin(marcaFiltro.getFechafin());
            filtro.setMarca(marca);
            filtro.setRazonSocial(reporteService.
                    consultarRazonSocial(filtro.getRfc()));

            if (!validarCasos(filtro, marcaFiltro.getSeleccion(), 1)) {
                return;
            }

            byte[] reporte = marcaReposteService.
                    makeReportMarcas(ReportesTabacosEnum.REPORT_MARCAS, filtro, REPORTEPDF);

            if (reporte != null) {
                generaDocumento(reporte, MIMETypesEnum.PDF,
                        "reporte_prueba", FileExtensionEnum.PDF);
            } else {
                addMessage(INFO, MSG_REPORTE);
            }
        } catch (ServiceException ex) {
            LOGGER.error(ex);
        } catch (ReporteServiceException ex) {
            super.addErrorMessage(ERROR, ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
    }

    public void btnGenerarReporteMarcaExcel() {
        try {
            ReportesFiltroBase filtro = new ReportesFiltroBase();
            this.marcaFiltro.setFechainicio(marcaFiltro.getFechainicio());

            boolean flgRfcVacio = marcaFiltro.getRfc() == null || marcaFiltro.getRfc().isEmpty();

            if (flgRfcVacio) {
                filtro.setRfc("");
            } else {
                filtro.setRfc(marcaFiltro.getRfc());
            }

            filtro.setFechaInicio(this.marcaFiltro.getFechainicio());
            filtro.setFechaFin(this.marcaFiltro.getFechafin());
            filtro.setMarca(marca);
            filtro.setRazonSocial(reporteService.
                    consultarRazonSocial(filtro.getRfc()));

            if (!validarCasos(filtro, this.marcaFiltro.getSeleccion(), 1)) {
                return;
            }

            byte[] reporte = marcaReposteService.
                    makeReportMarcas(ReportesTabacosEnum.REPORT_MARCAS, filtro, REPORTEEXCEL);

            if (reporte != null) {
                generaDocumento(reporte, MIMETypesEnum.EXCEL,
                        "reporte_prueba", FileExtensionEnum.EXCEL);
            } else {
                addMessage(INFO, MSG_REPORTE);
            }
        } catch (ReporteServiceException e) {
            LOGGER.error(e);
            super.addErrorMessage(ERROR, e.getMessage());
        } catch (ServiceException ex) {
            LOGGER.error(ex);
            super.addErrorMessage(ERROR, ex.getMessage());
        }
    }

    public void onCaptRFC() {
        validaRFC();
        try {
            if (marcaFiltro.getRfc() != null) {
                marcas = reporteService.consultaCboMarcasAutorizadasXRfc(marcaFiltro.getRfc());
            } else {
                marcas = reporteService.consultaCombosMarcasAutorizadas();
                marca = null;
            }
        } catch (ReporteServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            getLogger().error(ex);
        }
    }

    /**
     *
     * @return
     */
    public Date getToday() {
        return new Date();
    }

    /**
     *
     * @param filtro
     * @param seleccion
     * @param opcion
     * @return
     */
    public boolean validarCasos(ReportesFiltroBase filtro, String seleccion, int opcion) {
        if (filtro.getRfc() != null && (filtro.getRfc().equals("")
                && filtro.getFechaInicio() == null
                && filtro.getFechaFin() == null)) {
            super.addErrorMessage(ERROR, "Debe ingresar RFC o fecha");
            return false;
        }

        if (filtro.getFechaInicio() != null
                && filtro.getFechaFin() == null) {
            super.addErrorMessage(ERROR, "Debe de seleccionar la fecha fin");
            return false;
        }

        if (filtro.getRfc() != null) {
            if (!filtro.getRfc().equals("")) {
                if (!ValidaRFC.validaRFC(filtro.getRfc())) {
                    return false;
                }
                if (filtro.getRfc().length() == Expresiones.RFC_LONGITUD) {
                    if (!ValidaRFC.validaRFCFisica(filtro.getRfc())) {
                        return false;
                    }
                } else if (filtro.getRfc().length() == Expresiones.RFCM_LONGITUD && (!ValidaRFC.validaRFCMoral(filtro.getRfc()))) {
                    return false;
                }
            }
        } else {
            super.addErrorMessage(ERROR, "Debe de contener un rfc");
        }

        if (opcion == 1) {
            if (seleccion != null) {
                if (seleccion.equals(OpcionReportesEnum.ALTA.getOpcion())) {
                    filtro.setMovimiento(OpcionReportesEnum.ALTA.getIdOpcion());
                } else if (seleccion.equals(OpcionReportesEnum.CAMBIO.getOpcion())) {
                    filtro.setMovimiento(OpcionReportesEnum.CAMBIO.getIdOpcion());
                } else if (seleccion.equals(OpcionReportesEnum.BAJA.getOpcion())) {
                    filtro.setMovimiento(OpcionReportesEnum.BAJA.getIdOpcion());
                }
            } else {
                super.addErrorMessage(ERROR, "Debe seleccionar por lo menos una opción");
                return false;
            }
        }

        return true;
    }

    /**
     * getters and setters.
     */
    /**
     * @return
     */
    public ReportesParametrosBase getMarcaFiltro() {
        return marcaFiltro;
    }

    /**
     *
     * @param marcaFiltro
     */
    public void setMarcaFiltro(ReportesParametrosBase marcaFiltro) {
        this.marcaFiltro = marcaFiltro;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getMarcas() {
        return marcas;
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<String> getLstTabacalerasActivas() {
        return lstTabacaleras;
    }

    public void setLstTabacalerasActivas(List<String> lstTabacalerasActivas) {
        this.lstTabacaleras = lstTabacalerasActivas;
    }

    public boolean isFlgBtnReportes() {
        return flgBtnReportes;
    }

    public void setFlgBtnReportes(boolean flgBtnReportes) {
        this.flgBtnReportes = flgBtnReportes;
    }

}
