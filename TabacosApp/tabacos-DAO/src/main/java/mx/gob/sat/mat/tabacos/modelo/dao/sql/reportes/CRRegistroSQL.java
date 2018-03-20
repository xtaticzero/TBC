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
public interface CRRegistroSQL {

    String SQL_SELECT_REGISTRO = "SELECT * " +
            "FROM (" +
            "SELECT DISTINCT " +
            "TTAB.IDTABACALERA, " +
            "TTAB.RFC, " +
            "TTAB.RAZONSOCIAL AS TABACALERA, " +
            "TTP.IDPROVEEDOR, " +
            "TPRV.RAZONSOCIAL AS PROVEEDOR, " +
            "TSOL.IDSOLICITUD, " +
            "TPC.NUMLOTE, " +
            "TPLA.NOMPLANTA, " +
            "TPC.CANTPRODUCIDA, " +
            "TPC.IDPAISPRODUCCION AS PAIS, " +
            "TPC.DESCMAQUINAPROD, " +
            "TPC.IDPAISORIGEN AS ORIGEN, " +
            "TPC.LINEAPRODUCCION, " +
            "TO_CHAR (TPC.FECPRODUCCION, 'DD/MM/YYYY/ HH24:MI:SS') AS FECPRODUCCION, " +
            "TPC.IDTIPORETRO " +
            "FROM " +
            "TBCT_PRODCIGARRO TPC, TBCT_RANGOFOLIO TRF, " +
            "TBCT_SOLICITUD TSOL, TBCP_TABACALERA TTAB, " +
            "TBCT_PROVEEDOR TPRV, TBCT_PLANTA TPLA, " +
            "TBCT_RESOLUCION TRES, TBCA_TBC_PROV TTP, " +
            "TBCT_MARCA MAR " +
            "WHERE TPC.IDPRODCIGARRO = TRF.IDPRODCIGARRO " +
            "AND MAR.IDMARCA = TPC.IDMARCA " +
            "AND TPLA.IDPLANTA = TPC.IDPLANTA " +
            "AND TPLA.IDTABACALERA = TTAB.IDTABACALERA " +
            "AND TRF.IDRESOLUCION = TRES.IDRESOLUCION " +
            "AND TRES.IDSOLICITUD = TSOL.IDSOLICITUD " +
            "AND TSOL.IDTBCPROV = TTP.IDTBCPROV " +
            "AND TPRV.IDPROVEEDOR = TTP.IDPROVEEDOR " +
            "AND TTP.IDTABACALERA = TTAB.IDTABACALERA " +            
            "AND TPC.IDTIPORETRO IS NULL ";
    
    String SQL_SELECT_REGISTRO_OP1 =             
        "AND TPC.FECPRODUCCION BETWEEN ? AND ? ";
                
    String SQL_SELECT_REGISTRO_OP2 =             
        "AND TTAB.RFC = ? ";            
    
    String SQL_SELECT_REGISTRO_OP3 = 
        "AND TPC.FECPRODUCCION BETWEEN ? AND ? AND TTAB.RFC = ? ";
    
    String SQL_ORDER_BY = ") ORDER BY FECPRODUCCION"; 
    
    String SQL_SELECT_REGISTRO_OP4 = 
            "AND TTAB.IDTABACALERA = ? " +
            "AND TTP.IDPROVEEDOR = ? ";
    
    String SQL_SELECT_REGISTRO_OP5 = 
            "AND TTAB.IDTABACALERA = ? ";
    
    String SQL_SELECT_REGISTRO_OP6 = 
            "AND TTP.IDPROVEEDOR = ? ";    
}
