package mx.gob.sat.mat.tabacos.modelo.dto.marca;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class Marcas implements Serializable, Cloneable {

    private static final long serialVersionUID = 2593538720999259409L;

    private String rfc;
    private Long idMarca;
    private String marca;
    private Date fechaInicio;
    private Date fechaFin;
    private Long idEstatus;
    private String clave;
    private String rutaArchivo;
    private Long idTabacalera;
    private Date fecMovimiento;
    private String motivoBaja;
    private String fechaAcuse;
    private String estatusDesc;
    private String razonSocial;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc.toUpperCase();
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaInicio() {
        return (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFecMovimiento() {
        return (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    public void setFecMovimiento(Date fecMovimiento) {
        this.fecMovimiento = (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    /**
     * @return the rutaArchivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @param rutaArchivo the rutaArchivo to set
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getFechaAcuse() {
        return fechaAcuse;
    }

    public void setFechaAcuse(String fechaAcuse) {
        this.fechaAcuse = fechaAcuse;
    }

    public String getEstatusDesc() {
        return estatusDesc;
    }

    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Override
    public Marcas clone() throws CloneNotSupportedException {
        super.clone();
        Marcas marcaClonada;
        marcaClonada = new Marcas();

        marcaClonada.setRfc(rfc);
        marcaClonada.setIdMarca(idMarca);
        marcaClonada.setMarca(marca);
        marcaClonada.setFechaInicio(fechaInicio);
        marcaClonada.setFechaFin(fechaFin);
        marcaClonada.setIdEstatus(idEstatus);
        marcaClonada.setClave(clave);
        marcaClonada.setRutaArchivo(rutaArchivo);
        marcaClonada.setIdTabacalera(idTabacalera);
        marcaClonada.setFecMovimiento(fecMovimiento);
        marcaClonada.setMotivoBaja(motivoBaja);
        marcaClonada.setFechaAcuse(fechaAcuse);
        marcaClonada.setEstatusDesc(estatusDesc);
        marcaClonada.setRazonSocial(razonSocial);

        return marcaClonada;

    }

}
