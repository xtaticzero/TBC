/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PacDaoException;

/**
 *
 * @author root
 */
public interface PacDao {

    String IDRANGOFOLIO = "IDRANGOFOLIO";
    String IDPRODCIGARRO = "IDPRODCIGARRO";
    String FOLIOINICIAL = "FOLIOINICIAL";
    String FOLIOFINAL = "FOLIOFINAL";
    String FECRETROALIMEN = "FECRETROALIMEN";
    String IDESTATUS = "IDESTATUS";
    String IDRESOLUCION = "IDRESOLUCION";
    String IDSOLICITUD = "IDSOLICITUD";
    
    String SELECT_RES = "SELECT RES.* \n";
    
    String FROM_RESOLUCION = "FROM TBCT_RESOLUCION RES \n";
    
    String JOIN_SOLICITUD_TAB_PROV = " INNER JOIN TBCT_SOLICITUD SOL ON RES.IDSOLICITUD = SOL.IDSOLICITUD \n"
            + "INNER JOIN TBCA_TBC_PROV REL ON SOL.IDTBCPROV = REL.IDTBCPROV \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON TAB.IDTABACALERA = REL.IDTABACALERA \n"
            + "INNER JOIN TBCT_PROVEEDOR PRO ON PRO.IDPROVEEDOR = REL.IDPROVEEDOR \n";
    
    String SQL_SELECT_RESOLUCION_BY_IDSOL_RFCTAB_RFCPROV = SELECT_RES
            + FROM_RESOLUCION
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE SOL.IDSOLICITUD = ? AND TAB.RFC = ? AND PRO.RFC = ?";

    String SQL_SELECT_ARCHIVO_BY_IDSOL_RFCPROV_NOMARC = SELECT_RES
            + "FROM TBCT_ARCHIVO RES \n"
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE SOL.IDSOLICITUD = ? AND TAB.RFC = ? \n"
            + "AND SUBSTR(RES.RUTAARCHIVO, (INSTR(RES.RUTAARCHIVO, '/', -1) + 1), LENGTH(RES.RUTAARCHIVO)) =  ?";

    String SQL_SELECT_SOLICITUD_BY_RFCTAB_RFCPROV_FI_FF = "SELECT SOL.* " + FROM_RESOLUCION
            + JOIN_SOLICITUD_TAB_PROV
            + "WHERE PRO.RFC = ? AND TAB.RFC = ? \n"
            + "  AND RES.FOLIOINICIAL >= ? AND RES.FOLIOFINAL <= ? AND RES.IDESTRESOLUCION = 5 AND RES.IDESTCARGADOR = 5";

    String SQL_SELECT_PLANTA_BY_RFCTAB_NOMPLAN = "SELECT PLN.* FROM TBCT_PLANTA PLN \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON PLN.IDTABACALERA = TAB.IDTABACALERA \n"
            + "WHERE TAB.RFC = ? AND PLN.NOMPLANTA = ?";

    String SQL_SELECT_MARCA_BY_RFCTAB_CLAMAR = "SELECT MAR.* FROM TBCT_MARCA MAR \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON MAR.IDTABACALERA = TAB.IDTABACALERA \n"
            + "WHERE TAB.RFC = ? AND MAR.CLAVE = ?";
    

    Resolucion getResolucion(Long idSolicitud, String rfcTabacalera, String rfcProveedor) throws PacDaoException;

    Archivo getArchivo(Long idSolicitud, String rfcTabacalera, String nombreArchivo) throws PacDaoException;

    Solicitud getSolicitud(String rfcProveedor, String rfcTabacalera, Long folioInicial, Long folioFinal) throws PacDaoException;

    Planta getPlanta(String rfcTabacalera, String nombrePlanta) throws PacDaoException;

    Marcas getMarca(String rfcTabacalera, String clave) throws PacDaoException;
    
}
