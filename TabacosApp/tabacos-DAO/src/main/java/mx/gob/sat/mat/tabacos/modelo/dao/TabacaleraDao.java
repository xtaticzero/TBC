package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusTabacaleraEnum;

import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TabacaleraDaoException;

public interface TabacaleraDao {

    String SELECT = "SELECT \n";

    String TABACALERA_RFC = "TBCP_TABACALERA.RFC RFC,\n";
    String TABACALERA_ESTATUS = "INNER JOIN TBCC_ESTATUS ON TBCP_TABACALERA.IDESTATUS = TBCC_ESTATUS.IDESTATUS\n";
    String TABACALERA_RAZONSOCIAL = "TBCP_TABACALERA.RAZONSOCIAL RAZONSOCIAL,\n";

    String SQL_TABACALERA_RFC = "SELECT IDTABACALERA,RFC,RAZONSOCIAL,DOMICILIO,IDTIPOUSUARIO,FECREGISTRO,FECCAPTURA,IDESTATUS,CORREOELECTRONICO,TELEFONO FROM TBCP_TABACALERA WHERE RFC = ? AND IDESTATUS = 1";

    String SQL_TABACALERA_LIKE_RFC_HEDER = SELECT
            + "TBCP_TABACALERA.IDTABACALERA IDTABACALERA,\n"
            + TABACALERA_RFC
            + TABACALERA_RAZONSOCIAL
            + "TBCP_TABACALERA.IDESTATUS IDESTATUS,\n"
            + "TBCC_ESTATUS.NOMESTATUS ESTATUS,\n"
            + "TBCP_TABACALERA.CORREOELECTRONICO CORREOE,\n"
            + "TBCP_TABACALERA.DOMICILIO DOMICILIO\n"
            + "FROM \n"
            + "TBCP_TABACALERA \n"
            + TABACALERA_ESTATUS
            + "WHERE TBCC_ESTATUS.IDESTATUS = ? AND TBCP_TABACALERA.RFC LIKE '%";

    String IDESTATUS_CONDICION = "TBCC_ESTATUS.IDESTATUS = ? AND";

    String SQL_TABACALERA_LAKE_RFC_FOOTER = "%'\n"
            + "ORDER BY TBCP_TABACALERA.RFC";

    String SQL_IDESTATUS_CONDICION = "WHERE TBCC_ESTATUS.IDESTATUS = ?\n";

    String SQL_TABACALERA_X_IDESTADO = SELECT
            + "TBCP_TABACALERA.IDTABACALERA IDTABACALERA,\n"
            + TABACALERA_RFC
            + TABACALERA_RAZONSOCIAL
            + "TBCP_TABACALERA.IDESTATUS IDESTATUS,\n"
            + "TBCC_ESTATUS.NOMESTATUS ESTATUS,\n"
            + "TBCP_TABACALERA.CORREOELECTRONICO CORREOE,\n"
            + "TBCP_TABACALERA.DOMICILIO DOMICILIO\n"
            + "FROM \n"
            + "TBCP_TABACALERA \n"
            + TABACALERA_ESTATUS
            + SQL_IDESTATUS_CONDICION
            + "ORDER BY TBCP_TABACALERA.RFC";

    String SQL_TABACALERA_ALTA_HEAD = SELECT
            + TABACALERA_RFC
            + TABACALERA_RAZONSOCIAL
            + "TBCP_TABACALERA.FECREGISTRO MOVIMIENTO\n"
            + "FROM\n"
            + "TBCP_TABACALERA\n"
            + TABACALERA_ESTATUS
            + "WHERE TBCC_ESTATUS.IDESTATUS = ? ";

    String SQL_PARAM_ALTA_RFC = "AND TBCP_TABACALERA.RFC = ? ";

    String SQL_PARAM_ALTA_FECHA = "AND TBCP_TABACALERA.FECREGISTRO BETWEEN ? AND ?";

    String SQL_TABACALERA_FOOTER = "ORDER BY MOVIMIENTO";

    String SQL_TABACALERA_BAJA_HEAD = SELECT
            + TABACALERA_RFC
            + TABACALERA_RAZONSOCIAL
            + "TBCT_BAJA.FECREGISTRO MOVIMIENTO\n"
            + "FROM\n"
            + "TBCP_TABACALERA\n"
            + TABACALERA_ESTATUS
            + "INNER JOIN TBCT_BAJA ON TBCT_BAJA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "WHERE TBCC_ESTATUS.IDESTATUS = ? ";

    String SQL_PARAM_BAJA_RFC = SQL_PARAM_ALTA_RFC;

    String SQL_PARAM_BAJA_FECHA = "AND TBCT_BAJA.FECREGISTRO BETWEEN ? AND ? ";

    String SQL_TABACALERA_CAMBIO_HEAD = SELECT
            + "TBCH_TABACALERA.RFC RFC,\n"
            + "TBCH_TABACALERA.RAZONSOCIAL RAZONSOCIAL,\n"
            + "TBCH_TABACALERA.FECMOVIMIENTO MOVIMIENTO\n"
            + "FROM\n"
            + "TBCH_TABACALERA\n"
            + "INNER JOIN TBCC_ESTATUS ON TBCH_TABACALERA.IDESTATUS = TBCC_ESTATUS.IDESTATUS\n"
            + "WHERE TBCC_ESTATUS.IDESTATUS = ? ";

