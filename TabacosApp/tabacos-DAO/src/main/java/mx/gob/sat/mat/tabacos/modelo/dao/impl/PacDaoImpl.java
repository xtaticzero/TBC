/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dao.PacDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PacDaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Repository
@Transactional
@Qualifier("pacDao")
public class PacDaoImpl implements PacDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(PacDaoImpl.class);

    @Override
    public Resolucion getResolucion(Long idSolicitud, String rfcTabacalera, String rfcProveedor) throws PacDaoException {
        try {
            return (Resolucion) jdbcTemplate.queryForObject(SQL_SELECT_RESOLUCION_BY_IDSOL_RFCTAB_RFCPROV, new Object[]{idSolicitud, rfcTabacalera, rfcProveedor}, new BeanPropertyRowMapper(Resolucion.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA RESOLUCION";
            LOGGER.error(msgError, e);
            throw new PacDaoException(msgError, e);
        }
    }

    @Override
    public Archivo getArchivo(Long idSolicitud, String rfcTabacalera, String nombreArchivo) throws PacDaoException {
        try {
            return (Archivo) jdbcTemplate.queryForObject(SQL_SELECT_ARCHIVO_BY_IDSOL_RFCPROV_NOMARC, new Object[]{idSolicitud, rfcTabacalera, nombreArchivo}, new BeanPropertyRowMapper(Archivo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER EL ARCHIVO";
            LOGGER.error(msgError, e);
            throw new PacDaoException(msgError, e);
        }
    }

    @Override
    public Solicitud getSolicitud(String rfcProveedor, String rfcTabacalera, Long folioInicial, Long folioFinal) throws PacDaoException {
        try {
            return (Solicitud) jdbcTemplate.queryForObject(SQL_SELECT_SOLICITUD_BY_RFCTAB_RFCPROV_FI_FF, new Object[]{rfcProveedor, rfcTabacalera, folioInicial, folioFinal}, new BeanPropertyRowMapper(Solicitud.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA SOLICITUD";
            LOGGER.error(msgError, e);
            throw new PacDaoException(msgError, e);
        }
    }

    @Override
    public Planta getPlanta(String rfcTabacalera, String nombrePlanta) throws PacDaoException {
        try {
            return (Planta) jdbcTemplate.queryForObject(SQL_SELECT_PLANTA_BY_RFCTAB_NOMPLAN, new Object[]{rfcTabacalera, nombrePlanta}, new BeanPropertyRowMapper(Planta.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA PLANTA";
            LOGGER.error(msgError, e);
            throw new PacDaoException(msgError, e);
        }
    }

    @Override
    public Marcas getMarca(String rfcTabacalera, String clave) throws PacDaoException {
        try {
            return (Marcas) jdbcTemplate.queryForObject(SQL_SELECT_MARCA_BY_RFCTAB_CLAMAR, new Object[]{rfcTabacalera, clave}, new RowMapper<Marcas>() {

                @Override
                public Marcas mapRow(ResultSet rs, int i) throws SQLException {
                    Marcas marcas = new Marcas();
                    marcas.setIdMarca(rs.getLong("IDMARCA"));
                    marcas.setClave(rs.getString("CLAVE"));
                    marcas.setMarca(rs.getString("NOMMARCA"));
                    marcas.setFechaInicio(rs.getDate("FECINICIO"));
                    marcas.setFechaFin(rs.getDate("FECFIN"));
                    marcas.setIdEstatus(rs.getLong(IDESTATUS));
                    marcas.setRutaArchivo(rs.getString("RUTAARCHIVO"));
                    marcas.setIdTabacalera(rs.getLong("IDTABACALERA"));

                    return marcas;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA MARCA";
            LOGGER.error(msgError, e);
            throw new PacDaoException(msgError, e);
        }
    }
}
