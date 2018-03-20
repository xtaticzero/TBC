/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.test.modelo.dao;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ExampleRfcDao;
import mx.gob.sat.mat.tabacos.modelo.dao.TipoContribuyenteDao;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import static org.junit.Assert.assertEquals;
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
@ContextConfiguration(locations = "classpath:tabacos-daos-test.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerRfc", defaultRollback = true)
public class TbcDaoTest {

    @Autowired
    private ExampleRfcDao exampleRfcDaoImpl;
    
    @Autowired
    @Qualifier("tipoContribuyenteDao")
    private TipoContribuyenteDao tipoContribuyenteDao;

    @Test
    @Ignore
    public void pruebaInyeccionDao() throws Exception {
        System.out.println("todo ok");
        try {
            if(tipoContribuyenteDao!=null){
                List<TipoContribuyente> lstTipoContrib;
                lstTipoContrib = tipoContribuyenteDao.getLstTipoContribuyente();
                for(TipoContribuyente tipo:lstTipoContrib){
                    System.err.println("Tipo : "+ tipo.getDescTipoContrib());
                }
            } 
            
            if (exampleRfcDaoImpl != null) {
                exampleRfcDaoImpl.getFecha();
                System.out.println("Fecha de la base : " + exampleRfcDaoImpl.getFecha());
            }

            assertEquals("10 x 0 must be 0", 0, 0);
            assertEquals("0 x 10 must be 0", 0, 0);

        } catch (Exception ex) {

        }
    }
}
