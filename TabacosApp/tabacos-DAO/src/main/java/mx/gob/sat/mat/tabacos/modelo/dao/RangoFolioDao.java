/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusCargador;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.constants.enums.TipoRetroEnum;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;

/**
 *
 * @author MMMF
 */
public interface RangoFolioDao {
    
    String ORDER_BY = "ORDER BY RAN.FOLIOINICIAL, RAN.FOLIOFINAL";

    String TBCQ_RANGOFOLIO_NEXTVAL = "SELECT TBCQ_RANGOFOLIO.NEXTVAL FROM DUAL";

    String TB_RANGO = "TBCT_RANGOFOLIO";
    String SELECT_RANGOFOLIO = "SELECT IDRANGOFOLIO, IDPRODCIGARRO, FOLIOINICIAL, FOLIOFINAL, FECRETROALIMEN,"
            + " IDESTATUS, IDRESOLUCION FROM " + TB_RANGO + " WHERE "
            + "( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL)";

    String SELECT_RANGOFOLIO_IDRESOLUCION = "SELECT IDRANGOFOLIO, IDPRODCIGARRO, FOLIOINICIAL, FOLIOFINAL, FECRETROALIMEN,"
            + " IDESTATUS, IDRESOLUCION FROM " + TB_RANGO
            + " WHERE IDRESOLUCION = ? AND  "
            + " ( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL)";

    String SELECT_RANGOFOLIO_TABACALERA = "SELECT RAN.IDRANGOFOLIO AS IDRANGOFOLIO, \n"
            + "RAN.FOLIOINICIAL AS FOLIOINICIAL, RAN.FOLIOFINAL AS FOLIOFINAL, RAN.IDESTATUS AS IDESTATUS\n"
            + "FROM TBCT_RANGOFOLIO RAN \n"
            + "INNER JOIN TBCT_PRODCIGARRO PCI ON RAN.IDPRODCIGARRO = PCI.IDPRODCIGARRO \n"
            + "INNER JOIN TBCT_RESOLUCION RES ON RAN.IDRESOLUCION = RES.IDRESOLUCION \n"
            + "INNER JOIN TBCT_SOLICITUD SOL ON RES.IDSOLICITUD = SOL.IDSOLICITUD \n"
            + "INNER JOIN TBCA_TBC_PROV REL ON SOL.IDTBCPROV = REL.IDTBCPROV \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON REL.IDTABACALERA = TAB.IDTABACALERA \n"
            + "INNER JOIN TBCT_PROVEEDOR PRO ON REL.IDPROVEEDOR = PRO.IDPROVEEDOR \n"
            + "WHERE TAB.RFC = ? AND PCI.IDTIPORETRO IS NULL \n"
            + "AND (  ? BETWEEN RAN.FOLIOINICIAL AND RAN.FOLIOFINAL OR ? BETWEEN RAN.FOLIOINICIAL AND RAN.FOLIOFINAL)\n"
            + "ORDER BY RAN.FOLIOINICIAL, RAN.FOLIOFINAL";

    String SELECT_RANGOFOLIO_CANCEL = "SELECT IDRANGOFOLIO, IDPRODCIGARRO, FOLIOINICIAL, FOLIOFINAL, FECRETROALIMEN,"
            + " IDESTATUS, IDRESOLUCION FROM " + TB_RANGO + " WHERE IDESTATUS = ? "
            + "( ? BETWEEN FOLIOINICIAL AND FOLIOFINAL OR ? BETWEEN FOLIOINICIAL AND FOLIOFINAL)";

    String INSERT_RANGOFOLIO = "INSERT INTO TBCT_RANGOFOLIO ("
            + " IDRANGOFOLIO, "
            + " IDPRODCIGARRO, FOLIOINICIAL, FOLIOFINAL,"
            + " FECRETROALIMEN, IDESTATUS, IDRESOLUCION) VALUES ("
            + " ?,"
            + " ?, ?, ?, ?, ?, ?)";

    String FROM_RESOLUCION = "FROM TBCT_RESOLUCION RES \n";

    String JOIN_SOLICITUD_TAB_PROV = " INNER JOIN TBCT_SOLICITUD SOL ON RES.IDSOLICITUD = SOL.IDSOLICITUD \n"
            + "INNER JOIN TBCA_TBC_PROV REL ON SOL.IDTBCPROV = REL.IDTBCPROV \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON TAB.IDTABACALERA = REL.IDTABACALERA \n"
            + "INNER JOIN TBCT_PROVEEDOR PRO ON PRO.IDPROVEEDOR = REL.IDPROVEEDOR \n";

    String SELECT_RES = "SELECT RES.* \n";

    String SQL_SELECT_NEXTID_RANGOFOLIO = TBCQ_RANGOFOLIO_NEXTVAL;

    String SQL_INSERT_RANGOFOLIO = "INSERT INTO TBCT_RANGOFOLIO(IDRANGOFOLIO, IDPRODCIGARRO, FOLIOINICIAL, FOLIOFINAL, FECRETROALIMEN, IDESTATUS, IDRESOLUCION) VALUES(?,?,?,?,?,?,?)";

    String SQL_SELECT_NEXTVAL_RANGOFOLIO = TBCQ_RANGOFOLIO_NEXTVAL;

