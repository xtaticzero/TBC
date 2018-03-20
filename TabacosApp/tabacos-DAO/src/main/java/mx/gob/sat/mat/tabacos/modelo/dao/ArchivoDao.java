/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface ArchivoDao {

    String SQL_INSERT_ARCHIVO_AUTORIZACION = "INSERT INTO \n"
            + "TBCT_ARCHIVO(IDARCHIVO,RUTAARCHIVO,FECREGISTRO,IDSOLICITUD,FECHAINICIO,IDTIPOARCHIVO,NUMOOFICIO) \n"
            + "VALUES (TBCQ_ARCHIVO.NEXTVAL,?,SYSDATE,?,SYSDATE,?,?)";

    int insertArchivoAtorizacionSol(Archivo archivo) throws DaoException;
}
