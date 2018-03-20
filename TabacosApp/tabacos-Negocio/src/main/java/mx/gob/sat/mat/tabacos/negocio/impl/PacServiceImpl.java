/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusResolucion;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.constants.enums.TipoRetroEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.PacDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.AcuseTransaccion;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.CodigoInvalido;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Resolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.Solicitud;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.modelo.exceptions.PacDaoException;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.PacService;
import mx.gob.sat.mat.tabacos.negocio.RetroCodigosService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorAccesoService;
import mx.gob.sat.mat.tabacos.negocio.ValidadorRangosService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.AccesoNoPermitidoException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RangosException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.PacServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.RetroCodigosException;
import mx.gob.sat.mat.tabacos.negocio.util.DownloadFileUtil;
import mx.gob.sat.mat.tabacos.ws.dto.Acuse;
import mx.gob.sat.mat.tabacos.ws.dto.AcuseRetro;
import mx.gob.sat.mat.tabacos.ws.dto.Folio;
import mx.gob.sat.mat.tabacos.ws.dto.ObjectFactory;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuRboSlc;
import mx.gob.sat.mat.tabacos.ws.dto.TBCAcuseRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespConsulFolio;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRespDescarga;
import mx.gob.sat.mat.tabacos.ws.dto.TBCRetroInfo;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSlcCodSeg;
import mx.gob.sat.mat.tabacos.ws.dto.TBCSolDescarga;
import mx.gob.sat.mat.tabacos.ws.dto.Version;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sat.mat.tabacos.negocio.util.web.Utilerias;
import mx.gob.sat.mat.tabacos.ws.dto.Archivos;
import mx.gob.sat.mat.tabacos.ws.dto.CodigosNoValidos;
import mx.gob.sat.mat.tabacos.ws.dto.Destruccion;
import mx.gob.sat.mat.tabacos.ws.dto.Produccion;
import mx.gob.sat.mat.tabacos.ws.dto.RangoCodigosSeguridad;

/**
 *
 * @author root
 */
@Service
@Qualifier("pacService")
public class PacServiceImpl implements PacService {

    @Autowired
    private PacDao pacDao;

    @Autowired
    private ValidadorAccesoService accesoService;

    @Autowired
    private ValidadorRangosService rangosService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RetroCodigosService codigosService;

