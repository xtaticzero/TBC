/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteDesperdiciosDTO;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReporteDesperdiciosMapper implements RowMapper<ReporteDesperdiciosDTO> {

    @Override
    public ReporteDesperdiciosDTO mapRow(ResultSet rs, int i) throws SQLException {
        ReporteDesperdiciosDTO reportDesperdicios = new ReporteDesperdiciosDTO();

        reportDesperdicios.setProducto(rs.getString(Fields.FIELD_MARCAS_NOMBRE_MARCA));
        reportDesperdicios.setPlantaProduccion(rs.getString(Fields.FIELD_PLANTA_NOMBRE));
        reportDesperdicios.setCantidadProduccion(rs.getInt(Fields.FIELD_PRODUCCION_CANTIDAD_PRODUCCION));
        reportDesperdicios.setNombrePais(rs.getString(Fields.FIELD_PRODUCCION_DESCRIPCION_PAIS));
        reportDesperdicios.setMaquinaProduccion(rs.getString(Fields.FIELD_PRODUCCION_DESCRIPCION_MAQUINA));
        reportDesperdicios.setLoteProduccion(rs.getString(Fields.FIELD_PRODUCCION_LOTE));
        reportDesperdicios.setFechaHora(rs.getTimestamp(Fields.FIELD_PRODUCCION_FECHA_PRODUCCION));
        reportDesperdicios.setCantidadDestruccion(rs.getInt(Fields.FIELD_PRODUCCION_CANTIDAD_CIGARROS));
        reportDesperdicios.setNumeroRegistro(rs.getInt(Fields.FIELD_PRODUCCION_ID_PRODUCCION));

        return reportDesperdicios;
    }

}
