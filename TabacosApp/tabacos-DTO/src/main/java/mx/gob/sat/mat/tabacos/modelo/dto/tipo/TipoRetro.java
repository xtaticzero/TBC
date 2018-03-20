package mx.gob.sat.mat.tabacos.modelo.dto.tipo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class TipoRetro implements Serializable {

    private static final long serialVersionUID = 7688081437199606728L;

    private Long idTipoRetro;
    private String descTipoRetro;
    private Date fecInicio;
    private Date fecFinal;

    public Long getIdTipoRetro() {
        return idTipoRetro;
    }

    public void setIdTipoRetro(Long idTipoRetro) {
        this.idTipoRetro = idTipoRetro;
    }

    public String getDescTipoRetro() {
        return descTipoRetro;
    }

    public void setDescTipoRetro(String descTipoRetro) {
        this.descTipoRetro = descTipoRetro;
    }

    public Date getFecInicio() {
        return (fecInicio != null) ? (Date) fecInicio.clone() : null;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = (fecInicio != null) ? (Date) fecInicio.clone() : null;
    }

    public Date getFecFinal() {
        return (fecFinal != null) ? (Date) fecFinal.clone() : null;
    }

    public void setFecFinal(Date fecFinal) {
        this.fecFinal = (fecFinal != null) ? (Date) fecFinal.clone() : null;
    }

}
