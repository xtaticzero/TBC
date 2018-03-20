package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusRepresentante;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusTipoRepLegal;

/**
 *
 * @author root
 */
public class RepresentanteLegal implements Serializable {
    private static final long serialVersionUID = 302306443867006473L;

    private Long idRepLegal;
    private Long idTabacalera;
    private Long idProveedor;
    private Long idEstatus;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String telefono;
    private Date fecInicio;
    private Date fecFin;
    private Estatus estatus;
    private String rfc;
    private Long idTipoRepLegal;

    private EstatusRepresentante estatusRepresentante = EstatusRepresentante.ACTIVO;
    private EstatusTipoRepLegal estatusTipoRepLegal = EstatusTipoRepLegal.LEGAL;
    
    
    /**
     * @return the idRepLegal
     */
    public Long getIdRepLegal() {
        return idRepLegal;
    }

    /**
     * @param idRepLegal the idRepLegal to set
     */
    public void setIdRepLegal(Long idRepLegal) {
        this.idRepLegal = idRepLegal;
    }

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
     * @return the idProveedor
     */
    public Long getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * @param apellidoPaterno the apellidoPaterno to set
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * @param apellidoMaterno the apellidoMaterno to set
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
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

    /**
     * @return the fecInicio
     */
    public Date getFecInicio() {
        return (fecInicio != null) ? (Date) fecInicio.clone() : null;
    }

    /**
     * @param fecInicio the fecInicio to set
     */
    public void setFecInicio(Date fecInicio) {
        this.fecInicio = (fecInicio != null) ? (Date) fecInicio.clone() : null;
    }

    /**
     * @return the fecFin
     */
    public Date getFecFin() {
        return (fecFin != null) ? (Date) fecFin.clone() : null;
    }

    /**
     * @param fecFin the fecFin to set
     */
    public void setFecFin(Date fecFin) {
        this.fecFin = (fecFin != null) ? (Date) fecFin.clone() : null;
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
     * @return the idTipoRepLegal
     */
    public Long getIdTipoRepLegal() {
        return idTipoRepLegal;
    }

    /**
     * @param idTipoRepLegal the idTipoRepLegal to set
     */
    public void setIdTipoRepLegal(Long idTipoRepLegal) {
        this.idTipoRepLegal = idTipoRepLegal;
    }

    public EstatusRepresentante getEstatusRepresentante() {
        return estatusRepresentante;
    }

    public void setEstatusRepresentante(EstatusRepresentante estatusRepresentante) {
        this.estatusRepresentante = estatusRepresentante;
    }

    public EstatusTipoRepLegal getEstatusTipoRepLegal() {
        return estatusTipoRepLegal;
    }

    public void setEstatusTipoRepLegal(EstatusTipoRepLegal estatusTipoRepLegal) {
        this.estatusTipoRepLegal = estatusTipoRepLegal;
    }
    
    
    
}
