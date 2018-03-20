/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.persistence.ReportesFields;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author emmanuel
 */
public class MarcaReporteMapper implements ParameterizedRowMapper<Marcas> {

    @Override
    public Marcas mapRow(ResultSet rs, int i) throws SQLException {

        Marcas marca = new Marcas();
        marca.setFecMovimiento(rs.getDate(ReportesFields.FIELD_CONTRIBUYENTE_FECHAMOD));
        marca.setMarca(rs.getString(ReportesFields.FIELD_MARCA_MARCA));
        return marca;
    }

}
