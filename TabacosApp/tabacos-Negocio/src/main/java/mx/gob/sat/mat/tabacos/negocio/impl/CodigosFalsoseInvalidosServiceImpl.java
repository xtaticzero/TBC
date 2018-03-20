/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoDao;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoFalsoDao;
import mx.gob.sat.mat.tabacos.modelo.dao.CodigoInvalidoDao;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.negocio.CodigosFalsoseInvalidosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CodigosFalsoseInvalidosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author MMMF
 */
@Service
@Qualifier("codigosFalsosInvalidosService")
public class CodigosFalsoseInvalidosServiceImpl implements CodigosFalsoseInvalidosService {

    @Autowired
    @Qualifier("codigoInvalidoDaoImpl")
    private CodigoInvalidoDao codigoInvalidoDao;

    @Autowired
    @Qualifier("codigoFalsoDaoImpl")
    private CodigoFalsoDao codigoFalsoDao;
    
    @Autowired
    @Qualifier("codigoDaoImpl")
    private CodigoDao codigoDao;

    @Override
    public int guardarCodigoFalso(Codigo codigo) throws CodigosFalsoseInvalidosException {
        try {
            return codigoFalsoDao.guardar(codigo);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }

    @Override
    public int guardarCodigoInvalido(Codigo codigo) throws CodigosFalsoseInvalidosException {
        try {
            return codigoInvalidoDao.guardar(codigo);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }
    
    
    @Override
    public Codigo buscarPorId(String numCodigo) throws CodigosFalsoseInvalidosException {
        try {
            return codigoDao.buscarPorID(numCodigo);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }
    
    @Override
    public List<Codigo> buscarCodigoInvPorRangoFolio(String rfc, Long folioInicial, Long folioFinal) throws CodigosFalsoseInvalidosException {
        try {
            return codigoInvalidoDao.buscarCodigoInvPorRangoFolio(rfc, folioInicial, folioFinal);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }
    
    @Override
    public List<Codigo> getCodigosInvalidos(ReportesFiltroBase filtro) throws CodigosFalsoseInvalidosException {
        try {
            return codigoInvalidoDao.getCodigosInvalidos(filtro);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }
    
    @Override
    public List<Codigo> getCodigosFalsos(ReportesFiltroBase filtro) throws CodigosFalsoseInvalidosException {
        try {
            return codigoFalsoDao.getCodigosFalsos(filtro);
        } catch (DaoException e) {
            throw new CodigosFalsoseInvalidosException(e);
        }
    }


    public CodigoInvalidoDao getCodigoInvalidoDao() {
        return codigoInvalidoDao;
    }

    public void setCodigoInvalidoDao(CodigoInvalidoDao codigoInvalidoDao) {
        this.codigoInvalidoDao = codigoInvalidoDao;
    }

    public CodigoFalsoDao getCodigoFalsoDao() {
        return codigoFalsoDao;
    }

    public void setCodigoFalsoDao(CodigoFalsoDao codigoFalsoDao) {
        this.codigoFalsoDao = codigoFalsoDao;
    }
    
    public CodigoDao getCodigoDao() {
        return codigoDao;
    }

    public void setCodigoDao(CodigoDao codigoDao) {
        this.codigoDao = codigoDao;
    }
}
