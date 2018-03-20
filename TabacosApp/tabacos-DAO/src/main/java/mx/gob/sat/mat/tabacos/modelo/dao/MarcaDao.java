/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface MarcaDao {
    
    String NOMMARCA = "NOMMARCA";
    String ERROR_OBTENER = "Error al obtener: ";

    String SQL_SELECT_NEXTID_MARCAS = "SELECT TBCQ_MARCA.NEXTVAL FROM DUAL";
    String SQL_SELECT_NEXTID_BAJA = "SELECT TBCQ_BAJA.NEXTVAL FROM DUAL";
    String SQL_SELECT_MARCAS = "SELECT IDMARCA, \n"
            + "NOMMARCA, \n"
            + "FECINICIO, \n"
            + "FECFIN,\n"
            + "IDESTATUS,\n"
            + "CLAVE,\n"
            + "RUTAARCHIVO,\n"
            + "IDTABACALERA FROM TBCT_MARCA \n"
            + "WHERE IDESTATUS = 1 ORDER BY NOMMARCA";

    String SQL_SELECT_TABACALERAS = "SELECT IDTABACALERA FROM TBCP_TABACALERA "
            + "WHERE RFC=?";
    String SQL_SELECT_TABACALERASCB = "SELECT IDTABACALERA, RFC FROM TBCP_TABACALERA WHERE IDESTATUS = 1 ORDER BY RFC";
    String SQL_SELECT_MARCAID = "SELECT IDMARCA FROM TBCT_MARCA "
            + "WHERE CLAVE=?";
    String SQL_SELECT_NOMMARCA = "SELECT NOMMARCA FROM TBCT_MARCA "
            + "WHERE IDMARCA=?";
    String SQL_SELECT_EXISTE = "SELECT COUNT(IDMARCA) FROM TBCT_MARCA "
            + "WHERE CLAVE=? AND NOMMARCA=? AND IDTABACALERA=?";
    String SQL_INSERT_MARCAS = "INSERT INTO TBCT_MARCA(IDMARCA, NOMMARCA, "
            + "FECINICIO, FECFIN, IDESTATUS, CLAVE, RUTAARCHIVO, IDTABACALERA) "
            + "VALUES(?,?,?,?,3,?,?,?)";
    String SQL_MOD_MARCAS = "UPDATE TBCT_MARCA SET NOMMARCA = ?, "
            + "CLAVE = ?, IDTABACALERA = ? "
            + "WHERE IDMARCA = ?";
    String SQL_DELETE_MARCAS = "UPDATE TBCT_MARCA SET IDESTATUS = ?, "
            + "FECFIN = SYSDATE WHERE CLAVE = ?";
    String SQL_INSERT_BAJA = "INSERT INTO TBCT_BAJA(IDBAJA, IDTABACALERA, "
            + "IDPROVEEDOR, IDMARCA, DESCMOTIVOBAJA, FECREGISTRO, FECBAJA) "
            + "VALUES(?,?,?,?,?,?,SYSDATE)";

    String SQL_COUNT_CLAVE_MARCA = "SELECT COUNT(*) REGISTROS FROM TBCT_MARCA WHERE CLAVE = ?";

    String SQL_BUSCA_MARCA_NOMBRE = "SELECT \n"
            + "IDMARCA,\n"
            + "CLAVE,\n"
            + "NOMMARCA,\n"
            + "TBCC_ESTATUS.NOMESTATUS ESTATUS,\n"
            + "TBCP_TABACALERA.RFC RFC,\n"
            + "TBCP_TABACALERA.RAZONSOCIAL RAZONSOCIAL\n"
            + "FROM TBCT_MARCA \n"
            + "INNER JOIN TBCC_ESTATUS ON TBCT_MARCA.IDESTATUS = TBCC_ESTATUS.IDESTATUS \n"
            + "INNER JOIN TBCP_TABACALERA ON TBCT_MARCA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "WHERE IDMARCA = ?";

    String SQL_BUSCA_MARCA_HEDER = "SELECT \n"
            + "IDMARCA, \n"
            + "CLAVE, \n"
            + "NOMMARCA, \n"
            + "TBCC_ESTATUS.NOMESTATUS ESTATUS, \n"
            + "TBCP_TABACALERA.RFC RFC, \n"
            + "TBCP_TABACALERA.RAZONSOCIAL RAZONSOCIAL \n"
            + "FROM TBCT_MARCA  \n"
            + "INNER JOIN TBCC_ESTATUS ON TBCT_MARCA.IDESTATUS = TBCC_ESTATUS.IDESTATUS  \n"
            + "INNER JOIN TBCP_TABACALERA ON TBCT_MARCA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA \n"
            + "WHERE ";

    String SQL_CONECTOR_AND = " AND ";

    String SQL_BUSCA_MARCA_IDMARCA = "IDMARCA = ? ";
    String SQL_BUSCA_MARCA_CLAVE = "CLAVE = ?";
    String SQL_BUSCA_MARCA_NOMMARCA = "NOMMARCA = ? ";

    String SQL_BUSCA_MARCA_RFC = " RFC = ? ";
    String SQL_BUSCA_MARCA_RAZONSOCIAL = " RAZONSOCIAL = ? ";
    String SQL_BUSCA_MARCA_FECHA = " FECINICIO = ? ";

    String SQL_BUSCAR_MARCA_AUTORIZAR_HEDER = "SELECT \n"
            + "TAB.IDTABACALERA IDTABACALERA,\n"
            + "TAB.RFC RFC,\n"
            + "TAB.RAZONSOCIAL RAZONSOCIAL,\n"
            + "MARC.IDMARCA IDMARCA,\n"
            + "MARC.NOMMARCA NOMMARCA,\n"
            + "MARC.CLAVE CLAVE,\n"
            + "MARC.FECINICIO FECINICIO,\n"
            + "MARC.FECFIN FECFIN,\n"
            + "MARC.IDESTATUS IDESTATUS,\n"
            + "ESTATUS.NOMESTATUS ESTATUS,\n"
            + "MARC.IDSPAUTORIZA SPAUTORIZA,\n"
            + "MARC.RUTAARCHIVO RUTAARCHIVO\n"
            + "FROM TBCT_MARCA MARC \n"
            + "INNER JOIN TBCC_ESTATUS ESTATUS ON MARC.IDESTATUS = ESTATUS.IDESTATUS\n"
            + "INNER JOIN TBCP_TABACALERA TAB ON TAB.IDTABACALERA = MARC.IDTABACALERA\n"
            + "WHERE ";

    String SQL_BUSCAR_MARCA_AUTORIZAR_FOOTER = " ORDER BY MARC.NOMMARCA";
    
    String SQL_SELECT_MARCAS_BY_RFC_TAB = "SELECT MAR.* FROM TBCT_MARCA MAR\n"
            + "INNER JOIN TBCP_TABACALERA TAB ON TAB.IDTABACALERA = MAR.IDTABACALERA \n"
            + "WHERE TAB.RFC = ? AND MAR.IDESTATUS = 1";
    
    String SQL_SELECT_MARCAS_TIPO = "SELECT MAR.* FROM TBCT_MARCA MAR ORDER BY MAR.NOMMARCA";
    
    String SQL_SELECT_MARCAS_X_TABACALERA = "SELECT IDMARCA, NOMMARCA  FROM TBCT_MARCA \n"
            + "WHERE IDTABACALERA = ?\n"
            + "AND IDESTATUS = 1\n"
            + "ORDER BY NOMMARCA";
    
    String SQL_SELECT_AUTORIZACIONCB = "SELECT IDSPAUTORIZA, DESCSPAUTORIZA "
            + "FROM TBCC_SPAUTORIZA";

    String SQL_UPDATE_MARCA = "UPDATE TBCT_MARCA SET IDESTATUS = ?, IDSPAUTORIZA = ?, "
            + "RUTAARCHIVO = ?, FECINICIO = ? WHERE CLAVE = ?";

    String SQL_SELECT_AUTSOLICITUD = "SELECT TMAR.IDMARCA, TMAR.CLAVE, "
            + "TTAB.RFC, TTAB.RAZONSOCIAL, "
            + "TO_CHAR(TMAR.FECINICIO, 'DD/MM/YYYY') AS FECINICIO, "
            + "TMAR.IDESTATUS, TMAR.NOMMARCA "
            + "FROM TBCT_MARCA TMAR, TBCP_TABACALERA TTAB "
            + "WHERE TTAB.IDTABACALERA=TMAR.IDTABACALERA "
            + "AND TMAR.IDESTATUS = 3 ";

    String SQL_SELECT_AUTSOLICITUD_CLAVE = "AND TMAR.CLAVE = ?";
    String SQL_SELECT_AUTSOLICITUD_RFC = "AND TMAR.IDTABACALERA = ?";
    String SQL_SELECT_AUTSOLICITUD_RSOCIAL = "AND TTAB.RAZONSOCIAL = ?";
    String SQL_SELECT_AUTSOLICITUD_FECHA = "AND TMAR.FECINICIO BETWEEN ? AND ?";

    Marcas insertaMarca(Marcas info);

    Marcas modificaMarca(Marcas info, String claveBusq);

    Integer borraMarca(Marcas info) throws MarcaDaoException;

    List<Marcas> consultaMarcas();

    boolean consultaMarcas(Marcas info);

    String obtieneMarca(String idMarca);

    Integer numeroClaves(String claveMarca) throws MarcaDaoException;

    List<SelectItem> getCombos(final String strSQL, final String id, final String name);

    Marcas buscaMarca(String nombre) throws MarcaDaoException;

    List<Marcas> buscaMarcaXAtributo(Long idMarca, String nomMarca, String clave) throws MarcaDaoException;

    List<MarcaResolucion> buscaMarcaResolucion(String clave, String rfc, String razonSocial, Date fecha) throws MarcaDaoException;
    
    List<Marcas> getMarcasByRFCTabacalera(String rfcTabacalera) throws MarcaDaoException;

    List<Marcas> getMarcas() throws MarcaDaoException;
    
    List<Marcas> selectedMarcas(Long idTabacalera) throws MarcaDaoException;
    
    List<SolicitudesRegistradas> obtenerSolicitudesRegistradas(int tipo, String valor1, Date valor2);

    List<SelectItem> getCombosMarcas(final String strSQL, final String id, final String name) throws MarcaDaoException;

    int autorizarSolicitud(MarcaResolucion info) throws MarcaDaoException;
}