    String SQL_SELECT_RANGOSRESOLUCION_BY_RFCTAB_RFCPROV = SELECT_RES
            + FROM_RESOLUCION
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? AND RES.IDESTRESOLUCION = ? AND RES.IDESTCARGADOR = ? \n"
            + "ORDER BY RES.FOLIOINICIAL, RES.FOLIOFINAL";

    String SQL_SELECT_RANGOSRESOLUCION_BY_RFCTAB = SELECT_RES
            + FROM_RESOLUCION
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND RES.IDESTRESOLUCION = ? AND RES.IDESTCARGADOR = ? \n"
            + "ORDER BY RES.FOLIOINICIAL, RES.FOLIOFINAL";

    String JOIN_RESOLUCION_PROD = " INNER JOIN TBCT_PRODCIGARRO PCI ON RAN.IDPRODCIGARRO = PCI.IDPRODCIGARRO \n"
            + "INNER JOIN TBCT_RESOLUCION RES ON RAN.IDRESOLUCION = RES.IDRESOLUCION \n";

    String SELECT_RAN = "SELECT RAN.*, SOL.IDSOLICITUD FROM TBCT_RANGOFOLIO RAN \n";

    String SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV_TIPORETRO = SELECT_RAN
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? AND PCI.IDTIPORETRO = ?";
    
    String SQL_SELECT_RESOLUCION_BY_RFCTAB_RFCPROV_FI_FF = SELECT_RES
            + FROM_RESOLUCION
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? \n"
            + " AND RES.FOLIOINICIAL >= ? AND RES.FOLIOFINAL <= ?";
    
    String SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV = SELECT_RAN
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? \n"
            + ORDER_BY;
    
    String SQL_SELECT_RANGOS_BY_RFCTAB = SELECT_RAN
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? \n"
            + ORDER_BY;
    
    String SQL_SELECT_RANINVALIDOS_BY_RFCTAB_RFCPROV = "SELECT INV.*, RES.IDRESOLUCION, PCI.IDPRODCIGARRO, SOL.IDSOLICITUD \n"
            + "FROM TBCT_CODINVALIDO INV \n"
            + "INNER JOIN TBCT_RANGOFOLIO RAN ON INV.IDRANGOFOLIO = RAN.IDRANGOFOLIO \n"
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? \n"
            + "ORDER BY INV.FOLIOINICIAL, INV.FOLIOFINAL";
    
    String SQL_SELECT_RANINVALIDOS_BY_RFCTAB = "SELECT INV.*, RES.IDRESOLUCION, PCI.IDPRODCIGARRO, SOL.IDSOLICITUD \n"
            + "FROM TBCT_CODINVALIDO INV \n"
            + "INNER JOIN TBCT_RANGOFOLIO RAN ON INV.IDRANGOFOLIO = RAN.IDRANGOFOLIO \n"
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? \n"
            + "ORDER BY INV.FOLIOINICIAL, INV.FOLIOFINAL";
    
    String SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV_TIPORETRO_PRODUCCION = SELECT_RAN
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PRO.RFC = ? AND PCI.IDTIPORETRO IS NULL \n"
            + ORDER_BY;
    
    String SQL_SELECT_RANGOS_BY_RFCTAB_TIPORETRO_PRODUCCION = SELECT_RAN
            + JOIN_RESOLUCION_PROD
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE TAB.RFC = ? AND PCI.IDTIPORETRO IS NULL \n"
            + ORDER_BY;

    List<RangoFolio> validaRango(long folioInicial, long folioFinal, Long idResolucion) throws RangoFolioDaoException;

    RangoFolio validaRangoFolioPorRfc(String rfcTabacalera, long folioInicial, long folioFinal) throws RangoFolioDaoException;

    int guardar(RangoFolio rangoFolio) throws RangoFolioDaoException;

    List<RangoFolio> guardarRangosFolios(RangoFolio rangosFolio, ProduccionCigarros produccion) throws RangoFolioDaoException;

    RangoFolio createRangoFolio(RangoFolio rangoFolio) throws RangoFolioDaoException;

    List<RangoFolio> getRangosResolucion(String rfcTabacalera, String rfcProveedor, EstatusResolucion estResolucion, EstatusCargador estCargador) throws RangoFolioDaoException;

    List<RangoFolio> getRangosResolucion(String rfcTabacalera, EstatusResolucion estResolucion, EstatusCargador estCargador) throws RangoFolioDaoException;

    List<RangoFolio> getRangos(String rfcTabacalera, String rfcProveedor, TipoRetroEnum tipoRetro) throws RangoFolioDaoException;

    List<Resolucion> getResoluciones(String rfcTabacalera, String rfcProveedor, Long folioInicial, Long folioFinal) throws RangoFolioDaoException;

    List<RangoFolio> getRangos(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException;

    List<RangoFolio> getRangos(String rfcTabacalera) throws RangoFolioDaoException;

    List<RangoFolio> getRangosInvalidos(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException;

    List<RangoFolio> getRangosInvalidos(String rfcTabacalera) throws RangoFolioDaoException;

    List<RangoFolio> getRangosProduccion(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException;

    List<RangoFolio> getRangosProduccion(String rfcTabacalera) throws RangoFolioDaoException;
}
