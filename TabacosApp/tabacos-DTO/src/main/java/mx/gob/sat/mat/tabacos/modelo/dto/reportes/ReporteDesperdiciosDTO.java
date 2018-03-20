package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ReporteDesperdiciosDTO implements Serializable {

    private static final long serialVersionUID = -1477472141918452694L;

    private String rfc;
    private String razonSocial;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaDescarga;
    private String producto;
    private String plantaProduccion;
    private Integer cantidadProduccion;
    private Long idOrigen;
    private String maquinaProduccion;
    private String loteProduccion;
    private Date fechaHora;
    private Integer cantidadDestruccion;
    private Integer numeroRegistro;

    private String nombrePais;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public Date getFechaDescarga() {
        return (fechaDescarga != null) ? (Date) fechaDescarga.clone() : null;
    }

    public void setFechaDescarga(Date fechaDescarga) {
        this.fechaDescarga = (fechaDescarga != null) ? (Date) fechaDescarga.clone() : null;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPlantaProduccion() {
        return plantaProduccion;
    }

    public void setPlantaProduccion(String plantaProduccion) {
        this.plantaProduccion = plantaProduccion;
    }

    public Integer getCantidadProduccion() {
        return cantidadProduccion;
    }

    public void setCantidadProduccion(Integer cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getMaquinaProduccion() {
        return maquinaProduccion;
    }

    public void setMaquinaProduccion(String maquinaProduccion) {
        this.maquinaProduccion = maquinaProduccion;
    }

    public String getLoteProduccion() {
        return loteProduccion;
    }

    public void setLoteProduccion(String loteProduccion) {
        this.loteProduccion = loteProduccion;
    }

    public Date getFechaHora() {
        return (fechaHora != null) ? (Date) fechaHora.clone() : null;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = (fechaHora != null) ? (Date) fechaHora.clone() : null;
    }

    public Integer getCantidadDestruccion() {
        return cantidadDestruccion;
    }

    public void setCantidadDestruccion(Integer cantidadDestruccion) {
        this.cantidadDestruccion = cantidadDestruccion;
    }

    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

}
