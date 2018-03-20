/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.codigos;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import mx.gob.sat.mat.tabacos.constants.enums.RetroFalsoNoValido;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoCodigo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.ArchivoRangosFolioService;
import mx.gob.sat.mat.tabacos.negocio.CodigosFalsoseInvalidosService;
import mx.gob.sat.mat.tabacos.negocio.MarcaService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.RetroCodigosService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorRangosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.MarcaServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangoFolioServiceExcepcion;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RetroCodigosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
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
public class IngresaCodigosMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(IngresaCodigosMBean.class);

    private static final int MAXIMO_FOLIOS_CANCELADOS = 1049000;
    private String errorGeneral;

    private static final String ERROR_ARCHIVO_INVALIDO = "El tipo de archivo no es válido";
    private static final String ERROR_ARCHIVO_GRANDE = "El tamaño del archivo es demasiado grande";
    private static final String ERROR_CAMPO_REQUERIDO = "Falta llenar el campo";

    private IngresaCodigosHelper ingresaCodigosHelper;
    private boolean visiblePanelCodigos;
    private boolean visiblePanelAcuse;
    private boolean visiblePanelExito;

    private static final String MSG_EXITO = "La información se guardó correctamente";
    private static final String MSG_INCIDENCIA = "Códigos falsos o no válidos";
    private Map parameters;
    private String exitoarchivo;
    private Date maxFecha;

    private String fechaAcuse;
    private String folioAcuse;

    private List<RangoFolio> rangosFoliosLeidos;

    @Autowired
    private ArchivoRangosFolioService archivoRangosFolioService;

    @Autowired
    private CodigosFalsoseInvalidosService codigosFalsoseInvalidosService;

    @Autowired
    private ReporterService reporterService;

    @Autowired
    private RetroCodigosService codigosService;

    @Autowired
    private ValidadorRangosService rangosService;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    @Autowired
    @Qualifier("marcaService")
    private MarcaService marcaService;

    @PostConstruct
    public void init() {
        visiblePanelCodigos = true;
        visiblePanelAcuse = false;
        visiblePanelExito = false;
        exitoarchivo = "";

        Calendar cal = Calendar.getInstance();
        maxFecha = cal.getTime();

        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO.getDescripcion());
        ingresaCodigosHelper = new IngresaCodigosHelper();

        inicializar();
        parameters = new HashMap();

    }

    private List<TipoCodigo> recuperaTiposCodigo() {
        List<TipoCodigo> tipos = new ArrayList<TipoCodigo>();
        TipoCodigo tc;
        for (RetroFalsoNoValido item : RetroFalsoNoValido.values()) {
            tc = new TipoCodigo();
            tc.setId(item.getKey());
            tc.setTipo(item.getDescripcion());
            tipos.add(tc);
        }
        return tipos;
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null) {
            try {
                this.errorGeneral = "";
                ingresaCodigosHelper.setArchivoFolios(event.getFile());
                rangosFoliosLeidos = extraccionRangoFolioArchivo();
                exitoarchivo = "El archivo se cargó correctamente.";
                ingresaCodigosHelper.setErrorSeleccionFile("");
            } catch (RangoFolioServiceExcepcion e) {
                LOGGER.error(e.getMessage().trim());
                this.errorGeneral = e.getMessage();
                ingresaCodigosHelper.setArchivoFolios(null);
            }
        }
    }

    public void verificarFormulario() {
        boolean valido = true;
        if (ingresaCodigosHelper != null) {

            boolean flgCodigo = ingresaCodigosHelper.getCodigo() != null && ingresaCodigosHelper.getCodigo().getJustificacion() == null
                    || ingresaCodigosHelper.getCodigo().getJustificacion().trim().length() == 0;

            if (flgCodigo) {
                ingresaCodigosHelper.setErrorJustificacion(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.justificacion"));
                valido = false;
            } else {
                ingresaCodigosHelper.setErrorJustificacion("");
            }
            if (ingresaCodigosHelper.getCodigo().getFecCaptura() == null) {
                ingresaCodigosHelper.setErrorFecha(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.fechayhora"));
                valido = false;
            } else {
                ingresaCodigosHelper.setErrorFecha("");
            }
            if (ingresaCodigosHelper.getOpcionTipoCodigo() == null || (ingresaCodigosHelper.getOpcionTipoCodigo() != null && ingresaCodigosHelper.getOpcionTipoCodigo() <= 0)) {
                valido = false;
                ingresaCodigosHelper.setErrorSeleccionTipoCod(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.tipocodigo"));
            } else {
                ingresaCodigosHelper.setErrorSeleccionTipoCod("");
                if (ingresaCodigosHelper.getOpcionTipoCodigo().equals(RetroFalsoNoValido.FALSO.getKey())) {
                    if (ingresaCodigosHelper.getCodigo().getNumeroCodigo() == null
                            || (ingresaCodigosHelper.getCodigo().getNumeroCodigo() != null && ingresaCodigosHelper.getCodigo().getNumeroCodigo().isEmpty())) {
                        ingresaCodigosHelper.setErrorNumeroCodigo(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.numerocodigo"));
                        valido = false;
                    } else {
                        ingresaCodigosHelper.setErrorNumeroCodigo("");
                    }
                } else if (ingresaCodigosHelper.getOpcionTipoCodigo().equals(RetroFalsoNoValido.NO_VALIDO.getKey())) {
                    if (ingresaCodigosHelper.getOpcionRfcTabacalera() == null) {
                        ingresaCodigosHelper.setErrorSeleccionRfc(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.rfc"));
                        valido = false;
                    } else {
                        ingresaCodigosHelper.setErrorSeleccionRfc("");
                    }

                    if (ingresaCodigosHelper.getArchivoFolios() == null) {
                        ingresaCodigosHelper.setErrorSeleccionFile(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.cargaarchivo"));
                        valido = false;
                    } else {
                        ingresaCodigosHelper.setErrorSeleccionFile("");
                    }
                }
                if (ingresaCodigosHelper.getOpcionMarca() == null || (ingresaCodigosHelper.getOpcionMarca() != null && ingresaCodigosHelper.getOpcionMarca() <= 0)) {
                    valido = false;
                    ingresaCodigosHelper.setErrorMarca(ERROR_CAMPO_REQUERIDO + " " + super.getMessageResourceString("page.ingresar.codigos.marca"));
                } else {
                    ingresaCodigosHelper.setErrorMarca("");
                }
            }
        }

        if (valido) {
            if (ingresaCodigosHelper.getOpcionTipoCodigo().equals(RetroFalsoNoValido.FALSO.getKey())) {
                this.guardarFalsos();
            } else {
                this.guardarNoValidos();
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formCodigosF");
    }

    private void guardarFalsos() {
        try {
            Codigo codigo = ingresaCodigosHelper.getCodigo();
            Long opcionMarca = ingresaCodigosHelper.getOpcionMarca();

            codigo.setIdMarca(opcionMarca);
            codigo.setFecRegistro(codigo.getFecCaptura());
            int result = codigosFalsoseInvalidosService.guardarCodigoFalso(codigo);
            if (result > 0) {
                super.msgInfo(MSG_EXITO);
                this.visiblePanelExito = true;
                this.visiblePanelCodigos = true;
                this.visiblePanelAcuse = false;
                inicializar();
                registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO, new Date(), new Date(), MovimientosBitacoraEnum.CODIGOS_FALSOS_NO_VALIDOS_PE);
            } else {
                this.visiblePanelExito = false;
            }
        } catch (CodigosFalsoseInvalidosException ser) {
            this.errorGeneral = ser.getMessage();
            LOGGER.error("FALLO ALGO en asignarRangosFolios" + ser.getMessage());
        }
    }

    private void guardarNoValidos() {
        Map<CodigoInvalido, List<RangoFolio>> mapInvalido = new HashMap<CodigoInvalido, List<RangoFolio>>();
        try {
            List<RangoFolio> lstRangos = rangosService.generarRangosInvalidos(ingresaCodigosHelper.getRfcTabacalera(), rangosFoliosLeidos);

            CodigoInvalido codigoInvalido = new CodigoInvalido();
            codigoInvalido.setJustificacion(ingresaCodigosHelper.getCodigo().getJustificacion());

            mapInvalido.put(codigoInvalido, lstRangos);
            folioAcuse = codigosService.guardarInvalido(mapInvalido);

            parameters.put("msgexito", MSG_EXITO);
            parameters.put("rfc", ingresaCodigosHelper.getRfcTabacalera());
            parameters.put("incidencia", MSG_INCIDENCIA);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dtHoy = new Date();
            fechaAcuse = sdf.format(dtHoy);
            parameters.put("fecha", dtHoy);
            parameters.put("folio", folioAcuse);

            visiblePanelCodigos = false;
            visiblePanelAcuse = true;

            registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO, new Date(), new Date(), MovimientosBitacoraEnum.CODIGOS_FALSOS_NO_VALIDOS_PE);
        } catch (RangosException e) {
            this.errorGeneral = e.getMessage();
            LOGGER.error("ERROR: asignarRangosFolios", e);
        } catch (RetroCodigosException e) {
            this.errorGeneral = e.getMessage();
            LOGGER.error("ERROR: guardarNoValidos", e);
        }
    }

    private void inicializar() {
        try {
            ingresaCodigosHelper.setTabacaleras(tababacaleraService.getTabacaleras());
            ingresaCodigosHelper.setCodigo(new Codigo());
            ingresaCodigosHelper.setTiposCodigo(recuperaTiposCodigo());
            ingresaCodigosHelper.setOpcionTipoCodigo(0);
            ingresaCodigosHelper.setOpcionRfcTabacalera(null);
            ingresaCodigosHelper.setOpcionMarca(null);
            ingresaCodigosHelper.setMarcasList(null);
            ingresaCodigosHelper.setArchivoFolios(null);
        } catch (TabacaleraServiceException e) {
            LOGGER.error(e);
        }
    }

    public void cargarMarcas() {
        this.limpiarAtt();

        if (ingresaCodigosHelper.getOpcionTipoCodigo() == null || (ingresaCodigosHelper.getOpcionTipoCodigo() != null && ingresaCodigosHelper.getOpcionTipoCodigo() <= 0)) {
            ingresaCodigosHelper.setOpcionRfcTabacalera(null);
        } else {
            List<Marcas> marcas = null;
            if (ingresaCodigosHelper.getOpcionTipoCodigo().equals(RetroFalsoNoValido.FALSO.getKey())) {
                ingresaCodigosHelper.setOpcionRfcTabacalera(null);
                try {
                    marcas = marcaService.getMarcas();
                } catch (MarcaServiceException e) {
                    LOGGER.error(e);
                }
                ingresaCodigosHelper.setMarcasList(marcas);
            } else {
                Long idTabacalera = ingresaCodigosHelper.getOpcionRfcTabacalera();
                String rfcTabacalera = "";

                if (idTabacalera != null) {

                    if (ingresaCodigosHelper.getTabacaleras() != null && !ingresaCodigosHelper.getTabacaleras().isEmpty()) {
                        for (int i = 0; i < ingresaCodigosHelper.getTabacaleras().size(); i++) {
                            if (ingresaCodigosHelper.getTabacaleras().get(i) != null && ingresaCodigosHelper.getTabacaleras().get(i).getIdTabacalera().equals(idTabacalera)) {
                                rfcTabacalera = ingresaCodigosHelper.getTabacaleras().get(i).getRfc();
                                ingresaCodigosHelper.setRfcTabacalera(ingresaCodigosHelper.getTabacaleras().get(i).getRfc());
                                break;
                            }
                        }
                    }

                    try {
                        marcas = marcaService.getMarcasByRFCTabacalera(rfcTabacalera);
                    } catch (MarcaServiceException e) {
                        LOGGER.error(e);
                    }

                    ingresaCodigosHelper.setMarcasList(marcas);
                }
            }
        }

    }

    private void limpiarAtt() {
        this.errorGeneral = "";
        this.exitoarchivo = "";
        ingresaCodigosHelper.setErrorJustificacion("");
        ingresaCodigosHelper.setErrorFecha("");
        ingresaCodigosHelper.setErrorSeleccionTipoCod("");
        ingresaCodigosHelper.setErrorNumeroCodigo("");
        ingresaCodigosHelper.setErrorSeleccionRfc("");
        ingresaCodigosHelper.setErrorSeleccionFile("");
        ingresaCodigosHelper.setErrorMarca("");

        ingresaCodigosHelper.setArchivoFolios(null);
        ingresaCodigosHelper.setOpcionMarca(null);
        ingresaCodigosHelper.setMarcasList(null);
    }

    public void btnGenerarReportePDF() {
        try {
            byte[] bytesFile;

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSECOD, ARCHIVO_PDF,
                        this.parameters, null);
                generaDocumento(bytesFile, MIMETypesEnum.PDF, "acuseCodigos", FileExtensionEnum.PDF);
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, e.getMessage());
            LOGGER.error(">>>>> ERROR AL GENERAR ARCHIVO!!! " + e.getMessage());
        }

    }

    private List<RangoFolio> extraccionRangoFolioArchivo() throws RangoFolioServiceExcepcion {
        List<RangoFolio> rangosFol = null;
        boolean banderaErrores = false;
        UploadedFile archivoFolios = ingresaCodigosHelper.getArchivoFolios();

        if (archivoRangosFolioService != null && archivoFolios != null) {
            String nombreArchivo = archivoFolios.getFileName();

            if (!(nombreArchivo.endsWith(Constantes.EXCEL_ANTES_2007)
                    || nombreArchivo.endsWith(Constantes.EXCEL_DESPUES_2007))) {
                banderaErrores = true;
                this.errorGeneral = ERROR_ARCHIVO_INVALIDO;
                LOGGER.error(ERROR_ARCHIVO_INVALIDO);
                throw new RangoFolioServiceExcepcion(ERROR_ARCHIVO_INVALIDO);
            } else if (archivoFolios.getSize() > MAXIMO_FOLIOS_CANCELADOS) {
                banderaErrores = true;
                this.errorGeneral = ERROR_ARCHIVO_GRANDE;
                LOGGER.error(ERROR_ARCHIVO_GRANDE);
                throw new RangoFolioServiceExcepcion(ERROR_ARCHIVO_GRANDE);
            } else {
                try {
                    InputStream inputStream = archivoFolios.getInputstream();
                    rangosFol = archivoRangosFolioService.leerArchivoFolios(inputStream, nombreArchivo);

                } catch (IOException e) {
                    errorGeneral = e.getMessage();
                    banderaErrores = true;
                    LOGGER.error(e.getMessage());
                    throw new RangoFolioServiceExcepcion(e);
                } catch (RangosException e) {
                    banderaErrores = true;
                    errorGeneral = e.getMessage().trim();
                    LOGGER.error("FALLO extraccionRangoFolioArchivo: " + errorGeneral);
                    throw new RangoFolioServiceExcepcion(e);
                }
            }
        }

        if (banderaErrores) {
            return null;
        } else {
            Collections.sort(rangosFol);
            return rangosFol;
        }
    }

    public String getErrorGeneral() {
        return errorGeneral;
    }

    public void setErrorGeneral(String errorGeneral) {
        this.errorGeneral = errorGeneral;
    }

    public String getMsgExito() {
        return MSG_EXITO;
    }

    public IngresaCodigosHelper getIngresaCodigosHelper() {
        return ingresaCodigosHelper;
    }

    public void setIngresaCodigosHelper(IngresaCodigosHelper ingresaCodigosHelper) {
        this.ingresaCodigosHelper = ingresaCodigosHelper;
    }

    public boolean isVisiblePanelExito() {
        return visiblePanelExito;
    }

    public void setVisiblePanelExito(boolean visiblePanelExito) {
        this.visiblePanelExito = visiblePanelExito;
    }

    public boolean isVisiblePanelCodigos() {
        return visiblePanelCodigos;
    }

    public void setVisiblePanelCodigos(boolean visiblePanelCodigos) {
        this.visiblePanelCodigos = visiblePanelCodigos;
    }

    public boolean isVisiblePanelAcuse() {
        return visiblePanelAcuse;
    }

    public void setVisiblePanelAcuse(boolean visiblePanelAcuse) {
        this.visiblePanelAcuse = visiblePanelAcuse;
    }

    public String getExitoarchivo() {
        return exitoarchivo;
    }

    public void setExitoarchivo(String exitoarchivo) {
        this.exitoarchivo = exitoarchivo;
    }

    public Date getMaxFecha() {
        return (maxFecha != null) ? (Date) maxFecha.clone() : null;
    }

    public void setMaxFecha(Date maxFecha) {
        this.maxFecha = (maxFecha != null) ? (Date) maxFecha.clone() : null;
    }

    public String getFechaAcuse() {
        return fechaAcuse;
    }

    public void setFechaAcuse(String fechaAcuse) {
        this.fechaAcuse = fechaAcuse;
    }

    public String getFolioAcuse() {
        return folioAcuse;
    }

    public void setFolioAcuse(String folioAcuse) {
        this.folioAcuse = folioAcuse;
    }
}
