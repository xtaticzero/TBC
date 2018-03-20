/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.persistence.Fields;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudAutorisadaMapper implements ParameterizedRowMapper<AutorizacionResol> {

    @Override
    public AutorizacionResol mapRow(ResultSet rs, int i) throws SQLException {
        AutorizacionResol autorizacionResol;
        Archivo archivo;
        autorizacionResol = new AutorizacionResol();

        do {
            if (!((Long.valueOf(rs.getString(Fields.FIELD_SOL_AUTORIZADA_FOLIO))).equals(autorizacionResol.getIdSolicitud()))) {
                autorizacionResol = new AutorizacionResol();
                autorizacionResol.setIdSolicitud(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_FOLIO));
                autorizacionResol.setRfcTavacalera(rs.getString(Fields.FIELD_SOL_AUTORIZADA_RFC));
                autorizacionResol.setRazonSocial(rs.getString(Fields.FIELD_SOL_AUTORIZADA_CONTRIBUYENTE));
                autorizacionResol.setFechaResolucion(rs.getDate(Fields.FIELD_SOL_AUTORIZADA_FECHARES));
                autorizacionResol.setCodigosAutorizados(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_CANTAUTORIZADA));
                autorizacionResol.setFolioInicial(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_FOLIOINICIAL));
                autorizacionResol.setFolioFinal(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_FOLIOFINAL));
                autorizacionResol.setLstArchivos(new ArrayList<Archivo>());
            }
            archivo = new Archivo();
            archivo.setIdSolicitud(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_FOLIO));
            archivo.setIdArchivo(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_IDARCHIVO));
            archivo.setNumLinea(rs.getLong(Fields.FIELD_SOL_AUTORIZADA_NUMLINEAS));
            archivo.setRutaArchivo(rs.getString(Fields.FIELD_SOL_AUTORIZADA_RUTAARCHIVO));
            autorizacionResol.getLstArchivos().add(archivo);
        } while (rs.next());

        return autorizacionResol;
    }

}
