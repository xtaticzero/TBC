package mx.gob.sat.mat.tabacos.modelo.dto.archivo;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author root
 */
public class Acuse extends SolicitudDTO implements Serializable {

    private static final long serialVersionUID = -3252701085555507574L;
    private String serieAcuse;
    private Long folioAcuse;
    private Long idProveedor;
    private Long idMarca;
    private String selloDigital;
    private String cadenaOriginal;
    private Date fecCaptura;

    /**
     * @return the serieAcuse
     */
    public String getSerieAcuse() {
        return serieAcuse;
    }

    /**
     * @param tipoAcuse the tipoAcuse to set
     */
    public void setSerieAcuse(TipoAcuse tipoAcuse) {
        this.serieAcuse = tipoAcuse.getKey();
    }

    /**
     * @return the folioAcuse
     */
    public Long getFolioAcuse() {
        return folioAcuse;
    }

    /**
     * @param folioAcuse the folioAcuse to set
     */
    public void setFolioAcuse(Long folioAcuse) {
        this.folioAcuse = folioAcuse;
    }

    /**
     * @return the selloDigital
     */
    public String getSelloDigital() {
        return selloDigital;
    }

    /**
     * @param selloDigital the selloDigital to set
     */
    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    /**
     * @return the cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * @param cadenaOriginal the cadenaOriginal to set
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
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
     *
     * @return idProveedor
     */
    public Long getIdProveedor() {
        return idProveedor;
    }

    /**
     *
     * @param idProveedor
     */
    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     *
     * @return Long IdMarca
     */
    public Long getIdMarca() {
        return idMarca;
    }

    /**
     *
     * @param idMarca
     */
    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

}
