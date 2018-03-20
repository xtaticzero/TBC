/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReporteContribuyenteMapper implements RowMapper<Tabacalera> {

    @Override
    public Tabacalera mapRow(ResultSet rs, int i) throws SQLException {
        Tabacalera tabacalera = new Tabacalera();
        tabacalera.setRfc(rs.getString(Fields.RFC));
        tabacalera.setRazonSocial(rs.getString(Fields.RAZON_SOCIAL));
        tabacalera.setFecRegistro(rs.getTimestamp(Fields.MOVIMIENTO));
        
        return tabacalera;
    }

}
