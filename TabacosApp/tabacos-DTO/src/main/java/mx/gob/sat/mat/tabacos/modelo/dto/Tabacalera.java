package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class Tabacalera implements Serializable {

    private static final long serialVersionUID = -3696896513039886065L;
    private Long idTabacalera;
    private String rfc;
    private String razonSocial;
    private String domicilio;
    private Long idTipoUsuario;
    private Date fecRegistro;
    private Date fecCaptura;
    private Long idEstatus;
    private String correoElectronico;
    private String telefono;
    private Date fecMovimiento;
    private Estatus estatus;

    /**
     * @return the idTabacalera
     */
    public Long getIdTabacalera() {
        return idTabacalera;
    }

    /**
     * @param idTabacalera the idTabacalera to set
     */
    public void setIdTabacalera(Long idTabacalera) {
        this.idTabacalera = idTabacalera;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the domicilio
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * @return the idTipoUsuario
     */
    public Long getIdTipoUsuario() {
        return idTipoUsuario;
    }

    /**
     * @param idTipoUsuario the idTipoUsuario to set
     */
    public void setIdTipoUsuario(Long idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    /**
     * @return the fecRegistro
     */
    public Date getFecRegistro() {
        return (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @param fecRegistro the fecRegistro to set
     */
    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @return the fecCaptura
     */
    public Date getFecCaptura() {
        return (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    /**
     * @param fecCaptura the fecCaptura to set
     */
    public void setFecCaptura(Date fecCaptura) {
        this.fecCaptura = (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    /**
     * @return the estatus
     */
    public Estatus getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Date getFecMovimiento() {
        return (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    public void setFecMovimiento(Date fecMovimiento) {
        this.fecMovimiento = (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    /**
     * @return the idEstatus
     */
    public Long getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param idEstatus the idEstatus to set
     */
    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
