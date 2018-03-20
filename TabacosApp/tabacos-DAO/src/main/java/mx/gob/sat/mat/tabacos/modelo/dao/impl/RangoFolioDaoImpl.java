/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusCargador;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusResolucion;
import mx.gob.sat.mat.tabacos.constants.enums.TipoRetroEnum;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.FECRETROALIMEN;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.FOLIOFINAL;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.FOLIOINICIAL;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.IDESTATUS;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.IDPRODCIGARRO;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.IDRANGOFOLIO;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.IDRESOLUCION;
import static mx.gob.sat.mat.tabacos.modelo.dao.PacDao.IDSOLICITUD;
import mx.gob.sat.mat.tabacos.modelo.dao.RangoFolioDao;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mmmf
 */
@Repository
@Transactional
@Qualifier("rangoFolioDaoImpl")
public class RangoFolioDaoImpl implements RangoFolioDao {

    protected static final Logger LOGGER = Logger.getLogger(RangoFolioDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RangoFolio> validaRango(long folioInicial, long folioFinal, Long idResolucion) throws RangoFolioDaoException {

        Object[] sqlParams = {idResolucion, folioInicial, folioFinal};

        return jdbcTemplate.query(SELECT_RANGOFOLIO_IDRESOLUCION, new RangoFolioMapper(), sqlParams);
    }

    @Override
    public RangoFolio validaRangoFolioPorRfc(String rfcTabacalera, long folioInicial, long folioFinal) throws RangoFolioDaoException {

        Object[] sqlParams = {rfcTabacalera, folioInicial, folioFinal};

        List<RangoFolio> rangos = jdbcTemplate.query(SELECT_RANGOFOLIO_TABACALERA, new RangoFolioMapper(), sqlParams);
        if (rangos != null && !rangos.isEmpty()) {
            return rangos.get(0);
        }
        return null;
    }

    @Override
    public int guardar(RangoFolio rangoFolio) throws RangoFolioDaoException {
        //guarda en BD
        final Long id = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_RANGOFOLIO, Long.class);
        rangoFolio.setIdRangoFolios(id);
        int r = 0;
        if (rangoFolio.getIdRangoFolios() != null) {
            r = jdbcTemplate.update(INSERT_RANGOFOLIO,
                    new Object[]{
                        rangoFolio.getIdRangoFolios(),
                        rangoFolio.getIdProdCigarro(),
                        rangoFolio.getFolioInicial(),
                        rangoFolio.getFolioFinal(),
                        rangoFolio.getFechaRetroalimentacion(),
                        rangoFolio.getEstado(),
                        rangoFolio.getIdResolucion()
                    });
            LOGGER.info("Registros afectados en RangoFolioDao: " + r);
        }
        return r;
    }

    private static class RangoFolioMapper implements ParameterizedRowMapper<RangoFolio> {

        @Override
        public RangoFolio mapRow(ResultSet rs, int row) throws SQLException {
            RangoFolio rangoF = new RangoFolio();
            //si es 1 el estatus esta activo
            rangoF.setEstado(rs.getInt("IDESTATUS"));

            rangoF.setFolioInicial(rs.getLong("FOLIOINICIAL"));
            rangoF.setFolioFinal(rs.getLong("FOLIOFINAL"));
            rangoF.setIdRangoFolios(rs.getLong("IDRANGOFOLIO"));

            return rangoF;
        }
    }

