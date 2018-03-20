/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public enum ReportesTabacosEnum {

    REPORT_TEST("reports/ConsultaDocumentosReport.jasper"),
    REPORT_CODIGOS("reports/ReporteCodigos.jasper"),
    REPORT_CONTRIBUYENTES("reports/ReporteContribuyentes.jasper"),
    REPORT_DESPEDICIOS("reports/ReporteDesperdicios.jasper"),
    REPORT_HISTORICOS("reports/ReporteHistoricos.jasper"),
    REPORT_ACUSECOD("reports/AcuseCodigosFalsos.jasper"),
    REPORT_MARCAS("reports/ReporteMarcas.jasper"),
    REPORT_PROVEEDORES("reports/ReporteProveedores.jasper"),
    REPORT_REGISTROS("reports/ReporteRegistros.jasper"),
    REPORT_ACUSE_AUTSOL("reports/AcuseAutSol.jasper"),
    REPORT_ACUSE_AMARCA("reports/AcuseMarca.jasper"),
    REPORT_ACUSE_BAJA_AMARCA("reports/AcuseBajaMarca.jasper"),
    REPORT_ACUSE_CAMBIO_AMARCA("reports/AcuseCambioMarca.jasper"),
    REPORT_ACUSE_SOLICITUD("reports/AcuseReciboTabacos.jasper"),
    REPORT_ACUSE_DESCARGA("reports/AcuseDescargaTabacos.jasper"),
    REPORT_ACUSE_AUTMARCA("reports/AcuseAutMarca.jasper"),
    REPORTE_ACUSE_ALTA_MARCA("reports/AcuseAltaMarca.jasper"),
    REPORTE_ACUSE_RECIBO("reports/AcuseReciboTabacos.jasper"),
    REPORTE_ACUSE_PRODUCCION_CIGARROS("reports/AcuseProduccionCigarros.jasper"),
    REPORTE_ACUSE_DESPERDICIO_DESTRUCCION("reports/AcuseDesperdicioDestruccion.jasper"),
    REPORTE_ACUSE_ALTA_PROVEEDOR("reports/AcuseAltaProveedor.jasper"),
    REPORTE_ACUSE_BAJA_PROVEEDOR("reports/AcuseBajaProveedor.jasper"),
    REPORTE_ACUSE_CAMBIO_PROVEEDOR("reports/AcuseCambioProveedor.jasper"),
    REPORTE_DESPERDICIOS("reports/ReporteDesperdiciosDestruccion.jasper"),
    REPORTE_PRODUCCION("reports/ReporteProduccion.jasper");
    
    private final String path;

    ReportesTabacosEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
