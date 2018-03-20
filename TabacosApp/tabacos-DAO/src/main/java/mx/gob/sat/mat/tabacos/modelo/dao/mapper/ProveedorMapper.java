/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ProveedorMapper implements ParameterizedRowMapper<Proveedor> {
    
    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Proveedor mapRow(ResultSet resultSet, int i) throws SQLException {
        Proveedor proveedor = new Proveedor();
        Estatus estatus = new Estatus();
        
        proveedor.setRazonSocial(resultSet.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL));
        proveedor.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
        proveedor.setRfc(resultSet.getString(Fields.FIELD_PROVEEDOR_RFC));
        proveedor.setFecCaptura(resultSet.getDate(Fields.FIELD_PROVEEDOR_FECHA_CAPTURA));
        proveedor.setFecRegistro(resultSet.getDate(Fields.FIELD_PROVEEDOR_FECHA_REGISTRO));
        estatus.setIdEstatus(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID_ESTATUS));
        estatus.setNomEstatus(resultSet.getString(Fields.FIELD_PROVEEDOR_NOMESTATUS));
        proveedor.setEstatus(estatus);
        
        return proveedor;
    }
}
