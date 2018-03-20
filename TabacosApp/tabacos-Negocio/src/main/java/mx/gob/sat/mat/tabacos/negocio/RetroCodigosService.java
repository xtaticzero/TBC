/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RetroCodigosException;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public interface RetroCodigosService {

    String guardarProduccion(Map<ProduccionCigarros, List<RangoFolio>> mapProduccion) throws RetroCodigosException;

    String guardarDestruccion(Map<ProduccionCigarros, List<RangoFolio>> mapDestruccion) throws RetroCodigosException;

    String guardarInvalido(Map<CodigoInvalido, List<RangoFolio>> mapInvalido) throws RetroCodigosException;
}
