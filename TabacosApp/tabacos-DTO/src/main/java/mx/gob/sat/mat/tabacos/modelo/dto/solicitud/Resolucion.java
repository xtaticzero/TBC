package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author root
 */
public class Resolucion extends SolicitudDTO {

    private static final long serialVersionUID = -2502118388769395600L;

    private Long idResolucion;
    private Long idEstResolucion;
    private Date fecResolucion;
    private Long numServidorPublico;
    private Long folioInicial;
    private Long folioFinal;
    private Date fechaInicio;
    private Date fechaFin;
    private Long idEstCargador;

    /**
     * @return the idResolucion
     */
    public Long getIdResolucion() {
        return idResolucion;
    }

    /**
     * @param idResolucion the idResolucion to set
     */
    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
    }

    /**
     * @return the idEstResolucion
     */
    public Long getIdEstResolucion() {
        return idEstResolucion;
    }

    /**
     * @param idEstResolucion the idEstResolucion to set
     */
    public void setIdEstResolucion(Long idEstResolucion) {
        this.idEstResolucion = idEstResolucion;
    }

    /**
     * @return the fecResolucion
     */
    public Date getFecResolucion() {
        return (fecResolucion != null) ? (Date) fecResolucion.clone() : null;
    }

    /**
     * @param fecResolucion the fecResolucion to set
     */
    public void setFecResolucion(Date fecResolucion) {
        this.fecResolucion = (fecResolucion != null) ? (Date) fecResolucion.clone() : null;
    }

    /**
     * @return the idServidorPublico
     */
    public Long getNumServidorPublico() {
        return numServidorPublico;
    }

    /**
     * @param numServidorPublico the numServidorPublico to set
     */
    public void setNumServidorPublico(Long numServidorPublico) {
        this.numServidorPublico = numServidorPublico;
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    /**
     * @return the idEstCargador
     */
    public Long getIdEstCargador() {
        return idEstCargador;
    }

    /**
     * @param idEstCargador the idEstCargador to set
     */
    public void setIdEstCargador(Long idEstCargador) {
        this.idEstCargador = idEstCargador;
    }

    @Override
    public String toString() {
        return "IdResolucion = " + idResolucion + ", idSolicitud = " + this.getIdResolucion() + ", idEstResolucion = " + idEstResolucion + ", fecResolucion = " + fecResolucion + ", numServidorPublico = " + numServidorPublico + ", folioInicial = " + folioInicial + ", folioFinal = " + folioFinal + ", fechaInicio = " + fechaInicio + ", fechaFin = " + fechaFin + ", idEstCargador = " + idEstCargador;
    }

}
