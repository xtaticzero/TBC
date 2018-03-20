package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.util.FechaUtil;

/**
 *
 * @author root
 */
public class DatosBusqueda implements Serializable {

    private static final long serialVersionUID = -8262260756993002131L;

    private String rfcContribuyente;
    private String rfcProveedor;
    private String marca;
    private String codigo;
    private String loteproduccion;
    private String plantaproduccion;
    private String maquinaproduccion;
    private Date fecha;
    private Date fechaFin;

    private String rfc;
    private String fechaFormateada;

    public DatosBusqueda() {
        super();
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String contribuyente) {
        this.rfcContribuyente = contribuyente;
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setRfcProveedor(String proveedor) {
        this.rfcProveedor = proveedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLoteproduccion() {
        return loteproduccion;
    }

    public void setLoteproduccion(String loteproduccion) {
        this.loteproduccion = loteproduccion;
    }

    public String getPlantaproduccion() {
        return plantaproduccion;
    }

    public void setPlantaproduccion(String plantaproduccion) {
        this.plantaproduccion = plantaproduccion;
    }

    public String getMaquinaproduccion() {
        return maquinaproduccion;
    }

    public void setMaquinaproduccion(String maquinaproduccion) {
        this.maquinaproduccion = maquinaproduccion;
    }

    public Date getFecha() {
        return (fecha != null) ? (Date) fecha.clone() : null;
    }

    public void setFecha(Date fecha) {
        this.fecha = (fecha != null) ? (Date) fecha.clone() : null;
        this.fechaFormateada = this.fecha != null ? FechaUtil.formatFecha(this.fecha, FechaUtil.FORMATO_DEFAULT) : "";
    }

    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    @Override
    public String toString() {
        return "DatosBusqueda{" + "rfcContribuyente=" + rfcContribuyente + ", rfcProveedor=" + rfcProveedor + ", marca=" + marca + ", codigo=" + codigo + ", loteproduccion=" + loteproduccion + ", plantaproduccion=" + plantaproduccion + ", maquinaproduccion=" + maquinaproduccion + ", fecha=" + fecha + ", fechaFin=" + fechaFin + ", rfc=" + rfc + '}';
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

}
