/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class TabacaleraMapper implements RowMapper<Tabacalera> {

    @Override
    public Tabacalera mapRow(ResultSet rs, int i) throws SQLException {

        Tabacalera tabacalera = new Tabacalera();

        tabacalera.setIdTabacalera(rs.getLong(Fields.FIELD_LIKE_IDTABACALERA));
        tabacalera.setRfc(rs.getString(Fields.FIELD_LIKE_RFC));
        tabacalera.setRazonSocial(rs.getString(Fields.FIELD_LIKE_RAZONSOCIAL));
        tabacalera.setCorreoElectronico(rs.getString(Fields.FIELD_LIKE_CORREOE));
        tabacalera.setDomicilio(rs.getString(Fields.FIELD_LIKE_DOMICILIO));

        tabacalera.setEstatus(new Estatus());
        tabacalera.getEstatus().setIdEstatus(rs.getLong(Fields.FIELD_LIKE_IDESTATUS));
        tabacalera.getEstatus().setNomEstatus(rs.getString(Fields.FIELD_LIKE_ESTATUS));

        return tabacalera;

    }

}
