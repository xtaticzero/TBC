package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesContribuyentes;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesDesperdicios;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface ReportesDAO {

    String SQL_SELECT_MARCASCB1 = "SELECT TMAR.IDMARCA, TMAR.NOMMARCA FROM TBCT_MARCA TMAR, TBCP_TABACALERA TTAB "
            + "WHERE TMAR.IDTABACALERA = TTAB.IDTABACALERA AND TMAR.IDESTATUS=1 ORDER BY TMAR.NOMMARCA";
    String SQL_SELECT_MARCASCB2 = "SELECT TMAR.IDMARCA, TMAR.NOMMARCA FROM TBCT_MARCA TMAR, TBCP_TABACALERA TTAB "
            + "WHERE TMAR.IDTABACALERA = TTAB.IDTABACALERA AND TTAB.RFC = ? AND TMAR.IDESTATUS=1 ORDER BY TMAR.NOMMARCA";

    String SQL_SELECT_MARCAS_HEAD = "SELECT\n"
            + "TMAR.IDMARCA VALUE,\n"
            + "TMAR.NOMMARCA LABEL\n"
            + "FROM TBCT_MARCA TMAR\n"
            + "INNER JOIN TBCP_TABACALERA TTAB ON TTAB.IDTABACALERA = TMAR.IDTABACALERA \n"
            + "WHERE \n"
            + "TMAR.IDESTATUS != 3 ";
    
    String SQL_SELECT_MARCAS_FOOTER = " ORDER BY TMAR.NOMMARCA";

    String SQL_SELECT_MARCAS_AUTORIZADAS = SQL_SELECT_MARCAS_HEAD
            + SQL_SELECT_MARCAS_FOOTER;

    String SQL_SELECT_MARCAS_AUTORIZADAS_X_RFC = SQL_SELECT_MARCAS_HEAD
            + "AND TTAB.RFC = ?\n"
            + SQL_SELECT_MARCAS_FOOTER;

    String SQL_SELECT_PROVEEDORESCB1 = "SELECT IDPROVEEDOR, RAZONSOCIAL FROM TBCT_PROVEEDOR";
    String SQL_SELECT_PROVEEDORESCB2 = "SELECT TPRO.IDPROVEEDOR, TPRO.RAZONSOCIAL "
            + "FROM TBCT_PROVEEDOR TPRO, TBCA_TBC_PROV TTP, TBCP_TABACALERA TTAB "
            + "WHERE TPRO.IDPROVEEDOR = TTP.IDPROVEEDOR "
            + "AND TTP.IDTABACALERA = TTAB.IDTABACALERA "
            + "AND TTAB.RFC = ? ";
    String SQL_SELECT_CONTRIBUYENTESCB = "SELECT IDTABACALERA, RAZONSOCIAL FROM TBCP_TABACALERA WHERE IDESTATUS = 1";
    String SQL_SELECT_TABACALERA = "SELECT RAZONSOCIAL FROM TBCP_TABACALERA WHERE RFC = ?";
    String SQL_SELECT_TABCONT = "SELECT COUNT(RAZONSOCIAL) FROM TBCP_TABACALERA WHERE RFC = ?";
    String SQL_SELECT_PROVEEDOR = "SELECT RAZONSOCIAL FROM TBCT_PROVEEDOR WHERE RFC = ?";
    String SQL_SELECT_PROVCONT = "SELECT COUNT(RAZONSOCIAL) FROM TBCT_PROVEEDOR WHERE RFC = ?";

    String SQL_CONSULTA_DATOS_REPORTE_HEDER = "SELECT \n"
            + "TBCT_MARCA.NOMMARCA AS NOMMARCA,\n"
            + "TBCT_PLANTA.NOMPLANTA AS NOMPLANTA,\n"
            + "TBCT_PRODCIGARRO.CANTPRODUCIDA AS CANTPRODUCIDA,\n"
            + "TBCT_PRODCIGARRO.IDPAISORIGEN  AS IDPAISORIGEN,\n"
            + "TBCT_PRODCIGARRO.DESCMAQUINAPROD AS DESCMAQUINAPROD,\n"
            + "TBCT_PRODCIGARRO.NUMLOTE AS NUMLOTE,\n"
            + "TBCT_PRODCIGARRO.FECPRODUCCION AS FECPRODUCCION,\n"
            + "TBCT_PRODCIGARRO.CANTCIGARROS AS CANTCIGARROS,\n"
            + "TBCT_PRODCIGARRO.IDPRODCIGARRO AS IDPRODCIGARRO\n"
            + "FROM TBCT_PRODCIGARRO\n"
            + "INNER JOIN TBCT_MARCA ON TBCT_PRODCIGARRO.IDMARCA = TBCT_MARCA.IDMARCA \n"
            + "INNER JOIN TBCT_PLANTA ON TBCT_PRODCIGARRO.IDPLANTA = TBCT_PLANTA.IDPLANTA\n"
            + "INNER JOIN TBCP_TABACALERA ON TBCT_MARCA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "WHERE TBCT_PRODCIGARRO.IDTIPORETRO IS NOT NULL\n"
            + "AND \n";

    String SQL_CONSULTA_DATOS_REPORTE_FOOTER_RFC = "TBCP_TABACALERA.RFC = ? \n"
            + "ORDER BY TBCT_PRODCIGARRO.IDPRODCIGARRO";

    String SQL_CONSULTA_DATOS_REPORTE_FOOTER_FECHAS = "TBCT_PRODCIGARRO.FECPRODUCCION BETWEEN ? AND ? \n"
            + "ORDER BY TBCT_PRODCIGARRO.IDPRODCIGARRO";
    
    ReportesContribuyentes getContriyentes(ReportesFiltroBase filtro);

    ReportesRegistros getRegistros(ReportesRegistrosYDespedicios filtro);

    ReportesDesperdicios getDesperdicios(ReportesRegistrosYDespedicios filtro);

    String getRazonSocial(String rfc) throws ReporteDaoException;

    String getRazonSocialProv(String rfc);

    List<SelectItem> getCombos(final String strSQL, final String id, final String name) throws ReporteDaoException;

    List<SelectItem> getCombos(final String strSQL, final String id, final String name, final String param1);

    List<SelectItem> getCombosMarcaAutorizadas() throws ReporteDaoException;

    List<SelectItem> getMarcaAutorizadasXRfc(String rfc) throws ReporteDaoException;
}
