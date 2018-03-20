/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author MMMF
 */
public interface CodigoFalsoDao {
    String SQL_SELECT_NEXTID_CODIGOFALSO = "SELECT TBCQ_CODIGOFALSO.NEXTVAL FROM DUAL";
    
    String INSERT_CODIGOFALSO = "INSERT INTO TBCT_CODIGOFALSO (IDCODIGOFALSO,"
            + " IDMARCA, FECREGISTRO, JUSTIFICACION, CODIGO) VALUES (?,?,?,?,?)";
    
    String SQL_SELECT_FALSOS_SINMARCA = "SELECT TCFAL.FECREGISTRO, TCFAL.CODIGO "
            + "FROM TBCT_CODIGOFALSO TCFAL "
            + "WHERE TCFAL.FECREGISTRO BETWEEN ? AND ? ORDER BY TCFAL.FECREGISTRO";
    
    String SQL_SELECT_FALSOS = 
            "SELECT TCFAL.FECREGISTRO, TTAB.RFC, TCFAL.CODIGO " +
            "FROM TBCT_CODIGOFALSO TCFAL, TBCT_MARCA TMAR, TBCP_TABACALERA TTAB " +
            "WHERE TCFAL.IDMARCA = TMAR.IDMARCA " +
            "AND TMAR.IDTABACALERA = TTAB.IDTABACALERA ";

    String SQL_SELECT_FALSOS_OP1 = "AND TCFAL.FECREGISTRO BETWEEN ? AND ? " +
            "ORDER BY TTAB.RFC, TCFAL.FECREGISTRO";
    
    String SQL_SELECT_FALSOS_OP2 = "AND TTAB.RFC = ? ORDER BY TCFAL.FECREGISTRO";
    
    String SQL_SELECT_FALSOS_OP3 =  "AND TTAB.RFC = ? " + 
            "AND TCFAL.FECREGISTRO BETWEEN ? AND ? ORDER BY TCFAL.FECREGISTRO";
    
    int guardar (Codigo codigo) throws DaoException;
    List<Codigo> getCodigosFalsos(ReportesFiltroBase filtro) throws DaoException;
    
}
