package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class Baja implements Serializable {

    private static final long serialVersionUID = -7035329344710956699L;

    private Long idBaja;
    private Long idTabacalera;
    private Long idProveedor;
    private Long idMarca;
    private String descMotivoBaja;
    private Date fecRegistro;
    private Date fecBaja;

    public Long getIdBaja() {
        return idBaja;
    }

    public void setIdBaja(Long idBaja) {
        this.idBaja = idBaja;
    }

    public Long getIdTabacalera() {
        return idTabacalera;
    }

    public void setIdTabacalera(Long idTabacalera) {
        this.idTabacalera = idTabacalera;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescMotivoBaja() {
        return descMotivoBaja;
    }

    public void setDescMotivoBaja(String descMotivoBaja) {
        this.descMotivoBaja = descMotivoBaja;
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
}
