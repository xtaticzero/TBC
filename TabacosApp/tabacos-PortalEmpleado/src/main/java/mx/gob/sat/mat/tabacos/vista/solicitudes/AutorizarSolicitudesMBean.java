/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.solicitudes;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucion;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.AutorizarSolicitudSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;
import mx.gob.sat.mat.tabacos.negocio.AutorizarSolicitudService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.FileUtil;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import mx.gob.sat.mat.tabacos.vista.helper.AutorizarSolicitudHelper;
import mx.gob.sat.mat.tabacos.vista.helper.SolicitudHelper;
import mx.gob.sat.mat.tabacos.vista.util.Expresiones;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRFC;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Controller("autorizarSolicitudesMBean")
@Scope(value = "view")
public class AutorizarSolicitudesMBean extends AbstractManagedBean {

    private AutorizarSolicitudHelper autorizarSolicitudHelper;

    private static final String ACTION = "action";
    private static final String FOLIO = "folio";
    private static final String FILTRO = "filtro";
    private static final String BUSQUEDA = "busqueda";
    private static final int SIZE_QUEST = 22;
    
    private static final int CASE_R = 1;
    private static final int CASE_F = 2;
    private static final int CASE_S = 3;
    private static final int CASE_D = 4;

    @Autowired
    private transient AutorizarSolicitudService autorizarSolicitudService;

    @Autowired
    @Qualifier("fileUtil")
    private FileUtil fileUtil;
    
    private SolicitudHelper solicitudHelper;

