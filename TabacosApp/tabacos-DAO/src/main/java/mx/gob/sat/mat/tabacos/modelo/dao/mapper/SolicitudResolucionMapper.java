/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudResolucionMapper implements ParameterizedRowMapper<SolicitudResolucion> {

    public SolicitudResolucion mapRow(ResultSet rs, int i) throws SQLException {
        SolicitudResolucion solicitud = new SolicitudResolucion();

            solicitud.setIdSolicitud(rs.getLong(Fields.FIELD_SOL_RES_IDSOLICITUD));
            solicitud.setRfcTavacalera(rs.getString(Fields.FIELD_SOL_RES_RFC));
            solicitud.setRfcProveedor(rs.getString(Fields.FIELD_SOL_RES_PAS));
            solicitud.setCantidadCodigos(rs.getLong(Fields.FIELD_SOL_RES_CANTSOLICITADA));
            solicitud.setCantidadAutorizada(rs.getLong(Fields.FIELD_SOL_RES_CANTAUTORIZADA));
            solicitud.setFechaSolicitud(rs.getDate(Fields.FIELD_SOL_RES_FECSOLICITUD));
            solicitud.setIdPaisOrigen(rs.getLong(Fields.FIELD_SOL_RES_ORIGEN));
            solicitud.setEstatus(rs.getString(Fields.FIELD_SOL_RES_RESOLUCION));
            solicitud.setFechaResolucion(rs.getDate(Fields.FIELD_SOL_RES_FECRESOLUCION));

            return solicitud;
    }
    
}
