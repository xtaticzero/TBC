/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoArchivoEnum;
import mx.gob.sat.mat.tabacos.modelo.dao.ArchivoDao;
import mx.gob.sat.mat.tabacos.modelo.dao.AutorizarSolicitudDao;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;
import mx.gob.sat.mat.tabacos.modelo.exceptions.DaoException;
import mx.gob.sat.mat.tabacos.negocio.AutorizarSolicitudService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException;
import mx.gob.sat.mat.tabacos.negocio.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MTI Agustin Romero Mata.
 */
@Service
@Qualifier("autorizarSolicitudService")
public class AutorizarSolicitudServiceImpl implements AutorizarSolicitudService {

    @Autowired
    @Qualifier("autorizarSolicitudDaoImpl")
    private AutorizarSolicitudDao autorizarSolicitudDao;

    @Autowired
    private ReporterService reporterService;

    @Value("#{tbc_prop_ambiente['base.path.file']}")
    private String directorioBase;

    @Value("#{tbc_prop_ambiente['ruta.archivo.autorizacionSolicitud']}")
    private String dirDoctosAutorizacionSolicitudes;

    @Autowired
    @Qualifier("archivoDao")
    private ArchivoDao archivoDao;

    @Autowired
    @Qualifier("fileUtil")
    private FileUtil fileUtil;

    private static final org.apache.log4j.Logger LOG
            = org.apache.log4j.Logger.getLogger(AutorizarSolicitudServiceImpl.class);

    private static final String MSG_ERROR_GUARDAR = "No se pudo guardar la solicitud";

    @Override
    public List<SelectItem> obtenerCombos(String strSQL, String id,
            String valor) {
        return autorizarSolicitudDao.getCombos(strSQL, id, valor);
    }

    @Override
    public List<SolicitudesRegistradas> obtenerSolicitudes(int tipo,
            String valor1, Date valor2) throws ServiceException {
        try {
            return autorizarSolicitudDao.
                    obtenerSolicitudesRegistradas(tipo, valor1, valor2);
        } catch (DaoException ex) {
            LOG.error(ex);
            throw new ServiceException(ex);
        }
    }

    /**
     *
     * @param info
     * @param contenidoArchivo
     * @param rutaArchivo
     * @param nombreArchivo
     * @return
     * @throws mx.gob.sat.mat.tabacos.negocio.excepcion.ServiceException
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int autorizarSolicitud(SolitudAutorizada info, InputStream contenidoArchivo, String rutaArchivo, String nombreArchivo) throws ServiceException {
        try {

            int resultadoAutorizacion;

            resultadoAutorizacion = autorizarSolicitudDao.autorizarSolicitud(info);

            switch (resultadoAutorizacion) {
                case 1:
                    int resultadoArchivo;
                    resultadoArchivo = archivoDao.insertArchivoAtorizacionSol(generaArchivoResolucion(info, rutaArchivo, nombreArchivo));

                    switch (resultadoArchivo) {
                        case 1:
                            if (!fileUtil.cearArchivo(contenidoArchivo, rutaArchivo, nombreArchivo)) {
                                throw new ServiceException("No se pudo cargar el documento legal");
                            }
                            break;

                        default:
                            throw new ServiceException(MSG_ERROR_GUARDAR);

                    }

                    break;

                default:
                    throw new ServiceException(MSG_ERROR_GUARDAR);

            }
            
            return resultadoAutorizacion;
        } catch (DaoException ex) {
            throw new ServiceException(MSG_ERROR_GUARDAR, ex);
        }
    }

    private Archivo generaArchivoResolucion(SolitudAutorizada solicitud, String rutaArchivo, String nombreArchivo) {

        if (solicitud != null && !solicitud.getFolio().isEmpty()) {
            Archivo archivo = new Archivo();
            archivo.setIdSolicitud(Long.valueOf(solicitud.getFolio()));
            archivo.setRutaArchivo(rutaArchivo + nombreArchivo);
            archivo.setIdTipoArchivo(TipoArchivoEnum.TIPO_LEGAL.getId());
            archivo.setNumeroOficio(solicitud.getNumeroOficio());

            return archivo;
        }
        return new Archivo();
    }

    @Override
    public byte[] generarAcuse(SolitudAutorizada info) {
        try {
            Map parameteros = new HashMap();

            parameteros.put("folio", info.getFolio());
            parameteros.put("rfc", info.getRfc());
            parameteros.put("fecha", info.getFecha());
            parameteros.put("cantsol", info.getCantidadSolicitud());
            parameteros.put("servpub", info.getServidorPublicoAutorizanteInfo());
            parameteros.put("cantaut", info.getCantidadAutorizada());
            parameteros.put("docresol", info.getNombreArchivo());
            parameteros.put("estatus", info.getEstatusResolucionInfo());

            return reporterService.makeReport(ReportesTabacosEnum.REPORT_ACUSE_AUTSOL, ARCHIVO_PDF, parameteros, null);
        } catch (ReporterJasperException ex) {
            LOG.error(ex);
        }
        return null;
    }

    @Override
    public List<SolitudAutorizada> obtenerSolRegActual(int tipo, String valor1,
            Date valor2) {
        return autorizarSolicitudDao.
                obtenerSolRegActual(tipo, valor1, valor2);
    }

    public String getDirDoctosAutorizacionSolicitudes() {
        return dirDoctosAutorizacionSolicitudes;
    }

    public void setDirDoctosAutorizacionSolicitudes(String dirDoctosAutorizacionSolicitudes) {
        this.dirDoctosAutorizacionSolicitudes = dirDoctosAutorizacionSolicitudes;
    }

    public String getDirectorioBase() {
        return directorioBase + dirDoctosAutorizacionSolicitudes;
    }

    public String getDirDoctosAutSolicitud() {
        return dirDoctosAutorizacionSolicitudes;
    }
}
