package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dao.ReportesDAO;
import static mx.gob.sat.mat.tabacos.modelo.dao.impl.ReportesExtension.OPCIONES.OPCION1;
import static mx.gob.sat.mat.tabacos.modelo.dao.impl.ReportesExtension.OPCIONES.OPCION2;
import static mx.gob.sat.mat.tabacos.modelo.dao.impl.ReportesExtension.OPCIONES.OPCION3;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.SelectItemMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.reportes.CRContribuyenteSQL;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.reportes.CRDespDestruccionSQL;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.reportes.CRRegistroSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.RepDesperdicio;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.RepRegistro;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesContribuyentes;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesDesperdicios;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PersistenceException;
import mx.gob.sat.mat.tabacos.persistence.ReportesFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
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
@Qualifier("reportesDaoImpl")
public class ReportesDaoImpl extends ReportesExtension implements ReportesDAO {

    private static final org.apache.log4j.Logger LOGGER
            = org.apache.log4j.Logger.getLogger(ReportesDaoImpl.class);

    /**
     *
     */
    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param opcion
     * @return
     */
    private String queryContribuyente(int opcion) {
        String retorno = null;
        switch (opcion) {
            case VAL1:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_PROD
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_PROD_OP1;
                break;
            case VAL2:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_PROD
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_PROD_OP2;
                break;
            case VAL3:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_PROD
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_OP3;
                break;
            case VAL4:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_OP1;
                break;
            case VAL5:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_OP2;
                break;
            case VAL6:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_OP3;
                break;
            case VAL7:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_BAJA
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_BAJA_OP1;
                break;
            case VAL8:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_BAJA
                        + "AND TBAJ.FECREGISTRO BETWEEN ? AND ? "
                        + "AND TBAJ.IDMARCA IS NOT NULL";
                break;
            case VAL9:
                retorno = CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_BAJA
                        + CRContribuyenteSQL.SQL_SELECT_CONTRIBUYENTE_BAJA_OP3;
                break;
            default:
                break;
        }
        return retorno;
    }

    /**
     *
     * @param filtro
     * @return
     */
    @Override
    public ReportesContribuyentes getContriyentes(ReportesFiltroBase filtro) {
        ReportesContribuyentes retorno = new ReportesContribuyentes();
        String strQuery = null;
        Object[] parametros = null;

        try {
            switch (filtro.getMovimiento()) {
                case VAL1:
                    strQuery = queryContribuyente(selectOpc(filtro));
                    parametros = parametrosContProv(filtro);
                    break;
                case VAL2:
                    strQuery = queryContribuyente(VAL3 + selectOpc(filtro));
                    parametros = parametrosContProv(filtro);
                    break;
                case VAL3:
                    strQuery = queryContribuyente(VAL6 + selectOpc(filtro));
                    parametros = parametrosContProv(filtro);
                    break;
                default:
                    break;
            }

            List<Tabacalera> tabacaleras;

            tabacaleras = jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<Tabacalera>() {
                        @Override
                        public Tabacalera mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            Tabacalera contribuyente = new Tabacalera();
                            contribuyente.setFecMovimiento(resultSet.getDate(
                                            ReportesFields.FIELD_CONTRIBUYENTE_FECHAMOD));
                            contribuyente.setRfc(resultSet.getString(
                                            ReportesFields.FIELD_CONTRIBUYENTE_RFC));
                            return contribuyente;
                        }
                    });

