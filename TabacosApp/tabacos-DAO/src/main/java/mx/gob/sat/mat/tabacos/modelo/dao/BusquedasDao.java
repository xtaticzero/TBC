/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.DatosBusqueda;
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.exceptions.BusquedasDaoException;

/**
 *
 * @author root
 */
public interface BusquedasDao {

    String TB_CONTRIBUYENTE = "TBCH_TABACALERA";
    
    //cambio de resolucion.folioinicial a resolucion.idsolicitud
    String SQL_SELECT_HIST_CONTRIBUYENTES = "SELECT resolucion.idsolicitud AS FOLIO, contr.RFC as RFC, "
            + " contr.RAZONSOCIAL AS CONTRIBUYENTE, sol.cantautorizada as CANTIDAD, "
            + " sol.fecsolicitud as FECHA, prov.RFC as PAS "
            + " FROM TBCP_TABACALERA contr, "
            + " TBCT_PROVEEDOR prov, "
            + " TBCT_SOLICITUD sol, "
            + " TBCA_TBC_PROV prov_contr, "
            + " TBCT_RESOLUCION resolucion "
            + " where "
            + " prov_contr.IDTABACALERA = contr.IDTABACALERA "
            + " AND prov_contr.IDTBCPROV = sol.IDTBCPROV "
            + " AND prov_contr.IDPROVEEDOR = prov.IDPROVEEDOR "
            + " AND resolucion.IDSOLICITUD = sol.IDSOLICITUD "
            + " ORDER BY contr.RFC ";
    
    String SQL_SELECT_HIST_CONTRIBUYENTES_RFC = "SELECT resolucion.idsolicitud AS FOLIO, contr.RFC as RFC, "
            + " contr.RAZONSOCIAL AS CONTRIBUYENTE, sol.cantautorizada as CANTIDAD, "
            + " sol.fecsolicitud as FECHA, prov.RFC as PAS "
            + " FROM TBCP_TABACALERA contr, "
            + " TBCT_PROVEEDOR prov, "
            + " TBCT_SOLICITUD sol, "
            + " TBCA_TBC_PROV prov_contr, "
            + " TBCT_RESOLUCION resolucion "
            + " where "
            + " contr.rfc = ? "
            + " AND prov_contr.IDTABACALERA = contr.IDTABACALERA "
            + " AND prov_contr.IDTBCPROV = sol.IDTBCPROV "
            + " AND prov_contr.IDPROVEEDOR = prov.IDPROVEEDOR "
            + " AND resolucion.IDSOLICITUD = sol.IDSOLICITUD "
            + " ORDER BY contr.RFC ";

    String SQL_PROV_TEST = "SELECT IDN, STATUS, NOMBRE, RFC, BOID, FECHA, CORREO, TELEFONO, "
            + "TELEFONO2, DOMICILIO FROM TBCC_PROVEEDOR WHERE RFC LIKE ___%";
    String SQL_CONTRIBUYENTE = "";
    String SQL_PLANTAS = "SELECT IDPLANTA, IDTABACALERA, NOMPLANTA, FECREGISTRO, FECBAJA, IDESTATUS FROM TBCT_PLANTA";
    String SQL_CODIGOSFALSOS = "";
    String SQL_LOTEPRODUCCION = "";
    String SQL_FILTRO_SIN_CODIGO = "SELECT \n"
            + "DISTINCT TBCP_TABACALERA.RFC           AS CONTRIBUYENTE,\n"
            + "TBCT_PROVEEDOR.RFC            AS PROVEEDOR,\n"
            + "TBCT_MARCA.NOMMARCA           AS MARCA,\n"
            + "TBCT_MAQUINA.NOMMAQUINA       AS MAQUINA,\n"
            + "TBCT_PRODCIGARRO.NUMLOTE      AS LOTE,\n"
            + "TBCT_PLANTA.NOMPLANTA         AS PLANTA,\n"
            + "TBCT_RESOLUCION.FECRESOLUCION AS FECHA\n"
            + "FROM  TBCT_SOLICITUD\n"
            + "INNER JOIN TBCT_RESOLUCION ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_RESOLUCION.IDSOLICITUD\n"
            + "INNER JOIN TBCT_RANGOFOLIO ON TBCT_RESOLUCION.IDRESOLUCION = TBCT_RANGOFOLIO.IDRESOLUCION\n"
            + "INNER JOIN TBCT_PRODCIGARRO ON TBCT_RANGOFOLIO.IDPRODCIGARRO = TBCT_PRODCIGARRO.IDPRODCIGARRO\n"
            + "INNER JOIN TBCT_MARCA ON TBCT_PRODCIGARRO.IDMARCA = TBCT_MARCA.IDMARCA\n"
            + "INNER JOIN TBCA_TBC_PROV ON TBCT_SOLICITUD.IDTBCPROV = TBCA_TBC_PROV.IDTBCPROV\n"
            + "INNER JOIN TBCP_TABACALERA ON TBCP_TABACALERA.IDTABACALERA = TBCA_TBC_PROV.IDTABACALERA\n"
            + "INNER JOIN TBCT_MAQUINA ON  TBCT_MAQUINA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PLANTA ON TBCT_PLANTA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR";

    String SQL_FILTRO_CON_CODIGO = "SELECT \n"
            + "DISTINCT TBCP_TABACALERA.RFC           AS CONTRIBUYENTE,\n"
            + "TBCT_PROVEEDOR.RFC            AS PROVEEDOR,\n"
            + "TBCT_CODIGO.CODIGO           AS CODIGO,\n"
            + "TBCT_MARCA.NOMMARCA           AS MARCA,\n"
            + "TBCT_MAQUINA.NOMMAQUINA       AS MAQUINA,\n"
            + "TBCT_PRODCIGARRO.NUMLOTE      AS LOTE,\n"
            + "TBCT_PLANTA.NOMPLANTA         AS PLANTA,\n"
            + "TBCT_RESOLUCION.FECRESOLUCION AS FECHA\n"
            + "FROM  TBCT_SOLICITUD\n"
            + "INNER JOIN TBCT_CODIGO ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_CODIGO.IDSOLICITUD\n"
            + "INNER JOIN TBCT_RESOLUCION ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_RESOLUCION.IDSOLICITUD\n"
            + "INNER JOIN TBCT_RANGOFOLIO ON TBCT_RESOLUCION.IDRESOLUCION = TBCT_RANGOFOLIO.IDRESOLUCION\n"
            + "INNER JOIN TBCT_PRODCIGARRO ON TBCT_RANGOFOLIO.IDPRODCIGARRO = TBCT_PRODCIGARRO.IDPRODCIGARRO\n"
            + "INNER JOIN TBCT_MARCA ON TBCT_PRODCIGARRO.IDMARCA = TBCT_MARCA.IDMARCA\n"
            + "INNER JOIN TBCA_TBC_PROV ON TBCT_SOLICITUD.IDTBCPROV = TBCA_TBC_PROV.IDTBCPROV\n"
            + "INNER JOIN TBCP_TABACALERA ON TBCP_TABACALERA.IDTABACALERA = TBCA_TBC_PROV.IDTABACALERA\n"
            + "INNER JOIN TBCT_MAQUINA ON  TBCT_MAQUINA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PLANTA ON TBCT_PLANTA.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR";

    List<Historico> listarHistoricosContribuyente() throws BusquedasDaoException;
    
    List<Historico> listarHistoricosContribuyente(String rfc) throws BusquedasDaoException;

    List<DatosBusqueda> busquedaPorFiltros(DatosBusqueda filtro) throws BusquedasDaoException;

    List<Planta> getPlantas() throws BusquedasDaoException;
}
