/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ResolucionDao;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.negocio.ResolucionService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MMMF
 */
@Service
@Qualifier("resolucionService")
public class ResolucionServiceImpl implements ResolucionService {

    @Autowired
    @Qualifier("resolucionDaoImpl")
    private ResolucionDao resolucionDao;
    
    
    public List<Resolucion> buscarPorFoliosLimites(Long folioInicia, Long folioFinal, Long idSolicitud) throws BusinessException {
        List <Resolucion> resoluciones = null;
        try {
            resoluciones = resolucionDao.buscarPorFoliosLimites(folioInicia, folioFinal, idSolicitud);
            
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
        return resoluciones;

    }

    public ResolucionDao getResolucionDao() {
        return resolucionDao;
    }

    public void setResolucionDao(ResolucionDao resolucionDao) {
        this.resolucionDao = resolucionDao;
    }
}
