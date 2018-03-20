package mx.gob.sat.mat.tabacos.vista.desperdicio;

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
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoRetro;
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
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.DesperdiciosHelper;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import mx.gob.sat.mat.tabacos.vista.helper.ProduccionCigarrosHelper;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("desperdicioMB")
@Scope(value = "view")
public class DesperdiciosMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(DesperdiciosMBean.class);

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
    @Qualifier("rangoFolioService")
    private RangoFolioService rangoFolioService;

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
    @Qualifier("marcaService")
    private MarcaService marcaService;

    private ProduccionCigarros produccion;
    private List<Marcas> marcasList;
    private List<Planta> plantasList;
    private List<PaisOrigen> paisList;
    private List<PaisOrigen> origenList;
    private List<TipoRetro> tipoRetroList;

    private ProduccionCigarrosHelper produccionCigarrosHelper;
    private ValidarAccesoRespuesta accesoRespuesta;
    private FirmaFormHelper firmaFormHelper;
    private Tabacalera tabacalera;

    private Long opcionMarca;
    private Long opcionPlanta;
    private Long opcionPais;
    private Long opcionOrigen;
    private Long opcionTipo;

    private static final String MSGERROR = "Error al intentar guardar la información";
    private static final String MSGERRORVALIDARPROD = "Error al validar la información";
    private static final String MSGEXITOVALIDAPROD = "La información se valido correctamente";
    
    private static final String MSG_ERROR_KEY = "msg.error.carga.informacion";

    private DesperdiciosHelper desperdiciosHelper;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
            accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);
            
            desperdiciosHelper = new DesperdiciosHelper();
            habilitarPnlPrincipal();
            produccionCigarrosHelper = new ProduccionCigarrosHelper();
            firmaFormHelper = new FirmaFormHelper();
            firmaFormHelper.setRfcSession(getRFCSession());
            produccion = new ProduccionCigarros();
            produccion.setFechImportacion(new Date());
            produccion.setFechProduccion(new Date());

            desperdiciosHelper.setDeshabilitaBtnGuardar(true);
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
            desperdiciosHelper.setDeshabilitaCargaArchivo(true);

            produccionCigarrosHelper.setLstPais(solicitudService.getLstOrigen());
            produccionCigarrosHelper.setLstTipoContrib(solicitudService.getLstTipoContribuyente());
            inicializarLsts();

            if (!produccionCigarrosHelper.getLstContribuyente().isEmpty()) {
                produccionCigarrosHelper.setTabacalera(produccionCigarrosHelper.getLstContribuyente().get(0));
            }
            if (!produccionCigarrosHelper.getLstProveedores().isEmpty()) {
                produccionCigarrosHelper.setProveedor(produccionCigarrosHelper.getLstProveedores().get(0));
            }

            if (produccionService != null) {
                this.marcasList = marcaService.selectedMarcas(tabacalera.getIdTabacalera());
                this.plantasList = produccionService.selectedPlantas(tabacalera.getIdTabacalera());
                this.origenList = paisOrigenService.selectedOrigen();
                this.tipoRetroList = produccionService.selectedTipoRetro();
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
        produccionCigarrosHelper.setLstProveedores(new ArrayList<Proveedor>());
        produccionCigarrosHelper.setLstContribuyente(new ArrayList<Tabacalera>());

        for (Proveedor pro : accesoRespuesta.getProveedores()) {
            Proveedor proTmp = new Proveedor();
            proTmp.setIdProveedor(pro.getIdProveedor());
            proTmp.setRfc(pro.getRfc());
            proTmp.setRazonSocial(pro.getRazonSocial());
            proTmp.setDomicilio(pro.getDomicilio());
            proTmp.setEmail(pro.getEmail());
            produccionCigarrosHelper.getLstProveedores().add(proTmp);
        }
        for (Tabacalera tab : accesoRespuesta.getTabacaleras()) {
            tabacalera = new Tabacalera();
            tabacalera.setIdTabacalera(tab.getIdTabacalera());
            tabacalera.setRfc(tab.getRfc());
            tabacalera.setRazonSocial(tab.getRazonSocial());
            produccionCigarrosHelper.getLstContribuyente().add(tabacalera);
        }
    }

    public void actualizaRSTabacalera() {
        produccionCigarrosHelper.setTabacalera(getTavacalera(produccionCigarrosHelper.getTabacalera().getRfc(), accesoRespuesta.getTabacaleras()));
        inicializarLsts();
    }

    public void actualizaRSProveedor() {
        produccionCigarrosHelper.setProveedor(getProveedor(produccionCigarrosHelper.getProveedor().getRfc(), accesoRespuesta.getProveedores()));
        inicializarLsts();
    }

    public Tabacalera getTavacalera(String rfc, List<Tabacalera> lstTabacaleras) {
        if (rfc != null) {
            Tabacalera tabacaleraAux;
            for (Tabacalera tbc : lstTabacaleras) {
                if (tbc.getRfc().equals(rfc)) {
                    tabacaleraAux = new Tabacalera();
                    tabacaleraAux.setIdTabacalera(tbc.getIdTabacalera());
                    tabacaleraAux.setRfc(tbc.getRfc());
                    tabacaleraAux.setRazonSocial(tbc.getRazonSocial());
                    return tabacaleraAux;
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
     */
    public void leerArchivo() {
        String rfcProveedor = produccionCigarrosHelper.getProveedor().getRfc();
        String rfcTabacalera = produccionCigarrosHelper.getTabacalera().getRfc();

        if (archivoFoliosService != null && desperdiciosHelper.getArchivoFolios() != null) {
            try {
                desperdiciosHelper.setNombreArchivo(desperdiciosHelper.getArchivoFolios().getFileName());
                InputStream inputStream = desperdiciosHelper.getArchivoFolios().getInputstream();

                desperdiciosHelper.setRangoFoliosList(archivoFoliosProduccionService.leerArchivoFolios(inputStream, desperdiciosHelper.getNombreArchivo()));
                desperdiciosHelper.setRangoFoliosListAux(validadorRangosService.generarRangosProduccion(rfcTabacalera, rfcProveedor, desperdiciosHelper.getRangoFoliosList()));
                desperdiciosHelper.setMsgErrorArchivo("");

                solicitudService.generaCadenaOriginal(firmaFormHelper.getRfcSession(), sumaRangosFolio(desperdiciosHelper.getRangoFoliosListAux()), null);
                firmaFormHelper.setCadenaOriginal(solicitudService.generaCadenaOriginal(firmaFormHelper.getRfcSession(), sumaRangosFolio(desperdiciosHelper.getRangoFoliosListAux()), null));
                desperdiciosHelper.setCantidadSolicitada(sumaRangosFolio(desperdiciosHelper.getRangoFoliosListAux()));

            } catch (SolicitudServiceException bE) {
                desperdiciosHelper.setMsgErrorArchivo(bE.getMessage());
                desperdiciosHelper.setMsgExitoArchivo("");
                LOGGER.error("Error: al Leer el archivo" + bE.getMessage(), bE);
            } catch (RangosException rE) {
                desperdiciosHelper.setMsgErrorArchivo(rE.getMessage());
                desperdiciosHelper.setMsgExitoArchivo("");
                super.msgError(rE.getMessage());
            } catch (IOException io) {
                desperdiciosHelper.setMsgErrorArchivo(io.getMessage());
                desperdiciosHelper.setMsgExitoArchivo("");
                LOGGER.error("ERROR: Al leer el Archivo : " + io.getMessage());
            } catch (ArchivoFoliosServiceException ex) {
                LOGGER.error("ERROR: Al leer el Archivo : " + ex.getMessage());
            }
            if (!desperdiciosHelper.getMsgErrorArchivo().isEmpty()) {
                desperdiciosHelper.setDeshabilitaBtnGuardar(true);
                desperdiciosHelper.setMsgExitoArchivo("");
            } else {
                desperdiciosHelper.setMsgExitoArchivo("El archivo " + desperdiciosHelper.getNombreArchivo() + " se  cargo con éxito");
                desperdiciosHelper.setMsgErrorArchivo("");
            }
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
     * Metodo para guardar los datos de la pantalla de Destruccion
     */
    public void guardarDestruccion() {
        try {
            produccion.setIdMarca(opcionMarca);
            produccion.setIdPlantaProd(opcionPlanta);
            produccion.setIdPaisOrigen(opcionOrigen);
            produccion.setIdTipoRetro(opcionTipo);
            produccion.setFechProduccion(new Date());
            produccion.setDescPaisOrigen(desperdiciosHelper.getNombrePais());

            if (!habilitarBtnValidarProd()) {
                if (produccion != null) {
                    produccion = produccionService.guardaDestruccion(produccion);
                    super.msgInfo(MSGEXITOVALIDAPROD);
                    desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
                    desperdiciosHelper.setDeshabilitaCargaArchivo(false);
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
            if (produccionService != null && !desperdiciosHelper.getRangoFoliosListAux().isEmpty()) {
                desperdiciosHelper.setRangoFoliosListTemp(rangoFolioService.guardarRangosFolios(desperdiciosHelper.getRangoFoliosListAux(), produccion));
                limpiaCampos();
                desperdiciosHelper.setDeshabilitaBtnGuardar(true);
                desperdiciosHelper.setDeshabilitaCargaArchivo(true);
            }
        } catch (RangoFolioServiceExcepcion e) {
            super.msgError(MSGERROR);
            LOGGER.error("ERROR: Al guardar los Rangos Folios de la produccion" + e.getMessage(), e);
        }
    }

    /*
     *Metodo encargado de generar el Acuse junto con la firma 
     */
    public void guardarSolicitud() {
        Map<String, String> paramMap = getParametrosSesion();
        String cadenaFirmada;
        try {
            if (paramMap != null) {
                cadenaFirmada = paramMap.get(firmaFormHelper.FIRMA_DIGITAL);
                firmaFormHelper.setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));

                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                produccion.setFechProduccion(new Date());
                desperdiciosHelper.setFechaAcuse(formato.format(produccion.getFechProduccion()));

                if (cadenaFirmada.length() > 0) {
                    guardarRangosFolios();
                    Acuse acuse = new Acuse();
                    acuse.setSerieAcuse(TipoAcuse.DESPERDICIO);
                    acuse.setIdProveedor(produccionCigarrosHelper.getProveedor().getIdProveedor());
                    acuse.setSelloDigital(firmaFormHelper.getSelloDigital());
                    acuse.setCadenaOriginal(firmaFormHelper.getCadenaOriginal());
                    acuse.setFecCaptura(new Date());
                    desperdiciosHelper.setIdAcuseRecibo(commonService.crearAcuse(acuse));
                    habilitarPnlAcuse();
                    registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES, new Date(), new Date(), MovimientosBitacoraEnum.DESPERDICIOS_DESTRUCCION);
                } else {
                    habilitarPnlPrincipal();
                }
            }
        } catch (CommonServiceException e) {
            LOGGER.error(e.getMessage(), e);
            addErrorMessage(ERROR, "No se pudo generar el acuse");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Metodo encargado de generar el Reporte del acuse en formato PDF
     *
     */
    public void btnGenerarReporteDestruccionPDF() {
        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parameters = new HashMap();

            parameters.put("rfc", produccionCigarrosHelper.getTabacalera().getRfc());
            parameters.put("incidencia", "Desperdicios y destrucción");
            parameters.put("fecha", new Date());
            parameters.put("folio", desperdiciosHelper.getIdAcuseRecibo());
            parameters.put("cadenaOriginal", firmaFormHelper.getCadenaOriginal());
            parameters.put("selloDigital", selloDigital.generaSelloDigital(firmaFormHelper.getCadenaOriginal()));
            parameters.put("codigo_qr", QRCodeUtil.getQr(desperdiciosHelper.getIdAcuseRecibo(), produccionCigarrosHelper.getTabacalera().getRfc(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_DESPERDICIO_DESTRUCCION, ARCHIVO_PDF, parameters, null);
                if (bytesFile != null) {
                    generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargapDesperdiciosDestruccion_" + desperdiciosHelper.getIdAcuseRecibo(), FileExtensionEnum.PDF);
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
        setOpcionMarca(null);
        setOpcionPlanta(null);
        setOpcionPais(null);
        setOpcionOrigen(null);
        produccion.setDescMaquinaProd(Constantes.ESPACIO_VACIO);
        produccion.setNumLote(Constantes.ESPACIO_VACIO);
        produccion.setCantidadProd(null);
        desperdiciosHelper.setMsgErrorArchivo("");
    }

    public boolean habilitarBtnValidarProd() {
        desperdiciosHelper.setDeshabilitaBtnValidarProd(false);
        produccion.setIdMarca(opcionMarca);
        produccion.setIdPlantaProd(opcionPlanta);
        produccion.setIdPaisOrigen(opcionOrigen);
        produccion.setIdTipoRetro(opcionTipo);

        if (this.produccion.getIdMarca() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getIdPlantaProd() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getIdPaisOrigen() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        } else {
            for (PaisOrigen pais : origenList) {
                if (opcionOrigen.equals(pais.getIdPais())) {
                    desperdiciosHelper.setNombrePais(pais.getDescCorta());
                    produccion.setDescPaisOrigen(desperdiciosHelper.getNombrePais());
                    break;
                }
            }
        }
        if (this.produccion.getDescMaquinaProd() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getNumLote() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getCantidadProd() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getIdTipoRetro() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        if (this.produccion.getCantidadCigarros() == null) {
            desperdiciosHelper.setDeshabilitaBtnValidarProd(true);
        }
        return desperdiciosHelper.isDeshabilitaBtnValidarProd();
    }

    public boolean habilitarBtnGuardar() {
        desperdiciosHelper.setDeshabilitaBtnGuardar(false);
        if (desperdiciosHelper.getRangoFoliosList().isEmpty()) {
            desperdiciosHelper.setDeshabilitaBtnGuardar(false);
        } else {
            desperdiciosHelper.setDeshabilitaBtnGuardar(true);
        }
        return desperdiciosHelper.isDeshabilitaBtnGuardar();
    }

    /**
     * Metodo encargado de invocar la validacion del Archivo a cargar
     *
     */
    public void validaArchivoFolios(FileUploadEvent event) {
        try {
            if (event.getFile() != null) {
                desperdiciosHelper.setArchivoFolios(event.getFile());
                if (desperdiciosHelper.getArchivoFolios().getFileName() != null) {
                    desperdiciosHelper.setDeshabilitaBtnGuardar(false);
                    leerArchivo();
                } else {
                    desperdiciosHelper.setDeshabilitaBtnGuardar(true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: No subio correctamente el archivo" + e.getMessage());
        }
    }

    public void habilitarPnlAcuse() {
        desperdiciosHelper.setPnlPrincipal(false);
        desperdiciosHelper.setPnlAcuse(true);
    }

    public void habilitarPnlPrincipal() {
        desperdiciosHelper.setPnlPrincipal(true);
        desperdiciosHelper.setPnlAcuse(false);
    }

    public ProduccionCigarros getProduccion() {
        return produccion;
    }

    public void setProduccion(ProduccionCigarros produccion) {
        this.produccion = produccion;
    }

    public List<Marcas> getMarcasList() {
        return marcasList;
    }

    public void setMarcasList(List<Marcas> marcasList) {
        this.marcasList = marcasList;
    }

    public List<PaisOrigen> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<PaisOrigen> paisList) {
        this.paisList = paisList;
    }

    public List<Planta> getPlantasList() {
        return plantasList;
    }

    public void setPlantasList(List<Planta> plantasList) {
        this.plantasList = plantasList;
    }

    public List<PaisOrigen> getOrigenList() {
        return origenList;
    }

    public void setOrigenList(List<PaisOrigen> origenList) {
        this.origenList = origenList;
    }

    public List<TipoRetro> getTipoRetroList() {
        return tipoRetroList;
    }

    public void setTipoRetroList(List<TipoRetro> tipoRetroList) {
        this.tipoRetroList = tipoRetroList;
    }

    public ProduccionCigarrosHelper getProduccionCigarrosHelper() {
        return produccionCigarrosHelper;
    }

    public void setProduccionCigarrosHelper(ProduccionCigarrosHelper produccionCigarrosHelper) {
        this.produccionCigarrosHelper = produccionCigarrosHelper;
    }

    public Long getOpcionMarca() {
        return opcionMarca;
    }

    public void setOpcionMarca(Long opcionMarca) {
        this.opcionMarca = opcionMarca;
    }

    public Long getOpcionPlanta() {
        return opcionPlanta;
    }

    public void setOpcionPlanta(Long opcionPlanta) {
        this.opcionPlanta = opcionPlanta;
    }

    public Long getOpcionPais() {
        return opcionPais;
    }

    public void setOpcionPais(Long opcionPais) {
        this.opcionPais = opcionPais;
    }

    public Long getOpcionOrigen() {
        return opcionOrigen;
    }

    public void setOpcionOrigen(Long opcionOrigen) {
        this.opcionOrigen = opcionOrigen;
    }

    public Long getOpcionTipo() {
        return opcionTipo;
    }

    public void setOpcionTipo(Long opcionTipo) {
        this.opcionTipo = opcionTipo;
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

    public DesperdiciosHelper getDesperdiciosHelper() {
        return desperdiciosHelper;
    }

    public void setDesperdiciosHelper(DesperdiciosHelper desperdiciosHelper) {
        this.desperdiciosHelper = desperdiciosHelper;
    }
}
