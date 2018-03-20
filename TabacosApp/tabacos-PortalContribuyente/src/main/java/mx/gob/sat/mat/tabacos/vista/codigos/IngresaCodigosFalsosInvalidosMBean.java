/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.codigos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoCodigo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.ArchivoRangosFolioService;
import mx.gob.sat.mat.tabacos.negocio.CodigosFalsoseInvalidosService;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.RangoFolioService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.SolicitudService;
import mx.gob.sat.mat.tabacos.negocio.TratamientoRangoFolioService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.QRCodeUtilException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SelloDigitalException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.QRCodeUtil;
import mx.gob.sat.mat.tabacos.negocio.util.SelloDigitalUtil;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.FirmaFormHelper;
import mx.gob.sat.mat.tabacos.vista.helper.IngresaCodigosHelper;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MMMF
 */
@Controller("ingresaCodMB")
@Scope(value = "view")
public class IngresaCodigosFalsosInvalidosMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(IngresaCodigosFalsosInvalidosMBean.class);

    private static final int LONGITUD_CODIGO = 12;

    private boolean flag = false;

    private static final String ERROR_CAMPO_REQUERIDO = "El campo es requerido.";
    private static final String CARGA_ARCHIVO_EXITO = "El archivo se cargo correctamente.";

    private ValidarAccesoRespuesta accesoRespuesta;
    private IngresaCodigosHelper ingresaCodigosHelper;
    private FirmaFormHelper firmaFormHelper;

    private static final String MSGEXITO = "La información se guardó correctamente";
    private static final String MSG_INCIDENCIA = "Códigos falsos o no válidos";

    private static final String MSG_ERRORGENERAL_FORM = "formCodigosF:msgErrorGeneral";
    
    private static final String MSG_ERROR_REDIRECCIONAR_KEY = "msg.error.redireccionar";

    private static final String FOLIO_ACUSE = "folioAcuse";
    private static final String ACUSE = "ObjectAcuse";
    private Acuse ultimoAcuse;
    private String folioUltimoAcuse;
    private Map parameters;
    private boolean deshabilitaBoton;
    private String exitoarchivo;
    private Date maxFecha;

    private List<RangoFolio> rangosFoliosProcesados;
    private List<Codigo> codigosInvalidos;

    private List<RangoFolio> rangosFoliosLeidos;

    private String fechaUltimoAcuse;
    private boolean deshabilitaBotonArchivo;
    private boolean muestraCargaArchivo;

    @Autowired
    @Qualifier("tratamientoRangoFolioService")
    private TratamientoRangoFolioService tratamientoRangoFolioService;

    @Autowired
    @Qualifier("archivoFoliosServiceImpl")
    private ArchivoRangosFolioService archivoRangosFolioService;

    @Autowired
    @Qualifier("codigosFalsosInvalidosService")
    private CodigosFalsoseInvalidosService codigosFalsoseInvalidosService;

    @Autowired
    @Qualifier("solicitudService")
    private SolicitudService solicitudService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("selloDigitalUtil")
    private SelloDigitalUtil selloDigital;

    @Autowired
    @Qualifier("marcaService")
    private MarcaService marcaService;

    @Autowired
    @Qualifier("rangoFolioService")
    private RangoFolioService rangoFolioService;

    @PostConstruct
    public void init() {

        deshabilitaBoton = true;
        exitoarchivo = "";

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK + 1, cal.get(Calendar.DAY_OF_WEEK));
        maxFecha = new Date(cal.getTimeInMillis());

        validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());

        accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);

        ingresaCodigosHelper = new IngresaCodigosHelper();
        ingresaCodigosHelper.setVisiblePanelCodigos(true);
        ingresaCodigosHelper.setVisiblePanelFirma(false);
        ingresaCodigosHelper.setVisiblePanelAcuse(false);
        ingresaCodigosHelper.setVisiblePanelExito(false);

        firmaFormHelper = new FirmaFormHelper();
        firmaFormHelper.setRfcSession(getRFCSession());
        LOGGER.info("RFC de Session: " + firmaFormHelper.getRfcSession());

        //asigancion al helper
        inicializar();
        parameters = new HashMap();
        codigosInvalidos = new ArrayList<Codigo>();
        //deshabilitar la carga de archivo
        validaCargaDeArchivo();

    }

    private List<TipoCodigo> recuperaTiposCodigo() {
        List<TipoCodigo> tipos = new ArrayList<TipoCodigo>();
        TipoCodigo cod = new TipoCodigo();
        cod.setId(1);
        cod.setTipo("FALSO");
        tipos.add(cod);

        cod = new TipoCodigo();
        cod.setId(2);
        cod.setTipo("INVALIDO");
        tipos.add(cod);

        return tipos;
    }

    /**
     * Utilizado para el procesamiento y guardado de codigos falsos
     */
    public void asignarRangosFolios() {
        int opcionTipoCodigo = ingresaCodigosHelper.getOpcionTipoCodigo();
        if (opcionTipoCodigo == 1) {
            LOGGER.info("Solo puede procesar codigos falsos!!!!");
            realizaProcesamientoFoliosRango();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formCodigosF");
        }
    }

    /**
     * Se realiza el procesamiento de rangos folios al momento de cargar el
     * archivo. En caso de que no cumpla las validaciones no se habilitara el
     * boton Guardar de la vista
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        boolean procesamientoInval = false;

        if (event.getFile() != null) {
            try {
                ingresaCodigosHelper.setErrorGeneral("");
                LOGGER.info("Cargando archivo...");
                ingresaCodigosHelper.setArchivoFolios(event.getFile());

                //leer el archivo de rango folios
                rangosFoliosLeidos = extraccionRangoFolioArchivo();
                //generar la cadena original
                String cadena = solicitudService.generaCadenaOriginal(firmaFormHelper.getRfcSession(), sumaRangosFolios(rangosFoliosLeidos), null);
                LOGGER.info("Cadena GENERADA : " + cadena);
                firmaFormHelper.setCadenaOriginal(cadena);
                deshabilitaBoton = false;
                exitoarchivo = CARGA_ARCHIVO_EXITO;

                procesamientoInval = realizaProcesamientoFoliosRango();

            } catch (SolicitudServiceException e) {
                //No se leyo la informacion del excel
                LOGGER.error(e.getMessage().trim());
                ingresaCodigosHelper.setErrorGeneral(e.getMessage());
                ingresaCodigosHelper.setArchivoFolios(null);
            } catch (CodigosFalsoseInvalidosException ex) {
                LOGGER.error(ex);
            }
        }
        verificarFormulario();
        //no paso el procesamiento correctamente, no activar el boton Guardar
        if (!procesamientoInval) {
            this.deshabilitaBoton = !procesamientoInval;
        }

    }

    public boolean verificarFormulario() {
        boolean valido = true;
        if (ingresaCodigosHelper != null) {
            if (!validaCamposObligatorios(ingresaCodigosHelper)) {
                valido = false;
            }
            if (ingresaCodigosHelper.getOpcionTipoCodigo() == 1
                    && !esCampoValido(ingresaCodigosHelper.getCodigo().getNumeroCodigo())) {
                valido = false;
            } else if (ingresaCodigosHelper.getOpcionTipoCodigo() == 2
                    && ingresaCodigosHelper.getArchivoFolios() == null) {
                valido = false;
            }
        }

        deshabilitaBoton = !valido;
        validaCargaDeArchivo();
        LOGGER.info("Saliendo de a verificacion: " + deshabilitaBoton);
        return deshabilitaBoton;

    }

    private boolean validaCamposObligatorios(IngresaCodigosHelper ingresaCodigosHelper) {
        boolean valido = true;

        if (ingresaCodigosHelper.getCodigo() != null && !esCampoValido(ingresaCodigosHelper.getCodigo().getJustificacion())) {
            valido = false;
        }
        if (!esFechaDeCodigoValida(ingresaCodigosHelper.getCodigo())) {
            valido = false;
        }
        if (ingresaCodigosHelper.getOpcionTipoCodigo() < 1) {
            ingresaCodigosHelper.setErrorSeleccionTipoCod(ERROR_CAMPO_REQUERIDO);
            valido = false;
        }
        if (ingresaCodigosHelper.getOpcionRfcTabacalera() < 1) {
            valido = false;
        }

        return valido;
    }

    private void validaCargaDeArchivo() {
        boolean deshabilitaArchivo = false;
        if (ingresaCodigosHelper != null && ingresaCodigosHelper.getOpcionTipoCodigo() == 2) {
            LOGGER.info("realizando validacionCargaDeArchivo");
            if (!esCampoValido(ingresaCodigosHelper.getCodigo().getJustificacion())) {
                deshabilitaArchivo = true;
            }
            if (!esFechaDeCodigoValida(ingresaCodigosHelper.getCodigo())) {
                deshabilitaArchivo = true;
            }
            if (ingresaCodigosHelper.getOpcionRfcTabacalera() < 1) {
                deshabilitaArchivo = true;
            }
            if (!deshabilitaArchivo) {
                muestraCargaArchivo = true;
            } else {
                muestraCargaArchivo = false;
            }
        } else {
            deshabilitaArchivo = true;
            muestraCargaArchivo = false;
        }
        deshabilitaBotonArchivo = deshabilitaArchivo;

    }

    private boolean realizaProcesamientoFoliosRango() {
        boolean resultProcesamiento = false;
        asignaRfcTabacalera();

        Codigo codigo = ingresaCodigosHelper.getCodigo();
        int opcionTipoCodigo = ingresaCodigosHelper.getOpcionTipoCodigo();
        Long opcionMarca = ingresaCodigosHelper.getOpcionMarca();
        List<RangoFolio> rangosFoliosArchivo = null;

        if (validaFormulario()) {
            LOGGER.info("Tipo de Codigo: " + opcionTipoCodigo + " Marca: " + opcionMarca);
            try {
                switch (opcionTipoCodigo) {
                    //FALSO
                    case 1:
                        //se guarda solo un codigo
                        if (codigo != null) {
                            codigo.setIdMarca(opcionMarca);
                            codigo.setFecRegistro(codigo.getFecCaptura());

                            LOGGER.info("Guardando el siguiente codigo falso: "
                                    + codigo.getNumeroCodigo() + " >Justificacion> "
                                    + codigo.getJustificacion() + " >Fecha> " + codigo.getFecRegistro());
                        }
                        int result = this.codigosFalsoseInvalidosService.guardarCodigoFalso(codigo);
                        if (result > 0) {
                            super.msgInfo(MSGEXITO);
                            mostrarExitoFalso();
                            inicializar();
                            resultProcesamiento = true;
                        } else {
                            ingresaCodigosHelper.setVisiblePanelExito(false);
                            resultProcesamiento = false;
                        }

                        break;
                    //INVALIDO
                    case 2:
                        //validar que se haya adjuntado el arhcivo
                        if (ingresaCodigosHelper.getArchivoFolios() == null) {
                            ingresaCodigosHelper.setErrorGeneral("Se requiere adjuntar el archivo");
                        } else {
                            LOGGER.info("Inicia el procesamiento de rangos folios");
                            rangosFoliosArchivo = rangosFoliosLeidos;
                            validaExistenciaCodigosInvalidosBD(rangosFoliosArchivo, ingresaCodigosHelper.getRfcTabacalera());
                            boolean esValido = this.realizaValidaciones(rangosFoliosArchivo, ingresaCodigosHelper.getRfcTabacalera());
                            resultProcesamiento = esValido;
                            if (esValido) {
                                //solo se hace la asignacion de folio procesados, se guarda hasta que se firme
                                this.rangosFoliosProcesados = rangosFoliosArchivo;

                            } else {
                                //mostrar mensaje de que algo fallo
                                LOGGER.error("No cumplio con las validaciones");
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (BusinessException ser) {
                super.msgError(MSG_ERRORGENERAL_FORM, ser.getMessage());
                ingresaCodigosHelper.setErrorGeneral(ser.getMessage());
                resultProcesamiento = false;
                LOGGER.error("FALLO ALGO en asignarRangosFolios" + ser.getMessage());
            } catch (CodigosFalsoseInvalidosException ex) {
                super.msgError(MSG_ERRORGENERAL_FORM, ex.getMessage());
                ingresaCodigosHelper.setErrorGeneral(ex.getMessage());
                resultProcesamiento = false;
                LOGGER.error("FALLO ALGO en asignarRangosFolios" + ex.getMessage());
            }
        }
        return resultProcesamiento;

    }

    private void procesamientoCodigosInvalidos(List<RangoFolio> rangosFoliosArchivo) throws CodigosFalsoseInvalidosException {
        //funciona correctamente
        guardaCodigosInvalidos(rangosFoliosArchivo.size());
        guardarAcusesPorSolicitud(this.rangosFoliosProcesados);
        mostrarAcuse();

    }

    private void mostrarExitoFalso() {
        ingresaCodigosHelper.setVisiblePanelExito(true);
        ingresaCodigosHelper.setVisiblePanelCodigos(true);
        ingresaCodigosHelper.setVisiblePanelAcuse(false);
        ingresaCodigosHelper.setVisiblePanelFirma(false);
    }

    public void mostrarFirma() {
        ingresaCodigosHelper.setVisiblePanelExito(false);
        ingresaCodigosHelper.setVisiblePanelCodigos(false);
        ingresaCodigosHelper.setVisiblePanelAcuse(false);
        ingresaCodigosHelper.setVisiblePanelFirma(true);
    }

    private void mostrarAcuse() {
        ingresaCodigosHelper.setVisiblePanelExito(false);
        ingresaCodigosHelper.setVisiblePanelCodigos(false);
        ingresaCodigosHelper.setVisiblePanelFirma(false);
        ingresaCodigosHelper.setVisiblePanelAcuse(true);
    }

    private void mostrarIngresa() {
        ingresaCodigosHelper.setVisiblePanelExito(false);
        ingresaCodigosHelper.setVisiblePanelCodigos(true);
        ingresaCodigosHelper.setVisiblePanelFirma(false);
        ingresaCodigosHelper.setVisiblePanelAcuse(false);
    }

    private void inicializar() {
        //asigancion al helper
        ingresaCodigosHelper.setTabacaleras(accesoRespuesta.getTabacaleras());
        ingresaCodigosHelper.setCodigo(new Codigo());
        ingresaCodigosHelper.setTiposCodigo(recuperaTiposCodigo());
        if (ingresaCodigosHelper.getTabacaleras() != null && !ingresaCodigosHelper.getTabacaleras().isEmpty()) {
            ingresaCodigosHelper.setOpcionRfcTabacalera(ingresaCodigosHelper.getTabacaleras().get(0).getIdTabacalera());
            ingresaCodigosHelper.setRazonSocial(ingresaCodigosHelper.getTabacaleras().get(0).getRazonSocial());
            try {
                cargarMarcas();
            } catch (MarcaServiceException ser) {
                LOGGER.error(ser);
            }
        } else {
            ingresaCodigosHelper.setOpcionRfcTabacalera(0L);
        }
        ingresaCodigosHelper.setOpcionTipoCodigo(0);

    }

    private void asignaRfcTabacalera() {
        Long idTabacalera = ingresaCodigosHelper.getOpcionRfcTabacalera();

        for (Tabacalera tab : ingresaCodigosHelper.getTabacaleras()) {
            if (tab != null && tab.getIdTabacalera().equals(idTabacalera)) {
                ingresaCodigosHelper.setRfcTabacalera(tab.getRfc());
            }
        }
    }

    private Long sumaRangosFolios(List<RangoFolio> rangos) {
        Long result = 0L;

        if (rangos != null) {
            for (RangoFolio rango : rangos) {
                if (rango != null) {
                    result += (rango.getFolioInicial() + rango.getFolioFinal());
                }
            }
        }
        return result;
    }

    public void cargarMarcas() throws MarcaServiceException {
        Long idTabacalera = ingresaCodigosHelper.getOpcionRfcTabacalera();
        asignaRFC();

        String rfcTabacalera = "";
        for (Tabacalera tab : ingresaCodigosHelper.getTabacaleras()) {
            if (tab != null && tab.getIdTabacalera().equals(idTabacalera)) {
                rfcTabacalera = tab.getRfc();
                break;
            }
        }

        try {
            ingresaCodigosHelper.setMarcasList(marcaService.getMarcasByRFCTabacalera(rfcTabacalera));
        } catch (MarcaServiceException ex) {
            getLogger().error(ex);
            throw ex;
        }
    }

    /**
     * Guarda los codigos invalidos previamente procesados, solo se hace si se
     * firma correctamente.
     */
    public void guardarSolicitud() {
        Map<String, String> paramMap = getParametrosSesion();
        String cadenaFirmada;

        if (paramMap != null) {
            cadenaFirmada = paramMap.get(firmaFormHelper.FIRMA_DIGITAL);
            if (cadenaFirmada.length() > 0) {

                //guardar sello ok
                firmaFormHelper.setSelloDigital(paramMap.get(FirmaFormHelper.FIRMA_DIGITAL));

                LOGGER.info(".-. CADENA: " + paramMap.get(FirmaFormHelper.CADENA_ORIGINA)
                        + "\tSELLO: " + paramMap.get(FirmaFormHelper.FIRMA_DIGITAL)
                        + "\t.-. RFC: " + firmaFormHelper.getRfcSession());
                try {
                    procesamientoCodigosInvalidos(this.rangosFoliosProcesados);
                    registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES, new Date(), new Date(), MovimientosBitacoraEnum.CODIGOS_FALSOS_NO_VALIDOS);
                } catch (CodigosFalsoseInvalidosException ser) {
                    LOGGER.error(ser);
                }
            } else {
                mostrarIngresa();
            }

        }

    }

    private void guardarAcusesPorSolicitud(List<RangoFolio> folios) {
        //obtener solo un rango por solicitud
        List<List<RangoFolio>> foliosDiferenteSolicitud = archivoRangosFolioService.rangosFolioSeparadosPorSolicitud(folios);
        Map<String, Object> datosfolioAcuse = null;
        String cadenaFirmada;

        Map<String, String> paramMap = getParametrosSesion();
        if (paramMap != null) {
            cadenaFirmada = paramMap.get(firmaFormHelper.FIRMA_DIGITAL);
            if (cadenaFirmada.length() > 0) {

                try {
                    String cadOriginal = solicitudService.generaCadenaOriginal(firmaFormHelper.getRfcSession(), sumaRangosFolios(folios), null);
                    String selloDig = firmaFormHelper.getSelloDigital();
                    for (List<RangoFolio> lista : foliosDiferenteSolicitud) {

                        if (lista.isEmpty()) {
                            datosfolioAcuse = guardarAcuse(cadOriginal, selloDig, folios);
                        } else {
                            datosfolioAcuse = guardarAcuse(cadOriginal, selloDig, lista);
                        }

                        ultimoAcuse = (Acuse) datosfolioAcuse.get(ACUSE);

                        if (ultimoAcuse == null) {
                            ultimoAcuse = new Acuse();
                            ultimoAcuse.setFecCaptura(new Date());
                        }

                        fechaUltimoAcuse = ultimoAcuse.getFecCaptura() != null ? FechaUtil.formatFecha(ultimoAcuse.getFecCaptura(), FechaUtil.FORMATO_DEFAULT) : "";
                        folioUltimoAcuse = (String) datosfolioAcuse.get(FOLIO_ACUSE);
                    }

                    parameters.put("rfc", ingresaCodigosHelper.getRfcTabacalera());
                    parameters.put("incidencia", MSG_INCIDENCIA);
                    parameters.put("fecha", ultimoAcuse.getFecCaptura());
                    parameters.put("folio", folioUltimoAcuse);
                    parameters.put("cadenaOriginal", firmaFormHelper.getCadenaOriginal());
                    parameters.put("selloDigital", selloDigital.generaSelloDigital(firmaFormHelper.getCadenaOriginal()));
                    parameters.put("codigo_qr", QRCodeUtil.getQr(folioUltimoAcuse, ingresaCodigosHelper.getRfcTabacalera(), Constantes.CINCUENTA, Constantes.CINCUENTA, Constantes.ARCHIVO_PNG));

                    mostrarAcuse();

                } catch (SolicitudServiceException e) {
                    mostrarIngresa();

                    ingresaCodigosHelper.setErrorGeneral(e.getMessage());
                    LOGGER.error(getMessageResourceString(MSG_ERROR_REDIRECCIONAR_KEY) + e.getMessage(), e);
                } catch (CommonServiceException ex) {
                    LOGGER.error(getMessageResourceString(MSG_ERROR_REDIRECCIONAR_KEY) + ex.getMessage(), ex);
                } catch (QRCodeUtilException ex) {
                    LOGGER.error(getMessageResourceString(MSG_ERROR_REDIRECCIONAR_KEY) + ex.getMessage(), ex);
                } catch (SelloDigitalException ex) {
                    LOGGER.error(getMessageResourceString(MSG_ERROR_REDIRECCIONAR_KEY) + ex.getMessage(), ex);
                }
            } else {
                mostrarIngresa();
            }
        }
    }

    public void btnGenerarReportePDF() {
        try {
            byte[] bytesFile = null;

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSECOD, ARCHIVO_PDF,
                        this.parameters, null);

                //   Agregar metodo que genera Jasper
                generaDocumento(bytesFile, MIMETypesEnum.PDF, "acuseCodigos", FileExtensionEnum.PDF);
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, e.getMessage());
            LOGGER.error(">>>>> ERROR AL GENERAR ARCHIVO!!! " + e.getMessage());
        }

    }

    private void guardaCodigosInvalidos(int noRangosFolios) throws CodigosFalsoseInvalidosException {
        if (noRangosFolios > 0 && (this.codigosInvalidos == null || this.codigosInvalidos.isEmpty())) {
            //Incosistencia en BD. Ocurrio un fallo inesperado del sistema, No se guardo la información!
            LOGGER.error("Verificar si el rango fue previamente cargado en producción. No se puede guardar la información");
            throw new CodigosFalsoseInvalidosException("Verificar si el rango fue previamente cargado en producción. No se puede guardar la información");
        } else {
            for (Codigo cod : this.codigosInvalidos) {
                cod.setJustificacion(this.ingresaCodigosHelper.getCodigo().getJustificacion());
                cod.setFecRegistro(this.ingresaCodigosHelper.getCodigo().getFecCaptura());
                int r = this.codigosFalsoseInvalidosService.guardarCodigoInvalido(cod);
                LOGGER.info("Se guardo ? " + r + " invalido : " + cod);
            }
        }
    }

    private boolean validaFormulario() {
        boolean valido = true;
        if (!esCampoValido(ingresaCodigosHelper.getCodigo().getJustificacion())) {
            valido = false;
        }
        if (!esFechaDeCodigoValida(ingresaCodigosHelper.getCodigo())) {
            valido = false;
        }
        if (ingresaCodigosHelper.getOpcionTipoCodigo() < 1) {
            ingresaCodigosHelper.setErrorSeleccionTipoCod(ERROR_CAMPO_REQUERIDO);
            valido = false;
        } else {
            ingresaCodigosHelper.setErrorSeleccionTipoCod("");
        }

        if (ingresaCodigosHelper.getOpcionRfcTabacalera() < 1) {
            ingresaCodigosHelper.setErrorSeleccionRfc(ERROR_CAMPO_REQUERIDO);
            valido = false;
        } else {
            ingresaCodigosHelper.setErrorSeleccionRfc("");
        }
        if (ingresaCodigosHelper.getOpcionTipoCodigo() == 1
                && !esCampoValido(ingresaCodigosHelper.getCodigo().getNumeroCodigo())) {
            valido = false;
        } else if (ingresaCodigosHelper.getOpcionTipoCodigo() == 2
                && ingresaCodigosHelper.getArchivoFolios() == null) {
            valido = false;
        } else {
            ingresaCodigosHelper.setErrorNumeroCodigo("");
        }

        return valido;
    }

    /**
     *
     */
    private void asignaRFC() {
        if (ingresaCodigosHelper.getOpcionRfcTabacalera() < 1) {
            super.msgError("formCodigosF:rfcTabacalera", ERROR_CAMPO_REQUERIDO);
            deshabilitaBoton = true;
        }
    }

    public void deshabilitaArchivo() {
        if ((ingresaCodigosHelper.getCodigo().getNumeroCodigo() != null
                && ingresaCodigosHelper.getCodigo().getNumeroCodigo().trim().length() == LONGITUD_CODIGO)
                && ingresaCodigosHelper.getOpcionTipoCodigo() == 1) {
            flag = true;
        } else {
            flag = false;
        }
    }

    private List<RangoFolio> extraccionRangoFolioArchivo() throws CodigosFalsoseInvalidosException {
        List<RangoFolio> rangosFol = null;
        boolean banderaErrores = false;
        UploadedFile archivoFolios = ingresaCodigosHelper.getArchivoFolios();

        if (archivoRangosFolioService != null && archivoFolios != null) {
            String nombreArchivo = archivoFolios.getFileName();
            try {
                // Se procesa el archivo
                InputStream inputStream = archivoFolios.getInputstream();
                rangosFol = archivoRangosFolioService.leerArchivoFolios(inputStream, nombreArchivo);
                // Se valida que los rangos de folios cancelados se encuentren dentro de los limites de la serie
            } catch (IOException e) {
                ingresaCodigosHelper.setErrorGeneral(e.getMessage());
                super.msgError(MSG_ERRORGENERAL_FORM, e.getMessage());
                banderaErrores = true;
                LOGGER.error(e.getMessage());
                throw new CodigosFalsoseInvalidosException(e);
            } catch (RangosException e) {
                super.msgError(MSG_ERRORGENERAL_FORM, e.getMessage());
                banderaErrores = true;
                ingresaCodigosHelper.setErrorGeneral(e.getMessage().trim());
                LOGGER.error("FALLO extraccionRangoFolioArchivo: " + e);
                throw new CodigosFalsoseInvalidosException(e);
            }
        }

        if (banderaErrores) {
            //ocurrio un error, lanzar una excepcion
            return null;
        } else {
            Collections.sort(rangosFol);
            return rangosFol;
        }
    }

    private boolean realizaValidaciones(List<RangoFolio> rangoFoliosRecuperados, String rfcTabacalera) {
        boolean intersectanLista = false;
        boolean validoEnBase = true;

        for (RangoFolio rangoFolEnt : rangoFoliosRecuperados) {
            List<RangoFolio> resto = new ArrayList<RangoFolio>(rangoFoliosRecuperados);
            resto.remove(rangoFolEnt);
            for (RangoFolio rangoCancelado : resto) {
                if (RangoFolio.intersectan(rangoFolEnt, rangoCancelado)) {
                    intersectanLista = true;
                    ingresaCodigosHelper.setErrorGeneral(super.getMessageResourceString("error.rango.cancelado.invalido.form")
                            + ": " + rangoFolEnt.getFolioInicial() + " - "
                            + rangoFolEnt.getFolioFinal());
                    LOGGER.error(ingresaCodigosHelper.getErrorGeneral());
                    super.msgError(MSG_ERRORGENERAL_FORM, super.getMessageResourceString("error.rango.cancelado.invalido.form")
                            + ": " + rangoFolEnt.getFolioInicial() + " - "
                            + rangoFolEnt.getFolioFinal());
                    break;
                }
            }
            if (intersectanLista) {
                LOGGER.info("Se intersecto el rango");
                break;
            }
        }
        if (!intersectanLista) {
            try {
                Map<String, Object> result = rangoFolioService.validaRangoSerie(rfcTabacalera, rangoFoliosRecuperados);
                validoEnBase = (Boolean) result.get("valido");
                List<Codigo> codigosInvalidosTemp = (List<Codigo>) result.get("codigosInvalidos");
                agregaInvalidos(codigosInvalidosTemp);

                if (!validoEnBase) {
                    ingresaCodigosHelper.setErrorGeneral(super.getMessageResourceString("error.rango.cancelado.invalido.bd"));
                    LOGGER.error(ingresaCodigosHelper.getErrorGeneral());
                    super.msgError(MSG_ERRORGENERAL_FORM, super.getMessageResourceString("error.rango.cancelado.invalido.bd"));

                }
            } catch (RangoFolioServiceExcepcion e) {
                super.msgError(MSG_ERRORGENERAL_FORM, super.getMessageResourceString("error.consulta.bd"));
                LOGGER.error(e);

            }
        }
        return !intersectanLista && validoEnBase;
    }

    private void agregaInvalidos(List<Codigo> codigos) {
        for (Codigo codigo1 : codigos) {
            if (!existeEnInvalidos(codigo1)) {
                //si no existe en invalidos, entonces se agrega
                codigosInvalidos.add(codigo1);
                LOGGER.info(">> Agregando " + codigo1);
            } else {
                //si existe ya no se agrega
            }
        }
    }

    private boolean existeEnInvalidos(Codigo codigo) {
        for (Codigo cod : codigosInvalidos) {
            if (cod.getFolioInicial().compareToIgnoreCase(codigo.getFolioInicial()) == 0
                    && cod.getFolioFinal().compareToIgnoreCase(codigo.getFolioFinal()) == 0
                    && cod.getIdSolicitud().equals(codigo.getIdSolicitud())) {
                return true;
            }
        }
        return false;
    }

    private boolean validaExistenciaCodigosInvalidosBD(List<RangoFolio> mbttRangoFolEnts, String rfc) throws BusinessException {
        boolean existeenBD = false;

        for (RangoFolio rango : mbttRangoFolEnts) {
            existeenBD = this.tratamientoRangoFolioService.validaExistenciaCodigosInvalidos(
                    rango, rfc);
            if (existeenBD) {
                LOGGER.error("No se puede guardar codigo invalido, ya existe almacenado previamente");
                throw new BusinessException("No se puede guardar codigo invalido, ya existe almacenado previamente");
            }
        }
        return existeenBD;
    }

    /**
     * Retorna el folio del acuse
     */
    private Map<String, Object> guardarAcuse(String cadenaOriginal, String selloDigital,
            List<RangoFolio> rangosfoliosAc) throws CommonServiceException {
        Acuse acuse = null;
        Calendar cal = null;
        String folioAcuse = null;
        Map<String, Object> datosAcuse = new HashMap<String, Object>();

        for (RangoFolio foliosAcu : rangosfoliosAc) {
            acuse = new Acuse();
            cal = Calendar.getInstance();
            acuse.setSerieAcuse(TipoAcuse.INVALIDO);
            acuse.setSelloDigital(selloDigital);
            acuse.setCadenaOriginal(cadenaOriginal);
            acuse.setFecCaptura(cal.getTime());
            acuse.setIdSolicitud(foliosAcu.getIdSolicitud());
            try {
                folioAcuse = this.commonService.crearAcuse(acuse);
            } catch (CommonServiceException e) {
                throw new CommonServiceException(e);
            }
        }

        datosAcuse.put(FOLIO_ACUSE, folioAcuse);
        datosAcuse.put(ACUSE, acuse);

        return datosAcuse;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFolioUltimoAcuse() {
        return folioUltimoAcuse;
    }

    public String getMSGEXITO() {
        return MSGEXITO;
    }

    public Acuse getUltimoAcuse() {
        return ultimoAcuse;
    }

    public IngresaCodigosHelper getIngresaCodigosHelper() {
        return ingresaCodigosHelper;
    }

    public void setIngresaCodigosHelper(IngresaCodigosHelper ingresaCodigosHelper) {
        this.ingresaCodigosHelper = ingresaCodigosHelper;
    }

    public FirmaFormHelper getFirmaFormHelper() {
        return firmaFormHelper;
    }

    public void setFirmaFormHelper(FirmaFormHelper firmaFormHelper) {
        this.firmaFormHelper = firmaFormHelper;
    }

    public boolean isDeshabilitaBoton() {
        return deshabilitaBoton;
    }

    public void setDeshabilitaBoton(boolean deshabilitaBoton) {
        this.deshabilitaBoton = deshabilitaBoton;
    }

    public String getExitoarchivo() {
        return exitoarchivo;
    }

    public Date getMaxFecha() {
        return (maxFecha != null) ? (Date) maxFecha.clone() : null;
    }

    public void setMaxFecha(Date maxFecha) {
        this.maxFecha = (maxFecha != null) ? (Date) maxFecha.clone() : null;
    }

    public String getFechaUltimoAcuse() {
        return fechaUltimoAcuse;
    }

    public void setFechaUltimoAcuse(String fechaUltimoAcuse) {
        this.fechaUltimoAcuse = fechaUltimoAcuse;
    }

    public boolean isDeshabilitaBotonArchivo() {
        return deshabilitaBotonArchivo;
    }

    public void setDeshabilitaBotonArchivo(boolean deshabilitaBotonArchivo) {
        this.deshabilitaBotonArchivo = deshabilitaBotonArchivo;
    }

    public boolean isMuestraCargaArchivo() {
        return muestraCargaArchivo;
    }

    public void setMuestraCargaArchivo(boolean muestraCargaArchivo) {
        this.muestraCargaArchivo = muestraCargaArchivo;
    }

    private boolean esCampoValido(String campo) {
        if (campo != null && !campo.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean esFechaDeCodigoValida(Codigo codigo) {
        if (codigo != null && codigo.getFecCaptura() != null) {
            return true;
        }
        return false;
    }

}
