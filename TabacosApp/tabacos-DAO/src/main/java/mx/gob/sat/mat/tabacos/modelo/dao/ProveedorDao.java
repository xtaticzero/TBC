package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Baja;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;

import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.filtro.ProveedorFiltroDTO;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException;

/**
 * DAO del proveedor
 *
 * @author Salvador Pocteco Saldaña
 */
public interface ProveedorDao {
    
    //ALTA PROVEEDOR
    //Obtiene la secuencias para proveedor
    String SQL_SELECT_NEXTID_PROVEEDOR = "SELECT TBCQ_PROVEEDOR.NEXTVAL FROM DUAL";
    String SQL_SELECT_NEXTID_REPRESENTANTE = "SELECT TBCQ_REPLEGAL.NEXTVAL FROM DUAL";
    String SQL_SELECT_NEXTID_RELACIONAL_PROVEDOR = "SELECT TBCQ_TBCPROV.NEXTVAL FROM DUAL";
    String SQL_SELECT_NEXTID_BAJA = "SELECT TBCQ_BAJA.NEXTVAL FROM DUAL";

    //Obtiene los datos para llenar los combos de el proveedor
    String SQL_SELECT_MARCAS_PROVEEDOR = "SELECT IDMARCA, NOMMARCA, IDTABACALERA FROM TBCT_MARCA";
    
    String SQL_SELECT_ESTATUS_PROVEEDOR = "SELECT IDESTATUS, NOMESTATUS FROM TBCC_ESTATUS";

    //Busca el RFC que se quiere dar de alta
    String SQL_SELECT_RFC_PROVEEDOR = "SELECT IDPROVEEDOR, RFC FROM TBCT_PROVEEDOR WHERE RFC = ?";

    //Inserta los datos de el proveedor en las tablas correspondientes
    String SQL_INSERT_PROVEEDOR = "INSERT INTO TBCT_PROVEEDOR "
            + "(IDPROVEEDOR, RFC, RAZONSOCIAL, DOMICILIO, FECREGISTRO, FECCAPTURA, IDESTATUS ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    String SQL_INSERT_REPRESENTANTE = "INSERT INTO TBCT_REPLEGAL "
            + "(IDREPLEGAL, IDTABACALERA, IDPROVEEDOR, NOMBRE, APELLIDOMATERNO, APELLIDOPATERNO, CORREOELECTRONICO, TELEFONO, FECINICIO, IDESTATUS, IDTIPOREPLEGAL)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String SQL_INSERT_RELACIONAL_PROVEEDOR = "INSERT INTO TBCA_TBC_PROV "
            + "(IDTABACALERA, IDPROVEEDOR, FECREGISTRO, IDESTATUS, IDTBCPROV) "
            + "VALUES (?, ?, ?, ?, ?)";

    //CAMBIO PROVEEDOR
    String SQL_SELECT_PROVEEDOR_DATOS = "SELECT \n"
            + "TBCT_PROVEEDOR.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "TBCT_PROVEEDOR.RFC AS RFC,\n"
            + "TBCT_PROVEEDOR.RAZONSOCIAL AS RAZONSOCIAL,\n"
            + "TBCT_PROVEEDOR.DOMICILIO  AS DOMICILIO,\n"
            + "TBCT_PROVEEDOR.FECREGISTRO AS FECREGISTRO,\n"
            + "TBCT_PROVEEDOR.IDESTATUS AS IDESTATUS,\n"
            + "TBCT_REPLEGAL.IDREPLEGAL AS IDREPLEGAL,\n"
            + "TBCT_REPLEGAL.IDTABACALERA AS IDTABACALERA,\n"
            + "TBCT_REPLEGAL.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "TBCT_REPLEGAL.NOMBRE AS NOMBRE,\n"
            + "TBCT_REPLEGAL.APELLIDOPATERNO AS APELLIDOPATERNO,\n"
            + "TBCT_REPLEGAL.APELLIDOMATERNO AS APELLIDOMATERNO,\n"
            + "TBCT_REPLEGAL.CORREOELECTRONICO AS CORREOELECTRONICO,\n"
            + "TBCT_REPLEGAL.TELEFONO AS TELEFONO,\n"
            + "TBCT_REPLEGAL.IDESTATUS AS IDESTATUS,\n"
            + "TBCC_ESTATUS.NOMESTATUS AS NOMESTATUS\n"
            + "FROM TBCT_PROVEEDOR\n"
            + "INNER JOIN TBCT_REPLEGAL ON TBCT_REPLEGAL.IDPROVEEDOR = TBCT_PROVEEDOR.IDPROVEEDOR\n"
            + "INNER JOIN TBCC_ESTATUS ON TBCT_PROVEEDOR.IDESTATUS = TBCC_ESTATUS.IDESTATUS\n"
            + "WHERE TBCT_PROVEEDOR.RFC = ?";

