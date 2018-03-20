/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;

/**
 *
 * @author MMMF
 */
public interface CodigosFalsoseInvalidosService {

    int guardarCodigoFalso(Codigo codigo) throws CodigosFalsoseInvalidosException;

    int guardarCodigoInvalido(Codigo codigo) throws CodigosFalsoseInvalidosException;

    Codigo buscarPorId(String numCodigo) throws CodigosFalsoseInvalidosException;

    List<Codigo> buscarCodigoInvPorRangoFolio(String rfc, Long folioInicial, Long folioFinal) throws CodigosFalsoseInvalidosException;

    List<Codigo> getCodigosInvalidos(ReportesFiltroBase filtro) throws CodigosFalsoseInvalidosException;

    List<Codigo> getCodigosFalsos(ReportesFiltroBase filtro) throws CodigosFalsoseInvalidosException;
}
