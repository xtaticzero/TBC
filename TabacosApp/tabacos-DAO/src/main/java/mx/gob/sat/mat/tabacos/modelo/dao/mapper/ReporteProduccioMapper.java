package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteProduccion;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ReporteProduccioMapper implements RowMapper<ReporteProduccion> {

    @Override
    public ReporteProduccion mapRow(ResultSet resultSet, int i) throws SQLException {
        ReporteProduccion reporteProduccion = new ReporteProduccion();

        reporteProduccion.setIdProveedor(resultSet.getLong(Fields.FIELD_PROVEEDOR_ID));
        reporteProduccion.setRfc(resultSet.getString(Fields.FIELD_TABACALERA_RFC));
        reporteProduccion.setRazonSocialProveedor(resultSet.getString(Fields.FIELD_PROVEEDOR_RAZON_SOCIAL_ALIAS));
        reporteProduccion.setRazonSocialContribuyente(resultSet.getString(Fields.FIELD_TABACALERA_RAZON_SOCIAL_ALIAS));
        reporteProduccion.setLoteProduccion(resultSet.getString(Fields.FIELD_PRODUCCION_LOTE));
        reporteProduccion.setPlantaProduccion(resultSet.getString(Fields.FIELD_PLANTA_NOMBRE));
        reporteProduccion.setCantidadProduccion(resultSet.getInt(Fields.FIELD_PRODUCCION_CANTIDAD_PRODUCCION));
        reporteProduccion.setMaquinaProduccion(resultSet.getString(Fields.FIELD_PRODUCCION_DESCRIPCION_MAQUINA));
        reporteProduccion.setNombrePais(resultSet.getString(Fields.FIELD_PRODUCCION_DESCRIPCION_PAIS));
        reporteProduccion.setLineaProduccion(resultSet.getString(Fields.FIELD_PRODUCCION_LINEA_PRODUCCION));

        return reporteProduccion;
    }

}
