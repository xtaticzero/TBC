package mx.gob.sat.mat.tabacos.modelo.dto.codigos;

import java.io.Serializable;
import java.math.BigInteger;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class CodigoGenerado extends SolicitudDTO implements Serializable{
    private static final long serialVersionUID = -6431343717698909962L;
    
    private BigInteger  folio;
    private String      codigo;
    
    public CodigoGenerado() {
        this.folio = null;
        this.codigo = "";
        setIdSolicitud(0L);
    }    
    
    public BigInteger getFolio() {
        return folio;
    }

    public void setFolio(BigInteger folio) {
        this.folio = folio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }  
}
