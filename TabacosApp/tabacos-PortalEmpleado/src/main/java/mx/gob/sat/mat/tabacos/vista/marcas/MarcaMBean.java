/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.marcas;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.ReportesDAO;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.AbcMarcaHelper;
import static mx.gob.sat.mat.tabacos.vista.helper.AbcMarcaHelper.MAX_LENGTH_MARCA;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Controller("marcaMBean")
@Scope(value = "view")
public class MarcaMBean extends AbstractManagedBean {

    private AbcMarcaHelper abcMarcaHelper;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporterService reporterService;

    private Marcas movimiento;
    private String motivo;
    private String marcaSeleccionada;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO.getDescripcion());
            movimiento = new Marcas();
            movimiento.setFechaInicio(new Date());
            abcMarcaHelper = new AbcMarcaHelper();

            abcMarcaHelper.setMarcas(marcaService.consultaMarcas());
            abcMarcaHelper.setTabacaleras(marcaService.consultaTabacaleras());
            motivo = "";

            abcMarcaHelper.setMarcaBaja(new Marcas());
            habilitarGuardarBaja();
            Map requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String tmp = (String) requestParams.get("rfc");
            abcMarcaHelper.getMarcaBaja().setFecMovimiento(new Date());
            movimiento.setFecMovimiento(new Date());
            habilitaForm();
            if (tmp != null) {
                movimiento.setRfc(tmp);
                tmp = (String) requestParams.get("clave");
                movimiento.setClave(tmp);
                tmp = (String) requestParams.get("marca");
                movimiento.setMarca(tmp);
                tmp = (String) requestParams.get("fecha");
                movimiento.setFechaAcuse(tmp);
            }
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     * @param event
     */
    public void upload(FileUploadEvent event) {
        if ((movimiento.getRfc() == null) || (movimiento.getMarca() == null)) {
            addErrorMessage(ERROR, "Debera de captruar la marca y rfc, para subir el archivos");
            return;
        }
        if (event.getFile() != null) {
            String tmp = event.getFile().getFileName();
            tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
            abcMarcaHelper.setArchivo(event.getFile());
            abcMarcaHelper.setNombreArchivo(tmp);
            
            habilitarGuardarAlta(false);
        }

    }

    public void habilitarGuardarBaja() {
        boolean flgRfc = (abcMarcaHelper.getMarcaBaja().getRfc() != null);
        boolean flgMarca = marcaSeleccionada != null;
        boolean flgMotivo = abcMarcaHelper.getMarcaBaja().getMotivoBaja() != null;

        abcMarcaHelper.setFlgBtnGuardaBaja(flgRfc && flgMarca && flgMotivo);

    }

    public boolean esClaveValida() {

        try {
            boolean flgClaveNueva = (movimiento.getClave() != null && movimiento.getClave().length() == MAX_LENGTH_MARCA && !marcaService.isClaveRepetida(movimiento.getClave()));

            if (abcMarcaHelper.getMarcaCambio() != null) {
                return ((abcMarcaHelper.getMarcaCambio().getClave()).equals(movimiento.getClave()) || flgClaveNueva);
            }

            if ((movimiento.getClave() != null)) {
                return flgClaveNueva;
            }
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
        }
        return false;
    }

    public void validarClavle() {
        habilitarGuardarAlta(false);
        if (!esClaveValida()) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.solicitud.marca.clave"));
        }
    }

    public void validarClavleCambio() {
        if (!esClaveValida()) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.solicitud.marca.clave"));
        } else {
            habilitarGuardarCambio();
            habilitaForm();
        }
    }

    public boolean esMarcaValida(String marca, String confirmacion) {

        try {
            if (abcMarcaHelper.getMarcaCambio() != null) {
                return validateMarcaCambio(marca,confirmacion);
            }

            if ((marca != null) && (marcaService.isNombreRepetido(marca))) {
                addErrorMessage(ERROR, "El nombre de la marca ya existe para otro producto");
                return false;
            } else {
                return ((marca != null) && (marca.equals(confirmacion)));
            }
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
            return false;
        }
    }
    
    private boolean validateMarcaCambio(String marca, String confirmacion){
        try {
            return abcMarcaHelper.getMarcaCambio().getMarca().equals(marca) && abcMarcaHelper.getMarcaCambio().getMarca().equals(confirmacion) || (!marcaService.isNombreRepetido(marca) && ((marca.equals(confirmacion))));
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
            return false;
        }
    }

    public void validarMarca() {
        esMarcaValida(movimiento.getMarca(), null);
        habilitarGuardarAlta(true);
    }

    public void validarMarcaCambio() {
        if (esMarcaValida(movimiento.getMarca(), abcMarcaHelper.getConfirmacion())) {
            habilitarGuardarCambio();
            habilitaForm();
        } else {
            abcMarcaHelper.setFlgBtnGuardaCambio(false);
        }
    }

    public void validarRfc() {
        habilitarGuardarAlta(false);
    }

    public void habilitarGuardarAlta(boolean saltaClave) {
        esClaveValida();
        if ((movimiento.getRfc() != null)) {
            if (!saltaClave && esMarcaValida(movimiento.getMarca(), abcMarcaHelper.getConfirmacion())) {
                if ((esClaveValida())) {
                    abcMarcaHelper.setFlgFile(true);
                    if ((abcMarcaHelper.getArchivo() != null)) {
                        abcMarcaHelper.setFlgBtnGuarda(true);
                    } else {
                        abcMarcaHelper.setFlgBtnGuarda(false);
                    }
                } else {
                    abcMarcaHelper.setFlgFile(false);
                    abcMarcaHelper.setFlgBtnGuarda(false);
                }
            } else {
                abcMarcaHelper.setFlgBtnGuarda(false);
            }
        } else {
            abcMarcaHelper.setFlgBtnGuarda(false);
        }
    }

    public void habilitarGuardarCambio() {
        boolean flgRequeridosParte1 = movimiento.getRfc() != null
                && marcaSeleccionada != null
                && movimiento.getMarca() != null;
                
        boolean flgRequeridosParte2 = abcMarcaHelper.getConfirmacion() != null
                && movimiento.getClave() != null;
        
        boolean flgRequeridosCompleto = flgRequeridosParte1&&flgRequeridosParte2;
        
        boolean flgValifos = esClaveValida() && (esMarcaValida(movimiento.getMarca(), abcMarcaHelper.getConfirmacion()));
        abcMarcaHelper.setFlgBtnGuardaCambio((flgRequeridosCompleto && flgValifos));
    }

    public void confirmarMarca() {
        habilitarGuardarAlta(false);
        if (movimiento.getMarca() != null && !movimiento.getMarca().equals(abcMarcaHelper.getConfirmacion())) {
            addErrorMessage(ERROR, "La información no coincide");
        }
    }

    public void btnGenerarAcusePDF() {

        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", abcMarcaHelper.getMarcaBaja().getRfc());
            parameters.put("marca", abcMarcaHelper.getMarcaBaja().getMarca());
            parameters.put("clave", abcMarcaHelper.getMarcaBaja().getClave());
            parameters.put("fechaRep", abcMarcaHelper.getMarcaBaja().getFecMovimiento());

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_BAJA_AMARCA, ARCHIVO_PDF, parameters, null);
            }
            //   Agregar metodo que genera Jasper

            generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseBajaDeMarca", FileExtensionEnum.PDF);

        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(e);
        }
    }

    public void btnGenerarAcuseCambioPDF() {

        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", movimiento.getRfc());
            parameters.put("marca", movimiento.getMarca());
            parameters.put("clave", movimiento.getClave());
            parameters.put("fechaRep", movimiento.getFecMovimiento());

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_CAMBIO_AMARCA, ARCHIVO_PDF, parameters, null);
            }
            //   Agregar metodo que genera Jasper

            generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseBajaDeMarca", FileExtensionEnum.PDF);

        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(e);
        }
    }

    /**
     *
     * @return
     */
    public Marcas getMovimiento() {
        return movimiento;
    }

    /**
     *
     * @param movimiento
     */
    public void setMovimiento(Marcas movimiento) {
        this.movimiento = movimiento;
    }

    /**
     *
     * @return
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     *
     * @param motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFechaAcuseBaja() {
        if ((abcMarcaHelper.getMarcaBaja() != null) && (abcMarcaHelper.getMarcaBaja().getFecMovimiento() != null)) {
            return FechaUtil.formatFecha(new Date(), FechaUtil.FORMATO_DEFAULT);
        } else {
            return "";
        }
    }

    public String getFechaAcuseCambio() {
        if ((movimiento != null) && (movimiento.getFecMovimiento() != null)) {
            return FechaUtil.formatFecha(new Date(), FechaUtil.FORMATO_DEFAULT);
        } else {
            return "";
        }
    }

    public boolean isMarcaConfirmada(String marca, String marcaConfirmada) {
        return (marca.equals(marcaConfirmada));
    }

    /**
     *
     */
    public void btnGuardarAlta() {
        Marcas retorno = null;
        try {

            if (!marcaService.existeMarca(movimiento)) {
                retorno = marcaService.insertaMarca(movimiento, abcMarcaHelper.getArchivo().getInputstream(), abcMarcaHelper.getNombreArchivo());
            } else {
                addMessage(INFO, "Ya esta la marca registrada");
            }

            if (retorno == null) {
                addErrorMessage(ERROR, "No se almaceno la marca");
            } else {
                addMessage(INFO, "Se almaceno correctamente la marca");
                registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO,new Date(),new Date(),MovimientosBitacoraEnum.REGISTRO_MARCA);
                String url = "acuseAltaMarca.jsf?rfc=";
                url += movimiento.getRfc();
                url += "&marca=" + movimiento.getMarca();
                url += "&clave=" + movimiento.getClave();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                url += "&fecha=" + df.format(movimiento.getFechaInicio());
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            }
        } catch (IOException ex) {
            getLogger().error(ex);
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void btnGuardarBaja() {
        try {
            movimiento.setMarca(marcaSeleccionada);

            int retorno = marcaService.borraMarca(abcMarcaHelper.getMarcaBaja());
            if (retorno == 0) {
                addErrorMessage(ERROR, "No se borro la marca");
            } else {
                registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO,new Date(),new Date(),MovimientosBitacoraEnum.BAJA_MARCA);
                addMessage(INFO, "La marca fue dada de baja correctamente.");
                habilitarAcuse();
            }
        } catch (MarcaServiceException ex) {
            addErrorMessage(ERROR, "No se pudo dar de baja la marca.");
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void btnGuardarCambio() {
        try {
            Marcas retorno = marcaService.modificaMarca(movimiento, this.marcaSeleccionada);

            if (retorno == null) {
                addErrorMessage(ERROR, getMessageResourceString("msg.error.marcas.modificacion"));
                habilitaForm();
            } else {
                habilitarAcuse();
                registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO,new Date(),new Date(),MovimientosBitacoraEnum.MODIFICACION_MARCA);
                addMessage(INFO, getMessageResourceString("msg.actualizar.marca.exitoso"));
            }
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void btnImprimirAcuse() {
        byte[] retorno = marcaService.generarAcuse(movimiento);
        generaDocumento(retorno, MIMETypesEnum.PDF, "AcuseAltaMarca", FileExtensionEnum.PDF);
    }

    /**
     *
     */
    public void btnSalirAcuse() {
        try {
            String url = "altaMarca.jsf";
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void onCaptRFC() {
        if (movimiento.getRfc() != null) {
            abcMarcaHelper.setMarcas(reporteService.consultaCombos(
                    ReportesDAO.SQL_SELECT_MARCASCB2, "IDMARCA",
                    "NOMMARCA", movimiento.getRfc()));
        }
    }

    /**
     *
     * @param event
     */
    public void selectFiltro(ValueChangeEvent event) {
        String idConf = (String) event.getNewValue();
        abcMarcaHelper.setConfirmacion(marcaService.obtieneMarca(idConf));
    }

    public void selectRFC() {
        if (movimiento.getRfc().equals("-1")) {
            try {
                movimiento = new Marcas();
                movimiento.setFecMovimiento(new Date());
                marcaSeleccionada = null;
                movimiento.setMarca(null);
                movimiento.setClave(null);
                abcMarcaHelper.setConfirmacion(null);
                abcMarcaHelper.setMarcaCambio(null);
                abcMarcaHelper.setMarcas(marcaService.consultaMarcas());
            } catch (MarcaServiceException ex) {
                getLogger().error(ex);
                addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            }
        } else {
            if (movimiento.getRfc() != null) {
                abcMarcaHelper.setMarcas(reporteService.consultaCombos(
                        ReportesDAO.SQL_SELECT_MARCASCB2, "IDMARCA",
                        "NOMMARCA", movimiento.getRfc()));
                marcaSeleccionada = null;
                movimiento.setMarca(null);
                movimiento.setClave(null);
                abcMarcaHelper.setConfirmacion(null);
                abcMarcaHelper.setMarcaCambio(null);
                movimiento.setFecMovimiento(new Date());
            }
        }
        habilitarGuardarCambio();
        habilitaForm();
    }

    public void selectRFCBaja() {
        if (!abcMarcaHelper.getMarcaBaja().getRfc().equals("-1")) {

            abcMarcaHelper.setMarcas(reporteService.consultaCombos(
                    ReportesDAO.SQL_SELECT_MARCASCB2, "IDMARCA",
                    "NOMMARCA", abcMarcaHelper.getMarcaBaja().getRfc()));
            marcaSeleccionada = null;
            abcMarcaHelper.getMarcaBaja().setClave(null);
            habilitaForm();
        } else {
            try {
                abcMarcaHelper = new AbcMarcaHelper();
                abcMarcaHelper.setMarcaBaja(new Marcas());
                abcMarcaHelper.setMarcas(marcaService.consultaMarcas());
                abcMarcaHelper.setTabacaleras(marcaService.consultaTabacaleras());
                marcaSeleccionada = "-1";
                abcMarcaHelper.getMarcaBaja().setFecMovimiento(new Date());
                habilitaForm();
            } catch (MarcaServiceException ex) {
                getLogger().error(ex);
            }
        }
        habilitarGuardarBaja();
    }

    public void selectMarca() {
        if (marcaSeleccionada != null) {
            try {
                abcMarcaHelper.setMarcaBaja(marcaService.buscaMarca(marcaSeleccionada));
                abcMarcaHelper.getMarcaBaja().setFecMovimiento(new Date());
            } catch (MarcaServiceException ex) {
                getLogger().error(ex);
            }
        } 
        habilitarGuardarBaja();
    }

    public void selectMarcaCambio() {
        if (marcaSeleccionada != null) {
            try {
                movimiento = marcaService.buscaMarca(marcaSeleccionada);
                abcMarcaHelper.setMarcaCambio(movimiento.clone());
                abcMarcaHelper.setConfirmacion(movimiento.getMarca());
                movimiento.setFecMovimiento(new Date());
                habilitarGuardarCambio();
                habilitaForm();
            } catch (MarcaServiceException ex) {
                getLogger().error(ex);
            } catch (CloneNotSupportedException ex) {
                getLogger().error(ex);
            }
        }
    }

    public void habilitaForm() {
        abcMarcaHelper.setFormBaja(true);
        abcMarcaHelper.setAcuseBaja(false);
        abcMarcaHelper.setAcuseCambio(false);
    }

    public void habilitarAcuse() {
        abcMarcaHelper.setFormBaja(false);
        abcMarcaHelper.setAcuseBaja(true);
        abcMarcaHelper.setAcuseCambio(true);
    }

    /**
     *
     * @return
     */
    public Date getToday() {
        return new Date();
    }

    public String getMarcaSeleccionada() {
        return marcaSeleccionada;
    }

    public void setMarcaSeleccionada(String marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }

    public AbcMarcaHelper getAbcMarcaHelper() {
        return abcMarcaHelper;
    }

    public void setAbcMarcaHelper(AbcMarcaHelper abcMarcaHelper) {
        this.abcMarcaHelper = abcMarcaHelper;
    }

}
