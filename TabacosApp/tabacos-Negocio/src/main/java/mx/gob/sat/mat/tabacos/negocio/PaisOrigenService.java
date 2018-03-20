package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public interface PaisOrigenService {

    List<PaisOrigen> selectedPais() throws ServiceException;

    List<PaisOrigen> selectedOrigen() throws ServiceException;
    
}
