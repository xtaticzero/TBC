/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;

/**
 *
 * @author MMMF
 */
public interface TratamientoRangoFolioService {

    long obtenerNumeroFoliosCancelados(final List<RangoFolio> rangoFoliosCancelados);

    boolean validaExistenciaCodigosInvalidos(
            final RangoFolio rango,
            String rfc) throws BusinessException;
}
