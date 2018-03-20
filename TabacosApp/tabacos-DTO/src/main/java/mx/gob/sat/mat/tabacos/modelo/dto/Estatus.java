package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class Estatus implements Serializable {

    private static final long serialVersionUID = -8006077507957168808L;

    private Long idEstatus;
    private String nomEstatus;
    private Date fecInicio;
    private Date fecFin;

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

    /**
     * @return the nomEstatus
     */
    public String getNomEstatus() {
        return nomEstatus;
    }

    /**
     * @param nomEstatus the nomEstatus to set
     */
    public void setNomEstatus(String nomEstatus) {
        this.nomEstatus = nomEstatus;
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
