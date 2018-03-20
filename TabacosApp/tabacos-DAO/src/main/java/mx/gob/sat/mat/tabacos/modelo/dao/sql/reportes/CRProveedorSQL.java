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
public interface CRProveedorSQL {
    String SQL_SELECT_PROVEEDOR = 
            "SELECT FECMOVIMIENTO, RFC " +
            "FROM TBCH_PROVEEDOR " +
            "WHERE IDESTATUS = 1 ";
    
    String SQL_SELECT_PROVEEDOR_OP1 =             
            "AND RFC = ? " +
            "AND FECMOVIMIENTO BETWEEN ? and ? " +
            "ORDER BY FECMOVIMIENTO";
            
    
    String SQL_SELECT_PROVEEDOR_OP2 =             
            "AND FECMOVIMIENTO BETWEEN ? AND ? " +
            "ORDER BY FECMOVIMIENTO";
    
    String SQL_SELECT_PROVEEDOR_OP3 =             
            "AND RFC = ? " +
            "ORDER BY FECMOVIMIENTO";
    
    String SQL_SELECT_PROVEEDOR_PROD = 
            "SELECT FECREGISTRO AS FECMOVIMIENTO, RFC " +
            "FROM TBCT_PROVEEDOR " +
            "WHERE IDESTATUS = 1 ";
    
    String SQL_SELECT_PROVEEDOR_OP1_PROD =             
            "AND RFC = ? " +
            "AND FECREGISTRO BETWEEN ? and ? " +
            "ORDER BY FECREGISTRO";            
    
    String SQL_SELECT_PROVEEDOR_OP2_PROD =             
            "AND FECREGISTRO BETWEEN ? AND ? " +
            "ORDER BY FECREGISTRO";
    
    String SQL_SELECT_PROVEEDOR_BAJA = 
            "SELECT TBAJ.FECREGISTRO AS FECMOVIMIENTO, TTAB.RFC " +
            "FROM TBCT_BAJA TBAJ, TBCP_TABACALERA TTAB, TBCT_PROVEEDOR TPRO " +
            "WHERE TBAJ.IDTABACALERA = TTAB.IDTABACALERA" + 
            "AND TBAJ.IDPROVEEDOR = TPRO.IDPROVEEDOR " +
            "AND TPRO.IDESTATUS = 2 ";
    
    String SQL_SELECT_PROVEEDOR_BAJA_OP1 =             
            "AND TTAB.RFC = ? " +
            "AND TBAJ.FECREGISTRO BETWEEN ? AND ? " + 
            "AND ORDER BY TBAJ.FECREGISTRO ";  
               
    
    String SQL_SELECT_PROVEEDOR_BAJA_OP2 =                         
            "AND TBAJ.FECREGISTRO BETWEEN ? AND ? " + 
            "AND ORDER BY TBAJ.FECREGISTRO ";  
    
    String SQL_SELECT_PROVEEDOR_BAJA_OP3 =                         
            "AND TTAB.RFC = ? " + 
            "AND ORDER BY TBAJ.FECREGISTRO ";  
                
}