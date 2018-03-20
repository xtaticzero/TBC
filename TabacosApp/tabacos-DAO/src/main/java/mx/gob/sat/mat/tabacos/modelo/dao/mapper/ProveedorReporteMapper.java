/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ProveedorReporteMapper implements RowMapper<Proveedor> {

    @Override
    public Proveedor mapRow(ResultSet rs, int i) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getLong(Fields.FIELD_PROVEEDOR_ID));
        proveedor.setRfc(rs.getString(Fields.FIELD_PROVEEDOR_RFC));
        proveedor.setRazonSocial(rs.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL));

        return proveedor;
    }

}
