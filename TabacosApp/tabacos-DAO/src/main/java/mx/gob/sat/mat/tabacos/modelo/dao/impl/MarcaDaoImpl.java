/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.MarcaDao;
import static mx.gob.sat.mat.tabacos.modelo.dao.MarcaDao.SQL_SELECT_TABACALERAS;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.MarcaMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.MarcaResolucionMapper;
import mx.gob.sat.mat.tabacos.modelo.dao.sql.AutorizarSolicitudSQL;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PersistenceException;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MTI Agustin Romero Mata
 */
@Repository
@Transactional
@Qualifier("marcaDaoImpl")
public class MarcaDaoImpl implements MarcaDao {

    private static final Logger LOGGER
            = Logger.getLogger(ProveedorDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    
    
    private static final int VAL1 = 1;
    private static final int VAL2 = 2;
    private static final int VAL3 = 3;
    private static final int VAL4 = 4;
    private static final int DIA_ADIC = 86400000;

    /**
     *
     * @param info
     * @return
     */
    @Override
    public Marcas insertaMarca(Marcas info) {
        Long id;
        Long idtabacalera;

        try {
            id = jdbcTemplate.queryForObject(
                    SQL_SELECT_NEXTID_MARCAS, Long.class);
            idtabacalera = jdbcTemplate.queryForObject(
                    SQL_SELECT_TABACALERAS, new Object[]{info.getRfc()}, Long.class);

            jdbcTemplate.update(SQL_INSERT_MARCAS,
                    new Object[]{id, info.getMarca(), info.getFechaInicio(),
                        info.getFechaFin(), info.getClave(), info.getRutaArchivo(),
                        idtabacalera});
            info.setIdMarca(id);
            return info;
        } catch (DataAccessException e) {
            LOGGER.error("Error al insertar los datos de la marca"
                    + e.getMessage(), e);
            throw new PersistenceException(
                    "Error al insertar los datos de la marca" + e.getMessage(), e);
        }
    }

    /**
     *
     * @param info
     * @param claveBusq
     * @return
     */
    @Override
    public Marcas modificaMarca(Marcas info, String claveBusq) {
        Marcas retorno = null;
        Long idtabacalera;
        try {
            idtabacalera = jdbcTemplate.queryForObject(
                    SQL_SELECT_TABACALERAS, new Object[]{info.getRfc()}, Long.class);

            jdbcTemplate.update(SQL_MOD_MARCAS,
                    new Object[]{
                        info.getMarca(),
                        info.getClave(),
                        idtabacalera,
                        claveBusq
                    });
            retorno = info;
        } catch (DataAccessException e) {
            throw new PersistenceException(e.getMessage(), e);
        }

        return retorno;
    }

    /**
     *
     * @param info
     * @return
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException
     */
    @Override
    public Integer borraMarca(Marcas info) throws MarcaDaoException {
        int retorno = 0;
        Long idBaja;
        Long idtabacalera;
        Long idmarca;
        try {
            info.setIdEstatus(2L);

            jdbcTemplate.update(SQL_DELETE_MARCAS,
                    new Object[]{
                        info.getIdEstatus(),
                        info.getClave()
                    });

            idBaja = jdbcTemplate.queryForObject(
                    SQL_SELECT_NEXTID_BAJA, Long.class);

            idtabacalera = jdbcTemplate.queryForObject(
                    SQL_SELECT_TABACALERAS, new Object[]{info.getRfc()}, Long.class);

            idmarca = jdbcTemplate.queryForObject(
                    SQL_SELECT_MARCAID, new Object[]{info.getClave()}, Long.class);

            if (idtabacalera == null) {
                LOGGER.error("Error tabacalera no existente");
                throw new PersistenceException("Error tabacalera no existente");
            }

            jdbcTemplate.update(SQL_INSERT_BAJA,
                    new Object[]{
                        idBaja, idtabacalera, null, idmarca,
                        info.getMotivoBaja(), info.getFecMovimiento()
                    });

            retorno = 1;
        } catch (DataAccessException e) {
            retorno = 0;
            LOGGER.error(e);
            throw new MarcaDaoException(e);
        }
        return retorno;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Marcas> consultaMarcas() {

        try {
            return jdbcTemplate.query(SQL_SELECT_MARCAS,
                    new RowMapper<Marcas>() {

                        @Override
                        public Marcas mapRow(ResultSet resultSet, int i)
                        throws SQLException {
                            Marcas marca = new Marcas();

                            marca.setClave(resultSet.getString("IDMARCA"));
                            marca.setMarca(resultSet.getString(NOMMARCA));

                            return marca;
                        }
                    });

        } catch (Exception e) {
            throw new PersistenceException(
                    "Error al consultar las marcas" + e.getMessage(), e);
        }
    }

    @Override
    public Integer numeroClaves(String claveMarca) throws MarcaDaoException {
        Integer numeroDeClaves = null;
        SqlRowSet srs = jdbcTemplate.queryForRowSet(SQL_COUNT_CLAVE_MARCA, claveMarca);

        while (srs.next()) {
            numeroDeClaves = srs.getInt("REGISTROS");
        }

        return numeroDeClaves;
    }

    @Override
    public boolean consultaMarcas(Marcas info) {
        Long resultado = null;

        Object[] params = {info.getClave(), info.getMarca(), info.getIdTabacalera()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.NUMERIC};

        try {
            resultado = jdbcTemplate.queryForLong(SQL_SELECT_EXISTE, params, types);

        } catch (Exception e) {
            throw new PersistenceException(ERROR_OBTENER
                    + AutorizarSolicitudSQL.SQL_UPDATE_RESOLUCION
                    + "\n" + e.getMessage(), e);
        }

        return (resultado > 0);
    }

    @Override
    public String obtieneMarca(String idMarca) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_NOMMARCA,
                    new Object[]{idMarca}, String.class);

        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(
                    "Error al recuperar los datos de la marca" + e.getMessage(), e);
        }
    }

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
            throw new PersistenceException(ERROR_OBTENER + strSQL
                    + "\n" + e.getMessage(), e);
        }
    }

    @Override
    public Marcas buscaMarca(String nombre) throws MarcaDaoException {
        try {
            List<Marcas> lstMarcas = jdbcTemplate.query(SQL_BUSCA_MARCA_NOMBRE, new Object[]{nombre},
                    new MarcaMapper());
            if (lstMarcas != null && (!lstMarcas.isEmpty())) {
                return lstMarcas.get(0);
            }
            return new Marcas();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new MarcaDaoException("Error al obtener marca por nombre: ", e);
        }
    }

    @Override
    public List<Marcas> buscaMarcaXAtributo(Long idMarca, String nomMarca, String clave) throws MarcaDaoException {

        try {

            StringBuilder sqlDinamica;
            sqlDinamica = new StringBuilder(SQL_BUSCA_MARCA_HEDER);

            if (idMarca != null) {
                sqlDinamica.append(SQL_BUSCA_MARCA_IDMARCA);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{idMarca},
                        new MarcaMapper());
            } else if (nomMarca != null) {
                sqlDinamica.append(SQL_BUSCA_MARCA_NOMMARCA);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{nomMarca},
                        new MarcaMapper());
            } else if (clave != null) {
                sqlDinamica.append(SQL_BUSCA_MARCA_CLAVE);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{clave},
                        new MarcaMapper());
            }

            return new ArrayList<Marcas>();

        } catch (DataAccessException ex) {
            LOGGER.error(ex);
            throw new MarcaDaoException("Error al obtener marca por buscaMarcaXAtributo : ", ex);
        }

    }

    @Override
    public List<MarcaResolucion> buscaMarcaResolucion(String clave, String rfc, String razonSocial, Date fecha) throws MarcaDaoException {
        try {
            StringBuilder sqlDinamica;
            sqlDinamica = new StringBuilder(SQL_BUSCAR_MARCA_AUTORIZAR_HEDER);

            if (clave != null) {

                sqlDinamica.append(SQL_BUSCA_MARCA_CLAVE);
                sqlDinamica.append(SQL_BUSCAR_MARCA_AUTORIZAR_FOOTER);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{clave},
                        new MarcaResolucionMapper());
            }
            if (rfc != null) {

                sqlDinamica.append(SQL_BUSCA_MARCA_RFC);
                sqlDinamica.append(SQL_BUSCAR_MARCA_AUTORIZAR_FOOTER);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{rfc},
                        new MarcaResolucionMapper());
            }
            if (razonSocial != null) {

                sqlDinamica.append(SQL_BUSCA_MARCA_RAZONSOCIAL);
                sqlDinamica.append(SQL_BUSCAR_MARCA_AUTORIZAR_FOOTER);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{razonSocial},
                        new MarcaResolucionMapper());
            }
            if (fecha != null) {

                sqlDinamica.append(SQL_BUSCA_MARCA_FECHA);
                sqlDinamica.append(SQL_BUSCAR_MARCA_AUTORIZAR_FOOTER);
                return jdbcTemplate.query(sqlDinamica.toString(), new Object[]{fecha},
                        new MarcaResolucionMapper());
            }
            return null;

        } catch (Exception e) {
            throw new MarcaDaoException(e);
        }

    }
    
    @Override
    public List<Marcas> getMarcasByRFCTabacalera(String rfcTabacalera) throws MarcaDaoException {
        try {
            return (List<Marcas>) jdbcTemplate.query(SQL_SELECT_MARCAS_BY_RFC_TAB, new Object[]{rfcTabacalera}, new RowMapper<Marcas>() {

                @Override
                public Marcas mapRow(ResultSet rs, int i) throws SQLException {
                    Marcas marca = new Marcas();
                    marca.setIdMarca(rs.getLong("IDMARCA"));
                    marca.setClave(rs.getString("CLAVE"));
                    marca.setMarca(rs.getString(NOMMARCA));
                    marca.setFechaInicio(rs.getDate("FECINICIO"));
                    marca.setFechaFin(rs.getDate("FECFIN"));
                    marca.setIdEstatus(rs.getLong("IDESTATUS"));
                    marca.setRutaArchivo(rs.getString("RUTAARCHIVO"));
                    marca.setIdTabacalera(rs.getLong("IDTABACALERA"));

                    return marca;
                }

            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR LAS MARCAS DE LA TABACALERA POR RFC";
            LOGGER.error(msgError, e);
            throw new MarcaDaoException(msgError, e);
        }
    }

    @Override
    public List<Marcas> getMarcas() throws MarcaDaoException {
        try {
            return (List<Marcas>) jdbcTemplate.query(SQL_SELECT_MARCAS_TIPO, new RowMapper<Marcas>() {

                @Override
                public Marcas mapRow(ResultSet rs, int i) throws SQLException {
                    Marcas marca = new Marcas();
                    marca.setIdMarca(rs.getLong("IDMARCA"));
                    marca.setClave(rs.getString("CLAVE"));
                    marca.setMarca(rs.getString(NOMMARCA));
                    marca.setFechaInicio(rs.getDate("FECINICIO"));
                    marca.setFechaFin(rs.getDate("FECFIN"));
                    marca.setIdEstatus(rs.getLong("IDESTATUS"));
                    marca.setRutaArchivo(rs.getString("RUTAARCHIVO"));
                    marca.setIdTabacalera(rs.getLong("IDTABACALERA"));

                    return marca;
                }

            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR LAS MARCAS";
            LOGGER.error(msgError, e);
            throw new MarcaDaoException(msgError, e);
        }
    }
    
    /**
     *
     * @param idTabacalera
     * @return marcasProduccion
     * @throws mx.gob.sat.mat.tabacos.modelo.exceptions.MarcaDaoException
     */
    @Override
    public List<Marcas> selectedMarcas(Long idTabacalera) throws MarcaDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_MARCAS_X_TABACALERA,
                    new Object[]{idTabacalera},
                    new RowMapper<Marcas>() {

                        @Override
                        public Marcas mapRow(ResultSet resultSet, int i) throws SQLException {
                            Marcas marca = new Marcas();
                            marca.setIdMarca(resultSet.getLong(Fields.FIELD_MARCAS_ID));
                            marca.setMarca(resultSet.getString(Fields.FIELD_MARCAS_NOMBRE_MARCA));
                            return marca;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR  - Al consultar las marcas de produccion de cigarros: " + e.getMessage(), e);
            throw new MarcaDaoException(e.getMessage(), e);
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
    public List<SolicitudesRegistradas> obtenerSolicitudesRegistradas(int tipo, String valor1, Date valor2) {
        String strQuery = SQL_SELECT_AUTSOLICITUD;
        Object[] parametros = null;
                        
        switch(tipo) {
            case VAL1: strQuery += SQL_SELECT_AUTSOLICITUD_CLAVE;
                    parametros = new Object[]{valor1};
                    break;
            case VAL2: strQuery += SQL_SELECT_AUTSOLICITUD_RFC;                    
                    Long idtabacalera = jdbcTemplate.queryForObject(
                        SQL_SELECT_TABACALERAS,  new Object[]{valor1}, Long.class);                    
                    parametros = new Object[]{idtabacalera};
                    break;
            case VAL3: strQuery += SQL_SELECT_AUTSOLICITUD_RSOCIAL;
                    parametros = new Object[]{valor1};
                    break;
            case VAL4: strQuery += SQL_SELECT_AUTSOLICITUD_FECHA;
                    java.util.Date parametro2 = new java.util.Date(valor2.getTime() + DIA_ADIC);
                    parametros = new Object[]{valor2, parametro2};
                    break;
            default: break;
        }
        
        try {                        
            return jdbcTemplate.query(
                    strQuery, parametros,
                    new RowMapper<SolicitudesRegistradas>() {
                public SolicitudesRegistradas mapRow(ResultSet rs, int i) 
                        throws SQLException {
                    SolicitudesRegistradas solicitud = new SolicitudesRegistradas();
                    solicitud.setClave(rs.getString("CLAVE"));
                    solicitud.setRfc(rs.getString("RFC"));
                    solicitud.setFecha(rs.getString("FECINICIO"));                    
                    solicitud.setMarca(rs.getString(NOMMARCA));
                    solicitud.setRazonSocial(rs.getString(Fields.FIELD_SOLAUT_RAZONSOCIAL));                    
                    solicitud.setEnProceso(rs.getLong(Fields.FIELD_SOLAUT_PROC));                    
                    return solicitud;
                }
            });
        } catch (Exception e) {
            throw new PersistenceException("Error al obtener solicitudes: " 
                    + e.getMessage(), e);
        }
    }

    /**
     * 
     * @param strSQL
     * @param id
     * @param name
     * @return 
     * @throws MarcaDaoException 
     */
    @Override
    public List<SelectItem> getCombosMarcas(final String strSQL, final String id, final String name) throws MarcaDaoException {
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
            throw new MarcaDaoException(ERROR_OBTENER + strSQL + 
                    "\n" + e.getMessage(), e);
        }
    }

    /**
     * 
     *
     * @param info
     * @return 
     * @throws MarcaDaoException 
     */
    @Override
    public int autorizarSolicitud(MarcaResolucion info) throws MarcaDaoException{        
        Object[] params = {info.getEstatusResolucion(), 
            info.getServidorPublicoAutorizante(),
            info.getDocumentoResolucion(),
            info.getFechaInicio(),
            info.getClave()};
        int[] types = {Types.INTEGER, Types.INTEGER, 
            Types.VARCHAR, Types.DATE, Types.VARCHAR};
        int rows = 0;
        
        try {
            rows = jdbcTemplate.update(SQL_UPDATE_MARCA, params, types);
        } catch (DataAccessException e) {
            throw new MarcaDaoException(ERROR_OBTENER + 
                SQL_UPDATE_MARCA + "\n" + e.getMessage(), e);
        }
        return rows;
    }  
}
