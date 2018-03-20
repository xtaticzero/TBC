/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class MarcaResolucionMapper implements RowMapper<MarcaResolucion>{

    @Override
    public MarcaResolucion mapRow(ResultSet rs, int i) throws SQLException {
        MarcaResolucion marcaRes = new MarcaResolucion();
        
        marcaRes.setIdServidorPublico(rs.getLong(Fields.ID_TABACALERA));
        marcaRes.setRfc(rs.getString(Fields.RFC));
        marcaRes.setRazonsocial(rs.getString(Fields.RAZON_SOCIAL));
        marcaRes.setIdMarca(rs.getLong(Fields.FIELD_MARCAS_ID));
        marcaRes.setMarca(rs.getString(Fields.FIELD_MARCAS_NOMBRE_MARCA));
        marcaRes.setClave(rs.getString(Fields.CLAVE_MARCA));
        marcaRes.setFechaInicio(rs.getDate(Fields.MARCA_FECINICIO));
        marcaRes.setFechaFin(rs.getDate(Fields.MARCA_FECFIN));
        marcaRes.setIdEstatus(rs.getLong(Fields.MARCA_IDESTATUS));
        marcaRes.setEstatus(rs.getString(Fields.MARCA_ESTATUS));
        marcaRes.setIdServidorPublico(rs.getLong(Fields.MARCA_SPAUTORIZA));
        marcaRes.setDocumentoResolucion(rs.getString(Fields.MARCA_RUTAARCHIVO));
                
        return marcaRes;
    }
    
}
