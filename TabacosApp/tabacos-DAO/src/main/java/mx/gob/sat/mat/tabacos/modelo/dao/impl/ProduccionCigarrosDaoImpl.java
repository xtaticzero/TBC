package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ProduccionCigarrosDao;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.ReporteDesperdiciosMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.ReporteProduccioMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteDesperdiciosDTO;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteProduccion;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoRetro;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Repository
@Transactional
@Qualifier("produccionDaoImpl")
public class ProduccionCigarrosDaoImpl implements ProduccionCigarrosDao {

    private static final Logger LOGGER = Logger.getLogger(ProduccionCigarrosDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param idTabacalera
     * @return plantaProduccion
     * @throws ProduccionDaoException
     */
    @Override
    public List<Planta> selectedPlantas(Long idTabacalera) throws ProduccionDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PLANTAS,
                    new Object[]{idTabacalera},
                    new RowMapper<Planta>() {

                        @Override
                        public Planta mapRow(ResultSet resulSet, int i) throws SQLException {
                            Planta planta = new Planta();
                            planta.setIdPlanta(resulSet.getLong(Fields.FIELD_PLANTA_ID));
                            planta.setNombrePlanta(resulSet.getString(Fields.FIELD_PLANTA_NOMBRE));

                            return planta;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar las Plantas de produccion de cigarros " + e.getMessage(), e);
            throw new ProduccionDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List TipoRetro
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException
     */
    @Override
    public List<TipoRetro> selectedTipoRetro() throws ProduccionDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_TIPO_RETRO, new RowMapper<TipoRetro>() {

                @Override
                public TipoRetro mapRow(ResultSet resulSet, int i) throws SQLException {
                    TipoRetro tipo = new TipoRetro();

                    tipo.setIdTipoRetro(resulSet.getLong(Fields.FIELD_TIPO_RETRO_ID));
                    tipo.setDescTipoRetro(resulSet.getString(Fields.FIELD_TIPO_RETRO_DESCTIPORETRO));
                    return tipo;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar el Tipo Retro " + e.getMessage(), e);
            throw new ProduccionDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param produccion
     * @return produccion
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException
     */
    @Override
    public ProduccionCigarros guardaProduccion(ProduccionCigarros produccion) throws ProduccionDaoException {
        try {
            final Long idProduccion = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_PRODUCCION, Long.class);
            produccion.setIdProdCigarro(idProduccion);

            jdbcTemplate.update(SQL_INSERT_PRODUCCION_CIGARROS,
                    new Object[]{
                        idProduccion,
                        produccion.getIdMarca(),
                        produccion.getDescMaquinaProd(),
                        produccion.getNumLote(),
                        produccion.getFechImportacion(),
                        produccion.getFechProduccion(),
                        produccion.getIdPaisOrigen(),
                        produccion.getDescPaisOrigen(),
                        produccion.getCantidadProd(),
                        produccion.getIdPlantaProd()
                    });
            return produccion;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los datos de Produccion de cigarros" + e.getMessage(), e);
            throw new ProduccionDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param produccion
     * @return Produccion
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException
     */
    @Override
    public ProduccionCigarros guardarDestruccion(ProduccionCigarros produccion) throws ProduccionDaoException {
        try {
            final Long idProduccion = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_PRODUCCION, Long.class);
            produccion.setIdProdCigarro(idProduccion);

            jdbcTemplate.update(SQL_INSERT_DESPERDICIOS_DESTRUCCION,
                    new Object[]{
                        idProduccion,
                        produccion.getIdMarca(),
                        produccion.getDescMaquinaProd(),
                        produccion.getNumLote(),
                        produccion.getFechProduccion(),
                        produccion.getIdPaisOrigen(),
                        produccion.getDescPaisOrigen(),
                        produccion.getCantidadCigarros(),
                        produccion.getCantidadProd(),
                        produccion.getIdPlantaProd(),
                        produccion.getIdTipoRetro()
                    });
            return produccion;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los datos de Desperdicios - Destruccion" + e.getMessage(), e);
            throw new ProduccionDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param reporteDesperdicios
     * @param rfcExiste
     * @return List<ReporteDesperdicios>
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException
     */
    @Override
    public List<ReporteDesperdiciosDTO> consultaReporteDesperdicios(ReporteDesperdiciosDTO reporteDesperdicios, boolean rfcExiste) throws ProduccionDaoException {
        try {
            StringBuilder queryDinamico;
            queryDinamico = new StringBuilder(SQL_CONSULTA_DATOS_REPORTE_DESPERDICIOS);

            if (rfcExiste) {
                queryDinamico.append(SQL_CONSULTA_DATOS_REPORTE_DESPERDICIOS_POR_RFC);
                return jdbcTemplate.query(queryDinamico.toString(),
                        new Object[]{reporteDesperdicios.getRfc()},
                        new ReporteDesperdiciosMapper());

            } else {
                queryDinamico.append(SQL_CONSULTA_DATOS_REPORTE_DESPERDICIOS_POR_FECHAS);
                return jdbcTemplate.query(queryDinamico.toString(),
                        new Object[]{reporteDesperdicios.getFechaInicio(), reporteDesperdicios.getFechaFin()},
                        new ReporteDesperdiciosMapper());
            }
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            throw new ProduccionDaoException(e);
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los datos del reporte Desperdicicion y destruccion : " + e.getMessage(), e);
            throw new ProduccionDaoException(e);
        }
    }

    /**
     *
     * @param reporteProduccion
     * @return ArrayList<ReporteProduccion>()
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException
     */
    @Override
    public List<ReporteProduccion> consultaReporteProduccion(ReporteProduccion reporteProduccion) throws ProduccionDaoException {
        try {
            StringBuilder queryDinamico;
            queryDinamico = new StringBuilder(SQL_CONSULTA_DATOS_REPORTE_PRODUCCION);

            if (reporteProduccion.getRfc() != null) {
                queryDinamico.append(SQL_CONSULTA_DATOS_REPORTE_PRODUCCION_POR_RFC);

                if (reporteProduccion.getIdProveedor() != null) {
                    queryDinamico.append(SQL_CONSULTA_REPORTE_PRODUCCION_RAZON_SOCIAL);
                    queryDinamico.append(SQL_ORDER_IDPRODCIGARRO);
                    return jdbcTemplate.query(queryDinamico.toString(),
                            new Object[]{reporteProduccion.getRfc(), reporteProduccion.getIdProveedor()},
                            new ReporteProduccioMapper());
                }
                queryDinamico.append(SQL_ORDER_IDPRODCIGARRO);
                return jdbcTemplate.query(queryDinamico.toString(),
                        new Object[]{reporteProduccion.getRfc()},
                        new ReporteProduccioMapper());
            } else {
                queryDinamico.append(SQL_CONSULTA_DATOS_REPORTE_PRODUCCION_POR_FECHAS);

                if (reporteProduccion.getIdProveedor() != null) {
                    queryDinamico.append(SQL_CONSULTA_REPORTE_PRODUCCION_RAZON_SOCIAL);
                    queryDinamico.append(SQL_ORDER_IDPRODCIGARRO);
                    return jdbcTemplate.query(queryDinamico.toString(),
                            new Object[]{reporteProduccion.getFechaInicio(), reporteProduccion.getFechaFin(), reporteProduccion.getIdProveedor()},
                            new ReporteProduccioMapper());
                }
                queryDinamico.append(SQL_ORDER_IDPRODCIGARRO);
                return jdbcTemplate.query(queryDinamico.toString(),
                        new Object[]{reporteProduccion.getFechaInicio(), reporteProduccion.getFechaFin()},
                        new ReporteProduccioMapper());
            }
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            throw new ProduccionDaoException(e);
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los datos del Reporte de Produccion : " + e.getMessage(), e);
            throw new ProduccionDaoException(e);
        }
    }

}
