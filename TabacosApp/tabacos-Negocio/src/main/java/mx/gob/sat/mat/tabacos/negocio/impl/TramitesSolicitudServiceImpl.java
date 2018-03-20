/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.PaisOrigenDao;
import mx.gob.sat.mat.tabacos.modelo.dao.TipoContribuyenteDao;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PaisDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TipoContribuyenteDaoException;
import mx.gob.sat.mat.tabacos.negocio.TramitesSolicitudService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Service
@Qualifier("tramitesSolicitudService")
public class TramitesSolicitudServiceImpl implements TramitesSolicitudService{
    
    @Autowired
    @Qualifier("paisDaoImpl")
    private PaisOrigenDao paisDaoImpl;
    
    @Autowired
    @Qualifier("tipoContribuyenteDao")
    private TipoContribuyenteDao tipoContribuyenteDao;

    public List<PaisOrigen> getLstOrigen() throws ServiceException {
        try {
            return paisDaoImpl.selectedOrigen();
        } catch (PaisDaoException pe) {
            throw new ServiceException(pe);
        }
    }

    /**
     *
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TipoContribuyente> getLstTipoContribuyente() throws ServiceException {
        try {
            return tipoContribuyenteDao.getLstTipoContribuyente();
        } catch (TipoContribuyenteDaoException pe) {
            throw new ServiceException(pe);
        }
    }
}