    String SQL_UPDATE_PROVEEDOR = "UPDATE TBCT_PROVEEDOR SET FECCAPTURA = ? WHERE RFC = ?";

    String SQL_UPDATE_REPRESENTATE = "UPDATE TBCT_REPLEGAL \n"
            + "SET IDTABACALERA = ?, NOMBRE = ?, APELLIDOPATERNO = ?, APELLIDOMATERNO = ?, CORREOELECTRONICO = ?, TELEFONO = ? \n"
            + "WHERE IDREPLEGAL = ?";

    String SQL_UPDATE_PROVEEDOR_RELACION_ESTATUS_BAJA = "UPDATE TBCA_TBC_PROV \n"
            + "SET FECBAJA = ?, \n"
            + "IDESTATUS = ? \n"
            + "WHERE IDPROVEEDOR = ? \n"
            + "AND IDTABACALERA = ?";
    
    //BAJA PROVEEDOR
    String SQL_DELETE_PROVEEDOR = "INSERT INTO TBCT_BAJA \n"
            + "(IDBAJA, IDTABACALERA, IDPROVEEDOR, DESCMOTIVOBAJA, FECREGISTRO, FECBAJA) \n"
            + "VALUES (?, ?, ?, ?, ?, ?)";

    String SQL_UPDATE_PROVEEDOR_BAJA = "UPDATE TBCT_PROVEEDOR SET IDESTATUS = ? WHERE RFC= ?";

    String SQL_UPDATE_PROVEEDOR_RELACION_BAJA = "UPDATE TBCA_TBC_PROV SET FECBAJA = ?, IDESTATUS = ? WHERE IDPROVEEDOR = ?";
    
    String SQL_SELECT_PROVEEDOR_RELACION_BAJA = "SELECT IDBAJA, IDTABACALERA, IDPROVEEDOR, DESCMOTIVOBAJA, FECREGISTRO, FECBAJA\n"
            + "FROM TBCT_BAJA\n"
            + "WHERE IDPROVEEDOR = ?";

    String SQL_SELECT_PROVEEDOR_BY_RFC_DELETE = "SELECT IDPROVEEDOR, RFC, RAZONSOCIAL, DOMICILIO, FECREGISTRO, FECCAPTURA, IDESTATUS FROM TBCT_PROVEEDOR WHERE RFC = ? AND IDESTATUS = 1";

    String SQL_FIND_PROVEDOR_X_TABACALERA = "SELECT TBCT_PROVEEDOR.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "TBCT_PROVEEDOR.RFC AS RFC,\n"
            + "TBCT_PROVEEDOR.RAZONSOCIAL AS RAZONSOCIAL,\n"
            + "TBCT_PROVEEDOR.DOMICILIO AS DOMICILIO,\n"
            + "TBCT_PROVEEDOR.FECREGISTRO AS FECREGISTRO,\n"
            + "TBCT_PROVEEDOR.FECCAPTURA AS FECCAPTURA,\n"
            + "TBCT_PROVEEDOR.IDESTATUS AS IDESTATUS,\n"
            + "TBCC_ESTATUS.NOMESTATUS AS NOMESTATUS      \n"
            + "FROM TBCP_TABACALERA\n"
            + "INNER JOIN TBCA_TBC_PROV on TBCP_TABACALERA.IDTABACALERA = TBCA_TBC_PROV.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR\n"
            + "INNER JOIN TBCC_ESTATUS ON TBCT_PROVEEDOR.IDESTATUS = TBCC_ESTATUS.IDESTATUS\n"
            + "WHERE TBCP_TABACALERA.RFC = ? AND TBCP_TABACALERA.IDESTATUS = 1 ORDER BY IDPROVEEDOR";

    String SQL_REPLACE_RFC = "PROVEEDOR_CONSULTA";

    String SQL_FIND_PROVEDOR_LIKE_RFC = "SELECT TBCT_PROVEEDOR.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "             TBCT_PROVEEDOR.RFC AS RFC,\n"
            + "             TBCT_PROVEEDOR.RAZONSOCIAL AS RAZONSOCIAL, \n"
            + "             TBCT_PROVEEDOR.DOMICILIO AS DOMICILIO, \n"
            + "             TBCT_PROVEEDOR.FECREGISTRO AS FECREGISTRO, \n"
            + "             TBCT_PROVEEDOR.FECCAPTURA AS FECCAPTURA, \n"
            + "             TBCT_PROVEEDOR.IDESTATUS AS IDESTATUS, \n"
            + "             TBCC_ESTATUS.NOMESTATUS AS NOMESTATUS       \n"
            + "             FROM TBCT_PROVEEDOR\n"
            + "             INNER JOIN TBCC_ESTATUS ON TBCT_PROVEEDOR.IDESTATUS = TBCC_ESTATUS.IDESTATUS \n"
            + "             WHERE TBCT_PROVEEDOR.RFC LIKE '%PROVEEDOR_CONSULTA%' ORDER BY RFC";