    private static final Logger LOGGER = Logger.getLogger(PacServiceImpl.class);
    private final ObjectFactory factory = new ObjectFactory();

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public TBCAcuRboSlc solicitarCodigos(TBCSlcCodSeg tbcSlcCodSeg) throws PacServiceException {
        try {
            validaAcceso(tbcSlcCodSeg.getRFCProveedorCertificado(), tbcSlcCodSeg.getSolicitud().getRFC());

            TBCAcuRboSlc acuRboSlc = factory.createTBCAcuRboSlc();
            acuRboSlc.setVersion(Version.UNO);

            AcuseTransaccion at = crearSolicitud(tbcSlcCodSeg);
            Acuse acuse = factory.createTBCAcuRboSlcAcuse();
            acuse.setCantidadCodigos(tbcSlcCodSeg.getSolicitud().getCantidadCodigos());
            acuse.setFolio(at.getFolioTransaccion());
            acuse.setFolioAcuse(at.getFolioAcuse());
            acuse.setRFC(tbcSlcCodSeg.getSolicitud().getRFC());

            acuRboSlc.setAcuse(acuse);
            acuRboSlc.setFecha(Utilerias.dateToXMLGCalendar(at.getFechaTransaccion()));

            return acuRboSlc;
        } catch (AccesoNoPermitidoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new PacServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public TBCRespConsulFolio consultarEstatusSolicitud(TBCConsulFolio tbcConsulFolio) throws PacServiceException {
        try {
            validaAcceso(tbcConsulFolio.getRFCProveedorCertificado(), tbcConsulFolio.getFolios().getRFC());

            TBCRespConsulFolio consulFolio = factory.createTBCRespConsulFolio();
            Long idSolicitud = Long.valueOf(tbcConsulFolio.getFolios().getFolio());
            Resolucion resolucion = pacDao.getResolucion(idSolicitud, tbcConsulFolio.getFolios().getRFC(), tbcConsulFolio.getRFCProveedorCertificado());
            if (resolucion == null) {
                throw new PacServiceException("FOLIO INVALIDO");
            }
            consulFolio.setVersion(Version.UNO);
            consulFolio.setRFCProveedorCertificado(tbcConsulFolio.getRFCProveedorCertificado());

            Folio folio = factory.createTBCRespConsulFolioFolio();
            folio.setEstado(EstatusResolucion.parse(resolucion.getIdEstResolucion().intValue()).getValue());
            folio.setFolio(tbcConsulFolio.getFolios().getFolio());
            folio.setRFC(tbcConsulFolio.getFolios().getRFC());

            List<Archivo> lstArchivos = commonService.getArchivos(idSolicitud);
            if (lstArchivos != null && !lstArchivos.isEmpty()) {
                List<Archivos> lstFiles = parseaListaArchivos(lstArchivos);
                folio.getArchivos().addAll(lstFiles);
            }
            consulFolio.setFolio(folio);

            return consulFolio;
        } catch (AccesoNoPermitidoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new PacServiceException(e.getMessage(), e);
        } catch (PacDaoException e) {
            String msgError = "ERROR AL CONSULTAR ESTATUS DE LA SOLICITUD: ";
            LOGGER.error(msgError + e.getMessage(), e);
            throw new PacServiceException(msgError, e);
        } catch (CommonServiceException ex) {
            String msgError = "ERROR AL CONSULTAR ARCHIVOS : ";
            LOGGER.error(msgError + ex.getMessage(), ex);
            throw new PacServiceException(msgError, ex);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public TBCRespDescarga descargarCodigos(TBCSolDescarga tbcSolDescarga) throws PacServiceException {
        try {
            validaAcceso(tbcSolDescarga.getRFC(), null);

            TBCRespDescarga respDescarga = factory.createTBCRespDescarga();
            Long idSolicitud = Long.valueOf(tbcSolDescarga.getFolio());
            Archivo archivo = pacDao.getArchivo(idSolicitud, tbcSolDescarga.getRFC(), tbcSolDescarga.getNomArch());
            if (archivo == null) {
                throw new PacServiceException("FOLIO O NOMBRE DE ARCHIVO INVALIDO");
            }
            String folioAcuse = crearAcuse(TipoAcuse.DESCARGA, idSolicitud, null, null);

            respDescarga.setCantidadCodigos(archivo.getNumLinea().intValue());
            DataSource dataSource = new FileDataSource(archivo.getRutaArchivo());
            DataHandler dataHandler = new DataHandler(dataSource);
            respDescarga.setContenido(dataHandler);
            respDescarga.setFechaDescarga(Utilerias.fechaHoy());
            respDescarga.setFolio(tbcSolDescarga.getFolio());
            respDescarga.setFolioAcuse(folioAcuse);
            respDescarga.setNomArch(tbcSolDescarga.getNomArch());
            respDescarga.setVersion(Version.UNO);

            return respDescarga;
        } catch (AccesoNoPermitidoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new PacServiceException(e.getMessage(), e);
        } catch (PacDaoException e) {
            String msgError = "ERROR AL DESCARGAR CODIGOS: ";
            LOGGER.error(msgError + e.getMessage(), e);
            throw new PacServiceException(msgError, e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public TBCAcuseRetroInfo retroalimentarCodigos(TBCRetroInfo tbcRetroInfo) throws PacServiceException {
        try {
            validaAcceso(tbcRetroInfo.getRFCProveedorCertificado(), null);
            List listas = new ArrayList();
            List<Produccion> lstProd = tbcRetroInfo.getProduccion();
            List<Destruccion> lstDest = tbcRetroInfo.getDestruccion();
            List<CodigosNoValidos> lstInv = tbcRetroInfo.getCodigosNoValidos();

            listas.add(lstProd);
            listas.add(lstDest);
            listas.add(lstInv);
            validaListas(listas);

            AcuseRetro acuseRetro = null;
            if (lstProd != null && !lstProd.isEmpty()) {
                acuseRetro = procesaProduccion(lstProd);

            } else if (lstDest != null && !lstDest.isEmpty()) {
                acuseRetro = procesaDestruccion(lstDest);

            } else if (lstInv != null && !lstInv.isEmpty()) {
                acuseRetro = procesaInvalido(lstInv);

            }

            TBCAcuseRetroInfo acuseRetroInfo = factory.createTBCAcuseRetroInfo();
            acuseRetroInfo.setAcuse(acuseRetro);
            acuseRetroInfo.setFecha(Utilerias.fechaHoy());
            acuseRetroInfo.setVersion(Version.UNO);

            return acuseRetroInfo;
        } catch (AccesoNoPermitidoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new PacServiceException(e.getMessage(), e);
        }
    }

    private void validaListas(List listas) throws PacServiceException {
        int noListas = 0;
        List list;
        for (Object obj : listas) {
            if (obj == null) {
                continue;
            }
            list = (List) obj;
            if (!list.isEmpty()) {
                noListas++;
            }
        }

        if (noListas == 0) {
            throw new PacServiceException("NO HAY NINGUNA LISTA PARA RETROALIMENTAR");
        } else if (noListas > 1) {
            throw new PacServiceException("SOLO SE PERMIRMITE UN SOLO TIPO DE LISTA");
        }
    }

    private void validaAcceso(String rfcProveedor, String rfcTabacalera) throws AccesoNoPermitidoException {
        boolean existe = false;
        ValidarAccesoRespuesta accesoRespuesta = accesoService.validarAcceso(rfcProveedor);
        if (rfcTabacalera != null) {
            if (accesoRespuesta.getTabacaleras() != null && !accesoRespuesta.getTabacaleras().isEmpty()) {
                for (int i=0;i<accesoRespuesta.getTabacaleras().size();i++) {
                    if (accesoRespuesta.getTabacaleras().get(i).getRfc().equalsIgnoreCase(rfcTabacalera)) {
                        existe = true;
                        break;
                    }
                }
            }
        } else {
            existe = true;
        }

        if (!existe) {
            throw new AccesoNoPermitidoException("TABACALERA NO ASOCIADA AL PROVEEDOR");
        }
    }

    private AcuseTransaccion crearSolicitud(TBCSlcCodSeg tbcSlcCodSeg) throws PacServiceException {

        try {
            Solicitud solicitud = new Solicitud();
            solicitud.setCantSolicitada(tbcSlcCodSeg.getSolicitud().getCantidadCodigos().longValue());
            RelacionTabProv tabProv = commonService.getRelacionTabProv(tbcSlcCodSeg.getRFCProveedorCertificado(), tbcSlcCodSeg.getSolicitud().getRFC());
            solicitud.setIdTbcProv(tabProv.getIdTbcProv());

            PaisOrigen paisOrigen = commonService.getPaisByCodigo(tbcSlcCodSeg.getSolicitud().getOrigen());
            if (paisOrigen == null) {
                throw new PacServiceException(PAIS_NO_ENCONTRADO);
            }

            solicitud.setIdPaisOrigen(paisOrigen.getIdPais());

            Calendar cal = Calendar.getInstance();
            solicitud.setFecSolicitud(cal.getTime());
            solicitud.setIdTipoContrib(Long.valueOf(tbcSlcCodSeg.getSolicitud().getTipoContribuyente()));

            return commonService.crearSolicitud(solicitud, tbcSlcCodSeg.getSolicitud().getFirma(), null);
        } catch (CommonServiceException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private String crearAcuse(TipoAcuse tipoAcuse, Long idSolicitud, String selloDigital, String cadenaOriginal) throws PacServiceException {
        try {
            Calendar cal = Calendar.getInstance();
            mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse acu = new mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse();
            acu.setSerieAcuse(tipoAcuse);
            acu.setIdSolicitud(idSolicitud);
            acu.setSelloDigital(selloDigital);
            acu.setCadenaOriginal(cadenaOriginal);
            acu.setFecCaptura(cal.getTime());

            return commonService.crearAcuse(acu);
        } catch (CommonServiceException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private AcuseRetro procesaProduccion(List<Produccion> lstProduccion) throws PacServiceException {

        try {
            Map<ProduccionCigarros, List<RangoFolio>> mapProduccion = new HashMap<ProduccionCigarros, List<RangoFolio>>();
            ProduccionCigarros produccionCigarro;
            Planta planta;
            Marcas marca;
            PaisOrigen paisOrigen;

            String rfc = "";
            boolean init = false;
            List<RangoFolio> rangoFolios;
            for (Produccion p : lstProduccion) {

                if (!init) {
                    init = true;
                    rfc = p.getRFC();
                }
                if (!rfc.equals(p.getRFC())) {
                    throw new PacServiceException(RETROALIMENTAR_UNA_TABACALERA.concat(" ").concat(p.getRFC()));
                }

                planta = getPlanta(p.getRFC(), p.getPlantaProduccion());
                marca = getMarca(p.getRFC(), p.getMarca());
                paisOrigen = getPaisOrigen(p.getOrigen());

                produccionCigarro = new ProduccionCigarros();
                produccionCigarro.setIdMarca(marca.getIdMarca());
                produccionCigarro.setDescMaquinaProd(p.getMaquinaProduccion());
                produccionCigarro.setNumLote(p.getLoteProduccion());
                produccionCigarro.setFechImportacion(Utilerias.xmlgCalendarToDate(p.getFechaImportacion()));
                produccionCigarro.setFechProduccion(Utilerias.xmlgCalendarToDate(p.getFechaHoraProd()));
                produccionCigarro.setIdPaisOrigen(paisOrigen.getIdPais());
                produccionCigarro.setDescPaisOrigen(paisOrigen.getDescLarga());
                produccionCigarro.setCantidadCigarros(p.getCantidadCigarros());
                produccionCigarro.setCantidadProd(p.getCantidadProduccion());
                produccionCigarro.setLineaProd(p.getLineaProd());
                produccionCigarro.setIdPlantaProd(planta.getIdPlanta());
                produccionCigarro.setIdTipoRetro(null);

                rangoFolios = parseaListaRangos(p.getRangos());
                try {
                    rangoFolios = rangosService.generarRangosProduccion(p.getRFC(), rangoFolios);
                    if (rangoFolios != null && !rangoFolios.isEmpty()) {
                        mapProduccion.put(produccionCigarro, rangoFolios);
                    }
                } catch (RangosException e) {
                    throw new PacServiceException(e.getMessage(), e);
                }
            }

            String folioAcuse = codigosService.guardarProduccion(mapProduccion);
            AcuseRetro acuseRetro = factory.createTBCAcuseRetroInfoAcuse();
            acuseRetro.setRFC(rfc);
            acuseRetro.setFolioAcuse(folioAcuse);
            acuseRetro.setIncidencia(RETRO_REGISTRADA);

            return acuseRetro;
        } catch (RetroCodigosException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private Planta getPlanta(String rfcTabacalera, String plantaProduccion) throws PacServiceException {
        try {
            Planta planta = pacDao.getPlanta(rfcTabacalera, plantaProduccion);
            if (planta == null) {
                throw new PacServiceException(PLANTA_NO_ENCONTRADA);
            }
            return planta;
        } catch (PacDaoException e) {
            throw new PacServiceException(PLANTA_NO_ENCONTRADA, e);
        }
    }

    private Marcas getMarca(String rfcTabacalera, String claveMarca) throws PacServiceException {
        try {
            Marcas marca = pacDao.getMarca(rfcTabacalera, claveMarca);
            if (marca == null) {
                throw new PacServiceException(MARCA_NO_ENCONTRADA);
            }
            return marca;
        } catch (PacDaoException e) {
            throw new PacServiceException(MARCA_NO_ENCONTRADA, e);
        }
    }

    private PaisOrigen getPaisOrigen(String clavePais) throws PacServiceException {
        try {
            PaisOrigen paisOrigen = commonService.getPaisByCodigo(clavePais);
            if (paisOrigen == null) {
                throw new PacServiceException(PAIS_NO_ENCONTRADO);
            }
            return paisOrigen;
        } catch (CommonServiceException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private AcuseRetro procesaDestruccion(List<Destruccion> lstDestruccion) throws PacServiceException {
        try {
            Map<ProduccionCigarros, List<RangoFolio>> mapDestruccion = new HashMap<ProduccionCigarros, List<RangoFolio>>();
            ProduccionCigarros destruccionCigarro;
            Planta planta;
            Marcas marca;
            PaisOrigen paisOrigen;

            String rfc = "";
            boolean init = false;
            List<RangoFolio> rangoFolios;
            for (Destruccion d : lstDestruccion) {

                if (!init) {
                    init = true;
                    rfc = d.getRFC();
                }
                if (!rfc.equals(d.getRFC())) {
                    throw new PacServiceException(RETROALIMENTAR_UNA_TABACALERA.concat(" ").concat(d.getRFC()));
                }

                planta = getPlanta(d.getRFC(), d.getPlantaProduccion());
                marca = getMarca(d.getRFC(), d.getMarca());
                paisOrigen = getPaisOrigen(d.getOrigen());

                destruccionCigarro = new ProduccionCigarros();
                destruccionCigarro.setIdMarca(marca.getIdMarca());
                destruccionCigarro.setDescMaquinaProd(d.getMaquinaProduccion());
                destruccionCigarro.setNumLote(d.getLoteProduccion());
                destruccionCigarro.setFechProduccion(Utilerias.xmlgCalendarToDate(d.getFechaHoraReg()));
                destruccionCigarro.setIdPaisOrigen(paisOrigen.getIdPais());
                destruccionCigarro.setDescPaisOrigen(paisOrigen.getDescLarga());
                destruccionCigarro.setCantidadCigarros(d.getCantidadDestruccion());
                destruccionCigarro.setCantidadProd(d.getCantidadProduccion());
                destruccionCigarro.setIdPlantaProd(planta.getIdPlanta());
                destruccionCigarro.setIdTipoRetro(TipoRetroEnum.DESTRUCCION.getKey().longValue());

                rangoFolios = new ArrayList<RangoFolio>();
                rangoFolios.add(new RangoFolio(null, Long.valueOf(d.getRangos().getLimInif()), Long.valueOf(d.getRangos().getLimSup()), null, null, null));

                try {
                    rangoFolios = rangosService.generarRangosProduccion(d.getRFC(), rangoFolios);
                    if (rangoFolios != null && !rangoFolios.isEmpty()) {
                        mapDestruccion.put(destruccionCigarro, rangoFolios);
                    }
                } catch (RangosException e) {
                    throw new PacServiceException(e.getMessage(), e);
                }

            }

            String folioAcuse = codigosService.guardarDestruccion(mapDestruccion);
            AcuseRetro acuseRetro = factory.createTBCAcuseRetroInfoAcuse();
            acuseRetro.setRFC(rfc);
            acuseRetro.setFolioAcuse(folioAcuse);
            acuseRetro.setIncidencia(RETRO_REGISTRADA);
            return acuseRetro;
        } catch (RetroCodigosException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private AcuseRetro procesaInvalido(List<CodigosNoValidos> lstFalso) throws PacServiceException {
        try {
            Map<CodigoInvalido, List<RangoFolio>> mapInvalido = new HashMap<CodigoInvalido, List<RangoFolio>>();
            CodigoInvalido codigoInvalido;
            List<RangoFolio> rangoFolios;

            Long inferior;
            Long superior;
            String rfc = "";
            boolean init = false;
            for (CodigosNoValidos cnv : lstFalso) {

                if (!init) {
                    init = true;
                    rfc = cnv.getRFC();
                }
                if (!rfc.equals(cnv.getRFC())) {
                    throw new PacServiceException(RETROALIMENTAR_UNA_TABACALERA.concat(" ").concat(cnv.getRFC()));
                }

                inferior = Long.valueOf(cnv.getRangos().getLimInif());
                superior = Long.valueOf(cnv.getRangos().getLimSup());

                codigoInvalido = new CodigoInvalido();
                codigoInvalido.setJustificacion(cnv.getJustificacion());

                rangoFolios = new ArrayList<RangoFolio>();
                rangoFolios.add(new RangoFolio(null, inferior, superior, null, null, null));

                try {
                    rangoFolios = rangosService.generarRangosInvalidos(cnv.getRFC(), rangoFolios);
                    if (rangoFolios != null && !rangoFolios.isEmpty()) {
                        mapInvalido.put(codigoInvalido, rangoFolios);
                    }
                } catch (RangosException e) {
                    throw new PacServiceException(e.getMessage(), e);
                }

            }

            String folioAcuse = codigosService.guardarInvalido(mapInvalido);
            AcuseRetro acuseRetro = factory.createTBCAcuseRetroInfoAcuse();
            acuseRetro.setRFC(rfc);
            acuseRetro.setFolioAcuse(folioAcuse);
            acuseRetro.setIncidencia(RETRO_REGISTRADA);
            return acuseRetro;
        } catch (RetroCodigosException ex) {
            LOGGER.error(ex);
            throw new PacServiceException(ex);
        }
    }

    private List<RangoFolio> parseaListaRangos(List<RangoCodigosSeguridad> lstRangos) {
        List<RangoFolio> rangoFolios = new ArrayList<RangoFolio>();
        if (lstRangos != null) {
            RangoFolio rf;
            for (RangoCodigosSeguridad rcs : lstRangos) {
                rf = new RangoFolio(null, Long.valueOf(rcs.getLimInif()), Long.valueOf(rcs.getLimSup()), null, null, null);
                rangoFolios.add(rf);
            }
        }
        return rangoFolios;
    }

    private List<Archivos> parseaListaArchivos(List<Archivo> lstArchivos) {
        List<Archivos> lst = new ArrayList<Archivos>();
        try {
            Archivos archivo;
            File file;
            for (Archivo a : lstArchivos) {
                archivo = factory.createTBCRespConsulFolioFolioArchivos();
                if (a.getNumLinea() != null) {
                    archivo.setCantidadCodigos(a.getNumLinea().intValue());
                }

                file = new File(a.getRutaArchivo());
                archivo.setChecksum(DownloadFileUtil.calcularCheckSum("SHA-256", file.getPath()));
                archivo.setFechaHorCreacion(Utilerias.dateToXMLGCalendar(a.getFecRegistro()));
                archivo.setNomArch(file.getName());

                lst.add(archivo);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return lst;
    }
}
