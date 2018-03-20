package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class Planta implements Serializable {

    private static final long serialVersionUID = 466906716291925430L;

    private Long idPlanta;
    private String nombrePlanta;

    /**/
    private Long idTabacalera;
    private String nomPlanta;
    private Date fecRegistro;
    private Date fecBaja;
    private Long idEstatus;

    public Long getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Long idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
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

    /**
     * @return the nomPlanta
     */
    public String getNomPlanta() {
        return nomPlanta;
    }

    /**
     * @param nomPlanta the nomPlanta to set
     */
    public void setNomPlanta(String nomPlanta) {
        this.nomPlanta = nomPlanta;
    }

    /**
     * @return the fecRegistro
     */
    public Date getFecRegistro() {
        return (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @param fecRegistro the fecRegistro to set
     */
    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @return the fecBaja
     */
    public Date getFecBaja() {
        return (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

    /**
     * @param fecBaja the fecBaja to set
     */
    public void setFecBaja(Date fecBaja) {
        this.fecBaja = (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

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

}