    String SQL_PARAM_CAMBIO_RFC = "AND TBCH_TABACALERA.RFC = ? ";

    String SQL_PARAM_CAMBIO_FECHA = "AND TBCH_TABACALERA.FECMOVIMIENTO BETWEEN ? AND ? ";

    String SQL_SELECT_TACACALERAS = "SELECT TAB.* FROM TBCP_TABACALERA TAB WHERE TAB.IDESTATUS = 1 ORDER BY TAB.RFC";

    String SQL_SELECT_TABACALERA_BY_RFC = "SELECT * FROM TBCP_TABACALERA WHERE RFC = ?";

    String SQL_SELECT_PROVEEDOR_TABACALERAS = "SELECT A.IDTABACALERA, A.RFC, A.RAZONSOCIAL,A.DOMICILIO,A.IDTIPOUSUARIO,A.FECREGISTRO,A.FECCAPTURA,A.IDESTATUS,A.CORREOELECTRONICO,A.TELEFONO FROM TBCP_TABACALERA A INNER JOIN TBCA_TBC_PROV B ON A.IDTABACALERA = B.IDTABACALERA INNER JOIN TBCT_PROVEEDOR C ON B.IDPROVEEDOR=C.IDPROVEEDOR where C.idproveedor = ? AND A.IDESTATUS = 1 AND B.IDESTATUS = 1 AND C.IDESTATUS = 1";

    String SQL_SELECT_RFC_CLIENTE_PROVEEDOR_ALTA = "SELECT IDTABACALERA, RFC FROM TBCP_TABACALERA\n"
            + "WHERE IDESTATUS = 1  \n"
            + "ORDER BY RFC";

    String SQL_SELECT_RFC_CLIENTE_PROVEEDOR_CAMBIO = SELECT
            + "TBCA_TBC_PROV.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "TBCP_TABACALERA.IDTABACALERA AS IDTABACALERA,\n"
            + "TBCP_TABACALERA.RFC AS RFC,\n"
            + "TBCA_TBC_PROV.IDESTATUS AS IDESTATUS\n"
            + "FROM TBCT_PROVEEDOR\n"
            + "INNER JOIN TBCA_TBC_PROV ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR\n"
            + "INNER JOIN TBCP_TABACALERA ON TBCP_TABACALERA.IDTABACALERA = TBCA_TBC_PROV.IDTABACALERA\n"
            + "WHERE TBCP_TABACALERA.IDESTATUS = 1\n"
            + "AND TBCA_TBC_PROV.IDESTATUS  = 1\n"
            + "AND TBCT_PROVEEDOR.RFC = ?";

    String SQL_CONSULTA_TABACALERA_RAZON_SOCIAL = "SELECT IDTABACALERA, RFC, RAZONSOCIAL FROM TBCP_TABACALERA WHERE IDESTATUS = 1 ORDER BY RAZONSOCIAL";

    String SQL_CONSULTA_TABACALERA_RAZON_SOCIAL_POR_RFC = "SELECT IDTABACALERA, RFC, RAZONSOCIAL "
            + "FROM TBCP_TABACALERA "
            + "WHERE RFC = ? "
            + "AND IDESTATUS = 1 ORDER BY RAZONSOCIAL";

    String SQL_REPLEGAL_TABACALERA = "SELECT A.IDTABACALERA,A.RFC,A.RAZONSOCIAL,A.DOMICILIO,A.IDTIPOUSUARIO,A.FECREGISTRO,A.FECCAPTURA,A.IDESTATUS,"
            + " A.CORREOELECTRONICO,A.TELEFONO FROM TBCP_TABACALERA A INNER JOIN TBCT_REPLEGAL B ON A.IDTABACALERA = B.IDTABACALERA WHERE B.IDREPLEGAL = ? "
            + " AND A.IDESTATUS = 1 AND B.IDESTATUS = 1";

    Tabacalera consultarPorRfc(String rfc) throws TabacaleraDaoException;

    List<String> buscaTabacalerasLikeRfc(String rfc, EstatusTabacaleraEnum estatus) throws TabacaleraDaoException;

    List<Tabacalera> buscaTabacaleras(EstatusTabacaleraEnum estatus) throws TabacaleraDaoException;

    List<Tabacalera> buscaTabacalerasXMovimiento(String rfc, Date fechaInicio, Date fechafin, ABCEnum tipoConsulta, EstatusTabacaleraEnum estatus) throws TabacaleraDaoException;

    List<Tabacalera> getTabacaleras() throws TabacaleraDaoException;

    Tabacalera getTabacaleraByRFC(String rfc) throws TabacaleraDaoException;

    List<Tabacalera> consultarTabacaleras(Proveedor proveedor) throws TabacaleraDaoException;

    List<Tabacalera> selectedRfcClienteAlta() throws TabacaleraDaoException;

    List<Tabacalera> selectedRfcClienteCambio(Proveedor proveedor) throws TabacaleraDaoException;

    List<Tabacalera> consultaTabacaleras() throws TabacaleraDaoException;

    List<Tabacalera> consultaContribuyentesPorRfc(String rfc) throws TabacaleraDaoException;

    Tabacalera consultarTabacaleraRepresentada(RepresentanteLegal representanteLegal) throws TabacaleraDaoException;
}