    /**
     *
     */
    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_SOLICITUD.getDescripcion());
        solicitudHelper = new SolicitudHelper();
        
        solicitudHelper.setFiltros(new ArrayList<SelectItem>());

        inicializaFiltros();

        autorizarSolicitudHelper = new AutorizarSolicitudHelper();

        solicitudHelper.setAutorizantes(autorizarSolicitudService.
                obtenerCombos(AutorizarSolicitudSQL.SQL_SELECT_AUTORIZACIONCB,
                        "IDSPAUTORIZA", "DESCSPAUTORIZA"));

        solicitudHelper.setResoluciones(autorizarSolicitudService.
                obtenerCombos(AutorizarSolicitudSQL.SQL_SELECT_RESOLUCIONCB,
                        "IDESTRESOLUCION", "DESCESTRESOLUCION"));

        solicitudHelper.setRegistro(new SolicitudesRegistradas());

        String accion = (String) getSession().getAttribute(ACTION);
        solicitudHelper.setCaptFiltro(true);
        getEstadosResolucion();

        if (accion != null) {
            evaluacionAccion(accion);
        }
    }

    private void inicializaFiltros() {
        SelectItem item = new SelectItem();
        item.setValor("Folio");
        item.setId("F");
        solicitudHelper.getFiltros().add(item);

        item = new SelectItem();
        item.setValor("RFC");
        item.setId("R");
        solicitudHelper.getFiltros().add(item);

        item = new SelectItem();
        item.setValor("Razón Social");
        item.setId("S");
        solicitudHelper.getFiltros().add(item);

        item = new SelectItem();
        item.setValor("Fecha");
        item.setId("D");
        solicitudHelper.getFiltros().add(item);
    }

    private void evaluacionAccion(String accion) {
        if (accion.equals("guardar")) {
            accionGuardar(accion);
        } else if (accion.equals("buscar")) {
            accionBuscar();
        } else if (accion.equals("acuse")) {
            accionAcuse();
        }
    }

    private void accionGuardar(String accion) {
        String accionEvaluada = accion;
        SolicitudesRegistradas solRegGuardar = (SolicitudesRegistradas) getSession().getAttribute("solicitud");
        getSession().setAttribute("solicitud", null);

        if (solRegGuardar != null) {
            solicitudHelper.setTipoResolucionSelect(String.valueOf(solRegGuardar.getEnProceso()));

            accionEvaluada = (String) getSession().getAttribute(FOLIO);
            solicitudHelper.setSolicitud(new SolitudAutorizada());
            solicitudHelper.getSolicitud().setFolio(accionEvaluada);
            solicitudHelper.setFiltroSel("" + getSession().getAttribute(FILTRO));
            solicitudHelper.setBuscSel(solicitudHelper.getFiltroSel());
            solicitudHelper.setParamBusq((String) getSession().getAttribute(BUSQUEDA));
            List<SolitudAutorizada> retorno = autorizarSolicitudService.obtenerSolRegActual(2, accionEvaluada, null);

            solicitudHelper.setCtrlUploader(true);
            solicitudHelper.setBtnBorrar(true);
            solicitudHelper.setBtnGuardar(true);
            solicitudHelper.setBtnImprimir(true);
            solicitudHelper.setCantAutCapt(false);
            solicitudHelper.setNoFolioCapt(false);
            solicitudHelper.setAutCapt(false);
            solicitudHelper.setFechaCapt(false);
            solicitudHelper.setResCapt(false);

            if (retorno.size() > 0) {
                solicitudHelper.setSolicitud(retorno.get(0));
                solicitudHelper.setFiltroSel(solicitudHelper.getSolicitud().getEstatusResolucionInfo());
                solicitudHelper.setAutorizante(String.valueOf(solicitudHelper.getSolicitud().getServidorPublicoAutorizante()));
                if (solicitudHelper.getSolicitud().getCantidadAutorizada() > 0) {
                    solicitudHelper.setCantAutCapt(true);
                    solicitudHelper.setNoFolioCapt(true);
                    solicitudHelper.setAutCapt(true);
                    solicitudHelper.setFechaCapt(true);
                    solicitudHelper.setResCapt(true);
                }
            } else {
                try {
                    List<SolicitudesRegistradas> solicitudesList
                            = autorizarSolicitudService.obtenerSolicitudes(2, accionEvaluada, null);
                    if (solicitudesList.size() > 0) {
                        solicitudHelper.getSolicitud().setCantidadSolicitud(solicitudesList.get(0).getCantCodigos());
                        solicitudHelper.getSolicitud().setEstatusResolucion(0);
                        solicitudHelper.getSolicitud().setCantidadAutorizada(0L);
                        solicitudHelper.getSolicitud().setDocumentoResolucion("");
                        solicitudHelper.getSolicitud().setFecha(null);
                        solicitudHelper.getSolicitud().setNumeroOficio("");
                        solicitudHelper.getSolicitud().setRfc(solicitudesList.get(0).getRfc());
                        solicitudHelper.getSolicitud().setRazonSocial(solicitudesList.get(0).getRazonSocial());
                        solicitudHelper.getSolicitud().setFolio(solicitudesList.get(0).getFolio());
                    }
                } catch (ServiceException ex) {
                    addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
                    getLogger().error(ex);
                }
            }
        }
    }

    private void accionBuscar() {
        String accionEvaluada;
        try {
            accionEvaluada = (String) getSession().getAttribute(FOLIO);
            if (solicitudHelper.getSolicitud() == null) {
                solicitudHelper.setSolicitud(new SolitudAutorizada());
            }
            solicitudHelper.getSolicitud().setFolio(accionEvaluada);
            solicitudHelper.setParamBusq((String) getSession().getAttribute(BUSQUEDA));
            solicitudHelper.setFiltroSel("" + getSession().getAttribute(FILTRO));

            Date fecha;
            if (solicitudHelper.getFiltroSel().equals("D")) {
                fecha = converteSD(solicitudHelper.getParamBusq());
                solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes((EstadoResolucionEnum.PROCESADO.getKey().intValue()),
                        null, fecha));
            } else {
                solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes(
                        parametroBusqueda(solicitudHelper.getFiltroSel()), solicitudHelper.getParamBusq(), null));
            }

        } catch (ServiceException ex) {
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
            getLogger().error(ex);
        }
    }

    private void accionAcuse() {
        solicitudHelper.setSolicitud((SolitudAutorizada) getSession().getAttribute("solicitudAutorizada"));

        solicitudHelper.setParamBusq((String) getSession().getAttribute(BUSQUEDA));
        solicitudHelper.setFiltroSel("" + getSession().getAttribute(FILTRO));
        solicitudHelper.setBuscSel(solicitudHelper.getFiltroSel());
    }

    /**
     *
     * @param event
     */
    public void upload(FileUploadEvent event) {
        if (event.getFile() != null) {
            autorizarSolicitudHelper.setArchivoResolucion(event.getFile());
            autorizarSolicitudHelper.setNombreArchivoRes(Utilerias.obtenerNobreArchivo(autorizarSolicitudHelper.getArchivoResolucion().getFileName()));
            autorizarSolicitudHelper.setRutaArchivoRes(fileUtil.getRutaArchivoValida(autorizarSolicitudService.getDirectorioBase(), solicitudHelper.getSolicitud().getRfc(), solicitudHelper.getSolicitud().getFolio()));
            solicitudHelper.getSolicitud().setNombreArchivo(autorizarSolicitudHelper.getNombreArchivoRes());

            solicitudHelper.setBtnBorrar(false);
            solicitudHelper.setBtnGuardar(false);
            solicitudHelper.setCtrlUploader(true);
        }
    }

    /**
     *
     * @param e
     */
    public void onChangeAut(ValueChangeEvent e) {
        String item = (String) e.getNewValue();
        String anterior = (String) e.getOldValue();

        if (item != null && item.equals("3")) {
            solicitudHelper.setCantAutCapt(true);
            solicitudHelper.getSolicitud().setEstatusResolucion((EstadoResolucionEnum.RECHAZADO.getKey().intValue()));
            return;
        } else if (anterior != null && anterior.equals("3")) {
            solicitudHelper.setCantAutCapt(false);
        }

        if (item != null && item.equals("2")) {
            solicitudHelper.getSolicitud().setEstatusResolucion((EstadoResolucionEnum.AUTORIZADO.getKey().intValue()));
        } else {
            solicitudHelper.getSolicitud().setEstatusResolucion(0);
        }
    }

    public void buscarSolicitud() {
        if ((solicitudHelper.getFiltroSel() != null) && (solicitudHelper.getParamBusq() != null)) {
            solicitudHelper.setParamBusq(solicitudHelper.getParamBusq().toUpperCase());
            try {
                switch (solicitudHelper.getFiltroSel().charAt(0)) {
                    case 'R': {

                        if (!ValidaRFC.validaRFC(solicitudHelper.getParamBusq())) {
                            return;
                        }
                        if (solicitudHelper.getParamBusq().length() == Expresiones.RFC_LONGITUD && (!ValidaRFC.validaRFCFisica(solicitudHelper.getParamBusq()))) {
                            return;
                        } else if (solicitudHelper.getParamBusq().length() == Expresiones.RFCM_LONGITUD && ((!ValidaRFC.validaRFCMoral(solicitudHelper.getParamBusq())))) {
                            return;
                        }
                        solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes(1,
                                solicitudHelper.getParamBusq(), null));
                    }
                    break;
                    case 'F': {

                        BigInteger tmp;

                        try {
                            tmp = new BigInteger(solicitudHelper.getParamBusq());
                        } catch (NumberFormatException ex) {
                            getLogger().error("El parametro de busqueda no es numerico", ex);
                            tmp = null;
                        }

                        if (solicitudHelper.getParamBusq().length() > SIZE_QUEST) {
                            addErrorMessage(ERROR, "No puede ser mayor a 22 posiciones");
                            return;
                        }
                        if (tmp == null) {
                            addErrorMessage(ERROR, "No es un valor numerico");
                            return;
                        }

                        solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes((EstadoResolucionEnum.AUTORIZADO.getKey().intValue()),
                                solicitudHelper.getParamBusq(), null));
                    }
                    break;
                    case 'S': {
                        solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes((EstadoResolucionEnum.RECHAZADO.getKey().intValue()),
                                solicitudHelper.getParamBusq(), null));
                    }
                    break;
                    case 'D': {
                        Date fecha;

                        if (!isFechaValida(solicitudHelper.getParamBusq())) {
                            addErrorMessage(ERROR, getMessageResourceString("msg.error.solicitud.formato.fecha"));
                            return;
                        }

                        fecha = converteSD(solicitudHelper.getParamBusq());

                        solicitudHelper.setSolicitudes(autorizarSolicitudService.obtenerSolicitudes((EstadoResolucionEnum.PROCESADO.getKey().intValue()),
                                null, fecha));
                    }
                    break;
                    default: {
                        addErrorMessage(ERROR, "Debera seleccionar primero alguna opcion del combo");
                    }
                }
            } catch (Exception ex) {
                getLogger().error(ex);
            }
        }
    }

    /**
     *
     * @param parametro
     * @return
     */
    public int parametroBusqueda(String parametro) {
        switch (parametro.charAt(0)) {
            case 'R':
                return CASE_R;
            case 'F':
                return CASE_F;
            case 'S':
                return CASE_S;
            case 'D':
                return CASE_D;
            default:
                return 0;
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void seleccionarSolicitud(SelectEvent event) throws IOException {
        solicitudHelper.setRegistro(((SolicitudesRegistradas) event.getObject()));
        String url = "guardarSolicitud.jsf";

        getSession().setAttribute(ACTION, "guardar");
        getSession().setAttribute(FOLIO, solicitudHelper.getRegistro().getFolio());
        getSession().setAttribute(FILTRO, solicitudHelper.getFiltroSel());
        getSession().setAttribute(BUSQUEDA, solicitudHelper.getParamBusq());
        getSession().setAttribute("solicitud", solicitudHelper.getRegistro());

        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    /**
     *
     */
    public void regresarSolicitud() {
        String url = "buscarSolicitud.jsf";

        getSession().setAttribute(ACTION, "buscar");
        getSession().setAttribute(FOLIO, solicitudHelper.getSolicitud().getFolio());
        getSession().setAttribute(FILTRO, solicitudHelper.getBuscSel());
        getSession().setAttribute(BUSQUEDA, solicitudHelper.getParamBusq());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(AutorizarSolicitudesMBean.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void onDateSelect() {
        solicitudHelper.setCtrlUploader(true);
        solicitudHelper.setBtnBorrar(true);
        solicitudHelper.setBtnGuardar(true);
        solicitudHelper.setBtnImprimir(true);
    }

    /**
     *
     */
    public void guardarSolicitud() {
        if (solicitudHelper.getSolicitud().getEstatusResolucion() == (EstadoResolucionEnum.AUTORIZADO.getKey().intValue())) {
            if (solicitudHelper.getSolicitud().getCantidadAutorizada() == 0) {
                addErrorMessage(ERROR, "Deberá de ser mayor a cero la cantidad autorizada");
                return;
            } else if (solicitudHelper.getSolicitud().getCantidadAutorizada()
                    > solicitudHelper.getSolicitud().getCantidadSolicitud()) {
                addErrorMessage(ERROR, "La cantidad autorizada debe ser menor"
                        + " o igual a la cantidad solicitada.");
                return;
            }
        }

        if (autorizarSolicitudHelper.getNombreArchivoRes().isEmpty()) {
            addErrorMessage(ERROR, "Deberá de llevar un documento de resolución");
            return;
        }

        solicitudHelper.setBtnImprimir(false);
        int resultado;
        solicitudHelper.getSolicitud().setServidorPublicoAutorizante(Integer.parseInt(solicitudHelper.getAutorizante()));
        if ((solicitudHelper.getSolicitud().getEstatusResolucion() == (EstadoResolucionEnum.AUTORIZADO.getKey().intValue())) || (solicitudHelper.getSolicitud().getEstatusResolucion() == (EstadoResolucionEnum.RECHAZADO.getKey().intValue()))) {
            try {
                resultado = autorizarSolicitudService.autorizarSolicitud(solicitudHelper.getSolicitud(), autorizarSolicitudHelper.getArchivoResolucion().getInputstream(),
                        autorizarSolicitudHelper.getRutaArchivoRes(), autorizarSolicitudHelper.getNombreArchivoRes());
                registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.PE_AUTORIZAR_SOLICITUD, new Date(), new Date(), MovimientosBitacoraEnum.AUTORIZA_SOLICITUDES);
            } catch (ServiceException ex) {
                resultado = 0;
            } catch (IOException ex) {
                resultado = 0;

            }
        } else {
            addErrorMessage(ERROR, "Debe de asignar un estado a la resolución");
            return;
        }

        if (resultado == 1) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Info!", "Se ha almacenado correctamente la autorización, puede imprimir su acuse."));

            String url = "acuseAutSol.jsf";

            getSession().setAttribute(ACTION, "acuse");
            getSession().setAttribute("solicitudAutorizada", solicitudHelper.getSolicitud());
            getSession().setAttribute(FILTRO, solicitudHelper.getBuscSel());
            getSession().setAttribute(BUSQUEDA, solicitudHelper.getParamBusq());

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } catch (IOException ex) {
                Logger.getLogger(AutorizarSolicitudesMBean.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        } else {
            addErrorMessage(ERROR, "No se ha almacenado correctamente la autorización.");
        }
    }

    /**
     *
     */
    public void imprimirSolicitud() {
        byte[] retorno;
        retorno = autorizarSolicitudService.generarAcuse(solicitudHelper.getSolicitud());
        generaDocumento(retorno, MIMETypesEnum.PDF, "Acuse", FileExtensionEnum.PDF);
    }

    /**
     *
     * @param event
     */
    public void selectFiltro(AjaxBehaviorEvent event) {
        solicitudHelper.setCaptFiltro(false);
    }

    /**
     *
     * @param event
     */
    public void selectFecha(SelectEvent event) {
        solicitudHelper.setCtrlUploader(false);
    }

    /**
     *
     */
    public void borrarArchivo() {
        solicitudHelper.setCtrlUploader(false);
        solicitudHelper.getSolicitud().setDocumentoResolucion("");
        solicitudHelper.getSolicitud().setNombreArchivo("");
        autorizarSolicitudHelper = new AutorizarSolicitudHelper();
        solicitudHelper.setBtnBorrar(true);
        solicitudHelper.setBtnGuardar(true);
    }

    /**
     *
     * @param fecha
     * @return
     */
    private Date converteSD(String fecha) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            getLogger().error("Error al convertir la fecha" + e.getMessage(), e);
        }

        return date;
    }

    /**
     *
     * @param fecha
     * @return
     */
    public boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public void getEstadosResolucion() {
        solicitudHelper.setLstTipoResolucion(new ArrayList<SelectItem>());
        for (EstadoResolucion item : EstadoResolucion.values()) {
            SelectItem tipoRes = new SelectItem();
            tipoRes.setId(item.getValue());
            tipoRes.setValor(item.getDescripcion());
            solicitudHelper.getLstTipoResolucion().add(tipoRes);
        }
    }

    /**
     *
     * @return
     */
    public Date getToday() {
        return new Date();
    }

    public String getFechaSolicitud() {
        if (solicitudHelper.getSolicitud() != null && solicitudHelper.getSolicitud().getFecha() != null) {
            return FechaUtil.formatFecha(solicitudHelper.getSolicitud().getFecha(), FechaUtil.FORMATO_DEFAULT);
        } else {
            return "";
        }
    }

    public SolicitudHelper getSolicitudHelper() {
        return solicitudHelper;
    }

    public void setSolicitudHelper(SolicitudHelper solicitudHelper) {
        this.solicitudHelper = solicitudHelper;
    }
}
