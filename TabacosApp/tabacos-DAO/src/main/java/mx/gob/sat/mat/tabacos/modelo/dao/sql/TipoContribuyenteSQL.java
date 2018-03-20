/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.sql;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface TipoContribuyenteSQL {
    String SQL_SELECT_TIPOCONTRIB = "SELECT IDTIPOCONTRIB,DESCTIPOCONTRIB,FECINICIO,FECFINAL FROM TBCC_TIPOCONTRIB ORDER BY DESCTIPOCONTRIB";
}
