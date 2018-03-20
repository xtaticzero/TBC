package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ReporteProduccion implements Serializable {

    private static final long serialVersionUID = -4674146422734447845L;

    private String rfc;
    private Date fechaInicio;
    private Date fechaFin;
    private Long idProveedor;
    private Long idContribuyente;
    private String loteProduccion;
    private String plantaProduccion;
    private Integer cantidadProduccion;
    private Long idPaisProduccion;
    private String maquinaProduccion;
    private Long idOrigen;
    private Integer numeroProduccion;
    private String nombrePais;
    private String lineaProduccion;
    private String razonSocialContribuyente;
    private String razonSocialProveedor;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getIdContribuyente() {
        return idContribuyente;
    }

    public void setIdContribuyente(Long idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public String getLoteProduccion() {
        return loteProduccion;
    }

    public void setLoteProduccion(String loteProduccion) {
        this.loteProduccion = loteProduccion;
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

    public Long getIdPaisProduccion() {
        return idPaisProduccion;
    }

    public void setIdPaisProduccion(Long idPaisProduccion) {
        this.idPaisProduccion = idPaisProduccion;
    }

    public String getMaquinaProduccion() {
        return maquinaProduccion;
    }

    public void setMaquinaProduccion(String maquinaProduccion) {
        this.maquinaProduccion = maquinaProduccion;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Integer getNumeroProduccion() {
        return numeroProduccion;
    }

    public void setNumeroProduccion(Integer numeroProduccion) {
        this.numeroProduccion = numeroProduccion;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getLineaProduccion() {
        return lineaProduccion;
    }

    public void setLineaProduccion(String lineaProduccion) {
        this.lineaProduccion = lineaProduccion;
    }

    public String getRazonSocialContribuyente() {
        return razonSocialContribuyente;
    }

    public void setRazonSocialContribuyente(String razonSocialContribuyente) {
        this.razonSocialContribuyente = razonSocialContribuyente;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

}
