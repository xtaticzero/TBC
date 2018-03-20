package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import static mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao.SQL_SELECT_ORIGEN;
import static mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao.SQL_SELECT_PAIS;
import static mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao.SQL_SELECT_PAIS_BY_CODIGO;
import static mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao.SQL_SELECT_PAIS_BY_ID;
import mx.gob.sat.mat.tabacos.modelo.dao.mapper.PaisOrigenMapper;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
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
 * @author Ing Salvador Pocteco Saldaña
 */
@Repository
@Transactional
@Qualifier("paisDaoImpl")
public class PaisOrigenDaoImpl implements PaisOrigenDao {

    private static final Logger LOGGER = Logger.getLogger(PaisOrigenDaoImpl.class);

    @Autowired(required = false)
    @Qualifier("jdbcTemplateRfcTBC")
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @return List PaisOrigen
     * @throws PaisDaoException
     * @throws PersistenceException
     */
    @Override
    public List<PaisOrigen> selectedPais() throws PaisDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_PAIS, new RowMapper<PaisOrigen>() {
                @Override
                public PaisOrigen mapRow(ResultSet resultSet, int i) throws SQLException {
                    PaisOrigen pais = new PaisOrigen();
                    pais.setIdPais(resultSet.getLong(Fields.FIELD_PAIS_ID));
                    pais.setDescCorta(resultSet.getString(Fields.FIELD_PAIS_DESC_CORTA));
                    return pais;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al consultar los Paises" + e.getMessage(), e);
            throw new PaisDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List PaisOrigen
     * @throws PaisDaoException
     */
    @Override
    public List<PaisOrigen> selectedOrigen() throws PaisDaoException {
        try {
            return jdbcTemplate.query(SQL_SELECT_ORIGEN, new RowMapper<PaisOrigen>() {
                @Override
                public PaisOrigen mapRow(ResultSet resultSet, int i) throws SQLException {
                    PaisOrigen origen = new PaisOrigen();
                    origen.setIdPais(resultSet.getLong(Fields.FIELD_PAIS_ID));
                    origen.setDescLarga(resultSet.getString(Fields.FIELD_PAIS_DESC_LARGA));
                    origen.setDescCorta(resultSet.getString(Fields.FIELD_PAIS_DESC_CORTA));
                    return origen;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR - Al obtener el Origen" + e.getMessage(), e);
            throw new PaisDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param codigo
     * @return PaisOrigen     
     * @throws PaisDaoException
     */
    @Override
    public PaisOrigen getPaisByCodigo(String codigo) throws PaisDaoException {
        try {
            return (PaisOrigen) jdbcTemplate.queryForObject(SQL_SELECT_PAIS_BY_CODIGO, new Object[]{codigo}, new RowMapper<PaisOrigen>(){

                @Override
                public PaisOrigen mapRow(ResultSet rs, int i) throws SQLException {
                    PaisOrigen po = new PaisOrigen();
                    po.setIdPais(rs.getLong(Fields.FIELD_PAIS_ID));
                    po.setClavePais(rs.getString(Fields.FIELD_PAIS_CLAVEPAIS));
                    po.setCodigoPais(rs.getString("CODIGOPAIS"));
                    po.setDescCorta(rs.getString(Fields.FIELD_PAIS_DESC_CORTA));
                    po.setDescLarga(rs.getString(Fields.FIELD_PAIS_DESC_LARGA));
                    po.setUsuarioCreador(rs.getString("USUARIOCREADOR"));
                    po.setFechaCreacion(rs.getDate("FECHACREACION"));
                    po.setUsuarioModificador(rs.getString("USUARIOMODIFICADOR"));
                    po.setFechaModificacion(rs.getDate("FECHAMODIFICACION"));
                    po.setFechaInicioVigencia(rs.getDate("FECHAINICIOVIGENCIA"));
                    po.setFechaFinVigencia(rs.getDate("FECHAFINVIGENCIA"));
                    
                    return po;
                }                
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR AL OBTENER EL PAIS POR CODIGO: ", e);
            throw new PaisDaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param idPais
     * @return PaisOrigen
     * @throws PaisDaoException
     */
    @Override
    public PaisOrigen getPaisById(Long idPais) throws PaisDaoException {
        try {
            return (PaisOrigen) jdbcTemplate.queryForObject(SQL_SELECT_PAIS_BY_ID, new Object[]{idPais}, new RowMapper<PaisOrigen>(){

                @Override
                public PaisOrigen mapRow(ResultSet rs, int i) throws SQLException {
                    PaisOrigen po = new PaisOrigen();
                    po.setIdPais(rs.getLong(Fields.FIELD_PAIS_ID));
                    po.setClavePais(rs.getString(Fields.FIELD_PAIS_CLAVEPAIS));
                    po.setCodigoPais(rs.getString("CODIGOPAIS"));
                    po.setDescCorta(rs.getString(Fields.FIELD_PAIS_DESC_CORTA));
                    po.setDescLarga(rs.getString(Fields.FIELD_PAIS_DESC_LARGA));
                    po.setUsuarioCreador(rs.getString("USUARIOCREADOR"));
                    po.setFechaCreacion(rs.getDate("FECHACREACION"));
                    po.setUsuarioModificador(rs.getString("USUARIOMODIFICADOR"));
                    po.setFechaModificacion(rs.getDate("FECHAMODIFICACION"));
                    po.setFechaInicioVigencia(rs.getDate("FECHAINICIOVIGENCIA"));
                    po.setFechaFinVigencia(rs.getDate("FECHAFINVIGENCIA"));
                    
                    return po;
                }                
            });
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("ERROR AL OBTENER EL PAIS POR CODIGO: ", e);
            throw new PaisDaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<PaisOrigen> getLstPaisOrigen(Long... idPaises) throws PaisDaoException {
        try {
            StringBuilder queryStr;
            if (idPaises != null) {
                queryStr = new StringBuilder(SQL_SELECT_PAIS_HEDER);
                queryStr = queryStr.append("IDPAIS in (");
                for (int i = 0; i < idPaises.length; i++) {
                    if (i == 0) {
                        queryStr = queryStr.append(idPaises[i]);
                    } else {
                        queryStr = queryStr.append(",").append(idPaises[i]);
                    }
                }
                queryStr = queryStr.append(")");

                return jdbcTemplate.query(queryStr.toString(), new PaisOrigenMapper());
            }
            return new ArrayList<PaisOrigen>();
        } catch (EmptyResultDataAccessException e) {
            throw new PaisDaoException(e.getMessage(), e);
        } catch (DataAccessException e) {
            LOGGER.error("ERROR AL OBTENER EL PAIS POR CODIGO: " + e.getMessage(), e);
            throw new PaisDaoException(e.getMessage(), e);
        }

    }

}
