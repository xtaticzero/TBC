package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusProveedor;

/**
 *
 * @author root
 */
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 7798509990469305468L;

    private Long idProveedor;
    private String rfc;
    private String razonSocial;
    private String domicilio;
    private Date fecRegistro;
    private Date fecCaptura;
    private Date fecMovimiento;
    private Long idEstatus;
    private Estatus estatus;

    private String telefono;
    private String email;
    private String representanteLegal;
    private String rfcCliente;
    private String marcasCliente;
    private String motivoBaja;
    private EstatusProveedor estatusProv = EstatusProveedor.ACTIVO;

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

    /**
     *
     * @return
     */
    public Date getFecMovimiento() {
        return (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    /**
     *
     * @param fecMovimiento
     */
    public void setFecMovimiento(Date fecMovimiento) {
        this.fecMovimiento = (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public String getMarcasCliente() {
        return marcasCliente;
    }

    public void setMarcasCliente(String marcasCliente) {
        this.marcasCliente = marcasCliente;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public EstatusProveedor getEstatusProv() {
        return estatusProv;
    }

    public void setEstatusProv(EstatusProveedor estatusProv) {
        this.estatusProv = estatusProv;
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

}
