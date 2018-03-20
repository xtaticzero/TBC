package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class SolitudAutorizada extends SolicitudDTO {

    private static final long serialVersionUID = -5777881418103075309L;
    private static final int AUTORIZADA = 2;
    private static final int RECHAZADA = 3;
    private static final int GENERADA = 5;
    private static final int ADMCNT = 1;
    private static final int ADM = 2;
    private static final int SUBADM = 3;
    private static final int JEFE = 4;

    private String folio;
    private String rfc;
    private String razonSocial;
    private long cantidadSolicitud;
    private int estatusResolucion;
    private long cantidadAutorizada;
    private int servidorPublicoAutorizante;
    private Date fecha;
    private String documentoResolucion;
    private String numeroOficio;
    private String nombreArchivo;

    public SolitudAutorizada() {
        this.folio = "";
        this.rfc = "";
        this.cantidadSolicitud = 0L;
        this.estatusResolucion = 0;
        this.cantidadAutorizada = 0L;
        this.servidorPublicoAutorizante = 0;
        this.fecha = null;
        this.documentoResolucion = "";
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public long getCantidadSolicitud() {
        return cantidadSolicitud;
    }

    public void setCantidadSolicitud(long cantidadSolicitud) {
        this.cantidadSolicitud = cantidadSolicitud;
    }

    public int getEstatusResolucion() {
        return estatusResolucion;
    }

    public String getEstatusResolucionInfo() {
        String retorno = "";

        switch (estatusResolucion) {
            case AUTORIZADA:
                retorno = "Autorizada";
                break;
            case RECHAZADA:
                retorno = "Rechazada";
                break;
            case GENERADA:
                retorno = "Generada";
                break;
            default:
                break;
        }

        return retorno;
    }

    public void setEstatusResolucion(int estatusResolucion) {
        this.estatusResolucion = estatusResolucion;
    }

    public long getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    public void setCantidadAutorizada(long cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    public int getServidorPublicoAutorizante() {
        return servidorPublicoAutorizante;
    }

    public String getServidorPublicoAutorizanteInfo() {
        String retorno = "";

        switch (servidorPublicoAutorizante) {
            case ADMCNT:
                retorno = "Administrador Central";
                break;
            case ADM:
                retorno = "Administrador";
                break;
            case SUBADM:
                retorno = "SubAdministrador";
                break;
            case JEFE:
                retorno = "Jefe de Departamento";
                break;
            default:
                break;
        }

        return retorno;
    }

    public void setServidorPublicoAutorizante(int servidorPublicoAutorizante) {
        this.servidorPublicoAutorizante = servidorPublicoAutorizante;
    }

    public Date getFecha() {
        return (fecha != null) ? (Date) fecha.clone() : null;
    }

    public void setFecha(Date fecha) {
        this.fecha = (fecha != null) ? (Date) fecha.clone() : null;
    }

    public String getDocumentoResolucion() {
        return documentoResolucion;
    }

    public void setDocumentoResolucion(String documentoResolucion) {
        this.documentoResolucion = documentoResolucion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
