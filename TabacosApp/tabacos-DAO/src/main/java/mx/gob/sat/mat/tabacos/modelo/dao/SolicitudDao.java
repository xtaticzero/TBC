/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.SolicitudDaoException;

/**
 *
 * @author MMMF
 */
public interface SolicitudDao {

    String SQL_SOLICITUD_TABACALERA = "SELECT IDSOLICITUD, RELACION.IDTABACALERA, CANTSOLICITADA, CANTAUTORIZADA, FECSOLICITUD," +
        " IDTIPOCONTRIB, IDPAISORIGEN, SOL.IDTBCPROV FROM" +
        " TBCT_SOLICITUD SOL,TBCA_TBC_PROV RELACION" +
        " WHERE  RELACION.IDTBCPROV = SOL.IDTBCPROV" +
        " AND RELACION.IDTABACALERA = ?";
    String SQL_SOLICITUD_PROVEEDOR = "SELECT IDSOLICITUD, IDTABACALERA, IDPROVEEDOR,CANTSOLICITADA, CANTAUTORIZADA, "
            + "FECSOLICITUD, IDTIPOCONTRIB, IDPAISORIGEN FROM TBCT_SOLICITUD WHERE IDPROVEEDOR = ?";
    String SQL_SOLICITUD_PROV_TAB = "SELECT IDSOLICITUD, IDTABACALERA, IDPROVEEDOR,CANTSOLICITADA, CANTAUTORIZADA, "
            + "FECSOLICITUD, IDTIPOCONTRIB, IDPAISORIGEN FROM TBCT_SOLICITUD WHERE IDPROVEEDOR = ? AND IDTABACALERA = ?";

    String SQL_SOLICITUD_RESOLUCION
            = "SELECT TBCT_SOLICITUD.IDSOLICITUD     AS IDSOLICITUD,\n"
            + "TBCP_TABACALERA.RFC                   AS RFC,\n"
            + "TBCT_PROVEEDOR.RFC                    AS PAS,\n"
            + "TBCT_SOLICITUD.CANTSOLICITADA         AS CANTSOLICITADA,\n"
            + "TBCT_SOLICITUD.CANTAUTORIZADA         AS CANTAUTORIZADA,\n"
            + "TBCT_SOLICITUD.FECSOLICITUD           AS FECSOLICITUD,\n"
            + "TBCT_SOLICITUD.IDPAISORIGEN           AS ORIGEN,\n"
            + "TBCC_ESTRESOLUCION.DESCESTRESOLUCION  AS RESOLUCION,\n"
            + "TBCT_RESOLUCION.FECRESOLUCION         AS FECRESOLUCION \n"
            + "FROM TBCT_SOLICITUD \n"
            + "INNER JOIN TBCT_RESOLUCION    ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_RESOLUCION.IDSOLICITUD\n"
            + "INNER JOIN TBCC_ESTRESOLUCION ON TBCT_RESOLUCION.IDESTRESOLUCION = TBCC_ESTRESOLUCION.IDESTRESOLUCION \n"
            + "INNER JOIN TBCA_TBC_PROV      ON TBCT_SOLICITUD.IDTBCPROV = TBCA_TBC_PROV.IDTBCPROV\n"
            + "INNER JOIN TBCP_TABACALERA    ON TBCA_TBC_PROV.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR     ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR\n"
            + "WHERE TBCP_TABACALERA.RFC = ? ORDER BY TBCT_SOLICITUD.IDSOLICITUD";

    String SQL_RESOLUCION_ARCHIVO
            = "SELECT \n"
            + "TBCT_SOLICITUD.IDSOLICITUD      AS FOLIO,\n"
            + "TBCP_TABACALERA.RFC             AS RFC,\n"
            + "TBCP_TABACALERA.RAZONSOCIAL     AS CONTRIBUYENTE,\n"
            + "TBCT_RESOLUCION.FECRESOLUCION   AS FECHARES,\n"
            + "TBCT_SOLICITUD.CANTAUTORIZADA   AS CANTAUTORIZADA,\n"
            + "TBCT_RESOLUCION.FOLIOINICIAL    AS FOLIOINICIAL,\n"
            + "TBCT_RESOLUCION.FOLIOFINAL      AS FOLIOFINAL,\n"
            + "TBCT_ARCHIVO.RUTAARCHIVO        AS RUTAARCHIVO,\n"
            + "TBCT_ARCHIVO.NUMLINEA           AS NUMLINEAS,\n"
            + "TBCT_ARCHIVO.IDARCHIVO          AS IDARCHIVO\n"
            + "FROM TBCT_SOLICITUD \n"
            + "INNER JOIN TBCT_RESOLUCION    ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_RESOLUCION.IDSOLICITUD\n"
            + "INNER JOIN TBCA_TBC_PROV      ON TBCT_SOLICITUD.IDTBCPROV = TBCA_TBC_PROV.IDTBCPROV\n"
            + "INNER JOIN TBCP_TABACALERA    ON TBCA_TBC_PROV.IDTABACALERA = TBCP_TABACALERA.IDTABACALERA\n"
            + "INNER JOIN TBCT_PROVEEDOR     ON TBCT_PROVEEDOR.IDPROVEEDOR = TBCA_TBC_PROV.IDPROVEEDOR\n"
            + "INNER JOIN TBCT_ARCHIVO       ON TBCT_SOLICITUD.IDSOLICITUD = TBCT_ARCHIVO.IDSOLICITUD\n"
            + "WHERE TBCP_TABACALERA.RFC = ? AND TBCT_ARCHIVO.IDTIPOARCHIVO = 1 "
            + "AND TBCT_SOLICITUD.IDSOLICITUD = ? "
            + "AND TBCT_RESOLUCION.IDESTRESOLUCION = ? "
            + "ORDER BY TBCT_ARCHIVO.RUTAARCHIVO";

    List<Solicitud> buscarPorProveedor(Long idProveedor) throws SolicitudDaoException;

    List<Solicitud> buscarPorTabacalera(Long idTabacalera) throws SolicitudDaoException;

    List<SolicitudResolucion> buscarSolicitudes(String rfcTabacalera) throws SolicitudDaoException;

    List<AutorizacionResol> buscarSolicitudesAutorizadas(String rfcTabacalera, Long idSolicitud, Long idEstResolucion) throws SolicitudDaoException;

}
