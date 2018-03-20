/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.mat.tabacos.modelo.dao.sql.reportes;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface CRContribuyenteSQL {
    String SQL_SELECT_CONTRIBUYENTE = 
            "SELECT FECMOVIMIENTO, RFC " +
            "FROM TBCH_TABACALERA " + 
            "WHERE IDESTATUS = 1 ";
    
    String SQL_SELECT_CONTRIBUYENTE_OP1 =             
            "AND RFC = ? AND FECMOVIMIENTO BETWEEN ? AND ? " + 
            "ORDER BY FECMOVIMIENTO";
    
    String SQL_SELECT_CONTRIBUYENTE_OP2 =             
            "AND FECMOVIMIENTO BETWEEN ? AND ? ORDER BY FECMOVIMIENTO";
    
    String SQL_SELECT_CONTRIBUYENTE_OP3 =             
            "AND RFC = ? ORDER BY FECMOVIMIENTO";
    
    String SQL_SELECT_CONTRIBUYENTE_PROD = 
            "SELECT FECREGISTRO AS FECMOVIMIENTO, RFC " +
            "FROM TBCP_TABACALERA WHERE IDESTATUS = 1 ";

    String SQL_SELECT_CONTRIBUYENTE_PROD_OP1 = 
            "AND RFC = ? AND FECREGISTRO BETWEEN ? AND ? "+             
            "ORDER BY FECREGISTRO";
    
    String SQL_SELECT_CONTRIBUYENTE_PROD_OP2 =             
            "AND FECREGISTRO BETWEEN ? AND ? ORDER BY FECREGISTRO";
    
    String SQL_SELECT_CONTRIBUYENTE_PROD_OP3 =             
            "AND RFC = ? ORDER BY FECREGISTRO";
    
    String SQL_SELECT_CONTRIBUYENTE_BAJA = 
            "SELECT TBAJ.FECREGISTRO AS FECMOVIMIENTO, TTAB.RFC " +
            "FROM TBCT_BAJA TBAJ, TBCP_TABACALERA TTAB " +
            "WHERE TTAB.IDTABACALERA = TBAJ.IDTABACALERA " +
            "AND TTAB.IDESTATUS = 2 ";
    
    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP1 =             
            "AND TTAB.RFC = ? AND TBAJ.FECREGISTRO BETWEEN ? AND ? " + 
            "ORDER BY TBAJ.FECREGISTRO";  

    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP2 =                         
            "AND TBAJ.FECREGISTRO BETWEEN ? AND ? ORDER BY TBAJ.FECREGISTRO";  
    
    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP3 =                         
            "AND TTAB.RFC = ? ORDER BY TBAJ.FECREGISTRO";  
}