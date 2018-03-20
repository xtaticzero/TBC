package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class RepRegistro implements Serializable {

    private static final long serialVersionUID = 5681791988313370012L;

    private String fecha;
    private String lote;
    private String planta;
    private String cantidad;
    private String proveedor;
    private String contribuyente;
    private String pais;
    private String maquinaria;
    private String origen;
    private String linea;
    private String solicitud;

    /**
     *
     */
    public RepRegistro() {
        this.fecha = "";
        this.solicitud = "";
        this.lote = "";
        this.planta = "";
        this.cantidad = "";
        this.proveedor = "";
        this.contribuyente = "";
        this.pais = "";
        this.maquinaria = "";
        this.origen = "";
        this.linea = "";
    }

    /**
     *
     * @return
     */
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public String getSolicitud() {
        return solicitud;
    }

    /**
     *
     * @param solicitud
     */
    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    /**
     *
     * @return
     */
    public String getLote() {
        return lote;
    }

    /**
     *
     * @param lote
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     *
     * @return
     */
    public String getPlanta() {
        return planta;
    }

    /**
     *
     * @param planta
     */
    public void setPlanta(String planta) {
        this.planta = planta;
    }

    /**
     *
     * @return
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param cantidad
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    /**
     *
     * @return
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     *
     * @param proveedor
     */
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    /**
     *
     * @return
     */
    public String getContribuyente() {
        return contribuyente;
    }

    /**
     *
     * @param contribuyente
     */
    public void setContribuyente(String contribuyente) {
        this.contribuyente = contribuyente;
    }

    /**
     *
     * @return
     */
    public String getPais() {
        return pais;
    }

    /**
     *
     * @param pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     *
     * @return
     */
    public String getMaquinaria() {
        return maquinaria;
    }

    /**
     *
     * @param maquinaria
     */
    public void setMaquinaria(String maquinaria) {
        this.maquinaria = maquinaria;
    }

    /**
     *
     * @return
     */
    public String getOrigen() {
        return origen;
    }

    /**
     *
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     *
     * @return
     */
    public String getLinea() {
        return linea;
    }

    /**
     *
     * @param linea
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }
}
