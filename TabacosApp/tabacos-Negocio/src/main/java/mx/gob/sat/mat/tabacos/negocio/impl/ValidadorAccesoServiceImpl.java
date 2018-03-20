package mx.gob.sat.mat.tabacos.negocio.impl;

import java.util.List;

import mx.gob.sat.mat.tabacos.constants.enums.Rol;
import mx.gob.sat.mat.tabacos.modelo.dao.ProveedorDao;
import mx.gob.sat.mat.tabacos.modelo.dao.RepresentanteLegalDao;
import mx.gob.sat.mat.tabacos.modelo.dao.TabacaleraDao;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.modelo.dto.filtro.ProveedorFiltroDTO;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PersistenceException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.ProveedorDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.RepLegalDaoException;
import mx.gob.sat.mat.tabacos.modelo.exceptions.TabacaleraDaoException;
import mx.gob.sat.mat.tabacos.negocio.ValidadorAccesoService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.AccesoNoPermitidoException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("validadorAccesoServiceImpl")
public class ValidadorAccesoServiceImpl implements ValidadorAccesoService {

    private static final Logger LOG = Logger.getLogger(ValidadorAccesoServiceImpl.class);

    @Autowired
    @Qualifier("representanteLegalDaoImpl")
    private RepresentanteLegalDao representanteLegalDao;
    @Autowired
    @Qualifier("tabacaleraDaoImpl")
    private TabacaleraDao tabacaleraDao;
    @Autowired
    @Qualifier("proveedorDaoImpl")
    private ProveedorDao proveedorDao;

    @Override
    public ValidarAccesoRespuesta validarAcceso(String rfc) throws AccesoNoPermitidoException {
        try {
            ValidarAccesoRespuesta validarAccesoRespuesta = null;

            RepresentanteLegal representante = representanteLegalDao.consultarPorRfc(rfc);

            validarAccesoRespuesta = validarRepresentante(representante);

            if (validarAccesoRespuesta == null) {
                try {
                    Tabacalera tabacalera = tabacaleraDao.consultarPorRfc(rfc);
                    if (isTabacalera(tabacalera)) {
                        // Si es Tabacalera
                        // Consulta Proveedores
                        validarAccesoRespuesta = ValidarAccesoRespuesta.crearRespuestaTabacalera(
                                tabacalera,
                                proveedorDao.consultarProveedores(tabacalera.getIdTabacalera()));
                    }
                } catch (ProveedorDaoException ex) {
                    LOG.error(ex);
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
                } catch (TabacaleraDaoException ex) {
                    LOG.error(ex);
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
                }
            }

            if (validarAccesoRespuesta == null) {
                ProveedorFiltroDTO filtro = new ProveedorFiltroDTO();
                filtro.setRfc(rfc);

                validarAccesoRespuesta = validaProveedor(filtro);
            }

            if (validarAccesoRespuesta == null) {
                throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES);
            }

            return validarAccesoRespuesta;
        } catch (RepLegalDaoException ex) {
            LOG.error(ex);
            throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
        }
    }

    private ValidarAccesoRespuesta validarRepresentante(RepresentanteLegal representante) throws AccesoNoPermitidoException {
        if (representante != null) {
            Rol.parse(representante.getIdTipoRepLegal().intValue());
            if (isRepresentanteLegal(representante)) {
                throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES);
            }
            if (isRepresentanteLegalOperativo(representante)) {
                try {
                    // Si es Representante Legal Operativo:
                    //Consulta Tabacalera
                    Tabacalera tabacalera = tabacaleraDao.consultarTabacaleraRepresentada(representante);
                    List<Proveedor> lstProvedores = proveedorDao.buscaProveedorPorTabacalera(tabacalera.getRfc());

                    return ValidarAccesoRespuesta.crearRespuestaTabacalera(tabacalera, lstProvedores);
                } catch (PersistenceException pex) {
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, pex);
                } catch (ProveedorDaoException ex) {
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
                } catch (TabacaleraDaoException ex) {
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
                }
            }
        }
        return null;
    }

    private ValidarAccesoRespuesta validaProveedor(ProveedorFiltroDTO filtro) throws AccesoNoPermitidoException {
        List<Proveedor> proveedores = null;
        try {
            proveedores = proveedorDao.findRfc(filtro);
        } catch (ProveedorDaoException ex) {
            LOG.error(ex);
            throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
        }

        if (proveedores.size() > 0) {
            Proveedor proveedor = proveedores.get(0);
            if (isProveedor(proveedor)) {
                // Si es Proveedor
                // Consulta Tabacaleras
                List<Tabacalera> tabacaleras = null;
                try {
                    tabacaleras = tabacaleraDao.consultarTabacaleras(proveedor);
                } catch (TabacaleraDaoException ex) {
                    LOG.error(ex);
                    throw new AccesoNoPermitidoException(UNAUTHORIZED_ACCES, ex);
                }
                return ValidarAccesoRespuesta.crearRespuestaProveedor(proveedor, tabacaleras);
            }
        }
        return null;
    }

    private boolean isRepresentanteLegal(RepresentanteLegal representanteLegal) {
        Rol rol = Rol.parse(representanteLegal.getIdTipoRepLegal().intValue());
        return rol.equals(Rol.REPRESENTANTE_LEGAL);
    }

    private boolean isRepresentanteLegalOperativo(RepresentanteLegal representanteLegal) {
        Rol rol = Rol.parse(representanteLegal.getIdTipoRepLegal().intValue());
        return rol.equals(Rol.REPRESENTANTE_OPERATIVO);
    }

    private boolean isTabacalera(Tabacalera tabacalera) {
        LOG.debug("Tab" + tabacalera);
        return tabacalera != null;
    }

    private boolean isProveedor(Proveedor proveedor) {
        LOG.debug("Pro" + proveedor);
        return proveedor != null;
    }
}
