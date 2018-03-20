package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class SolicitudesRegistradas extends SolicitudDTO {

    private static final long serialVersionUID = -3714343450081667946L;

    private String folio;
    private String rfc;
    private String razonSocial;
    private long cantCodigos;
    private String fecha;
    private long enProceso;
    private String estadoResolicion;
    private String marca;
    private String clave;

    /**
     *
     */
    public SolicitudesRegistradas() {
        this.folio = "";
        this.rfc = "";
        this.razonSocial = "";
        this.cantCodigos = 0L;
        this.fecha = "";
        this.enProceso = 0L;
        this.marca = "";
        this.clave = "";
    }

    /**
     *
     * @param folio
     * @param rfc
     * @param razonSocial
     * @param cantCodigos
     * @param fecha
     * @param enProceso
     */
    public SolicitudesRegistradas(String folio, String rfc, String razonSocial,
            long cantCodigos, String fecha, long enProceso) {
        this.folio = folio;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.cantCodigos = cantCodigos;
        this.fecha = fecha;
        this.enProceso = enProceso;
        this.marca = "";
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
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     *
     * @param razonSocial
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     *
     * @return
     */
    public long getCantCodigos() {
        return cantCodigos;
    }

    /**
     *
     * @param cantCodigos
     */
    public void setCantCodigos(long cantCodigos) {
        this.cantCodigos = cantCodigos;
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
    public long getEnProceso() {
        return enProceso;
    }

    /**
     *
     * @param enProceso
     */
    public void setEnProceso(long enProceso) {
        this.enProceso = enProceso;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstadoResolicion() {
        return estadoResolicion;
    }

    public void setEstadoResolicion(String estadoResolicion) {
        this.estadoResolicion = estadoResolicion;
    }

}
