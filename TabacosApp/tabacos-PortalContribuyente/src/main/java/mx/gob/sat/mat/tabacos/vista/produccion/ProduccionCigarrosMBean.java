package mx.gob.sat.mat.tabacos.vista.produccion;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.ArchivoFoliosProduccionService;
import mx.gob.sat.mat.tabacos.negocio.ArchivoRangosFolioService;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.PaisOrigenService;
import mx.gob.sat.mat.tabacos.negocio.ProduccionCigarrosService;
import mx.gob.sat.mat.tabacos.negocio.RangoFolioService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.SolicitudService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorRangosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ArchivoFoliosServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.QRCodeUtilException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SelloDigitalException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.QRCodeUtil;
import mx.gob.sat.mat.tabacos.negocio.util.SelloDigitalUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import mx.gob.sat.mat.tabacos.vista.helper.ProduccionCigarrosHelper;
import mx.gob.sat.mat.tabacos.vista.helper.ProduccionHelper;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("produccionCigarrillosMB")
@Scope(value = "view")
public class ProduccionCigarrosMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(ProduccionCigarrosMBean.class);

    @Autowired
    private ProduccionCigarrosService produccionService;
    @Autowired
    private PaisOrigenService paisOrigenService;
    @Autowired
    private ArchivoRangosFolioService archivoFoliosService;
    @Autowired
    private ArchivoFoliosProduccionService archivoFoliosProduccionService;
    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Autowired
    @Qualifier("solicitudService")
    private SolicitudService solicitudService;

    @Autowired
    @Qualifier("validadorRangosService")
    private ValidadorRangosService validadorRangosService;

    @Autowired
    @Qualifier("selloDigitalUtil")
    private SelloDigitalUtil selloDigital;

    @Autowired
    @Qualifier("rangoFolioService")
    private RangoFolioService rangoFolioService;

    @Autowired
    @Qualifier("marcaService")
    private MarcaService marcaService;

    private ValidarAccesoRespuesta accesoRespuesta;
    private ProduccionHelper produccionHelper;

    private static final String MSGERROR = "Error al intentar guardar la información";
    private static final String MSGERRORARCH = "Error al leer el Archivo";
    private static final String MSGERRORVALIDARPROD = "Error al validar la producción";
    private static final String MSGEXITOVALIDAPROD = "La información se validó correctamente";
    
    private static final String MSG_ERROR_KEY = "msg.error.carga.informacion";

    private List<RangoFolio> rangoFoliosList;
    private transient UploadedFile archivoFolios;
    private List<RangoFolio> rangoFoliosListAux;
    private List<RangoFolio> rangoFoliosListTemp;

    private Date fechaMaxima;
    private String idAcuseRecibo;
    private Long cantidadSolicitada;
    private String fechaAcuse;
    private String nombreArchivo;
    private String msgErrorArchivo;
    private String msgExitoArchivo;
    private String nombrePais;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
            accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);

            produccionHelper = new ProduccionHelper();

            habilitarPnlPrincipal();

            produccionHelper.setProduccionCigarrosHelper(new ProduccionCigarrosHelper());
            produccionHelper.setFirmaFormHelper(new FirmaFormHelper());
            produccionHelper.getFirmaFormHelper().setRfcSession(getRFCSession());
            produccionHelper.setProduccion(new ProduccionCigarros());
            produccionHelper.getProduccion().setFechImportacion(new Date());
            produccionHelper.getProduccion().setFechProduccion(new Date());
            fechaMaxima = new Date();

            produccionHelper.setDeshabilitaBtnGuardar(true);
            produccionHelper.setDeshabilitaBtnValidarProd(true);
            produccionHelper.setDeshabilitaCargaArchivo(true);

            produccionHelper.getProduccionCigarrosHelper().setLstPais(solicitudService.getLstOrigen());
            produccionHelper.getProduccionCigarrosHelper().setLstTipoContrib(solicitudService.getLstTipoContribuyente());
            inicializarLsts();

            if (!produccionHelper.getProduccionCigarrosHelper().getLstContribuyente().isEmpty()) {
                produccionHelper.getProduccionCigarrosHelper().setTabacalera(produccionHelper.getProduccionCigarrosHelper().getLstContribuyente().get(0));
            }
            if (!produccionHelper.getProduccionCigarrosHelper().getLstProveedores().isEmpty()) {
                produccionHelper.getProduccionCigarrosHelper().setProveedor(produccionHelper.getProduccionCigarrosHelper().getLstProveedores().get(0));
            }
            if (produccionService != null) {
                produccionHelper.setMarcasList(marcaService.selectedMarcas(produccionHelper.getTabacalera().getIdTabacalera()));
                produccionHelper.setPlantasList(produccionService.selectedPlantas(produccionHelper.getTabacalera().getIdTabacalera()));
                produccionHelper.setOrigenList(paisOrigenService.selectedOrigen());
            }
        } catch (ProduccionServiceException e) {
            addErrorMessage(ERROR, getMessageResourceString(MSG_ERROR_KEY));
            LOGGER.error(getMessageResourceString(MSG_ERROR_KEY) + e.getMessage(), e);
        } catch (ServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString(MSG_ERROR_KEY));
            LOGGER.error(getMessageResourceString(MSG_ERROR_KEY) + ex.getMessage(), ex);
        } catch (MarcaServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString(MSG_ERROR_KEY));
            LOGGER.error(getMessageResourceString(MSG_ERROR_KEY) + ex.getMessage(), ex);
        } catch (SolicitudServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString(MSG_ERROR_KEY));
            LOGGER.error(getMessageResourceString(MSG_ERROR_KEY) + ex.getMessage(), ex);
        }
    }

    public void inicializarLsts() {
        produccionHelper.getProduccionCigarrosHelper().setLstProveedores(new ArrayList<Proveedor>());
        produccionHelper.getProduccionCigarrosHelper().setLstContribuyente(new ArrayList<Tabacalera>());

        for (Proveedor pro : accesoRespuesta.getProveedores()) {
            Proveedor proTmp = new Proveedor();
            proTmp.setIdProveedor(pro.getIdProveedor());
            proTmp.setRfc(pro.getRfc());
            proTmp.setRazonSocial(pro.getRazonSocial());
            proTmp.setDomicilio(pro.getDomicilio());
            proTmp.setEmail(pro.getEmail());
            produccionHelper.getProduccionCigarrosHelper().getLstProveedores().add(proTmp);
        }
        for (Tabacalera tab : accesoRespuesta.getTabacaleras()) {
            produccionHelper.setTabacalera(new Tabacalera());
            produccionHelper.getTabacalera().setIdTabacalera(tab.getIdTabacalera());
            produccionHelper.getTabacalera().setRfc(tab.getRfc());
            produccionHelper.getTabacalera().setRazonSocial(tab.getRazonSocial());
            produccionHelper.getProduccionCigarrosHelper().getLstContribuyente().add(produccionHelper.getTabacalera());
        }
    }

    public void actualizaRSTabacalera() {
        produccionHelper.getProduccionCigarrosHelper().setTabacalera(getTavacalera(produccionHelper.getProduccionCigarrosHelper().getTabacalera().getRfc(), accesoRespuesta.getTabacaleras()));
        inicializarLsts();
    }

    public void actualizaRSProveedor() {
        produccionHelper.getProduccionCigarrosHelper().setProveedor(getProveedor(produccionHelper.getProduccionCigarrosHelper().getProveedor().getRfc(), accesoRespuesta.getProveedores()));
        inicializarLsts();
    }

    public Tabacalera getTavacalera(String rfc, List<Tabacalera> lstTabacaleras) {
        if (rfc != null) {
            Tabacalera tabacaleraTmp;
            for (Tabacalera tbc : lstTabacaleras) {
                if (tbc.getRfc().equals(rfc)) {
                    tabacaleraTmp = new Tabacalera();
                    tabacaleraTmp.setIdTabacalera(tbc.getIdTabacalera());
                    tabacaleraTmp.setRfc(tbc.getRfc());
                    tabacaleraTmp.setRazonSocial(tbc.getRazonSocial());
                    return tabacaleraTmp;
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

    /**
     * Metodo que invoca la lectura del archivo
     *
     * @throws java.io.IOException
     */
    public void leerArchivo() throws IOException {
        String rfcProveedor = produccionHelper.getProduccionCigarrosHelper().getProveedor().getRfc();
        String rfcTabacalera = produccionHelper.getProduccionCigarrosHelper().getTabacalera().getRfc();

        if (archivoFoliosService != null && archivoFolios != null) {
            try {
                nombreArchivo = archivoFolios.getFileName();
                InputStream inputStream = archivoFolios.getInputstream();

                rangoFoliosList = archivoFoliosProduccionService.leerArchivoFolios(inputStream, nombreArchivo);
                rangoFoliosListAux = validadorRangosService.generarRangosProduccion(rfcTabacalera, rfcProveedor, rangoFoliosList);
                setMsgErrorArchivo("");

                solicitudService.generaCadenaOriginal(produccionHelper.getFirmaFormHelper().getRfcSession(), sumaRangosFolio(rangoFoliosListAux), null);
                produccionHelper.getFirmaFormHelper().setCadenaOriginal(solicitudService.generaCadenaOriginal(produccionHelper.getFirmaFormHelper().getRfcSession(), sumaRangosFolio(rangoFoliosListAux), null));

                cantidadSolicitada = sumaRangosFolio(rangoFoliosListAux);

            } catch (ArchivoFoliosServiceException be) {
                msgErrorArchivo = be.getMessage();
                setMsgExitoArchivo("");
                LOGGER.error("Error: al Leer el archivo" + be.getMessage(), be);
            } catch (RangosException rE) {
                msgErrorArchivo = rE.getMessage();
                setMsgExitoArchivo("");
                super.msgError(rE.getMessage());
            } catch (IOException io) {
                msgErrorArchivo = io.getMessage();
                setMsgExitoArchivo("");
                LOGGER.error("ERROR: Al leer el Archivo : " + io.getMessage());
            } catch (SolicitudServiceException ex) {
                LOGGER.error("ERROR: Al leer el Archivo : " + ex.getMessage());
            }
            if (!msgErrorArchivo.equals("")) {
                produccionHelper.setDeshabilitaBtnGuardar(true);
                setMsgExitoArchivo("");
            } else {
                msgExitoArchivo = "El archivo " + nombreArchivo + " se  cargo con éxito";
                setMsgErrorArchivo("");
            }
        } else {
            super.msgError(MSGERRORARCH);
        }
    }

    private Long sumaRangosFolio(List<RangoFolio> rangoFoliosListAux) {
        Long resultado = 0L;

        for (RangoFolio folios : rangoFoliosListAux) {
            if (folios != null) {
                resultado += folios.getFolioInicial() + folios.getFolioFinal();
            }
        }
        return resultado;
    }

    /**
     * Metodo que guarda la produccion
     */
    public void guardarProduccion() {
        try {
            produccionHelper.getProduccion().setIdMarca(produccionHelper.getOpcionMarca());
            produccionHelper.getProduccion().setIdPlantaProd(produccionHelper.getOpcionPlanta());
            produccionHelper.getProduccion().setIdPaisOrigen(produccionHelper.getOpcionOrigen());
            produccionHelper.getProduccion().setDescPaisOrigen(nombrePais);

            if (!habilitarBtnValidarProd()) {
                if (produccionHelper.getProduccion() != null) {
                    produccionHelper.setProduccion(produccionService.guardaProduccion(produccionHelper.getProduccion()));
                    super.msgInfo(MSGEXITOVALIDAPROD);
                    produccionHelper.setDeshabilitaBtnValidarProd(true);
                    produccionHelper.setDeshabilitaCargaArchivo(false);
                } else {
                    super.msgError(MSGERRORVALIDARPROD);
                }
            }
        } catch (ProduccionServiceException e) {
            LOGGER.error("ERROR: Al guardar los datos de produccion" + e.getMessage(), e);
        }
    }

    public void guardarRangosFolios() {
        try {
            if (produccionService != null && !rangoFoliosListAux.isEmpty()) {
                rangoFoliosListTemp = rangoFolioService.guardarRangosFolios(rangoFoliosListAux, produccionHelper.getProduccion());
                limpiaCampos();
                produccionHelper.setDeshabilitaBtnGuardar(true);
                produccionHelper.setDeshabilitaCargaArchivo(true);
            }
        } catch (RangoFolioServiceExcepcion e) {
            super.msgError(MSGERROR);
            LOGGER.error("ERROR: Al guardar los Rangos Folios de la produccion" + e.getMessage(), e);
        }
    }

    /**
     * Metodo encargado de generar el Acuse junto con la firma
     */
    public void guardarSolicitud() {
        Map<String, String> paramMap = getParametrosSesion();
        String cadenaFirmada;
        try {
            if (paramMap != null) {
                cadenaFirmada = paramMap.get(FirmaFormHelper.FIRMA_DIGITAL);
                produccionHelper.getFirmaFormHelper().setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));

                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                produccionHelper.getProduccion().setFechProduccion(new Date());
                fechaAcuse = formato.format(produccionHelper.getProduccion().getFechProduccion());

                if (cadenaFirmada.length() > 0) {
                    guardarRangosFolios();
                    Acuse acuse = new Acuse();
                    acuse.setSerieAcuse(TipoAcuse.PRODUCCION);
                    acuse.setIdProveedor(produccionHelper.getProduccionCigarrosHelper().getProveedor().getIdProveedor());
                    acuse.setSelloDigital(produccionHelper.getFirmaFormHelper().getSelloDigital());
                    acuse.setCadenaOriginal(produccionHelper.getFirmaFormHelper().getCadenaOriginal());
                    acuse.setFecCaptura(new Date());
                    idAcuseRecibo = commonService.crearAcuse(acuse);
                    habilitarPnlAcuse();
                    registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES, new Date(), new Date(), MovimientosBitacoraEnum.PRODUCCION_CIGARRILLOS);
                } else {
                    habilitarPnlPrincipal();
                }
            }
        } catch (CommonServiceException be) {
            LOGGER.error(be.getMessage());
            addErrorMessage(ERROR, "No se pudo generar el acuse");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Metodo encargado de generar el Reporte del acuse en formato PDF
     *
     */
    public void btnGenerarReportePDF() {
        try {
            byte[] bytesFile;
            Map parameters = new HashMap();

            parameters.put("rfc", produccionHelper.getProduccionCigarrosHelper().getTabacalera().getRfc());
            parameters.put("incidencia", "Producción");
            parameters.put("fecha", new Date());
            parameters.put("folio", idAcuseRecibo);
            parameters.put("cadenaOriginal", produccionHelper.getFirmaFormHelper().getCadenaOriginal());
            parameters.put("selloDigital", selloDigital.generaSelloDigital(produccionHelper.getFirmaFormHelper().getCadenaOriginal()));
            parameters.put("codigo_qr", QRCodeUtil.getQr(idAcuseRecibo, produccionHelper.getProduccionCigarrosHelper().getTabacalera().getRfc(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_PRODUCCION_CIGARROS, ARCHIVO_PDF, parameters, null);
                if (bytesFile != null) {
                    generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargapProduccionCigarros_" + idAcuseRecibo, FileExtensionEnum.PDF);
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            LOGGER.error("Error al generar el Reporte en PDF" + e.getMessage());
        } catch (QRCodeUtilException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            LOGGER.error("Error al generar el Reporte en PDF" + ex.getMessage());
        } catch (SelloDigitalException ex) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            LOGGER.error("Error al generar el Reporte en PDF" + ex.getMessage());
        }

    }

    public void limpiaCampos() {
        produccionHelper.setOpcionMarca(null);
        produccionHelper.setOpcionPlanta(null);
        produccionHelper.setOpcionPais(null);
        produccionHelper.setOpcionOrigen(null);
        produccionHelper.getProduccion().setDescMaquinaProd(Constantes.ESPACIO_VACIO);
        produccionHelper.getProduccion().setNumLote(Constantes.ESPACIO_VACIO);
        produccionHelper.getProduccion().setCantidadProd(null);
        setMsgErrorArchivo("");
    }

    public boolean habilitarBtnValidarProd() {
        produccionHelper.setDeshabilitaBtnValidarProd(false);
        produccionHelper.getProduccion().setIdMarca(produccionHelper.getOpcionMarca());
        produccionHelper.getProduccion().setIdPlantaProd(produccionHelper.getOpcionPlanta());
        produccionHelper.getProduccion().setIdPaisOrigen(produccionHelper.getOpcionOrigen());

        if (produccionHelper.getProduccion().getIdMarca() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getIdPlantaProd() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getIdPaisOrigen() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        } else {
            for (PaisOrigen pais : produccionHelper.getOrigenList()) {
                if (produccionHelper.getOpcionOrigen().equals(pais.getIdPais())) {
                    nombrePais = pais.getDescCorta();
                    produccionHelper.getProduccion().setDescPaisOrigen(nombrePais);
                    break;
                }
            }
        }
        if (produccionHelper.getProduccion().getDescMaquinaProd() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getNumLote() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getCantidadProd() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getFechImportacion() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.getProduccion().getFechProduccion() == null) {
            produccionHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (produccionHelper.isDeshabilitaBtnValidarProd()) {
            produccionHelper.getProduccion().setFechProduccion(new Date());
        }
        return produccionHelper.isDeshabilitaBtnValidarProd();
    }

    public boolean habilitarBtnGuardar() {
        produccionHelper.setDeshabilitaBtnGuardar(false);
        if (!rangoFoliosList.isEmpty()) {
            produccionHelper.setDeshabilitaBtnGuardar(false);
        } else {
            produccionHelper.setDeshabilitaBtnGuardar(true);
        }
        return produccionHelper.isDeshabilitaBtnGuardar();
    }

    /**
     * Metodo encargado de invocar la validacion del Archivo a cargar
     *
     */
    public void validaArchivoFolios(FileUploadEvent event) {
        try {
            if (event.getFile() != null) {
                archivoFolios = event.getFile();
                if (archivoFolios.getFileName() != null) {
                    produccionHelper.setDeshabilitaBtnGuardar(false);
                    leerArchivo();
                } else {
                    produccionHelper.setDeshabilitaBtnGuardar(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: No subio correctamente el archivo" + e.getMessage());
        }
    }

    public void habilitarPnlAcuse() {
        produccionHelper.setPnlPrincipal(false);
        produccionHelper.setPnlAcuse(true);
    }

    public void habilitarPnlPrincipal() {
        produccionHelper.setPnlPrincipal(true);
        produccionHelper.setPnlAcuse(false);
    }

    public ValidarAccesoRespuesta getAccesoRespuesta() {
        return accesoRespuesta;
    }

    public void setAccesoRespuesta(ValidarAccesoRespuesta accesoRespuesta) {
        this.accesoRespuesta = accesoRespuesta;
    }

    public List<RangoFolio> getRangoFoliosListAux() {
        return rangoFoliosListAux;
    }

    public void setRangoFoliosListAux(List<RangoFolio> rangoFoliosListAux) {
        this.rangoFoliosListAux = rangoFoliosListAux;
    }

    public List<RangoFolio> getRangoFoliosListTemp() {
        return rangoFoliosListTemp;
    }

    public void setRangoFoliosListTemp(List<RangoFolio> rangoFoliosListTemp) {
        this.rangoFoliosListTemp = rangoFoliosListTemp;
    }

    public void setFechaMaxima(final Date fechaMaxima) {
        this.fechaMaxima = (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public Date getFechaMaxima() {
        return (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }

    public Long getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Long cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getFechaAcuse() {
        return fechaAcuse;
    }

    public void setFechaAcuse(String fechaAcuse) {
        this.fechaAcuse = fechaAcuse;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMsgErrorArchivo() {
        return msgErrorArchivo;
    }

    public void setMsgErrorArchivo(String msgErrorArchivo) {
        this.msgErrorArchivo = msgErrorArchivo;
    }

    public String getMsgExitoArchivo() {
        return msgExitoArchivo;
    }

    public void setMsgExitoArchivo(String msgExitoArchivo) {
        this.msgExitoArchivo = msgExitoArchivo;
    }

    public ProduccionHelper getProduccionHelper() {
        return produccionHelper;
    }

    public void setProduccionHelper(ProduccionHelper produccionHelper) {
        this.produccionHelper = produccionHelper;
    }

    public UploadedFile getArchivoFolios() {
        return archivoFolios;
    }

    public void setArchivoFolios(UploadedFile archivoFolios) {
        this.archivoFolios = archivoFolios;
    }
}
