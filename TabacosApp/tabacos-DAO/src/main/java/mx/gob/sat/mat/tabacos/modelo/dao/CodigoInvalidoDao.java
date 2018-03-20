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
public interface CodigoInvalidoDao {
    
    String SQL_SELECT_NEXTID_CODIGOINVALIDO = "SELECT TBCQ_CODINVALIDO.NEXTVAL FROM DUAL";
    
    String INSERT_CODIGOINVALIDO = "INSERT INTO TBCT_CODINVALIDO (IDCODINVALIDO,"
            + " IDRANGOFOLIO, FOLIOINICIAL, FOLIOFINAL, FECREGISTRO, FECCAPTURA,"
            + " JUSTIFICACION) VALUES (?, ?, ?, ?, ?, SYSDATE, ?)";
    
    String SQL_CODIGOINVALIDO_IDRANGOFOLIO = "SELECT IDCODINVALIDO,"
            + " IDRANGOFOLIO, FOLIOINICIAL, FOLIOFINAL, FECREGISTRO, FECCAPTURA,"
            + " JUSTIFICACION FROM TBCT_CODINVALIDO WHERE IDRANGOFOLIO = ?"
            + " AND ( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL)";
    
    String SQL_CODINVALIDO_TABACALAREA = ""
            + "SELECT COD.IDCODINVALIDO AS IDCODINVALIDO, COD.IDRANGOFOLIO AS IDRANGOFOLIO, \n" +
                "COD.FOLIOINICIAL AS FOLIOINICIAL, COD.FOLIOFINAL AS FOLIOFINAL, \n" +
                "COD.FECREGISTRO AS FECREGISTRO, COD.FECCAPTURA AS FECCAPTURA, COD.JUSTIFICACION AS JUSTIFICACION, \n" +
                "SOL.IDSOLICITUD, PCI.IDTIPORETRO \n" +
                "FROM TBCT_RANGOFOLIO RAN \n" +
                "INNER JOIN TBCT_PRODCIGARRO PCI ON RAN.IDPRODCIGARRO = PCI.IDPRODCIGARRO \n" +
                "INNER JOIN TBCT_RESOLUCION RES ON RAN.IDRESOLUCION = RES.IDRESOLUCION \n" +
                "INNER JOIN TBCT_CODINVALIDO COD ON COD.IDRANGOFOLIO = RAN.IDRANGOFOLIO\n" +
                "INNER JOIN TBCT_SOLICITUD SOL ON RES.IDSOLICITUD = SOL.IDSOLICITUD \n" +
                "INNER JOIN TBCA_TBC_PROV REL ON SOL.IDTBCPROV = REL.IDTBCPROV \n" +
                "INNER JOIN TBCP_TABACALERA TAB ON REL.IDTABACALERA = TAB.IDTABACALERA \n" +
                "INNER JOIN TBCT_PROVEEDOR PRO ON REL.IDPROVEEDOR = PRO.IDPROVEEDOR \n" +
                "WHERE TAB.RFC = ? AND PCI.IDTIPORETRO IS NULL \n" +
                "AND ( COD.FOLIOINICIAL >= ?  AND  COD.FOLIOFINAL <= ?)\n" +
                "ORDER BY COD.FOLIOINICIAL, COD.FOLIOFINAL";
    
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

    int guardar (Codigo codigo) throws DaoException;
    List<Codigo> buscarCodigoInvPorRangoFolio(String rfc, Long folioInicial, Long folioFinal) throws DaoException;
    List<Codigo> getCodigosInvalidos(ReportesFiltroBase filtro) throws DaoException;
}