            retorno.setRfc(filtro.getRfc());
            retorno.setFechaInicio(fechaInit(filtro.getFechaInicio()));
            retorno.setFechaFin(fechaInit(filtro.getFechaFin()));
            retorno.setContribuyentes(tabacaleras);

        } catch (Exception e) {
            LOGGER.error(e.getMessage().trim());
            throw new PersistenceException("Error al obtener contribuyente: "
                    + e.getMessage(), e);
        }

        return retorno;
    }

    /**
     *
     * @param filtro
     * @param opcion
     * @return
     */
    private Object[] parametrosRegRFF(final ReportesRegistrosYDespedicios filtro,
            OPCIONES opcion) {
        Object[] retorno = null;

        switch (opcion) {
            case OPCION1:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getRfc(),
                    filtro.getContribuyenteCB(), filtro.getProveedorCB()};
                break;
            case OPCION2:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getRfc(),
                    filtro.getContribuyenteCB()};
                break;
            case OPCION3:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getRfc(),
                    filtro.getProveedorCB()};
                break;
            case OPCION4:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getRfc(),};
                break;
            default:
                break;
        }
        return retorno;
    }

    /**
     *
     * @param filtro
     * @param opcion
     * @return
     */
    private Object[] parametrosRegFF(final ReportesRegistrosYDespedicios filtro,
            OPCIONES opcion) {
        Object[] retorno = null;

        switch (opcion) {
            case OPCION1:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getContribuyenteCB(),
                    filtro.getProveedorCB()};
                break;
            case OPCION2:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getContribuyenteCB()};
                break;
            case OPCION3:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin(), filtro.getProveedorCB()};
                break;
            case OPCION4:
                retorno = new Object[]{filtro.getFechaInicio(),
                    filtro.getFechaFin()};
                break;
            default:
                break;
        }
        return retorno;
    }

    /**
     *
     * @param filtro
     * @param opcion
     * @return
     */
    private Object[] parametrosRegR(final ReportesRegistrosYDespedicios filtro,
            OPCIONES opcion) {
        Object[] retorno = null;

        switch (opcion) {
            case OPCION1:
                retorno = new Object[]{filtro.getRfc(),
                    filtro.getContribuyenteCB(),
                    filtro.getProveedorCB()};
                break;
            case OPCION2:
                retorno = new Object[]{filtro.getRfc(),
                    filtro.getContribuyenteCB()};
                break;
            case OPCION3:
                retorno = new Object[]{filtro.getRfc(),
                    filtro.getProveedorCB()};
                break;
            case OPCION4:
                retorno = new Object[]{filtro.getRfc()};
                break;
            default:
                break;
        }
        return retorno;
    }

    /**
     *
     * @param resultSet
     * @param filtro
     * @return
     */
    private RepRegistro getRecordRegistro(ResultSet resultSet,
            ReportesRegistrosYDespedicios filtro) {
        RepRegistro registro = new RepRegistro();
        try {
            registro.setLote(filtroActivo(filtro.getLoteProduccion(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_LOTE)));
            registro.setPlanta(filtroActivo(filtro.getPlantaProduccion(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_PLANTA)));
            registro.setCantidad(filtroActivo(filtro.getCantidadProduccion(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_CANTIDAD)));
            registro.setProveedor(filtroActivo(filtro.getProveedor(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_PROVEEDOR)));
            registro.setContribuyente(resultSet.getString(
                    ReportesFields.FIELD_REGISTRO_CONTRIBUYENTE));
            registro.setPais(filtroActivo(filtro.getPaisProducto(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_PAIS)));
            registro.setMaquinaria(filtroActivo(filtro.getMaquinariaProduccion(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_MAQUINARIA)));
            registro.setOrigen(filtroActivo(filtro.getOrigen(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_ORIGEN)));
            registro.setLinea(filtroActivo(filtro.getNumeroProduccion(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_LINEA)));
            registro.setFecha(filtroActivo(filtro.getFechaYHora(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_FECHA)));
            registro.setSolicitud(filtroActivo(filtro.getTipoCodigo(),
                    resultSet.getString(ReportesFields.FIELD_REGISTRO_SOLICITUD)));
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage().trim());
        }
        return registro;
    }

    /**
     *
     * @param filtro
     * @return
     */
    @Override
    public ReportesRegistros getRegistros(final ReportesRegistrosYDespedicios filtro) {
        ReportesRegistros retorno = new ReportesRegistros();
        String strQuery = "";
        Object[] parametros = null;

        try {

            switch (selectOpciones(filtro)) {
                case OPCION1:
                    strQuery = CRRegistroSQL.SQL_SELECT_REGISTRO
                            + CRRegistroSQL.SQL_SELECT_REGISTRO_OP3;
                    parametros = parametrosRegRFF(filtro, selectOpcionesRegistro(filtro));
                    break;
                case OPCION2:
                    strQuery = CRRegistroSQL.SQL_SELECT_REGISTRO
                            + CRRegistroSQL.SQL_SELECT_REGISTRO_OP1;
                    parametros = parametrosRegFF(filtro, selectOpcionesRegistro(filtro));
                    break;
                case OPCION3:
                    strQuery = CRRegistroSQL.SQL_SELECT_REGISTRO
                            + CRRegistroSQL.SQL_SELECT_REGISTRO_OP2;
                    parametros = parametrosRegR(filtro, selectOpcionesRegistro(filtro));
                    break;
                    
                default:
                    break;
            }

            strQuery = adicQuery(strQuery, filtro);

            List<RepRegistro> registros;

            registros = jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<RepRegistro>() {
                        @Override
                        public RepRegistro mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            return getRecordRegistro(resultSet, filtro);
                        }
                    });

            retorno.setRfc(filtro.getRfc());
            retorno.setFechaInicio(fechaInit(filtro.getFechaInicio()));
            retorno.setFechaFin(fechaInit(filtro.getFechaFin()));
            retorno.setRegistros(registros);

        } catch (Exception e) {
            LOGGER.error(e.getMessage().trim());
            throw new PersistenceException("Error al obtener companias: "
                    + e.getMessage(), e);
        }

        return retorno;
    }

    private String adicQuery(String strQuery, final ReportesRegistrosYDespedicios filtro) {
        String retorno = strQuery;

        if ((filtro.getContribuyenteCB() != null)
                && (filtro.getProveedorCB() != null)) {
            if (!filtro.getContribuyenteCB().equals(VACIO)
                    && !filtro.getProveedorCB().equals(VACIO)) {
                retorno += CRRegistroSQL.SQL_SELECT_REGISTRO_OP4;
            }
        } else if ((filtro.getContribuyenteCB() != null)
                && (!filtro.getContribuyenteCB().equals(VACIO))) {
            retorno += CRRegistroSQL.SQL_SELECT_REGISTRO_OP5;
        } else if ((filtro.getProveedorCB() != null)
                && (!filtro.getProveedorCB().equals(VACIO))) {
            retorno += CRRegistroSQL.SQL_SELECT_REGISTRO_OP6;
        }

        retorno += CRRegistroSQL.SQL_ORDER_BY;

        return retorno;
    }

    /**
     *
     * @param resultSet
     * @param filtro
     * @return
     */
    private RepDesperdicio getRecord(ResultSet resultSet,
            ReportesRegistrosYDespedicios filtro) {
        RepDesperdicio desperdicio = new RepDesperdicio();
        try {
            desperdicio.setMarca(resultSet.getString(ReportesFields.FIELD_DESPERDICIOS_MARCA));
            desperdicio.setLote(filtroActivo(filtro.getLoteProduccion(),
                    resultSet.getString(ReportesFields.FIELD_DESPERDICIOS_LOTE)));
            desperdicio.setPlanta(filtroActivo(filtro.getPlantaProduccion(),
                    resultSet.getString(ReportesFields.FIELD_DESPERDICIOS_PLANTA)));
            desperdicio.setCantidadAutorizada(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_CANTIDAD_AUTORIZADA));
            desperdicio.setOrigen(filtroActivo(filtro.getOrigen(),
                    resultSet.getString(ReportesFields.FIELD_DESPERDICIOS_ORIGEN)));
            desperdicio.setMaquinaria(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_MAQUINARIA));
            desperdicio.setLote(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_LOTE));
            desperdicio.setFechaHora(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_FECHA));
            desperdicio.setCantidadCigarros(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_CCIGARROS));
            desperdicio.setCantidadProduccion(resultSet.getString(
                    ReportesFields.FIELD_DESPERDICIOS_CPRODUCIDA));
            desperdicio.setFolioInicial(resultSet.getLong(
                    ReportesFields.FIELD_DESPERDICIOS_FOLIOINICIAL));
            desperdicio.setFolioFinal(resultSet.getLong(
                    ReportesFields.FIELD_DESPERDICIOS_FOLIOFINAL));
        } catch (SQLException ex) {
            Logger.getLogger(ReportesDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return desperdicio;
    }

    /**
     *
     * @param filtro
     * @return
     */
    @Override
    public ReportesDesperdicios getDesperdicios(final ReportesRegistrosYDespedicios filtro) {
        ReportesDesperdicios retorno = new ReportesDesperdicios();
        String strQuery = "";
        Object[] parametros = null;

        try {
            switch (selectOpciones(filtro)) {
                case OPCION1:
                    strQuery = CRDespDestruccionSQL.SQL_SELECT_DESPDEST
                            + CRDespDestruccionSQL.SQL_SELECT_DESPDEST_OP3 + CRDespDestruccionSQL.SQL_ORDER_BY;
                    parametros = new Object[]{filtro.getFechaInicio(), filtro.getFechaFin(),
                        filtro.getRfc()};
                    break;
                case OPCION2:
                    strQuery = CRDespDestruccionSQL.SQL_SELECT_DESPDEST
                            + CRDespDestruccionSQL.SQL_SELECT_DESPDEST_OP1 + CRDespDestruccionSQL.SQL_ORDER_BY;
                    parametros = new Object[]{filtro.getFechaInicio(), filtro.getFechaFin()};
                    break;
                case OPCION3:
                    strQuery = CRDespDestruccionSQL.SQL_SELECT_DESPDEST
                            + CRDespDestruccionSQL.SQL_SELECT_DESPDEST_OP2 + CRDespDestruccionSQL.SQL_ORDER_BY;
                    parametros = new Object[]{filtro.getRfc()};
                    break;
                default:
                    break;
            }

            List<RepDesperdicio> desperdicios;

            desperdicios = jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<RepDesperdicio>() {
                        @Override
                        public RepDesperdicio mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            return getRecord(resultSet, filtro);
                        }
                    });

            retorno.setRfc(filtro.getRfc());
            retorno.setFechaInicio(fechaInit(filtro.getFechaInicio()));
            retorno.setFechaFin(fechaInit(filtro.getFechaFin()));
            retorno.setDesperdicios(desperdicios);
        } catch (Exception e) {
            LOGGER.error(e.getMessage().trim());
            throw new PersistenceException("Error al obtener companias: "
                    + e.getMessage(), e);
        }

        return retorno;
    }

    /**
     *
     * @param strSQL
     * @param id
     * @param name
     * @param param1
     * @return
     */
    @Override
    public List<SelectItem> getCombos(final String strSQL, final String id,
            final String name, final String param1) {

        Object[] parametros = new Object[]{param1};

        try {
            return jdbcTemplate.query(strSQL, parametros,
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
            LOGGER.error(e.getMessage().trim());
            throw new PersistenceException("Error al obtener: " + strSQL
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     *
     * @param strSQL
     * @param id
     * @param name
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException
     */
    @Override
    public List<SelectItem> getCombos(final String strSQL, final String id,
            final String name) throws ReporteDaoException {
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
            LOGGER.error(e.getMessage().trim());
            throw new ReporteDaoException("Error al obtener: " + strSQL
                    + "\n" + e.getMessage(), e);
        }
    }

    /**
     *
     * @param rfc
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.ReporteDaoException
     */
    @Override
    public String getRazonSocial(String rfc) throws ReporteDaoException {
        String razonSocial = "";
        Integer cont;

        try {
            cont = jdbcTemplate.queryForObject(ReportesDAO.SQL_SELECT_TABCONT,
                    new Object[]{rfc}, Integer.class);
            if (cont > VAL0) {
                razonSocial = jdbcTemplate.queryForObject(ReportesDAO.SQL_SELECT_TABACALERA,
                        new Object[]{rfc}, String.class);

            }
            return razonSocial;
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ReporteDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param rfc
     * @return
     */
    @Override
    public String getRazonSocialProv(String rfc) {
        String razonSocial = "";
        Integer cont;

        try {
            cont = jdbcTemplate.queryForObject(ReportesDAO.SQL_SELECT_PROVCONT,
                    new Object[]{rfc}, Integer.class);
            if (cont > VAL0) {
                razonSocial = jdbcTemplate.queryForObject(ReportesDAO.SQL_SELECT_PROVEEDOR,
                        new Object[]{rfc}, String.class);
            }
            return razonSocial;
        } catch (DataAccessException e) {
            LOGGER.error("Error al insertar los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(
                    "Error al insertar los datos de la marca" + e.getMessage(), e);
        }
    }
    
    /**
     *
     * @return
     * @throws ReporteDaoException
     */
    @Override
    public List<SelectItem> getCombosMarcaAutorizadas() throws ReporteDaoException{
        try {
            return jdbcTemplate.query(SQL_SELECT_MARCAS_AUTORIZADAS,
                    new SelectItemMapper());
        } catch (DataAccessException sql) {
            throw new ReporteDaoException(sql);
        }
    }
    
    /**
     *
     * @param rfc
     * @return
     * @throws ReporteDaoException
     */
    @Override
    public List<SelectItem> getMarcaAutorizadasXRfc(String rfc) throws ReporteDaoException{
        try {
            return jdbcTemplate.query(SQL_SELECT_MARCAS_AUTORIZADAS_X_RFC,new Object[]{rfc},
                    new SelectItemMapper());
        } catch (DataAccessException sql) {
            throw new ReporteDaoException(sql);
        }
    }

}
