/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.marcas;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudAltaMarca;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.QRCodeUtilException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SelloDigitalException;
import mx.gob.sat.mat.tabacos.negocio.util.QRCodeUtil;
import mx.gob.sat.mat.tabacos.negocio.util.SelloDigitalUtil;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import mx.gob.sat.mat.tabacos.vista.helper.SolicitudAltaMarcaHelper;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Controller("solicitudAltaMarcaMB")
@Scope(value = "view")
public class SolicitudAltaMarcaMBean extends AbstractManagedBean {

    private SolicitudAltaMarcaHelper solicitudAltaMarcaHelper;
    private FirmaFormHelper firmaFormHelper;
    private ValidarAccesoRespuesta accesoRespuesta;
    private UploadedFile file;
    private String nombreArchivo;
    private String fechaCadena;
    private boolean solicitud;
    private boolean acuse;
    private boolean flgBtnGuarda;
    private boolean flgFile;

    private SolicitudAltaMarca solicitudAltaMarca;

    @Autowired
    @Qualifier("marcaService")
    private MarcaService marcaService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("selloDigitalUtil")
    private SelloDigitalUtil selloDigital;

    public static final int MAX_LENGTH_MARCA = 6;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
        accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);
        solicitudAltaMarcaHelper = new SolicitudAltaMarcaHelper();
        solicitudAltaMarcaHelper.getTabacalera().setRfc(null);
        solicitudAltaMarcaHelper.getTabacalera().setRazonSocial("");
        solicitudAltaMarca = new SolicitudAltaMarca();
        habilitarSolicitud();
        flgBtnGuarda = false;

        for (Tabacalera tab
                : accesoRespuesta.getTabacaleras()) {
            solicitudAltaMarcaHelper.getTabacalera().setIdTabacalera(tab.getIdTabacalera());
            solicitudAltaMarcaHelper.getTabacalera().setRfc(tab.getRfc());
            solicitudAltaMarcaHelper.getTabacalera().setRazonSocial(tab.getRazonSocial());
            break;
        }

        firmaFormHelper = new FirmaFormHelper();
        fechaCadena = FechaUtil.formatFecha(new Date(), FechaUtil.FORMATO_DEFAULT);
        firmaFormHelper.setRfcSession(getRFCSession());
        inicializarLsts();

    }

    public void guardarSolicitud() {
        Map<String, String> paramMap;

        try {
            paramMap = getParametrosSesion();
            if (paramMap != null && ((paramMap.get(FirmaFormHelper.CADENA_ORIGINA) != null) && (paramMap.get(FirmaFormHelper.FIRMA_DIGITAL) != null))) {
                String cadenaFirmada = paramMap.get(FirmaFormHelper.FIRMA_DIGITAL);
                if (cadenaFirmada.length() > 0) {
                    firmaFormHelper.setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));

                    Marcas marca = new Marcas();
                    marca.setRfc(solicitudAltaMarcaHelper.getTabacalera().getRfc());
                    marca.setClave(solicitudAltaMarcaHelper.getClavleMarca());
                    marca.setMarca(solicitudAltaMarcaHelper.getMarca());
                    marca.setIdEstatus(EstatusEnum.SOLICITADO.getKey());
                    marca.setFechaInicio(new Date());
                    marca.setFechaFin(new Date());
                    marca.setIdTabacalera(solicitudAltaMarcaHelper.getTabacalera().getIdTabacalera());
                    marca.setRutaArchivo(marcaService.getRutaDocumentacion(marca.getRfc(), marca.getClave()) + nombreArchivo);

                    solicitudAltaMarca = marcaService.generaSolicitudAltaMarca(marca, file.getInputstream(), nombreArchivo, firmaFormHelper.getCadenaOriginal(), firmaFormHelper.getSelloDigital());
                    habilitarAcuse();
                    registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES,new Date(),new Date(),MovimientosBitacoraEnum.SOLICITAR_ALTA_MARCAS);
                } else {
                    habilitarSolicitud();
                }
            }
            
        } catch (MarcaServiceException serEx) {
            getLogger().error(serEx);
            addErrorMessage(ERROR, serEx.getMessage());
        } catch (Exception ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, "Error al generar la solicitud");
        }
    }

    public void btnGenerarAcusePDF() {

        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", solicitudAltaMarcaHelper.getTabacalera().getRfc());
            parameters.put("folio", solicitudAltaMarca.getIdAcuseRecibo());
            parameters.put("fecha", fechaCadena);
            parameters.put("clave", solicitudAltaMarcaHelper.getClavleMarca());
            parameters.put("marca", solicitudAltaMarcaHelper.getMarca());
            parameters.put("cadenaOriginal", firmaFormHelper.getCadenaOriginal());
            parameters.put("selloDigital", selloDigital.generaSelloDigital(firmaFormHelper.getCadenaOriginal()));
            parameters.put("codigo_qr", QRCodeUtil.getQr(solicitudAltaMarca.getIdAcuseRecibo(), solicitudAltaMarcaHelper.getTabacalera().getRfc(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_ALTA_MARCA, ARCHIVO_PDF, parameters, null);
            }
            //   Agregar metodo que genera Jasper

            generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseSolicitudAltaMarca_" + solicitudAltaMarcaHelper.getClavleMarca(), FileExtensionEnum.PDF);

        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(e);
        } catch (SelloDigitalException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(ex);
        } catch (QRCodeUtilException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(ex);
        }

    }

    public void habilitarSolicitud() {
        solicitud = true;
        acuse = false;
        flgFile = false;
    }

    public void habilitarAcuse() {
        solicitud = false;
        acuse = true;
    }

    public void fileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            file = event.getFile();
            nombreArchivo = Utilerias.obtenerNobreArchivo(file.getFileName());
            habilitarGuardar();
        }
    }

    public void actualizaRSTavacalera() {
        solicitudAltaMarcaHelper.getTabacalera().setRazonSocial(getRSTavacalera(solicitudAltaMarcaHelper.getTabacalera().getRfc(), accesoRespuesta.getTabacaleras()));
        habilitarGuardar();
    }

    public String getRSTavacalera(String rfc, List<Tabacalera> lstTabacaleras) {

        if (rfc != null) {

            for (Tabacalera tbc : lstTabacaleras) {
                if (tbc.getRfc().equals(rfc)) {
                    solicitudAltaMarcaHelper.getTabacalera().setIdTabacalera(tbc.getIdTabacalera());
                    return tbc.getRazonSocial();
                }
            }
        }
        return "";

    }

    public void inicializarLsts() {
        solicitudAltaMarcaHelper.setLstContribuyente(new ArrayList<Tabacalera>());
        for (Tabacalera tab : accesoRespuesta.getTabacaleras()) {
            Tabacalera tabacalera = new Tabacalera();
            tabacalera.setIdTabacalera(tab.getIdTabacalera());
            tabacalera.setRfc(tab.getRfc());
            tabacalera.setRazonSocial(tab.getRazonSocial());
            solicitudAltaMarcaHelper.getLstContribuyente().add(tabacalera);
        }
    }

    public void habilitarGuardar() {
        esClaveValida();
        if ((solicitudAltaMarcaHelper.getTabacalera().getRfc() != null) && (!solicitudAltaMarcaHelper.getTabacalera().getRazonSocial().equals(""))) {
            if (esMarcaValida()) {
                if ((esClaveValida())) {
                    flgFile = true;
                    if ((file != null)) {
                        flgBtnGuarda = true;
                        firmaFormHelper.setCadenaOriginal(marcaService.generaCadenaOriginal(solicitudAltaMarcaHelper.getTabacalera().getRfc(), solicitudAltaMarcaHelper.getMarca(), solicitudAltaMarcaHelper.getClavleMarca()));
                    } else {
                        flgBtnGuarda = false;
                    }
                } else {
                    flgFile = false;
                }

            } else {
                flgBtnGuarda = false;
            }
        } else {
            flgBtnGuarda = false;
        }
    }

    public boolean esClaveValida() {
        if ((solicitudAltaMarcaHelper.getClavleMarca() != null)) {
            try {
                return (solicitudAltaMarcaHelper.getClavleMarca().length() == MAX_LENGTH_MARCA && !marcaService.isClaveRepetida(solicitudAltaMarcaHelper.getClavleMarca()));
            } catch (MarcaServiceException ex) {
                getLogger().error(ex);
                return false;
            }
        }
        return false;
    }

    public void validarClavle() {
        habilitarGuardar();
        if (!esClaveValida()) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.solicitud.marca.clave"));
        }
    }

    public boolean esMarcaValida() {
        try {
            if(marcaService.isNombreRepetido(solicitudAltaMarcaHelper.getMarca())){
                addErrorMessage(ERROR, "El nombre de la marca ya existe para otro producto");
                return false;
            }
            return ((solicitudAltaMarcaHelper.getMarca() != null) && ((solicitudAltaMarcaHelper.getMarca().equals(solicitudAltaMarcaHelper.getConfirmMarca())))&&(!marcaService.isNombreRepetido(solicitudAltaMarcaHelper.getMarca())));
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
            return false;
        }
    }

    public void confirmarMarca() {
        habilitarGuardar();
        if (!esMarcaValida()) {
            addMessage(WARN, "La información no coincide");
        }
    }

    public ValidarAccesoRespuesta getAccesoRespuesta() {
        return accesoRespuesta;
    }

    public void setAccesoRespuesta(ValidarAccesoRespuesta accesoRespuesta) {
        this.accesoRespuesta = accesoRespuesta;
    }

    public SolicitudAltaMarcaHelper getSolicitudAltaMarcaHelper() {
        return solicitudAltaMarcaHelper;
    }

    public void setSolicitudAltaMarcaHelper(SolicitudAltaMarcaHelper solicitudAltaMarcaHelper) {
        this.solicitudAltaMarcaHelper = solicitudAltaMarcaHelper;
    }

    public FirmaFormHelper getFirmaFormHelper() {
        return firmaFormHelper;
    }

    public void setFirmaFormHelper(FirmaFormHelper firmaFormHelper) {
        this.firmaFormHelper = firmaFormHelper;
    }

    public boolean isSolicitud() {
        return solicitud;
    }

    public void setSolicitud(boolean solicitud) {
        this.solicitud = solicitud;
    }

    public boolean isAcuse() {
        return acuse;
    }

    public void setAcuse(boolean acuse) {
        this.acuse = acuse;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isFlgBtnGuarda() {
        return flgBtnGuarda;
    }

    public void setFlgBtnGuarda(boolean flgBtnGuarda) {
        this.flgBtnGuarda = flgBtnGuarda;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getFechaCadena() {
        return fechaCadena;
    }

    public void setFechaCadena(String fechaCadena) {
        this.fechaCadena = fechaCadena;
    }

    public SolicitudAltaMarca getSolicitudAltaMarca() {
        return solicitudAltaMarca;
    }

    public void setSolicitudAltaMarca(SolicitudAltaMarca solicitudAltaMarca) {
        this.solicitudAltaMarca = solicitudAltaMarca;
    }

    public boolean isFlgFile() {
        return flgFile;
    }

    public void setFlgFile(boolean flgFile) {
        this.flgFile = flgFile;
    }

}
