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
public interface CRCodigoSQL {
    String SQL_SELECT_CODIGO = 
            "SELECT TCOD.FECREGISTRO, TCOD.IDRANGOFOLIO, TCOD.FOLIOINICIAL, " + 
            "TCOD.FOLIOFINAL,TTAB.RFC " +
            "FROM " +
            "TBCT_CODINVALIDO TCOD, " +
            "TBCT_RANGOFOLIO TRF, " +
            "TBCT_SOLICITUD TSOL, " +
            "TBCP_TABACALERA TTAB, " +
            "TBCT_RESOLUCION TRES, " +
            "TBCA_TBC_PROV TTP " +
            "WHERE TCOD.IDRANGOFOLIO = TRF.IDRANGOFOLIO " +
            "AND TRF.IDRESOLUCION = TRES.IDRESOLUCION " +
            "AND TRES.IDSOLICITUD = TSOL.IDSOLICITUD " +
            "AND TSOL.IDTBCPROV = TTP.IDTBCPROV " +
            "AND TTP.IDTABACALERA = TTAB.IDTABACALERA ";

    String SQL_SELECT_CODIGO_OP1 = "AND TCOD.FECREGISTRO BETWEEN ? AND ? " + 
            "ORDER BY TTAB.RFC, TCOD.FECREGISTRO";

    String SQL_SELECT_CODIGO_OP2 = "AND TTAB.RFC = ? ORDER BY TCOD.FECREGISTRO";

    String SQL_SELECT_CODIGO_OP3 = "AND TTAB.RFC = ? " +
            "AND TCOD.FECREGISTRO BETWEEN ? AND ? ORDER BY TCOD.FECREGISTRO";
    
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

}
