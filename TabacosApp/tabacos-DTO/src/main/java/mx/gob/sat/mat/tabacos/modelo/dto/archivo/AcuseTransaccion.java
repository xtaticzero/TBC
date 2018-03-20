package mx.gob.sat.mat.tabacos.modelo.dto.archivo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public class AcuseTransaccion implements Serializable {

    private static final long serialVersionUID = 8855021381874733063L;

    private String folioTransaccion;
    private String folioAcuse;
    private Date fechaTransaccion;

    /**
     * @return the folioTransaccion
     */
    public String getFolioTransaccion() {
        return folioTransaccion;
    }

    /**
     * @param folioTransaccion the folioTransaccion to set
     */
    public void setFolioTransaccion(String folioTransaccion) {
        this.folioTransaccion = folioTransaccion;
    }

    /**
     * @return the folioAcuse
     */
    public String getFolioAcuse() {
        return folioAcuse;
    }

    /**
     * @param folioAcuse the folioAcuse to set
     */
    public void setFolioAcuse(String folioAcuse) {
        this.folioAcuse = folioAcuse;
    }

    /**
     * @return the fechaTransaccion
     */
    public Date getFechaTransaccion() {
        return (fechaTransaccion != null) ? (Date) fechaTransaccion.clone() : null;
    }

    /**
     * @param fechaTransaccion the fechaTransaccion to set
     */
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = (fechaTransaccion != null) ? (Date) fechaTransaccion.clone() : null;
    }
}