    /**
     *
     * @param rangosFolio
     * @param produccion
     * @return List RangoFolio
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.RangoFolioDaoException
     */
    @Override
    public List<RangoFolio> guardarRangosFolios(RangoFolio rangosFolio, ProduccionCigarros produccion) throws RangoFolioDaoException {
        List<RangoFolio> rangosFolioList = new ArrayList<RangoFolio>();
        try {
            final Long idRangoFolio = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_RANGOFOLIO, Long.class);
            rangosFolio.setIdRangoFolios(idRangoFolio);
            rangosFolio.setFechaRetroalimentacion(new Date());
            rangosFolio.setIdEstatus(EstatusEnum.ALTA);

            jdbcTemplate.update(SQL_INSERT_RANGOFOLIO,
                    new Object[]{
                        rangosFolio.getIdRangoFolios(),
                        rangosFolio.getIdProdCigarro(),
                        rangosFolio.getFolioInicial(),
                        rangosFolio.getFolioFinal(),
                        rangosFolio.getFechaRetroalimentacion(),
                        rangosFolio.getIdEstatus(),
                        rangosFolio.getIdResolucion()
                    });
            rangosFolioList.add(rangosFolio);
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los Rangos folios" + e.getMessage(), e);
            throw new RangoFolioDaoException(e.getMessage(), e);
        }
        return rangosFolioList;
    }

    /**
     *
     * @param rangoFolio
     * @return
     * @throws RangoFolioDaoException
     */
    @Override
    public RangoFolio createRangoFolio(RangoFolio rangoFolio) throws RangoFolioDaoException {
        try {
            Long idRangoFolio = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_RANGOFOLIO, Long.class);
            rangoFolio.setIdRangoFolios(idRangoFolio);
            jdbcTemplate.update(SQL_INSERT_RANGOFOLIO,
                    new Object[]{
                        rangoFolio.getIdRangoFolios(),
                        rangoFolio.getIdProdCigarro(),
                        rangoFolio.getFolioInicial(),
                        rangoFolio.getFolioFinal(),
                        rangoFolio.getFechaRetroalimentacion(),
                        rangoFolio.getEstado(),
                        rangoFolio.getIdResolucion()
                    });

            return rangoFolio;
        } catch (Exception e) {
            String msgError = "ERROR AL CREAR RANGOFOLIO";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangosResolucion(String rfcTabacalera, String rfcProveedor, EstatusResolucion estResolucion, EstatusCargador estCargador) throws RangoFolioDaoException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));
            String query = SQL_SELECT_RANGOSRESOLUCION_BY_RFCTAB_RFCPROV;
            Object params[] = new Object[]{rfcTabacalera, rfcProveedor, estResolucion.getKey(), estCargador.getKey()};
            if (isEmpty) {
                query = SQL_SELECT_RANGOSRESOLUCION_BY_RFCTAB;
                params = new Object[]{rfcTabacalera, estResolucion.getKey(), estCargador.getKey()};
            }

            return (List<RangoFolio>) jdbcTemplate.query(query, params, new RowMapper<RangoFolio>() {

                @Override
                public RangoFolio mapRow(ResultSet rs, int i) throws SQLException {
                    RangoFolio rangoFolio = new RangoFolio();
                    rangoFolio.setIdResolucion(rs.getLong(IDRESOLUCION));
                    rangoFolio.setFolioInicial(rs.getLong(FOLIOINICIAL));
                    rangoFolio.setFolioFinal(rs.getLong(FOLIOFINAL));
                    rangoFolio.setIdSolicitud(rs.getLong(IDSOLICITUD));
                    return rangoFolio;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RANGOS RESOLUCION";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangosResolucion(String rfcTabacalera, EstatusResolucion estResolucion, EstatusCargador estCargador) throws RangoFolioDaoException {
        return this.getRangosResolucion(rfcTabacalera, null, estResolucion, estCargador);
    }

    /**
     *
     * @param rfcTabacalera
     * @param rfcProveedor
     * @param tipoRetro
     * @return
     * @throws RangoFolioDaoException
     */
    @Override
    public List<RangoFolio> getRangos(String rfcTabacalera, String rfcProveedor, TipoRetroEnum tipoRetro) throws RangoFolioDaoException {
        try {
            return (List<RangoFolio>) jdbcTemplate.query(SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV_TIPORETRO, new Object[]{rfcTabacalera, rfcProveedor, tipoRetro.getKey()}, new RowMapper<RangoFolio>() {

                @Override
                public RangoFolio mapRow(ResultSet rs, int i) throws SQLException {
                    RangoFolio rangoFolio = new RangoFolio();
                    rangoFolio.setIdRangoFolios(rs.getLong(IDRANGOFOLIO));
                    rangoFolio.setIdProdCigarro(rs.getLong(IDPRODCIGARRO));
                    rangoFolio.setFolioInicial(rs.getLong(FOLIOINICIAL));
                    rangoFolio.setFolioFinal(rs.getLong(FOLIOFINAL));
                    rangoFolio.setFechaRetroalimentacion(rs.getDate(FECRETROALIMEN));
                    rangoFolio.setFolioFinal(rs.getLong(IDESTATUS));
                    rangoFolio.setIdResolucion(rs.getLong(IDRESOLUCION));
                    rangoFolio.setIdSolicitud(rs.getLong(IDSOLICITUD));
                    return rangoFolio;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RANGOS";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<Resolucion> getResoluciones(String rfcTabacalera, String rfcProveedor, Long folioInicial, Long folioFinal) throws RangoFolioDaoException {
        try {
            return (List<Resolucion>) jdbcTemplate.query(SQL_SELECT_RESOLUCION_BY_RFCTAB_RFCPROV_FI_FF, new Object[]{rfcTabacalera, rfcProveedor, folioInicial, folioFinal}, new BeanPropertyRowMapper(Resolucion.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RESOLUCIONES";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangos(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));
            String query = SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV;
            Object params[] = new Object[]{rfcTabacalera, rfcProveedor};
            if (isEmpty) {
                query = SQL_SELECT_RANGOS_BY_RFCTAB;
                params = new Object[]{rfcTabacalera};
            }

            return (List<RangoFolio>) jdbcTemplate.query(query, params, new RowMapper<RangoFolio>() {

                @Override
                public RangoFolio mapRow(ResultSet rs, int i) throws SQLException {
                    RangoFolio rangoFolio = new RangoFolio();
                    rangoFolio.setIdRangoFolios(rs.getLong(IDRANGOFOLIO));
                    rangoFolio.setIdProdCigarro(rs.getLong(IDPRODCIGARRO));
                    rangoFolio.setFolioInicial(rs.getLong(FOLIOINICIAL));
                    rangoFolio.setFolioFinal(rs.getLong(FOLIOFINAL));
                    rangoFolio.setFechaRetroalimentacion(rs.getDate(FECRETROALIMEN));
                    rangoFolio.setEstado(rs.getInt(IDESTATUS));
                    rangoFolio.setIdResolucion(rs.getLong(IDRESOLUCION));
                    rangoFolio.setIdSolicitud(rs.getLong(IDSOLICITUD));
                    return rangoFolio;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RANGOS";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangos(String rfcTabacalera) throws RangoFolioDaoException {
        return this.getRangos(rfcTabacalera, null);
    }

    @Override
    public List<RangoFolio> getRangosInvalidos(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));
            String query = SQL_SELECT_RANINVALIDOS_BY_RFCTAB_RFCPROV;
            Object params[] = new Object[]{rfcTabacalera, rfcProveedor};
            if (isEmpty) {
                query = SQL_SELECT_RANINVALIDOS_BY_RFCTAB;
                params = new Object[]{rfcTabacalera};
            }

            return (List<RangoFolio>) jdbcTemplate.query(query, params, new RowMapper<RangoFolio>() {

                @Override
                public RangoFolio mapRow(ResultSet rs, int i) throws SQLException {
                    RangoFolio rangoFolio = new RangoFolio();
                    rangoFolio.setIdRangoFolios(rs.getLong(IDRANGOFOLIO));
                    rangoFolio.setIdProdCigarro(rs.getLong(IDPRODCIGARRO));
                    rangoFolio.setFolioInicial(rs.getLong(FOLIOINICIAL));
                    rangoFolio.setFolioFinal(rs.getLong(FOLIOFINAL));
                    rangoFolio.setIdResolucion(rs.getLong(IDRESOLUCION));
                    rangoFolio.setIdSolicitud(rs.getLong(IDSOLICITUD));
                    return rangoFolio;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RANGOS INVALIDOS";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangosInvalidos(String rfcTabacalera) throws RangoFolioDaoException {
        return this.getRangosInvalidos(rfcTabacalera, null);
    }

    @Override
    public List<RangoFolio> getRangosProduccion(String rfcTabacalera, String rfcProveedor) throws RangoFolioDaoException {
        try {
            boolean isEmpty = ((rfcProveedor != null && rfcProveedor.isEmpty()) || (rfcProveedor == null));
            String query = SQL_SELECT_RANGOS_BY_RFCTAB_RFCPROV_TIPORETRO_PRODUCCION;
            Object params[] = new Object[]{rfcTabacalera, rfcProveedor};
            if (isEmpty) {
                query = SQL_SELECT_RANGOS_BY_RFCTAB_TIPORETRO_PRODUCCION;
                params = new Object[]{rfcTabacalera};
            }

            return (List<RangoFolio>) jdbcTemplate.query(query, params, new RowMapper<RangoFolio>() {

                @Override
                public RangoFolio mapRow(ResultSet rs, int i) throws SQLException {
                    RangoFolio rangoFolio = new RangoFolio();
                    rangoFolio.setIdRangoFolios(rs.getLong(IDRANGOFOLIO));
                    rangoFolio.setIdProdCigarro(rs.getLong(IDPRODCIGARRO));
                    rangoFolio.setFolioInicial(rs.getLong(FOLIOINICIAL));
                    rangoFolio.setFolioFinal(rs.getLong(FOLIOFINAL));
                    rangoFolio.setFechaRetroalimentacion(rs.getDate(FECRETROALIMEN));
                    rangoFolio.setEstado(rs.getInt(IDESTATUS));
                    rangoFolio.setIdResolucion(rs.getLong(IDRESOLUCION));
                    rangoFolio.setIdSolicitud(rs.getLong(IDSOLICITUD));
                    return rangoFolio;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER RANGOS PRODUCCION";
            LOGGER.error(msgError, e);
            throw new RangoFolioDaoException(msgError, e);
        }
    }

    @Override
    public List<RangoFolio> getRangosProduccion(String rfcTabacalera) throws RangoFolioDaoException {
        return this.getRangosProduccion(rfcTabacalera, null);
    }
}
