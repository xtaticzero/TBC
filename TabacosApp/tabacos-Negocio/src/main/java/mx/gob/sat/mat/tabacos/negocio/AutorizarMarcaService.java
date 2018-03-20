/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.MarcaResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public interface AutorizarMarcaService {

    List<SelectItem> obtenerCombos(final String strSQL,
            final String id, final String valor) throws ServiceException;

    List<SolicitudesRegistradas> obtenerSolicitudes(int tipo,
            String valor1, Date valor2);

    int autorizarSolicitud(MarcaResolucion info) throws ServiceException;

    byte[] generarAcuse(MarcaResolucion info);

    MarcaResolucion buscaMarcaXClave(String clave) throws ServiceException;
}
