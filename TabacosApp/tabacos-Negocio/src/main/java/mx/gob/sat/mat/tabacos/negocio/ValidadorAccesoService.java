package mx.gob.sat.mat.tabacos.negocio;

import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.excepcion.AccesoNoPermitidoException;


public interface ValidadorAccesoService {
    String UNAUTHORIZED_ACCES="Acceso no permitido";
    ValidarAccesoRespuesta validarAcceso(String rfc)throws AccesoNoPermitidoException;
}
