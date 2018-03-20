/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.codigos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.SolicitudService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.QRCodeUtilException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SelloDigitalException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.QRCodeUtil;
import mx.gob.sat.mat.tabacos.negocio.util.SelloDigitalUtil;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.DescargaCodigosHelper;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Controller("descargaCodigosMB")
@Scope(value = "view")
public class DescargaCodigosMBean extends AbstractManagedBean {

    private ValidarAccesoRespuesta accesoRespuesta;
    @Autowired
    @Qualifier("solicitudService")
    private SolicitudService solicitudService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    private DescargaCodigosHelper descargaCodigosHelper;
    private boolean activarBusqueda;
    public static final String TABLE_MSGEMTY = "La información no es correcta";
    private FirmaFormHelper firmaFormHelper;
    private Archivo archivoSeleccionado;
    private String idAcuseRecibo;

    @Autowired
    @Qualifier("selloDigitalUtil")
    private SelloDigitalUtil selloDigital;

    private boolean solicitud = true;
    private boolean firma = false;
    private boolean acuse = false;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
        accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);
        descargaCodigosHelper = new DescargaCodigosHelper(accesoRespuesta.getTabacaleras());
        if (descargaCodigosHelper.getLstTabacaleras().size() > 0) {
            descargaCodigosHelper.setRfcTabacalera(descargaCodigosHelper.getLstTabacaleras().get(0).getRfc());
        }
        activarBusqueda = false;
        firmaFormHelper = new FirmaFormHelper();
        firmaFormHelper.setRfcSession(getRFCSession());
    }

    public void habilitarFirma() {
        firma = true;
        solicitud = false;
        acuse = false;
    }

    public void habilitarAcuse() {
        firma = false;
        solicitud = false;
        acuse = true;
    }

    public void habilitarSolicitud() {
        firma = false;
        solicitud = true;
        acuse = false;
    }

    public void buscarSolicitud() {
        try {
            if ((descargaCodigosHelper.getIdSolicitud() != null) && (descargaCodigosHelper.getRfcTabacalera() != null)) {
                descargaCodigosHelper.setLstSolicitudResolucions(solicitudService.buscaSolicitudAutorizada(descargaCodigosHelper.getRfcTabacalera(), descargaCodigosHelper.getIdSolicitud(), EstadoResolucionEnum.GENERADO));
                if ((descargaCodigosHelper.getLstSolicitudResolucions() == null) || (descargaCodigosHelper.getLstSolicitudResolucions().isEmpty())) {
                    addMessage(INFO, TABLE_MSGEMTY);
                } else {
                    descargaCodigosHelper.setAutorizacionResol(descargaCodigosHelper.getLstSolicitudResolucions().get(0));
                }
            }
        } catch (SolicitudServiceException ex) {
            getLogger().error(ex);
        }
    }

    public void activarBusqueda() {
        activarBusqueda = ((descargaCodigosHelper.getRfcTabacalera() != null) && (descargaCodigosHelper.getIdSolicitud() != null));
    }

    public void guardarSolicitud() {
        Acuse acuseRecibo;
        Map<String, String> paramMap = getParametrosSesion();
        try {
            if (paramMap != null) {
                String cadenaFirmada = paramMap.get(FirmaFormHelper.FIRMA_DIGITAL);
                getLogger().debug(cadenaFirmada);

                if ((cadenaFirmada != null) && (cadenaFirmada.length() > 0)) {
                    firmaFormHelper.setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));
                    acuseRecibo = new Acuse();
                    acuseRecibo.setSerieAcuse(TipoAcuse.DESCARGA);
                    acuseRecibo.setIdSolicitud(descargaCodigosHelper.getIdSolicitud());
                    acuseRecibo.setSelloDigital(firmaFormHelper.getSelloDigital());
                    acuseRecibo.setCadenaOriginal(firmaFormHelper.getCadenaOriginal());
                    acuseRecibo.setFecCaptura(new Date());
                    idAcuseRecibo = commonService.crearAcuse(acuseRecibo);
                    habilitarAcuse();
                    registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES,new Date(),new Date(),MovimientosBitacoraEnum.DESCARGA_CODIGOS);
                } else {
                    habilitarSolicitud();
                }
            }
        } catch (Exception ex) {
            getLogger().error(ex);

        }

    }

    public void btnGenerarAcusePDF() {

        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", descargaCodigosHelper.getRfcTabacalera());
            parameters.put("folio", idAcuseRecibo);
            parameters.put("fecha", descargaCodigosHelper.getAutorizacionResol().getFechaResolucion());
            parameters.put("cantsol", descargaCodigosHelper.getAutorizacionResol().getCodigosAutorizados());
            parameters.put("cadenaOriginal", firmaFormHelper.getCadenaOriginal());
            parameters.put("selloDigital", selloDigital.generaSelloDigital(firmaFormHelper.getCadenaOriginal()));
            parameters.put("codigo_qr", QRCodeUtil.getQr(idAcuseRecibo, descargaCodigosHelper.getRfcTabacalera(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_DESCARGA, ARCHIVO_PDF, parameters, null);
            }
            //   Agregar metodo que genera Jasper

            generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargaDeCodigos_" + idAcuseRecibo, FileExtensionEnum.PDF);

        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(e);
        } catch (QRCodeUtilException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(ex);
        } catch (SelloDigitalException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error(ex);
        }

    }

    public void descargaArchivo() {
        InputStream is = null;
        try {
            getLogger().error(":::::::::::::::::::Ruta para desplegar el archivo" + archivoSeleccionado.getNombreArchivo() + " " + archivoSeleccionado.getRutaArchivo());
            is = new FileInputStream(archivoSeleccionado.getRutaArchivo());
            byte[] bytesFile = IOUtils.toByteArray(is);
            generaDocumento(bytesFile, MIMETypesEnum.ZIP, archivoSeleccionado.getNombreArchivo(), FileExtensionEnum.WITHOUT);
            super.addMessage(INFO, "Transacción completada correctamente");
        } catch (IOException ioe) {
            getLogger().error(ioe);
            addErrorMessage(ERROR, "No se localiza el archivo");
        } catch (Exception ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, "No se localiza el archivo");
        }
    }

    public void generaCadenaOriginal(String archivo) {
        if (descargaCodigosHelper.getAutorizacionResol() != null) {
            try {
                firmaFormHelper.setCadenaOriginal(solicitudService.generaCadenaOriginal(descargaCodigosHelper.getRfcTabacalera(), descargaCodigosHelper.getAutorizacionResol().getCodigosAutorizados(), archivo));
            } catch (SolicitudServiceException ex) {
                getLogger().error(ex);
                firmaFormHelper.setCadenaOriginal("");
            }
        }
    }

    public String getTripDateTimeDisplay() {
        if (descargaCodigosHelper.getAutorizacionResol() != null) {
            return FechaUtil.formatFecha(descargaCodigosHelper.getAutorizacionResol().getFechaResolucion(), FechaUtil.FORMATO_DEFAULT);
        } else {
            return "";
        }
    }

    public void firmar() {
        habilitarFirma();
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public ValidarAccesoRespuesta getAccesoRespuesta() {
        return accesoRespuesta;
    }

    public void setAccesoRespuesta(ValidarAccesoRespuesta accesoRespuesta) {
        this.accesoRespuesta = accesoRespuesta;
    }

    public DescargaCodigosHelper getDescargaCodigosHelper() {
        return descargaCodigosHelper;
    }

    public void setDescargaCodigosHelper(DescargaCodigosHelper descargaCodigosHelper) {
        this.descargaCodigosHelper = descargaCodigosHelper;
    }

    public boolean isActivarBusqueda() {
        return activarBusqueda;
    }

    public void setActivarBusqueda(boolean activarBusqueda) {
        this.activarBusqueda = activarBusqueda;
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

    public boolean isFirma() {
        return firma;
    }

    public void setFirma(boolean firma) {
        this.firma = firma;
    }

    public boolean isAcuse() {
        return acuse;
    }

    public void setAcuse(boolean acuse) {
        this.acuse = acuse;
    }

    public Archivo getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setArchivoSeleccionado(Archivo archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
        generaCadenaOriginal(archivoSeleccionado.getNombreArchivo());
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }

}
