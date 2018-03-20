/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class MarcaMapper implements RowMapper<Marcas> {

    @Override
    public Marcas mapRow(ResultSet rs, int i) throws SQLException {
        Marcas item = new Marcas();
        item.setIdMarca(rs.getLong("IDMARCA"));
        item.setClave(rs.getString("CLAVE"));
        item.setMarca(rs.getString("NOMMARCA"));
        item.setRfc(rs.getString("RFC"));
        item.setRazonSocial(rs.getString("RAZONSOCIAL"));
        return item;
    }

}
