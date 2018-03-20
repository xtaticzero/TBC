/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.Date;
import javax.servlet.http.HttpSession;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface BitacoraTbcService {
    void registroMovimientoBitacora(HttpSession session, IdentificadorProcesoEnum identificadorModulo, Date fechaSesion, Date fechaTramite, MovimientosBitacoraEnum mov);    
}
