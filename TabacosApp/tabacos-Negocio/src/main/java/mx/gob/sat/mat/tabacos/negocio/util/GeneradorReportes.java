/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.tabacos.constants.Constantes;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ConfiguracionJasperException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public final class GeneradorReportes {

    /**
     * String de error para formato no soportado.
     */
    public static final String ERROR_TIPO_REPORTE_NO_SOPORTADO = "Tipo de reporte no soportado (pdf/xls) ";

    private GeneradorReportes() {

    }

    /**
     * M&eacute;todo generico para crear reportes.
     *
     * @param reportIS
     * @param nombreReporte nombre del reporte con todo y su extenci&oacute;n
     * (.xls o .pdf). Ej. miReporte.pdf
     * @param parametros Mapa con los parametros que estaran en el reporte.
     * @param detalle Lista de mapas para insertar en la banda detail del
     * reporte.
     * @return arreglo de bytes del reporte generado.
     * @throws ConfiguracionJasperException en caso de haber un problema al
     * generar el reporte.
     */
    public static byte[] crearReporte(InputStream reportIS, String nombreReporte,
            Map<String, Object> parametros,
            List<?> detalle) throws ConfiguracionJasperException {

        ReporteJasperUtil reporte = new ReporteJasperUtil();
        reporte.setReporteJasper(reportIS);
        reporte.setNombreReporte(nombreReporte);

        if (nombreReporte.endsWith(Constantes.EXCEL_ANTES_2007)) {
            reporte.setFormatoReporte(ReporteJasperUtil.XLS);
        } else if (nombreReporte.endsWith(Constantes.ARCHIVO_PDF)) {
            reporte.setFormatoReporte(ReporteJasperUtil.PDF);
        } else if (nombreReporte.endsWith(Constantes.ARCHIVO_CSV)) {
            reporte.setFormatoReporte(ReporteJasperUtil.CSV);
        } else {
            throw new ConfiguracionJasperException(GeneradorReportes.ERROR_TIPO_REPORTE_NO_SOPORTADO);
        }

        reporte.setParametrosReporte(parametros);
        reporte.setDatosReporte(detalle);
        try {
            return reporte.generarBytesReporte();
        } catch (ConfiguracionJasperException cex) {
            if(cex.getMessage()!=null){
                throw new ConfiguracionJasperException(cex.getMessage(),cex);
            }else{
                throw new ConfiguracionJasperException("No se pudo generar el reporte",cex);
            }
        }
    }
}
