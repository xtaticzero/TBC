/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.AutorizarSolicitudDao;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.AutorizarSolicitudSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PersistenceException;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Repository
@Transactional
@Qualifier("autorizarSolicitudDaoImpl")
public class AutorizarSolicitudDaoImpl implements AutorizarSolicitudDao {

    private static final Logger LOGGER
            = Logger.getLogger(AutorizarSolicitudDaoImpl.class);

    private static final int VAL1 = 1;
    private static final int VAL2 = 2;
    private static final int VAL3 = 3;
    private static final int VAL4 = 4;
    private static final int DIA_ADIC = 86400000;

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param strSQL
     * @param id
     * @param name
     * @return
     */
    @Override
    public List<SelectItem> getCombos(final String strSQL, final String id,
            final String name) {

        try {
            return jdbcTemplate.query(strSQL,
                    new RowMapper<SelectItem>() {
                        @Override
                        public SelectItem mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            SelectItem item = new SelectItem();
                            item.setId(resultSet.getString(id));
                            item.setValor(resultSet.getString(name));
                            return item;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PersistenceException("Error al obtener: " + strSQL
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     *
     * @param tipo
     * @param valor1
     * @param valor2
     * @return
     */
    @Override
    public List<SolicitudesRegistradas> obtenerSolicitudesRegistradas(
            int tipo, String valor1, Date valor2) throws DaoException{

        String strQuery = AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD;
        Object[] parametros = null;

        switch (tipo) {
            case VAL1:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_RFC;
                parametros = new Object[]{valor1};
                break;
            case VAL2:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_FOLIO;
                parametros = new Object[]{valor1};
                break;
            case VAL3:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_RSOCIAL;
                parametros = new Object[]{valor1};
                break;
            case VAL4:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_FECHA;
                Date parametro2 = new Date(valor2.getTime() + DIA_ADIC);
                parametros = new Object[]{valor2, parametro2};
                break;
            default:
                break;
        }

        try {
            return jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<SolicitudesRegistradas>() {
                        @Override
                        public SolicitudesRegistradas mapRow(ResultSet rs, int i)
                        throws SQLException {
                            SolicitudesRegistradas solicitud = new SolicitudesRegistradas();
                            solicitud.setFolio(rs.getString(Fields.FIELD_SOLAUT_FOLIO));
                            solicitud.setRfc(rs.getString(Fields.FIELD_SOLAUT_RFC));
                            solicitud.setFecha(rs.getString(Fields.FIELD_SOLAUT_FECHA));
                            solicitud.setRazonSocial(rs.getString(Fields.FIELD_SOLAUT_RAZONSOCIAL));
                            solicitud.setCantCodigos(rs.getLong(Fields.FIELD_SOLAUT_CANTCOD));
                            solicitud.setEnProceso(rs.getLong(Fields.FIELD_SOLAUT_PROC));
                            solicitud.setEstadoResolicion(rs.getString(Fields.FIELD_SOLAUT_RESOL));
                            return solicitud;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Error al obtener companias: "
                    + e.getMessage(), e);
        }
    }

    /**
     *
     * @param info
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException
     */
    @Override
    @Transactional
    public int autorizarSolicitud(SolitudAutorizada info) throws DaoException{
        Object[] params = {info.getEstatusResolucion(), info.getFecha(), info.getServidorPublicoAutorizante(),info.getFolio()};
        int[] types = {Types.INTEGER, Types.DATE, Types.BIGINT,Types.BIGINT};
        Object[] params2 = {info.getCantidadAutorizada(), info.getFolio()};
        int[] types2 = {Types.INTEGER, Types.BIGINT};
        int rows = 0;

        try {

            rows = jdbcTemplate.update(AutorizarSolicitudSQL.SQL_UPDATE_RESOLUCION, params, types);
            rows = jdbcTemplate.update(AutorizarSolicitudSQL.SQL_UPDATE_SOLICITUD, params2, types2);
            
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Error al obtener: "
                    + AutorizarSolicitudSQL.SQL_UPDATE_RESOLUCION
                    + "\n" + e.getMessage(), e);
        }
        return rows;
    }

    @Override
    public List<SolitudAutorizada> obtenerSolRegActual(int tipo, String valor1, Date valor2) {
        String strQuery = AutorizarSolicitudSQL.SQL_SELAUTSOLICITUDXAUT;
        Object[] parametros = null;

        switch (tipo) {
            case VAL1:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_RFC;
                parametros = new Object[]{valor1};
                break;
            case VAL2:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_FOLIO;
                parametros = new Object[]{valor1};
                break;
            case VAL3:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_RSOCIAL;
                parametros = new Object[]{valor1};
                break;
            case VAL4:
                strQuery += AutorizarSolicitudSQL.SQL_SELECT_AUTSOLICITUD_FECHA;
                Date parametro2 = new Date(valor2.getTime() + DIA_ADIC);
                parametros = new Object[]{valor2, parametro2};
                break;
            default:
                break;
        }

        try {
            return jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<SolitudAutorizada>() {
                        @Override
                        public SolitudAutorizada mapRow(ResultSet rs, int i)
                        throws SQLException {
                            SolitudAutorizada solicitud = new SolitudAutorizada();
                            solicitud.setFolio(rs.getString(Fields.FIELD_SOLAUT_FOLIO));
                            solicitud.setRfc(rs.getString(Fields.FIELD_SOLAUT_RFC));
                            solicitud.setCantidadSolicitud(rs.getLong("CANTSOLICITADA"));
                            solicitud.setCantidadAutorizada(rs.getLong("CANTAUTORIZADA"));
                            solicitud.setRazonSocial(rs.getString("RAZONSOCIAL"));
                            solicitud.setEstatusResolucion(rs.getInt("IDESTATUS"));
                            solicitud.setFecha(rs.getDate("FECRESOLUCION"));
                            solicitud.setServidorPublicoAutorizante(rs.getInt("IDSPAUTORIZA"));
                            solicitud.setDocumentoResolucion(rs.getString("RUTAARCHIVO"));
                            solicitud.setNumeroOficio(rs.getString("NUMOOFICIO"));
                            return solicitud;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PersistenceException("Error al obtener companias: "
                    + e.getMessage(), e);
        }
    }
}
