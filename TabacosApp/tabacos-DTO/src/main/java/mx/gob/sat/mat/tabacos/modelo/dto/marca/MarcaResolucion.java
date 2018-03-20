package mx.gob.sat.mat.tabacos.modelo.dto.marca;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class MarcaResolucion implements Serializable {

    private static final long serialVersionUID = 5156884122872234074L;
    private static final int AUTORIZADA = 1;
    private static final int RECHAZADA = 2;
    private static final int GENERADA = 5;
    private static final int ADMCNT = 1;
    private static final int ADM = 2;
    private static final int SUBADM = 3;
    private static final int JEFE = 4;

    private String folio;
    private String rfc;
    private String razonsocial;
    private int estatusResolucion;
    private int servidorPublicoAutorizante;
    private String clave;
    private Date fechaInicio;
    private Date fechaFin;
    private String documentoResolucion;
    private String marca;
    private String fechaString;
    private Long idServidorPublico;
    private String servidorPublico;
    private Long idEstatus;
    private String estatus;
    private Long idMarca;

    /**
     *
     */
    public MarcaResolucion() {
        this.folio = "";
        this.rfc = "";
        this.estatusResolucion = 0;
        this.servidorPublicoAutorizante = 0;
        this.fechaInicio = null;
        this.documentoResolucion = "";
        this.clave = "";
    }

    /**
     *
     * @return
     */
    public String getFolio() {
        return folio;
    }

    /**
     *
     * @param folio
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc.toUpperCase();
    }

    /**
     *
     * @return
     */
    public int getEstatusResolucion() {
        return estatusResolucion;
    }

    /**
     *
     * @param estatusResolucion
     */
    public void setEstatusResolucion(int estatusResolucion) {
        this.estatusResolucion = estatusResolucion;
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

    /**
     *
     * @return
     */
    public int getServidorPublicoAutorizante() {
        return servidorPublicoAutorizante;
    }

    /**
     *
     * @param servidorPublicoAutorizante
     */
    public void setServidorPublicoAutorizante(int servidorPublicoAutorizante) {
        this.servidorPublicoAutorizante = servidorPublicoAutorizante;
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

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        return (this.fechaInicio == null) ? null : (Date) this.fechaInicio.clone();
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio == null ? null : (Date) fechaInicio.clone();
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        return (this.fechaFin == null) ? null : (Date) this.fechaFin.clone();
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaFin(final Date fechaInicio) {
        this.fechaFin = fechaFin == null ? null : (Date) fechaFin.clone();
    }

    /**
     *
     * @return
     */
    public String getDocumentoResolucion() {
        return documentoResolucion;
    }

    /**
     *
     * @param documentoResolucion
     */
    public void setDocumentoResolucion(String documentoResolucion) {
        this.documentoResolucion = documentoResolucion;
    }

    /**
     *
     * @return
     */
    public String getClave() {
        return clave;
    }

    /**
     *
     * @param clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    public Long getIdServidorPublico() {
        return idServidorPublico;
    }

    public void setIdServidorPublico(Long idServidorPublico) {
        this.idServidorPublico = idServidorPublico;
    }

    public String getServidorPublico() {
        return servidorPublico;
    }

    public void setServidorPublico(String servidorPublico) {
        this.servidorPublico = servidorPublico;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

}