    //Querys utilizados para la parte de Reportes
    String SQL_SELECT_PROVEEDOR
            = "SELECT FECMOVIMIENTO, RFC FROM TBCH_PROVEEDOR WHERE IDESTATUS = 1 ";

    String SQL_SELECT_PROVEEDOR_OP1
            = "AND RFC = ? AND FECMOVIMIENTO BETWEEN ? and ? ORDER BY FECMOVIMIENTO";

    String SQL_SELECT_PROVEEDOR_OP2
            = "AND FECMOVIMIENTO BETWEEN ? AND ? ORDER BY FECMOVIMIENTO";

    String SQL_SELECT_PROVEEDOR_OP3
            = "AND RFC = ? ORDER BY FECMOVIMIENTO";

    String SQL_SELECT_PROVEEDOR_PROD
            = "SELECT FECREGISTRO AS FECMOVIMIENTO, RFC "
            + "FROM TBCT_PROVEEDOR WHERE IDESTATUS = 1 ";

    String SQL_SELECT_PROVEEDOR_OP1_PROD
            = "AND RFC = ? AND FECREGISTRO BETWEEN ? and ? ORDER BY FECREGISTRO";

    String SQL_SELECT_PROVEEDOR_OP2_PROD
            = "AND FECREGISTRO BETWEEN ? AND ? ORDER BY FECREGISTRO";

    String SQL_SELECT_PROVEEDOR_BAJA = "SELECT\n"
            + "TBAJ.FECREGISTRO AS FECMOVIMIENTO,\n"
            + "TPRO.RFC AS RFC\n"
            + "FROM TBCT_PROVEEDOR TPRO\n"
            + "INNER JOIN TBCT_BAJA TBAJ ON TPRO.IDPROVEEDOR = TBAJ.IDPROVEEDOR\n"
            + "WHERE \n"
            + "TPRO.IDESTATUS = 2 ";

    String SQL_SELECT_PROVEEDOR_BAJA_OP1
            = "AND TTAB.RFC = ? AND TBAJ.FECREGISTRO BETWEEN ? AND ? "
            + "AND ORDER BY TBAJ.FECREGISTRO ";

    String SQL_SELECT_PROVEEDOR_BAJA_OP2
            = " AND TBAJ.FECREGISTRO BETWEEN ? AND ? ORDER BY TBAJ.FECREGISTRO";

    String SQL_SELECT_PROVEEDOR_BAJA_OP3
            = " AND TPRO.RFC = ? ORDER BY TBAJ.FECREGISTRO ";

    String SQL_SELECT_CONTRIBUYENTE_BAJA
            = "SELECT TBAJ.FECREGISTRO AS FECMOVIMIENTO, TTAB.RFC "
            + "FROM TBCT_BAJA TBAJ, TBCP_TABACALERA TTAB "
            + "WHERE TTAB.IDTABACALERA = TBAJ.IDTABACALERA "
            + "AND TTAB.IDESTATUS = 2 ";

    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP1
            = "AND TTAB.RFC = ? AND TBAJ.FECREGISTRO BETWEEN ? AND ? "
            + "ORDER BY TBAJ.FECREGISTRO";

    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP2
            = "AND TBAJ.FECREGISTRO BETWEEN ? AND ? ORDER BY TBAJ.FECREGISTRO ";

    String SQL_SELECT_CONTRIBUYENTE_BAJA_OP3
            = " AND TTAB.RFC = ? ORDER BY TBAJ.FECREGISTRO";
    
    String SQL_CONSULTA_PROVEEDOR_RAZON_SOCIAL = "SELECT IDPROVEEDOR, RFC, RAZONSOCIAL FROM TBCT_PROVEEDOR WHERE IDESTATUS = 1 ORDER BY RAZONSOCIAL";

