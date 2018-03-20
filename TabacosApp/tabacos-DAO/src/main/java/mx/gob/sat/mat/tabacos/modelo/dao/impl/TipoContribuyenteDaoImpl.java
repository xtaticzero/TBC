/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.TipoContribuyenteDao;
import static mx.gob.sat.mat.tabacos.modelo.dao.sql.TipoContribuyenteSQL.SQL_SELECT_TIPOCONTRIB;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TipoContribuyenteDaoException;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Repository
@Transactional
@Qualifier("tipoContribuyenteDao")
public class TipoContribuyenteDaoImpl implements TipoContribuyenteDao{
    
    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;

    public List<TipoContribuyente> getLstTipoContribuyente() throws TipoContribuyenteDaoException{
        try {
           return jdbcTemplate.query(SQL_SELECT_TIPOCONTRIB, new RowMapper<TipoContribuyente>() {
                public TipoContribuyente mapRow(ResultSet resultSet, int i) throws SQLException {
                    TipoContribuyente tipoContrib;
                    tipoContrib = new TipoContribuyente();
                    tipoContrib.setIdTipoContrib(resultSet.getLong(Fields.FIELD_IDTIPOCONTRIB));
                    tipoContrib.setDescTipoContrib(resultSet.getString(Fields.FIELD_DESCTIPOCONTRIB));
                    tipoContrib.setFecInicio(resultSet.getDate(Fields.FIELD_FECINICIO));
                    tipoContrib.setFecFin(resultSet.getDate(Fields.FIELD_FECFINAL));
                    return tipoContrib;                    
                }
            });
        } catch (Exception e) {
            throw new TipoContribuyenteDaoException("Error al obtener fecha: " + e.getMessage(), e);
        }
    }
    
}
