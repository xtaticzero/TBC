/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.mat.test.negocio;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorAccesoService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorRangosService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/**/tabacos-negocio-test.xml", "classpath*:/**/tabacos-daos-test.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerTBC", defaultRollback = true)
public class TbcNegocioTest {

    @Autowired
    private ReporterService reporterService;

    @Autowired
    @Qualifier("validadorAccesoServiceImpl")
    private ValidadorAccesoService ValidadorAccesoService;
    
    @Autowired
    @Qualifier("validadorRangosService")
    private ValidadorRangosService validadorRangosService;

    @Test
    //@Ignore
    public void pruebaAcuse() throws Exception {
        try {
            assert validadorRangosService!=null;
            List<Object> detalle = new ArrayList<Object>();
            byte[] bytesFile;
            Map parameters = new HashMap();
            FileOutputStream fos;

            parameters.put("rfc", "EAGE8581");
            parameters.put("folio", "S0000032");
            parameters.put("fecha", new Date());
            parameters.put("cantsol", 25L);

            if (reporterService != null) {
                System.out.println("Se inyecto el servicio de reportes correctamente... ");
                //###########--------------------Genera Acuse---------################################
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_SOLICITUD, ARCHIVO_PDF, parameters, null);
                fos = new FileOutputStream("/siat/tmp/file_acuse_test.pdf");
                fos.write(bytesFile);
                fos.close();
            }
        } catch (Exception ex) {
            System.err.println("Error al obtener el recurso: " + ex.getCause().getMessage());
        }
    }
}
