/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ConfiguracionJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.util.GeneradorReportes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Service
@Qualifier("reporterService")
public class ReporterServiceImpl implements ReporterService{

    @Override
    public byte[] makeReportJASPER(InputStream streamFileJASPER, Map parametersReport, JRBeanCollectionDataSource lstBeanDataSource) throws ReporterJasperException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        byte[] archivo;
        archivo=null;
        try{
            jasperReport = (JasperReport) JRLoader.loadObject(streamFileJASPER);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport,lstBeanDataSource);
            archivo = JasperExportManager.exportReportToPdf(jasperPrint);            
        }catch(JRException jrex){
            throw new ReporterJasperException(MSG_ERROR,jrex);
        }catch(Exception fne){
            throw new ReporterJasperException(MSG_ERROR,fne);
        }
        return archivo;  
    }

    @Override
    public void makeReportJASPER(InputStream streamFileJASPER, Map parametersReport, JRBeanCollectionDataSource lstBeanDataSource, String pathFile) throws ReporterJasperException {
        try {
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            jasperReport = (JasperReport) JRLoader.loadObject(streamFileJASPER);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, lstBeanDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint,pathFile);
        } catch (JRException ex) {
            throw new ReporterJasperException(MSG_ERROR,ex);
        }
    }

    @Override
    public byte[] makeReportJRXML(InputStream streamFileJRXML, Map parametersReport, JRBeanCollectionDataSource lstBeanDataSource) throws ReporterJasperException {
        byte[] archivo = null;        
        try {
            JasperDesign jasperDesign;
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            
            
            jasperDesign = JRXmlLoader.load(streamFileJRXML);
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, lstBeanDataSource);
            
            archivo = JasperExportManager.exportReportToPdf(jasperPrint);
             
            return archivo;
        } catch (JRException ex) {
            throw new ReporterJasperException(MSG_ERROR,ex);
        }
        
    }
    
    @Override
    public byte[] makeReport(ReportesTabacosEnum reporteEnum, String nombreReporte,
                                      Map<String, Object> parametros,
                                      List<?> detalle) throws ReporterJasperException{
        try{
            InputStream fileIS = this.getClass().getClassLoader().getResourceAsStream((reporteEnum.getPath()));
            return GeneradorReportes.crearReporte(fileIS, nombreReporte, parametros, detalle);
        } catch(ConfiguracionJasperException cje){
            throw new ReporterJasperException(MSG_ERROR,cje);
        }
    
    }
    
}
