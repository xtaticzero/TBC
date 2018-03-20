package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusProveedor;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusRepresentante;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusTipoRepLegal;
import mx.gob.sat.mat.tabacos.modelo.dao.ProveedorDao;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.ProveedorMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.ProveedorReporteMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.Baja;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.filtro.ProveedorFiltroDTO;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException;
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

/**
 * DAO de implementacion del proveedor
 *
 * @author Salvador Pocteco Saldaña
 */
@Repository
@Transactional
@Qualifier("proveedorDaoImpl")
public class ProveedorDaoImpl implements ProveedorDao {

    private static final Logger LOGGER = Logger.getLogger(ProveedorDaoImpl.class);

    private static final int VAL1 = 1;
    private static final int VAL2 = 2;
    private static final int VAL3 = 3;
    private static final int VAL4 = 4;
    private static final int VAL5 = 5;
    private static final int VAL6 = 6;
    private static final int VAL7 = 7;
    private static final int VAL8 = 8;
    private static final int VAL9 = 9;
    protected static final String VACIO = "";
    
    

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param proveedor
     * @return Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public Proveedor insertProveedor(Proveedor proveedor) throws ProveedorDaoException {
        try {
            final Long idProveedor = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_PROVEEDOR, Long.class);
            proveedor.setIdProveedor(idProveedor);
            proveedor.setEstatusProv(EstatusProveedor.ACTIVO);
            proveedor.setFecCaptura(new Date());

            jdbcTemplate.update(SQL_INSERT_PROVEEDOR,
                    new Object[]{
                        idProveedor,
                        proveedor.getRfc(),
                        proveedor.getRazonSocial(),
                        proveedor.getDomicilio(),
                        proveedor.getFecRegistro(),
                        proveedor.getFecCaptura(),
                        proveedor.getEstatusProv().getKey()
                    });
            return proveedor;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los datos del Proveedor" + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param idTabacalera
     * @param baja
     * @return Baja
     * @throws ProveedorDaoException
     */
    @Override
    public Baja deleteProveedor(Proveedor proveedor, Long idTabacalera, Baja baja) throws ProveedorDaoException {
        try {
            final Long idBaja = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_BAJA, Long.class);
            baja.setIdBaja(idBaja);
            baja.setIdTabacalera(idTabacalera);
            baja.setIdProveedor(proveedor.getIdProveedor());
            baja.setFecRegistro(new Date());

            jdbcTemplate.update(SQL_DELETE_PROVEEDOR,
                    new Object[]{
                        idBaja,
                        baja.getIdTabacalera(),
                        baja.getIdProveedor(),
                        baja.getDescMotivoBaja(),
                        baja.getFecRegistro(),
                        baja.getFecBaja()
                    });
            return baja;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al dar de baja los datos del Representante - Proveedor " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param representante
     * @param idTabacalera
     * @return RepresentanteLegal
     * @throws ProveedorDaoException
     */
    @Override
    public RepresentanteLegal insertRepresentante(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera) throws ProveedorDaoException {
        try {
            final Long idRepresentante = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_REPRESENTANTE, Long.class);

            representante.setIdRepLegal(idRepresentante);
            representante.setIdTabacalera(idTabacalera);
            representante.setIdProveedor(proveedor.getIdProveedor());
            representante.setEstatusRepresentante(EstatusRepresentante.ACTIVO);
            representante.setFecInicio(proveedor.getFecRegistro());
            representante.setEstatusTipoRepLegal(EstatusTipoRepLegal.LEGAL);

            jdbcTemplate.update(SQL_INSERT_REPRESENTANTE,
                    new Object[]{
                        idRepresentante,
                        representante.getIdTabacalera(),
                        representante.getIdProveedor(),
                        representante.getNombre(),
                        representante.getApellidoMaterno(),
                        representante.getApellidoPaterno(),
                        representante.getCorreoElectronico(),
                        representante.getTelefono(),
                        representante.getFecInicio(),
                        representante.getEstatusRepresentante().getKey(),
                        representante.getEstatusTipoRepLegal().getKey()
                    });
            return representante;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los datos del Representante - Proveedor " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param representante
     * @param idTabacalera
     * @param relacionProveedor
     * @return RelacionTabProv
     * @throws ProveedorDaoException
     */
    @Override
    public RelacionTabProv insertRelacionProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor) throws ProveedorDaoException {
        try {
            final Long id = jdbcTemplate.queryForObject(SQL_SELECT_NEXTID_RELACIONAL_PROVEDOR, Long.class);

            relacionProveedor.setIdTbcProv(id);
            relacionProveedor.setIdTabacalera(idTabacalera);
            relacionProveedor.setIdProveedor(proveedor.getIdProveedor());
            relacionProveedor.setFecRegistro(proveedor.getFecRegistro());
            relacionProveedor.setEstatusEnum(EstatusEnum.ALTA);

            jdbcTemplate.update(SQL_INSERT_RELACIONAL_PROVEEDOR,
                    new Object[]{
                        relacionProveedor.getIdTabacalera(),
                        relacionProveedor.getIdProveedor(),
                        relacionProveedor.getFecRegistro(),
                        relacionProveedor.getEstatusEnum().getKey(),
                        id
                    });
            return relacionProveedor;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al insertar los datos En la tabla Relacional del  Representante - Proveedor " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public Proveedor updateProveedorBaja(Proveedor proveedor) throws ProveedorDaoException {
        try {
            jdbcTemplate.update(SQL_UPDATE_PROVEEDOR_BAJA,
                    new Object[]{
                        proveedor.getIdEstatus(),
                        proveedor.getRfc()
                    });
            return proveedor;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al actualizar el estatus del Proveedor - Baja  " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param baja
     * @param relacionProveedor
     * @return RelacionTabProv
     * @throws ProveedorDaoException
     */
    @Override
    public RelacionTabProv updateProveedorRelacionBaja(Proveedor proveedor, Baja baja, RelacionTabProv relacionProveedor) throws ProveedorDaoException {
        try {
            relacionProveedor.setFecBaja(baja.getFecBaja());
            relacionProveedor.setIdEstatus(proveedor.getIdEstatus());
            relacionProveedor.setIdProveedor(proveedor.getIdProveedor());

            jdbcTemplate.update(SQL_UPDATE_PROVEEDOR_RELACION_BAJA,
                    new Object[]{
                        relacionProveedor.getFecBaja(),
                        relacionProveedor.getIdEstatus(),
                        relacionProveedor.getIdProveedor()
                    });
            return relacionProveedor;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al actualizar el estatus del Proveedor - Baja - Relacional " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param tabacalera
     * @param relacionProveedor
     * @param idTabacaleraTem
     * @return RelacionTabProv
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException
     */
    @Override
    public RelacionTabProv updateProveedorRelacionCambio(Proveedor proveedor, RelacionTabProv relacionProveedor, Long idTabacaleraTem) throws ProveedorDaoException {
        try {
            relacionProveedor.setFecBaja(new Date());
            relacionProveedor.setEstatusEnum(EstatusEnum.BAJA);
            relacionProveedor.setIdProveedor(proveedor.getIdProveedor());
            relacionProveedor.setIdTabacalera(idTabacaleraTem);

            jdbcTemplate.update(SQL_UPDATE_PROVEEDOR_RELACION_ESTATUS_BAJA,
                    new Object[]{
                        relacionProveedor.getFecBaja(),
                        relacionProveedor.getEstatusEnum().getKey(),
                        relacionProveedor.getIdProveedor(),
                        relacionProveedor.getIdTabacalera()
                    });
            return relacionProveedor;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al actualizar los datos del Proveedor en la tabla Relacional " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @param representante
     * @param tabacalera
     * @return RepresentanteLegal
     * @throws ProveedorDaoException
     */
    @Override
    public RepresentanteLegal updateRepresentante(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera) throws ProveedorDaoException {
        try {
            representante.setIdTabacalera(idTabacalera);

            jdbcTemplate.update(SQL_UPDATE_REPRESENTATE,
                    new Object[]{
                        representante.getIdTabacalera(),
                        representante.getNombre(),
                        representante.getApellidoPaterno(),
                        representante.getApellidoMaterno(),
                        representante.getCorreoElectronico(),
                        representante.getTelefono(),
                        representante.getIdRepLegal()
                    });
            return representante;

        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al actualizar los datos del Representante - Proveedor " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> consultaDatosProveedor(Proveedor proveedor) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PROVEEDOR_DATOS,
                    new Object[]{proveedor.getRfc()}, new RowMapper<Proveedor>() {

                        public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                            Proveedor proveedor = new Proveedor();

                            proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
                            proveedor.setRazonSocial(resultSet.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL));
                            proveedor.setDomicilio(resultSet.getString(Fields.FIELD_PROVEEDOR_DOMICILIO));
                            proveedor.setFecRegistro(resultSet.getDate(Fields.FIELD_PROVEEDOR_FECHA_REGISTRO));
                            proveedor.setIdEstatus(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID_ESTATUS));

                            return proveedor;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los datos del Proveedor - Cambio" + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param representante
     * @param proveedor
     * @return List RepresentanteLegal
     * @throws ProveedorDaoException
     */
    @Override
    public List<RepresentanteLegal> consultaDatosRepresentante(RepresentanteLegal representante, Proveedor proveedor) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PROVEEDOR_DATOS,
                    new Object[]{proveedor.getRfc()},
                    new RowMapper<RepresentanteLegal>() {

                        public RepresentanteLegal mapRow(ResultSet resultSet, int i) throws SQLException {
                            RepresentanteLegal representante = new RepresentanteLegal();

                            representante.setIdRepLegal(resultSet.getLong(Fields.FIELD_REPLEGAL_IDREPLEGAL));
                            representante.setIdTabacalera(resultSet.getLong(Fields.FIELD_REPLEGAL_IDTABACALERA));
                            representante.setIdProveedor(resultSet.getLong(Fields.FIELD_REPLEGAL_IDPROVEEDOR));
                            representante.setNombre(resultSet.getString(Fields.FIELD_REPLEGAL_NOMBRE));
                            representante.setApellidoPaterno(resultSet.getString(Fields.FIELD_REPLEGAL_APELLIDOPATERNO));
                            representante.setApellidoMaterno(resultSet.getString(Fields.FIELD_REPLEGAL_APELLIDOMATERNO));
                            representante.setCorreoElectronico(resultSet.getString(Fields.FIELD_REPLEGAL_CORREOELECTRONICO));
                            representante.setTelefono(resultSet.getString(Fields.FIELD_REPLEGAL_TELEFONO));
                            representante.setIdEstatus(resultSet.getLong(Fields.FIELD_REPLEGAL_IDESTATUS));

                            return representante;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los datos del Proveedor -  Represante - Cambio" + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List Baja
     * @throws ProveedorDaoException
     */
    @Override
    public List<Baja> consultaDatosProveedorBaja(Proveedor proveedor) throws ProveedorDaoException {
        try {

            return jdbcTemplate.query(SQL_SELECT_PROVEEDOR_RELACION_BAJA,
                    new Object[]{proveedor.getIdProveedor()},
                    new RowMapper<Baja>() {

                        @Override
                        public Baja mapRow(ResultSet resultSet, int i) throws SQLException {
                            Baja baja = new Baja();

                            baja.setIdBaja(resultSet.getLong(Fields.FIELD_BAJA_ID));
                            baja.setIdTabacalera(resultSet.getLong(Fields.FIELD_BAJA_TABACALERA_ID));
                            baja.setIdProveedor(resultSet.getLong(Fields.FIELD_BAJA_PROVEEDOR_ID));
                            baja.setDescMotivoBaja(resultSet.getString(Fields.FIELD_BAJA_DESC_MOTIVO_BAJA));
                            baja.setFecRegistro(resultSet.getDate(Fields.FIELD_BAJA_FECHA_REGISTRO));
                            baja.setFecBaja(resultSet.getTimestamp(Fields.FIELD_BAJA_FECHA_BAJA));

                            return baja;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Error al consultar los datos del Proveedor - Baja" + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List Marcas
     * @throws ProveedorDaoException
     */
    @Override
    public List<Marcas> selectedMarcas() throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_MARCAS_PROVEEDOR, new RowMapper<Marcas>() {

                @Override
                public Marcas mapRow(ResultSet resultSet, int i) throws SQLException {
                    Marcas marca = new Marcas();

                    marca.setIdMarca(resultSet.getLong(Fields.FIELD_MARCAS_ID));
                    marca.setMarca(resultSet.getString(Fields.FIELD_MARCAS_NOMBRE_MARCA));
                    marca.setIdTabacalera(resultSet.getLong(Fields.FIELD_MARCAS_TABACALERA_ID));

                    return marca;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al obtener las marcas del proveedor : " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> buscaRfc(Proveedor proveedor) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_RFC_PROVEEDOR,
                    new Object[]{proveedor.getRfc()},
                    new RowMapper<Proveedor>() {

                        @Override
                        public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                            Proveedor proveedor = new Proveedor();

                            proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
                            proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
                            return proveedor;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar el RFC de el Proveedor : " + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List Estatus
     * @throws ProveedorDaoException
     */
    @Override
    public List<Estatus> selectedEstatus(Proveedor proveedor) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_ESTATUS_PROVEEDOR,
                    new RowMapper<Estatus>() {

                        @Override
                        public Estatus mapRow(ResultSet resultSet, int i) throws SQLException {
                            Estatus estatus = new Estatus();

                            estatus.setIdEstatus(resultSet.getLong(Fields.FIELD_ESTATUS_ID));
                            estatus.setNomEstatus(resultSet.getString(Fields.FIELD_ESTATUS_NOMBRE));
                            return estatus;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Estatus del proveedor" + e.getMessage(), e);
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedorDatos
     * @return List Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> findRfc(ProveedorFiltroDTO proveedorDatos) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PROVEEDOR_BY_RFC_DELETE,
                    new Object[]{proveedorDatos.getRfc()},
                    new RowMapper<Proveedor>() {

                        @Override
                        public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                            Proveedor proveedor = new Proveedor();
                            /*Cambios Jonathan*/
                            proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
                            proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
                            proveedor.setFecCaptura(resultSet.getDate(Fields.FIELD_PROVEEDOR_FECHA_CAPTURA));

                            return proveedor;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los datos del Proveedor por RFC : " + e.getMessage());
            throw new ProveedorDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param rfcTabacalera
     * @return List Proveedor
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> buscaProveedorPorTabacalera(String rfcTabacalera) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_FIND_PROVEDOR_X_TABACALERA,
                    new Object[]{rfcTabacalera},
                    new ProveedorMapper()
            );
        } catch (DataAccessException dae) {
            LOGGER.error("ERROR - Al consultar los Estatus del proveedor : " + dae.getMessage(), dae);
            throw new ProveedorDaoException(dae.getMessage(), dae);
        }
    }

    /**
     *
     * @param opcion
     * @return
     */
    private String queryProveedor(int opcion) {
        String retorno = null;
        switch (opcion) {
            case VAL1:
                retorno = SQL_SELECT_PROVEEDOR_PROD + SQL_SELECT_PROVEEDOR_OP1_PROD;
                break;
            case VAL2:
                retorno = SQL_SELECT_PROVEEDOR_PROD + SQL_SELECT_PROVEEDOR_OP2_PROD;
                break;
            case VAL3:
                retorno = SQL_SELECT_PROVEEDOR_PROD + SQL_SELECT_PROVEEDOR_OP3;
                break;
            case VAL4:
                retorno = SQL_SELECT_PROVEEDOR + SQL_SELECT_PROVEEDOR_OP1;
                break;
            case VAL5:
                retorno = SQL_SELECT_PROVEEDOR + SQL_SELECT_PROVEEDOR_OP2;
                break;
            case VAL6:
                retorno = SQL_SELECT_PROVEEDOR + SQL_SELECT_PROVEEDOR_OP3;
                break;
            case VAL7:
                retorno = SQL_SELECT_CONTRIBUYENTE_BAJA + SQL_SELECT_CONTRIBUYENTE_BAJA_OP1;
                break;
            case VAL8:
                retorno = SQL_SELECT_PROVEEDOR_BAJA + SQL_SELECT_PROVEEDOR_BAJA_OP2;
                break;
            case VAL9:
                retorno = SQL_SELECT_PROVEEDOR_BAJA + SQL_SELECT_PROVEEDOR_BAJA_OP3;
                break;
            default:
                break;
        }
        return retorno;
    }

    private Object[] parametrosContribuyenteProveedor(ReportesFiltroBase filtro) {
        Object[] retorno = null;

        switch (seleccionOpcion(filtro)) {
            case VAL1:
                retorno = new Object[]{filtro.getRfc()};
                break;
            case VAL2:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin()};
                break;
            case VAL3:
                retorno = new Object[]{filtro.getRfc()};
                break;
            default:
                break;
        }

        return retorno;
    }

    protected int seleccionOpcion(ReportesFiltroBase filtro) {
        int retorno = 0;

        if ((!filtro.getRfc().isEmpty())
                && (filtro.getFechaInicio() != null)
                && (filtro.getFechaFin() != null)) {
            retorno = VAL3;
        } else if ((filtro.getFechaInicio() != null)
                && (filtro.getFechaFin() != null)) {
            retorno = VAL2;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = VAL3;
        }

        return retorno;
    }

    /**
     *
     * @param filtro
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException
     */
    @Override
    public List<Proveedor> recuperaProveedores(ReportesFiltroBase filtro) throws ProveedorDaoException {
        List<Proveedor> proveedores;
        String strQuery = null;
        Object[] parametros = null;

        try {
            switch (filtro.getMovimiento()) {
                case VAL1:
                    strQuery = queryProveedor(seleccionOpcion(filtro));
                    parametros = parametrosContribuyenteProveedor(filtro);
                    break;
                case VAL2:
                    strQuery = queryProveedor(VAL3 + seleccionOpcion(filtro));
                    parametros = parametrosContribuyenteProveedor(filtro);
                    break;
                case VAL3:
                    strQuery = queryProveedor(VAL6 + seleccionOpcion(filtro));
                    parametros = parametrosContribuyenteProveedor(filtro);
                    break;
                default:
                    break;
            }
            proveedores = jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<Proveedor>() {
                        @Override
                        public Proveedor mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            Proveedor proveedor = new Proveedor();
                            proveedor.setFecMovimiento(resultSet.getDate("FECMOVIMIENTO"));
                            proveedor.setRfc(resultSet.getString("RFC"));
                            return proveedor;
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ProveedorDaoException(e);
        }
        return proveedores;
    }

    /**
     *
     * @param like
     * @return
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> buscaProveedorLikeRFC(String like) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_FIND_PROVEDOR_LIKE_RFC.replace(SQL_REPLACE_RFC, like.toUpperCase()),
                    new ProveedorMapper()
            );
        } catch (DataAccessException dae) {
            LOGGER.error("ERROR - Al consultar los Estatus del proveedor : " + dae.getMessage(), dae);
            throw new ProveedorDaoException(dae.getMessage(), dae);
        }
    }

    /**
     *
     * @return List<Proveedor>
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> consultaProveedores() throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_CONSULTA_PROVEEDOR_RAZON_SOCIAL, new ProveedorReporteMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Proveedores : ", e);
            throw new ProveedorDaoException(e);
        }
    }

    @Override
    public List<Proveedor> consultaProveedoresPorRfc(String rfc) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_CONSULTA_PROVEEDOR_RAZON_SOCIAL_POR_RFC,
                    new Object[]{rfc},
                    new RowMapper<Proveedor>() {

                        @Override
                        public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                            Proveedor proveedor = new Proveedor();

                            proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
                            proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
                            proveedor.setRazonSocial(resultSet.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL));

                            return proveedor;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Proveedores por RFC : ", e);
            throw new ProveedorDaoException(e);
        }
    }

    /**
     *
     * @return @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> consultaTodosLosProveedores() throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_CONSULTA_PROVEEDOR_TODOS, new ProveedorReporteMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Proveedores : ", e);
            throw new ProveedorDaoException(e);
        }
    }

    @Override
    public Proveedor getProveedorByRFC(String rfc) throws ProveedorDaoException {
        try {
            return (Proveedor) jdbcTemplate.queryForObject(SQL_SELECT_PROVEEDOR_BY_RFC, new Object[]{rfc}, new BeanPropertyRowMapper(Proveedor.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR LA TABACALERA POR RFC";
            LOGGER.error(msgError, e);
            throw new ProveedorDaoException(msgError, e);
        }
    }

    /**
     *
     * @param idTabacalera
     * @return
     * @throws ProveedorDaoException
     */
    @Override
    public List<Proveedor> consultarProveedores(Long idTabacalera) throws ProveedorDaoException {
        try {
            return jdbcTemplate.query(SQL_TABACALERA_PROVEEDORES, new Object[]{idTabacalera}, new RowMapper<Proveedor>() {

                @Override
                public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                    Proveedor proveedor = new Proveedor();

                    proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
                    proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
                    proveedor.setRazonSocial(resultSet.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL));
                    proveedor.setDomicilio(resultSet.getString(Fields.FIELD_PROVEEDOR_DOMICILIO));
                    proveedor.setFecCaptura(resultSet.getDate(Fields.FIELD_PROVEEDOR_FECHA_CAPTURA));
                    proveedor.setIdEstatus(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID_ESTATUS));

                    return proveedor;
                }
            });
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new ProveedorDaoException("Error al consultar los datos del Proveedor de tabacalera" + e.getMessage(), e);
        }
    }

    @Override
    public Proveedor buscarProveedorPorRfc(String rfc) throws ProveedorDaoException {
        List<Proveedor> destinos = getProveedores();
        return destinos != null ? destinos.get(0) : null;
    }

    private List<Proveedor> getProveedores() throws ProveedorDaoException {
        List<Proveedor> proveedores;

        proveedores = jdbcTemplate.query(SQL_SELECT_PROVEEDORES, new RowMapper<Proveedor>() {
            @Override
            public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
                Proveedor proveedor = new Proveedor();

                proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
                LOGGER.info("Encontro en getProveedores : " + proveedor.getRfc());

                return proveedor;
            }
        });

        return proveedores;
    }

}
