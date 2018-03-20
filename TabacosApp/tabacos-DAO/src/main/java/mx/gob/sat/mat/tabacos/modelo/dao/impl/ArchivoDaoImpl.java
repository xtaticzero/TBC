/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import mx.gob.sat.mat.tabacos.modelo.dao.ArchivoDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
@Qualifier("archivoDao")
public class ArchivoDaoImpl implements ArchivoDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(CommonDaoImpl.class);

    @Override
    @Transactional
    public int insertArchivoAtorizacionSol(Archivo archivo) throws DaoException {
        try {
            return jdbcTemplate.update(SQL_INSERT_ARCHIVO_AUTORIZACION,
                    new Object[]{
                        archivo.getRutaArchivo(),
                        archivo.getIdSolicitud(),
                        archivo.getIdTipoArchivo(),
                        archivo.getNumeroOficio()
                    });
        } catch (DataAccessException dex) {
            LOGGER.error(dex);
            throw new DaoException(dex);
        }
    }

}
