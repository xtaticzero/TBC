/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.codigos;

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
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.AcuseTransaccion;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
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
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import mx.gob.sat.mat.tabacos.vista.helper.SolicitarCodigoHelper;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Controller("solicitarCodigosMB")
@Scope(value = "view")
public class SolicitarCodigosMBean extends AbstractManagedBean {

    private SolicitarCodigoHelper solicitarCodigoHelper;
    private ValidarAccesoRespuesta accesoRespuesta;
    private FirmaFormHelper firmaFormHelper;
    private AcuseTransaccion acuseTransaccion;
    private Solicitud solicitudGen;

    private boolean solicitud = true;
    private boolean firma = false;
    private boolean acuse = false;
    private boolean solicitar = false;

    private StreamedContent file;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("solicitudService")
    private SolicitudService solicitudService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Autowired
    @Qualifier("selloDigitalUtil")
    private SelloDigitalUtil selloDigital;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());

            accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);

            solicitarCodigoHelper = new SolicitarCodigoHelper();
            firmaFormHelper = new FirmaFormHelper();
            solicitudGen = new Solicitud();
            acuseTransaccion = new AcuseTransaccion();

            firmaFormHelper.setRfcSession(getRFCSession());
            solicitarCodigoHelper.setLstPais(solicitudService.getLstOrigen());
            solicitarCodigoHelper.setLstTipoContrib(solicitudService.getLstTipoContribuyente());

            inicializarLsts();

            if (!solicitarCodigoHelper.getLstContribuyente().isEmpty()) {
                solicitarCodigoHelper.setTabacalera(solicitarCodigoHelper.getLstContribuyente().get(0));
            }
            if (!solicitarCodigoHelper.getLstProveedores().isEmpty()) {
                solicitarCodigoHelper.setProveedor(solicitarCodigoHelper.getLstProveedores().get(0));
            }
        } catch (SolicitudServiceException ex) {
            getLogger().error("Error al cargar la informacion", ex);
        }

    }

    public void inicializarLsts() {
        solicitarCodigoHelper.setLstProveedores(new ArrayList<Proveedor>());
        solicitarCodigoHelper.setLstContribuyente(new ArrayList<Tabacalera>());
        for (Proveedor pro : accesoRespuesta.getProveedores()) {
            Proveedor proTmp = new Proveedor();
            proTmp.setIdProveedor(pro.getIdProveedor());
            proTmp.setRfc(pro.getRfc());
            proTmp.setRazonSocial(pro.getRazonSocial());
            proTmp.setDomicilio(pro.getDomicilio());
            proTmp.setEmail(pro.getEmail());
            solicitarCodigoHelper.getLstProveedores().add(proTmp);
        }

        for (Tabacalera tab : accesoRespuesta.getTabacaleras()) {
            Tabacalera tabacalera = new Tabacalera();
            tabacalera.setIdTabacalera(tab.getIdTabacalera());
            tabacalera.setRfc(tab.getRfc());
            tabacalera.setRazonSocial(tab.getRazonSocial());
            solicitarCodigoHelper.getLstContribuyente().add(tabacalera);
        }

    }

    public void actualizaRSTavacalera() {
        solicitarCodigoHelper.setTabacalera(getTavacalera(solicitarCodigoHelper.getTabacalera().getRfc(), accesoRespuesta.getTabacaleras()));
        inicializarLsts();
    }

    public void actualizaRSProveedor() {
        solicitarCodigoHelper.setProveedor(getProveedor(solicitarCodigoHelper.getProveedor().getRfc(), accesoRespuesta.getProveedores()));
        inicializarLsts();
    }

    public Tabacalera getTavacalera(String rfc, List<Tabacalera> lstTabacaleras) {

        if (rfc != null) {
            Tabacalera tabacalera;
            for (Tabacalera tbc : lstTabacaleras) {
                if (tbc.getRfc().equals(rfc)) {
                    tabacalera = new Tabacalera();
                    tabacalera.setIdTabacalera(tbc.getIdTabacalera());
                    tabacalera.setRfc(tbc.getRfc());
                    tabacalera.setRazonSocial(tbc.getRazonSocial());
                    return tabacalera;
                }
            }
        }
        return null;

    }

    public Proveedor getProveedor(String rfc, List<Proveedor> lstProveedoress) {
        if (rfc != null) {
            Proveedor proveedor;
            for (Proveedor prov : lstProveedoress) {
                if (prov.getRfc().equals(rfc)) {
                    proveedor = new Proveedor();
                    proveedor.setIdProveedor(prov.getIdProveedor());
                    proveedor.setRfc(prov.getRfc());
                    proveedor.setRazonSocial(prov.getRazonSocial());
                    return proveedor;
                }
            }
        }

        return null;
    }

    public void guardarSolicitud() {
        Map<String, String> paramMap;
        RelacionTabProv relacionTabProv;

        try {
            paramMap = getParametrosSesion();
            relacionTabProv = commonService.getRelacionTabProv(solicitarCodigoHelper.getProveedor().getRfc(), solicitarCodigoHelper.getTabacalera().getRfc());
            solicitudGen = getSolicitud(relacionTabProv.getIdTbcProv());
            if (paramMap != null && ((paramMap.get(FirmaFormHelper.CADENA_ORIGINA) != null) && (paramMap.get(FirmaFormHelper.FIRMA_DIGITAL) != null))) {
                String cadenaFirmada = paramMap.get(FirmaFormHelper.FIRMA_DIGITAL);
                firmaFormHelper.setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));
                if (cadenaFirmada.length() > 0) {
                    if (solicitudGen != null) {
                        acuseTransaccion = commonService.crearSolicitud(solicitudGen, firmaFormHelper.getSelloDigital(), firmaFormHelper.getCadenaOriginal());
                    }

                    if (acuseTransaccion != null) {
                        habilitarAcuse();
                        getLogger().debug("Guardar la solicitud");
                        registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES, new Date(), new Date(), MovimientosBitacoraEnum.SOLICITAR_CODIGOS);
                    }
                } else {
                    habilitarSolicitud();
                }
            }
        } catch (Exception ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, "Error al generar la solicitud");
        }
    }

    public void firmar() {
        if ((solicitarCodigoHelper.getTabacalera().getRfc() != null) && (solicitarCodigoHelper.getProveedor().getRfc() != null) && (solicitarCodigoHelper.getOrigen() != null) && (solicitarCodigoHelper.getCantidadCodigos() != null)) {
            habilitarFirma();
        } else {
            habilitarSolicitud();
        }
    }

    public Solicitud getSolicitud(Long idTbcProv) {
        Solicitud solicitudCod;
        solicitudCod = new Solicitud();
        solicitudCod.setCantSolicitada(solicitarCodigoHelper.getCantidadCodigos());
        solicitudCod.setIdPaisOrigen(solicitarCodigoHelper.getOrigen().getIdPais());
        solicitudCod.setIdTbcProv(idTbcProv);
        solicitudCod.setIdTipoContrib(solicitarCodigoHelper.getTipoContribuyente());
        solicitudCod.setFecSolicitud(new Date());

        getLogger().debug("Solicitud : " + solicitudCod.getIdSolicitud());

        return solicitudCod;
    }

    public void btnGenerarAcusePDF() {

        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", solicitarCodigoHelper.getTabacalera().getRfc());
            parameters.put("folio", acuseTransaccion.getFolioAcuse());
            parameters.put("fecha", solicitudGen.getFecSolicitud());
            parameters.put("cantsol", solicitudGen.getCantSolicitada());
            parameters.put("cadenaOriginal", firmaFormHelper.getCadenaOriginal());
            parameters.put("selloDigital", selloDigital.generaSelloDigital(firmaFormHelper.getCadenaOriginal()));
            parameters.put("codigo_qr", QRCodeUtil.getQr(acuseTransaccion.getFolioAcuse(), solicitarCodigoHelper.getTabacalera().getRfc(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_SOLICITUD, ARCHIVO_PDF, parameters, null);
            }
            //   Agregar metodo que genera Jasper
            generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseSolicitudDeCodigos", FileExtensionEnum.PDF);

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

    public void habilitarBtnSolicitud() {
        if ((solicitarCodigoHelper.getTabacalera().getRfc() != null) && (solicitarCodigoHelper.getProveedor() != null)) {
            if ((solicitarCodigoHelper.getCantidadCodigos() != null) && (solicitarCodigoHelper.getOrigen().getIdPais() != null)) {
                if (solicitarCodigoHelper.getTipoContribuyente() != null && (solicitarCodigoHelper.getTabacalera().getRazonSocial() != null) && (solicitarCodigoHelper.getProveedor() != null)) {
                    solicitar = true;
                    generaCadenaOriginal();
                } else {
                    solicitar = false;
                }
            } else {
                solicitar = false;
            }
        } else {
            solicitar = false;
        }
    }

    public void generaCadenaOriginal() {
        try {
            solicitarCodigoHelper.getTabacalera().getRfc();
            firmaFormHelper.setCadenaOriginal(solicitudService.generaCadenaOriginal(solicitarCodigoHelper.getTabacalera().getRfc(), solicitarCodigoHelper.getCantidadCodigos(), null));
        } catch (SolicitudServiceException ex) {
            getLogger().error("Error al generar cadena original", ex);
            firmaFormHelper.setCadenaOriginal("");
        }
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

    public void validateName() {
        getLogger().debug("OnBlur Action");
    }

    public boolean verificaExistenciaRFC(String rfc) {
        return true;
    }

    public SolicitarCodigoHelper getSolicitarCodigoHelper() {
        return solicitarCodigoHelper;
    }

    public void setSolicitarCodigoHelper(SolicitarCodigoHelper solicitarCodigoHelper) {
        this.solicitarCodigoHelper = solicitarCodigoHelper;
    }

    public ValidarAccesoRespuesta getAccesoRespuesta() {
        return accesoRespuesta;
    }

    public void setAccesoRespuesta(ValidarAccesoRespuesta accesoRespuesta) {
        this.accesoRespuesta = accesoRespuesta;
    }

    public FirmaFormHelper getFirmaFormHelper() {
        return firmaFormHelper;
    }

    public void setFirmaFormHelper(FirmaFormHelper firmaFormHelper) {
        this.firmaFormHelper = firmaFormHelper;
    }

    public ReporterService getReporterService() {
        return reporterService;
    }

    public void setReporterService(ReporterService reporterService) {
        this.reporterService = reporterService;
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

    public boolean isSolicitud() {
        return solicitud;
    }

    public void setSolicitud(boolean solicitud) {
        this.solicitud = solicitud;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public AcuseTransaccion getAcuseTransaccion() {
        return acuseTransaccion;
    }

    public void setAcuseTransaccion(AcuseTransaccion acuseTransaccion) {
        this.acuseTransaccion = acuseTransaccion;
    }

    public Solicitud getSolicitudGen() {
        return solicitudGen;
    }

    public void setSolicitudGen(Solicitud solicitudGen) {
        this.solicitudGen = solicitudGen;
    }

    public boolean isSolicitar() {
        return solicitar;
    }

    public void setSolicitar(boolean solicitar) {
        this.solicitar = solicitar;
    }

    public String getfechaAcuse() {
        if ((solicitudGen != null) && (solicitudGen.getFecSolicitud() != null)) {
            return FechaUtil.formatFecha(solicitudGen.getFecSolicitud(), FechaUtil.FORMATO_COMPLETO_AMPM);
        } else {
            return "";
        }
    }
}
