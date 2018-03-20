/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoDao;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
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
@Qualifier("codigoDaoImpl")
public class CodigoDaoImpl implements CodigoDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    

    public Codigo buscarPorID(String id) throws DaoException {
        Object[] sqlParams = {id};
        List<Codigo> resultados = jdbcTemplate.query(SQL_SELECT_CODIGO, new CodigoMapper(), sqlParams);
        return  resultados != null && !resultados.isEmpty() ? resultados.get(0) : null;
    }

    private static class CodigoMapper implements ParameterizedRowMapper<Codigo> {

        @Override
        public Codigo mapRow(ResultSet rs, int row) throws SQLException {
            Codigo codigo = new Codigo();
            codigo.setNumeroCodigo(rs.getString("IDCODIGO"));
            codigo.setFolioInicial(rs.getString("FOLIO"));
            codigo.setIdSolicitud(rs.getLong("IDSOLICITUD"));
            codigo.setFecCaptura(rs.getDate("FECCAPTURA"));
            return codigo;
        }
    }

}
