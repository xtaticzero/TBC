/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.mat.test.negocio;

import java.io.FileOutputStream;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;
import mx.gob.sat.mat.tabacos.negocio.MarcaReporteService;
import mx.gob.sat.mat.tabacos.negocio.ReporteService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author platon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/**/tabacos-negocio-test.xml",
    "classpath*:/**/tabacos-daos-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerTBC",
        defaultRollback = true)
public class TbcNegocioReportesPdfTest {

    private static int reportePDF = 1;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private MarcaReporteService marcareporteService;

    @Test
    @Ignore
    public void pruebaReporteContribuyentePDF() throws Exception {
        FileOutputStream fos;
        byte[] reporte = null;

        ReportesFiltroBase filtro = getFiltro();
        filtro.setRfc("ROMA720627B82");
        filtro.setFechaInicio(new Date(110, 3, 20));
        filtro.setFechaFin(new Date(110, 3, 28));

        try {
            reporte = reporteService.makeReportContriyentes(
                    ReportesTabacosEnum.REPORT_CONTRIBUYENTES,
                    filtro, reportePDF);
            fos = new FileOutputStream("/siat/tmp/Contribuyente_test.pdf");
            fos.write(reporte);
            fos.close();
        } catch (Exception ex) {
            System.err.println("Error al obtener el recurso: "
                    + ex.getCause().getMessage());
        }
    }

    @Test
    @Ignore
    public void pruebaReporteMarcaPDF() throws Exception {
        FileOutputStream fos;
        byte[] reporte = null;

        ReportesFiltroBase filtro = getFiltro();
        filtro.setRfc("ROMA720627B82");
        filtro.setFechaInicio(null);
        filtro.setFechaFin(null);

        try {
            reporte = marcareporteService.makeReportMarcas(
                    ReportesTabacosEnum.REPORT_MARCAS,
                    filtro, reportePDF);
            fos = new FileOutputStream("/siat/tmp/Marcas_test.pdf");
            fos.write(reporte);
            fos.close();
        } catch (Exception ex) {
            System.err.println("Error al obtener el recurso: "
                    + ex.getCause().getMessage());
        }
    }

    @Test
    @Ignore
    public void pruebaReporteRegistrosPDF() throws Exception {
        FileOutputStream fos;
        byte[] reporte = null;

        ReportesFiltroBase filtro = getFiltro();
        filtro.setRfc("ROMA720627B82");
        filtro.setFechaInicio(null);
        filtro.setFechaFin(null);

        try {
            reporte = reporteService.makeReportRegistros(
                    ReportesTabacosEnum.REPORT_REGISTROS,
                    getComplejos(), reportePDF);
            fos = new FileOutputStream("/siat/tmp/RegistroProduccion_test.pdf");
            fos.write(reporte);
            fos.close();
        } catch (Exception ex) {
            System.err.println("Error al obtener el recurso: "
                    + ex.getCause().getMessage());
        }
    }

    @Test
    @Ignore
    public void pruebaReporteDesperdiciosPDF() throws Exception {
        FileOutputStream fos;
        byte[] reporte = null;

        ReportesFiltroBase filtro = getFiltro();
        filtro.setRfc("ROMA720627B82");
        filtro.setFechaInicio(null);
        filtro.setFechaFin(null);

        try {
            reporte = reporteService.makeReportDesperdicios(
                    ReportesTabacosEnum.REPORT_DESPEDICIOS,
                    getComplejos(), reportePDF);
            fos = new FileOutputStream("/siat/tmp/Desperdicios_test.pdf");
            fos.write(reporte);
            fos.close();
        } catch (Exception ex) {
            System.err.println("Error al obtener el recurso: "
                    + ex.getCause().getMessage());
        }
    }

    public ReportesFiltroBase getFiltro() {
        int Movimiento = 3;
        int Codigo = 1;
        ReportesFiltroBase retorno = new ReportesFiltroBase();

        retorno.setRfc("ROMA720627B82");
        //retorno.setFechaInicio(new Date(115,3,20));
        //retorno.setFechaFin(new Date(115,3,25));
        retorno.setFechaInicio(null);
        retorno.setFechaFin(null);
        retorno.setMarca("Apple");
        retorno.setMovimiento(Movimiento);
        retorno.setTipoCodigo(Codigo);
        return retorno;
    }

    public ReportesRegistrosYDespedicios getComplejos() {
        ReportesRegistrosYDespedicios retorno
                = new ReportesRegistrosYDespedicios(getFiltro());

        retorno.setCantidadDestruccion(1);
        retorno.setCantidadProduccion(1);
        retorno.setContribuyenteCB("");
        retorno.setFechaYHora(1);
        retorno.setLoteProduccion(1);
        retorno.setMaquinariaProduccion(1);
        retorno.setNumeroDeRegistro(1);
        retorno.setNumeroProduccion(1);
        retorno.setOrigen(1);
        retorno.setPaisProducto(1);
        retorno.setPlantaProduccion(1);
        retorno.setProducto(1);
        retorno.setProveedor(1);

        return retorno;
    }
}
