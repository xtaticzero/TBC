/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author MMMF
 */
public interface CodigoDao {

    String SQL_SELECT_CODIGO = "SELECT IDCODIGO, IDSOLICITUD, "
            + "FOLIO, FECCAPTURA FROM TBCT_CODIGO WHERE IDCODIGO = ?";

    Codigo buscarPorID(String id) throws DaoException;
}
