package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MMMF
 */
public class Maquina implements Serializable {

    private static final long serialVersionUID = -8633336205863170704L;

    private Long idMaquina;
    private String nomMaquina;
    private Date fecRegistro;
    private Date fecBaja;
    private Long idEstatus;
    private Long idTabacalera;

    public Long getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Long idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getNomMaquina() {
        return nomMaquina;
    }

    public void setNomMaquina(String nomMaquina) {
        this.nomMaquina = nomMaquina;
    }

    public Date getFecRegistro() {
        return (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    public Date getFecBaja() {
        return (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

    public void setFecBaja(Date fecBaja) {
        this.fecBaja = (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Long getIdTabacalera() {
        return idTabacalera;
    }

    public void setIdTabacalera(Long idTabacalera) {
        this.idTabacalera = idTabacalera;
    }

}
