package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author root
 */
public class Solicitud extends SolicitudDTO {

    private static final long serialVersionUID = 8042444703423068543L;

    private Long cantSolicitada;
    private Long cantAutorizada;
    private Date fecSolicitud;
    private Long idTipoContrib;
    private Long idPaisOrigen;
    private Long idTbcProv;

    /**
     * @return the cantSolicitada
     */
    public Long getCantSolicitada() {
        return cantSolicitada;
    }

    /**
     * @param cantSolicitada the cantSolicitada to set
     */
    public void setCantSolicitada(Long cantSolicitada) {
        this.cantSolicitada = cantSolicitada;
    }

    /**
     * @return the cantAutorizada
     */
    public Long getCantAutorizada() {
        return cantAutorizada;
    }

    /**
     * @param cantAutorizada the cantAutorizada to set
     */
    public void setCantAutorizada(Long cantAutorizada) {
        this.cantAutorizada = cantAutorizada;
    }

    /**
     * @return the fecSolicitud
     */
    public Date getFecSolicitud() {
        return (fecSolicitud != null) ? (Date) fecSolicitud.clone() : null;
    }

    /**
     * @param fecSolicitud the fecSolicitud to set
     */
    public void setFecSolicitud(Date fecSolicitud) {
        this.fecSolicitud = (fecSolicitud != null) ? (Date) fecSolicitud.clone() : null;
    }

    /**
     * @return the idTipoContrib
     */
    public Long getIdTipoContrib() {
        return idTipoContrib;
    }

    /**
     * @param idTipoContrib the idTipoContrib to set
     */
    public void setIdTipoContrib(Long idTipoContrib) {
        this.idTipoContrib = idTipoContrib;
    }

    /**
     * @return the idPaisOrigen
     */
    public Long getIdPaisOrigen() {
        return idPaisOrigen;
    }

    /**
     * @param idPaisOrigen the idPaisOrigen to set
     */
    public void setIdPaisOrigen(Long idPaisOrigen) {
        this.idPaisOrigen = idPaisOrigen;
    }

    @Override
    public String toString() {
        return "" + "IDSOLICITUD = " + getIdSolicitud() + ", cantSolicitada = " + cantSolicitada + ", cantAutorizada = " + cantAutorizada + ", fecSolicitud = " + fecSolicitud + ", idTipoContrib = " + idTipoContrib + ", idPaisOrigen = " + idPaisOrigen;
    }

    /**
     * @return the idTbcProv
     */
    public Long getIdTbcProv() {
        return idTbcProv;
    }

    /**
     * @param idTbcProv the idTbcProv to set
     */
    public void setIdTbcProv(Long idTbcProv) {
        this.idTbcProv = idTbcProv;
    }
}
