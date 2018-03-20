package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudResolucion extends SolicitudDTO {

    private static final long serialVersionUID = 5711329611775948176L;

    private String rfcTavacalera;
    private String rfcProveedor;
    private Long cantidadCodigos;
    private Long cantidadAutorizada;
    private Date fechaSolicitud;
    private Long idPaisOrigen;
    private String estatus;
    private Date fechaResolucion;
    private String paisOrigen;

    public String getRfcTavacalera() {
        return rfcTavacalera;
    }

    public void setRfcTavacalera(String rfcTavacalera) {
        this.rfcTavacalera = rfcTavacalera;
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor;
    }

    public Long getCantidadCodigos() {
        return cantidadCodigos;
    }

    public void setCantidadCodigos(Long cantidadCodigos) {
        this.cantidadCodigos = cantidadCodigos;
    }

    public Date getFechaSolicitud() {
        return (fechaSolicitud != null) ? (Date) fechaSolicitud.clone() : null;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = (fechaSolicitud != null) ? (Date) fechaSolicitud.clone() : null;
    }

    public Long getIdPaisOrigen() {
        return idPaisOrigen;
    }

    public void setIdPaisOrigen(Long idPaisOrigen) {
        this.idPaisOrigen = idPaisOrigen;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaResolucion() {
        return (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public Long getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    public void setCantidadAutorizada(Long cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
}