    String SQL_CONSULTA_PROVEEDOR_RAZON_SOCIAL_POR_RFC = "SELECT \n"
            + "TBCT_PROVEEDOR.IDPROVEEDOR AS IDPROVEEDOR,\n"
            + "TBCT_PROVEEDOR.RAZONSOCIAL AS RAZONSOCIAL,\n"
            + "TBCT_PROVEEDOR.RFC AS RFC\n"
            + "FROM TBCP_TABACALERA\n"
            + "INNER JOIN TBCA_TBC_PROV ON TBCP_TABACALERA.IDTABACALERA = TBCA_TBC_PROV.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR\n"
            + "WHERE TBCP_TABACALERA.RFC = ? "
            + "ORDER BY TBCT_PROVEEDOR.RAZONSOCIAL";
    
    String SQL_CONSULTA_PROVEEDOR_TODOS = "SELECT IDPROVEEDOR, RFC, RAZONSOCIAL FROM TBCT_PROVEEDOR ORDER BY RAZONSOCIAL";
    
    String SQL_SELECT_PROVEEDOR_BY_RFC = "SELECT * FROM TBCT_PROVEEDOR WHERE RFC = ?";
    
    String SQL_TABACALERA_PROVEEDORES = "SELECT A.IDPROVEEDOR,A.RFC,A.RAZONSOCIAL,A.DOMICILIO,A.FECREGISTRO,A.FECCAPTURA,"
            + "A.IDESTATUS FROM TBCT_PROVEEDOR A INNER JOIN TBCA_TBC_PROV B ON A.IDPROVEEDOR=B.IDPROVEEDOR INNER JOIN TBCP_TABACALERA C ON B.IDTABACALERA=C.IDTABACALERA WHERE C.IDTABACALERA = ? "
            + "AND A.IDESTATUS = 1 AND B.IDESTATUS = 1 AND C.IDESTATUS = 1";
    
    String TB_PROVEEDOR = "TBCC_PROVEEDOR";

    String SQL_PROVEEDOR_RFC = "SELECT RFC FROM " + TB_PROVEEDOR + "";
    String SQL_SELECT_PROVEEDORES = "SELECT IDN, STATUS, NOMBRE, RFC, BOID, FECHA, CORREO, TELEFONO, TELEFONO2, DOMICILIO FROM " + TB_PROVEEDOR;
    
    List<Proveedor> findRfc(ProveedorFiltroDTO proveedorDatos) throws ProveedorDaoException;

    Proveedor insertProveedor(Proveedor proveedor) throws ProveedorDaoException;

    RepresentanteLegal insertRepresentante(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera) throws ProveedorDaoException;

    RelacionTabProv insertRelacionProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor) throws ProveedorDaoException;

    RepresentanteLegal updateRepresentante(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera) throws ProveedorDaoException;

    Baja deleteProveedor(Proveedor proveedor, Long idTabacalera, Baja baja) throws ProveedorDaoException;

    Proveedor updateProveedorBaja(Proveedor proveedor) throws ProveedorDaoException;

    RelacionTabProv updateProveedorRelacionBaja(Proveedor proveedor, Baja baja, RelacionTabProv relacionProveedor) throws ProveedorDaoException;

    List<Proveedor> consultaDatosProveedor(Proveedor proveedor) throws ProveedorDaoException;

    List<RepresentanteLegal> consultaDatosRepresentante(RepresentanteLegal representante, Proveedor proveedor) throws ProveedorDaoException;

    List<Marcas> selectedMarcas() throws ProveedorDaoException;

    List<Estatus> selectedEstatus(Proveedor proveedor) throws ProveedorDaoException;

    List<Proveedor> buscaRfc(Proveedor proveedor) throws ProveedorDaoException;

    List<Baja> consultaDatosProveedorBaja(Proveedor proveedor) throws ProveedorDaoException;

    RelacionTabProv updateProveedorRelacionCambio(Proveedor proveedor, RelacionTabProv relacionProveedor, Long idTabacaleraTem) throws ProveedorDaoException;

    List<Proveedor> buscaProveedorPorTabacalera(String rfcTabacalera) throws ProveedorDaoException;

    List<Proveedor> recuperaProveedores(ReportesFiltroBase filtro) throws ProveedorDaoException;

    List<Proveedor> buscaProveedorLikeRFC(String like) throws ProveedorDaoException;
    
    List<Proveedor> consultaProveedores() throws ProveedorDaoException;
    
    List<Proveedor> consultaProveedoresPorRfc(String rfc) throws ProveedorDaoException;
    
    List<Proveedor> consultaTodosLosProveedores() throws ProveedorDaoException;
    
    Proveedor getProveedorByRFC(String rfc) throws ProveedorDaoException;
    
    List<Proveedor> consultarProveedores(Long idTabacalera) throws ProveedorDaoException;
    
    Proveedor buscarProveedorPorRfc(String rfc) throws ProveedorDaoException;
}
