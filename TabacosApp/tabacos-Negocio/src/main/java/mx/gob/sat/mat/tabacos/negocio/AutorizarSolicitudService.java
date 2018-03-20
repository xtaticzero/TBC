/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface AutorizarSolicitudService {

    List<SelectItem> obtenerCombos(final String strSQL,
            final String id, final String valor);

    List<SolicitudesRegistradas> obtenerSolicitudes(int tipo,
            String valor1, Date valor2) throws ServiceException;

    List<SolitudAutorizada> obtenerSolRegActual(int tipo,
            String valor1, Date valor2);

    int autorizarSolicitud(SolitudAutorizada info, InputStream contenidoArchivo, String rutaArchivo, String nombreArchivo) throws ServiceException;

    byte[] generarAcuse(SolitudAutorizada info);

    String getDirectorioBase();

    String getDirDoctosAutSolicitud();
}
