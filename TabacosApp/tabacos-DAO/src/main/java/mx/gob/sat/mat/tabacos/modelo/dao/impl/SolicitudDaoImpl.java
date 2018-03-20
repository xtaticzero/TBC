/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao;
import static mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao.SQL_RESOLUCION_ARCHIVO;
import static mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao.SQL_SOLICITUD_PROVEEDOR;
import static mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao.SQL_SOLICITUD_RESOLUCION;
import static mx.gob.sat.mat.tabacos.modelo.dao.SolicitudDao.SQL_SOLICITUD_TABACALERA;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.SolicitudAutorisadaMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.SolicitudResolucionMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.SolicitudDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MMMF
 */
@Repository
@Transactional
@Qualifier("solicitudDaoImpl")
public class SolicitudDaoImpl implements SolicitudDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Solicitud> buscarPorProveedor(Long idProveedor) throws SolicitudDaoException {

        List<Solicitud> solicitudes;
        Object[] sqlParams = {idProveedor};

        solicitudes = jdbcTemplate.query(SQL_SOLICITUD_PROVEEDOR, new SolicitudMapper(), sqlParams);

        return solicitudes;
    }

    @Override
    public List<Solicitud> buscarPorTabacalera(Long idTabacalera) {
        List<Solicitud> solicitudes;
        Object[] sqlParams = {idTabacalera};

        solicitudes = jdbcTemplate.query(SQL_SOLICITUD_TABACALERA, new SolicitudMapper(), sqlParams);

        return solicitudes;
    }

    @Override
    public List<SolicitudResolucion> buscarSolicitudes(String rfcTabacalera) throws SolicitudDaoException {
        Object[] sqlParams = {rfcTabacalera};

        return jdbcTemplate.query(SQL_SOLICITUD_RESOLUCION, new SolicitudResolucionMapper(), sqlParams);
    }

    @Override
    public List<AutorizacionResol> buscarSolicitudesAutorizadas(String rfcTabacalera, Long idSolicitud, Long idEstResolucion) throws SolicitudDaoException {
        Object[] sqlParams = {rfcTabacalera, idSolicitud, idEstResolucion};
        return jdbcTemplate.query(SQL_RESOLUCION_ARCHIVO, new SolicitudAutorisadaMapper(), sqlParams);
    }

    private static class SolicitudMapper implements ParameterizedRowMapper<Solicitud> {

        @Override
        public Solicitud mapRow(ResultSet rs, int row) throws SQLException {
            Solicitud solicitud = new Solicitud();

            solicitud.setIdSolicitud(rs.getLong("IDSOLICITUD"));
            solicitud.setCantSolicitada(rs.getLong("CANTSOLICITADA"));
            solicitud.setCantAutorizada(rs.getLong("CANTAUTORIZADA"));
            solicitud.setFecSolicitud(rs.getDate("FECSOLICITUD"));
            solicitud.setIdTipoContrib(rs.getLong("IDTIPOCONTRIB"));

            return solicitud;
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
