/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TipoContribuyenteDaoException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface TipoContribuyenteDao {
    List<TipoContribuyente> getLstTipoContribuyente() throws TipoContribuyenteDaoException;
}
