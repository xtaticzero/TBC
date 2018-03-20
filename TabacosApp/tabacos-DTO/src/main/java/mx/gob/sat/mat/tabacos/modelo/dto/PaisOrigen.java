package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class PaisOrigen implements Serializable {

    private static final long serialVersionUID = -8962397870669357267L;

    private Long idPais;
    private String descCorta;
    private String descLarga;

    /**/
    private String clavePais;
    private String codigoPais;
    private String usuarioCreador;
    private Date fechaCreacion;
    private String usuarioModificador;
    private Date fechaModificacion;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public String getDescCorta() {
        return descCorta;
    }

    public void setDescCorta(String descCorta) {
        this.descCorta = descCorta;
    }

    public String getDescLarga() {
        return descLarga;
    }

    public void setDescLarga(String descLarga) {
        this.descLarga = descLarga;
    }

    /**
     * @return the clavePais
     */
    public String getClavePais() {
        return clavePais;
    }

    /**
     * @param clavePais the clavePais to set
     */
    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }

    /**
     * @return the codigoPais
     */
    public String getCodigoPais() {
        return codigoPais;
    }

    /**
     * @param codigoPais the codigoPais to set
     */
    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    /**
     * @return the usuarioCreador
     */
    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    /**
     * @param usuarioCreador the usuarioCreador to set
     */
    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return (fechaCreacion != null) ? (Date) fechaCreacion.clone() : null;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = (fechaCreacion != null) ? (Date) fechaCreacion.clone() : null;
    }

    /**
     * @return the usuarioModificador
     */
    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    /**
     * @param usuarioModificador the usuarioModificador to set
     */
    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return (fechaModificacion != null) ? (Date) fechaModificacion.clone() : null;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = (fechaModificacion != null) ? (Date) fechaModificacion.clone() : null;
    }

    /**
     * @return the fechaInicioVigencia
     */
    public Date getFechaInicioVigencia() {
        return (fechaInicioVigencia != null) ? (Date) fechaInicioVigencia.clone() : null;
    }

    /**
     * @param fechaInicioVigencia the fechaInicioVigencia to set
     */
    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = (fechaInicioVigencia != null) ? (Date) fechaInicioVigencia.clone() : null;
    }

    /**
     * @return the fechaFinVigencia
     */
    public Date getFechaFinVigencia() {
        return (fechaFinVigencia != null) ? (Date) fechaFinVigencia.clone() : null;
    }

    /**
     * @param fechaFinVigencia the fechaFinVigencia to set
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = (fechaFinVigencia != null) ? (Date) fechaFinVigencia.clone() : null;
    }

}
