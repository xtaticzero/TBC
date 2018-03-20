package mx.gob.sat.test.modelo.dao;

import mx.gob.sat.mat.tabacos.modelo.dao.RepresentanteLegalDao;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RepLegalDaoException;

import org.apache.log4j.Logger;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:tabacos-daos-test.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManagerRfc", defaultRollback = true)
public class RepresentanteLegalDaoTest {
    
    private static final Logger logger = Logger.getLogger(RepresentanteLegalDaoTest.class);
    
    @Autowired
    @Qualifier("representanteLegalDaoImpl")
    private RepresentanteLegalDao representanteLegalDao;
    
    @Test
    @Ignore
    public void consultarPorRfcTest(){
        try {
            logger.debug("Executing Query...");
            RepresentanteLegal representante = representanteLegalDao.consultarPorRfc("CACD830530941");
            logger.debug("Done!");
            logger.debug(representante.getApellidoMaterno());
        } catch (RepLegalDaoException ex) {
            logger.error(ex);
        }
    }
    
}
