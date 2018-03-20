/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class PaisOrigenMapper implements ParameterizedRowMapper<PaisOrigen> {

    public PaisOrigen mapRow(ResultSet resultSet, int i) throws SQLException {
        PaisOrigen origen = new PaisOrigen();
        origen.setIdPais(resultSet.getLong(Fields.FIELD_PAIS_ID));
        origen.setClavePais(resultSet.getString(Fields.FIELD_PAIS_CLAVEPAIS));
        origen.setDescLarga(resultSet.getString(Fields.FIELD_PAIS_DESC_LARGA));
        origen.setDescCorta(resultSet.getString(Fields.FIELD_PAIS_DESC_CORTA));
        return origen;
    }

}
