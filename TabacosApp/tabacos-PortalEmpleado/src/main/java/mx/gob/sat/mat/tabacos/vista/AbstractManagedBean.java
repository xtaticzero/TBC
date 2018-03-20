package mx.gob.sat.mat.tabacos.vista;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.negocio.BitacoraTbcService;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;

import mx.gob.sat.siat.controlador.AccesoProceso;
import mx.gob.sat.siat.exception.AccesoDenegadoException;
import mx.gob.sat.siat.exception.SessionRolNullException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;
import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractManagedBean {

    /**
     * Objeto del log.
     */
    private final Logger log;
    private HttpSession session;

    public static final String ERROR = "Error";
    public static final String INFO = "Información";
    public static final String WARN = "Atención";
    public static final String FATAL = "Error grave";

    @Autowired
    @Qualifier("proveedorServiceImpl")
    private ProveedorService proveedorServiceImpl;

    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    @Autowired
    @Qualifier("bitacoraTbcService")
    private BitacoraTbcService bitacoraTbcService;

    /**
     * pistaAuditoriaService es la referencia del servicio de Pistas de
     * Auditoria.
     */
    public AbstractManagedBean() {
        log = Logger.getLogger(getClass());
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public boolean isDevelop() {
        ServletContext servletContext
                = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        return Boolean.parseBoolean(servletContext.getInitParameter(Constantes.SCOOP_DEV));
    }

    public void validaAccesoProceso(String identificadorProceso) {
        try {
            if (!isDevelop()) {
                AccesoProceso.validaAccesoProceso(Faces.getSession(false), identificadorProceso);
            }
        } catch (SessionRolNullException e) {
            log.error("Se presento un problema al obtener la session ", e);
            redireccionarPorOperacionDenegada(e);
        } catch (AccesoDenegadoException e) {
            log.error("Acceso Denegado ", e);
            redireccionarPorOperacionDenegada(e);
        } catch (Exception e) {
            log.error("Error, acceso denegado: ", e);
            redireccionarPorOperacionDenegada(e);
        }
    }

    public void redireccionarPorOperacionDenegada(Exception e) {
        HttpSession sessionRedirect;
        sessionRedirect = getSession();

        sessionRedirect.setAttribute("mensaje", e);

        ServletContext dir = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detalleError", e.getCause());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(dir.getContextPath()
                    + Constantes.PAGINA_ADD_ENCONSTRUCCION_ERROR);
        } catch (IOException ioe) {
            log.error("Error al redireccionar: ", e);
        }
    }

    public String getRFCSession() {
        String rfc = null;
        ServletContext servletContext
                = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        if (Boolean.parseBoolean(servletContext.getInitParameter(Constantes.SCOOP_DEV))) {
            rfc = getAccesoUsr().getUsuario().toUpperCase();
        } else {
            try {
                rfc = getAccesoUsr().getUsuario().toUpperCase();
            } catch (Exception e) {
                log.error("No se pudo obtener el rfc de session. Modo develop: ", e);
            }
        }

        log.error("*****RFC Utilizado " + rfc);

        return rfc;
    }

    public AccesoUsr getAccesoUsr() {
        AccesoUsr accesoUsr;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        accesoUsr = (AccesoUsr) session.getAttribute(Constantes.SESSION_PE);
        if (accesoUsr == null) {
            accesoUsr = Utilerias.obtenerAccesoUsrEmpleados();
        }
        return accesoUsr;
    }

    public HttpSession getSession() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        return session;
    }

    public void generaDocumento(byte[] arregloBytes, MIMETypesEnum mimeType, String nombre, FileExtensionEnum extencion) {
        if (arregloBytes != null) {
            despachaArchivo(arregloBytes, mimeType.getType(), nombre + extencion.getExtension(),
                    "Error al generar Archivo.");
        }
    }

    public void despachaArchivo(byte[] archivo, String contentType, String nombreArchivo, String error) {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                    getExternalContext().getResponse();
            response.setContentType(contentType);
            response.setContentLength(archivo.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");

            ServletOutputStream out;
            out = response.getOutputStream();
            out.write(archivo);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException e) {
            getLogger().error(e.getMessage());
            AbstractManagedBean.msgFatal(error);
        }

    }

    public void createMessage(String summary, String detail,
            FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_INFO);
    }

    public void addErrorMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_ERROR);
    }

    public void addFatalMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_FATAL);
    }

    public void addWarningMessage(String titulo, String mensaje) {
        createMessage(titulo, mensaje, FacesMessage.SEVERITY_WARN);
    }

    /**
     * Agrega un mensaje de error sin detalle.
     *
     * @param clientId, identificador del componente.
     * @param msg mensaje a mostrar.
     */
    public void msgError(String clientId, String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public void msgInfo(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
    }

    public static void msgFatal(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", msg));
    }

    public void msgWarn(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", msg));
    }

    /**
     * Agrega un mensaje de error sin detalle.
     *
     * @param msg mensaje a mostrar.
     */
    public void msgError(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
    }

    /**
     * Obtiene un mensaje del archivo de propiedades de una clave enviada.
     *
     * @param key clave a buscar.
     * @param params uno varios objetos que determinan el formato del mensaje.
     * @return String con el mensaje obtenido.
     */
    public String getMessageResourceString(String key, Object... params) {
        String value;
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "tbcMsj");

        value = formatMessage(bundle, key, params);
        return value;
    }

    private static String formatMessage(ResourceBundle bundle, String key, Object... params) {
        String text;

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }
        if (params != null) {
            MessageFormat mf = new MessageFormat(text);
            text = mf.format(params, new StringBuffer(), null).toString();
        }
        return text;
    }

    public String getRequestURL() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest) request).getRequestURL().toString();
        } else {
            return "";
        }
    }

    public List<String> autocompletarRFC(String query) {
        List<String> results = new ArrayList<String>();

        try {
            if ((query != null) && (query.length() > 0)) {
                results.clear();
                for (String rfcTmp:tababacaleraService.buscaTabacalerasLikeRfc(query)) {
                    results.add(rfcTmp);
                }
            }
        } catch (TabacaleraServiceException e) {
            getLogger().error(e);
        } catch (IndexOutOfBoundsException ex) {
            getLogger().error(ex);
        }

        return results;
    }

    public List<String> autocompletarRfcProvedor(String query) {
        List<String> results = new ArrayList<String>();

        try {
            if ((query != null) && (query.length() > 0)) {
                results.clear();
                for (Proveedor prov : proveedorServiceImpl.buscaProvedoresLikeRfc(query)) {
                    results.add(prov.getRfc());
                }
            }
        } catch (ProveedorServiceException e) {
            getLogger().error(e);
        }

        return results;
    }

    public Logger getLogger() {
        return log;
    }

    public void registroMovimientoBitacora(HttpSession session, IdentificadorProcesoEnum identificadorModulo, Date fechaSesion, Date fechaTramite, MovimientosBitacoraEnum mov) {
        try {
            bitacoraTbcService.registroMovimientoBitacora(session, identificadorModulo, fechaSesion, fechaTramite, mov);
        } catch (Exception e) {
            getLogger().error("No se pudo insertar en la bitacora " + e.getMessage(), e);
        }
    }
}
