/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class RepresentanteLegalMapper implements RowMapper<RepresentanteLegal> {

    @Override
    public RepresentanteLegal mapRow(ResultSet rs, int i) throws SQLException {
        RepresentanteLegal representanteLegal = new RepresentanteLegal();
        representanteLegal.setIdRepLegal(rs.getLong(Fields.FIELD_REPLEGAL_IDREPLEGAL));
        representanteLegal.setIdTabacalera(rs.getLong(Fields.FIELD_REPLEGAL_IDTABACALERA));
        representanteLegal.setIdProveedor(rs.getLong(Fields.FIELD_REPLEGAL_IDPROVEEDOR));
        representanteLegal.setNombre(rs.getString(Fields.FIELD_REPLEGAL_NOMBRE));
        representanteLegal.setApellidoPaterno(rs.getString(Fields.FIELD_REPLEGAL_APELLIDOPATERNO));
        representanteLegal.setApellidoMaterno(rs.getString(Fields.FIELD_REPLEGAL_APELLIDOMATERNO));
        representanteLegal.setCorreoElectronico(rs.getString(Fields.FIELD_REPLEGAL_CORREOELECTRONICO));
        representanteLegal.setTelefono(rs.getString(Fields.FIELD_REPLEGAL_TELEFONO));
        representanteLegal.setFecInicio(rs.getTimestamp(Fields.FIELD_REPLEGAL_FECINICIO));
        representanteLegal.setFecFin(rs.getTimestamp(Fields.FIELD_REPLEGAL_FECFIN));
        representanteLegal.setRfc(rs.getString(Fields.FIELD_REPLEGAL_RFC));
        representanteLegal.setIdTipoRepLegal(rs.getLong(Fields.FIELD_REPLEGAL_IDTIPOREPLEGAL));
        return representanteLegal;
    }

}
