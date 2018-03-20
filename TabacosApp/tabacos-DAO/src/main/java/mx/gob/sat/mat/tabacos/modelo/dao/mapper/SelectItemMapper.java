/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SelectItemMapper implements RowMapper<SelectItem> {

    @Override
    public SelectItem mapRow(ResultSet rs, int i) throws SQLException {
        SelectItem item = new SelectItem();
        item.setId(rs.getString(Fields.VALUE));
        item.setValor(rs.getString(Fields.LABEL));
        return item;
    }

}
