package mx.gob.sat.mat.tabacos.modelo.dto.tipo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class TipoContribuyente implements Serializable {

    private static final long serialVersionUID = 7551592474123432521L;

    private Long idTipoContrib;
    private String descTipoContrib;
    private Date fecInicio;
    private Date fecFin;

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
     * @return the descTipoContrib
     */
    public String getDescTipoContrib() {
        return descTipoContrib;
    }

    /**
     * @param descTipoContrib the descTipoContrib to set
     */
    public void setDescTipoContrib(String descTipoContrib) {
        this.descTipoContrib = descTipoContrib;
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
}
