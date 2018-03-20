package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ProveedorDao;
import mx.gob.sat.mat.tabacos.modelo.dto.Baja;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del Servicio de Proveedor
 *
 * @author Salvador Pocteco Saldaña
 */
@Service
@Qualifier("proveedorServiceImpl")
public class ProveedorServiceImpl implements ProveedorService {

    private static final Logger LOGGER = Logger.getLogger(ProveedorServiceImpl.class);

    @Autowired
    @Qualifier("proveedorDaoImpl")
    private ProveedorDao proveedorDaoImpl;
    
    /**
     *
     * @param proveedor
     * @param representante
     * @param idTabacalera
     * @param relacionProveedor
     * @return proveedor
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Proveedor insertProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor) throws ProveedorServiceException {
        try {
            proveedorDaoImpl.insertProveedor(proveedor);
            proveedorDaoImpl.insertRepresentante(proveedor, representante, idTabacalera);
            proveedorDaoImpl.insertRelacionProveedor(proveedor, representante, idTabacalera, relacionProveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("EROR - Al insertar los datos del Proveedor - Representante - Relacional Proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
        return proveedor;
    }

    /**
     *
     * @param proveedor
     * @param representante
     * @param idTabacalera
     * @param relacionProveedor
     * @return proveedor
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Proveedor updateProveedor(Proveedor proveedor, RepresentanteLegal representante, Long idTabacalera, RelacionTabProv relacionProveedor) throws ProveedorServiceException {
        try {
            proveedorDaoImpl.updateRepresentante(proveedor, representante, idTabacalera);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al actualizar los datos del Proveedor - Representante" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
        return proveedor;
    }

    /**
     *
     * @param relacionProveedor
     * @param proveedor
     * @param idTabacalera
     * @param representante
     * @param baja
     * @param idTabacaleraTem
     * @return proveedor     
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException     
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Proveedor updateProveedorRelacional(RelacionTabProv relacionProveedor, Proveedor proveedor, Long idTabacalera, RepresentanteLegal representante, Baja baja, Long idTabacaleraTem) throws ProveedorServiceException {
        try {
            if (!idTabacaleraTem.equals(idTabacalera)) {
                proveedorDaoImpl.updateProveedorRelacionCambio(proveedor, relacionProveedor, idTabacaleraTem);
                proveedorDaoImpl.insertRelacionProveedor(proveedor, representante, idTabacalera, relacionProveedor);
                proveedorDaoImpl.updateRepresentante(proveedor, representante, idTabacalera);
            } else {
                proveedorDaoImpl.updateRepresentante(proveedor, representante, idTabacaleraTem);
            }
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al actualizar los datos del Proveedor - Ralcional Proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
        return proveedor;
    }

    /**
     *
     * @param proveedor
     * @param idTabacalera
     * @param baja
     * @param relacionProveedor
     * @return proveedor
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Proveedor deleteProveedor(Proveedor proveedor, Long idTabacalera, Baja baja, RelacionTabProv relacionProveedor) throws ProveedorServiceException {
        try {
            proveedorDaoImpl.deleteProveedor(proveedor, idTabacalera, baja);
            proveedorDaoImpl.updateProveedorBaja(proveedor);
            proveedorDaoImpl.updateProveedorRelacionBaja(proveedor, baja, relacionProveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al dar de baja los datos del Proveedor - Baja" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
        return proveedor;
    }

    /**
     *
     * @param proveedor
     * @return List<Proveedor>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Proveedor> consultaDatosProveedor(Proveedor proveedor) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaDatosProveedor(proveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los datos del Proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param representante
     * @param proveedor
     * @return List<RepresentanteLegal>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<RepresentanteLegal> consultaDatosRepresentante(RepresentanteLegal representante, Proveedor proveedor) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaDatosRepresentante(representante, proveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los datos del Representante - Proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List<Baja>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Baja> consultaDatosProveedorBaja(Proveedor proveedor) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaDatosProveedorBaja(proveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los datos del Proveedor - Baja" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return List<Marcas>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Marcas> selectedMarcas() throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.selectedMarcas();
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar las Marcas del proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List<Estatus>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Estatus> selectedEstatus(Proveedor proveedor) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.selectedEstatus(proveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los Estatus" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param proveedor
     * @return List<Proveedor>
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Proveedor> buscaRfc(Proveedor proveedor) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.buscaRfc(proveedor);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar el RFC del proveedor" + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Proveedor> recuperaProveedores(ReportesFiltroBase filtro) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.recuperaProveedores(filtro);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar proveedores: " + e.getMessage(), e);
            throw new ProveedorServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<Proveedor> buscaProvedoresLikeRfc(String rfc) throws ProveedorServiceException{
        try {
            return proveedorDaoImpl.buscaProveedorLikeRFC(rfc);
        } catch (ProveedorDaoException ex) {
            throw new ProveedorServiceException(ex);
        }
    }
    
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Proveedor getProveedorByRfc(String rfc) throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.getProveedorByRFC(rfc);
        } catch (ProveedorDaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ProveedorServiceException(e);
        }
    }
    
    @Override
    public List<Proveedor> getProveedoresActivos() throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaProveedores();
        } catch (ProveedorDaoException daoe) {
            throw  new ProveedorServiceException(daoe);
        }
    }
    
    @Override
    public List<Proveedor> getProveedoresTodos() throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaTodosLosProveedores();
        } catch (ProveedorDaoException daoe) {
            throw  new ProveedorServiceException(daoe);
        }
    }
    
    /**
     * 
     * @return List<Proveedor>
     * @throws ProveedorServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Proveedor> consultaProveedores() throws ProveedorServiceException {
        try {
            return proveedorDaoImpl.consultaProveedores();
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los Proveedores : ", e);
            throw new ProveedorServiceException(e);
        }
    }
    
    /**
     * 
     * @param rfc
     * @return List<Proveedor>
     * @throws ProveedorServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Proveedor> consultaProveedoresPorRfc(String rfc) throws ProveedorServiceException {
       try {
            return proveedorDaoImpl.consultaProveedoresPorRfc(rfc);
        } catch (ProveedorDaoException e) {
            LOGGER.error("ERROR - Al consultar los Proveedores por RFC : ", e);
            throw new ProveedorServiceException(e);
        }
    }
}
