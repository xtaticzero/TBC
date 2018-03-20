/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporteServiceException;

/**
 *
 * @author platon
 */
public interface ReporteService {

    byte[] makeReportContriyentes(ReportesTabacosEnum reporte, ReportesFiltroBase filtro, int tipo) throws ReporteServiceException;

    byte[] makeReportRegistros(ReportesTabacosEnum reporte, ReportesRegistrosYDespedicios filtro, int tipo) throws ReporteServiceException;

    byte[] makeReportDesperdicios(ReportesTabacosEnum reporte, ReportesRegistrosYDespedicios filtro, int tipo) throws ReporteServiceException;

    List<SelectItem> consultaCombos(final String strSQL, final String id, final String name) throws ReporteServiceException;

    List<SelectItem> consultaCombos(final String strSQL, final String id, final String name, String param1);
    
    List<SelectItem> consultaCombosMarcasAutorizadas() throws ReporteServiceException;
    
    List<SelectItem> consultaCboMarcasAutorizadasXRfc(String rfc) throws ReporteServiceException;

    String consultarRazonSocial(final String rfc) throws ReporteServiceException;

    String consultarRazonSocialProv(final String rfc);

    byte[] makeReportContriyentes(ReportesFiltroBase filtro) throws ReporteServiceException;    
}
