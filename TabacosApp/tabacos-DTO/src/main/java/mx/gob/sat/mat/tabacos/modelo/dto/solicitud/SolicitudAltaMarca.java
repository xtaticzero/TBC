package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudAltaMarca extends SolicitudDTO {

    private static final long serialVersionUID = -3313066753845294310L;

    private Marcas marcas;
    private String idAcuseRecibo;

    public Marcas getMarcas() {
        return marcas;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }

}
