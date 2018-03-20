package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import static mx.gob.sat.mat.tabacos.constants.Constantes.RFC_LENGTH_MIN;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusTabacaleraEnum;

import mx.gob.sat.mat.tabacos.modelo.dao.TabacaleraDao;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.ReporteContribuyenteMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.TabacaleraMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.TabacaleraRfcMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TabacaleraDaoException;
import mx.gob.sat.mat.tabacos.persistence.Fields;
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

@Repository
@Transactional
@Qualifier("tabacaleraDaoImpl")
public class TabacaleraDaoImpl implements TabacaleraDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(TabacaleraDaoImpl.class);

    private static final int CASE_ALTA = 1;
    private static final int CASE_BAJA = 2;
    private static final int CASE_CAMBIO = 3;

    @Override
    public Tabacalera consultarPorRfc(String rfc) throws TabacaleraDaoException {
        Tabacalera tabacalera = null;
        try {
            List<Tabacalera> tabacaleras = jdbcTemplate.query(SQL_TABACALERA_RFC, new Object[]{rfc}, new RowMapper<Tabacalera>() {

                @Override
                public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                    Tabacalera tabacalera = new Tabacalera();
                    tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                    tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                    tabacalera.setRazonSocial(resultSet.getString(Fields.FIELD_TABACALERA_RAZONSOCIAL));
                    tabacalera.setDomicilio(resultSet.getString(Fields.FIELD_TABACALERA_DOMICILIO));
                    tabacalera.setIdTipoUsuario(resultSet.getLong(Fields.FIELD_TABACALERA_IDTIPOUSUARIO));
                    tabacalera.setFecRegistro(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECREGISTRO));
                    tabacalera.setFecCaptura(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECCAPTURA));
                    tabacalera.setIdEstatus(resultSet.getLong(Fields.FIELD_TABACALERA_IDESTATUS));
                    tabacalera.setCorreoElectronico(resultSet.getString(Fields.FIELD_TABACALERA_CORREOELECTRONICO));
                    tabacalera.setTelefono(resultSet.getString(Fields.FIELD_TABACALERA_TELEFONO));
                    return tabacalera;
                }
            });
            tabacalera = tabacaleras != null && tabacaleras.size() > 0 ? tabacaleras.get(0) : null;
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new TabacaleraDaoException("Error al consultar los datos de Tabaclaera por RFC" + e.getMessage(), e);
        }
        return tabacalera;
    }

    @Override
    public List<String> buscaTabacalerasLikeRfc(String rfc, EstatusTabacaleraEnum estatus) throws TabacaleraDaoException {
        try {

            StringBuilder queryDinamico;

            if (estatus == null) {
                queryDinamico = new StringBuilder((SQL_TABACALERA_LIKE_RFC_HEDER.replace(IDESTATUS_CONDICION, "")));
                queryDinamico.append(rfc.toUpperCase());
                queryDinamico.append(SQL_TABACALERA_LAKE_RFC_FOOTER);
                return jdbcTemplate.query(queryDinamico.toString(), new TabacaleraRfcMapper());
            } else {
                queryDinamico = new StringBuilder(SQL_TABACALERA_LIKE_RFC_HEDER);
                queryDinamico.append(rfc.toUpperCase());
                queryDinamico.append(SQL_TABACALERA_LAKE_RFC_FOOTER);
                return jdbcTemplate.query(queryDinamico.toString(), new Object[]{estatus.getKey()}, new TabacaleraRfcMapper());
            }

        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new TabacaleraDaoException("Error al consultar los datos de la Tabacalera" + e.getMessage(), e);
        }
    }

    @Override
    public List<Tabacalera> buscaTabacaleras(EstatusTabacaleraEnum estatus) throws TabacaleraDaoException {
        try {
            String query;
            if (estatus == null) {
                query = SQL_TABACALERA_X_IDESTADO.replace(SQL_IDESTATUS_CONDICION, "");
                return jdbcTemplate.query(query, new TabacaleraMapper());
            } else {
                query = SQL_TABACALERA_X_IDESTADO;
                return jdbcTemplate.query(query, new Object[]{estatus.getKey()}, new TabacaleraMapper());
            }
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new TabacaleraDaoException("Error al consultar los datos de la Tabacalera" + e.getMessage(), e);
        }
    }

    @Override
    public List<Tabacalera> buscaTabacalerasXMovimiento(String rfc, Date fechaInicio, Date fechafin, ABCEnum tipoConsulta, EstatusTabacaleraEnum estatus) throws TabacaleraDaoException {
        try {
            StringBuilder queryDinamico;

            boolean flgRfcValido = (rfc != null && rfc.length() > RFC_LENGTH_MIN);
            boolean flgFechasValidas = (fechaInicio != null && fechafin != null);

            switch (tipoConsulta.getId()) {
                case CASE_ALTA:
                    queryDinamico = new StringBuilder(SQL_TABACALERA_ALTA_HEAD);

                    if (flgRfcValido) {
                        return jdbcTemplate.query(queryAltaTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), rfc.toUpperCase()}, new ReporteContribuyenteMapper());
                    }

                    return jdbcTemplate.query(queryAltaTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), fechaInicio, fechafin}, new ReporteContribuyenteMapper());

                case CASE_BAJA:
                    queryDinamico = new StringBuilder(SQL_TABACALERA_BAJA_HEAD);

                    if (flgRfcValido) {
                        return jdbcTemplate.query(queryBajaTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), rfc.toUpperCase()}, new ReporteContribuyenteMapper());
                    }

                    return jdbcTemplate.query(queryBajaTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), fechaInicio, fechafin}, new ReporteContribuyenteMapper());

                case CASE_CAMBIO:
                    queryDinamico = new StringBuilder(SQL_TABACALERA_CAMBIO_HEAD);

                    if (flgRfcValido) {
                        return jdbcTemplate.query(queryCambioTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), rfc.toUpperCase()}, new ReporteContribuyenteMapper());
                    }

                    return jdbcTemplate.query(queryCambioTabXMovimiento(queryDinamico, flgRfcValido, flgFechasValidas), new Object[]{estatus.getKey(), fechaInicio, fechafin}, new ReporteContribuyenteMapper());

                default:
                    break;
            }

            return new ArrayList<Tabacalera>();

        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new TabacaleraDaoException("Error al consultar los datos de la Tabacalera" + e.getMessage(), e);
        }
    }

    private String queryAltaTabXMovimiento(StringBuilder queryDinamico, boolean flgRfcValido, boolean flgFechasValidas) {

        if (flgRfcValido) {
            queryDinamico.append(SQL_PARAM_ALTA_RFC);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        } else if (flgFechasValidas) {
            queryDinamico.append(SQL_PARAM_ALTA_FECHA);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        }

        return "";
    }

    private String queryBajaTabXMovimiento(StringBuilder queryDinamico, boolean flgRfcValido, boolean flgFechasValidas) {

        if (flgRfcValido) {
            queryDinamico.append(SQL_PARAM_BAJA_RFC);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        } else if (flgFechasValidas) {
            queryDinamico.append(SQL_PARAM_BAJA_FECHA);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        }

        return "";
    }

    private String queryCambioTabXMovimiento(StringBuilder queryDinamico, boolean flgRfcValido, boolean flgFechasValidas) {

        if (flgRfcValido) {
            queryDinamico.append(SQL_PARAM_CAMBIO_RFC);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        } else if (flgFechasValidas) {
            queryDinamico.append(SQL_PARAM_CAMBIO_FECHA);
            queryDinamico.append(SQL_TABACALERA_FOOTER);
            return queryDinamico.toString();
        }

        return "";
    }

    @Override
    public List<Tabacalera> getTabacaleras() throws TabacaleraDaoException {
        try {
            return (List<Tabacalera>) jdbcTemplate.query(SQL_SELECT_TACACALERAS, new BeanPropertyRowMapper(Tabacalera.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LAS TABACALERAS";
            LOGGER.error(msgError, e);
            throw new TabacaleraDaoException(msgError, e);
        }
    }

    @Override
    public Tabacalera getTabacaleraByRFC(String rfc) throws TabacaleraDaoException {
        try {
            return (Tabacalera) jdbcTemplate.queryForObject(SQL_SELECT_TABACALERA_BY_RFC, new Object[]{rfc}, new BeanPropertyRowMapper(Tabacalera.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR LA TABACALERA POR RFC";
            LOGGER.error(msgError, e);
            throw new TabacaleraDaoException(msgError, e);
        }
    }

    /**
     * Metodo encargado de las validaciones de acceso
     *
     * @param proveedor
     * @return
     * @throws TabacaleraDaoException
     */
    @Override
    public List<Tabacalera> consultarTabacaleras(Proveedor proveedor) throws TabacaleraDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PROVEEDOR_TABACALERAS,
                    new Object[]{proveedor.getIdProveedor()},
                    new RowMapper<Tabacalera>() {

                        public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                            Tabacalera tabacalera = new Tabacalera();

                            tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                            tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                            tabacalera.setRazonSocial(resultSet.getString(Fields.FIELD_TABACALERA_RAZONSOCIAL));
                            tabacalera.setDomicilio(resultSet.getString(Fields.FIELD_TABACALERA_DOMICILIO));
                            tabacalera.setIdTipoUsuario(resultSet.getLong(Fields.FIELD_TABACALERA_IDTIPOUSUARIO));
                            tabacalera.setFecRegistro(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECREGISTRO));
                            tabacalera.setFecCaptura(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECCAPTURA));
                            tabacalera.setIdEstatus(resultSet.getLong(Fields.FIELD_TABACALERA_IDESTATUS));
                            tabacalera.setCorreoElectronico(resultSet.getString(Fields.FIELD_TABACALERA_CORREOELECTRONICO));
                            tabacalera.setTelefono(resultSet.getString(Fields.FIELD_TABACALERA_TELEFONO));

                            return tabacalera;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar Tabacaleras" + e.getMessage());
            throw new TabacaleraDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List Tabacalera
     * @throws TabacaleraDaoException
     */
    @Override
    public List<Tabacalera> selectedRfcClienteAlta() throws TabacaleraDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_RFC_CLIENTE_PROVEEDOR_ALTA,
                    new RowMapper<Tabacalera>() {

                        public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                            Tabacalera tabacalera = new Tabacalera();

                            tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                            tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                            return tabacalera;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al obtener los RFC de los clientes del proveedor - Alta : " + e.getMessage(), e);
            throw new TabacaleraDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List Tabacalera
     * @throws TabacaleraDaoException
     */
    @Override
    public List<Tabacalera> selectedRfcClienteCambio(Proveedor proveedor) throws TabacaleraDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_RFC_CLIENTE_PROVEEDOR_CAMBIO,
                    new Object[]{proveedor.getRfc()},
                    new RowMapper<Tabacalera>() {

                        public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                            Tabacalera tabacalera = new Tabacalera();

                            tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                            tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                            return tabacalera;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al obtener los RFC de los clientes del proveedor - Cambio : " + e.getMessage(), e);
            throw new TabacaleraDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List<Tabacalera>
     * @throws TabacaleraDaoException
     */
    @Override
    public List<Tabacalera> consultaTabacaleras() throws TabacaleraDaoException {
        try {
            return jdbcTemplate.query(SQL_CONSULTA_TABACALERA_RAZON_SOCIAL, new RowMapper<Tabacalera>() {

                @Override
                public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                    Tabacalera tabacalera = new Tabacalera();

                    tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                    tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                    tabacalera.setRazonSocial(resultSet.getString(Fields.FIELD_TABACALERA_RAZONSOCIAL));

                    return tabacalera;
                }
            });

        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar las Tabacaleras : " + e.getMessage(), e);
            throw new TabacaleraDaoException(e);
        }
    }

    /**
     *
     * @param rfc
     * @return List<Tabacalera>
     * @throws TabacaleraDaoException
     */
    @Override
    public List<Tabacalera> consultaContribuyentesPorRfc(String rfc) throws TabacaleraDaoException {
        try {
            return jdbcTemplate.query(SQL_CONSULTA_TABACALERA_RAZON_SOCIAL_POR_RFC,
                    new Object[]{rfc},
                    new RowMapper<Tabacalera>() {

                        @Override
                        public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                            Tabacalera tabacalera = new Tabacalera();

                            tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                            tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                            tabacalera.setRazonSocial(resultSet.getString(Fields.FIELD_TABACALERA_RAZONSOCIAL));

                            return tabacalera;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Contribuyentes por RFC : ", e);
            throw new TabacaleraDaoException(e);
        }
    }
    
    @Override
    public Tabacalera consultarTabacaleraRepresentada(RepresentanteLegal representanteLegal) throws TabacaleraDaoException {

        try {
            List<Tabacalera> tabacaleras;
            tabacaleras = jdbcTemplate.query(SQL_REPLEGAL_TABACALERA, new Object[]{representanteLegal.getIdRepLegal()}, new RowMapper<Tabacalera>() {

                @Override
                public Tabacalera mapRow(ResultSet resultSet, int i) throws SQLException {
                    Tabacalera tabacalera = new Tabacalera();
                    tabacalera.setIdTabacalera(resultSet.getLong(Fields.FIELD_TABACALERA_IDTABACALERA));
                    tabacalera.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
                    tabacalera.setRazonSocial(resultSet.getString(Fields.FIELD_TABACALERA_RAZONSOCIAL));
                    tabacalera.setDomicilio(resultSet.getString(Fields.FIELD_TABACALERA_DOMICILIO));
                    tabacalera.setIdTipoUsuario(resultSet.getLong(Fields.FIELD_TABACALERA_IDTIPOUSUARIO));
                    tabacalera.setFecRegistro(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECREGISTRO));
                    tabacalera.setFecCaptura(resultSet.getTimestamp(Fields.FIELD_TABACALERA_FECCAPTURA));
                    tabacalera.setIdEstatus(resultSet.getLong(Fields.FIELD_TABACALERA_IDESTATUS));
                    tabacalera.setCorreoElectronico(resultSet.getString(Fields.FIELD_TABACALERA_CORREOELECTRONICO));
                    tabacalera.setTelefono(resultSet.getString(Fields.FIELD_TABACALERA_TELEFONO));
                    return tabacalera;
                }
            });
            return (tabacaleras != null && tabacaleras.size() > 0) ? tabacaleras.get(0) : null;
        } catch (DataAccessException e) {
            throw new TabacaleraDaoException("Error al consultar los datos del Proveedor de tabacalera" + e.getMessage(), e);
        }

    }

}
