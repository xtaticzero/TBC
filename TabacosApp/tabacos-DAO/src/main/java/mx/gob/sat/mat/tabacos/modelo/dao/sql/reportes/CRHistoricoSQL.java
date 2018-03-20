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
public interface CRHistoricoSQL {
    String SQL_SELECT_HISTORICO = "SELECT RESOLUCION.IDSOLICITUD AS FOLIO, " + 
            "CONTR.RFC AS RFC, CONTR.RAZONSOCIAL AS CONTRIBUYENTE, " + 
            "SOL.CANTAUTORIZADA AS CANTIDAD, SOL.FECSOLICITUD as FECHA, " + 
            "PROV.RFC as PAS " +
            "FROM TBCP_TABACALERA CONTR, " +
            "TBCT_PROVEEDOR PROV, " +
            "TBCT_SOLICITUD SOL, " +
            "TBCA_TBC_PROV PROV_CONTR, " +
            "TBCT_RESOLUCION RESOLUCION " +
            "WHERE PROV_CONTR.IDTABACALERA = CONTR.IDTABACALERA " +
            "AND PROV_CONTR.IDTBCPROV = SOL.IDTBCPROV " +
            "AND PROV_CONTR.IDPROVEEDOR = PROV.IDPROVEEDOR " +
            "AND RESOLUCION.IDSOLICITUD = SOL.IDSOLICITUD";
}
