/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.marcas;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.AutorizarSolicitudSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.negocio.AutorizarMarcaService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import mx.gob.sat.mat.tabacos.vista.util.Expresiones;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRFC;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Controller("autorizarMarcasMBean")
@Scope(value = "view")
public class AutorizarMarcasMBean extends AbstractManagedBean {

    private String destination;
    private List<SolicitudesRegistradas> solicitudes;
    private List<SelectItem> filtros;
    private List<SelectItem> resoluciones;
    private List<SelectItem> autorizantes;
    private String filtroSel;
    private String buscSel;
    private String paramBusq;
    private MarcaResolucion solicitud;
    private String razonSocial;
    private String oficio;
    private String autorizante;
    private boolean btnBorrar;
    private boolean btnGuardar;
    private boolean btnImprimir;
    private boolean captFiltro;
    private boolean fechaActiva;
    private SolicitudesRegistradas registro;
    private static final String PARAM_FILTRO ="&filtro=";
    private static final String PARAM_BUSQUEDA="&busqueda=";
    
    private static final int CASE_C = 1;
    private static final int CASE_R = 2;
    private static final int CASE_S = 3;
    private static final int CASE_D = 4;
    
    @Autowired
    private transient AutorizarMarcaService autorizarMarcaService;

    @PostConstruct
    public void init() {
        try {
            validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_MARCAS.getDescripcion());
            autorizantes = autorizarMarcaService.obtenerCombos(
                    AutorizarSolicitudSQL.SQL_SELECT_AUTORIZACIONCB,
                    "IDSPAUTORIZA", "DESCSPAUTORIZA");

            resoluciones = autorizarMarcaService.
                    obtenerCombos(AutorizarSolicitudSQL.SQL_SELECT_RESOLUCIONCB,
                            "IDESTRESOLUCION", "DESCESTRESOLUCION");

            filtros = new ArrayList<SelectItem>();
            SelectItem item = new SelectItem();
            item.setValor("Clave");
            item.setId("C");
            filtros.add(item);

            item = new SelectItem();
            item.setValor("RFC");
            item.setId("R");
            filtros.add(item);

            item = new SelectItem();
            item.setValor("Razón Social");
            item.setId("S");
            filtros.add(item);

            item = new SelectItem();
            item.setValor("Fecha");
            item.setId("D");
            filtros.add(item);

            captFiltro = true;

            Map requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String tmp = (String) requestParams.get("action");
            solicitud = new MarcaResolucion();

            if (tmp != null) {
                if (tmp.equals("guardar")) {
                    tmp = (String) requestParams.get("clave");
                    solicitud.setClave(tmp);
                    filtroSel = (String) requestParams.get("filtro");
                    buscSel = filtroSel;
                    paramBusq = (String) requestParams.get("busqueda");
                    solicitudes = autorizarMarcaService.obtenerSolicitudes(1, tmp, null);
                    solicitud.setRfc(solicitudes.get(0).getRfc());
                    solicitud.setMarca(solicitudes.get(0).getMarca());
                    solicitud.setClave(solicitudes.get(0).getClave());
                    razonSocial = solicitudes.get(0).getRazonSocial();

                    btnBorrar = true;
                    btnGuardar = true;
                    btnImprimir = true;
                    captFiltro = true;
                    fechaActiva = false;
                } else if (tmp.equals("buscar")) {
                    tmp = (String) requestParams.get("clave");
                    solicitud.setClave(tmp);
                    paramBusq = (String) requestParams.get("busqueda");
                    filtroSel = (String) requestParams.get("filtro");
                    
                    
                    Date fecha;
                    if (filtroSel.equals("D")) {
                        fecha = converteSD(paramBusq);
                        solicitudes = setEstadoResolucion(setEstadoResolucion(autorizarMarcaService.obtenerSolicitudes((EstatusEnum.CANCELADO.getKey().intValue()),
                                null, fecha)));
                    }else{
                        solicitudes = setEstadoResolucion(autorizarMarcaService.obtenerSolicitudes(
                            parametroBusqueda(filtroSel), paramBusq, null));
                    }
                    
                    
                    
                    
                } else if (tmp.equals("acuse")) {
                    tmp = (String) requestParams.get("clave");
                    solicitud.setClave(tmp);
                    tmp = (String) requestParams.get("rfc");
                    solicitud.setRfc(tmp);
                    tmp = (String) requestParams.get("rs");
                    solicitud.setRazonsocial(tmp);
                    tmp = (String) requestParams.get("marca");
                    solicitud.setMarca(tmp);
                    tmp = (String) requestParams.get("fecha");
                    solicitud.setFechaString(tmp);
                    solicitud.setFechaInicio(converteSD(tmp));
                    tmp = (String) requestParams.get("res");
                    solicitud.setEstatusResolucion(Integer.valueOf(tmp));
                    tmp = (String) requestParams.get("sp");
                    solicitud.setServidorPublicoAutorizante(Integer.valueOf(tmp));
                    paramBusq = (String) requestParams.get("busqueda");
                    filtroSel = (String) requestParams.get("filtro");
                    buscSel = filtroSel;
                }
            }
        } catch (ServiceException ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, getMessageResourceString("msg.error.carga.informacion"));
        }
    }

    /**
     *
     * @param parametro
     * @return
     */
    public int parametroBusqueda(String parametro) {
        switch (parametro.charAt(0)) {
            case 'C': {
                return CASE_C;
            }
            case 'R': {
                return CASE_R;
            }
            case 'S': {
                return CASE_S;
            }
            case 'D': {
                return CASE_D;
            }
            default:
                return 0;
        }

        
    }

    /**
     *
     */
    public void buscarSolicitud() {
        if ((filtroSel != null) && (paramBusq != null)) {
            paramBusq = paramBusq.toUpperCase();
            try {
                switch (filtroSel.charAt(0)) {
                    case 'C': {
                        solicitudes = autorizarMarcaService.obtenerSolicitudes((EstatusEnum.ALTA.getKey().intValue()),
                                paramBusq, null);

                        if (solicitudes.size() > 0) {
                            String url = "guardarMarca.jsf?action=guardar&clave=";
                            url += solicitudes.get(0).getClave();
                            url += PARAM_FILTRO + filtroSel.charAt(0);
                            url += PARAM_BUSQUEDA + paramBusq;
                            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                        } else {
                            addErrorMessage(ERROR, "No existe la clave definida en marcas");
                        }
                    }
                    break;
                    case 'R': {

                        if (!ValidaRFC.validaRFC(paramBusq)) {
                            return;
                        }
                        if (paramBusq.length() == Expresiones.RFC_LONGITUD) {
                            if (!ValidaRFC.validaRFCFisica(paramBusq)) {
                                return;
                            }
                        } else if (paramBusq.length() == Expresiones.RFCM_LONGITUD && (!ValidaRFC.validaRFCMoral(paramBusq))) {
                            return;
                        }

                        solicitudes = setEstadoResolucion(autorizarMarcaService.obtenerSolicitudes((EstatusEnum.BAJA.getKey().intValue()),
                                paramBusq, null));
                    }
                    break;
                    case 'S': {
                        solicitudes = setEstadoResolucion(autorizarMarcaService.obtenerSolicitudes((EstatusEnum.SOLICITADO.getKey().intValue()),
                                paramBusq, null));
                    }
                    break;
                    case 'D': {
                        Date fecha;

                        if (!isFechaValida(paramBusq)) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Error!", "No es una fecha valida en formato dd/MM/yyyy"));
                            return;
                        }

                        fecha = converteSD(paramBusq);

                        solicitudes = setEstadoResolucion(autorizarMarcaService.obtenerSolicitudes((EstatusEnum.CANCELADO.getKey().intValue()),
                                null, fecha));
                    }
                    break;
                    default: {
                        addMessage(INFO, "Deberá seleccionar primero alguna opciones del combo");
                    }
                }
            } catch (IOException ex) {
                getLogger().error(ex);
            }
        }
    }

    public List<SolicitudesRegistradas> setEstadoResolucion(List<SolicitudesRegistradas> lstSolicitudes) {
        for (SolicitudesRegistradas item : lstSolicitudes) {
            if (item.getEnProceso() == (EstatusEnum.SOLICITADO.getKey().intValue())) {
                item.setEstadoResolicion("SOLICITADO");
            }
        }

        return lstSolicitudes;

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
    private String converteDS(Date fecha) {
        String retorno = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        retorno = formatter.format(fecha);

        return retorno;
    }

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

    /**
     *
     * @param event
     * @throws IOException
     */
    public void seleccionarSolicitud(SelectEvent event) throws IOException {
        registro = ((SolicitudesRegistradas) event.getObject());
        String url = "guardarMarca.jsf?action=guardar&clave=";
        url += registro.getClave();
        url += PARAM_FILTRO + filtroSel.charAt(0);
        url += PARAM_BUSQUEDA + paramBusq;

        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    /**
     *
     */
    public void regresarSolicitud() {
        String url = "buscarMarca.jsf?action=buscar&clave=";
        url += solicitud.getClave();
        url += PARAM_FILTRO + buscSel;
        url += PARAM_BUSQUEDA + paramBusq;

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            getLogger().error(ex);
        }
    }

    /**
     *
     */
    public void onDateSelect() {
        btnBorrar = true;
        btnGuardar = true;
        btnImprimir = true;
    }

    /**
     *
     */
    public void guardarSolicitud() {
        try {
            if ((filtroSel != null)) {
                if (filtroSel.equals("Autorizada")) {
                    solicitud = autorizarMarcaService.buscaMarcaXClave(solicitud.getClave());
                    solicitud.setEstatusResolucion(1);
                } else if (filtroSel.equals("Rechazada")) {
                    solicitud.setEstatusResolucion((EstatusEnum.BAJA.getKey().intValue()));
                }
            }
            if (autorizante != null) {
                solicitud.setServidorPublicoAutorizante(Integer.valueOf(autorizante).intValue());
            }

            String mensaje = validaSolicitud();

            if (!mensaje.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Info!", mensaje));
                return;
            }

            if (autorizarMarcaService.autorizarSolicitud(solicitud) > 0) {
                String url = "acuseAutMarca.jsf?action=acuse&clave=";
                url += solicitud.getClave();
                url += "&rfc=" + solicitud.getRfc();
                url += "&marca=" + solicitud.getMarca();
                url += "&rs=" + this.razonSocial;
                url += "&fecha=" + converteDS(solicitud.getFechaInicio());
                url += "&res=" + solicitud.getEstatusResolucion();
                url += "&sp=" + solicitud.getServidorPublicoAutorizante();
                url += PARAM_FILTRO + buscSel;
                url += PARAM_BUSQUEDA + paramBusq;
                
                registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_MARCAS,new Date(),new Date(),MovimientosBitacoraEnum.AUTORIZAR_MARCAS);

                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                    addMessage(INFO, "Se almaceno correctamente la autorizacion de marca.");
                } catch (IOException ex) {
                    addErrorMessage(ERROR, "No se almaceno correctamente la autorizacion de marca.");
                    getLogger().error(ex);
                }

            } else {
                addErrorMessage(ERROR, "No se almaceno correctamente la autorizacion de marca.");
            }
        } catch (ServiceException ex) {
            getLogger().error(ex);
            addErrorMessage(ERROR, "No se almaceno correctamente la autorizacion de marca.");
        }
    }

    public void onChangeEstado(ValueChangeEvent event) {
        String item = (String) event.getNewValue();
        if (item.equals("1")) {
            solicitud.setFechaInicio(new Date());
            fechaActiva = true;
        } else {
            fechaActiva = false;
        }
    }

    private String validaSolicitud() {
        String retorno = "";

        if ((filtroSel == null)
                || (solicitud.getEstatusResolucion() == 0)) {
            retorno = "Deberá de seleccionar el estado de la resolución.";
        } else if ((autorizante == null)
                || (solicitud.getServidorPublicoAutorizante() == 0)) {
            retorno = "Deberá incluir el tipo de servidor público que autoriza la marca.";
        } else if (solicitud.getFechaInicio() == null) {
            retorno = "Deberá de seleccionar la fecha válida.";
        }

        return retorno;
    }

    /**
     *
     */
    public void imprimirSolicitud() {
        byte[] retorno = autorizarMarcaService.generarAcuse(solicitud);
        generaDocumento(retorno, MIMETypesEnum.PDF, "AcuseAutorizarMarca",
                FileExtensionEnum.PDF);
    }

    /**
     *
     * @param event
     */
    public void selectFiltro(AjaxBehaviorEvent event) {
        captFiltro = false;
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
     * @return
     */
    public SolicitudesRegistradas getRegistro() {
        return registro;
    }

    /**
     *
     * @param registro
     */
    public void setRegistro(SolicitudesRegistradas registro) {
        this.registro = registro;
    }

    /**
     *
     * @return
     */
    public boolean isBtnBorrar() {
        return btnBorrar;
    }

    /**
     *
     * @param btnBorrar
     */
    public void setBtnBorrar(boolean btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    /**
     *
     * @return
     */
    public boolean isBtnGuardar() {
        return btnGuardar;
    }

    /**
     *
     * @param btnGuardar
     */
    public void setBtnGuardar(boolean btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     *
     * @return
     */
    public boolean isBtnImprimir() {
        return btnImprimir;
    }

    /**
     *
     * @param btnImprimir
     */
    public void setBtnImprimir(boolean btnImprimir) {
        this.btnImprimir = btnImprimir;
    }

    /**
     *
     * @return
     */
    public boolean isCaptFiltro() {
        return captFiltro;
    }

    /**
     *
     * @param captFiltro
     */
    public void setCaptFiltro(boolean captFiltro) {
        this.captFiltro = captFiltro;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<SelectItem> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<SelectItem> filtros) {
        this.filtros = filtros;
    }

    public List<SelectItem> getResoluciones() {
        return resoluciones;
    }

    public void setResoluciones(List<SelectItem> resoluciones) {
        this.resoluciones = resoluciones;
    }

    public String getFiltroSel() {
        return filtroSel;
    }

    public void setFiltroSel(String filtroSel) {
        this.filtroSel = filtroSel;
    }

    public String getBuscSel() {
        return buscSel;
    }

    public void setBuscSel(String buscSel) {
        this.buscSel = buscSel;
    }

    public String getParamBusq() {
        return paramBusq;
    }

    public void setParamBusq(String paramBusq) {
        this.paramBusq = paramBusq;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public List<SolicitudesRegistradas> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudesRegistradas> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<SelectItem> getAutorizantes() {
        return autorizantes;
    }

    public void setAutorizantes(List<SelectItem> autorizantes) {
        this.autorizantes = autorizantes;
    }

    public MarcaResolucion getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(MarcaResolucion solicitud) {
        this.solicitud = solicitud;
    }

    public String getAutorizante() {
        return autorizante;
    }

    public void setAutorizante(String autorizante) {
        this.autorizante = autorizante;
    }

    public void selectFecha(SelectEvent event) {
        btnGuardar = false;
    }

    public boolean isFechaActiva() {
        return fechaActiva;
    }

    public void setFechaActiva(boolean fechaActiva) {
        this.fechaActiva = fechaActiva;
    }
}
