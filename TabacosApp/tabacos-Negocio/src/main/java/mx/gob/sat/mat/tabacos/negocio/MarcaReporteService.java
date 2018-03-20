/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;

/**
 *
 * @author emmanuel
 */
public interface MarcaReporteService {
    
    int ALTA    = 1;
    int CAMBIO  = 2;
    int BAJA    = 3;

    byte[] makeReportMarcas(ReportesTabacosEnum reporte, ReportesFiltroBase filtro, int tipo) throws ServiceException;
}
