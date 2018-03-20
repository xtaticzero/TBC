/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public interface ReporterService {
    
    String MSG_ERROR = "No se pudo generar el reporte";

    byte[] makeReportJASPER(InputStream streamFileJASPER, Map parametersReport, JRBeanCollectionDataSource lstBeanDataSource) throws ReporterJasperException;

    void makeReportJASPER(InputStream streamFileJASPER, Map parametersReport, JRBeanCollectionDataSource lstBeanDataSource, String pathFile) throws ReporterJasperException;
    
    byte[] makeReportJRXML(InputStream streamFileJRXML,Map parametersReport,JRBeanCollectionDataSource lstBeanDataSource) throws ReporterJasperException;
    
    byte[] makeReport(ReportesTabacosEnum reporteEnum, String nombreReporte,
                                      Map<String, Object> parametros,
                                      List<?> detalle) throws ReporterJasperException;
    
}
