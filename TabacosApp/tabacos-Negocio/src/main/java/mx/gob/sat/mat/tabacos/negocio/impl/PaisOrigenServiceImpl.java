package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
import mx.gob.sat.mat.tabacos.negocio.PaisOrigenService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Service
@Qualifier ("paisOrigenServiceImpl")
public class PaisOrigenServiceImpl implements PaisOrigenService{

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PaisOrigenServiceImpl.class);

    @Autowired
    @Qualifier("paisOrigenDaoImpl")
    private PaisOrigenDao paisOrigenDaoImpl;

    /**
     * 
     * @return List<PaisOrigen>
     * @throws ServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<PaisOrigen> selectedPais() throws ServiceException {
        try {
            return paisOrigenDaoImpl.selectedPais();
        } catch (PaisDaoException e) {
            LOGGER.error("ERROR - Al consultar los Paises" + e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @return List<PaisOrigen>
     * @throws ServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<PaisOrigen> selectedOrigen() throws ServiceException {
        try {
            return  paisOrigenDaoImpl.selectedOrigen();
        } catch (PaisDaoException e) {
            LOGGER.error("ERROR - Al consultar los Origenes" + e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
