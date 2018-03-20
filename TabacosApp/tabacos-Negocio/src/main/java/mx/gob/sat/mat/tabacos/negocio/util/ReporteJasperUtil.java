/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.constants.enums.TipoReportesJasperEnum;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ConfiguracionJasperException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReporteJasperUtil {

    public static final String PDF = "pdf";
    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    public static final String DOCX = "docx";
    public static final String PPTX = "pptx";
    public static final String ODS = "ods";
    public static final String ODT = "odt";
    public static final String CSV = "csv";
    public static final String RTF = "rtf";
    public static final String TXT = "txt";
    public static final String HTML = "html";
    public static final String XML = "xml";

    private static final int CASE_PDF = 1;
    private static final int CASE_XLS = 2;
    private static final int CASE_XLSX = 3;
    private static final int CASE_DOCX = 4;
    private static final int CASE_PPTX = 5;
    private static final int CASE_ODS = 6;
    private static final int CASE_ODT = 7;
    private static final int CASE_CSV = 8;
    private static final int CASE_RTF = 9;
    private static final int CASE_TXT = 10;
    private static final int CASE_HTML = 11;
    private static final int CASE_XML = 12;

    private String reporteJasper;
    private List<?> datosReporte;
    private Map<String, Object> parametrosReporte;
    private String nombreReporte;
    private String formatoReporte;
    private String rutaReporte;
    private JasperPrint jasperPrint;
    private InputStream reporteJasperIS;

    private static final Logger LOG = Logger.getLogger(ReporteJasperUtil.class);

    public ReporteJasperUtil() {
        this(null, new ArrayList(), null, null);
    }

    public ReporteJasperUtil(String reporteJasper) {
        this(reporteJasper, new ArrayList(), null, null);
    }

    public ReporteJasperUtil(String reporteJasper, List<Object> datosReporte) {
        this(reporteJasper, datosReporte, null, null);
    }

    public ReporteJasperUtil(String reporteJasper, Object[] datosReporte) {
        this(reporteJasper, datosReporte, null, null);
    }

    public ReporteJasperUtil(String reporteJasper, List<Object> datosReporte, Map<String, Object> parametrosReporte) {
        this(reporteJasper, datosReporte, parametrosReporte, null);
    }

    public ReporteJasperUtil(String reporteJasper, Object[] datosReporte, Map<String, Object> parametrosReporte) {
        this(reporteJasper, datosReporte, parametrosReporte, null);
    }

    public ReporteJasperUtil(String reporteJasper, List<Object> datosReporte, Map<String, Object> parametrosReporte, String formatoReporte) {
        if (datosReporte.isEmpty()) {
            this.datosReporte = null;
        } else {
            this.datosReporte = datosReporte;
        }
        this.reporteJasper = reporteJasper;
        this.parametrosReporte = parametrosReporte;
        this.formatoReporte = formatoReporte;
    }

    public ReporteJasperUtil(String reporteJasper, Object[] datosReporte, Map<String, Object> parametrosReporte, String formatoReporte) {
        this.datosReporte = (Arrays.asList(datosReporte));
        this.reporteJasper = reporteJasper;
        this.parametrosReporte = parametrosReporte;
        this.formatoReporte = formatoReporte;
    }

    public ReporteJasperUtil(String reporteJasper, List<Object> datosReporte, Map<String, Object> parametrosReporte, String formatoReporte, String nombreReporte) {
        if (datosReporte.isEmpty()) {
            this.datosReporte = null;
        } else {
            this.datosReporte = datosReporte;
        }
        this.reporteJasper = reporteJasper;
        this.parametrosReporte = parametrosReporte;
        this.formatoReporte = formatoReporte;
        this.nombreReporte = nombreReporte;
    }

    public ReporteJasperUtil(String reporteJasper, Object[] datosReporte, Map<String, Object> parametrosReporte, String formatoReporte, String nombreReporte) {
        this.datosReporte = (Arrays.asList(datosReporte));
        this.reporteJasper = reporteJasper;
        this.parametrosReporte = parametrosReporte;
        this.formatoReporte = formatoReporte;
        this.nombreReporte = nombreReporte;
    }

    public void agregarParametro(String identificador, Object valor) {
        if (this.parametrosReporte == null) {
            this.parametrosReporte = new HashMap();
        }
        this.parametrosReporte.put(identificador, valor);
    }

    public byte[] generarBytesReporte()
            throws ConfiguracionJasperException {
        JRExporter exporter = prepararReporte();
        ByteArrayOutputStream exporterOS = new ByteArrayOutputStream();
        try {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exporterOS);
            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(e);
            throw new ConfiguracionJasperException("Ocurrió un error al intentar generar el reporte.", e);
        }
        return exporterOS.toByteArray();
    }

    public void generarArchivoReporte()
            throws ConfiguracionJasperException {
        if ((this.nombreReporte == null) || (this.nombreReporte.trim().equals(""))) {
            throw new ConfiguracionJasperException("No se ha definido un nombre para el reporte.");
        }
        JRExporter exporter = prepararReporte();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, this.rutaReporte + this.nombreReporte + "." + this.formatoReporte);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(e);
            throw new ConfiguracionJasperException("Ocurrió un error al intentar generar el reporte.", e);
        }
    }

    public void setReporteJasper(String reporteJasper) {
        this.reporteJasper = reporteJasper;
    }

    public void setReporteJasper(InputStream reporteJasperIS) {
        this.reporteJasperIS = reporteJasperIS;
    }

    public void setDatosReporte(List<?> datosReporte) {
        this.datosReporte = datosReporte;
    }

    public void setParametrosReporte(Map<String, Object> parametrosReporte) {
        this.parametrosReporte = parametrosReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public void setFormatoReporte(String formatoReporte) {
        this.formatoReporte = formatoReporte;
    }

    private JRExporter prepararReporte() throws ConfiguracionJasperException {
        if (((this.formatoReporte == null)) || (this.formatoReporte.trim().equals(""))) {
            throw new ConfiguracionJasperException("No se ha definido un formato para el reporte.");
        }
        if (this.reporteJasperIS == null) {
            reporteJasperIS = leerReporteJasper();
        }

        JRDataSource datosReporteJ = this.datosReporte == null ? new JREmptyDataSource() : new JRBeanCollectionDataSource(this.datosReporte);
        try {
            this.jasperPrint = JasperFillManager.fillReport(reporteJasperIS, this.parametrosReporte, datosReporteJ);
        } catch (JRException e) {
            LOG.error(e.toString());
            throw new ConfiguracionJasperException("Ocurrió un error al intentar generar el reporte.", e);
        }

        TipoReportesJasperEnum tipoRep = obtenerTipoReporte(formatoReporte);
        
        return evalualTipoReporte(tipoRep);
    }

    private JRExporter evalualTipoReporte(TipoReportesJasperEnum tipoRep) throws ConfiguracionJasperException {
        JRExporter exporter = null;

        switch (tipoRep.getId()) {
            case CASE_PDF:
                exporter = new JRPdfExporter();
                break;
            case CASE_XLS:
                exporter = new JRXlsExporter();
                break;
            case CASE_XLSX:
                exporter = new JRXlsxExporter();
                break;
            case CASE_DOCX:
                exporter = new JRDocxExporter();
                break;
            case CASE_PPTX:
                exporter = new JRPptxExporter();
                break;
            case CASE_ODS:
                exporter = new JROdsExporter();
                break;
            case CASE_ODT:
                exporter = new JROdtExporter();
                break;
            case CASE_CSV:
                exporter = new JRCsvExporter();
                break;
            case CASE_RTF:
                exporter = new JRRtfExporter();
                break;
            case CASE_TXT:
                exporter = new JRTextExporter();
                break;
            case CASE_HTML:
                exporter = new JRHtmlExporter();
                break;
            case CASE_XML:
                exporter = new JRXmlExporter();
                break;
            default:
                throw new ConfiguracionJasperException("El formato para el reporte no es válido.");

        }

        return exporter;
    }

    private TipoReportesJasperEnum obtenerTipoReporte(String tipoReporte) throws ConfiguracionJasperException {

        for (TipoReportesJasperEnum tipo : TipoReportesJasperEnum.values()) {
            if (tipoReporte.equals(tipo.getDescripcion())) {
                return tipo;
            }
        }

        throw new ConfiguracionJasperException("El formato para el reporte no es válido.");
    }

    private InputStream leerReporteJasper() throws ConfiguracionJasperException {
        if ((this.reporteJasper == null) || (this.reporteJasper.trim().equals(""))) {
            throw new ConfiguracionJasperException("No se ha definido una ruta para leer el archivo jasper.");
        }
        InputStream reporteJasperISTmp = null;
        String reporteJasperTmp = this.reporteJasper.trim();
        if (reporteJasperTmp.startsWith("classpath:")) {
            reporteJasperTmp = reporteJasperTmp.replace("classpath:", "").trim();
            reporteJasperISTmp = ReporteJasperUtil.class.getResourceAsStream(reporteJasperTmp);
            if (reporteJasperISTmp == null) {
                throw new ConfiguracionJasperException("La ruta del archivo jasper no es una ruta válida.");
            }
            this.rutaReporte = ReporteJasperUtil.class.getResource(reporteJasperTmp).getFile();
        } else {
            try {
                File reporteJasperF = new File(reporteJasperTmp);
                reporteJasperISTmp = new FileInputStream(reporteJasperF);
                this.rutaReporte = reporteJasperF.getPath();
            } catch (FileNotFoundException e) {
                LOG.error(e);
                throw new ConfiguracionJasperException("La ruta del archivo jasper no es una ruta válida.", e);
            } catch (SecurityException e) {
                LOG.error(e);
                throw new ConfiguracionJasperException("No se puede leer el archivo jasper.", e);
            }
        }
        this.rutaReporte = ("/" + this.rutaReporte).replace("\\\\", "/");
        this.rutaReporte = this.rutaReporte.substring(0, this.rutaReporte.lastIndexOf("/") + 1);
        agregarParametro("RUTA_REPORTE", this.rutaReporte);
        return reporteJasperISTmp;
    }
}
