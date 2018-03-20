package mx.gob.sat.mat.tabacos.modelo.dto.codigos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public class CodigoInvalido implements Serializable {
    private static final long serialVersionUID = 7576612282865219875L;
    
    private Long idCodInvalido;
    private Long idRangoFolio;
    private Long folioInicial;
    private Long folioFinal;
    private Date fecRegistro;
    private Date fecCaptura;
    private String justificacion;

    /**
     * @return the idCodInvalido
     */
    public Long getIdCodInvalido() {
        return idCodInvalido;
    }

    /**
     * @param idCodInvalido the idCodInvalido to set
     */
    public void setIdCodInvalido(Long idCodInvalido) {
        this.idCodInvalido = idCodInvalido;
    }

    /**
     * @return the idRangoFolio
     */
    public Long getIdRangoFolio() {
        return idRangoFolio;
    }

    /**
     * @param idRangoFolio the idRangoFolio to set
     */
    public void setIdRangoFolio(Long idRangoFolio) {
        this.idRangoFolio = idRangoFolio;
    }

    /**
     * @return the folioInicial
     */
    public Long getFolioInicial() {
        return folioInicial;
    }

    /**
     * @param folioInicial the folioInicial to set
     */
    public void setFolioInicial(Long folioInicial) {
        this.folioInicial = folioInicial;
    }

    /**
     * @return the folioFinal
     */
    public Long getFolioFinal() {
        return folioFinal;
    }

    /**
     * @param folioFinal the folioFinal to set
     */
    public void setFolioFinal(Long folioFinal) {
        this.folioFinal = folioFinal;
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
     * @return the fecCaptura
     */
    public Date getFecCaptura() {
        return (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    /**
     * @param fecCaptura the fecCaptura to set
     */
    public void setFecCaptura(Date fecCaptura) {
        this.fecCaptura = (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    /**
     * @return the justificacion
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * @param justificacion the justificacion to set
     */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}
