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
public interface CRMarcaSQL {

    String AND_BETWEEN_TMAR_FECINICIO = "AND TMAR.FECINICIO BETWEEN ? AND ? ";
    String AND_TMAR_IDMARCA = "AND TMAR.IDMARCA = ? ";
    String AND_TTAB_RFC = " AND TTAB.RFC = ? ";

    /**
     * Altas.
     */
    String SQL_SELECT_MARCA_PROD
            = "SELECT \n"
            + "  TMAR.FECINICIO AS FECMOVIMIENTO, \n"
            + "  TMAR.NOMMARCA \n"
            + "FROM TBCT_MARCA TMAR\n"
            + "  JOIN TBCP_TABACALERA TTAB \n"
            + "    ON TMAR.IDTABACALERA = TTAB.IDTABACALERA\n"
            + "    AND TMAR.IDESTATUS = 1\n"
            + "WHERE 1=1 ";

    String ORDER_BY_TMAR_FECINICIO = " ORDER BY TMAR.FECINICIO";

    /**
     * Modificacion.
     */
    String SELECT_MODIFICACION_MARCA = "SELECT \n"
            + "  TMAR.FECMOVIMIENTO, \n"
            + "  TMAR.NOMMARCA \n"
            + "FROM TBCH_MARCA TMAR\n"
            + "  JOIN TBCH_TABACALERA TTAB \n"
            + "      ON TMAR.IDTABACALERA = TTAB.IDTABACALERA \n"
            + "      AND TMAR.IDESTATUS = 1\n"
            + "WHERE 1=1 ";

    String BETWEEN_FECHA_MOVIMIENTO = "AND TMAR.FECMOVIMIENTO BETWEEN ? and ? ";

    String ORDER_BY_TMAR_FECMOVIMIENTO = " ORDER BY TMAR.FECMOVIMIENTO";

    /**
     * Bajas.
     */
    String SQL_SELECT_MARCA_BAJA = "SELECT \n"
            + "  TBAJ.FECREGISTRO AS FECMOVIMIENTO, \n"
            + "  TMAR.NOMMARCA \n"
            + "FROM TBCT_MARCA TMAR\n"
            + "  JOIN TBCT_BAJA TBAJ\n"
            + "    ON TBAJ.IDMARCA = TMAR.IDMARCA \n"
            + "    AND TMAR.IDMARCA = TBAJ.IDMARCA \n"
            + "    AND TMAR.IDESTATUS = 2\n"
            + "  JOIN TBCP_TABACALERA TTAB \n"
            + "    ON TBAJ.IDTABACALERA = TTAB.IDTABACALERA\n"
            + " WHERE 1=1 ";

    String BETWEEN_FECHA_REGISTRO = "AND TBAJ.FECREGISTRO BETWEEN ? AND ?";

    String ORDER_BY_TBAJ_FECREGISTRO = " ORDER BY TBAJ.FECREGISTRO";

}
