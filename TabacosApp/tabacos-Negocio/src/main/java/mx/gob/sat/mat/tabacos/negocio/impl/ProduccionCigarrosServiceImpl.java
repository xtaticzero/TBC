package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.ProduccionCigarrosDao;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteDesperdiciosDTO;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReporteProduccion;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoRetro;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProduccionDaoException;
import mx.gob.sat.mat.tabacos.negocio.ProduccionCigarrosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException;
import org.apache.log4j.Logger;
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
@Qualifier("produccionServiceImpl")
public class ProduccionCigarrosServiceImpl implements ProduccionCigarrosService {

    private static final Logger LOGGER = Logger.getLogger(ProduccionCigarrosServiceImpl.class);

    @Autowired
    @Qualifier("produccionDaoImpl")
    private ProduccionCigarrosDao produccionDaoImpl;
    
    /**
     *
     * @param idTabacalera
     * @return
     * @throws ProduccionServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<Planta> selectedPlantas(Long idTabacalera) throws ProduccionServiceException {
        try {
            return produccionDaoImpl.selectedPlantas(idTabacalera);
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al consultar las Plantas" + e.getMessage(), e);
            throw new ProduccionServiceException(e);
        }
    }

    /**
     *
     * @return @throws ProduccionServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<TipoRetro> selectedTipoRetro() throws ProduccionServiceException {
        try {
            return produccionDaoImpl.selectedTipoRetro();
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al consultar el Tipo Retro" + e.getMessage(), e);
            throw new ProduccionServiceException(e);
        }

    }

    /**
     *
     * @param produccion
     * @return produccionDaoImpl.guardaProduccion(produccion);
     * @throws ProduccionServiceException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public ProduccionCigarros guardaProduccion(ProduccionCigarros produccion) throws ProduccionServiceException {
        try {
            return produccionDaoImpl.guardaProduccion(produccion);
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al insertar los datos de Produccion de cigarros " + e.getMessage(), e);
            throw new ProduccionServiceException(e);
        }
    }

    /**
     *
     * @param produccion
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ProduccionServiceException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public ProduccionCigarros guardaDestruccion(ProduccionCigarros produccion) throws ProduccionServiceException {
        try {
            return produccionDaoImpl.guardarDestruccion(produccion);
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al insertar los datos de Desperdicios - Destruccion " + e.getMessage(), e);
            throw new ProduccionServiceException(e);
        }
    }

    /**
     *
     * @param reporteDesperdicios
     * @return List<ReporteDesperdicios>
     * @throws ProduccionServiceException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<ReporteDesperdiciosDTO> consultaReporteDesperdicios(ReporteDesperdiciosDTO reporteDesperdicios) throws ProduccionServiceException {
        try {            
            boolean rfcExiste = reporteDesperdicios.getRfc() != null;
            
            return produccionDaoImpl.consultaReporteDesperdicios(reporteDesperdicios, rfcExiste);
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al consultar la informacion del reporte de Desperdicios y Destruccion" + e.getMessage(), e);
            throw new ProduccionServiceException(e);
        }
    }
    
    /**
     * 
     * @param reporteProduccion
     * @return List<ReporteProduccion>
     * @throws ProduccionServiceException 
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = {Exception.class})
    public List<ReporteProduccion> consultaReporteProduccion(ReporteProduccion reporteProduccion) throws ProduccionServiceException {
         try {
            return produccionDaoImpl.consultaReporteProduccion(reporteProduccion);
        } catch (ProduccionDaoException e) {
            LOGGER.error("ERROR - Al consultar la informacion del reporte de Produccion : ", e);
            throw new ProduccionServiceException(e);
        }
    }
}
