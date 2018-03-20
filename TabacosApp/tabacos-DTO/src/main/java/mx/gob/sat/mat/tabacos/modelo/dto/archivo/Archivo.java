package mx.gob.sat.mat.tabacos.modelo.dto.archivo;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author root
 */
public class Archivo extends SolicitudDTO implements Serializable {

    private static final long serialVersionUID = 8382534976370489179L;

    private Long idArchivo;
    private String rutaArchivo;
    private Date fecRegistro;
    private Long numLinea;
    private Date fecInicio;
    private Date fecFin;
    private String extension;
    private String numeroOficio;
    private Long idTipoArchivo;

    /**
     * @return the idArchivo
     */
    public Long getIdArchivo() {
        return idArchivo;
    }

    /**
     * @param idArchivo the idArchivo to set
     */
    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    /**
     * @return the rutaArchivo
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * @param rutaArchivo the rutaArchivo to set
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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
     * @return the numLinea
     */
    public Long getNumLinea() {
        return numLinea;
    }

    /**
     * @param numLinea the numLinea to set
     */
    public void setNumLinea(Long numLinea) {
        this.numLinea = numLinea;
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

    public String getNombreArchivo() {
        String[] segmentos;
        if (rutaArchivo != null) {
            segmentos = rutaArchivo.split("/");
            if (segmentos.length == 0) {
                segmentos = rutaArchivo.split("\\\\");
            }
            if ((segmentos != null) && (segmentos.length > 0)) {
                return segmentos[segmentos.length - 1];
            }
        }
        return null;
    }

    public String getExtension() {
        String[] fileExtension = rutaArchivo.split("\\.");
        if (fileExtension.length > 1) {
            extension = "." + fileExtension[fileExtension.length - 1];
        }
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public void setIdTipoArchivo(Long idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }

    public Long getIdTipoArchivo() {
        return idTipoArchivo;
    }
}
