/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.BusquedasDao;
import mx.gob.sat.mat.tabacos.modelo.dto.DatosBusqueda;
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;

import mx.gob.sat.mat.tabacos.modelo.exceptions.BusquedasDaoException;
import mx.gob.sat.mat.tabacos.negocio.BusquedasService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MMMF
 */
@Service
@Qualifier("genericoService")
public class BusquedasServiceImpl implements BusquedasService {

    protected static final Logger LOGGER = Logger.getLogger(BusquedasServiceImpl.class);

    @Autowired
    @Qualifier("genericoDaoImpl")
    private BusquedasDao busquedasDao;
    
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS)
    @Override
    public List<Historico> listarHistoricosContribuyente() throws BusinessException {
        try {
            return busquedasDao.listarHistoricosContribuyente();
        } catch (BusquedasDaoException e) {
            throw new BusinessException(e);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS)
    @Override
    public List<Historico> listarHistoricosContribuyente(String rfc) throws BusinessException {
        try {
            return busquedasDao.listarHistoricosContribuyente(rfc);
        } catch (BusquedasDaoException e) {
            throw new BusinessException(e);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS)
    @Override
    public List<DatosBusqueda> busquedaPorFiltros(DatosBusqueda filtro) throws BusinessException {
        List<DatosBusqueda> result = null;

        try {
            if (filtro != null) {
                result = busquedasDao.busquedaPorFiltros(filtro);
                int registros = result != null ? result.size() : 0;
                LOGGER.info("Resultado de la busqueda: " + registros + " registro ");
            }
        } catch (BusquedasDaoException e) {
            throw new BusinessException(e);
        }

        return result;
    }
}
