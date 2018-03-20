/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dao.CommonDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Repository
@Transactional
@Qualifier("commonDao")
public class CommonDaoImpl implements CommonDao {

    @Autowired(required = false)
    @Qualifier("jdbcTemplateTBC")
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(CommonDaoImpl.class);

    @Override
    public RepresentanteLegal getRepLegalByRFC(String rfc) throws DaoException {
        try {
            return (RepresentanteLegal) jdbcTemplate.queryForObject(SQL_SELECT_REPLEGAL_BY_RFC, new Object[]{rfc}, new BeanPropertyRowMapper(RepresentanteLegal.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR REPRESENTANTE LEGAL POR RFC";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Solicitud createSolicitud(Solicitud solicitud) throws DaoException {
        try {
            Long idSolicitud = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_SOLICITUD, Long.class);
            solicitud.setIdSolicitud(idSolicitud);
            jdbcTemplate.update(SQL_INSERT_SOLICITUD,
                    new Object[]{
                        solicitud.getIdSolicitud(),
                        solicitud.getCantSolicitada(),
                        solicitud.getCantAutorizada(),
                        solicitud.getFecSolicitud(),
                        solicitud.getIdTipoContrib(),
                        solicitud.getIdPaisOrigen(),
                        solicitud.getIdTbcProv()
                    });

            return solicitud;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CREAR LA SOLICITUD";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Solicitud getSolicitud(Long idSolicitud) throws DaoException {
        try {
            return (Solicitud) jdbcTemplate.queryForObject(SQL_SELECT_SOLICITUD_BY_ID, new Object[]{idSolicitud}, new BeanPropertyRowMapper(Solicitud.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CONSULTAR LA SOLICITUD";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Resolucion getResolucion(Long idResolucion) throws DaoException {
        try {
            return (Resolucion) jdbcTemplate.queryForObject(SQL_SELECT_RESOLUCION, new Object[]{idResolucion}, new BeanPropertyRowMapper(Resolucion.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA RESOLUCION";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public List<Archivo> getArchivos(Long idSolicitud) throws DaoException {
        try {
            return (List<Archivo>) jdbcTemplate.query(SQL_SELECT_ARCHIVOS, new Object[]{idSolicitud}, new BeanPropertyRowMapper(Archivo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LOS ARCHIVOS";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Acuse createAcuse(Acuse acuse) throws DaoException {
        try {
            Long folioAcuse = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_ACUSE, new Object[]{acuse.getSerieAcuse()}, Long.class);
            acuse.setFolioAcuse(folioAcuse);
            jdbcTemplate.update(SQL_INSERT_ACUSE,
                    new Object[]{
                        acuse.getSerieAcuse(),
                        acuse.getIdSolicitud(),
                        acuse.getIdProveedor(),
                        acuse.getIdMarca(),
                        acuse.getSelloDigital(),
                        acuse.getCadenaOriginal(),
                        acuse.getFecCaptura(),
                        acuse.getFolioAcuse()
                    });

            return acuse;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL CREAR EL ACUSE";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Resolucion createResolucion(Resolucion resolucion) throws DaoException {
        try {
            Long idResolucion = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_RESOLUCION, Long.class);
            resolucion.setIdResolucion(idResolucion);
            jdbcTemplate.update(SQL_INSERT_RESOLUCION,
                    new Object[]{
                        resolucion.getIdResolucion(),
                        resolucion.getIdSolicitud(),
                        resolucion.getIdEstResolucion(),
                        resolucion.getFecResolucion(),
                        resolucion.getNumServidorPublico(),
                        resolucion.getFolioInicial(),
                        resolucion.getFolioFinal(),
                        resolucion.getFechaInicio(),
                        resolucion.getFechaFin(),
                        resolucion.getIdEstCargador()
                    });

            return resolucion;
        } catch (Exception e) {
            String msgError = "ERROR AL CREAR LA RESOLUCION";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public ProduccionCigarros createProduccionCigarro(ProduccionCigarros produccionCigarro) throws DaoException {
        try {
            Long idProdCigarro = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_PRODCIGARRO, Long.class);
            produccionCigarro.setIdProdCigarro(idProdCigarro);
            jdbcTemplate.update(SQL_INSERT_PRODCIGARRO,
                    new Object[]{
                        produccionCigarro.getIdProdCigarro(),
                        produccionCigarro.getIdMarca(),
                        produccionCigarro.getDescMaquinaProd(),
                        produccionCigarro.getNumLote(),
                        produccionCigarro.getFechImportacion(),
                        produccionCigarro.getFechProduccion(),
                        produccionCigarro.getIdPaisOrigen(),
                        produccionCigarro.getDescPaisOrigen(),
                        produccionCigarro.getCantidadCigarros(),
                        produccionCigarro.getCantidadProd(),
                        produccionCigarro.getLineaProd(),
                        produccionCigarro.getIdPlantaProd(),
                        produccionCigarro.getIdTipoRetro()
                    });

            return produccionCigarro;
        } catch (Exception e) {
            String msgError = "ERROR AL CREAR LA PRODUCCION CIGARRO";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public Planta getPlanta(Long idPlanta) throws DaoException {
        try {
            return (Planta) jdbcTemplate.queryForObject(SQL_SELECT_RESOLUCION, new Object[]{idPlanta}, new BeanPropertyRowMapper(Planta.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL OBTENER LA PLANTA";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public RelacionTabProv getRelacionTabProv(String rfcProveedor, String rfcTabacalera) throws DaoException {
        try {
            return (RelacionTabProv) jdbcTemplate.queryForObject(SQL_SELECT_RELACION_TAB_PROV, new Object[]{rfcProveedor, rfcTabacalera}, new BeanPropertyRowMapper(RelacionTabProv.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String msgError = "ERROR AL RELACION PROVEEDOR-TABACALERA";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }

    @Override
    public CodigoInvalido createCodigoInvalido(CodigoInvalido codigoInvalido) throws DaoException {
        try {
            Long idCodInvalido = jdbcTemplate.queryForObject(SQL_SELECT_NEXTVAL_CODIGOINVALIDO, Long.class);
            codigoInvalido.setIdCodInvalido(idCodInvalido);
            jdbcTemplate.update(SQL_INSERT_CODIGOINVALIDO,
                    new Object[]{
                        codigoInvalido.getIdCodInvalido(),
                        codigoInvalido.getIdRangoFolio(),
                        codigoInvalido.getFolioInicial(),
                        codigoInvalido.getFolioFinal(),
                        codigoInvalido.getFecRegistro(),
                        codigoInvalido.getFecCaptura(),
                        codigoInvalido.getJustificacion()
                    });

            return codigoInvalido;
        } catch (Exception e) {
            String msgError = "ERROR AL CREAR CODIGO INVALIDO";
            LOGGER.error(msgError, e);
            throw new DaoException(msgError, e);
        }
    }
    
}
