/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author root
 */
public interface CommonDao {

    String SQL_SELECT_NEXTVAL_ACUSE = "SELECT (NVL(MAX(FOLIOACUSE),0) + 1) NEXTVALUE FROM TBCT_ACUSE WHERE SERIEACUSE = ?";
    String SQL_INSERT_ACUSE = "INSERT INTO TBCT_ACUSE (SERIEACUSE, IDSOLICITUD,IDPROVEEDOR,IDMARCA, SELLODIGITAL, CADENAORIGINAL, FECCAPTURA, FOLIOACUSE) VALUES (?,?,?,?,?,?,?,?)";
    String SQL_INSERT_SOLICITUD = "INSERT INTO TBCT_SOLICITUD (IDSOLICITUD, CANTSOLICITADA, CANTAUTORIZADA, FECSOLICITUD, IDTIPOCONTRIB, IDPAISORIGEN, IDTBCPROV) VALUES (?,?,?,?,?,?,?)";
    String SQL_SELECT_NEXTVAL_SOLICITUD = "SELECT TBCQ_SOLICITUD.NEXTVAL FROM DUAL";
    String SQL_SELECT_SOLICITUD_BY_ID = "SELECT * FROM TBCT_SOLICITUD WHERE IDSOLICITUD = ?";
    String SQL_SELECT_RESOLUCION = "SELECT * FROM TBCT_RESOLUCION WHERE IDRESOLUCION = ?";
    String SQL_SELECT_ARCHIVOS = "SELECT * FROM TBCT_ARCHIVO WHERE IDSOLICITUD = ? AND IDTIPOARCHIVO = 1";
    String SQL_SELECT_NEXTVAL_RESOLUCION = "SELECT TBCQ_RESOLUCION.NEXTVAL FROM DUAL";
    String SQL_INSERT_RESOLUCION = "INSERT INTO TBCT_RESOLUCION (IDRESOLUCION, IDSOLICITUD, IDESTRESOLUCION, FECRESOLUCION, NUMSERVIDORPUBLICO, FOLIOINICIAL, FOLIOFINAL, FECHAINICIO, FECHAFIN, IDESTCARGADOR) VALUES (?,?,?,?,?,?,?,?,?,?)";
    String SQL_SELECT_NEXTVAL_PRODCIGARRO = "SELECT TBCQ_PRODCIGARRO.NEXTVAL FROM DUAL";
    String SQL_INSERT_PRODCIGARRO = "INSERT INTO TBCT_PRODCIGARRO (IDPRODCIGARRO, IDMARCA, DESCMAQUINAPROD, NUMLOTE, FECIMPORTACION, FECPRODUCCION, IDPAISORIGEN, DESCPAISORIGEN, CANTCIGARROS, CANTPRODUCIDA, LINEAPRODUCCION, IDPLANTA, IDTIPORETRO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String SQL_SELECT_RELACION_TAB_PROV = "SELECT REL.* FROM TBCA_TBC_PROV REL \n"
            + "INNER JOIN TBCP_TABACALERA TAB ON REL.IDTABACALERA = TAB.IDTABACALERA \n"
            + "INNER JOIN TBCT_PROVEEDOR PRO ON REL.IDPROVEEDOR = PRO.IDPROVEEDOR \n"
            + "WHERE PRO.RFC = ? AND TAB.RFC = ? AND REL.IDESTATUS = 1 AND TAB.IDESTATUS = 1 AND PRO.IDESTATUS = 1";
    
    String SQL_SELECT_NEXTVAL_CODIGOINVALIDO = "SELECT TBCQ_CODINVALIDO.NEXTVAL FROM DUAL";

    String SQL_INSERT_CODIGOINVALIDO = "INSERT INTO TBCT_CODINVALIDO (IDCODINVALIDO, IDRANGOFOLIO, FOLIOINICIAL, FOLIOFINAL, FECREGISTRO, FECCAPTURA, JUSTIFICACION) VALUES (?, ?, ?, ?, ?, ?, ?)";

    String SQL_SELECT_REPLEGAL_BY_RFC = "SELECT * FROM TBCT_REPLEGAL WHERE RFC = ?";

    RepresentanteLegal getRepLegalByRFC(String rfc) throws DaoException;

    Solicitud createSolicitud(Solicitud solicitud) throws DaoException;

    Solicitud getSolicitud(Long idSolicitud) throws DaoException;

    Resolucion getResolucion(Long idResolucion) throws DaoException;

    List<Archivo> getArchivos(Long idSolicitud) throws DaoException;

    Acuse createAcuse(Acuse acuse) throws DaoException;

    Resolucion createResolucion(Resolucion resolucion) throws DaoException;

    ProduccionCigarros createProduccionCigarro(ProduccionCigarros produccionCigarro) throws DaoException;

    Planta getPlanta(Long idPlanta) throws DaoException;

    RelacionTabProv getRelacionTabProv(String rfcProveedor, String rfcTabacalera) throws DaoException;

    CodigoInvalido createCodigoInvalido(CodigoInvalido codigoInvalido) throws DaoException;
    
}
